package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.DTO;

import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.DTO.Integracion.DonacionDTO;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil.PerfilDonante;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones.Mision;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones.Insignia;
import java.util.Date;
import java.util.List;


public class Mapper {
    // parametros donante
    private long Id;
    private String categoriaActual;
    private int cantidadInsignias;
    private int misionesCompletadas;
    

    //parametro compratido
    private String nombre; 

    //parametros mision e insignia
    private String descripcion;
    private String categoria;
    private String insignia;  

    //parametros donacionDTO
    private long donanteId;
    private long donacionId;
    private List<Long> segmentadasIncluidas;
    private Date fechaEntrada = new Date();
    private List<String> categoriasIncluidas;
    private int cantidadDonada;




    public Mapper() {
        // Constructor vacío
    }
    //mapper de metricas
    public Mapper(PerfilDonante perfil) {
        this.Id = perfil.getID();
        this.nombre = perfil.getNombre();
        this.categoriaActual = perfil.getCategoria();
        this.cantidadInsignias = perfil.getInsignias().size();
        this.misionesCompletadas = perfil.getMisionesCompletadas();
    }


    //mapper de misiones 
    public Mapper(Mision mision) {
        this.Id = mision.getId();
        this.nombre = mision.getNombre();
        this.descripcion = mision.getDescripcion();
        this.categoria = mision.getCategoria();
        this.insignia = mision.getInsignia().getNombre();
        
    }
    
    public Mapper transformarInsignia(Insignia insignia) {
        this.Id = insignia.getID();
        this.nombre = insignia.getNombre();
        this.descripcion = insignia.getDescripcion();
        //por ahora no vemos el url 
        return this;
    }

    public Mapper transformarDonacionDTO(DonacionDTO donacionDTO) {
        if (donacionDTO == null) {
            return this;
        }
        this.donacionId = donacionDTO.getId() != null ? donacionDTO.getId() : 0L;
        this.donanteId = donacionDTO.getDonante() != null ? donacionDTO.getDonante().getId() : 0L;
        this.categoria = donacionDTO.getCategoriasIncluidas() != null ? String.join(",", donacionDTO.getCategoriasIncluidas()) : null;
        this.cantidadDonada = donacionDTO.getCantBienes() != null ? donacionDTO.getCantBienes() : 0;
        this.segmentadasIncluidas = donacionDTO.getSegmentadasIncluidas();
        this.fechaEntrada = donacionDTO.getFechaEntrada();
        return this;
    }



    public int getId() {
        return (int) Id;
    }
    public String getCategoriaActual() {
        return categoriaActual;
    }
    public int getCantidadInsignias() {
        return cantidadInsignias;
    }
    public int getMisionesCompletadas() {
        return misionesCompletadas;
    }
    public String getNombre() {
        return nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public String getCategoria() {
        return categoria;
    }
    public String getInsignia() {
        return insignia;
    }
}


