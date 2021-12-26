import javax.persistence.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class FormTransactions {

    private JTextField txtIdTransactions;
    private JTextField txtIdPackages;
    private JTextField txtIdPlaces;
    private JButton saveButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JTable table1;
    public JPanel FormTransactions;
    private JScrollPane table_1;
    private JTextField txtIdMembers;

    public static void main(String[] args) {
        JFrame frame = new JFrame("FormTransactions");
        frame.setContentPane(new FormTransactions().FormTransactions);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public FormTransactions() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("org.hibernate.tutorial.jpa");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Transactions transactions = new Transactions();

        table1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                int row = table1.getSelectedRow();
                if (row != -1) {
                    txtIdTransactions.setText(table1.getValueAt(row, 0).toString());
                    txtIdMembers.setText(table1.getValueAt(row, 1).toString());
                    txtIdPackages.setText(table1.getValueAt(row, 2).toString());
                    txtIdPlaces.setText(table1.getValueAt(row, 3).toString());

                }
            }
        });

        isiTable();

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String idTransaction = txtIdTransactions.getText();
                    String idMembers = txtIdMembers.getText();
                    String idPackages = txtIdPackages.getText();
                    String idPlaces = txtIdPlaces.getText();
                    LocalDate date = LocalDate.now();
                    String tanggal = date.toString();
                    LocalTime time = LocalTime.now();
                    String jam = time.toString();

                    Query query = entityManager.createQuery( "Select u from Packages u where u.id = ?1" );
                    query.setParameter(1, idPackages);
                    List<Packages> list = query.getResultList( );

                    String durasi = null;
                    for( Packages m:list ) {
                        durasi = String.valueOf(m.getDurasi());
                    }

                    String jam2 = LocalTime.parse(jam).plusHours(Long.parseLong(durasi)).toString();

                    if (cekText()) {
                        transactions.setId_transactions(idTransaction);
                        transactions.setId_members(idMembers);
                        transactions.setId_packages(idPackages);
                        transactions.setId_places(idPlaces);
                        transactions.setTanggal(tanggal);
                        transactions.setMulai(jam);
                        transactions.setSelesai(jam2);

                        entityManager.getTransaction().begin();
                        entityManager.persist(transactions);
                        entityManager.getTransaction().commit();
                        JOptionPane.showMessageDialog(null, "Data Transactions Berhasil Ditambahkan");
                    } else {
                        JOptionPane.showMessageDialog(null, "Data harus diisi semua");
                    }
                    isiTable();
                    txtIdTransactions.setText("");
                    txtIdMembers.setText("");
                    txtIdPackages.setText("");
                    txtIdPlaces.setText("");
                } catch (RollbackException exception) {
                    entityManager.getTransaction().rollback();
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String idTransaction = txtIdTransactions.getText();
                    String idMembers = txtIdMembers.getText();
                    String idPackages = txtIdPackages.getText();
                    String idPlaces = txtIdPlaces.getText();
                    LocalDate date = LocalDate.now();
                    String tanggal = date.toString();
                    LocalTime time = LocalTime.now();
                    String jam = time.toString();

                    Query query = entityManager.createQuery( "Select u from Packages u where u.id = ?1" );
                    query.setParameter(1, idPackages);
                    List<Packages> list = query.getResultList( );

                    String durasi = null;
                    for( Packages m:list ) {
                        durasi = String.valueOf(m.getDurasi());
                    }

                    String jam2 = LocalTime.parse(jam).plusHours(Long.parseLong(durasi)).toString();

                    if (cekText()) {
                        transactions.setId_transactions(idTransaction);
                        entityManager.detach(transactions);
                        transactions.setId_members(idMembers);
                        transactions.setId_packages(idPackages);
                        transactions.setId_places(idPlaces);
                        transactions.setTanggal(tanggal);
                        transactions.setMulai(jam);
                        transactions.setSelesai(jam2);
                        entityManager.getTransaction().begin();
                        entityManager.merge(transactions);
                        entityManager.getTransaction().commit();
                        JOptionPane.showMessageDialog(null, "Data Transactions Berhasil Diubah");
                    } else {
                        JOptionPane.showMessageDialog(null, "Data harus diisi semua");
                    }
                    isiTable();
                    txtIdTransactions.setText("");
                    txtIdMembers.setText("");
                    txtIdPackages.setText("");
                    txtIdPlaces.setText("");
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
                        String idTransaction = txtIdTransactions.getText();
                        entityManager.getTransaction().begin();
                        Transactions transactions = entityManager.find(Transactions.class, idTransaction);
                        entityManager.remove(transactions);
                        entityManager.getTransaction().commit();
                        JOptionPane.showMessageDialog(null, "Data Transactions Berhasil Dihapus");
                    } else {
                        JOptionPane.showMessageDialog(null, "Data harus diisi semua");
                    }
                    isiTable();
                    txtIdTransactions.setText("");
                    txtIdMembers.setText("");
                    txtIdPackages.setText("");
                    txtIdPlaces.setText("");
                } catch (RollbackException exception) {
                    entityManager.getTransaction().rollback();
                }
            }
        });
    }

    public List<Transactions> queryForTransactions() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("org.hibernate.tutorial.jpa");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Transactions> transactions = entityManager.createQuery("SELECT u FROM Transactions u ORDER BY u.id DESC", Transactions.class).getResultList();
        return transactions;
    }

    public void isiTable() {
        Object data[][] = new Object[queryForTransactions().size()][7];
        int x = 0;
        for (Transactions m : queryForTransactions()) {
            data[x][0] = m.getId_transactions();
            data[x][1] = m.getId_members();
            data[x][2] = m.getId_packages();
            data[x][3] = m.getId_places();
            data[x][4] = m.getTanggal();
            data[x][5] = m.getMulai();
            data[x][6] = m.getSelesai();
            x++;
        }
        String judul[] = {"Id Transactions", "Id Members", "Id Packages", "Id Places", "Tanggal", "Mulai", "Selesai"};
        table1.setModel(new DefaultTableModel(data, judul));
        table_1.setViewportView(table1);
    }

    boolean cekText() {
        if (!txtIdTransactions.getText().isEmpty()
                && !txtIdMembers.getText().isEmpty()
                && !txtIdPackages.getText().isEmpty()
                && !txtIdPlaces.getText().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
}
