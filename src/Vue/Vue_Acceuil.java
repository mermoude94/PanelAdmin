package Vue;
import javax.swing.*;
import java.awt.*;

public class Vue_Acceuil extends JFrame
{

    public Vue_Acceuil()
    {
        setTitle("Page d'accueil");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 720);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        JPanel boutonsPanel = new JPanel(new GridLayout(1, 5));
        JButton bouton1 = new JButton("Accueil");
        JButton bouton2 = new JButton("Annonce");
        JButton bouton3 = new JButton("Signalement");
        JButton bouton4 = new JButton("Liste Utilisateurs");
        JButton bouton5 = new JButton("Déconnexion");

        JLabel label = new JLabel("Bonjour et bien venue dans le PanelAdmin");
        label.setHorizontalAlignment(SwingConstants.CENTER);

        boutonsPanel.add(bouton1);
        boutonsPanel.add(bouton2);
        boutonsPanel.add(bouton3);
        boutonsPanel.add(bouton4);
        boutonsPanel.add(bouton5);

        panel.add(label, BorderLayout.CENTER);
        panel.add(boutonsPanel, BorderLayout.NORTH);

        add(panel);

        setVisible(true);
    }
}
