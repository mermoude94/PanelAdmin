package Vue;

import Model.Client;
import Model.Utilisateur;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;


public class Vue_Acceuil extends JFrame
{
    private Client client;

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


        boutonsPanel.add(bouton1);
        boutonsPanel.add(bouton2);
        boutonsPanel.add(bouton3);
        boutonsPanel.add(bouton4);
        boutonsPanel.add(bouton5);
        panel.add(boutonsPanel, BorderLayout.NORTH);

        bouton4.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Client.afficherListeUtilisateurs();
            }
        });

        add(panel);

        setVisible(true);
    }

}
