package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil;

import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.CategoriasDonante.CategoriaDonante;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones.AsignacionInsignia;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones.Insignia;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones.Mision;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "perfil_donante")
public class PerfilDonante {

    @Id
    @Column(name = "donante_id")
    private long donanteID;

    @Version
    private Long version;

    @Embedded
    private CategoriaDonante categoria;

    @Column(name = "total_donaciones_historicas", nullable = false)
    private int totalDonacionesHistoricas;

    @Column(name = "total_organizaciones_ayudadas", nullable = false)
    private int totalOrganizacionesAyudadas;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mision_actual_id")
    private Mision misionActual;

    @Embedded
    private RachaDonante racha;

    @OneToMany(
        mappedBy = "perfil",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private final List<AsignacionInsignia> insignias = new ArrayList<>();

    @OneToMany(
        mappedBy = "perfil",
        cascade = CascadeType.PERSIST
    )
    @OrderBy("id ASC")
    private final List<ProgresoMision> historialMisiones = new ArrayList<>();

    public PerfilDonante() {
    }

    public PerfilDonante(long donanteID, CategoriaDonante categoria, Mision misionActual) {
        this.donanteID = donanteID;
        this.categoria = categoria;
        this.misionActual = misionActual;
        this.racha = new RachaDonante();
        inicializarProgreso();
    }

    public long getDonanteID() {
        return donanteID;
    }

    public void setDonanteID(long donanteID) {
        this.donanteID = donanteID;
    }

    public long getID() {
        return donanteID;
    }

    public void setID(long donanteID) {
        this.donanteID = donanteID;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public CategoriaDonante getCategoriaDonante() {
        return categoria;
    }

    public void setCategoria(CategoriaDonante categoria) {
        this.categoria = categoria;
    }

    public String getCategoria() {
        return categoria != null ? categoria.getCategoria() : "Sin categoría";
    }

    public int getTotalDonacionesHistoricas() {
        return totalDonacionesHistoricas;
    }

    public void setTotalDonacionesHistoricas(int totalDonacionesHistoricas) {
        this.totalDonacionesHistoricas = totalDonacionesHistoricas;
    }

    public int getTotalOrganizacionesAyudadas() {
        return totalOrganizacionesAyudadas;
    }

    public void setTotalOrganizacionesAyudadas(int totalOrganizacionesAyudadas) {
        this.totalOrganizacionesAyudadas = totalOrganizacionesAyudadas;
    }

    public Mision getMisionActual() {
        return misionActual;
    }

    public void setMisionActual(Mision misionActual) {
        this.misionActual = misionActual;
    }

    public RachaDonante getRacha() {
        return racha;
    }

    public void setRacha(RachaDonante racha) {
        this.racha = racha;
    }

    public List<AsignacionInsignia> getInsigniasAsignadas() {
        return insignias;
    }

    public List<Insignia> getInsignias() {
        return insignias.stream()
            .map(AsignacionInsignia::getInsignia)
            .filter(Objects::nonNull)
            .toList();
    }

    public List<ProgresoMision> getHistorialMisiones() {
        return historialMisiones;
    }

    public double getPorcentajeProgreso() {
        ProgresoMision progreso = getProgreso();
        return progreso == null ? 0.0 : progreso.getProgresoActual();
    }

    public void subirCategoria() {
        if (categoria != null) {
            categoria.avanzarASiguienteCategoria();
        }
    }

    public boolean misionCompletada() {
        ProgresoMision progreso = getProgreso();
        return progreso != null && progreso.getCompletada();
    }

    public boolean otorgarInsigniaSiCorresponde() {
        if (!misionCompletada()) return false;

        Insignia insigniaAResolver = misionActual.getInsignia();
        if (insigniaAResolver == null) return false;

        boolean yaOtorgada = insignias.stream()
            .anyMatch(a -> a.getInsignia() != null && Objects.equals(a.getInsignia().getID(), insigniaAResolver.getID()));

        if (yaOtorgada) return false;

        insignias.add(new AsignacionInsignia(this, insigniaAResolver, LocalDate.now()));
        return true;
    }

    public ProgresoMision getProgreso() {
        if (misionActual == null) return null;

        return historialMisiones.stream()
            .filter(p -> p.getMisionAsociada() != null && Objects.equals(p.getMisionAsociada().getId(), misionActual.getId()))
            .findFirst()
            .orElse(null);
    }

    private void inicializarProgreso() {
        if (misionActual == null || getProgreso() != null) return;
        historialMisiones.add(new ProgresoMision(this, misionActual, LocalDate.now()));
    }

    public void sumarDonacion() {
        totalDonacionesHistoricas++;
    }

    public void incrementarOrganizacionesAyudadas() {
        totalOrganizacionesAyudadas++;
    }

    public void verificarRachaDonaciones(LocalDate fechaNuevaDonacion) {
        if (this.racha == null) {
            this.racha = new RachaDonante();
        }
        this.racha.registrarDonacion(fechaNuevaDonacion);
    }

    public String getCategoriaNombre() {
        return categoria != null ? categoria.getCategoria() : "Sin categoría";
    }

    public int getMisionesCompletadas() {
        return (int) historialMisiones.stream()
            .filter(ProgresoMision::getCompletada)
            .count();
    }

    public void asignarMision(Mision mision) {
        this.misionActual = mision;
        inicializarProgreso();
    }
}

