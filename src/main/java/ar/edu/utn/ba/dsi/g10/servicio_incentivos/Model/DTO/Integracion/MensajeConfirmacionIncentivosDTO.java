package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.DTO.Integracion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter 
@Setter 
@NoArgsConstructor
@AllArgsConstructor
public class MensajeConfirmacionIncentivosDTO {
    private Long idAsignada; //id asignada del user o la donacion???
    private List<Long> idSegmentadasIncluidos;
    private Long idEntidad;
}
