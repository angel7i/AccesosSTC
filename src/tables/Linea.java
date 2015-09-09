package tables;
// Generated Sep 2, 2015 4:24:31 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Linea generated by hbm2java
 */
@Entity
@Table(name="linea"
    ,catalog="accesos"
)
public class Linea  implements java.io.Serializable {


     private int idLinea;
     private String nombre;
     private Set<Estacion> estacions = new HashSet<Estacion>(0);

    public Linea() {
    }

	
    public Linea(int idLinea) {
        this.idLinea = idLinea;
    }
    public Linea(int idLinea, String nombre, Set<Estacion> estacions) {
       this.idLinea = idLinea;
       this.nombre = nombre;
       this.estacions = estacions;
    }
   
     @Id 

    
    @Column(name="idLinea", unique=true, nullable=false)
    public int getIdLinea() {
        return this.idLinea;
    }
    
    public void setIdLinea(int idLinea) {
        this.idLinea = idLinea;
    }

    
    @Column(name="nombre", length=45)
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="linea")
    public Set<Estacion> getEstacions() {
        return this.estacions;
    }
    
    public void setEstacions(Set<Estacion> estacions) {
        this.estacions = estacions;
    }




}


