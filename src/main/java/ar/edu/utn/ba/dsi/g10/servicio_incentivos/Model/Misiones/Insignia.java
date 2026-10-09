package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Insignia {
        private long ID;
        private String nombre;
        private String descripcion;
        private String imagenURL;
        private boolean esVisible;

        public long getID() {
                return ID;
        }

        public void setID(long ID) {
                this.ID = ID;
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

        public String getImagenURL() {
                return imagenURL;
        }

        public void setImagenURL(String imagenURL) {
                this.imagenURL = imagenURL;
        }

        public boolean isEsVisible() {
                return esVisible;
        }

        public void setEsVisible(boolean esVisible) {
                this.esVisible = esVisible;
        }

        public void toggleVisibilidad() {this.esVisible = !(this.esVisible);}

}

