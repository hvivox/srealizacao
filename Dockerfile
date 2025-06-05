# Usa a imagem base Alpine Linux versão 3.21 (leve e minimalista)
FROM alpine:3.21

# Instala o JRE do OpenJDK 21 sem cache (necessário para rodar aplicações Java)
RUN apk add --no-cache openjdk21-jre

# Copia o arquivo JAR gerado da aplicação para o diretório /app dentro do container
COPY target/srealizacao.jar /app/srealizacao.jar

# Comando para iniciar a aplicação Java quando o container for executado
CMD java -jar /app/srealizacao.jar