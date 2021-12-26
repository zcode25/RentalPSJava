import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "transactions")
public class Transactions {
    @Id
    @Column(name = "id_transactions")
    private String id_transactions;
    @Column(name = "id_members")
    private String id_members;
    @Column(name = "id_packages")
    private String id_packages;
    @Column(name = "id_places")
    private String id_places;
    @Column(name = "tanggal")
    private String tanggal;
    @Column(name = "mulai")
    private String mulai;
    @Column(name = "selesai")
    private String selesai;

    public String getId_transactions() {
        return id_transactions;
    }

    public void setId_transactions(String id_transactions) {
        this.id_transactions = id_transactions;
    }

    public String getId_members() {
        return id_members;
    }

    public void setId_members(String id_members) {
        this.id_members = id_members;
    }

    public String getId_packages() {
        return id_packages;
    }

    public void setId_packages(String id_packages) {
        this.id_packages = id_packages;
    }

    public String getId_places() {
        return id_places;
    }

    public void setId_places(String id_places) {
        this.id_places = id_places;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getMulai() {
        return mulai;
    }

    public void setMulai(String mulai) {
        this.mulai = mulai;
    }

    public String getSelesai() {
        return selesai;
    }

    public void setSelesai(String selesai) {
        this.selesai = selesai;
    }

    @Override
    public String toString() {
        return "Transactions{" +
                "id_transactions='" + id_transactions + '\'' +
                ", id_members='" + id_members + '\'' +
                ", id_packages='" + id_packages + '\'' +
                ", id_places='" + id_places + '\'' +
                ", tanggal='" + tanggal + '\'' +
                ", mulai='" + mulai + '\'' +
                ", selesai='" + selesai + '\'' +
                '}';
    }
}
