package plantProject.fr;

public class Plante {
    public String nom;
    public double humiditeMoins;
    public double humiditePlus;
    public double temperatureMoins;
    public double temperaturePlus;
    public double luminositeMoins;
    public double luminositePlus;

    public Plante(String nom, double humiditeMoins, double humiditePlus, double temperatureMoins, double temperaturePlus, double luminositeMoins, double luminositePlus) {
        this.nom = nom;
        this.humiditeMoins = humiditeMoins;
        this.humiditePlus = humiditePlus;
        this.temperatureMoins = temperatureMoins;
        this.temperaturePlus = temperaturePlus;
        this.luminositeMoins = luminositeMoins;
        this.luminositePlus = luminositePlus;
    }
    public Plante(){}

}
