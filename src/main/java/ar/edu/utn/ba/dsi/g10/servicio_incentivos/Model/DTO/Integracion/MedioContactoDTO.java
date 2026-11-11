package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.DTO.Integracion;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class MedioContactoDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private MedioNotificacion tipo;
    private String correoElectronico;
    private String telefono;

    public String getCorreo() {
        return correoElectronico;
    }

    public void setCorreo(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }
}
