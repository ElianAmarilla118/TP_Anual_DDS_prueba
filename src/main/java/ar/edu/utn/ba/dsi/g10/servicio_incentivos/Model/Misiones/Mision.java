package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones;

import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.CategoriasDonante.Categoria;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil.DonacionImportada;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

import jakarta.persistence.*;

@Getter
@Setter
@Entity
@Table(
    name = "mision",
    uniqueConstraints = @UniqueConstraint(
        columnNames = {"categoria", "orden"}
    )
)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_mision")
public abstract class Mision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String nombre;


    private String descripcion;

    //una misio necesita indicar “pertenezco a COLABORADOR”
    // no necesita un objeto con operaciones para avanzar la categoria de un donante. cambio a categoria
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Categoria categoria = Categoria.COLABORADOR;

    @Column(nullable = false)
    private int orden;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "insignia_id")
    private Insignia insignia;           // minúscula inicial

    public abstract double calcularProgreso(List<DonacionImportada> historialProgreso);

    public String getObjetivo() {
        return descripcion;
    }
    public long getId() {
        return id;
    }
    public String getNombre() {
        return nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public String getCategoria() {
        return categoria != null ? categoria.getNombre() : Categoria.COLABORADOR.getNombre();
    }
    public Insignia getInsignia() {
        return insignia;
    } 
}

