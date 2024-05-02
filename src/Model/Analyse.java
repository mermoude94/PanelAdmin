package Model;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Analyse
{
    public static ArrayList<Signalement> DonneeSignalements()
    {
        MonPdo monPdo = new MonPdo();
        ArrayList<Signalement> signalements = new ArrayList<>();
        try
        {
            ResultSet resultSet = monPdo.executerRequete(
                        "SELECT \n" +
                            "    s.Id_signalement,\n" +
                            "    s.description,\n" +
                            "    s.Id_annonce,\n" +
                            "    plaignant.nom AS nom_plaignant,\n" +
                            "    plaignant.prenom AS prenom_plaignant,\n" +
                            "    signaler.nom AS nom_signaler,\n" +
                            "    signaler.prenom AS prenom_signaler\n" +
                            "FROM \n" +
                            "    signalement AS s\n" +
                            "JOIN \n" +
                            "    user AS plaignant ON s.idplaignant = plaignant.iduser\n" +
                            "JOIN \n" +
                            "    user AS signaler ON s.idsignaler = signaler.iduser;");
            while (resultSet.next())
            {
                String idSignalement = resultSet.getString("Id_signalement");
                String description = resultSet.getString("description");
                String idAnnonce = resultSet.getString("Id_annonce");
                String nomPlaignant = resultSet.getString("nom_plaignant");
                String prenomPlaignant = resultSet.getString("prenom_plaignant");
                String nomSignaler = resultSet.getString("nom_signaler");
                String prenomSignaler = resultSet.getString("prenom_signaler");
                signalements.add(new Signalement(idSignalement, description, idAnnonce, nomPlaignant, prenomPlaignant, nomSignaler, prenomSignaler));
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return signalements;
    }

    public static void afficherListeSignalement(JPanel panel)
    {
        ArrayList<Signalement> signalements = Analyse.DonneeSignalements();

        Object[][] data = new Object[signalements.size()][7];

        for (int i = 0; i < signalements.size(); i++)
        {
            Signalement signalement = signalements.get(i);
            data[i] = new Object[]
                    {
                        false,
                            signalement.getId_signalement(),
                            signalement.getdDescription(),
                            signalement.getId_annonce(),
                            signalement.getNomPlaignant(),
                            signalement.getPrenomPlaignant(),
                            signalement.getNomSignaler(),
                            signalement.getPrenomSignaler()
                    };
        }

        String[] columnNames =
                {
                        "Sélection",
                        "ID Signalement",
                        "Description",
                        "ID Annonce",
                        "Nom plaignant",
                        "Prenom plaignant",
                        "Nom signaler",
                        "Prenom signaler"
                };

        DefaultTableModel model = new DefaultTableModel(data, columnNames)
        {
            @Override
            public Class<?> getColumnClass(int column)
            {
                if (column == 0)
                {
                    return Boolean.class;
                }
                return super.getColumnClass(column);
            }
        };

        JTable table = new JTable(model);

        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        JScrollPane scrollPane = new JScrollPane(table);

        panel.removeAll();
        panel.setLayout(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.revalidate();
        panel.repaint();
    }


}
