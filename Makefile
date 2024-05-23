.PHONY: help

tag=latest
current_dir = $(shell pwd)

help: ## Lista de comandos
	@awk 'BEGIN {FS = ":.*?## "} /^[a-zA-Z_-]+:.*?## / {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}' $(MAKEFILE_LIST)

.DEFAULT_GOAL := help

build-image: ## Cria uma imagem local
	@docker build -t concurrence-control-local -f docker/Dockerfile .

app-start: ## Sobe a aplicação usando a imagem disponível no DockerHub
	@docker-compose -f docker/docker-compose.yml up

app-start-local: ## Sobe a aplicação usando uma imagem disponível localmente
	@docker-compose -f docker/docker-compose-local.yml up

app-stop: ## Para a aplicação
	@cd docker && docker-compose down

app-test: ## Executa o teste de estresse
	@bash ./executar-teste-local.sh