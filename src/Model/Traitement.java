package Model;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Traitement
{
    public static ArrayList<Annonce> DonneeAnnonce()
    {
        MonPdo monPdo = new MonPdo();
        ArrayList<Annonce> annonces = new ArrayList<>();
        try {
            ResultSet resultSet = monPdo.executerRequete("SELECT annonce.*, marque.nom AS nom_marque, ref.nom AS nom_ref, user.nom AS nom_user, user.prenom AS prenom_user, photo.nom_fichier AS nom_fichier FROM annonce LEFT JOIN marque ON annonce.Id_marque = marque.Id_marque LEFT JOIN photo ON photo.nom_fichier = photo.nom_fichier LEFT JOIN ref ON annonce.Id_ref = ref.Id_ref LEFT JOIN user ON annonce.iduser = user.iduser;");
            while (resultSet.next())
            {
                String iduser = resultSet.getString("iduser");
                String Id_annonce = resultSet.getString("Id_annonce");
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

    public static void afficherListeAnnonces(JFrame frame)
    {
        Traitement Traitement = new Traitement();
        ArrayList<Annonce> annonces = DonneeAnnonce();

        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("iduser");
        model.addColumn("Id_annonce");
        model.addColumn("Id_marque");
        model.addColumn("Id_ref");
        model.addColumn("Prix");
        model.addColumn("Id_photo");
        model.addColumn("description");
        model.addColumn("nom_marque");
        model.addColumn("nom_ref");
        model.addColumn("nom_user");
        model.addColumn("prenom_user");
        model.addColumn("nom_fichier");

        for (Annonce annonce : annonces)
        {
            model.addRow(new Object[]
                    {
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
                    });
        }

        JTable table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);

        frame.setTitle("Liste des Utilisateurs");
        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();

    }
}