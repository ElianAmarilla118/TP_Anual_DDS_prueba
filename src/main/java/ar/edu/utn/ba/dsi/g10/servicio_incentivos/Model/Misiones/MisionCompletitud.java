package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones;

import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil.DonacionImportada;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;

@Getter
@Setter
@Entity
@DiscriminatorValue("COMPLETITUD")
public class MisionCompletitud extends Mision {

    @Column(name = "categorias_distintas_requeridas")
    private double categoriasDistintasRequeridas;

    @Override
    public double calcularProgreso(List<DonacionImportada> historialMision) {
        if (historialMision == null || historialMision.isEmpty()) {
            return 0;
        }
        // ahora mismo, la donacion importada tiene una lista de categorias, no una sola categoria, este metodo de calculo no sirve. RESOLVED
        
        // Aquí guardamos únicamente las categorías que son distintas
        Set<String> categoriasVistas = new HashSet<>();

        for (DonacionImportada donacionInstancia :historialMision) {
            // Condición de parada rápida: si ya encontramos 10 distintas, frenamos el bucle
            if (categoriasVistas.size() >= categoriasDistintasRequeridas) {
                break;
            }


            // hace un recorrido del vector de categorias por cada instancia. verificar funcionamiento #7. 
            List<String> categoriasInstancia = donacionInstancia.getCategoria();
            if (categoriasInstancia != null) {
                for (String categoria : categoriasInstancia) {
                    categoriasVistas.add(categoria);
                    // Condición de parada rápida: si ya encontramos 10 distintas, frenamos el bucle
                    if (categoriasVistas.size() >= categoriasDistintasRequeridas) {
                        break;
                    }
                }
            }
        }

        if (categoriasDistintasRequeridas == 0) {
            return 0;
        }

        return (((double)categoriasVistas.size() / categoriasDistintasRequeridas)*100);
    }
}
