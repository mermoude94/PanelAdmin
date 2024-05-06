package Model;

import Vue.VueUnUtilisateur;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static Vue.Vue_Acceuil.contentPanel;

public class Client
{
    public static ArrayList<Utilisateur> DonneeUtilisateurs()
    {
        MonPdo monPdo = new MonPdo();
        ArrayList<Utilisateur> utilisateurs = new ArrayList<>();
        try
        {
            ResultSet resultSet = monPdo.executerRequete("SELECT * FROM user");
            while (resultSet.next())
            {
                int iduser = resultSet.getInt("iduser");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String email = resultSet.getString("email");
                String adresse = resultSet.getString("adresse");
                utilisateurs.add(new Utilisateur(iduser,nom, prenom, email, adresse));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return utilisateurs;
    }
    private static ArrayList<Integer> utilisateursSelectionnees = new ArrayList<>();
    public static void afficherListeUtilisateurs(JPanel panel)
    {
        ArrayList<Utilisateur> clients = DonneeUtilisateurs();
        Object[][] data = new Object[clients.size()][13];
        for (int i = 0; i < clients.size(); i++) {
            Utilisateur utilisateur = clients.get(i);
            data[i] = new Object[]
                    {
                            null,
                            utilisateur.getIduser(),
                            utilisateur.getNom(),
                            utilisateur.getPrenom(),
                            utilisateur.getEmail(),
                            utilisateur.getAdresse()
                    };
        }

        String[] columnNames =
                {
                        "Sélection",
                        "iduser",
                        "Nom",
                        "Prénom",
                        "Email",
                        "Adresse"
                };
        DefaultTableModel model = new DefaultTableModel(data, columnNames)
        {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return column == 0;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex)
            {
                if (columnIndex == 0)
                {
                    return Boolean.class;
                }
                return super.getColumnClass(columnIndex);
            }

            @Override
            public void setValueAt(Object value, int row, int column)
            {
                if (column == 0 && value instanceof Boolean)
                {
                    if ((Boolean) value)
                    {
                        for (int i = 0; i < getRowCount(); i++)
                        {
                            if (i != row)
                            {
                                setValueAt(false, i, column);
                            }
                        }
                    }
                }
                super.setValueAt(value, row, column);
            }
        };

        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(table);

        table.getSelectionModel().addListSelectionListener(e ->
        {
                int ligneSelectionnee = table.getSelectedRow();
                if (ligneSelectionnee != -1)
                {
                    utilisateursSelectionnees.clear();
                    Object value = table.getValueAt(ligneSelectionnee, 1);
                    if (value != null)
                    {
                        utilisateursSelectionnees.add((Integer) value);
                    }
                }
        });
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton button1 = new JButton("Ouvrir");
        JButton button2 = new JButton("Supprimer");
        buttonPanel.add(button1);
        buttonPanel.add(button2);

        button1.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (!utilisateursSelectionnees.isEmpty())
                {
                    VueUnUtilisateur.afficherUtilisateur(utilisateursSelectionnees.get(0));
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Aucun utilisateur selectionner.", "Échec", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        button2.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(!utilisateursSelectionnees.isEmpty())
                {
                    int idAnnonce = utilisateursSelectionnees.getFirst();
                    contentPanel.removeAll();
                    Traitement.afficherListeAnnonces(contentPanel);
                    contentPanel.revalidate();
                    contentPanel.repaint();
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Aucun utilisateur.", "Échec", JOptionPane.ERROR_MESSAGE);
                }
            }

        });

        panel.removeAll();
        panel.setLayout(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        panel.revalidate();
        panel.repaint();
    }
    public static Utilisateur getUtilisateurById(int Iduser)
    {
        MonPdo monPdo = new MonPdo();
        Utilisateur utilisateurs = null;
        try
        {
            ResultSet resultSet = monPdo.executerRequete(
                    "SELECT iduser, nom, prenom, email, adresse\n" +
                    "FROM user\n" +
                    STR."\tWHERE iduser = \{Iduser}");

            if (resultSet.next())
            {
                int iduser = resultSet.getInt("iduser");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String email = resultSet.getString("email");
                String adresse = resultSet.getString("adresse");
                utilisateurs = new Utilisateur(iduser,nom, prenom, email, adresse);
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return utilisateurs;
    }
}

