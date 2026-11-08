package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil;

import java.time.LocalDate;
import jakarta.persistence.*;


// REVISAR


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

   // private int idOrganizacion;

    private List<String> categoria;

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

    
    public List<String> getCategoria() {
        return categoria;
    }

    public void setCategoria(List<String> categoria) {
        //asociacion directa a la direccion del vector, no genera otro vector
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
    public int getIdOrganizacion() {
        return idOrganizacion;
    }

    public void setIdOrganizacion(int idOrganizacion) {
        this.idOrganizacion = idOrganizacion;
    }
}