package ar.edu.utn.ba.dsi.g10.frontend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MisionResponseDTO {
    private long id;
    private String nombre;
    private String descripcion;
    private String categoria;
    private String insigniaNombre;
    private double progreso;
}