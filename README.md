<p align="center"><a href="https://laravel.com" target="_blank"><img src="https://raw.githubusercontent.com/laravel/art/master/logo-lockup/5%20SVG/2%20CMYK/1%20Full%20Color/laravel-logolockup-cmyk-red.svg" width="400"></a></p>

<p align="center">
<a href="https://travis-ci.org/laravel/framework"><img src="https://travis-ci.org/laravel/framework.svg" alt="Build Status"></a>
<a href="https://packagist.org/packages/laravel/framework"><img src="https://img.shields.io/packagist/dt/laravel/framework" alt="Total Downloads"></a>
<a href="https://packagist.org/packages/laravel/framework"><img src="https://img.shields.io/packagist/v/laravel/framework" alt="Latest Stable Version"></a>
<a href="https://packagist.org/packages/laravel/framework"><img src="https://img.shields.io/packagist/l/laravel/framework" alt="License"></a>
</p>

# srealizacao

Srealização é um sistema para auxiliar na produtividade das atividades de produção diária,
visando manter o clareza, foco, motivação para evitar as distrações que nos distanciam dos objetivos

Este projeto surgiu a partir do curso Academia da Produtividade, que usa como ferramenta a
folha da produtividade, segue
o [link de acesso disponibilizado pelo autor](https://produtividadea.com.br/wp-content/uploads/2015/11/FolhadeProdutividadeA.pdf)

## Descrição

Este repositório contém o projeto "srealizacao", que é uma aplicação de controle de realização de tarefas desenvolvida
em Java com o framework Spring. A aplicação permite aos usuários gerenciar suas tarefas e acompanhar o progresso de sua
realização.

## Recursos

- Cadastro de tarefas: Os usuários podem criar novas tarefas especificando o título, descrição, prazo e prioridade.
- Listagem de tarefas: Os usuários podem visualizar todas as tarefas cadastradas, incluindo informações como título,
  descrição, prazo e status de conclusão.
- Atualização de tarefas: Os usuários podem atualizar o status de conclusão das tarefas, marcar como concluídas ou
  reabrir tarefas já concluídas.
- Remoção de tarefas: Os usuários podem excluir tarefas que não são mais necessárias.

## Requisitos do Sistema

- Java 11 ou superior
- Spring Boot 2.5.0 ou superior
- Banco de dados relacional. Lembrando que o mesmo foi desenvolvido no MSSQL, no entanto por usar Spring Data JPA pode 
ser utilizado qualquer banco
- 

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

1. Acesse a aplicação através do navegador web no endereço `http://localhost:8080`.
2. Na página inicial, você poderá criar, visualizar, atualizar e remover tarefas.
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

## Sobre o SRealização

Sistema de cadastro de inscrições do maior evento ciclistico da região norte

## Comandos para iniciar o projeto

* composer install
* npm install
* editar o arquivo .env com os dados corretos
* rodar o comando php artisan key:generate

## Antes de por em produção deve-se rodar o comando para compilar fonts do vuejs

* npm run watch
* npm run build (este comando depende do npm run watch rodando)
* desabilitar APP_DEBUG=true do .env
* Rodar o comando php artisan config:clear sempre ao alterar o env

## Executar a aplicação em desenvolvimento

* php artisan serve
* npm run watch (alguns momentos é necessário sair e entrar nesse comando para recompilar corretamente)

## License

The Laravel framework is open-sourced software licensed under the [MIT license](https://opensource.org/licenses/MIT).
