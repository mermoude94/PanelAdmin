package Vue;

import Model.Analyse;
import Model.Client;
import Model.Reference;
import Model.Traitement;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Vue_Acceuil extends JFrame {
    private Client client;
    public static JPanel contentPanel;

    public Vue_Acceuil() {
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
        JButton bouton5 = new JButton("Reference");
        JButton bouton6 = new JButton("Déconnexion");

        boutonsPanel.add(bouton1);
        boutonsPanel.add(bouton2);
        boutonsPanel.add(bouton3);
        boutonsPanel.add(bouton4);
        boutonsPanel.add(bouton5);
        boutonsPanel.add(bouton6);
        panel.add(boutonsPanel, BorderLayout.NORTH);

        contentPanel = new JPanel();
        panel.add(contentPanel, BorderLayout.CENTER);

        bouton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                contentPanel.removeAll();

                JLabel label = new JLabel("<html>Vous ete actuellement sur le Panel Admin du site.\n" +
                        "Les Donnée presente dans ce logiciel son privée et reserver a l'equipe interne du site.</html>");
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setVerticalAlignment(SwingConstants.CENTER);

                contentPanel.add(label);
                contentPanel.revalidate();
                contentPanel.repaint();
            }
        });

        bouton2.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                contentPanel.removeAll();
                Traitement.afficherListeAnnonces(contentPanel);
                contentPanel.revalidate();
                contentPanel.repaint();
            }
        });
        bouton3.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                contentPanel.removeAll();
                Analyse.afficherListeSiglalement(contentPanel);
                contentPanel.revalidate();
                contentPanel.repaint();
            }
        });

        bouton4.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                contentPanel.removeAll();
                Client.afficherListeUtilisateurs(contentPanel);
                contentPanel.revalidate();
                contentPanel.repaint();
            }
        });
        bouton5.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                contentPanel.removeAll();
                Reference.afficherLesListe(contentPanel);
                contentPanel.revalidate();
                contentPanel.repaint();
            }
        });

        add(panel);
        setVisible(true);
    }

}

