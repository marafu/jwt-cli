package assis.matheus.artigo.jwt;

import assis.matheus.artigo.jwt.valueobjects.Squad;
import assis.matheus.artigo.jwt.valueobjects.Title;

public class Article {
    private transient Squad transientSquad;
    private transient Title transientTitle;

    private String title;
    private String squad;

    public Article(String squad, String title) {
        this.transientSquad = new Squad(squad);
        this.transientTitle = new Title(title);
        this.squad = this.transientSquad.getValue();
        this.title = this.transientTitle.getValue();
    }

    public String getSquad() {
        return this.squad;
    }

    public String getTitle() {
        return this.title;
    }
}