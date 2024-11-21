# Cabeleleira Leila

Este projeto consiste em uma aplicação para gerenciamento de um salão de beleza, desenvolvida como parte de um desafio técnico para a empresa DSIN Tecnologia da Informação. O sistema inclui um backend para gerenciamento de dados e uma aplicação frontend para interação do usuário.

## Tecnologias Utilizadas

### Backend
- **Java com Spring Boot**: Framework para construção de APIs e gerenciamento de lógica de negócios.
- **PostgreSQL**: Banco de dados relacional.
- **Docker**: Para containerização e gerenciamento do ambiente de execução.
- **Spring Data JPA**: Integração com o banco de dados.
- **Spring Security com JWT**: Controle de autenticação e autorização.
- **Lombok**: Para simplificar a escrita do código.
- **ModelMapper**: Para mapeamento de objetos.

### Frontend
- **Angular**: Framework para desenvolvimento frontend.
- **TypeScript**: Linguagem tipada para desenvolvimento frontend.
- **TailwindCSS**: Framework de CSS utilitário para estilização.
- **date-fns**: Biblioteca para manipulação de datas.


## Pré-requisitos
Certifique-se de ter instalado:

- #### Docker e Docker Compose.
- #### Java >= 17.
- #### Node.js >= 20.x.
- #### Angular CLI >= 18.x.

## Instalação e Execução

### Clonar o Repositório

```bash
git clone https://github.com/brunodias77/cabeleleila-dsin.git
cd cabeleleila-dsin
```

### Executar Backend

- Acesse o diretório do backend:
```bash
cd dsin-cabeleleila-server
```
- Execute o serviço usando Docker
```bash
docker-compose up -d
```
- Rode o backend para criar as tabelas no banco de dados.
- Execute as queries sql que estão no arquivo seeds.sql para a criação dos usuários leila e bruno e dos serviços do salão.
### Executar Frontend
 - Acesse o diretório do frontend:
```bash
cd cabeleleila-leila
```
- Execute o comando npm install para instalar todos os pacotes necessarios para a aplicação.
```bash
npm install
```
- Inicie a aplicação com o comando:
```bash
ng s
```
- Acesse a aplicação em: http://localhost:4200

## Usuários Padrão
### Apos rodas as queries sql estaram disponiveis esses usuarios
### Administrador
- #### E-mail: `leila@admin.com`
- #### Senha: `123456`

### Client
- #### E-mail: `bruno@user.com`
- #### Senha: `123456`

## Funcionalidades

### Backend

- Endpoints protegidos por autenticação JWT.
- Rotas para gerenciamento de agendamentos, serviços e usuários.
- Suporte para:
  - CRUD de agendamentos.
  - Cadastro e autenticação de usuários.
  - Criação de novos serviços.
  - Gestão administrativa de agendamentos (confirmação, cancelamento e atualização).
  - Performance semanal de agendamentos.

### Frontend
- Sistema de rotas dinâmicas com Angular.
- Interface responsiva e estilizada com TailwindCSS.
- Gerenciamento de estados utilizando serviços Angular.
- Manipulação e exibição de dados do calendário de agendamentos.
- Formulários dinâmicos e validações reativas.


