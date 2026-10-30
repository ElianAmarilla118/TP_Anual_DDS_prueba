// Ubicación: servicio_incentivos/src/main/java/ar/edu/utn.ba/dsi/g10/servicio_incentivos/Configuration/DataInitializer.java
package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Configuration;

import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.CategoriasDonante.CategoriaDonante;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones.MisionDonacionesExitosas;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil.PerfilDonante;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Repository.RepositorioPerfiles;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RepositorioPerfiles repositorioPerfiles;

    @Override
    public void run(String... args) throws Exception {
        // Inicializamos perfiles base de test en PostgreSQL si la base está vacía
        if (repositorioPerfiles.count() == 0) {
            CategoriaDonante catInicial = new CategoriaDonante();
            catInicial.initCategoria();

            MisionDonacionesExitosas misionInicial = new MisionDonacionesExitosas();
            misionInicial.setNombre("Donador Frecuente");
            misionInicial.setDescripcion("Realizar donaciones exitosas");
            misionInicial.setDonacionesExitosasRequeridas(5.0);

            PerfilDonante donanteTest = new PerfilDonante(1L, catInicial, misionInicial);
            repositorioPerfiles.save(donanteTest);
            
            System.out.println("🚀 Ecosistema relacional DonaTrack inicializado con perfiles base.");
        }
    }
}
