// Ubicación: servicio_incentivos/src/main/java/ar/edu/utn.ba/dsi/g10/servicio_incentivos/Repository/RepositorioPerfiles.java
package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Repository;

import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil.PerfilDonante;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Ranking.PosicionRanking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepositorioPerfiles extends JpaRepository<PerfilDonante, Long> {

    @Transactional
    default PerfilDonante getxId(long donanteId) {
        return findById(donanteId).orElse(null);
    }


    default Optional<PerfilDonante> buscarPorId(long donanteId) {
        return findById(donanteId);
    }

    @Transactional
    default void guardar(PerfilDonante perfil) {
        if (perfil != null) {
            save(perfil);
        }
    }

    default void reiniciarRankingMensual() {
    }

    @Transactional
    default boolean eliminar(long donanteId) {
        if (existsById(donanteId)) {
            deleteById(donanteId);
            return true;
        }
        return false;
    }

    /**
     * Obtiene el ranking mensual de donantes basado en la cantidad de misiones completadas.
     * En lugar de acumular en listas en memoria, calcula en tiempo real contando las 
     * misiones completas registradas en la base de datos PostgreSQL de Render.
     */
    @Query("SELECT new ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Ranking.PosicionRanking(" +
           "p.donanteID, CAST(COUNT(hm) AS int)) " +
           "FROM PerfilDonante p JOIN p.historialMisiones hm " +
           "WHERE hm.completada = true " +
           "GROUP BY p.donanteID " +
           "ORDER BY COUNT(hm) DESC")
    List<PosicionRanking> obtenerRankingMensualNativo();
}
