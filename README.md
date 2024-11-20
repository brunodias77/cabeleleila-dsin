# Cabeleleira Leila

Este projeto consiste em uma aplicação fullstack para gerenciamento de um salão de beleza, desenvolvida como parte de um desafio técnico. O sistema inclui um backend para gerenciamento de dados e uma aplicação frontend para interação do usuário.

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

## Funcionalidades

### Backend

- Endpoints protegidos por autenticação JWT.
- Rotas para gerenciamento de agendamentos, serviços e usuários.
- Suporte para:
  - CRUD de agendamentos.
  - Cadastro e autenticação de usuários.
  - Criação de novos serviços.
  - Gestão administrativa de agendamentos (confirmação, cancelamento e atualização).

### Frontend
- Sistema de rotas dinâmicas com Angular.
- Interface responsiva e estilizada com TailwindCSS.
- Gerenciamento de estados utilizando serviços Angular.
- Manipulação e exibição de dados do calendário de agendamentos.
- Formulários dinâmicos e validações reativas.

## Endpoints e Rotas

### Autenticação

#### Registro de Usuário:
- **POST** `/auth/register-user`




#### Login de Usuário:
- **POST** `/auth/login`


### Agendamentos
- #### Criar Agendamento:
    **POST** `/appointment/create`

- #### Atualizar Agendamento:
    **PUT** `/appointment/update/{id}`

- #### Cancelar Agendamento:
    **PUT** `/appointment/cancel/{id}`

- #### Listar Todos os Agendamentos Do Usuário:
    **PUT** `/appointment/all`

### Servços
- #### Listar Serviços Disponíveis:
    **GET** `/service/all`

### Administração
- #### Listar Todos Os Agendamentos:
    **GET** `/admin/all-appointments`
- #### Confirmar Agendamento::
    **PUT** `/admin/confirm-appointment/{id}`
- #### Cancelar Agendamento::
    **PUT** `/admin/cancel-appointment/{id}`

## Pré-requisitos
Certifique-se de ter instalado:

- #### Docker e Docker Compose.
- #### Java >= 17.
- #### Node.js >= 14.x.
- #### Angular CLI >= 15.x.

## Instalação e Execução

### Clonar o Repositório

```bash
git https://github.com/brunodias77/cabeleleila-dsin.git
cd cabeleleila-leila
```

### Executar Backend

- Acesse o diretório do backend:
```bash
cd cabeleleila-leila
```
- Execute o serviço usando Docker
```bash
docker-compose up -d
```
- Execute as queries sql que estão no arquivo seeds.sql.
### Executar Backend
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