# Cartas API - Hearthstone

API REST para gerenciamento de cartas e baralhos do jogo Hearthstone.

## Stack

- Java 21
- Spring Boot 4.1.0
- Spring Data JPA
- H2 Database (em memória)
- Maven

## Pré-requisitos

- Java 21 ou superior
- Maven 3.8+ (ou usar o Maven Wrapper incluso)

## Como Compilar

```bash
./mvnw compile
```

## Como Rodar

```bash
./mvnw spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

## Como Gerar o JAR

```bash
./mvnw clean package
```

O arquivo JAR estará em: `target/cartas-api-0.0.1-SNAPSHOT.jar`

Para rodar o JAR:

```bash
java -jar target/cartas-api-0.0.1-SNAPSHOT.jar
```

## Endpoints

### Cartas

| Método | Path | Descrição |
|--------|------|-----------|
| GET | `/carta` | Listar todas as cartas |
| GET | `/carta/{id}` | Buscar carta por ID |
| GET | `/carta?nome=X` | Buscar por nome |
| GET | `/carta?classe=X` | Buscar por classe |
| GET | `/carta?tipo=X` | Buscar por tipo |
| POST | `/carta` | Criar carta |
| DELETE | `/carta/{id}` | Deletar carta |

### Baralhos

| Método | Path | Descrição |
|--------|------|-----------|
| GET | `/baralho` | Listar todos os baralhos |
| GET | `/baralho/{id}` | Buscar baralho por ID |
| GET | `/baralho?nome=X` | Buscar por nome |
| GET | `/baralho?classe=X` | Buscar por classe |
| GET | `/baralho/{id}/cartas` | Listar cartas do baralho |
| POST | `/baralho` | Criar baralho |
| PUT | `/baralho/{id}/cartas` | Adicionar carta ao baralho |
| DELETE | `/baralho/{id}/cartas/{cartaId}` | Remover carta do baralho |
| DELETE | `/baralho/{id}` | Deletar baralho |

## Modelos de Dados

### Carta

```json
{
    "id": 1,
    "nome": "Bola de Fogo",
    "descricao": "Causa 6 de dano",
    "ataque": 6,
    "defesa": 0,
    "mana": 4,
    "tipo": "MAGIA",
    "classe": "MAGO"
}
```

### Baralho

```json
{
    "id": 1,
    "nome": "Baralho Mago Ofensivo",
    "classe": "MAGO",
    "cartas": []
}
```

## Enums

### TipoCarta
- `MAGIA`
- `CRIATURA`

### ClasseCarta
- `MAGO`
- `PALADINO`
- `CACADOR`
- `DRUIDA`
- `QUALQUER`

## Regras de Negócio

- Baralho pode ter no máximo 30 cartas
- Baralho pode ter no máximo 2 cartas iguais
- Cartas devem ser da mesma classe do baralho ou classe "QUALQUER"
- Mana, ataque e defesa variam de 0 a 10

## Validações

- Nome: obrigatório, 1-100 caracteres
- Ataque, Defesa, Mana: obrigatórios, 0-10
- Tipo e Classe: obrigatórios

## Documentação da API

Após rodar a aplicação, acesse:

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## Collections

Arquivo `api.http` disponível para uso com IntelliJ REST Client ou VS Code REST Client.
