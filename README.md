<h1 align="left">SREALIZACAO - PRODUTIVIDADE</h1>

Srealização é uma  API para auxiliar na produtividade das atividades de produção diária,
visando manter o clareza, foco, motivação para evitar as distrações que nos distanciam dos objetivos

Este projeto surgiu a partir do curso Academia da Produtividade, que usa como ferramenta a
folha da produtividade, segue
o [link de acesso disponibilizado pelo autor](https://produtividadea.com.br/wp-content/uploads/2015/11/FolhadeProdutividadeA.pdf)

** Sistema desenvolvido em Java com o framework Spring.

| :placard: Vitrine.Dev |     |
| -------------  | --- |
| :sparkles: Nome        | **Srealizacao**
| :label: Tecnologias | java, springboot, spring-mvc, spring-data-jpa, pagination, specification, dto, exceptionHandler, hateoas e muito mais
| :rocket: URL         | https://localhost
| :fire: Base Projeto     | (https://produtividadea.com.br/wp-content/uploads/2015/11/FolhadeProdutividadeA.pdf)

<!-- Inserir imagem com a #vitrinedev ao final do link -->
![](https://jobsx.com.br/wp-content/uploads/2021/01/Capas-Blog-Janeiro-Jobs-X_Prancheta-1-1200x500.png?text=srealizacao#vitrinedev)

<!-- ############################################################################## --> 

# Detalhes do projeto

## Recursos

- Listagem de tarefas com o uso de paginação e especification: 
  - Os usuários podem visualizar todas as tarefas cadastradas, incluindo informações como título,
      descrição, prazo e status de conclusão.
- Cadastro de tarefas: Os usuários podem criar novas tarefas especificando o título, descrição, prazo e prioridade.
- Atualização de tarefas: Os usuários podem atualizar o status de conclusão das tarefas, marcar como concluídas ou
  reabrir tarefas já concluídas.
- Remoção de tarefas: Os usuários podem excluir tarefas que não são mais necessárias.

## Requisitos do Sistema

- Java 11 ou superior
- Spring Boot 2.5.0 ou superior
- Banco de dados relacional. Lembrando que o mesmo foi desenvolvido no MSSQL, no entanto por usar Spring Data JPA pode 
ser utilizado qualquer banco


## Configuração do Ambiente de Desenvolvimento

1. Clone este repositório: `git clone https://github.com/hvivox/srealizacao.git`
2. Navegue até o diretório do projeto: `cd srealizacao`
3. Importe o projeto em sua IDE de desenvolvimento preferida.
4. Configure as dependências do Maven.
5. Configure as informações de conexão com o banco de dados MySQL no arquivo `application.properties` e não esqueça de 
de acrescentar `spring.jpa.hibernate.ddl-auto= create` para criação do banco

## Instalação

1. Certifique-se de ter configurado corretamente o ambiente de desenvolvimento.
2. Execute o comando `mvn clean install` para baixar as dependências e construir o projeto.
3. Execute o comando `java -jar target/srealizacao.jar` para iniciar a aplicação.

## Uso

1. Acesse a aplicação através do PostMan, o endereço base é `http://localhost:8080`.
2. Você poderá criar, visualizar, atualizar e remover tarefas.
3. Explore os diferentes recursos da aplicação para gerenciar suas tarefas.

## Contribuição

Contribuições são bem-vindas! Se você quiser contribuir para este projeto, siga as etapas abaixo:

1. Faça um fork deste repositório.
2. Crie uma nova branch: `git checkout -b minha-branch`
3. Faça suas alterações e faça commit delas: `git commit -m 'Descrição das alterações'`
4. Envie para a branch original: `git push origin minha-branch`
5. Abra um pull request.

## Licença

Este projeto está licenciado sob a [MIT License](https://opensource.org/licenses/MIT).

