package Model;
import Vue.Vue_Acceuil;

import javax.swing.*;
import java.sql.SQLException;

import static Vue.Vue_Acceuil.contentPanel;

public class Suppression
{
    public static void SupprimerUneAnnonce(int idAnnonce)
    {
        try
        {
            MonPdo monPdo = new MonPdo();
            String query = "DELETE annonce FROM annonce  WHERE Id_annonce = ?";
            int rowsAffected = monPdo.executerUneRequete(query, idAnnonce);
            if (rowsAffected > 0)
            {
                JOptionPane.showMessageDialog(null, "Annonce supprimée avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Échec de la suppression de l'annonce.", "Échec", JOptionPane.ERROR_MESSAGE);
            }
        }
        catch (Exception ex)
        {
            System.out.println("Une exception s'est produite lors de la suppression de l'annonce : " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Une erreur s'est produite lors de la suppression de l'annonce.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }


    }
    public static void SupprimerUnSignalement(int idSignalement)
    {
        try
        {
            MonPdo monPdo = new MonPdo();
            String query = "DELETE FROM signalement WHERE signalement.Id_signalement = ?";
            monPdo.executerUneRequete(query, idSignalement);
            JOptionPane.showMessageDialog(null, "Signalement supprimé avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
        }
        catch (SQLException e)
        {
            JOptionPane.showMessageDialog(null, "Probleme lors de la suppression du Signalement", "Erreur", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    public static void SupprimerUnUtilisateur(int Iduser)
    {
        try
        {
            MonPdo monPdo = new MonPdo();
            String query = "DELETE FROM user WHERE user.iduser = ?";
            monPdo.executerUneRequete(query, Iduser);
            JOptionPane.showMessageDialog(null, "Utilisateur supprimé avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
        }
        catch (SQLException e)
        {
            JOptionPane.showMessageDialog(null, "Probleme lors de la suppression de l'utilisateur", "Erreur", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    public static void SupprimerUnModele(int Id_reference)
    {
        try
        {
            MonPdo monPdo = new MonPdo();
            String query = "DELETE FROM ref WHERE ref.Id_ref = ?";
            monPdo.executerUneRequete(query, Id_reference);
            JOptionPane.showMessageDialog(null, "Modele supprimé avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
        }
        catch (SQLException e)
        {
            JOptionPane.showMessageDialog(null, "Probleme lors de la suppression du modele", "Erreur", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    public static void SupprimerUneMarque(int Id_marque)
    {
        try
        {
            MonPdo monPdo = new MonPdo();
            String query = "DELETE FROM marque WHERE marque.Id_marque = ?";
            monPdo.executerUneRequete(query, Id_marque);
            JOptionPane.showMessageDialog(null, "Marque supprimé avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
        }
        catch (SQLException e)
        {
            JOptionPane.showMessageDialog(null, "Probleme lors de la suppression de la marque", "Erreur", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
