package Model;

import Vue.VueAjouterMarque;
import Vue.VueAjouterModele;
import Vue.VueUnModele;
import Vue.VueUneMarque;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import static Vue.Vue_Acceuil.contentPanel;

public class Reference
{
    public static ArrayList<Marque> DonneeMarque()
    {
        MonPdo monPdo = new MonPdo();
        ArrayList<Marque> marques = new ArrayList<>();
        try {
            ResultSet resultSet = monPdo.executerRequete(
                    "SELECT * FROM marque");
            while (resultSet.next()) {
                int Id_marque = resultSet.getInt("Id_marque");
                String Nom_marque = resultSet.getString("Nom");
                marques.add(new Marque(Id_marque, Nom_marque));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return marques;
    }
    public static ArrayList<Modele> DonneeModele()
    {
        MonPdo monPdo = new MonPdo();
        ArrayList<Modele> modeles = new ArrayList<>();
        try
        {
            ResultSet resultSet = monPdo.executerRequete("SELECT * FROM ref");
            while (resultSet.next())
            {
                int Id_ref = resultSet.getInt("Id_ref");
                String Nom_modele = resultSet.getString("Nom");
                int Id_marque = resultSet.getInt("Id_marque");
                modeles.add(new Modele(Id_ref, Nom_modele, Id_marque));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return modeles;
    }
    public static ArrayList<Modele> DonneeModeleUneMarque(int Id_marque)
    {
        MonPdo monPdo = new MonPdo();
        ArrayList<Modele> modeles = new ArrayList<>();
        try
        {
            ResultSet resultSet = monPdo.executerRequete(
                    "SELECT *\n" +
                            "FROM ref\n" +
                            STR."WHERE Id_marque = \{Id_marque};");
            while (resultSet.next())
            {
                int Id_ref = resultSet.getInt("Id_ref");
                String Nom_modele = resultSet.getString("Nom");
                modeles.add(new Modele(Id_ref, Nom_modele, Id_marque));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return modeles;
    }
    private static ArrayList<Integer> modelesSelectionnees = new ArrayList<>();
    private static ArrayList<Integer> marquesSelectionnees = new ArrayList<>();
    public static void afficherListeMarque(JPanel panel)
    {
        ArrayList<Marque> modeles = DonneeMarque();
        Object[][] data = new Object[modeles.size()][13];
        for (int i = 0; i < modeles.size(); i++) {
            Marque marque = modeles.get(i);
            data[i] = new Object[]
                    {
                            null,
                            marque.getId_marque(),
                            marque.getNom_marque(),
                    };
        }
        String[] columnNames =
                {
                        "Sélection",
                        "Id Marque",
                        "Nom Marque"
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
                if (columnIndex == 0) {
                    return Boolean.class;
                }
                return super.getColumnClass(columnIndex);
            }
            @Override
            public void setValueAt(Object value, int row, int column)
            {
                if (column == 0 && value instanceof Boolean)
                {
                    if ((Boolean) value) {
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
        if (modeles.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Aucune marque Trouvé.", "Échec", JOptionPane.ERROR_MESSAGE);
        }
        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(table);

        table.getSelectionModel().addListSelectionListener(e ->
        {
            int ligneSelectionnee = table.getSelectedRow();
            if (ligneSelectionnee != -1)
            {
                marquesSelectionnees.clear();
                Object value = table.getValueAt(ligneSelectionnee, 1);
                if (value != null)
                {
                    marquesSelectionnees.add((Integer) value);
                }
            }
        });
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton button1 = new JButton("Ouvrir");
        JButton button2 = new JButton("Supprimer");
        JButton button3 = new JButton("Ajouter");
        JButton button4 = new JButton("Liste Modele");
        buttonPanel.add(button1);
        buttonPanel.add(button2);
        buttonPanel.add(button3);
        buttonPanel.add(button4);
        button1.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (!marquesSelectionnees.isEmpty())
                {
                    VueUneMarque.afficherMarque(marquesSelectionnees.getFirst());
                }

                else
                {
                    JOptionPane.showMessageDialog(null, "Aucune marque selectionnée.", "Échec", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        button2.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(!marquesSelectionnees.isEmpty())
                {
                    int Id_marque = marquesSelectionnees.getFirst();
                    Suppression.SupprimerUneMarque(Id_marque);
                    contentPanel.removeAll();
                    Reference.afficherListeMarque(contentPanel);
                    contentPanel.revalidate();
                    contentPanel.repaint();
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Aucune marque selectionnée.", "Échec", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        button3.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                VueAjouterMarque.AjouterUneMarque();
            }
        });
        button4.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int Id_marque = marquesSelectionnees.getFirst();
                contentPanel.removeAll();
                Reference.afficherListeModeleParMarque(contentPanel, Id_marque);
                contentPanel.revalidate();
                contentPanel.repaint();
            }
        });

        panel.removeAll();
        panel.setLayout(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        panel.revalidate();
        panel.repaint();
    }
    public static void afficherListeModele(JPanel panel)
    {
        ArrayList<Modele> modeles = DonneeModele();
        Object[][] data = new Object[modeles.size()][13];
        for (int i = 0; i < modeles.size(); i++) {
            Modele modele = modeles.get(i);
            data[i] = new Object[]
                    {
                            null,
                            modele.getId_ref(),
                            modele.getNom_ref(),
                    };
        }
        String[] columnNames =
                {
                        "Sélection",
                        "Id Reference",
                        "Nom",
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
                if (columnIndex == 0) {
                    return Boolean.class;
                }
                return super.getColumnClass(columnIndex);
            }
            @Override
            public void setValueAt(Object value, int row, int column)
            {
                if (column == 0 && value instanceof Boolean)
                {
                    if ((Boolean) value) {
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
        if (modeles.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Aucun modele Trouvé.", "Échec", JOptionPane.ERROR_MESSAGE);
        }
        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(table);

        table.getSelectionModel().addListSelectionListener(e ->
        {
            int ligneSelectionnee = table.getSelectedRow();
            if (ligneSelectionnee != -1)
            {
                modelesSelectionnees.clear();
                Object value = table.getValueAt(ligneSelectionnee, 1);
                if (value != null)
                {
                    modelesSelectionnees.add((Integer) value);
                }
            }
        });
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton button1 = new JButton("Ouvrir");
        buttonPanel.add(button1);

        button1.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (!modelesSelectionnees.isEmpty())
                {
                    VueUnModele.afficherModele(modelesSelectionnees.getFirst());
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Aucun modele selectionnée.", "Échec", JOptionPane.ERROR_MESSAGE);
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
    public static void afficherListeModeleParMarque(JPanel panel, int Id_marque)
    {

        ArrayList<Modele> modeles = DonneeModeleUneMarque(Id_marque);

        Object[][] data = new Object[modeles.size()][13];
        for (int i = 0; i < modeles.size(); i++) {
            Modele modele = modeles.get(i);
            data[i] = new Object[]
                    {
                            null,
                            modele.getId_ref(),
                            modele.getNom_ref(),
                    };
        }
        String[] columnNames =
                {
                        "Sélection",
                        "Id Reference",
                        "Nom",
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
                if (columnIndex == 0) {
                    return Boolean.class;
                }
                return super.getColumnClass(columnIndex);
            }
            @Override
            public void setValueAt(Object value, int row, int column)
            {
                if (column == 0 && value instanceof Boolean)
                {
                    if ((Boolean) value) {
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
        if (modeles.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Aucun modele Trouvé.", "Échec", JOptionPane.ERROR_MESSAGE);
        }
        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(table);

        table.getSelectionModel().addListSelectionListener(e ->
        {
            int ligneSelectionnee = table.getSelectedRow();
            if (ligneSelectionnee != -1)
            {
                modelesSelectionnees.clear();
                Object value = table.getValueAt(ligneSelectionnee, 1);
                if (value != null)
                {
                    modelesSelectionnees.add((Integer) value);
                }
            }
        });
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton button1 = new JButton("Ouvrir");
        JButton button2 = new JButton("Ajouter un model");
        buttonPanel.add(button1);
        buttonPanel.add(button2);

        button1.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (!modelesSelectionnees.isEmpty())
                {
                    VueUnModele.afficherModele(modelesSelectionnees.getFirst());
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Aucun modele selectionnée.", "Échec", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        button2.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                VueAjouterModele.AjouterUnModele(Id_marque);
            }
        });

        panel.removeAll();
        panel.setLayout(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        panel.revalidate();
        panel.repaint();
    }
    public static Marque getMarqueById(int Id_marque)
    {
        MonPdo monPdo = new MonPdo();
        Marque marque = null;
        try
        {
            ResultSet resultSet = monPdo.executerRequete(STR."SELECT Id_marque, nom FROM marque WHERE Id_marque = \{Id_marque};");

            if (resultSet.next())
            {
                Id_marque = resultSet.getInt("Id_marque");
                String Nom_marque = resultSet.getString("Nom");

                marque = new Marque(Id_marque, Nom_marque);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return marque;
    }
    public static Montre getReferenceById(int Id_ref)
    {
        MonPdo monPdo = new MonPdo();
        Montre montre = null;
        try
        {
            ResultSet resultSet = monPdo.executerRequete(
                    STR."SELECT ref.Id_ref, ref.Id_marque, ref.Nom AS Nom_ref, marque.Nom AS Nom_marque FROM ref JOIN marque ON ref.Id_marque = marque.Id_marque WHERE ref.Id_ref = \{Id_ref}" );

            if (resultSet.next())
            {
                int Id_reference = resultSet.getInt("Id_ref");
                String Nom_modele = resultSet.getString("Nom_ref");
                String Nom_marque = resultSet.getString("Nom_marque");

                montre = new Montre(Id_reference, Nom_modele, Nom_marque);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return montre;
    }
    public static void AjouterMarque(String Nom_marque)
    {
        MonPdo monPdo = new MonPdo();
        try
        {
            monPdo.executerRequeteAjout(STR."INSERT INTO marque (Nom) VALUES ('\{Nom_marque}')");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    public static void AjouterModel(String Nom_modele,int Id_marque)
    {
        MonPdo monPdo = new MonPdo();
        try
        {
            monPdo.executerRequeteAjout(STR."INSERT INTO ref (Id_marque, Nom) VALUES (\{Id_marque}, '\{Nom_modele}')");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

}
