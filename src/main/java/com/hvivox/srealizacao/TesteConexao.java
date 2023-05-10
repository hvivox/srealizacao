package com.hvivox.srealizacao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

//@SpringBootApplication
public class TesteConexao {

    public static void main(String[] args) throws SQLException {

        ConfigurableApplicationContext context = SpringApplication.run(TesteConexao.class, args);

        DataSource dataSource = context.getBean(DataSource.class);
        Connection conexao = dataSource.getConnection();

        if (conexao != null) {
            System.out.println("Conexão estabelecida com sucesso!");
            conexao.close();
        } else {
            System.out.println("Erro ao conectar com o banco de dados.");
        }

        context.close();
    }
}