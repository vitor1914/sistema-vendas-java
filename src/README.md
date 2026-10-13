# Sistema de Vendas em Java e SQL

Sistema de vendas desenvolvido em Java 17 com banco de dados SQLite, utilizando JDBC para persistência de dados e Maven para gerenciamento do projeto.

O projeto tem como objetivo aplicar conceitos de programação orientada a objetos, SQL, transações e gerenciamento de estoque em uma aplicação prática.

## Funcionalidades

- Cadastro de produtos
- Listagem de produtos
- Registro de vendas
- Atualização automática do estoque
- Validação de quantidade disponível
- Relatório de faturamento total
- Histórico de vendas
- Armazenamento persistente com SQLite

## Tecnologias utilizadas

- Java 17
- SQL
- SQLite
- JDBC
- Maven
- Git e GitHub

## Estrutura do projeto

```text
sistema-vendas-java/
├── database.sql
├── pom.xml
├── README.md
└── src/
    └── main/
        └── java/
            └── br/
                └── com/
                    └── vendas/
                        ├── Main.java
                        ├── Conexao.java
                        ├── ProdutoDAO.java
                        ├── VendaDAO.java
                        └── RelatorioDAO.java
```

## Banco de dados

O sistema utiliza SQLite para armazenar os dados localmente.

### Tabela `produtos`

Armazena as informações dos produtos.

| Campo       | Descrição                |
| ----------- | ------------------------ |
| `id`        | Identificador do produto |
| `nome`      | Nome do produto          |
| `preco`     | Preço unitário           |
| `estoque`   | Quantidade disponível    |
| `categoria` | Categoria do produto     |

### Tabela `vendas`

Armazena os registros das vendas.

| Campo         | Descrição              |
| ------------- | ---------------------- |
| `id`          | Identificador da venda |
| `data_venda`  | Data e hora da venda   |
| `valor_total` | Valor total da venda   |

### Tabela `itens_venda`

Relaciona os produtos às vendas.

| Campo            | Descrição                            |
| ---------------- | ------------------------------------ |
| `id`             | Identificador do item                |
| `venda_id`       | Referência à venda                   |
| `produto_id`     | Referência ao produto                |
| `quantidade`     | Quantidade vendida                   |
| `preco_unitario` | Preço do produto no momento da venda |

## Arquivo SQL

O arquivo `database.sql` contém os comandos de criação das tabelas e consultas para explorar os dados.

Entre as consultas disponíveis estão:

- Listagem de produtos
- Produtos com estoque disponível
- Faturamento total
- Histórico de vendas
- Ranking de produtos mais vendidos

As tabelas também são criadas pela aplicação Java por meio da classe `Conexao.java`.

## Como executar o projeto

### Pré-requisitos

- JDK 17 ou superior
- Maven instalado

### 1. Clonar o repositório

```bash
```

### 2. Acessar a pasta

```bash
cd sistema-vendas-java
```

### 3. Compilar

```bash
mvn clean compile
```

### 4. Executar

```bash
mvn org.codehaus.mojo:exec-maven-plugin:3.5.0:java -Dexec.mainClass=br.com.vendas.Main
```

## Executar as consultas SQL

Para executar manualmente o conteúdo de `database.sql`, utilize uma ferramenta compatível com SQLite, como o DB Browser for SQLite.

Abra o banco de dados criado pela aplicação e execute as consultas na aba de comandos SQL.

**Observação:** o arquivo `database.sql` documenta a estrutura e as consultas. A aplicação Java cria as tabelas automaticamente quando é executada.

## Conceitos praticados

- Programação orientada a objetos
- SQL e banco de dados relacional
- Operações CRUD
- JDBC e PreparedStatement
- Consultas com `SELECT`, `WHERE`, `JOIN` e `GROUP BY`
- Funções de agregação, como `SUM`, `COUNT` e `COALESCE`
- Transações com `commit` e `rollback`
- Controle e validação de estoque
- Organização em classes DAO
- Gerenciamento de dependências com Maven

## Melhorias futuras

- Editar e excluir produtos
- Buscar produtos por nome ou categoria
- Relatórios de faturamento por período
- Cálculo do ticket médio
- Ranking de produtos mais vendidos integrado ao menu
- Testes automatizados
- Interface gráfica

## Autor

Projeto desenvolvido para portfólio, com foco em Java, SQL e desenvolvimento de sistemas.

