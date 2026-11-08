package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.DTO.Integracion;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DonacionAsignadaDTO {
    private Long idAsignada;
    private List<DonacionSegmentadaDTO> donacionesSegmentadas; //DonacionesSegmentadas completadas que ya se entregaron. 
    private Long idEntidad;
    private String direccionEntidad;
}