package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones;

import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil.DonacionImportada;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import jakarta.persistence.*;

@Getter
@Setter
@Entity
@DiscriminatorValue("BIENES")
public class MisionHabilDonador extends Mision {

    @Column(name = "cantidad_bienes_requerida")
    private double cantidadBienesRequerida;

    @Override
    public double calcularProgreso(List<DonacionImportada> historialMision) {
        if (historialMision == null || historialMision.isEmpty()) {
            return 0;
        }
        double contadorBienes = 0;

        for (DonacionImportada donacionInstancia : historialMision) {
            if (contadorBienes >= cantidadBienesRequerida) {
                break;
            }
            contadorBienes += donacionInstancia.getCantidadDonada();
        }
        return ((contadorBienes / cantidadBienesRequerida)*100);
    }
}
