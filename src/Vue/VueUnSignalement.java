package Vue;

import Model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;

import static Vue.Vue_Acceuil.contentPanel;

public class VueUnSignalement
{
    public static void afficherSignalement(int SignalementId)
    {
        Signalement signalement = Analyse.getSignalementById(SignalementId);

        JFrame frame = new JFrame("Signalement" + SignalementId);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        if (signalement != null)
        {
            addLabel(panel, gbc, "ID de Signalement : " + signalement.getId_signalement());
            addLabel(panel, gbc, "Motif du Signalement: " + signalement.getmotif());
            addLabel(panel, gbc, "ID de l'Annonce : " + signalement.getId_annonce());
            addLabel(panel, gbc, "Nom Plaignant: " + signalement.getNomPlaignant());
            addLabel(panel, gbc, "Prenom Plaignant : " + signalement.getPrenomPlaignant());
            addLabel(panel, gbc, "Nom Signaler : " + signalement.getPrenomSignaler());
            addLabel(panel, gbc, "Prenom Signaler : " + signalement.getPrenomSignaler());
        }
        else
        {
            addLabel(panel, gbc, "Aucun Signalement trouvée : ");
        }
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton button3 = new JButton("Afficher Afficher Données Annonce");
        JButton button1 = new JButton("Afficher sur le site");
        JButton button2 = new JButton("Supprimer");
        buttonPanel.add(button3);
        buttonPanel.add(button1);
        buttonPanel.add(button2);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        button3.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (signalement != null)
                {
                    int idAnnonce = signalement.getId_annonce();
                    VueUneAnnonce.afficherAnnonce(idAnnonce);
                }
            }
        });

        button1.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    Desktop.getDesktop().browse(new URI("http//localhost/Model/php/Index.php"));
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        });
        button2.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int SignalementId = signalement.getId_signalement();
                Suppression.SupprimerUnSignalement(SignalementId);
                frame.dispose();
                contentPanel.removeAll();
                Analyse.afficherListeSiglalement(contentPanel);
                contentPanel.revalidate();
                contentPanel.repaint();
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
