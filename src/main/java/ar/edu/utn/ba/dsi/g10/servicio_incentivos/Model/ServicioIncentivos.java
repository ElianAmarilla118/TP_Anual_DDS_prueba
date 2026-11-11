package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.DTO.Integracion.DonacionDTO;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.DTO.Integracion.DonacionMapper;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.DTO.Integracion.DonanteDTO;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.DTO.Integracion.MensajeConfirmacionIncentivosDTO;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones.CatalogoMisiones;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;



import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil.DatosDonante;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones.Insignia;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones.Mision;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil.DonacionImportada;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil.PerfilDonante;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil.ProgresoMision;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Ranking.PosicionRanking;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Repository.RepositorioPerfiles;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.CategoriasDonante.CategoriaDonante;
/*<<<<<<< HEAD
=======
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
>>>>>>> d1de2abbcb740c34e73ef770a5a6216c902a9c8d
*/



@Service
@Transactional
public class ServicioIncentivos {
    private final RepositorioPerfiles repo;
    private final CatalogoMisiones catalogoMisiones;
    private final DonacionMapper donacionMapper; // 1. Inyección del Mapper

   
    private final RestClient restClientMensajes;


    public ServicioIncentivos(RepositorioPerfiles repo,
                              CatalogoMisiones catalogoMisiones,
                              DonacionMapper donacionMapper,
                               @Qualifier("restClientMensajes") RestClient restClientMensajes) { // 2. Constructor actualizado
        this.repo = repo;
        this.catalogoMisiones = catalogoMisiones;
        this.donacionMapper = donacionMapper;
        this.restClientMensajes = restClientMensajes;
    }

    // 3. Nuevo método para ser llamado desde IncentivosController recibiendo el DTO
    public void procesarNuevaDonacionDTO(DonacionDTO donacionDTO) {
        if (donacionDTO == null) {
            return;
        }
        DonacionImportada donacion = donacionMapper.aDonacionImportada(donacionDTO);
        this.procesarNuevaDonacion(donacionDTO.getDonante() != null ? donacionDTO.getDonante().getId() : 0L, donacion);
    }

    public void procesarNuevaDonacionDTO(long donanteId, DonacionDTO donacionDTO) {
        if (donacionDTO == null) {
            return;
        }
        DonacionImportada donacion = donacionMapper.aDonacionImportada(donacionDTO);
        this.procesarNuevaDonacion(donanteId, donacion);
    }

    //

    public void procesarNuevaDonacion(long donanteID, DonacionImportada donacion) {
        PerfilDonante perfilAsociado = obtenerPerfil(donanteID);
        if (perfilAsociado == null || donacion == null) {
            return;
        }
         
        //Esto esta mal, revisar. #6
        perfilAsociado.sumarDonacion();
        // Evalúa racha mensual del donante
        perfilAsociado.verificarRachaDonaciones(donacion.getFechaDonacion());

        // Actualiza el progreso de la misión actual con la nueva donación
        ProgresoMision progresoAsociado = perfilAsociado.getProgreso();
        if (progresoAsociado != null) {
            progresoAsociado.actualizar(donacion);
        }

        // Otorga insignia si completó la misión
        verificarYCompletarMision(perfilAsociado);

        // Promueve de categoría si alcanzó el 100% de progreso
        verificarSubidaCategoria(perfilAsociado);

    }

    public void procesarDonacionEntregada(long donanteID, DonacionImportada donacion) {
        //#2: Viene del broker.
        // Ahora mismo, los progresos de las misiones se verifican con las donaciones obtenidas del servicio de donaciones, no con las entregadas. Revisar logica de negocio en general del tp. TODO: #1. 

        PerfilDonante perfilAsociado = obtenerPerfil(donanteID);
        if (perfilAsociado == null || donacion == null) {  
            return;
        }
        else {
            //ProgresoMision(o no)... Cargar datos.. etc. 
        }
    }

    @Transactional(readOnly = true)
    public Mision getMisionActual(long donanteID) {
        PerfilDonante perfil = obtenerPerfil(donanteID);
        return perfil != null ? perfil.getMisionActual() : null;
    }

    @Transactional(readOnly = true)
    public List<Insignia> getInsignias(long donanteId) {
        PerfilDonante perfil = obtenerPerfil(donanteId);
        return perfil != null ? perfil.getInsignias() : null;
    }

    @Transactional(readOnly = true)
    public PerfilDonante getMetricas(long donanteID) {
        return obtenerPerfil(donanteID);
    }

    public void enviarInsigniaAN8N(PerfilDonante perfil) {

    try {

        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> body = new HashMap<>();

        Insignia ultimaInsignia =
                perfil.getInsignias().get(perfil.getInsignias().size() - 1);

        body.put("user", perfil.getDonanteID());
        body.put("badge", ultimaInsignia.getNombre());
        body.put("description", ultimaInsignia.getDescripcion());

        restTemplate.postForEntity(
                "http://localhost:5678/webhook/Insignias",
                body,
                String.class
        );

    } catch (Exception e) {
        System.out.println("Error enviando evento a n8n: " + e.getMessage());
    }
}

    private void verificarYCompletarMision(
        PerfilDonante perfilDonante)
    {
        if (perfilDonante == null) {
            return;
        }

        boolean seOtorgoInsignia =
            perfilDonante.otorgarInsigniasPendientes();

        if (!seOtorgoInsignia) {
            return;
        }

       // repo.actualizarRankingPerfil(perfilDonante);
        this.guardarPerfil(perfilDonante);
        

        restClientMensajes
                .post()
                .uri("/mensajes")
                .body(Map.of(
                    "tipo", "MISION_COMPLETADA",
                    "titulo", "Notificacion de mision",
                    "mensaje", "Mision Completada",
                    "destinatario", perfilDonante.getNombre()))
                .retrieve()
                .toBodilessEntity();
    
            


        enviarInsigniaAN8N(perfilDonante);
    }

