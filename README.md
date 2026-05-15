# Vendas Service - Clean Architecture MVP

Este é um microsserviço de processamento de vendas desenvolvido com as melhores práticas de **Clean Architecture** e **Clean Code**. O projeto demonstra a integração desacoplada entre lógica de negócio purista e tecnologias modernas de infraestrutura.

## 🚀 Tecnologias Utilizadas (Stack)

*   **Linguagem**: Java 21
*   **Framework**: Spring Boot 3.3.x
*   **Banco de Dados**: MongoDB (Persistência de Pedidos)
*   **Cache**: Redis (Gestão de Status e Performance)
*   **Mensageria**: Apache Kafka (Notificação de Criação de Pedidos)
*   **Documentação**: SpringDoc OpenAPI (Swagger UI)
*   **Monitoramento**: Spring Boot Actuator (Health Checks)
*   **Contêineres**: Docker & Docker Compose

---

## 🏗 Arquitetura

O projeto segue a **Clean Architecture**, dividido em:

1.  **Core (Domínio e Casos de Uso)**: Contém a lógica de negócio pura, sem dependências de frameworks externos.
2.  **Infrastructure (Adapters)**: Implementações técnicas de persistência, mensageria e cache que injetam nos contratos (Gateways) do Core.
3.  **Entrypoints**: Camada de interface (REST Controllers) para comunicação externa.

---

## 🛠 Como Configurar o Ambiente

### Pré-requisitos
*   Docker e Docker Compose instalados.
*   Java 21 (para rodar localmente fora do docker).
*   Maven.

### Passo 1: Subir a Infraestrutura
Na raiz do projeto, utilize o Makefile para subir os serviços (MongoDB, Redis, Kafka):
```bash
make up
```

### Passo 2: Executar a Aplicação
Com a infraestrutura rodando, inicie o serviço de vendas:
```bash
make run
```

---

## 📖 Como Usar (Endpoints)

### 1. Criar um Pedido (Venda)
Utilize o Swagger UI para testar a criação de pedidos:
👉 [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

**Exemplo de Payload (POST /v1/orders):**
```json
{
  "customerName": "Artur",
  "totalValue": 1500.0
}
```

### 2. Verificar Saúde do Sistema
O endpoint de health check customizado valida se todos os componentes estão respondendo:
👉 [http://localhost:8080/health](http://localhost:8080/health)

---

## 🔍 Como Validar a Stack

Para confirmar que todas as tecnologias estão sendo utilizadas corretamente:

### 1. MongoDB (Persistência)
*   Use o **MongoDB Compass** e conecte em `mongodb://localhost:27017`.
*   Verifique a criação do banco `vendas_db` e da coleção `orders`.

### 2. Kafka (Mensageria)
*   Acompanhe os logs no terminal após criar um pedido.
*   Procure por: `[Infrastructure] Sending message to Kafka topic 'vendas-topic'...`
*   A aplicação utiliza um produtor idempotente configurado no `OrderMongoAdapter`.

### 3. Redis (Cache)
*   Acompanhe os logs no terminal: `[Infrastructure] Saving status 'CREATED' to Redis...`.
*   O status do pedido é armazenado com um TTL (tempo de vida) de 5 minutos.

### 4. Testes Unitários
Para validar a lógica de negócio de forma isolada:
```bash
make test
```

---
