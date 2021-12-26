import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "packages")
public class Packages {
    @Id
    @Column(name = "id_packages")
    private String id_packages;
    @Column(name = "name_packages")
    private String name_packages;
    @Column(name = "durasi")
    private int durasi;
    @Column(name = "harga")
    private int harga;

    public String getId_packages() {
        return id_packages;
    }

    public void setId_packages(String id_packages) {
        this.id_packages = id_packages;
    }

    public String getName_packages() {
        return name_packages;
    }

    public void setName_packages(String name_packages) {
        this.name_packages = name_packages;
    }

    public int getDurasi() {
        return durasi;
    }

    public void setDurasi(int durasi) {
        this.durasi = durasi;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    @Override
    public String toString() {
        return "Packages{" +
                "id_packages='" + id_packages + '\'' +
                ", name_packages='" + name_packages + '\'' +
                ", durasi=" + durasi +
                ", harga=" + harga +
                '}';
    }
}