    @Transactional(readOnly = true)
    public List<PosicionRanking> getRanking(int tamaño) {
           return repo.obtenerRankingMensualNativo().stream()
            .limit(tamaño)
            .toList();
    }


    private void verificarSubidaCategoria(PerfilDonante perfilDonante) {
        if (perfilDonante != null && perfilDonante.getPorcentajeProgreso() >= 100.0) {
            perfilDonante.subirCategoria();// Eleva la categoría[cite: 12, 22]
                restClientMensajes
                .post()
                .uri("/mensajes")
                .body(Map.of(
                    "tipo", "CATEGORIA_SUBIDA",
                    "titulo", "Notificacion de categoria",
                    "mensaje", "Subida de categoria",
                    "destinatario", perfilDonante.getNombre()))
                .retrieve()
                .toBodilessEntity();
    
        }
    }



    private PerfilDonante obtenerPerfil(long donanteID) {
        return repo != null ? repo.getxId(donanteID) : null;
    }



    public void asignarNuevaMision(long donanteID, Mision nuevaMision) {
        PerfilDonante perfil = obtenerPerfil(donanteID);
        if (perfil != null && nuevaMision != null) {
            perfil.setMisionActual(nuevaMision);

            ProgresoMision nuevoProgreso = new ProgresoMision(perfil, nuevaMision, java.time.LocalDate.now());
            nuevoProgreso.setProgresoActual(0.0);
            perfil.getHistorialMisiones().add(nuevoProgreso);
        }
    }


    //revisar cron
    @Transactional
    @Scheduled(cron = "0 59 23 L * ?")
    public void reiniciarRanking() { // CAMBIA EL CORCHETE/LLAVE POR ESTA LLAVE DE APERTURA
    repo.reiniciarRankingMensual();
    System.out.println("Se ha reinventado el ranking mensual exitosamente.");   
    }

    //reiniciarRanking() manual


    public void guardarPerfil(PerfilDonante perfil) {
        repo.guardar(perfil);
    }


    public boolean eliminarPerfil(long donanteId) {
        return repo.eliminar(donanteId);
    }


    @Transactional
    public PerfilDonante crearPerfil(DonanteDTO donanteDTO) {
        if (donanteDTO == null || donanteDTO.getId() == null) {
            return null;
        }
        CategoriaDonante categoriaInicial = new CategoriaDonante();
        DatosDonante datosDonante = new DatosDonante(donanteDTO.getNombre(), donanteDTO.getDonaciones(), donanteDTO.getContactoPredeterminado());
        Mision misionInicial = catalogoMisiones.obtenerPrimeraMision();

        PerfilDonante nuevoPerfil = new PerfilDonante(
            donanteDTO.getId(),
            datosDonante,
            categoriaInicial,
            misionInicial
        );

        repo.guardar(nuevoPerfil);
        return nuevoPerfil;
    }

    public PerfilDonante crearPerfil(long donanteId) {
        CategoriaDonante categoriaInicial = new CategoriaDonante();
        Mision misionInicial = catalogoMisiones.obtenerPrimeraMision();
        PerfilDonante nuevoPerfil = new PerfilDonante(donanteId, categoriaInicial, misionInicial);
        repo.guardar(nuevoPerfil);
        return nuevoPerfil;
    }


    @Transactional
    public PerfilDonante crearPerfilDesdeDTO(DonanteDTO donanteDTO) {
        if (donanteDTO == null || donanteDTO.getId() == null) {
            return null;
        }

        Long donanteId = donanteDTO.getId();
        
        DatosDonante datosDonante = new DatosDonante(
            donanteDTO.getNombre(),
            donanteDTO.getDonaciones(),
            donanteDTO.getContactoPredeterminado()
        );

        // 1. Crear el perfil inicial con su categoría y primera misión
        PerfilDonante nuevoPerfil = crearPerfil(donanteId);
        nuevoPerfil.setDatosDonante(datosDonante);
        nuevoPerfil.cargarDatos();

        // 2. Si el DTO de donaciones trae historial previo, lo procesamos
        if (donanteDTO.getDonaciones() != null && !donanteDTO.getDonaciones().isEmpty()) {
            for (DonacionDTO donacionDTO : donanteDTO.getDonaciones()) {
                procesarNuevaDonacionDTO(donanteId, donacionDTO);
            }
        }

        return nuevoPerfil;
    }


public void procesarConfirmacionIncentivos(ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.DTO.Integracion.MensajeConfirmacionIncentivosDTO dto) {
   if (dto == null || dto.getIdAsignada() == null) {
        return;
    }

    // 1. Buscamos el perfil dueño de la donación en PostgreSQL (Opción A)
    PerfilDonante usuario = repo.buscarPorDonacionId(dto.getIdAsignada()).orElse(null);
    if (usuario == null) {
        return;
    }

    // 2. Procesamos la confirmación en el dominio (busca en misión actual o anteriores)
    usuario.procesarDonacionConfirmada(dto);

    // 3. Verificamos si completó la misión (especialmente útil para MisionDonacionesExitosas)
    verificarYCompletarMision(usuario);
    verificarSubidaCategoria(usuario);

    // 4. Persistimos los cambios en PostgreSQL
    repo.guardar(usuario);
}



}
