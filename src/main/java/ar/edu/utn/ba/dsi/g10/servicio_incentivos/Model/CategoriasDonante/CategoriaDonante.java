package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.CategoriasDonante;

import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones.Mision;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class CategoriaDonante {

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria", nullable = false)
    private Categoria categoria = Categoria.COLABORADOR;

    @Transient
    private final List<Mision> misiones = new ArrayList<>();

    public CategoriaDonante() {
        this.categoria = Categoria.COLABORADOR;
    }

    public Categoria getCategoriaEnum() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Categoria getCategoriaObj() {
        return categoria;
    }

    public String getCategoria() {
        return (this.categoria != null) ? this.categoria.getNombre() : Categoria.COLABORADOR.getNombre();
    }

    public List<Mision> getMisiones() {
        return misiones;
    }

    public void avanzarASiguienteCategoria() {
        if (this.categoria != null) {
            this.categoria = this.categoria.siguiente();
        }
    }

    public void agregarMision(Mision mision) {
        if (mision != null) {
            this.misiones.add(mision);
        }
    }

    public void initCategoria() {
        this.categoria = Categoria.COLABORADOR;
    }
}
