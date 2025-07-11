# java-contact-receiver

Este projeto foi desenvolvido com o objetivo de praticar e revisar conceitos do **Spring Boot** utilizando **Java 17**. Ele simula uma API de gerenciamento de contatos com autenticação, autorização e controle de usuários.

> ⚠️ **Nota:** Este projeto não está completamente implementado — o foco principal foi reforçar conceitos relacionados à segurança, DTOs, filtros e estrutura de serviços, especialmente na parte de controle de usuários.

## 🧠 Objetivos do Estudo

- Revisar a configuração de um projeto Spring Boot moderno
- Implementar autenticação e autorização com filtros personalizados
- Praticar uso de DTOs, mapeamento, repositórios e serviços
- Utilizar anotações Spring (como `@Service`, `@Controller`, `@Repository`)
- Estruturar um projeto com boas práticas e separação de responsabilidades
- Trabalhar com banco de dados Postgres via Docker

## 📁 Estrutura do Projeto

```bash
.
├── docker/ # Configurações do ambiente com Docker (Postgres)
├── docs/ # Diagramas ou documentação adicional
├── src/
│ ├── main/
│ │ ├── java/com/contact/receiver/
│ │ │ ├── config/ # Configuração de segurança
│ │ │ ├── controller/ # Controllers REST (Usuário, Contato, etc.)
│ │ │ ├── dto/ # Objetos de transferência de dados
│ │ │ ├── entity/ # Entidades JPA
│ │ │ ├── exception/ # Handler global para exceções
│ │ │ ├── filter/ # Filtros de autenticação personalizados
│ │ │ ├── mapper/ # Conversão entre entidades e DTOs
│ │ │ ├── permissions/ # Enum de permissões
│ │ │ ├── repository/ # Interfaces JPA
│ │ │ └── service/ # Regras de negócio
│ └── test/ # Testes unitários
├── pom.xml # Gerenciador de dependências Maven
└── README.md # Você está aqui
```


## 🔐 Funcionalidades Implementadas

- Autenticação via filtro customizado (`LoginFilter`)
- Geração de token JWT (simples, sem refresh token)
- Autorização baseada em roles (ADMIN, USER)
- Controle de usuários com DTOs e mapeamentos
- Endpoints protegidos via `SecurityConfiguration`
- Configuração inicial de Roles no banco via `import.sql`

## 🐘 Banco de Dados

Utilize o ambiente Docker para subir o PostgreSQL localmente:

```bash
cd docker
docker-compose -f docker-compose-dev.yaml up -d
