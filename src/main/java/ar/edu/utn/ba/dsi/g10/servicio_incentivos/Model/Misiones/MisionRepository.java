package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones.Mision;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.CategoriasDonante.Categoria;

import java.util.Optional;
import java.util.List;

public interface MisionRepository
        extends JpaRepository<Mision, Long> {

    Optional<Mision> findFirstByCategoriaOrderByOrdenAsc(
        Categoria categoria
    );

    List<Mision> findByCategoriaOrderByOrdenAsc(
        Categoria categoria
    );
}