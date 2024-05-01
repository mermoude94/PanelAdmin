package Model;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Client
{
    public ArrayList<Utilisateur> DonneeUtilisateurs()
    {
        MonPdo monPdo = new MonPdo();
        ArrayList<Utilisateur> utilisateurs = new ArrayList<>();
        try {
            ResultSet resultSet = monPdo.executerRequete("SELECT * FROM user");
            while (resultSet.next()) {
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String email = resultSet.getString("email");
                String adresse = resultSet.getString("adresse");
                utilisateurs.add(new Utilisateur(nom, prenom, email, adresse));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return utilisateurs;
    }
    public static void afficherListeUtilisateurs()
    {
        Client client = new Client();
        ArrayList<Utilisateur> utilisateurs = client.DonneeUtilisateurs();

        DefaultTableModel model = new DefaultTableModel();

        // Ajouter les colonnes au modèle
        model.addColumn("Nom");
        model.addColumn("Prénom");
        model.addColumn("Email");
        model.addColumn("Adresse");

        // Ajouter les données des utilisateurs au modèle
        for (Utilisateur utilisateur : utilisateurs)
        {
            model.addRow(new Object[]{utilisateur.getNom(), utilisateur.getPrenom(), utilisateur.getEmail(), utilisateur.getAdresse()});
        }

        // Créer la table avec le modèle de données
        JTable table = new JTable(model);

        // Créer un JScrollPane pour ajouter la table avec barre de défilement
        JScrollPane scrollPane = new JScrollPane(table);

        // Créer une fenêtre pour afficher la table
        JFrame frame = new JFrame("Liste des Utilisateurs");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}