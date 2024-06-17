package Vue;

import Model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Vue.Vue_Acceuil.contentPanel;

public class VueAjouterModele
{
    public static void AjouterUnModele(int Id_marque)
    {
        JFrame frame = new JFrame("Ajouter modele");
        JPanel panel = new JPanel(new GridLayout(3, 2));
        JLabel labelname = new JLabel("Nom du modele");
        JTextField textFieldname = new JTextField();
        JButton buttonValider = new JButton("Valider");

        buttonValider.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                String Nom_modele = textFieldname.getText();

                if (!Nom_modele.isEmpty())
                {
                    Reference.AjouterModel(Nom_modele, Id_marque);
                    JOptionPane.showMessageDialog(frame, "Modele Ajouté avec succès.");
                    frame.dispose();
                    Reference.afficherListeMarque(contentPanel);
                    contentPanel.revalidate();
                    contentPanel.repaint();
                }
                else
                {
                    JOptionPane.showMessageDialog(frame, "Nom du modele ne peut pas être vide. Veuillez réessayer.");
                }
            }
        });

        panel.add(labelname);
        panel.add(textFieldname);
        panel.add(new JLabel());
        panel.add(buttonValider);
        frame.add(panel);
        frame.setSize(1280, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
