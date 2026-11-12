package ar.edu.utn.ba.dsi.g10.frontend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PerfilMetricasDTO {
    private long id;
    private String categoriaActual;
    private int cantidadInsignias;
    private int misionesCompletadas;
}