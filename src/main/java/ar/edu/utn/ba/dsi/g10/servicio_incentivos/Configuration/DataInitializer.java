package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Configuration;

import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.CategoriasDonante.CategoriaDonante;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones.CatalogoMisiones;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones.MisionDonacionesExitosas;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil.PerfilDonante;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Repository.RepositorioPerfiles;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

  private final RepositorioPerfiles repositorioPerfiles;
  private final CatalogoMisiones catalogoMisiones;
  private final EntityManager entityManager;

  @Override
  @Transactional
  public void run(String... args) {

    if (repositorioPerfiles.count() == 0) {

      CategoriaDonante catInicial = new CategoriaDonante();
      catInicial.initCategoria();

      // Crear la misión desde el catálogo
      MisionDonacionesExitosas misionInicial =
          catalogoMisiones.crearMisionDonacionesExitosasInicial();

      // Persistir primero la misión
      entityManager.persist(misionInicial);
      entityManager.flush();

      // Crear el perfil asociado
      PerfilDonante donanteTest =
          new PerfilDonante(
              1L,
              catInicial,
              misionInicial
          );

      // Guardar el perfil
      repositorioPerfiles.save(donanteTest);

      System.out.println(
          "Ecosistema DonaTrack inicializado correctamente."
      );
    }
  }
}