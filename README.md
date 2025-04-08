# 🔐 JWT CLI – Prova de Conceito para Artigo

Este projeto é uma **prova de conceito (PoC)** para um artigo, demonstrando a **geração e validação de JSON Web Tokens (JWT)** via linha de comando (CLI). Ele segue boas práticas de segurança e arquitetura modular.

## 📌 Requisitos

- **Java 21+**
- **Maven**
- **(Opcional)** `make` instalado para comandos simplificados

## 🚀 Como Compilar e Testar

### Usando Makefile

Se você tem `make` instalado, pode executar:

```sh
make all
```

Isso realizará a limpeza, compilação e execução dos testes automaticamente.

### Manualmente

Caso não tenha `make`, execute os comandos abaixo:

```sh
./mvnw clean package
./mvnw test
```

## 🔥 Executando a CLI

Após a compilação, você pode rodar:

```sh
java -jar target/jwt-cli-0.0.1.jar --help
```

## 🏗️ Estrutura do Projeto

📂 `factory` → Centraliza a criação de objetos JWT\
📂 `usecases` → Casos de uso como **geração e validação de tokens**\
📂 `interfaces` → Contratos para desacoplamento e flexibilidade\
📂 `implementations` → Implementações concretas (ex.: **JJWT, Gson**)\
📂 `exceptions` → Exceções específicas do domínio JWT\
📂 `valueobjects` → Objetos imutáveis encapsulando regras de negócio\
📂 `utils` → Utilitários como **logging**\
📂 `test/unitary` → Testes unitários cobrindo lógica crítica

## 🛠️ Tecnologias Utilizadas

- **Java 21**
- **JJWT** para manipulação de tokens
- **JUnit** para testes unitários
- **Maven** para gerenciamento de dependências
- **Gson** para serialização JSON
- **BouncyCastle** para suporte a criptografia

## ⚙️ Makefile

```make
# Variáveis
MVNW=./mvnw
JAR_TARGET=target/jwt-cli-0.0.1.jar

# Build do projeto
build:
	$(MVNW) clean package

# Executar os testes
test:
	$(MVNW) test

# Limpar artefatos gerados
clean:
	$(MVNW) clean

# Executar o JAR gerado
run:
	java -jar $(JAR_TARGET) --help

# Atalho para build + testes
all: clean build test
```

## 🏁 Como Usar

### Para imprimir o help

```sh
java -jar target/jwt-cli-0.0.1.jar 
```

ou

```sh
java -jar target/jwt-cli-0.0.1.jar --help
```

### Gerar um token JWT

```sh
java -jar target/jwt-cli-0.0.1.jar --generate --title "Exemplo" --squad "Security"
```

#### Exemplo de print de tela

```sh
java -jar target/jwt-cli-0.0.1.jar --generate --squad "github" --title "README.md"
[2025-04-08 13:56:44] [INFO]: Gerando token JWT
[2025-04-08 13:56:44] [INFO]: Artigo README.md criado pela squad github
[2025-04-08 13:56:45] [INFO]: Token JWT gerado:
[2025-04-08 13:56:45] [INFO]: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ7XCJ0aXRsZVwiOlwiUkVBRE1FLm1kXCIsXCJzcXVhZFwiOlwiZ2l0aHViXCJ9IiwiaXNzIjoiRGV2U2VjT3BzIiwiYXVkIjpbIkdydXBvQm90aWNhcmlvIiwiI2RldmVsb3BlcnMiLCIjc2VjdXJpdHktY2hhbXBpb25zIl0sImlhdCI6MTc0NDEzMTQwNCwiZXhwIjoxNzQ0MTM1MDA0LCJqdGkiOiJjNmQwN2YyMy0yY2FlLTQwM2YtYTBhOS0wOGM5M2Q4N2YwYWMifQ.YHOCxiIvgfwUzIcSdUntc8nc_r-Cu4z_IfFb3Ag8svs
[2025-04-08 13:56:45] [INFO]: Recuperando a secret:
[2025-04-08 13:56:45] [INFO]: YjA1YzE4NGMtNTFjZi00YjJiLWE2OWEtMTNiYzQ1OGEzYTg5 
```

> **Nota**: A secret é gerada dinâmicamente a cada geração, portanto, não existirá dois tokens JWT com a mesma secret!

### Validar um token JWT

```sh
java -jar target/jwt-cli-0.0.1.jar --validate --token "seu_token_aqui" --secret "secret"
```

### Exibir detalhes de um JWT

```sh
java -jar target/jwt-cli-0.0.1.jar --restore --token "seu_token_aqui" --secret "secret"
```

## 📜 Licença

Este projeto está sob a **licença MIT**. Sinta-se à vontade para contribuir! 🚀

## 📫 Contato

Se tiver dúvidas, sugestões ou quiser contribuir, entre em contato pelo GitHub!

