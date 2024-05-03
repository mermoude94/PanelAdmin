package Model;
import javax.swing.*;
import java.sql.SQLException;

public class Suppression
{
    public static void SupprimerUneAnnonce(int idAnnonce)
    {
        try
        {
            MonPdo monPdo = new MonPdo();
            String query = "DELETE annonce, photo FROM annonce JOIN photo ON annonce.Id_Photo = photo.Id_Photo WHERE annonce.Id_annonce = ?";
            monPdo.executerUneRequete(query, idAnnonce);
            JOptionPane.showMessageDialog(null, "Annonce supprimée avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
        }
        catch (SQLException e)
        {
            JOptionPane.showMessageDialog(null, "Probleme lors de la suppression de l'annonce", "Erreur", JOptionPane.INFORMATION_MESSAGE);
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
}
