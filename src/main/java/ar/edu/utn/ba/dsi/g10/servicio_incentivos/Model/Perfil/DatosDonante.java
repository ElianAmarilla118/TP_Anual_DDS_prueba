package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil;

import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.DTO.Integracion.DonacionDTO;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil.MedioContacto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DatosDonante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nombre;
    @OneToMany(mappedBy = "donante", cascade = {CascadeType.PERSIST, CascadeType.MERGE}) 
    private List<DonacionDTO> donaciones;

    @OneToOne 
    private MedioContacto ContactoPredeterminado;
}
