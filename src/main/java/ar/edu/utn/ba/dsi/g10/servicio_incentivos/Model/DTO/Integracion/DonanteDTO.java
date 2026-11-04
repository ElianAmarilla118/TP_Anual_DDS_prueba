package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.DTO.Integracion;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DonanteDTO {
    private Long id;
    private String nombre;
    @OneToMany(mappedBy = "donante", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<DonacionDTO> donaciones;

    @OneToOne
    private MedioContactoDTO contactoPredeterminado;
}
