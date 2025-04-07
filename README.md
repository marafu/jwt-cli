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

### Gerar um token JWT

```sh
java -jar target/jwt-cli-0.0.1.jar generate --title "Exemplo" --squad "Security"
```

### Validar um token JWT

```sh
java -jar target/jwt-cli-0.0.1.jar validate --token "seu_token_aqui"
```

### Exibir detalhes de um JWT sem validá-lo

```sh
java -jar target/jwt-cli-0.0.1.jar decode --token "seu_token_aqui"
```

## 📜 Licença

Este projeto está sob a **licença MIT**. Sinta-se à vontade para contribuir! 🚀

## 📫 Contato

Se tiver dúvidas, sugestões ou quiser contribuir, entre em contato pelo GitHub!

