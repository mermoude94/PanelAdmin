package Model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MonPdo
{

    private Connection unPDO;

    public MonPdo()
    {
        try
        {
            String url = "jdbc:mysql://localhost/e5";
            String user = "root";
            String mdp = "";
            this.unPDO = DriverManager.getConnection(url, user, mdp);
        } catch (SQLException exp)
        {
            System.err.println("Erreur de connexion: " + exp.getMessage());
        }
    }

    public Connection getConnection()
    {
        return this.unPDO;
    }

    public void closeConnection()
    {
        try {
            if (this.unPDO != null)
            {
                this.unPDO.close();
            }
        } catch (SQLException exp)
        {
            System.err.println("Erreur lors de la fermeture de la connexion: " + exp.getMessage());
        }
    }

    public boolean verifierConnexion(String username, String password)
    {
        String sql = "SELECT * FROM user WHERE nom = ? AND mdp = ?";
        try (PreparedStatement statement = this.unPDO.prepareStatement(sql))
        {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // Vérifie si une ligne correspondante est trouvée dans la base de données
        } catch (SQLException exp) {
            System.err.println("Erreur lors de la vérification de la connexion: " + exp.getMessage());
            return false;
        }
    }
}
