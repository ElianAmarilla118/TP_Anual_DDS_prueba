package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.DTO.Integracion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DonacionDTO {
    private Long id;
    private Date fechaEntrada = new Date();
    private List<String> categoriasIncluidas = new ArrayList<>();
    private Integer cantBienes;
    private List<Long> segmentadasIncluidas; //SOLO las IDs de las segmentadas. AUN no entregadas.
    private DonanteDTO donante;

    public Integer cantidadSegmentadasEntregadas() {
        return segmentadasIncluidas == null ? 0 : segmentadasIncluidas.size();
    }
}