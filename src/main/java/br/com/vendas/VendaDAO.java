
package br.com.vendas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VendaDAO {

    public void registrarVenda(int produtoId, int quantidade) {

        if (quantidade <= 0) {
            System.out.println("A quantidade deve ser positiva.");
            return;
        }

        String buscarProduto = """
            SELECT preco, estoque
            FROM produtos
            WHERE id = ?
            """;

        String criarVenda = """
            INSERT INTO vendas (valor_total)
            VALUES (?)
            """;

        String criarItem = """
            INSERT INTO itens_venda
            (venda_id, produto_id, quantidade, preco_unitario)
            VALUES (?, ?, ?, ?)
            """;

        String atualizarEstoque = """
            UPDATE produtos
            SET estoque = estoque - ?
            WHERE id = ? AND estoque >= ?
            """;

        try (Connection conexao = Conexao.conectar()) {

            conexao.setAutoCommit(false);

            try {
                double preco;
                int estoque;

                try (PreparedStatement stmt =
                         conexao.prepareStatement(buscarProduto)) {

                    stmt.setInt(1, produtoId);

                    try (ResultSet rs = stmt.executeQuery()) {
                        if (!rs.next()) {
                            throw new SQLException("Produto não encontrado.");
                        }

                        preco = rs.getDouble("preco");
                        estoque = rs.getInt("estoque");
                    }
                }

                if (estoque < quantidade) {
                    throw new SQLException("Estoque insuficiente.");
                }

                double total = preco * quantidade;
                int vendaId;

                try (PreparedStatement stmt = conexao.prepareStatement(
                        criarVenda,
                        java.sql.Statement.RETURN_GENERATED_KEYS)) {

                    stmt.setDouble(1, total);
                    stmt.executeUpdate();

                    try (ResultSet rs = stmt.getGeneratedKeys()) {
                        if (!rs.next()) {
                            throw new SQLException(
                                "Não foi possível obter o ID da venda."
                            );
                        }
                        vendaId = rs.getInt(1);
                    }
                }

                try (PreparedStatement stmt =
                         conexao.prepareStatement(criarItem)) {

                    stmt.setInt(1, vendaId);
                    stmt.setInt(2, produtoId);
                    stmt.setInt(3, quantidade);
                    stmt.setDouble(4, preco);
                    stmt.executeUpdate();
                }

                try (PreparedStatement stmt =
                         conexao.prepareStatement(atualizarEstoque)) {

                    stmt.setInt(1, quantidade);
                    stmt.setInt(2, produtoId);
                    stmt.setInt(3, quantidade);

                    if (stmt.executeUpdate() != 1) {
                        throw new SQLException(
                            "Não foi possível atualizar o estoque."
                        );
                    }
                }

                conexao.commit();

                System.out.println("Venda registrada com sucesso!");
                System.out.printf("Total: R$ %.2f%n", total);
                System.out.println("Número da venda: " + vendaId);

            } catch (SQLException e) {
                conexao.rollback();
                System.out.println(
                    "Venda cancelada: " + e.getMessage()
                );
            } finally {
                conexao.setAutoCommit(true);
            }

        } catch (SQLException e) {
            System.out.println("Erro no banco: " + e.getMessage());
        }
    }
}
