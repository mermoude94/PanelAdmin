package Model;

public class Montre
{
    private int Id_reference;
    private String Nom_modele;
    private String Nom_marque;



    public Montre(int Id_reference, String Nom_modele,String Nom_marque)
    {
        this.Id_reference = Id_reference;
        this.Nom_modele = Nom_modele;
        this.Nom_marque = Nom_marque;
    }
    public  int getId_reference()
    {
        return Id_reference;
    }
    public  String getNom_modele()
    {
        return Nom_modele;
    }
    public String getNom_marque()
    {
        return Nom_marque;
    }
}
