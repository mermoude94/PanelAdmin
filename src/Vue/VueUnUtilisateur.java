package Vue;
import Model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Vue.Vue_Acceuil.contentPanel;

public class VueUnUtilisateur
{
    public static void afficherUtilisateur(int Iduser)
    {
        Utilisateur utilisateur = Client.getUtilisateurById(Iduser);

        JFrame frame = new JFrame("Utilisateur" + utilisateur.getNom() + " " + utilisateur.getPrenom());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        if (utilisateur != null)
        {
            addLabel(panel, gbc, "ID User : " + utilisateur.getIduser());
            addLabel(panel, gbc, "Nom: " + utilisateur.getNom());
            addLabel(panel, gbc, "Prenom : " + utilisateur.getPrenom());
            addLabel(panel, gbc, "email: " + utilisateur.getEmail());
            addLabel(panel, gbc, "adresse : " + utilisateur.getAdresse());
        }
        else
        {
            addLabel(panel, gbc, "Aucun Utilisateur trouvée.");
        }
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton button1 = new JButton("Afficher les annonce du Client");
        JButton button2 = new JButton("Supprimer");
        buttonPanel.add(button1);
        buttonPanel.add(button2);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        button1.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (utilisateur != null)
                {
                    Traitement.afficherListeAnnoncesDuClient(contentPanel, utilisateur.getIduser());
                    contentPanel.revalidate();
                    contentPanel.repaint();
                }
            }
        });

    }

    private static void addLabel(JPanel panel, GridBagConstraints gbc, String text)
    {
        JLabel label = new JLabel(text);
        panel.add(label, gbc);
        gbc.gridy++;
    }
}
