package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional 
public class CatalogoMisiones {

    private final MisionRepository misiones;

    public CatalogoMisiones(MisionRepository misiones) {
        this.misiones = misiones;
    }

    /**
     * Registra y persiste una misión nueva (y su insignia si tiene) en PostgreSQL
     * para que pueda ser asignada a cualquier perfil sin errores de Hibernate.
     */
    public Mision registrarNuevaMision(Mision nuevaMision) {
         // 1. Si ya viene con un ID válido existente en BD, la reutilizamos directamente
    if (nuevaMision.getId() != 0) {
        var existentePorId = misiones.findById(nuevaMision.getId());
        if (existentePorId.isPresent()) {
            return existentePorId.get();
        }
        nuevaMision.setId(0L); // Si el ID no existía en BD, lo reseteamos para que Postgres genere uno
    }

    // 2. Evitar duplicados por clave de negocio (Nombre + Categoría)
    if (nuevaMision.getNombre() != null) {
        var existentePorNombre = misiones.findByNombreIgnoreCaseAndCategoria(
                nuevaMision.getNombre().trim(),
                nuevaMision.getCategoriaEnum()
        );
        if (existentePorNombre.isPresent()) {
            return existentePorNombre.get(); // Reutiliza la misión ya guardada en PostgreSQL
        }
    }

    // 3. Si es realmente nueva, calculamos el siguiente orden disponible y la persistimos
    if (nuevaMision.getOrden() == 0) {
        int siguienteOrden = misiones.findTopByCategoriaOrderByOrdenDesc(nuevaMision.getCategoriaEnum())
                .map(m -> m.getOrden() + 1)
                .orElse(1);
        nuevaMision.setOrden(siguienteOrden);
    }

    return misiones.save(nuevaMision);
    }

    /**
     * Busca la misión inicial en PostgreSQL; si aún no existe, la crea y la persiste.
     * Evita duplicados cada vez que se crea un nuevo perfil de donante.
     */
    public Mision obtenerPrimeraMision() {
        return misiones.findByNombre("Diversidad solidaria")
                .orElseGet(() -> {
                    MisionCompletitud mision = new MisionCompletitud();
                    // NO usar setId(1L): dejamos que PostgreSQL asigne el ID real
                    mision.setNombre("Diversidad solidaria");
                    mision.setDescripcion("Realizar donaciones de 2 categorías distintas");
                    mision.setCategoriasDistintasRequeridas(2.0);
                    mision.setOrden(1);

                    Insignia insignia = new Insignia();
                    // NO usar setID(1L)
                    insignia.setNombre("Primeros aportes");
                    insignia.setDescripcion("Otorgada por donar bienes de 2 categorías distintas");

                    mision.setInsignia(insignia);

                    return misiones.save(mision);
                });
    }

    public MisionDonacionesExitosas crearMisionDonacionesExitosasInicial() {
        return (MisionDonacionesExitosas) misiones.findByNombre("Donador Frecuente")
                .orElseGet(() -> {
                    MisionDonacionesExitosas mision = new MisionDonacionesExitosas();
                    mision.setNombre("Donador Frecuente");
                    mision.setDescripcion("Realizar donaciones exitosas");
                    mision.setDonacionesExitosasRequeridas(5.0);
                    mision.setOrden(2);

                    return misiones.save(mision);
                });
    }
}