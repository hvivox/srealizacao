## STAGE 01 -  BUILD
# Use a imagem oficial do Gradle com JDK 21 como imagem base para a etapa de build
FROM maven:3.9.6-eclipse-temurin-21 AS builder-app

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia os arquivos de dependência primeiro para cache mais eficiente
COPY pom.xml ./
COPY src ./src

# Executa o Maven para limpar e gerar o JAR
RUN mvn clean package -DskipTests



## STAGE 02 -  RUN APP
# Use a imagem Eclipse Temurin JRE 21 como imagem base para execução
FROM eclipse-temurin:21-jre-jammy

# Define uma variável de ambiente para o ambiente de execução da aplicação.
# Quando não especificado, o padrão será 'dev'.
ARG ENVIRONMENT=homolog

# Define uma variável de ambiente com o nome do JAR e defini o tipo do profile ativo
ENV JAR_NAME=srealizacao-backend.jar \
    SPRING_PROFILES_ACTIVE=$ENVIRONMENT \
    TZ=America/Sao_Paulo


# Define o diretório de trabalho no container
WORKDIR /app

# Copia o arquivo JAR gerado na etapa de build
COPY --from=builder-app /app/target/$JAR_NAME .

# Comando para iniciar a aplicação Java quando o container for executado
CMD java -Dlogging.level.root=INFO -Xmx512m -jar "$JAR_NAME"