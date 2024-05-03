package Model;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Reference
{
    public static ArrayList<Marque> DonneeMarque()
    {
        MonPdo monPdo = new MonPdo();
        ArrayList<Marque> marques = new ArrayList<>();
        try
        {
            ResultSet resultSet = monPdo.executerRequete(
                    "SELECT * FROM marque");
            while (resultSet.next())
            {
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
            ResultSet resultSet = monPdo.executerRequete(
                    "SELECT * FROM ref");
            while (resultSet.next())
            {
                int Id_ref = resultSet.getInt("Id_ref");
                String Nom_ref = resultSet.getString("Nom");
                modeles.add(new Modele(Id_ref, Nom_ref));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return modeles;
    }
    private static ArrayList<Integer> MarqueSelectionnees = new ArrayList<>();
    private static ArrayList<Integer> ModeleSelectionnees = new ArrayList<>();

    public static void afficherLesListe(JPanel panel)
    {
        ArrayList<Marque> marques = DonneeMarque();
        ArrayList<Modele> modeles = DonneeModele();

        JTable tableMarques = CreerTableMarque(marques);
        JTable tableModeles = CreerTableModele(modeles);

        tableMarques.getSelectionModel().addListSelectionListener(e ->
        {
            if (!e.getValueIsAdjusting())
            {
                int selectedRow = tableMarques.getSelectedRow();
                if (selectedRow != -1) {
                    boolean isSelected = (boolean) tableMarques.getValueAt(selectedRow, 0);
                    if (isSelected)
                    {
                        for (int i = 0; i < tableMarques.getRowCount(); i++)
                        {
                            if (i != selectedRow && (boolean) tableMarques.getValueAt(i, 0))
                            {
                                tableMarques.setValueAt(false, i, 0);
                            }
                        }
                        for (int i = 0; i < tableModeles.getRowCount(); i++)
                        {
                            if ((boolean) tableModeles.getValueAt(i, 0))
                            {
                                tableModeles.setValueAt(false, i, 0);
                            }
                        }
                    }
                }
            }
        });

        tableModeles.getSelectionModel().addListSelectionListener(e ->
        {
            if (!e.getValueIsAdjusting())
            {
                int selectedRow = tableModeles.getSelectedRow();
                if (selectedRow != -1)
                {
                    boolean isSelected = (boolean) tableModeles.getValueAt(selectedRow, 0);
                    if (isSelected)
                    {
                        for (int i = 0; i < tableModeles.getRowCount(); i++)
                        {
                            if (i != selectedRow && (boolean) tableModeles.getValueAt(i, 0))
                            {
                                tableModeles.setValueAt(false, i, 0);
                            }
                        }
                        for (int i = 0; i < tableMarques.getRowCount(); i++)
                        {
                            if ((boolean) tableMarques.getValueAt(i, 0))
                            {
                                tableMarques.setValueAt(false, i, 0);
                            }
                        }
                    }
                }
            }
        });



        JPanel tablesPanel = new JPanel(new GridLayout(1, 2));
        tablesPanel.add(new JScrollPane(tableMarques));
        tablesPanel.add(new JScrollPane(tableModeles));

        panel.setLayout(new BorderLayout());
        panel.add(tablesPanel, BorderLayout.CENTER);

        panel.revalidate();
        panel.repaint();
    }

    private static JTable CreerTableMarque(ArrayList<Marque> marques)
    {
        Object[][] data = new Object[marques.size()][3];
        for (int i = 0; i < marques.size(); i++)
        {
            Marque marque = marques.get(i);
            data[i] = new Object[]
                    {
                    false,
                    marque.getId_marque(),
                    marque.getNom_marque()
            };
        }
        String[] columnNames = {"Sélection", "ID", "Nom"};
        DefaultTableModel model = new DefaultTableModel(data, columnNames)
        {
            @Override
            public Class<?> getColumnClass(int columnIndex)
            {
                if (columnIndex == 0)
                {
                    return Boolean.class;
                }
                return super.getColumnClass(columnIndex);
            }
        };

        return new JTable(model);
    }

    private static JTable CreerTableModele(ArrayList<Modele> modeles)
    {
        Object[][] data = new Object[modeles.size()][3];
        for (int i = 0; i < modeles.size(); i++)
        {
            Modele modele = modeles.get(i);
            data[i] = new Object[]{
                    false,
                    modele.getId_ref(),
                    modele.getNom_ref(),
            };
        }

        String[] columnNames = {"Sélection", "ID", "Nom"};
        DefaultTableModel model = new DefaultTableModel(data, columnNames)
        {
            @Override
            public Class<?> getColumnClass(int columnIndex)
            {
                if (columnIndex == 0)
                {
                    return Boolean.class;
                }
                return super.getColumnClass(columnIndex);
            }
        };

        return new JTable(model);
    }
}
