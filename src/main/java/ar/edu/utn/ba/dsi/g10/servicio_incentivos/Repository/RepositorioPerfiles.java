// Ubicación: servicio_incentivos/src/main/java/ar/edu/utn.ba/dsi/g10/servicio_incentivos/Repository/RepositorioPerfiles.java
package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Repository;

import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil.PerfilDonante;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Ranking.PosicionRanking;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Ranking.RankingPerfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositorioPerfiles extends JpaRepository<PerfilDonante, Long> {

    RankingPerfil RANKING = new RankingPerfil();

    default PerfilDonante getxId(long donanteId) {
        return findById(donanteId).orElse(null);
    }

    default void guardar(PerfilDonante perfil) {
        if (perfil != null) {
            save(perfil);
        }
    }

    default boolean eliminar(long donanteId) {
        if (existsById(donanteId)) {
            deleteById(donanteId);
            return true;
        }
        return false;
    }

    default void actualizarRankingPerfil(PerfilDonante perfil) {
        if (perfil != null) {
            RANKING.actualizarPerfil(perfil);
        }
    }

    default List<PosicionRanking> obtenerRankingMensual(int tamaño) {
        return RANKING.getPosicionesHistoricas().stream()
            .limit(tamaño)
            .toList();
    }

    default void reiniciarRankingMensual() {
        RANKING.reiniciarRanking();
    }
}
