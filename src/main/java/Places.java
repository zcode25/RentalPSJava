import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "places")
public class Places {
    @Id
    @Column(name = "id_places")
    private String id_places;
    @Column(name = "type_ps")
    private String type_ps;
    @Column(name = "merek_monitor")
    private String merek_monitor;
    @Column(name = "lebar_monitor")
    private int lebar_monitor;

    public String getId_places() {
        return id_places;
    }

    public void setId_places(String id_places) {
        this.id_places = id_places;
    }

    public String getType_ps() {
        return type_ps;
    }

    public void setType_ps(String type_ps) {
        this.type_ps = type_ps;
    }

    public String getMerek_monitor() {
        return merek_monitor;
    }

    public void setMerek_monitor(String merek_monitor) {
        this.merek_monitor = merek_monitor;
    }

    public int getLebar_monitor() {
        return lebar_monitor;
    }

    public void setLebar_monitor(int lebar_monitor) {
        this.lebar_monitor = lebar_monitor;
    }

    @Override
    public String toString() {
        return "Places{" +
                "id_places='" + id_places + '\'' +
                ", type_ps='" + type_ps + '\'' +
                ", merek_monitor='" + merek_monitor + '\'' +
                ", lebar_monitor=" + lebar_monitor +
                '}';
    }
}
