package Model;

public class Annonce
{
    private String iduser;
    private String Id_annonce;
    private String Id_marque;
    private String Id_ref;
    private String Prix;
    private String Id_photo;
    private String description;
    private String nom_marque;
    private String nom_ref;
    private String nom_user;
    private String prenom_user;
    private String nom_fichier;

    public Annonce(String Id_annonce, String Id_ref, String Id_marque, String Prix, String Id_photo, String description,String nom_marque, String iduser, String nom_ref, String nom_user, String prenom_user, String nom_fichier)
    {
        this.iduser = iduser;
        this.Id_annonce = Id_annonce;
        this.Id_marque = Id_marque;
        this.Id_ref = Id_ref;
        this.Prix = Prix;
        this.Id_photo = Id_photo;
        this.description = description;
        this.nom_marque = nom_marque;
        this.nom_ref = nom_ref;
        this.nom_user = nom_user;
        this.prenom_user = prenom_user;
        this.nom_fichier = nom_fichier;

    }
    public String getIduser()
    {
        return iduser;
    }
    public String getId_annonce()
    {
        return Id_annonce;
    }
    public String getId_marque()
    {
        return Id_marque;
    }
    public String getId_ref()
    {
        return Id_ref;
    }
    public String getPrix()
    {
        return Prix;
    }
    public String getId_photo()
    {
        return Id_photo;
    }
    public String getDescription()
    {
        return description;
    }
    public String getNom_marque()
    {
        return nom_marque;
    }
    public String getNom_ref()
    {
        return nom_ref;
    }
    public String getNom_user()
    {
        return nom_user;
    }
    public String getPrenom_user()
    {
        return prenom_user;
    }
    public String getNom_fichier()
    {
        return nom_fichier;
    }

}
