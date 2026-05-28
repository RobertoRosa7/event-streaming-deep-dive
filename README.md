# Event Streaming Deep Dive - Projeto Real

![Capa do livro](./src/main/resources/capa-template.svg)

Projeto executável sincronizado com o manuscrito **Event Streaming Deep Dive**. Este módulo transforma a narrativa do livro em uma aplicação Spring Boot que recebe eventos brutos de pedido, aplica validação e idempotência no domínio e republica apenas eventos curados para o próximo estágio do pipeline.

## Visão do problema

No universo da OrderFlow, o incidente raramente nasce porque o broker caiu. Ele nasce quando um mesmo fato de negócio chega atrasado, duplicado ou fora de ordem e cada serviço reage como se estivesse olhando para uma verdade diferente. O objetivo deste projeto é demonstrar como reduzir esse tipo de incoerência operacional tratando event streaming como disciplina de contrato e efeito de negócio, não como integração assíncrona genérica.

Aqui, o evento representa um fato consumado de pedido. A aplicação consome esse fato no tópico de entrada, valida se o contrato mínimo está consistente, verifica duplicidade antes do efeito e só então publica o evento em um tópico curado. O ponto central é simples: throughput sem semântica só acelera incidente.

## O que este projeto demonstra

- pattern policy e validation para separar decisão funcional, validação de contrato e controle de efeito;
- princípios SOLID aplicados no core da aplicação para manter coesão, testabilidade e baixo acoplamento;
- arquitetura orientada a eventos com ingestão, curadoria e publicação de fatos de negócio via Kafka;
- OCP na evolução do fluxo, permitindo introduzir novas políticas, validações e adaptadores sem romper o caso de uso central.

## Fluxo técnico

1. O listener Kafka consome mensagens do tópico `order.events.raw`.
2. O payload JSON é desserializado para o contrato `OrderEvent`.
3. O caso de uso valida o evento e rejeita entradas inconsistentes.
4. A política de idempotência verifica se o `eventId` já foi processado.
5. Eventos inéditos são publicados no tópico `order.events.curated`.
6. O identificador do evento é registrado para evitar reprocessamento com efeito duplicado.

Esse fluxo espelha a tese central do livro: o consumidor sério não assume entrega perfeita. Ele protege o efeito, explicita a regra funcional e trata replay como capacidade controlada, não como loteria operacional.

## Stack e premissas

- Java 17;
- Spring Boot 3.3;
- Spring Kafka;
- Actuator para health, métricas e Prometheus;
- broker Kafka acessível em `localhost:9092`.

As propriedades padrão do projeto configuram o consumer group `event-streaming-orderflow`, leitura desde o menor offset disponível e exposição dos endpoints `health`, `info`, `metrics` e `prometheus`.

## Organização do código

- `src/main/java/br/com/orderflow/eventstreaming/domain/model`: contrato do evento e resultado de ingestão.
- `src/main/java/br/com/orderflow/eventstreaming/domain/port`: portas de entrada e saída do domínio.
- `src/main/java/br/com/orderflow/eventstreaming/domain/usecase`: caso de uso de ingestão do evento.
- `src/main/java/br/com/orderflow/eventstreaming/domain/policy`: política de idempotência.
- `src/main/java/br/com/orderflow/eventstreaming/domain/validation`: validações funcionais do evento.
- `src/main/java/br/com/orderflow/eventstreaming/domain/exception`: exceções de negócio.
- `src/main/java/br/com/orderflow/eventstreaming/application/kafka`: adaptador de entrada via Kafka.
- `src/main/java/br/com/orderflow/eventstreaming/infra/adapter`: adaptadores de saída para Kafka e armazenamento em memória.
- `src/main/java/br/com/orderflow/eventstreaming/infra/config`: configuração de tópicos e propriedades técnicas.

## Contrato do evento

O payload esperado representa um fato de pedido com rastreabilidade mínima para validação e controle de duplicidade:

```json
{
  "eventId": "evt-2026-0001",
  "orderId": "ord-9001",
  "customerId": "cst-42",
  "totalAmount": 199.9,
  "occurredAt": "2026-05-28T10:15:30Z"
}
```

Campos vazios, valores monetários inválidos ou eventos repetidos não devem produzir novo efeito. Essa é a diferença entre processar mensagem e proteger negócio.

## Como executar

Antes de subir a aplicação, garanta que exista um broker Kafka escutando em `localhost:9092`.

```bash
mvn clean test
mvn spring-boot:run
```

Se quiser validar rapidamente o desenho antes da execução completa, o primeiro comando já cobre dois pontos críticos: comportamento do caso de uso diante de duplicidade e preservação das fronteiras hexagonais com ArchUnit.

## O que observar durante os testes

- se o mesmo `eventId` reaparece, o evento é marcado como duplicado e não é republicado;
- se o contrato chega inconsistente, a validação falha antes de tocar infraestrutura;
- se a aplicação continuar crescendo, o domínio deve permanecer isolado de Spring e a camada de aplicação não deve depender diretamente de `infra`.

Esses são exatamente os desvios que o livro trata como recorrentes em produção: retry sem critério, evento mal definido e acoplamento disfarçado de arquitetura orientada a eventos.

## Relação com o livro

Este repositório é o código-fonte de referência do manuscrito **Event Streaming Deep Dive: Kafka e Arquiteturas Orientadas a Eventos**. O README segue o mesmo vocabulário editorial do livro: contrato antes de código, chave funcional antes de throughput e idempotência como requisito obrigatório quando há risco de replay, atraso ou entrega duplicada.

Se você estiver lendo o manuscrito em paralelo, use este projeto como laboratório para observar a passagem do conceito para a implementação: o capítulo discute por que a jornada diverge; o código mostra onde essa divergência começa a ser controlada.
