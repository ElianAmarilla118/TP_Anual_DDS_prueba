// Ubicación: servicio_incentivos/src/main/java/ar/edu/utn.ba/dsi/g10/servicio_incentivos/Repository/RepositorioPerfiles.java
package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Repository;

import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil.PerfilDonante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepositorioPerfiles extends JpaRepository<PerfilDonante, Long> {

    // Spring Data JPA mapea automáticamente la búsqueda por ID usando findById
    // Definimos un alias por compatibilidad con tu código del Servicio core
    default PerfilDonante getxId(long donanteId) {
        return findById(donanteId).orElse(null);
    }

    // Remapeamos el método guardar usando la persistencia real de JPA
    default void guardar(PerfilDonante perfil) {
        if (perfil != null) {
            save(perfil);
        }
    }

    // Remapeamos la eliminación física compatible con tu lógica
    default boolean eliminar(long donanteId) {
        if (existsById(donanteId)) {
            deleteById(donanteId);
            return true;
        }
        return false;
    }
}
