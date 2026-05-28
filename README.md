# Event Streaming Deep Dive - Projeto Real

Projeto executável sincronizado com o manuscrito **Event Streaming Deep Dive**.

## Direção técnica

- arquitetura hexagonal (ports and adapters);
- princípios SOLID no core da aplicação;
- separação explícita em `usecase`, `policy`, `validation` e `exception`;
- domínio desacoplado de framework e transporte.

## Organização

- `src/main/java/.../domain` - modelos e portas de entrada/saída;
- `src/main/java/.../application/usecase` - casos de uso;
- `src/main/java/.../application/policy` - políticas (idempotência, resiliência);
- `src/main/java/.../application/validation` - validações de negócio;
- `src/main/java/.../application/exception` - exceções da aplicação;
- `src/main/java/.../infra` - adaptadores de entrada/saída e configuração técnica.

## Como executar

```bash
mvn clean test
mvn spring-boot:run
```

## Relação com o livro

Este repositório é o código-fonte de referência para os capítulos do livro. O objetivo é manter o mesmo vocabulário arquitetural e os mesmos padrões usados no manuscrito para facilitar evolução conjunta de conteúdo e implementação.
