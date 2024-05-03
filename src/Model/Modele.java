package Model;

public class Modele
{
    private int Id_ref;
    private String Nom_ref;
    public Modele(int Id_ref, String Nom_ref)
    {
        this.Id_ref = Id_ref;
        this.Nom_ref = Nom_ref;
    }
    public int getId_ref()
    {
        return Id_ref;
    }
    public String getNom_ref()
    {
        return Nom_ref;
    }
}
