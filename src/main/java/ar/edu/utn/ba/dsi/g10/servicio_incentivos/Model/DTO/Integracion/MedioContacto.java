package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.DTO.Integracion;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(exclude = {"donante"})
@Entity
@NoArgsConstructor
public class MedioContacto {
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