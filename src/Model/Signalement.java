package Model;

public class Signalement
{
    private int Id_signalement;
    private String motif;
    private int Id_annonce;
    private String nomPlaignant;
    private String prenomPlaignant;
    private String prenomSignaler;
    private String nomSignaler;

    public Signalement(int Id_signalement,
                       String motif,
                       int Id_annonce,
                       String nomPlaignant,
                       String prenomPlaignant,
                       String prenomSignaler,
                       String nomSignaler)
    {
        this.Id_signalement = Id_signalement;
        this.motif = motif;
        this.Id_annonce = Id_annonce;
        this.nomPlaignant = nomPlaignant;
        this.prenomPlaignant = prenomPlaignant;
        this.prenomSignaler = prenomSignaler;
        this.nomSignaler = nomSignaler;
    }

    public int getId_signalement()
    {
        return Id_signalement;
    }
    public String getmotif()
    {
        return motif;
    }
    public int getId_annonce()
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
    public String getPrenomSignaler()
{
    return prenomSignaler;
}
    public String getNomSignaler()
    {
        return nomSignaler;
    }

}
