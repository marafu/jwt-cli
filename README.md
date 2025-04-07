# JWT CLI

Este projeto é uma ferramenta de linha de comando (CLI) para manipulação de JSON Web Tokens (JWT).

## 📌 Requisitos
- Java 21+
- Maven

## 🚀 Como compilar e testar

### Usando Makefile
Se você tiver `make` instalado, pode simplesmente executar:
```sh
make all
```
Isso fará a limpeza, compilação e testes automaticamente.

### Manualmente
Caso não tenha `make`, execute os seguintes comandos:
```sh
./mvnw clean package
./mvnw test
```

## 🔥 Executando a CLI
Após compilar, execute:
```sh
java -jar target/jwt-cli-0.0.1.jar --help
```

## 🛠 Tecnologias utilizadas
- [JJWT](https://github.com/jwtk/jjwt)
- [Nimbus JOSE+JWT](https://connect2id.com/products/nimbus-jose-jwt)
- [BouncyCastle](https://www.bouncycastle.org/)
- [Gson](https://github.com/google/gson)

## 📖 Sobre JWT
JSON Web Token (JWT) é um padrão aberto (RFC 7519) que define um formato compacto e seguro para transmissão de informações entre partes como um objeto JSON. O JWT é amplamente utilizado para autenticação e troca de informações de maneira segura, podendo ser assinado digitalmente ou criptografado.

### 📌 Estrutura de um JWT
Um JWT é composto por três partes separadas por pontos (`.`):
1. **Header** (Cabeçalho): Contém metadados sobre o token, como o tipo de algoritmo de assinatura.
2. **Payload** (Corpo): Contém as informações (claims) que serão transmitidas.
3. **Signature** (Assinatura): Usada para verificar a autenticidade do token.

Exemplo de JWT:
```sh
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
```

## 📜 Licença
Este projeto está sob a licença MIT. Veja o arquivo `LICENSE` para mais detalhes.

# jwt-cli
