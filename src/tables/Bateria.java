package tables;
// Generated Jul 17, 2015 1:56:51 PM by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Bateria generated by hbm2java
 */
@Entity
@Table(name="bateria"
    ,catalog="accesos"
)
public class Bateria  implements java.io.Serializable {


     private Integer idBateria;
     private Estacion estacion;
     private String acceso;
     private Integer entradas;
     private Date fecha;
     private Set<Torniquete> torniquetes = new HashSet(0);

    public Bateria() {
    }

	
    public Bateria(Estacion estacion) {
        this.estacion = estacion;
    }
    public Bateria(Estacion estacion, String acceso, Integer entradas, Date fecha, Set torniquetes) {
       this.estacion = estacion;
       this.acceso = acceso;
       this.entradas = entradas;
       this.fecha = fecha;
       this.torniquetes = torniquetes;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="idBateria", unique=true, nullable=false)
    public Integer getIdBateria() {
        return this.idBateria;
    }
    
    public void setIdBateria(Integer idBateria) {
        this.idBateria = idBateria;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="Estacion_idEstacion", nullable=false)
    public Estacion getEstacion() {
        return this.estacion;
    }
    
    public void setEstacion(Estacion estacion) {
        this.estacion = estacion;
    }

    
    @Column(name="acceso", length=100)
    public String getAcceso() {
        return this.acceso;
    }
    
    public void setAcceso(String acceso) {
        this.acceso = acceso;
    }

    
    @Column(name="entradas")
    public Integer getEntradas() {
        return this.entradas;
    }
    
    public void setEntradas(Integer entradas) {
        this.entradas = entradas;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="fecha", length=19)
    public Date getFecha() {
        return this.fecha;
    }
    
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="bateria")
    public Set<Torniquete> getTorniquetes() {
        return this.torniquetes;
    }
    
    public void setTorniquetes(Set<Torniquete> torniquetes) {
        this.torniquetes = torniquetes;
    }




}


