package Model;

import Vue.VueUnModele;

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
        try {
            ResultSet resultSet = monPdo.executerRequete(
                    "SELECT * FROM ref");
            while (resultSet.next())
            {
                int Id_ref = resultSet.getInt("Id Reference");
                String Nom_modele = resultSet.getString("Nom");
                modeles.add(new Modele(Id_ref, Nom_modele));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return modeles;
    }
    private static ArrayList<Integer> modelesSelectionnees = new ArrayList<>();
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
                Object value = table.getValueAt(ligneSelectionnee, 2);
                if (value != null)
                {
                    modelesSelectionnees.add((Integer) value);
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
                if (!modelesSelectionnees.isEmpty())
                {
                    int idAnnonce = modelesSelectionnees.getFirst();
                    Suppression.SupprimerUneAnnonce(idAnnonce);
                    contentPanel.removeAll();
                    Traitement.afficherListeAnnonces(contentPanel);
                    contentPanel.revalidate();
                    contentPanel.repaint();
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
    public static Montre getReferenceById(int Id_ref)
    {
        MonPdo monPdo = new MonPdo();
        Montre montre = null;
        try
        {
            ResultSet resultSet = monPdo.executerRequete("SELECT \n" +
                    "    ref.Id_ref, \n" +
                    "    ref.Nom AS Nom_ref, \n" +
                    "    marque.Nom_marque\n" +
                    "FROM \n" +
                    "    ref\n" +
                    "JOIN \n" +
                    "    marque \n" +
                    "ON \n" +
                    STR."ref.Id_marque = \\{Id_ref}");

            if (resultSet.next())
            {
                int Id_reference = resultSet.getInt("Id reference");
                String Nom_modele = resultSet.getString("Nom modele");
                String Nom_marque = resultSet.getString("Id_ref");


                montre = new Montre(Id_ref, Nom_modele, Nom_marque);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return montre;
    }
}
