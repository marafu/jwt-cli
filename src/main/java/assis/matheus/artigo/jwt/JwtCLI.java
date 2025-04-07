package assis.matheus.artigo.jwt;

import assis.matheus.artigo.jwt.exceptions.AuthenticationException;
import assis.matheus.artigo.jwt.factory.JwtFactory;
import assis.matheus.artigo.jwt.implementations.GsonJSON;
import assis.matheus.artigo.jwt.interfaces.JSON;
import assis.matheus.artigo.jwt.interfaces.JWT;
import assis.matheus.artigo.jwt.interfaces.Logger;
import assis.matheus.artigo.jwt.usecases.GenerateJWT;
import assis.matheus.artigo.jwt.utils.SoutLogger;

public class JwtCLI {
    public static void main(String[] args) {
        Logger logger = new SoutLogger();

        // Garantir que argumentos foram fornecidos
        if (args.length == 0 || args[0].equals("--help")) {
            printHelp();
            return;
        }

        try {
            switch (args[0]) {
                case "--generate":
                    generate(args);
                    break;
                case "--validate":
                    validate(args);
                    break;
                case "--restore":
                    restore(args);
                    break;
                default:
                    logger.error("Comando inválido. Use '--help' para listar os comandos disponíveis.", null);
            }
        } catch (Exception e) {
            logger.error("Erro ao executar o comando.", e);
        }
    }

    private static void generate(String[] args) {
        Logger logger = new SoutLogger();
        
        if (args.length < 5 || !args[1].equals("--squad") || !args[3].equals("--title")) {
            logger.error("Uso inválido de '--generate'. Use '--help' para obter mais informações.", null);
            return;
        }

        String squad  = args[2];
        String title  = args[4];
        
        JWT jwt = new JwtFactory().getGenerateJWTFactory();
        JSON json = new GsonJSON();

        new GenerateJWT(jwt, json, logger).execute(squad, title);
    }

    private static void validate(String[] args) {
        Logger logger = new SoutLogger();
        
        if (args.length < 3 || !args[1].equals("--token") || !args[3].equals("--secret")) {
            logger.error("Uso inválido de '--validate'. Use '--help' para obter mais informações.", null);
            return;
        }
        
        
        String token  = args[2];
        String secret = args[4];

        logger.debug(token);
        logger.debug(secret);
        
        try {
            JWT jwt = new JwtFactory().getValidateJWTFactory(secret);
            jwt.validate(token);
            logger.info("O token é válido");
        } catch (AuthenticationException e) {
            logger.info("O token não válido.");
            return;
        } catch (Exception e) {
            logger.error("Problema em validar seu token", e);
        }
    }

    private static void restore(String[] args) {
        Logger logger = new SoutLogger();

        if (args.length < 3 || !args[1].equals("--token") || !args[3].equals("--secret")) {
            logger.error("Uso inválido de '--restore'. Use '--help' para obter mais informações.", null);
            return;
        }

        String token  = args[2];
        String secret = args[4];

        try {
            JWT jwt = new JwtFactory().getValidateJWTFactory(secret);
            String payload = jwt.restore(token);
            logger.info("Payload restaurado:");
            logger.info(payload);
        } catch (Exception e) {
            logger.error("Erro ao restaurar o payload do token.", e);
        }
    }

    private static void printHelp() {
        System.out.println();
        System.out.println("Uso: java -jar jwt-cli.jar [comando] [opcoes]");
        System.out.println();
        System.out.println("Comandos disponíveis:");
        System.out.println("Nota: as secrets de todos os token serão usadas com o mesmo token (SIM EU SEI, É INSEGURO!) e com tempo de expiração de 1h");
        System.out.println("--generate --squad [SQUAD] --title [TITLE]                          Gera um JWT com o payload fornecido em formato JSON");
        System.out.println("--validate --token [TOKEN] --secret [OPCIONAL]                      Valida um JWT fornecido");
        System.out.println("--restore --token [TOKEN] --secret [OPCIONAL]                       Restaura o payload de um JWT");
        System.out.println("--help                                                              Exibe esta ajuda");
    }
}
