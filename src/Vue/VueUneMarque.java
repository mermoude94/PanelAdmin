package Vue;

import Model.*;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;

import static Vue.Vue_Acceuil.contentPanel;

public class VueUneMarque
{
    public static void afficherMarque(int Id_marque)
    {
        Marque marque = Reference.getMarqueById(Id_marque);
        JFrame frame = new JFrame("Marque : "+ Id_marque);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        if (marque != null)
        {
            addLabel(panel, gbc, "Id Marque : " + marque.getId_marque());
            addLabel(panel, gbc, "Marque : " + marque.getNom_marque());
        }
        else
        {
            addLabel(panel, gbc, "Aucune Marque trouvée : " + Id_marque);
        }
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton button1 = new JButton("Supprimer");
        JButton button2 = new JButton("Ajouter Marque");
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
                int Id_marque = marque.getId_marque();
                Suppression.SupprimerUneMarque(Id_marque);
                frame.dispose();
                Reference.afficherListeMarque(contentPanel);
                contentPanel.revalidate();
                contentPanel.repaint();
            }
        });
        button2.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
                {
                    VueAjouterMarque.AjouterUneMarque();
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
