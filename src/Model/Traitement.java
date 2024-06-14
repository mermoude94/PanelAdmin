package Model;

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
import static java.lang.StringTemplate.STR;

public class Traitement
{
    public static ArrayList<Annonce> DonneeAnnonce()
    {
        MonPdo monPdo = new MonPdo();
        ArrayList<Annonce> annonces = new ArrayList<>();
        try
        {
            ResultSet resultSet = monPdo.executerRequete(
                    "SELECT annonce.iduser, " +
                            "annonce.Id_annonce, " +
                            "annonce.Id_marque, " +
                            "annonce.Id_ref, " +
                            "annonce.Prix, " +
                            "annonce.Id_photo, " +
                            "annonce.description, " +
                            "marque.nom AS nom_marque, " +
                            "ref.nom AS nom_ref, " +
                            "user.nom AS nom_user, " +
                            "user.prenom AS prenom_user, " +
                            "photo.nom_fichier AS nom_fichier " +
                            "FROM annonce " +
                            "LEFT JOIN marque ON annonce.Id_marque = marque.Id_marque " +
                            "LEFT JOIN photo ON annonce.Id_photo = photo.Id_photo " +
                            "LEFT JOIN ref ON annonce.Id_ref = ref.Id_ref " +
                            "LEFT JOIN user ON annonce.iduser = user.iduser");

            while (resultSet.next())
            {
                int iduser = resultSet.getInt("iduser");
                int Id_annonce = resultSet.getInt("Id_annonce");
                String Id_marque = resultSet.getString("Id_marque");
                String Id_ref = resultSet.getString("Id_ref");
                String Prix = resultSet.getString("Prix");
                String Id_photo = resultSet.getString("Id_photo");
                String description = resultSet.getString("description");
                String nom_marque = resultSet.getString("nom_marque");
                String nom_ref = resultSet.getString("nom_ref");
                String nom_user = resultSet.getString("nom_user");
                String prenom_user = resultSet.getString("prenom_user");
                String nom_fichier = resultSet.getString("nom_fichier");

                annonces.add(new Annonce(iduser, Id_annonce, Id_marque, Id_ref, Prix, Id_photo, description, nom_marque, nom_ref, nom_user, prenom_user, nom_fichier));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return annonces;
    }

    private static ArrayList<Integer> annoncesSelectionnees = new ArrayList<>();
    public static void afficherListeAnnonces(JPanel panel)
    {
        ArrayList<Annonce> annonces = DonneeAnnonce();
        Object[][] data = new Object[annonces.size()][13];
        for (int i = 0; i < annonces.size(); i++)
        {
            Annonce annonce = annonces.get(i);
            data[i] = new Object[]
                    {
                        null,
                        annonce.getIduser(),
                        annonce.getId_annonce(),
                        annonce.getId_marque(),
                        annonce.getId_ref(),
                        annonce.getPrix(),
                        annonce.getId_photo(),
                        annonce.getDescription(),
                        annonce.getNom_marque(),
                        annonce.getNom_ref(),
                        annonce.getNom_user(),
                        annonce.getPrenom_user(),
                        annonce.getNom_fichier()
                    };
        }
        String[] columnNames =
                {
                        "Sélection",
                        "iduser",
                        "Id_annonce",
                        "Id_marque",
                        "Id_ref",
                        "Prix",
                        "Id_photo",
                        "description",
                        "nom_marque",
                        "nom_ref",
                        "nom_user",
                        "prenom_user",
                        "nom_fichier"
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
        if(annonces.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Aucune annonce Trouvé.", "Échec", JOptionPane.ERROR_MESSAGE);
        }
        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(table);

        table.getSelectionModel().addListSelectionListener(e ->
        {
            int ligneSelectionnee = table.getSelectedRow();
            if (ligneSelectionnee != -1)
            {
                annoncesSelectionnees.clear();
                Object value = table.getValueAt(ligneSelectionnee, 2);
                if (value != null)
                {
                    annoncesSelectionnees.add((Integer) value);
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
                if (!annoncesSelectionnees.isEmpty())
                {
                    VueUneAnnonce.afficherAnnonce(annoncesSelectionnees.getFirst());
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Aucune annonce selectionnée.", "Échec", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        button2.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(!annoncesSelectionnees.isEmpty())
                {
                    int idAnnonce = annoncesSelectionnees.getFirst();
                    Suppression.SupprimerUneAnnonce(idAnnonce);
                    contentPanel.removeAll();
                    Traitement.afficherListeAnnonces(contentPanel);
                    contentPanel.revalidate();
                    contentPanel.repaint();
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Aucune annonce selectionnée.", "Échec", JOptionPane.ERROR_MESSAGE);
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
    public static Annonce getAnnonceById(int idAnnonce)
    {
        MonPdo monPdo = new MonPdo();
        Annonce annonce = null;

        try
        {
            ResultSet resultSet = monPdo.executerRequete("SELECT annonce.iduser," +
                    "annonce.Id_annonce," +
                            "annonce.Id_marque," +
                            "annonce.Id_ref," +
                            "annonce.Prix," +
                            "annonce.Id_photo," +
                            "annonce.description," +
                            "marque.nom AS nom_marque," +
                            "ref.nom AS nom_ref," +
                            "user.nom AS nom_user," +
                            "user.prenom AS prenom_user," +
                            "photo.nom_fichier AS nom_fichier " +
                            "FROM annonce LEFT JOIN marque ON annonce.Id_marque = marque.Id_marque " +
                            "LEFT JOIN photo ON annonce.Id_photo = photo.Id_photo " +
                            "LEFT JOIN ref ON annonce.Id_ref = ref.Id_ref " +
                            "LEFT JOIN user ON annonce.iduser = user.iduser " +
                            STR."WHERE annonce.Id_annonce = \{idAnnonce}");

            if (resultSet.next())
            {
                int iduser = resultSet.getInt("iduser");
                int Id_annonce = resultSet.getInt("Id_annonce");
                String Id_marque = resultSet.getString("Id_marque");
                String Id_ref = resultSet.getString("Id_ref");
                String Prix = resultSet.getString("Prix");
                String Id_photo = resultSet.getString("Id_photo");
                String description = resultSet.getString("description");
                String nom_marque = resultSet.getString("nom_marque");
                String nom_ref = resultSet.getString("nom_ref");
                String nom_user = resultSet.getString("nom_user");
                String prenom_user = resultSet.getString("prenom_user");
                String nom_fichier = resultSet.getString("nom_fichier");

                annonce = new Annonce(iduser, Id_annonce, Id_marque, Id_ref, Prix, Id_photo, description, nom_marque, nom_ref, nom_user, prenom_user, nom_fichier);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return annonce;
    }
    public static ArrayList<Annonce> RecupererLesAnnonceDunclient(int Iduser)
    {
        MonPdo monPdo = new MonPdo();
        ArrayList<Annonce> annonces = new ArrayList<>();
        try
        {
            ResultSet resultSet = monPdo.executerRequete(
                    "SELECT annonce.iduser, " +
                            "annonce.Id_annonce, " +
                            "annonce.Id_marque, " +
                            "annonce.Id_ref, " +
                            "annonce.Prix, " +
                            "annonce.Id_photo, " +
                            "annonce.description, " +
                            "marque.nom AS nom_marque, " +
                            "ref.nom AS nom_ref, " +
                            "user.nom AS nom_user, " +
                            "user.prenom AS prenom_user, " +
                            "photo.nom_fichier AS nom_fichier " +
                            "FROM annonce " +
                            "LEFT JOIN marque ON annonce.Id_marque = marque.Id_marque " +
                            "LEFT JOIN photo ON annonce.Id_photo = photo.Id_photo " +
                            "LEFT JOIN ref ON annonce.Id_ref = ref.Id_ref " +
                            "LEFT JOIN user ON annonce.iduser = user.iduser " +
                            STR."WHERE annonce.iduser = \{Iduser}");


            while (resultSet.next())
            {
                int Id_annonce = resultSet.getInt("Id_annonce");
                String Id_marque = resultSet.getString("Id_marque");
                String Id_ref = resultSet.getString("Id_ref");
                String Prix = resultSet.getString("Prix");
                String Id_photo = resultSet.getString("Id_photo");
                String description = resultSet.getString("description");
                String nom_marque = resultSet.getString("nom_marque");
                String nom_ref = resultSet.getString("nom_ref");
                String nom_user = resultSet.getString("nom_user");
                String prenom_user = resultSet.getString("prenom_user");
                String nom_fichier = resultSet.getString("nom_fichier");
                annonces.add(new Annonce(Iduser, Id_annonce, Id_marque, Id_ref, Prix, Id_photo, description, nom_marque, nom_ref, nom_user, prenom_user, nom_fichier));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return annonces;
    }
    public static void afficherListeAnnoncesDuClient(JPanel panel,int Iduser )
    {
        ArrayList<Annonce> annonces = RecupererLesAnnonceDunclient(Iduser);
        Object[][] data = new Object[annonces.size()][13];
        for (int i = 0; i < annonces.size(); i++)
        {
            Annonce annonce = annonces.get(i);
            data[i] = new Object[]
                    {
                            null,
                            annonce.getIduser(),
                            annonce.getId_annonce(),
                            annonce.getId_marque(),
                            annonce.getId_ref(),
                            annonce.getPrix(),
                            annonce.getId_photo(),
                            annonce.getDescription(),
                            annonce.getNom_marque(),
                            annonce.getNom_ref(),
                            annonce.getNom_user(),
                            annonce.getPrenom_user(),
                            annonce.getNom_fichier()
                    };
        }
        String[] columnNames =
                {
                        "Sélection",
                        "iduser",
                        "Id_annonce",
                        "Id_marque",
                        "Id_ref",
                        "Prix",
                        "Id_photo",
                        "description",
                        "nom_marque",
                        "nom_ref",
                        "nom_user",
                        "prenom_user",
                        "nom_fichier"
                };
        if(annonces.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Aucune annonce Trouvé.", "Échec", JOptionPane.ERROR_MESSAGE);
        }
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
                annoncesSelectionnees.clear();
                Object value = table.getValueAt(ligneSelectionnee, 2);
                if (value != null)
                {
                    annoncesSelectionnees.add((Integer) value);
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
                if (!annoncesSelectionnees.isEmpty())
                {
                    VueUneAnnonce.afficherAnnonce(annoncesSelectionnees.get(0));
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Aucune annonce selectionnée.", "Échec", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        button2.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(!annoncesSelectionnees.isEmpty())
                {
                    int idAnnonce = annoncesSelectionnees.getFirst();
                    Suppression.SupprimerUneAnnonce(idAnnonce);
                    contentPanel.removeAll();
                    Traitement.afficherListeAnnonces(contentPanel);
                    contentPanel.revalidate();
                    contentPanel.repaint();
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Aucune annonce selectionnée.", "Échec", JOptionPane.ERROR_MESSAGE);
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
}