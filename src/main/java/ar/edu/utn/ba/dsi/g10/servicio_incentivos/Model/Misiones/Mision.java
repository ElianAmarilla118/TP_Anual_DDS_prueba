package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones;

import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.CategoriasDonante.Categoria;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil.DonacionImportada;
import jakarta.persistence.*;
import java.util.List;

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Categoria categoria = Categoria.COLABORADOR;

    @Column(nullable = false)
    private int orden;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "insignia_id")
    private Insignia insignia;

    public abstract double calcularProgreso(List<DonacionImportada> historialProgreso);

    public String getObjetivo() {
        return descripcion;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Categoria getCategoriaEnum() {
        return categoria;
    }

    public String getCategoria() {
        return categoria != null ? categoria.getNombre() : Categoria.COLABORADOR.getNombre();
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public Insignia getInsignia() {
        return insignia;
    }

    public void setInsignia(Insignia insignia) {
        this.insignia = insignia;
    }
}

