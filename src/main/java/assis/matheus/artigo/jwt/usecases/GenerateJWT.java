package assis.matheus.artigo.jwt.usecases;


import assis.matheus.artigo.jwt.Article;
import assis.matheus.artigo.jwt.interfaces.JSON;
import assis.matheus.artigo.jwt.interfaces.JWT;
import assis.matheus.artigo.jwt.interfaces.Logger;

public class GenerateJWT {
    private Logger logger;
    private JWT jwt;
    private JSON json;

    public GenerateJWT(JWT jwt, JSON json, Logger logger) {
        this.logger = logger;
        this.jwt = jwt;
        this.json = json;
    }
    
    public void execute(String squad, String title) {
        logger.info("Gerando token JWT");
        Article article = new Article(squad, title);

        logger.info(
            String.format(
                "Artigo %s criado pela squad %s", 
                article.getTitle(), 
                article.getSquad()
            )
        );
        
        String token = this.jwt.generate(this.json.create(article));
        
        logger.info("Token JWT gerado:");
        logger.info(token);
        logger.info("Recuperando a chave de assinatura:");
        logger.info(this.jwt.getSigningKey());
    }
}
