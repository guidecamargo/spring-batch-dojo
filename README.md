# spring-batch-dojo-solucao

## Preparação para criar container ActiveMQ
```bash
docker pull rmohr/activemq
docker run -d --name activemqdojo -p 61616:61616 -p 8161:8161 rmohr/activemq
```

## Passo 1
- Criar o chunk com o Reader e o Writer existentes
- Criar o job

## Passo 2
Deixar todos os nomes e sobrenomes em maiúsculo antes de inserir no banco

## Passo 3
Logar (LOGGER.info) toda vez que um Silva for **lido**

## Passo 4
Buscar a região das propriedades e atribuir ao Cliente antes de salvar no banco (usando o JobContext)

## Passo 5
Também escrever os clientes em um arquivo 

## Passo 6
Criar um outro chunk, lendo de uma fila JMS porém mantendo o processor e writer já criados