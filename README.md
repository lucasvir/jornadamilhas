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


```bash

 # clonando repositório
git clone https://github.com/lucasvir/jornadamilhas.git

```

```bash
#acessar diretório
cd jornadamilhas
```

```bash
#inicializar aplicação setando as variáveis de ambiente
#DATASOURCE_URL: endereço do banco de dados
#DATASOURCE_USERNAME: nome do usuário do banco de dados
#DATASOURCE_PASSWORD: senha de acesso ao banco de dados
#JWT_SECRET: secret para api de geração dos token

#exemplo:
java -DJWT_SECRET=senhaparaapitoken -DDATASOURCE_URL=jdbc:mysql://endereco.do.db/nomedodb -DDATASOURCE_USERNAME=nomedousuario -DDATASOURCE_PASSWORD=senhadousuario -jar target/api-0.0.1-SNAPSHOT.jar
```

<center> Lucas do Amaral Virmond - https://www.linkedin.com/in/lucasavirmond/ </center> 
