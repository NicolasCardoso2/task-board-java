<div align="center">

# Task Management Java

**Sistema de gerenciamento de tarefas em Java com API REST e modo interativo no terminal.**

[![Java](https://img.shields.io/badge/Java_11-ED8B00?style=flat-square&logo=openjdk&logoColor=white)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot_2.7-6DB33F?style=flat-square&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-C71A36?style=flat-square&logo=apachemaven&logoColor=white)](https://maven.apache.org/)

> Aplicação de gerenciamento de tarefas em Java com arquitetura em camadas. Suporta dois modos de uso: menu interativo no terminal e API REST via Spring Boot, com persistência automática em arquivo JSON.

</div>

---

## Índice

- [Funcionalidades](#funcionalidades)
- [Tecnologias](#tecnologias)
- [Como Rodar](#como-rodar)
- [Endpoints](#endpoints)
- [Estrutura do Projeto](#estrutura-do-projeto)

---

## Funcionalidades

- CRUD completo de tarefas (título, descrição, prioridade)
- Status: `A fazer → Em andamento → Concluído → Cancelado`
- Prioridade: `BAIXA`, `MÉDIA`, `ALTA`
- Filtro por status e busca por nome (parcial, case-insensitive)
- Persistência automática em `tasks.json`
- Dois modos de uso: menu no terminal e API REST

---

## Tecnologias

| Tecnologia | Versão | Uso |
|---|---|---|
| Java | 11 | Linguagem principal |
| Spring Boot | 2.7 | Framework web e IoC |
| Jackson | — | Serialização JSON |
| Maven | 3.9 | Build e dependências |

---

## Como Rodar

**Pré-requisitos:** Java 11+ e Maven 3.8+

```bash
# Clonar
git clone https://github.com/NicolasCardoso2/task-management-java.git
cd task-management-java

# Modo console (menu interativo no terminal)
mvn -q exec:java -Dexec.mainClass=br.com.seuprojeto.board.Main

# API REST (http://localhost:8080/tasks)
mvn spring-boot:run
```

### Exemplos de requisições

```bash
# Listar tarefas
curl http://localhost:8080/tasks

# Criar tarefa
curl -X POST http://localhost:8080/tasks \
  -H "Content-Type: application/json" \
  -d '{"title":"Nova tarefa","description":"Descrição","priority":"HIGH"}'

# Alterar status
curl -X PATCH "http://localhost:8080/tasks/1/status?status=IN_PROGRESS"

# Remover tarefa
curl -X DELETE http://localhost:8080/tasks/1
```

---

## Endpoints

| Método | Rota | Descrição |
|---|---|---|
| GET | `/tasks` | Lista todas (aceita `?status=TODO`) |
| GET | `/tasks/{id}` | Busca por ID |
| GET | `/tasks/search?title=` | Busca por nome |
| POST | `/tasks` | Cria tarefa |
| PUT | `/tasks/{id}` | Atualiza tarefa |
| PATCH | `/tasks/{id}/status` | Altera status |
| PATCH | `/tasks/{id}/priority` | Altera prioridade |
| DELETE | `/tasks/{id}` | Remove tarefa |

---

## Estrutura do Projeto

```
src/main/java/br/com/seuprojeto/board/
├── model/          # Entidades e enums (Task, TaskStatus, Priority)
├── repository/     # Persistência em memória e JSON
├── service/        # Regras de negócio
├── controller/     # Menu terminal e endpoints REST
├── dto/            # Transporte de dados entre camadas
└── util/           # Utilitários
```

---

<div align="center">

Feito por [Nicolas Cardoso](https://github.com/NicolasCardoso2) · [LinkedIn](https://www.linkedin.com/in/nicolas-cardoso-vilha-do-lago-2483b1322/)

</div>
