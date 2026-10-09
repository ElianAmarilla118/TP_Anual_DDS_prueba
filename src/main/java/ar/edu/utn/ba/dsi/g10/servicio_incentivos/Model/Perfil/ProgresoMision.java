package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil;

import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones.Mision;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ProgresoMision {
    private Mision misionAsociada;
    private double progresoActual;
    private List<DonacionImportada> historialDonaciones = new ArrayList<>();
    private LocalDate fechaInicio;
    private LocalDate fechaCompletado;
    private boolean completada;

    public Mision getMisionAsociada() {
        return misionAsociada;
    }

    public void setMisionAsociada(Mision misionAsociada) {
        this.misionAsociada = misionAsociada;
    }

    public double getProgresoActual() {
        return progresoActual;
    }

    public void setProgresoActual(double progresoActual) {
        this.progresoActual = progresoActual;
    }

    public List<DonacionImportada> getHistorialDonaciones() {
        return historialDonaciones;
    }

    public void setHistorialDonaciones(List<DonacionImportada> historialDonaciones) {
        this.historialDonaciones = historialDonaciones;
    }

    public void reiniciar() {
        historialDonaciones.clear();
        progresoActual = 0.0;
        completada = false;
        fechaCompletado = null;
    }

    public void actualizar(DonacionImportada donacionImportada) {
        if (completada) {
            return;
        }

        if (donacionImportada == null || misionAsociada == null) {
            return;
        }

        historialDonaciones.add(donacionImportada);

        progresoActual = misionAsociada.calcularProgreso(
            historialDonaciones
        );

        if (progresoActual >= 100.0) {
            marcarCompletada();
        }
    }

    public void marcarCompletada() {
        completada = true;
        if (fechaCompletado == null) {
            fechaCompletado = LocalDate.now();
        }
    }

    public boolean getCompletada() {
        return completada;
    }

}

