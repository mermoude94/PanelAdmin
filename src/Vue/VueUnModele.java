package Vue;

import Model.*;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;

import static Vue.Vue_Acceuil.contentPanel;

public class VueUnModele
{
    public static void afficherModele(int Id_reference)
    {
        Montre montre = Reference.getReferenceById(Id_reference);
        JFrame frame = new JFrame("Reference Modele: " + Id_reference);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        if (montre != null)
        {
            addLabel(panel, gbc, "ID de la reference: " + montre.getId_reference());
            addLabel(panel, gbc, "Nom modele : " + montre.getNom_modele());
            addLabel(panel, gbc, "Marque : " + montre.getNom_marque());

        }
        else
        {
            addLabel(panel, gbc, "Aucune Montre trouvée : " + Id_reference);
        }
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton button1 = new JButton("Supprimer");
        buttonPanel.add(button1);
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
                int Id_reference = montre.getId_reference();
                Suppression.SupprimerUnModele(Id_reference);
                frame.dispose();
                Reference.afficherListeModele(contentPanel);
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
