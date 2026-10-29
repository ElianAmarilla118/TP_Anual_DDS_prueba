package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

import jakarta.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "donacion_importada")
public class DonacionImportada {

    @Id
    @Column(name = "donacion_id")
    private long donacionId; //evitamos procesar dos veces la misma donacion

    @Column(name = "donante_id", nullable = false)
    private long donanteId; // para localizarlo luego

    @Column(name = "cantidad_donada", nullable = false)
    private int cantidadDonada;

    private String categoria; //entendiendo que procesamos una sola categoria en la donacion

    @Column(nullable = false)
    private boolean exitosa;

    @Column(name = "fecha_donacion", nullable = false)
    private LocalDate fechaDonacion;

}
