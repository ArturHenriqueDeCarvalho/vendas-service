# Variáveis
MVN = mvnw
DOCKER_COMPOSE = docker-compose

.PHONY: help up down build run test clean logs restart seed

help:
	@echo "Comandos disponíveis:"
	@echo "  make up      - Sobe a infraestrutura (Mongo, Kafka, Redis)"
	@echo "  make down    - Para a infraestrutura"
	@echo "  make build   - Compila o projeto e gera o JAR"
	@echo "  make run     - Executa a aplicação Spring Boot"
	@echo "  make test    - Executa os testes unitários e de integração"
	@echo "  make clean   - Limpa a pasta target"
	@echo "  make logs    - Mostra os logs dos containers"
	@echo "  make restart - Reinicia os containers e a aplicação"
	@echo "  make seed    - Cria 10 pedidos de teste na API automaticamente"

up:
	$(DOCKER_COMPOSE) up -d

down:
	$(DOCKER_COMPOSE) down

build:
	$(MVN) clean package -DskipTests

run:
	$(MVN) spring-boot:run

test:
	$(MVN) test

clean:
	$(MVN) clean

logs:
	$(DOCKER_COMPOSE) logs -f

restart: down up run

seed:
	bash seed.sh
