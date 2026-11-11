package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.DTO.Integracion;

import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil.DonacionImportada;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Component
public class DonacionMapper {

    public DonacionImportada aDonacionImportada(DonacionDTO dto) {
        if (dto == null) {
            return null;
        }

        DonacionImportada importada = new DonacionImportada();
        importada.setDonanteId(dto.getDonante().getId());
        importada.setDonacionId(dto. getId() != null ? dto.getId() : 0L);
        importada.setCantidadDonada(dto.getCantBienes() != null ? dto.getCantBienes() : 0);

        List<String> categorias = dto.getCategoriasIncluidas();
        if (categorias != null) {
            importada.setCategoria(categorias);
        }
        else {
            //#1: que devuelva una lista simple con elemento "general"
            importada.setCategoria(List.of());
        }
        importada.setSegmentadasIncluidas(dto.getSegmentadasIncluidas());


        /* 
        // Determinar si la donación se considera entregada/exitosa

        AHORA: esto se hace cuando recibe un mensaje del broker, que indica que la donacion fue entregada.. 

        
        boolean esExitosa = dto.getCantidadSegmentadasEntregadas() != null
                && dto.getCantidadSegmentadasEntregadas() > 0;
        importada.setExitosa(esExitosa);
         */
        // Conversión de Date a LocalDate para la racha
        if (dto.getFechaEntrada() != null) {
            LocalDate fecha = dto.getFechaEntrada().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            importada.setFechaDonacion(fecha);
        } else {
            importada.setFechaDonacion(LocalDate.now());
        }

        return importada;
    }
}
