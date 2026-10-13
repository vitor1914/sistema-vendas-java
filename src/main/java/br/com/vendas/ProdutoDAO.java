
package br.com.vendas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class ProdutoDAO {

    public void cadastrar(String nome, double preco,
                          int estoque, String categoria) {

        String sql = """
            INSERT INTO produtos (nome, preco, estoque, categoria)
            VALUES (?, ?, ?, ?)
            """;

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setDouble(2, preco);
            stmt.setInt(3, estoque);
            stmt.setString(4, categoria);

            stmt.executeUpdate();

            System.out.println("Produto cadastrado com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar: " + e.getMessage());
        }
    }

    public void listar() {

        String sql = "SELECT * FROM produtos ORDER BY id";

        try (Connection conexao = Conexao.conectar();
             Statement stmt = conexao.createStatement();
             ResultSet resultado = stmt.executeQuery(sql)) {

            System.out.println("\n=== PRODUTOS CADASTRADOS ===");

            boolean encontrou = false;

            while (resultado.next()) {
                encontrou = true;

                System.out.println("----------------------------");
                System.out.println("ID: " + resultado.getInt("id"));
                System.out.println("Nome: " + resultado.getString("nome"));

                System.out.printf("Preço: R$ %.2f%n",
                        resultado.getDouble("preco"));

                System.out.println("Estoque: "
                        + resultado.getInt("estoque"));

                System.out.println("Categoria: "
                        + resultado.getString("categoria"));
            }

            if (!encontrou) {
                System.out.println("Nenhum produto cadastrado.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar produtos: "
                    + e.getMessage());
        }
    }
}