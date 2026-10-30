// Ubicación: servicio_incentivos/src/main/java/ar/edu/utn.ba/dsi/g10/servicio_incentivos/Configuration/DataInitializer.java
package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Configuration;

import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.CategoriasDonante.CategoriaDonante;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones.MisionDonacionesExitosas;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil.PerfilDonante;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Repository.RepositorioPerfiles;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Misiones.CatalogoMisiones;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RepositorioPerfiles repositorioPerfiles;
    private final CatalogoMisiones repositorioMisiones;

    @Override
    @Transactional
    public void run(String... args) {

        if (repositorioPerfiles.count() == 0) {

            CategoriaDonante catInicial = new CategoriaDonante();
            catInicial.initCategoria();

            // Crear la misión
            MisionDonacionesExitosas misionInicial =
                new MisionDonacionesExitosas();

            misionInicial.setNombre("Donador Frecuente");
            misionInicial.setDescripcion(
                "Realizar donaciones exitosas"
            );
            misionInicial.setDonacionesExitosasRequeridas(5.0);

            //Persistir primero la misión
            MisionDonacionesExitosas misionGuardada =
                (MisionDonacionesExitosas)
                    repositorioMisiones.save(misionInicial);

            // Crear el perfil con la mision persistida
            PerfilDonante donanteTest = new PerfilDonante(
                1L,
                catInicial,
                misionGuardada
            );

            // Persistir el perfil
            repositorioPerfiles.save(donanteTest);

            System.out.println(
                "Ecosistema DonaTrack inicializado correctamente."
            );
        }
    }
}