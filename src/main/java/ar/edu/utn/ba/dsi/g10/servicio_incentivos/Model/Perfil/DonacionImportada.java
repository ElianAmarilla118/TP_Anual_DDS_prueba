package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name = "donacion_importada")
public class DonacionImportada {

    @Id
    @Column(name = "donacion_id")
    private long donacionId;

    @Column(name = "donante_id", nullable = false)
    private long donanteId;

    @Column(name = "cantidad_donada", nullable = false)
    private int cantidadDonada;

    private String categoria;

    @Column(nullable = false)
    private boolean exitosa;

    @Column(name = "fecha_donacion", nullable = false)
    private LocalDate fechaDonacion;

    public long getDonacionId() {
        return donacionId;
    }

    public void setDonacionId(long donacionId) {
        this.donacionId = donacionId;
    }

    public long getDonanteId() {
        return donanteId;
    }

    public void setDonanteId(long donanteId) {
        this.donanteId = donanteId;
    }

    public int getCantidadDonada() {
        return cantidadDonada;
    }

    public void setCantidadDonada(int cantidadDonada) {
        this.cantidadDonada = cantidadDonada;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public boolean isExitosa() {
        return exitosa;
    }

    public boolean getExitosa() {
        return exitosa;
    }

    public void setExitosa(boolean exitosa) {
        this.exitosa = exitosa;
    }

    public LocalDate getFechaDonacion() {
        return fechaDonacion;
    }

    public void setFechaDonacion(LocalDate fechaDonacion) {
        this.fechaDonacion = fechaDonacion;
    }
}
