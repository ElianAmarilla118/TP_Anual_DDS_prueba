package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Controller;

import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones.Insignia;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones.Mision;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil.DatosDonante;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil.DonacionImportada;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil.PerfilDonante;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil.ProgresoMision;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Ranking.PosicionRanking;
import lombok.RequiredArgsConstructor;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.ServicioIncentivos;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.DTO.MisionResponseDTO;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.DTO.PosicionRankingDTO;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.DTO.Integracion.DonacionDTO;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.DTO.Integracion.DonanteDTO;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.DTO.Integracion.MensajeConfirmacionIncentivosDTO;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.DTO.PerfilMetricasDTO;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.DTO.InsigniaResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.DTO.CrearPerfilRequest;

import java.util.Collections;
import java.util.List;


//PENDIENTE: Manejo de errores


@RestController
@RequestMapping("/incentivos")
@RequiredArgsConstructor
public class IncentivosController {
    private final ServicioIncentivos servicio;
    
    //post funcionando
    @PostMapping("/{donanteId}/donaciones")
    public ResponseEntity<String> procesarDonacion(
            @PathVariable Long donanteId,
            @RequestBody DonacionDTO donacion) {
            
            DonacionImportada donacionImportada = new DonacionImportada();
            servicio.procesarNuevaDonacionDTO(donacion);
        return ResponseEntity.ok("Donación procesada correctamente");
    }

    // get funcionando, Se instancio una mision racha y al donar este iba aumentando el progreso.
    @GetMapping("/{donanteId}/misiones")
    public ResponseEntity<?> obtenerMisionActual(@PathVariable Long donanteId) {
   

  

        // cambios para que se pueda ver el progreso de la mision
        PerfilDonante perfil = servicio.getMetricas(donanteId);

        if (perfil == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Perfil no encontrado");
        }

        Mision mision = perfil.getMisionActual();
        if (mision == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Misión no encontrada");
        }

        ProgresoMision progresoMision = perfil.getProgreso();

        List<DonacionImportada> historial = (progresoMision != null && progresoMision.getHistorialDonaciones() != null)
                ? progresoMision.getHistorialDonaciones()
                : Collections.emptyList();

        MisionResponseDTO dto = new MisionResponseDTO(mision, historial);
        return ResponseEntity.ok(dto);
    }


    @GetMapping("/{donanteId}/insignias")
    public ResponseEntity<?> obtenerInsignias(@PathVariable Long donanteId) {
        List<Insignia> insignias = servicio.getInsignias(donanteId);
    
         if (insignias == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Insignias o perfil no encontrado");
            }
        

        List<InsigniaResponseDTO> listaDto = insignias.stream()
                .map(InsigniaResponseDTO::new)
                .toList();

        return ResponseEntity.ok(listaDto);
    }
    //test exitoso con perfil de test en repo
    @GetMapping("/{donanteId}/metricas")
    public ResponseEntity<?> obtenerMetricas(@PathVariable Long donanteId) {
        PerfilDonante perfilDonante = servicio.getMetricas(donanteId);
        //#3: Revisar y reestructurar metricas, separar entre donaciones ingresadas y entregas. 
        
        //ahora mismo las metricas solo devuelven el perfil completo, no solo metricas. Revisar logica de negocio. TODO: #3.
        if (perfilDonante == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Perfil no encontrado");
        }
        return ResponseEntity.ok(new PerfilMetricasDTO(perfilDonante));
    }
    //PARA THYMELEAF: estos tres GETs, misiones, insiginias y perfiles, devuelven objetos completos, tener esto en cuenta para la construccion de los datos en el front...

  @GetMapping("/ranking-mensual")
    public ResponseEntity<?> obtenerRankingMensual(){
    
    List<PosicionRanking> ranking = servicio.getRanking(10); 

    
    List<PosicionRankingDTO> rankingDTO = ranking.stream()
                .map(PosicionRankingDTO::new)
                .toList();


    System.out.println("DEBUG - Elementos en ranking original: " + ranking.size());
    System.out.println("DEBUG - Elementos en ranking dto: " + rankingDTO.size());
    return ResponseEntity.ok(rankingDTO);
}
    //Mismo aca: devuelve una lista de DTOS.


    // hardcodeado para probar la donacion 5
    // Recibe DTO mision? canmpos sueltos?
    @PostMapping("/{donanteId}/nueva-mision")
    public ResponseEntity<String> asignarMision(
            @PathVariable Long donanteId) {

        // Creamos una misión genérica rápida para la prueba
        ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones.MisionCompletitud nuevaMision = new ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones.MisionCompletitud();
        nuevaMision.setId(99L);
        nuevaMision.setNombre("Misión de Refuerzo");
        nuevaMision.setCategoriasDistintasRequeridas(1.0);

        servicio.asignarNuevaMision(donanteId, nuevaMision);

        return ResponseEntity.ok("Nueva misión asignada correctamente");
    }

    //metodo viejo eliminado, consultar commits anteriores.


    // crear perfil propuesto
    @PostMapping("/perfiles")
    public ResponseEntity<String> crearPerfil(
        @RequestBody DonanteDTO datos
    ) {
        Long donanteID = datos.getId();

        if (donanteID == null || donanteID <= 0) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("donanteID es obligatorio y debe ser positivo");
        }

        if (servicio.getMetricas(donanteID) != null) {
            return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("El donante ya tiene un perfil de incentivos");
        }


        
        servicio.crearPerfilDesdeDTO(datos);
    


        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body("Perfil creado correctamente");
    }

    
    // PUT: Actualizar un perfil existente buscando por ID
    @PutMapping("/perfiles/{donanteId}")
    public ResponseEntity<String> actualizarPerfil(
            @PathVariable Long donanteId,
            @RequestBody DonanteDTO perfilActualizado) {

        PerfilDonante existente = servicio.getMetricas(donanteId);
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Perfil no encontrado");
        }

        PerfilDonante perfil = servicio.getMetricas(donanteId);
        //perfil.setNombre(perfilActualizado.getNombre());
        perfil.setDatosDonante(new DatosDonante(
                perfilActualizado.getNombre(),
                perfilActualizado.getDonaciones(),
                perfilActualizado.getContactoPredeterminado()
        ));
        perfil.cargarDatos();
        servicio.guardarPerfil(perfil);

        return ResponseEntity.ok("Perfil actualizado correctamente");
    }

    // DELETE: Eliminar un perfil de donante por ID
    @DeleteMapping("/perfiles/{donanteId}")
    public ResponseEntity<String> eliminarPerfil(@PathVariable Long donanteId) {
        boolean eliminado = servicio.eliminarPerfil(donanteId);
        if (!eliminado) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Perfil no encontrado");
        }
        return ResponseEntity.ok("Perfil eliminado correctamente");
    }

    @PostMapping("/entregasConfirmadas")
    public ResponseEntity<Integer> recibirEntregaConfirmada(@RequestBody MensajeConfirmacionIncentivosDTO mensaje) {
        try {
            // Procesamos la lógica en el servicio
            servicio.procesarConfirmacionIncentivos(mensaje);
            
            // Retornamos HTTP 200 OK con el entero 200 en el body exigido por el RestClient
            return ResponseEntity.ok(200);
            
        } catch (Exception e) {
            // Si algo falla, responde 500 para que el Broker lance su respectiva HTTP Error Exception
            return ResponseEntity.internalServerError().body(500);
        }
    }


}

