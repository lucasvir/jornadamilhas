# Jornada Milhas API
[![NPM](https://img.shields.io/npm/l/react)](https://github.com/lucasvir/jornadamilhas/blob/main/LICENSE)

✔️ https://lucasvir.github.io/jornadamilhas/

# :scroll: Descrição

API Rest criada em Java 17 para uma aplicação web de viagens. Proposta elaborada pelo Challenge Back-End 7 da plataforma Alura.

Conta com gerenciamento e ciração de úsuarios, autenticação via JWT Token, sistema de criação de destinos com gerador de descrição via API do chatgpt, criação e randomização de comentários.

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
- Postgres
- JUnit
- Mockito
- Maven

## :construction: EXEC

> Pré-requisitos:
> - instalar o Java ([Oracle](https://www.oracle.com/java/technologies/downloads/)).
> - instalar banco de dados Postgres ([Postgres](https://www.postgresql.org/download/)).
> - instalar o Maven (build) ([Maven](https://maven.apache.org/install.html)).

<br />

Clonar repositório
```bash
git clone https://github.com/lucasvir/jornadamilhas.git
```

Acessar diretório
```bash
cd jornadamilhas
```

Criar banco de dados para persistência e para testes
```bash
sudo -u postgres psql
CREATE DATABASE <nomedb>;
CREATE DATABASE test_db;
ALTER DATABASE <nomedb> OWNER TO <nomeusuario>
ALTER DATABASE test_db OWNER TO <nomeusuario>;
```

Fazer o build
```bash
mvn verify
```

Inicializar aplicação setando as variáveis de ambiente
> DATASOURCE_URL: endereço do banco de dados <br />
> DATASOURCE_USERNAME: nome do usuário do banco de dados <br />
> DATASOURCE_PASSWORD: senha de acesso ao banco de dados <br />
> JWT_SECRET: secret para api de geração dos token <br />

*exemplo:*
```bash
java -DJWT_SECRET=$2a$12$5H0JqHT2eZJdIauXvxIMOuNjeCHKHRMDQLFztQAeGQs5eAGCANzje -DDATASOURCE_URL=jdbc:postgresql://localhost:5432/<nomedb> -DDATASOURCE_USERNAME=<nomeusuario> -DDATASOURCE_PASSWORD=<senhausuario> -jar target/api-0.0.1-SNAPSHOT.jar
```
