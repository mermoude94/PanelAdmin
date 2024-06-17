package Model;

public class Modele
{
    private int Id_ref;
    private String Nom_ref;
    private int Id_marque;
    public Modele(int Id_ref, String Nom_ref, int Id_marque)
    {
        this.Id_ref = Id_ref;
        this.Nom_ref = Nom_ref;
        this.Id_marque = Id_marque;
    }
    public int getId_ref()
    {
        return Id_ref;
    }
    public String getNom_ref()
    {
        return Nom_ref;
    }
    public int getId_marque()
    {
        return Id_marque;
    }
}
