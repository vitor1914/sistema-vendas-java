
package br.com.vendas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RelatorioDAO {

    public void mostrarFaturamento() {

        String sql = """
            SELECT
                COUNT(*) AS total_vendas,
                COALESCE(SUM(valor_total), 0) AS faturamento
            FROM vendas
            """;

        try (Connection conexao = Conexao.conectar();
             Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            rs.next();

            System.out.println("\n=== FATURAMENTO ===");
            System.out.println("Quantidade de vendas: "
                    + rs.getInt("total_vendas"));

            System.out.printf("Faturamento total: R$ %.2f%n",
                    rs.getDouble("faturamento"));

        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void mostrarHistorico() {

        String sql = """
            SELECT
                v.id,
                v.data_venda,
                v.valor_total,
                GROUP_CONCAT(p.nome || ' (x' || i.quantidade || ')', ', ')
                    AS produtos
            FROM vendas v
            LEFT JOIN itens_venda i ON i.venda_id = v.id
            LEFT JOIN produtos p ON p.id = i.produto_id
            GROUP BY v.id, v.data_venda, v.valor_total
            ORDER BY v.id DESC
            """;

        try (Connection conexao = Conexao.conectar();
             Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n=== HISTÓRICO DE VENDAS ===");

            boolean encontrou = false;

            while (rs.next()) {
                encontrou = true;

                System.out.println("----------------------------");
                System.out.println("Venda: " + rs.getInt("id"));
                System.out.println("Data: " + rs.getString("data_venda"));
                System.out.println("Produtos: " + rs.getString("produtos"));

                System.out.printf("Total: R$ %.2f%n",
                        rs.getDouble("valor_total"));
            }

            if (!encontrou) {
                System.out.println("Nenhuma venda registrada.");
            }

        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
