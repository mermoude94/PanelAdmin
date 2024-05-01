package Model;

public class Utilisateur 
{
    private String nom;
    private String prenom;
    private String email;
    private String adresse;

    public Utilisateur(String nom, String prenom, String email, String adresse)
    {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.adresse = adresse;
    }

    public String getNom()
    {
        return nom;
    }

    public String getPrenom()
    {
        return prenom;
    }

    public String getEmail()
    {
        return email;
    }

    public String getAdresse()
    {
        return adresse;
    }
}
