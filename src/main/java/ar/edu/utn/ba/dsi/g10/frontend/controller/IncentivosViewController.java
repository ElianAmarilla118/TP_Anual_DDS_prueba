package ar.edu.utn.ba.dsi.g10.frontend.controller;

import ar.edu.utn.ba.dsi.g10.frontend.dto.InsigniaResponseDTO;
import ar.edu.utn.ba.dsi.g10.frontend.dto.MisionResponseDTO;
import ar.edu.utn.ba.dsi.g10.frontend.dto.PerfilMetricasDTO;
import ar.edu.utn.ba.dsi.g10.frontend.dto.PosicionRankingDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/ui/incentivos")
public class IncentivosViewController {

    private final RestClient restClient;

    public IncentivosViewController(RestClient.Builder builder) {
        // Configurable luego via @Value("${servicios.incentivos.url}")
        this.restClient = builder.baseUrl("http://localhost:8080/incentivos").build();
    }

    // ==========================================
    // VISTAS DE LECTURA (GET) - TIPADAS CON DTOs
    // ==========================================

    @GetMapping("/{donanteId}/metricas")
    public String verMetricas(@PathVariable Long donanteId, Model model) {
        try {
            PerfilMetricasDTO metricas = restClient.get()
                    .uri("/{id}/metricas", donanteId)
                    .retrieve()
                    .body(PerfilMetricasDTO.class);

            model.addAttribute("metricas", metricas);
            model.addAttribute("donanteId", donanteId);
        } catch (Exception e) {
            model.addAttribute("error", "No se pudieron cargar las métricas del donante #" + donanteId);
            model.addAttribute("donanteId", donanteId);
        }
        return "incentivos/vista-metricas";
    }

    @GetMapping("/{donanteId}/misiones")
    public String verMisionActual(@PathVariable Long donanteId, Model model) {
        try {
            MisionResponseDTO mision = restClient.get()
                    .uri("/{id}/misiones", donanteId)
                    .retrieve()
                    .body(MisionResponseDTO.class);

            model.addAttribute("mision", mision);
            model.addAttribute("donanteId", donanteId);
        } catch (Exception e) {
            model.addAttribute("error", "No hay misión activa o el perfil #" + donanteId + " no existe.");
            model.addAttribute("donanteId", donanteId);
        }
        return "incentivos/vista-misiones";
    }

    @GetMapping("/{donanteId}/insignias")
    public String verInsignias(@PathVariable Long donanteId, Model model) {
        try {
            List<InsigniaResponseDTO> insignias = restClient.get()
                    .uri("/{id}/insignias", donanteId)
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<InsigniaResponseDTO>>() {});

            model.addAttribute("insignias", insignias);
            model.addAttribute("donanteId", donanteId);
        } catch (Exception e) {
            model.addAttribute("error", "No se pudieron obtener las insignias.");
            model.addAttribute("donanteId", donanteId);
        }
        return "incentivos/vista-insignias";
    }

    @GetMapping("/ranking-mensual")
    public String verRanking(Model model) {
        try {
            List<PosicionRankingDTO> ranking = restClient.get()
                    .uri("/ranking-mensual")
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<PosicionRankingDTO>>() {});

            model.addAttribute("ranking", ranking);
        } catch (Exception e) {
            model.addAttribute("error", "Error al consultar el ranking mensual.");
        }
        return "incentivos/vista-ranking";
    }

    // ==========================================
    // VISTAS Y ACCIONES DE ESCRITURA (POST/DELETE)
    // ==========================================

    @GetMapping("/admin/gestion")
    public String vistaGestionAdmin() {
        return "incentivos/vista-gestion-admin";
    }

    // Ejecuta POST /incentivos/perfiles
    @PostMapping("/perfiles")
    public String crearPerfil(@RequestParam Long donanteId, RedirectAttributes redirectAttributes) {
        try {
            // Tu backend espera un DonanteDTO con al menos el campo "id"
            restClient.post()
                    .uri("/perfiles")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of("id", donanteId))
                    .retrieve()
                    .toBodilessEntity();

            redirectAttributes.addFlashAttribute("exito", "Perfil creado correctamente para el donante #" + donanteId);
            return "redirect:/ui/incentivos/" + donanteId + "/metricas";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al crear perfil (verifique si ya existe o el ID es inválido).");
            return "redirect:/ui/incentivos/admin/gestion";
        }
    }

    // Ejecuta POST /incentivos/{donanteId}/nueva-mision
    @PostMapping("/{donanteId}/nueva-mision")
    public String asignarMisionRefuerzo(@PathVariable Long donanteId, RedirectAttributes redirectAttributes) {
        try {
            restClient.post()
                    .uri("/{id}/nueva-mision", donanteId)
                    .retrieve()
                    .toBodilessEntity();

            redirectAttributes.addFlashAttribute("exito", "Nueva misión de refuerzo asignada.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "No se pudo asignar la misión al donante #" + donanteId);
        }
        return "redirect:/ui/incentivos/" + donanteId + "/misiones";
    }

    // Ejecuta DELETE /incentivos/perfiles/{donanteId}
    @PostMapping("/perfiles/{donanteId}/eliminar")
    public String eliminarPerfil(@PathVariable Long donanteId, RedirectAttributes redirectAttributes) {
        try {
            restClient.delete()
                    .uri("/perfiles/{id}", donanteId)
                    .retrieve()
                    .toBodilessEntity();

            redirectAttributes.addFlashAttribute("exito", "Perfil #" + donanteId + " eliminado correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "No se encontró el perfil #" + donanteId + " para eliminar.");
        }
        return "redirect:/ui/incentivos/admin/gestion";
    }
}