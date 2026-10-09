package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones;

import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.CategoriasDonante.CategoriaDonante;

import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil.DonacionImportada;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public abstract class Mision {
    private long id;
    private String nombre;
    private String descripcion;
    //TODO: Revisar si la inicializacion esta bien
    private CategoriaDonante categoria = new CategoriaDonante()/*Inicializo la categotia*/; // minúscula inicial
    private int orden;
    private Insignia insignia;           // minúscula inicial

    public abstract double calcularProgreso(List<DonacionImportada> historialProgreso);

    public String getObjetivo() {
        return descripcion;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria != null ? categoria.getCategoria() : null;
    }

    public void setCategoria(CategoriaDonante categoria) {
        this.categoria = categoria;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public Insignia getInsignia() {
        return insignia;
    }

    public void setInsignia(Insignia insignia) {
        this.insignia = insignia;
    }
}

