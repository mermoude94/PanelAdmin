package Vue;

import Model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Vue.Vue_Acceuil.contentPanel;

public class VueAjouterMarque
{
    public static void AjouterUneMarque()
    {
        JFrame frame = new JFrame("Ajouter marque");
        JPanel panel = new JPanel(new GridLayout(3, 2));
        JLabel labelname = new JLabel("Nom de la marque");
        JTextField textFieldname = new JTextField();
        JButton buttonValider = new JButton("Valider");

        buttonValider.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                String Nom_marque = textFieldname.getText();

                if (Nom_marque != null)
                {
                    Reference.AjouterMarque(Nom_marque);
                    JOptionPane.showMessageDialog(frame, "Marque Ajouter avec succer.");
                    frame.dispose();
                    Reference.afficherListeMarque(contentPanel);
                    contentPanel.revalidate();
                    contentPanel.repaint();
                }
                else
                {
                    JOptionPane.showMessageDialog(frame, "Échec de la connexion. Veuillez réessayer.");
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
