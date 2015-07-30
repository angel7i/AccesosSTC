package tables;
// Generated Jul 17, 2015 1:56:51 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Estacion generated by hbm2java
 */
@Entity
@Table(name="estacion"
    ,catalog="accesos"
)
public class Estacion  implements java.io.Serializable {


     private int idEstacion;
     private Linea linea;
     private String nombre;
     private Set<Bateria> baterias = new HashSet(0);

    public Estacion() {
    }

	
    public Estacion(int idEstacion, Linea linea) {
        this.idEstacion = idEstacion;
        this.linea = linea;
    }
    public Estacion(int idEstacion, Linea linea, String nombre, Set baterias) {
       this.idEstacion = idEstacion;
       this.linea = linea;
       this.nombre = nombre;
       this.baterias = baterias;
    }
   
     @Id 

    
    @Column(name="idEstacion", unique=true, nullable=false)
    public int getIdEstacion() {
        return this.idEstacion;
    }
    
    public void setIdEstacion(int idEstacion) {
        this.idEstacion = idEstacion;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="Linea_idLinea", nullable=false)
    public Linea getLinea() {
        return this.linea;
    }
    
    public void setLinea(Linea linea) {
        this.linea = linea;
    }

    
    @Column(name="nombre", length=45)
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="estacion")
    public Set<Bateria> getBaterias() {
        return this.baterias;
    }
    
    public void setBaterias(Set<Bateria> baterias) {
        this.baterias = baterias;
    }




}

