# Jornada Milhas API
[![NPM](https://img.shields.io/npm/l/react)](https://github.com/lucasvir/jornadamilhas/blob/main/LICENSE)

# :scroll: Descrição

API Rest criada em Java 17 para uma aplicação web de viagens. Proposta elaborada pelo Challenge Back-End 7 da plataforma Alura.

Conta com gerenciamento e ciração de úsuarios, autenticação via JWT Token, sistema de criação de destinos, criação e randomização de comentários.

## :wrench: Funcionalidades

- *Login*
- *Autenticação JWT Token*
- *Crud de usuário*
- *Crud de destinos*
- *Crud de comentários*

## :toolbox: Tecnologias

- Java
- Spring Boot
- JPA / Hibernate
- Flyway
- MySQL
- JUnit
- Mockito
- Maven

## :construction: EXEC

```bash
# clonando repositório
git clone https://github.com/lucasvir/jornadamilhas.git

#acessar diretório
cd jornadamilhas

#inicializar aplicação setando as variáveis de ambiente
#o comando -Dspring.profiles.active=prod serve para ativar o perfil de produção, abilitando as variáveis de ambiente.
#DATASOURCE_URL: endereço do banco de dados
#DATASOURCE_USERNAME: nome do usuário do banco de dados
#DATASOURCE_PASSWORD: senha de acesso ao banco de dados

#exemplo:
java -Dspring.profiles.active=prod -DDATASOURCE_URL=jdbc:mysql://endereco.do.db/nomedodb -DDATASOURCE_USERNAME=nomedousuario -DDATASOURCE_PASSWORD=senhadousuario -jar target/api-0.0.1-SNAPSHOT.jar
```


<center> Lucas do Amaral Virmond - https://www.linkedin.com/in/lucasavirmond/ </center> 
