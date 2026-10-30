package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones;

import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil.DonacionImportada;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import jakarta.persistence.*;

@Getter
@Setter
@Entity
@DiscriminatorValue("EXITOSAS")
public class MisionDonacionesExitosas extends Mision {

    @Column(name = "donaciones_exitosas_requeridas")
    private double donacionesExitosasRequeridas;

    @Override
    public double calcularProgreso(List<DonacionImportada> historialMision) {
        if (historialMision == null || historialMision.isEmpty()) {
            return 0;
        }
        double contadorExitosas = 0;

        for (DonacionImportada donacionInstancia : historialMision) {
            if (contadorExitosas >= 10) {
                break;
            }
            if (donacionInstancia.isExitosa()) {
                contadorExitosas += 1;
            }
        }

        if (donacionesExitosasRequeridas == 0) {
            return 0;
        }

        return ((contadorExitosas / donacionesExitosasRequeridas)*100);
    }
}
