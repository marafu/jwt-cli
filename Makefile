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