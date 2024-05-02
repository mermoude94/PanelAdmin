package Vue;

import Model.Annonce;
import Model.Traitement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;

public class VueUneAnnonce
{
    public static void afficherAnnonce(int idAnnonce)
    {
        Annonce annonce = Traitement.getAnnonceById(idAnnonce);

        JFrame frame = new JFrame("Annonce " + idAnnonce);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        if (annonce != null)
        {
            addLabel(panel, gbc, "ID de l'annonce : " + annonce.getId_annonce());
            addLabel(panel, gbc, "ID de l'utilisateur : " + annonce.getIduser());
            addLabel(panel, gbc, "Marque : " + annonce.getNom_marque());
            addLabel(panel, gbc, "Référence : " + annonce.getNom_ref());
            addLabel(panel, gbc, "Prix : " + annonce.getPrix());
            addLabel(panel, gbc, "Description : " + annonce.getDescription());
        }
        else
        {
            addLabel(panel, gbc, "Aucune annonce trouvée : " + idAnnonce);
        }
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton button1 = new JButton("Afficher en web");
        JButton button2 = new JButton("Supprimer");
        buttonPanel.add(button1);
        buttonPanel.add(button2);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action à effectuer lors du clic sur le bouton
                try
                {
                    // Ouvrir l'URL dans le navigateur par défaut
                    Desktop.getDesktop().browse(new URI("http//localhost/Model/php/Index.php"));
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
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
