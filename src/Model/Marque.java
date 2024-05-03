package Model;

public class Marque
{
    private int Id_marque;
    private String Nom_marque;


    public Marque(int Id_marque, String Nom_marque)
    {
        this.Id_marque = Id_marque;
        this.Nom_marque = Nom_marque;
    }
    public int getId_marque()
    {
        return Id_marque;
    }
    public String getNom_marque()
    {
        return Nom_marque;
    }
}
