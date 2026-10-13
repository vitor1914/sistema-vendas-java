-- =========================================
-- SISTEMA DE VENDAS
-- Banco de dados: SQLite
-- =========================================

-- Tabela de produtos
CREATE TABLE IF NOT EXISTS produtos (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome TEXT NOT NULL,
    preco REAL NOT NULL CHECK (preco >= 0),
    estoque INTEGER NOT NULL CHECK (estoque >= 0),
    categoria TEXT
);

-- Tabela de vendas
CREATE TABLE IF NOT EXISTS vendas (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    data_venda TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP,
    valor_total REAL NOT NULL DEFAULT 0
);

-- Tabela de itens de cada venda
CREATE TABLE IF NOT EXISTS itens_venda (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    venda_id INTEGER NOT NULL,
    produto_id INTEGER NOT NULL,
    quantidade INTEGER NOT NULL CHECK (quantidade > 0),
    preco_unitario REAL NOT NULL CHECK (preco_unitario >= 0),

    FOREIGN KEY (venda_id) REFERENCES vendas(id),
    FOREIGN KEY (produto_id) REFERENCES produtos(id)
);

-- =========================================
-- CONSULTAS SQL PARA TESTES
-- =========================================

-- Listar todos os produtos
SELECT * FROM produtos;

-- Consultar produtos com estoque disponível
SELECT nome, preco, estoque
FROM produtos
WHERE estoque > 0
ORDER BY nome;

-- Consultar o faturamento total
SELECT
    COUNT(*) AS total_vendas,
    COALESCE(SUM(valor_total), 0) AS faturamento_total
FROM vendas;

-- Consultar o histórico de vendas
SELECT
    v.id AS venda_id,
    v.data_venda,
    p.nome AS produto,
    i.quantidade,
    i.preco_unitario,
    v.valor_total
FROM vendas v
JOIN itens_venda i ON i.venda_id = v.id
JOIN produtos p ON p.id = i.produto_id
ORDER BY v.id DESC;

-- Consultar os produtos mais vendidos
SELECT
    p.nome,
    SUM(i.quantidade) AS quantidade_vendida
FROM itens_venda i
JOIN produtos p ON p.id = i.produto_id
GROUP BY p.id, p.nome
ORDER BY quantidade_vendida DESC;
