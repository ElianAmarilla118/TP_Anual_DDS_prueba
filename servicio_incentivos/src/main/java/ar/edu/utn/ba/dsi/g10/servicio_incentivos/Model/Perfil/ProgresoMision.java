package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil;

import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones.Mision;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Getter
@Setter
@Entity
@Table(
    name = "historial_misiones",
    uniqueConstraints = @UniqueConstraint(
        columnNames = {"donante_id", "mision_id"}
    )
)
public class ProgresoMision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "donante_id", nullable = false)
    private PerfilDonante perfil;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mision_id", nullable = false)
    private Mision MisionAsociada;

    @Column(name = "progreso_actual", nullable = false)
    private double progresoActual;

    @ManyToMany
    @JoinTable(
        name = "historial_mision_donacion",
        joinColumns = @JoinColumn(name = "historial_id"),
        inverseJoinColumns = @JoinColumn(name = "donacion_id"),
        uniqueConstraints = @UniqueConstraint(
            columnNames = {"historial_id", "donacion_id"}
        )
    )
    @OrderBy("fechaDonacion ASC, donacionId ASC")
    private List<DonacionImportada> historialDonaciones = new ArrayList<>();

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_completado")
    private LocalDate fechaCompletado;

    @Column(nullable = false)
    private boolean completada;

    public ProgresoMision(
        PerfilDonante perfil,
        Mision mision,
        LocalDate fechaInicio
    ) {
        this.perfil = Objects.requireNonNull(perfil);
        this.misionAsociada = Objects.requireNonNull(mision);
        this.fechaInicio = Objects.requireNonNull(fechaInicio);
    }

    public void reiniciar() {
        historialDonaciones.clear();
        progresoActual = 0.0;
        completada = false;
        fechaCompletado = null;
    }

    public void actualizar(DonacionImportada donacion) {
        if (completada || donacion == null) return;

        if (donacion.getDonanteId() != perfil.getID()) {
            throw new IllegalArgumentException(
                "La donación pertenece a otro donante"
            );
        }

        boolean yaRegistrada = historialDonaciones.stream()
            .anyMatch(d -> d.getDonacionId() == donacion.getDonacionId());

        if (yaRegistrada) return;

        historialDonaciones.add(donacion);

        progresoActual = Math.max(
            0.0,
            Math.min(
                100.0,
                misionAsociada.calcularProgreso(historialDonaciones)
            )
        );

        if (progresoActual >= 100.0) {
            marcarCompletada();
        }
    }

    /*
    public void actualizar(DonacionImportada donacionImportada) {
        if (completada) {
            return;
        }

        if (donacionImportada == null || MisionAsociada == null) {
            return;
        }

        historialDonaciones.add(donacionImportada);

        progresoActual = MisionAsociada.calcularProgreso(
            historialDonaciones
        );

        if (progresoActual >= 100.0) {
            marcarCompletada();
        }
    }
*/
    public void marcarCompletada() {
        completada = true;
        if (fechaCompletado == null) {
            fechaCompletado = LocalDate.now();
        }
    }

    public boolean getCompletada() {
        return completada;
    }

}

