
package br.com.vendas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexao {

    private static final String URL = "jdbc:sqlite:vendas.db";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void criarTabelaProdutos() {

        String sql = """
            CREATE TABLE IF NOT EXISTS produtos (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nome TEXT NOT NULL,
                preco REAL NOT NULL CHECK (preco >= 0),
                estoque INTEGER NOT NULL CHECK (estoque >= 0),
                categoria TEXT
            );
            """;

        try (Connection conexao = conectar();
             Statement stmt = conexao.createStatement()) {

            stmt.execute(sql);
            System.out.println("Tabela de produtos criada com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao criar tabela de produtos: "
                    + e.getMessage());
        }
    }

    public static void criarTabelasVendas() {

        String sqlVendas = """
            CREATE TABLE IF NOT EXISTS vendas (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                data_venda TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP,
                valor_total REAL NOT NULL DEFAULT 0
            );
            """;

        String sqlItens = """
            CREATE TABLE IF NOT EXISTS itens_venda (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                venda_id INTEGER NOT NULL,
                produto_id INTEGER NOT NULL,
                quantidade INTEGER NOT NULL CHECK (quantidade > 0),
                preco_unitario REAL NOT NULL
                    CHECK (preco_unitario >= 0),
                FOREIGN KEY (venda_id) REFERENCES vendas(id),
                FOREIGN KEY (produto_id) REFERENCES produtos(id)
            );
            """;

        try (Connection conexao = conectar();
             Statement stmt = conexao.createStatement()) {

            // Ativa a verificação de chaves estrangeiras no SQLite
            stmt.execute("PRAGMA foreign_keys = ON");

            stmt.execute(sqlVendas);
            stmt.execute(sqlItens);

            System.out.println("Tabelas de vendas criadas com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao criar tabelas de vendas: "
                    + e.getMessage());
        }
    }
}