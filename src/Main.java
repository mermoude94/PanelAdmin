import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import Model.MonPdo;
import Vue.Vue_Acceuil;

public class Main
{

    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Page de Connexion");
        JPanel panel = new JPanel(new GridLayout(3, 2));
        JLabel labelUsername = new JLabel("Nom d'utilisateur:");
        JTextField textFieldUsername = new JTextField();
        JLabel labelPassword = new JLabel("Mot de passe:");
        JPasswordField passwordField = new JPasswordField();
        JButton buttonLogin = new JButton("Connexion");

        buttonLogin.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                String username = textFieldUsername.getText();
                String password = new String(passwordField.getPassword());

                MonPdo monPdo = new MonPdo();
                if (monPdo.verifierConnexion(username, password))
                {
                    JOptionPane.showMessageDialog(frame, "Connexion réussie !");
                    frame.dispose();
                    Vue_Acceuil vueAcceuil = new Vue_Acceuil();
                    vueAcceuil.setVisible(true);
                }
                else
                {
                    JOptionPane.showMessageDialog(frame, "Échec de la connexion. Veuillez réessayer.");
                }
                monPdo.closeConnection();
            }
        });

        panel.add(labelUsername);
        panel.add(textFieldUsername);
        panel.add(labelPassword);
        panel.add(passwordField);
        panel.add(new JLabel());
        panel.add(buttonLogin);

        frame.add(panel);
        frame.setSize(1280, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
