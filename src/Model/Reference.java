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

    }
    private static ArrayList<Integer> ReferenceSelectionnees = new ArrayList<>();
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
        DefaultTableModel marque = new DefaultTableModel(data, columnNames)
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

        return new JTable(marque);
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
