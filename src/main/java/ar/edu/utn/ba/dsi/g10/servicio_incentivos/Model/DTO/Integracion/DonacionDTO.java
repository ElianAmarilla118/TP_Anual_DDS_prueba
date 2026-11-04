package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.DTO.Integracion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.CategoriasDonante.Categoria;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class DonacionDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date fechaEntrada = new Date();
    @OneToMany(mappedBy = "donante", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<String> categoriasIncluidas = new ArrayList<>();
    private Integer cantBienes;
    private List<Long> segmentadasIncluidas;
    private DonanteDTO donante;

    public Integer cantidadSegmentadasEntregadas() {
        return segmentadasIncluidas.size();
    }
}