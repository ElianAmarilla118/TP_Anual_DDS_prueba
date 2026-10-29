package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones;

import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil.PerfilDonante;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones.Insignia;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(
    name = "asignacion_insignia",
    uniqueConstraints = @UniqueConstraint(
        columnNames = {"donante_id", "insignia_id"}
    )
)
public class AsignacionInsignia {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "donante_id", nullable = false)
  private PerfilDonante perfil;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "insignia_id", nullable = false)
  private Insignia insignia;

  @Column(name = "fecha_obtencion", nullable = false)
  private LocalDate fechaObtencion;

  @Column(name = "es_visible", nullable = false)
  private boolean esVisible;

  //dos donantes pueden tener la misma insignia y elegir distinta visibilidad.
  // Nombre, descripción e imagen son compartidos; la visibilidad y fecha de obtencion pertenecen
  // a cada asignacion, utilizamos asignacion insignia

  protected AsignacionInsignia() {}

  public AsignacionInsignia(
      PerfilDonante perfil,
      Insignia insignia,
      LocalDate fechaObtencion
  ) {
    this.perfil = Objects.requireNonNull(perfil);
    this.insignia = Objects.requireNonNull(insignia);
    this.fechaObtencion = Objects.requireNonNull(fechaObtencion);
    this.esVisible = false;
  }

  public Insignia getInsignia() {
    return insignia;
  }

  public boolean isEsVisible() {
    return esVisible;
  }

  public void toggleVisibilidad() {
    esVisible = !esVisible;
  }
}