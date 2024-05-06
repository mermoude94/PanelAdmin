package Model;
import Vue.VueUnSignalement;
import Vue.VueUneAnnonce;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static Vue.Vue_Acceuil.contentPanel;

public class Analyse
{
    public static ArrayList<Signalement> DonneeSignalements()
    {
        MonPdo monPdo = new MonPdo();
        ArrayList<Signalement> signalements = new ArrayList<>();
        try
        {
            ResultSet resultSet = monPdo.executerRequete(
                        "SELECT \n" +
                            "    s.Id_signalement,\n" +
                            "    s.motif,\n" +
                            "    s.Id_annonce,\n" +
                            "    plaignant.nom AS nom_plaignant,\n" +
                            "    plaignant.prenom AS prenom_plaignant,\n" +
                            "    signaler.nom AS nom_signaler,\n" +
                            "    signaler.prenom AS prenom_signaler\n" +
                            "FROM \n" +
                            "    signalement AS s\n" +
                            "JOIN \n" +
                            "    user AS plaignant ON s.idplaignant = plaignant.iduser\n" +
                            "JOIN \n" +
                            "    user AS signaler ON s.idsignaler = signaler.iduser;");
            while (resultSet.next())
            {
                int idSignalement = resultSet.getInt("Id_signalement");
                String motif = resultSet.getString("motif");
                int idAnnonce = resultSet.getInt("Id_annonce");
                String nomPlaignant = resultSet.getString("nom_plaignant");
                String prenomPlaignant = resultSet.getString("prenom_plaignant");
                String nomSignaler = resultSet.getString("nom_signaler");
                String prenomSignaler = resultSet.getString("prenom_signaler");
                signalements.add(new Signalement(idSignalement, motif, idAnnonce, nomPlaignant, prenomPlaignant, nomSignaler, prenomSignaler));
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return signalements;
    }
    private static ArrayList<Integer> SignalementSelectionnees = new ArrayList<>();
    public static void afficherListeSiglalement(JPanel panel)
    {
        ArrayList<Signalement> signalements = DonneeSignalements();
        Object[][] data = new Object[signalements.size()][13];
        for (int i = 0; i < signalements.size(); i++)
        {
            Signalement signalement = signalements.get(i);
            data[i] = new Object[]
                    {
                        null,
                        signalement.getId_signalement(),
                        signalement.getmotif(),
                        signalement.getId_annonce(),
                        signalement.getNomPlaignant(),
                        signalement.getPrenomPlaignant(),
                        signalement.getNomSignaler(),
                        signalement.getPrenomSignaler(),
                    };
        }

        String[] columnNames =
                {
                        "Sélection",
                        "Id signalement",
                        "Motif",
                        "Id Annonce",
                        "Nom plaignant",
                        "Prenom plaignant",
                        "Nom Signaler",
                        "Prenom Signaler",
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
                        SignalementSelectionnees.clear();
                        Object value = table.getValueAt(ligneSelectionnee, 1);
                        if (value != null)
                        {
                            SignalementSelectionnees.add((Integer) value);
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
                if (!SignalementSelectionnees.isEmpty())
                {
                    VueUnSignalement.afficherSignalement(SignalementSelectionnees.get(0));
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Aucun Signalement selectionné.", "Échec", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        button2.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (!SignalementSelectionnees.isEmpty())
                {
                    int idSignalement = SignalementSelectionnees.get(0);
                    Suppression.SupprimerUnSignalement(idSignalement);
                    contentPanel.removeAll();
                    Analyse.afficherListeSiglalement(contentPanel);
                    contentPanel.revalidate();
                    contentPanel.repaint();
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Aucun Signalement selectionné.", "Échec", JOptionPane.ERROR_MESSAGE);
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
    public static Signalement getSignalementById(int SignalementId)
    {
        MonPdo monPdo = new MonPdo();
        Signalement Signalement = null;
        try
        {
            ResultSet resultSet = monPdo.executerRequete(
                    "SELECT \n" +
                            "    s.Id_signalement,\n" +
                            "    s.motif,\n" +
                            "    s.Id_annonce,\n" +
                            "    plaignant.nom AS nom_plaignant,\n" +
                            "    plaignant.prenom AS prenom_plaignant,\n" +
                            "    signaler.nom AS nom_signaler,\n" +
                            "    signaler.prenom AS prenom_signaler\n" +
                            "FROM \n" +
                            "    signalement AS s\n" +
                            "JOIN \n" +
                            "    user AS plaignant ON s.idplaignant = plaignant.iduser\n" +
                            "JOIN \n" +
                            "    user AS signaler ON s.idsignaler = signaler.iduser\n" +
                            "WHERE\n" +
                            STR."    s.Id_signalement = \{SignalementId}"
            );

            if (resultSet.next())
            {
                int Id_Signalement = resultSet.getInt("Id_signalement");
                String Motif = resultSet.getString("motif");
                int Id_annonce = resultSet.getInt("Id_annonce");
                String Nom_Plaignant = resultSet.getString("nom_plaignant");
                String Prenom_Plaignant = resultSet.getString("prenom_plaignant");
                String Nom_Signaler = resultSet.getString("nom_signaler");
                String Prenom_Signaler = resultSet.getString("prenom_signaler");

                Signalement = new Signalement(Id_Signalement, Motif, Id_annonce, Nom_Plaignant, Prenom_Plaignant, Nom_Signaler, Prenom_Signaler);
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return Signalement;
    }

}
