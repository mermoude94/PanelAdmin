package Vue;

import Model.*;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;

public class VueUnModele
{
    public static void afficherModele(int Id_ref)
    {
        Montre montre = Reference.getReferenceById(Id_ref);
        JFrame frame = new JFrame("Reference Modele: " + Id_ref);
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
            addLabel(panel, gbc, "ID du modele : " + montre.getNom_modele());

        }
        else
        {
            addLabel(panel, gbc, "Aucune Montre trouvée : " + Id_ref);
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
