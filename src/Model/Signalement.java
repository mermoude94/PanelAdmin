package Model;

public class Signalement
{
    private String Id_signalement;
    private String description;
    private String Id_annonce;
    private String nomPlaignant;
    private String prenomPlaignant;
    private  String prenomSignaler;
    private String nomSignaler;

    public Signalement(String Id_signalement, String description, String Id_annonce, String nomPlaignant, String prenomPlaignant, String prenomSignaler, String nomSignaler)
    {
        this.Id_signalement = Id_signalement;
        this.description = description;
        this.Id_annonce = Id_annonce;
        this.nomPlaignant = nomPlaignant;
        this.prenomPlaignant = prenomPlaignant;
        this.prenomSignaler = prenomSignaler;
    }

    public String getId_signalement()
    {
        return Id_signalement;
    }

    public String getdDescription()
    {
        return description;
    }

    public String getId_annonce()
    {
        return Id_annonce;
    }
    public String getPrenomPlaignant()
    {
        return prenomPlaignant;
    }
    public String getNomPlaignant()
    {
        return nomPlaignant;
    }

    public String getNomSignaler()
    {
        return prenomPlaignant;
    }

    public String getPrenomSignaler()
    {
        return prenomSignaler;
    }
}
