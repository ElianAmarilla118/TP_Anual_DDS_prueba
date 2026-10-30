package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Getter
@Setter

@Entity
@Table(name = "insignia")
public class Insignia {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "insignia_id")
        private long ID;

        @Column(nullable = false)
        private String nombre;

        private String descripcion;

        @Column(name = "imagen_url")
        private String imagenURL;

        //dos donantes pueden tener la misma insignia y elegir distinta visibilidad.
        // Nombre, descripción e imagen son compartidos; la visibilidad y fecha de obtencion pertenecen
        // a cada asignacion, eliminamos esvisible y toggle visibilidad
}

