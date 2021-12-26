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

public class FormMembers {
    private JTextField txtId;
    private JTextField txtName;
    private JTextField txtEmail;
    private JTextField txtTel;
    public JPanel FormMembers;
    private JButton saveButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JTable table1;
    private JScrollPane table_1;

    public static void main(String[] args) {
        JFrame frame = new JFrame("FormMembers");
        frame.setContentPane(new FormMembers().FormMembers);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public FormMembers() {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("org.hibernate.tutorial.jpa");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Members members = new Members();

        table1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                int row = table1.getSelectedRow();
                if (row != -1) {
                    txtId.setText(table1.getValueAt(row, 0).toString());
                    txtName.setText(table1.getValueAt(row, 1).toString());
                    txtEmail.setText(table1.getValueAt(row, 2).toString());
                    txtTel.setText(table1.getValueAt(row, 3).toString());

                }
            }
        });

        isiTable();

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String id = txtId.getText();
                    String name = txtName.getText();
                    String email = txtEmail.getText();
                    String tel = txtTel.getText();

                    if (cekText()) {
                        members.setId(id);
                        members.setName(name);
                        members.setEmail(email);
                        members.setTel(tel);
                        entityManager.getTransaction().begin();
                        entityManager.persist(members);
                        entityManager.getTransaction().commit();
                        JOptionPane.showMessageDialog(null, "Data Member Berhasil Ditambahkan");
                    } else {
                        JOptionPane.showMessageDialog(null, "Data harus diisi semua");
                    }
                    isiTable();
                    txtId.setText("");
                    txtName.setText("");
                    txtEmail.setText("");
                    txtTel.setText("");
                } catch (RollbackException exception) {
                    entityManager.getTransaction().rollback();
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String id = txtId.getText();
                    String name = txtName.getText();
                    String email = txtEmail.getText();
                    String tel = txtTel.getText();

                    if (cekText()) {
                        members.setId(id);
                        entityManager.detach(members);
                        members.setName(name);
                        members.setEmail(email);
                        members.setTel(tel);
                        entityManager.getTransaction().begin();
                        entityManager.merge(members);
                        entityManager.getTransaction().commit();
                        JOptionPane.showMessageDialog(null, "Data Member Berhasil Diubah");
                    } else {
                        JOptionPane.showMessageDialog(null, "Data harus diisi semua");
                    }
                    isiTable();
                    txtId.setText("");
                    txtName.setText("");
                    txtEmail.setText("");
                    txtTel.setText("");
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
                        String id = txtId.getText();
                        entityManager.getTransaction().begin();
                        Members members = entityManager.find(Members.class, id);
                        entityManager.remove(members);
                        entityManager.getTransaction().commit();
                        JOptionPane.showMessageDialog(null, "Data Member Berhasil Dihapus");
                    } else {
                        JOptionPane.showMessageDialog(null, "Data harus diisi semua");
                    }
                    isiTable();
                    txtId.setText("");
                    txtName.setText("");
                    txtEmail.setText("");
                    txtTel.setText("");
                } catch (RollbackException exception) {
                    entityManager.getTransaction().rollback();
                }
            }
        });
    }

    public List<Members> queryForMembers() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("org.hibernate.tutorial.jpa");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Members> members = entityManager.createQuery("SELECT u FROM Members u", Members.class).getResultList();
        return members;
    }

    public void isiTable() {
        Object data[][] = new Object[queryForMembers().size()][4];
        int x = 0;
        for (Members m : queryForMembers()) {
            data[x][0] = m.getId();
            data[x][1] = m.getName();
            data[x][2] = m.getEmail();
            data[x][3] = m.getTel();
            x++;
        }
        String judul[] = {"Id", "Name", "Email", "Tel"};
        table1.setModel(new DefaultTableModel(data, judul));
        table_1.setViewportView(table1);
    }

    boolean cekText() {
        if (!txtId.getText().isEmpty()
                && !txtName.getText().isEmpty()
                && !txtEmail.getText().isEmpty()
                && !txtTel.getText().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
}
