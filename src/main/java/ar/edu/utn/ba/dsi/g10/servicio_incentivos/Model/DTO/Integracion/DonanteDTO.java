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
    private List<DonacionDTO> donaciones;
    private Object contactoPredeterminado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<DonacionDTO> getDonaciones() {
        return donaciones;
    }

    public void setDonaciones(List<DonacionDTO> donaciones) {
        this.donaciones = donaciones;
    }

    public Object getContactoPredeterminado() {
        return contactoPredeterminado;
    }

    public void setContactoPredeterminado(Object contactoPredeterminado) {
        this.contactoPredeterminado = contactoPredeterminado;
    }
}
