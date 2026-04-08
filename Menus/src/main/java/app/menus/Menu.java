package app.menus;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.*;

@Entity
@Table(name = "menus")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom;
    private int createurId;
    private String createurNom;
    private String dateCreation;
    private String dateMiseAJour;
    private double prixTotal;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "menu_plats_ids", joinColumns = @JoinColumn(name = "menu_id"))
    @Column(name = "plat_id")
    private List<Integer> platIds;

    @Transient
    private List<Plat> plats; // non persisté, enrichi à la volée

    public Menu() {}

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public int getCreateurId() { return createurId; }
    public void setCreateurId(int createurId) { this.createurId = createurId; }

    public String getCreateurNom() { return createurNom; }
    public void setCreateurNom(String createurNom) { this.createurNom = createurNom; }

    public String getDateCreation() { return dateCreation; }
    public void setDateCreation(String dateCreation) { this.dateCreation = dateCreation; }

    public String getDateMiseAJour() { return dateMiseAJour; }
    public void setDateMiseAJour(String dateMiseAJour) { this.dateMiseAJour = dateMiseAJour; }

    public List<Plat> getPlats() { return plats; }
    public void setPlats(List<Plat> plats) {
        this.plats = plats;
        if (plats != null) {
            this.platIds = plats.stream()
                    .map(Plat::getId)
                    .collect(Collectors.toList());
        }
    }

    public void setPlatIds(List<Integer> platIds) { this.platIds = platIds; }

    public double getPrixTotal() { return prixTotal; }
    public void setPrixTotal(double prixTotal) { this.prixTotal = prixTotal; }

    public List<Integer> getPlatIds() {
        return platIds;
    }
}