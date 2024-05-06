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
        }
        catch (SQLException exp)
        {
            System.err.println("Erreur de connexion: " + exp.getMessage());
        }
    }
    public void closeConnection()
    {
        try
        {
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
            return resultSet.next();
        }
        catch (SQLException exp)
        {
            System.err.println("Erreur lors de la vérification de la connexion: " + exp.getMessage());
            return false;
        }
    }

    public ResultSet executerRequete(String sql) throws SQLException
    {
        ResultSet resultSet = null;
        try
        {
            PreparedStatement statement = this.unPDO.prepareStatement(sql);
            resultSet = statement.executeQuery();
        }
        catch (SQLException exp)
        {
            System.err.println("Erreur lors de l'exécution de la requête: " + exp.getMessage());
            if (resultSet != null)
            {
                resultSet.close();
            }
            throw exp;
        }
        return resultSet;
    }
    public int executerUneRequete(String sql, Object... params) throws SQLException
    {
        try (PreparedStatement statement = this.unPDO.prepareStatement(sql))
        {
            for (int i = 0; i < params.length; i++)
            {
                statement.setObject(i + 1, params[i]);
            }
            statement.executeUpdate();
            return (1);
        }
        catch (SQLException exp)
        {
            System.err.println("Erreur lors de l'exécution de la requête: " + exp.getMessage());
            throw exp;
        }
    }


}
