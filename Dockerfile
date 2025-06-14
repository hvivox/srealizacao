# Usa a imagem base Alpine Linux versão 3.21 (leve e minimalista)
FROM alpine:3.21

# Instala o JRE do OpenJDK 21 sem cache (necessário para rodar aplicações Java)
RUN apk add --no-cache openjdk21-jre

# Define uma variável de ambiente com o nome do JAR a ser executado
ENV JAR_NAME=srealizacao-backend.jar

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia o arquivo JAR gerado da aplicação para o diretório /WORKDIR/$JAR_NAME  dentro do container
COPY target/$JAR_NAME .

# Comando para iniciar a aplicação Java quando o container for executado
CMD java -Dlogging.level.root=INFO -Xmx512m -jar "$JAR_NAME"