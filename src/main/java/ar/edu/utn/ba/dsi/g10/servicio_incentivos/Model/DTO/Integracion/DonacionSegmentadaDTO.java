package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.DTO.Integracion;

//import com.grupo10.servicio_donaciones.donaciones.dominio.bien.UnidadMedida;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DonacionSegmentadaDTO {
    private Long idSegmentada;
    private Integer cantidad; //cantidad de bienes?
   // private UnidadMedida unidad;
}

// No hacemos nada con la UnidadMedida, ver que hacer con este parametro