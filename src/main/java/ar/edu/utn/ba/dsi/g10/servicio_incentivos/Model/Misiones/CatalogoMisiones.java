package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones.MisionDonacionesExitosas;

@Service
@Transactional(readOnly = true)
public class CatalogoMisiones {

  private final MisionRepository misiones;

  public CatalogoMisiones(MisionRepository misiones) {
    this.misiones = misiones;
  }

  public Mision obtenerPrimeraMision() {
    MisionCompletitud mision = new MisionCompletitud();

    //mision propiesta p inicializar
    mision.setId(1L);
    mision.setNombre("Diversidad solidaria");
    mision.setDescripcion("Realizar donaciones de 2 categorías distintas");
    mision.setCategoriasDistintasRequeridas(2.0);
    mision.setOrden(1);

    Insignia insignia = new Insignia();

    insignia.setID(1L);
    insignia.setNombre("Primeros aportes");
    insignia.setDescripcion(
        "Otorgada por donar bienes de 2 categorías distintas"
    );

    mision.setInsignia(insignia);

    return mision;
  }

  public MisionDonacionesExitosas crearMisionDonacionesExitosasInicial() {

    MisionDonacionesExitosas mision =
        new MisionDonacionesExitosas();

    mision.setNombre("Donador Frecuente");
    mision.setDescripcion("Realizar donaciones exitosas");
    mision.setDonacionesExitosasRequeridas(5.0);

    return mision;
  }

}