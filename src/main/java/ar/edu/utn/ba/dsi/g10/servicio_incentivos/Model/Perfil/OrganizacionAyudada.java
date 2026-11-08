package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil;

public class OrganizacionAyudada {
    private int idOrganizacion;
    private String nombreOrganizacion;
    private List<String> idSegmentadas; 

    public OrganizacionAyudada(int idOrganizacion, String nombreOrganizacion) {
        this.idOrganizacion = idOrganizacion;
        this.nombreOrganizacion = nombreOrganizacion;
    }

    public int getIdOrganizacion() {
        return idOrganizacion;
    }

    public void setIdOrganizacion(int idOrganizacion) {
        this.idOrganizacion = idOrganizacion;
    }

    public String getNombreOrganizacion() {
        return nombreOrganizacion;
    }

    public void setNombreOrganizacion(String nombreOrganizacion) {
        this.nombreOrganizacion = nombreOrganizacion;
    }

    public List<String> getIdSegmentadas() {
        return idSegmentadas;
    }

    public void sumarIdSegmentada(String idSegmentada) {
        if (this.idSegmentadas == null) {
            this.idSegmentadas = new ArrayList<>();
        }
        this.idSegmentadas.add(idSegmentada);
    }

    public int getDonacionesSegmentadasRecibidas() {
        return idSegmentadas != null ? idSegmentadas.size() : 0;
    }
}