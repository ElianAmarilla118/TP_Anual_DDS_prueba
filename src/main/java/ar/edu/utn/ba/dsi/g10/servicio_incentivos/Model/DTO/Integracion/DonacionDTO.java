package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.DTO.Integracion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DonacionDTO {
    private Long id;
    private Date fechaEntrada;
    private List<Object> categoriasIncluidas; // o List<String> / DTO si solo precisan el nombre
    private Integer cantBienes;
    private Integer cantidadSegmentadasEntregadas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(Date fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public List<Object> getCategoriasIncluidas() {
        return categoriasIncluidas;
    }

    public void setCategoriasIncluidas(List<Object> categoriasIncluidas) {
        this.categoriasIncluidas = categoriasIncluidas;
    }

    public Integer getCantBienes() {
        return cantBienes;
    }

    public void setCantBienes(Integer cantBienes) {
        this.cantBienes = cantBienes;
    }

    public Integer getCantidadSegmentadasEntregadas() {
        return cantidadSegmentadasEntregadas;
    }

    public void setCantidadSegmentadasEntregadas(Integer cantidadSegmentadasEntregadas) {
        this.cantidadSegmentadasEntregadas = cantidadSegmentadasEntregadas;
    }
}
