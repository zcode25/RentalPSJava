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

public class FormPlaces {
    private JTextField txtIdPlaces;
    private JTextField txtTypePS;
    private JTextField txtMerekMonitor;
    private JTextField txtLebarMonitor;
    private JButton deleteButton;
    private JButton updateButton;
    private JButton saveButton;
    private JTable table1;
    public JPanel FormPlaces;
    private JScrollPane table_1;

    public static void main(String[] args) {
        JFrame frame = new JFrame("FormPlaces");
        frame.setContentPane(new FormPlaces().FormPlaces);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public FormPlaces() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("org.hibernate.tutorial.jpa");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Places places = new Places();

        table1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                int row = table1.getSelectedRow();
                if (row != -1) {
                    txtIdPlaces.setText(table1.getValueAt(row, 0).toString());
                    txtTypePS.setText(table1.getValueAt(row, 1).toString());
                    txtMerekMonitor.setText(table1.getValueAt(row, 2).toString());
                    txtLebarMonitor.setText(table1.getValueAt(row, 3).toString());
                }
            }
        });

        isiTable();

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String idPlaces = txtIdPlaces.getText();
                    String typePS = txtTypePS.getText();
                    String merekMonitor = txtMerekMonitor.getText();
                    String lebarMonitor = txtLebarMonitor.getText();

                    if (cekText()) {
                        places.setId_places(idPlaces);
                        places.setType_ps(typePS);
                        places.setMerek_monitor(merekMonitor);
                        places.setLebar_monitor(Integer.parseInt(lebarMonitor));
                        entityManager.getTransaction().begin();
                        entityManager.persist(places);
                        entityManager.getTransaction().commit();
                        JOptionPane.showMessageDialog(null, "Data Place Berhasil Ditambahkan");
                    } else {
                        JOptionPane.showMessageDialog(null, "Data harus diisi semua");
                    }
                    isiTable();
                    txtIdPlaces.setText("");
                    txtTypePS.setText("");
                    txtMerekMonitor.setText("");
                    txtLebarMonitor.setText("");
                } catch (RollbackException exception) {
                    entityManager.getTransaction().rollback();
                }
            }
        });


        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String idPlaces = txtIdPlaces.getText();
                    String typePS = txtTypePS.getText();
                    String merekMonitor = txtMerekMonitor.getText();
                    String lebarMonitor = txtLebarMonitor.getText();

                    if (cekText()) {
                        places.setId_places(idPlaces);
                        entityManager.detach(places);
                        places.setType_ps(typePS);
                        places.setMerek_monitor(merekMonitor);
                        places.setLebar_monitor(Integer.parseInt(lebarMonitor));
                        entityManager.getTransaction().begin();
                        entityManager.merge(places);
                        entityManager.getTransaction().commit();
                        JOptionPane.showMessageDialog(null, "Data Place Berhasil Diubah");
                    } else {
                        JOptionPane.showMessageDialog(null, "Data harus diisi semua");
                    }
                    isiTable();
                    txtIdPlaces.setText("");
                    txtTypePS.setText("");
                    txtMerekMonitor.setText("");
                    txtLebarMonitor.setText("");
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
                        String idPlaces = txtIdPlaces.getText();
                        entityManager.getTransaction().begin();
                        Places places = entityManager.find(Places.class, idPlaces);
                        entityManager.remove(places);
                        entityManager.getTransaction().commit();
                        JOptionPane.showMessageDialog(null, "Data Place Berhasil Dihapus");
                    } else {
                        JOptionPane.showMessageDialog(null, "Data harus diisi semua");
                    }
                    isiTable();
                    txtIdPlaces.setText("");
                    txtTypePS.setText("");
                    txtMerekMonitor.setText("");
                    txtLebarMonitor.setText("");
                } catch (RollbackException exception) {
                    entityManager.getTransaction().rollback();
                }
            }
        });
    }

    public List<Places> queryForPlaces() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("org.hibernate.tutorial.jpa");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Places> places = entityManager.createQuery("SELECT u FROM Places u", Places.class).getResultList();
        return places;
    }

    public void isiTable() {
        Object data[][] = new Object[queryForPlaces().size()][4];
        int x = 0;
        for (Places m : queryForPlaces()) {
            data[x][0] = m.getId_places();
            data[x][1] = m.getType_ps();
            data[x][2] = m.getMerek_monitor();
            data[x][3] = m.getLebar_monitor();
            x++;
        }
        String judul[] = {"Id Places", "Type PS", "Merek Monitor", "Lebar Monitor"};
        table1.setModel(new DefaultTableModel(data, judul));
        table_1.setViewportView(table1);
    }

    boolean cekText() {
        if (!txtIdPlaces.getText().isEmpty()
                && !txtTypePS.getText().isEmpty()
                && !txtMerekMonitor.getText().isEmpty()
                && !txtLebarMonitor.getText().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
}
