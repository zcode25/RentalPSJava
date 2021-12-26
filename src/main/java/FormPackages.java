import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FormPackages {
    private JTextField txtIdPackages;
    private JTextField txtNamePackages;
    private JTextField txtDurasi;
    private JTextField txtHarga;
    private JButton saveButton;
    private JButton updateButton;
    private JButton deleteButton;
    public JPanel FormPackages;
    private JTable table1;
    private JScrollPane table_1;

    public static void main(String[] args) {
        JFrame frame = new JFrame("FormPackages");
        frame.setContentPane(new FormPackages().FormPackages);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public FormPackages() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("org.hibernate.tutorial.jpa");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Packages packages = new Packages();

        table1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                int row = table1.getSelectedRow();
                if (row != -1) {
                    txtIdPackages.setText(table1.getValueAt(row, 0).toString());
                    txtNamePackages.setText(table1.getValueAt(row, 1).toString());
                    txtDurasi.setText(table1.getValueAt(row, 2).toString());
                    txtHarga.setText(table1.getValueAt(row, 3).toString());
                }
            }
        });

        isiTable();

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String idPackages = txtIdPackages.getText();
                    String namePackages = txtNamePackages.getText();
                    String durasi = txtDurasi.getText();
                    String harga = txtHarga.getText();

                    if (cekText()) {
                        packages.setId_packages(idPackages);
                        packages.setName_packages(namePackages);
                        packages.setDurasi(Integer.parseInt(durasi));
                        packages.setHarga(Integer.parseInt(harga));
                        entityManager.getTransaction().begin();
                        entityManager.persist(packages);
                        entityManager.getTransaction().commit();
                        JOptionPane.showMessageDialog(null, "Data Package Berhasil Ditambahkan");
                    } else {
                        JOptionPane.showMessageDialog(null, "Data harus diisi semua");
                    }
                    isiTable();
                    txtIdPackages.setText("");
                    txtNamePackages.setText("");
                    txtDurasi.setText("");
                    txtHarga.setText("");
                } catch (RollbackException exception) {
                    entityManager.getTransaction().rollback();
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String idPackages = txtIdPackages.getText();
                    String namePackages = txtNamePackages.getText();
                    String durasi = txtDurasi.getText();
                    String harga = txtHarga.getText();

                    if (cekText()) {
                        packages.setId_packages(idPackages);
                        entityManager.detach(packages);
                        packages.setName_packages(namePackages);
                        packages.setDurasi(Integer.parseInt(durasi));
                        packages.setHarga(Integer.parseInt(harga));
                        entityManager.getTransaction().begin();
                        entityManager.merge(packages);
                        entityManager.getTransaction().commit();
                        JOptionPane.showMessageDialog(null, "Data Package Berhasil Diubah");
                    } else {
                        JOptionPane.showMessageDialog(null, "Data harus diisi semua");
                    }
                    isiTable();
                    txtIdPackages.setText("");
                    txtNamePackages.setText("");
                    txtDurasi.setText("");
                    txtHarga.setText("");
                } catch (RollbackException exception) {
                    entityManager.getTransaction().rollback();
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (cekText()) {
                        String idPackages = txtIdPackages.getText();
                        entityManager.getTransaction().begin();
                        Packages packages = entityManager.find(Packages.class, idPackages);
                        entityManager.remove(packages);
                        entityManager.getTransaction().commit();
                        JOptionPane.showMessageDialog(null, "Data Package Berhasil Dihapus");
                    } else {
                        JOptionPane.showMessageDialog(null, "Data harus diisi semua");
                    }
                    isiTable();
                    txtIdPackages.setText("");
                    txtNamePackages.setText("");
                    txtDurasi.setText("");
                    txtHarga.setText("");
                } catch (RollbackException exception) {
                    entityManager.getTransaction().rollback();
                }
            }
        });
    }

    public List<Packages> queryForPackages() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("org.hibernate.tutorial.jpa");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Packages> packages = entityManager.createQuery("SELECT u FROM Packages u", Packages.class).getResultList();
        return packages;
    }

    public void isiTable() {
        Object data[][] = new Object[queryForPackages().size()][4];
        int x = 0;
        for (Packages m : queryForPackages()) {
            data[x][0] = m.getId_packages();
            data[x][1] = m.getName_packages();
            data[x][2] = m.getDurasi();
            data[x][3] = m.getHarga();
            x++;
        }
        String judul[] = {"Id Packages", "Name Packages", "Durasi", "Harga"};
        table1.setModel(new DefaultTableModel(data, judul));
        table_1.setViewportView(table1);
    }

    boolean cekText() {
        if (!txtIdPackages.getText().isEmpty()
                && !txtNamePackages.getText().isEmpty()
                && !txtDurasi.getText().isEmpty()
                && !txtHarga.getText().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
}
