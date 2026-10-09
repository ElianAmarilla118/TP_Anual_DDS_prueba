package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DonacionImportada {
    private long donacionId; //evitamos procesar dos veces la misma donacion
    private long donanteId; // para localizarlo luego
    private int cantidadDonada;
    private String categoria; //entendiendo que procesamos una sola categoria en la donacion
    private boolean exitosa;
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
