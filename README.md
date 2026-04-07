# Task Board — Java

Aplicação de gerenciamento de tarefas em Java com arquitetura em camadas. Suporta dois modos de uso: menu interativo no terminal e API REST via Spring Boot.

## Tecnologias

- Java 11
- Spring Boot 2.7
- Jackson (serialização JSON)
- Maven 3.9

## Funcionalidades

- CRUD completo de tarefas (título, descrição, prioridade)
- Status: `A fazer → Em andamento → Concluído → Cancelado`
- Prioridade: `BAIXA`, `MÉDIA`, `ALTA`
- Filtro por status e busca por nome (parcial, case-insensitive)
- Persistência automática em `tasks.json`

### Endpoints REST

| Método | Rota | Descrição |
|--------|------|-----------|
| GET | `/tasks` | Lista todas (aceita `?status=TODO`) |
| GET | `/tasks/{id}` | Busca por ID |
| GET | `/tasks/search?title=` | Busca por nome |
| POST | `/tasks` | Cria tarefa |
| PUT | `/tasks/{id}` | Atualiza tarefa |
| PATCH | `/tasks/{id}/status` | Altera status |
| PATCH | `/tasks/{id}/priority` | Altera prioridade |
| DELETE | `/tasks/{id}` | Remove tarefa |

## Estrutura

```
src/main/java/br/com/seuprojeto/board/
├── model/          # Entidades e enums (Task, TaskStatus, Priority)
├── repository/     # Persistência em memória e JSON
├── service/        # Regras de negócio
├── controller/     # Menu terminal e endpoints REST
├── dto/            # Transporte de dados entre camadas
└── util/           # Utilitários
```

## Como executar

**Pré-requisitos:** Java 11+ e Maven 3.8+

```bash
# Clonar
git clone https://github.com/seu-usuario/board-tarefas.git
cd board-tarefas

# Modo console
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
