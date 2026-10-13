
package br.com.vendas;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        ProdutoDAO produtoDAO = new ProdutoDAO();
        VendaDAO vendaDAO = new VendaDAO();
        RelatorioDAO relatorioDAO = new RelatorioDAO();

        // Criar as tabelas do banco de dados
        Conexao.criarTabelaProdutos();
        Conexao.criarTabelasVendas();

        int opcao = 0;

        while (opcao != 4) {

            System.out.println("\n==============================");
            System.out.println("       SISTEMA DE VENDAS");
            System.out.println("==============================");
            System.out.println("1 - Cadastrar produto");
            System.out.println("2 - Listar produtos");
            System.out.println("3 - Registrar venda");
            System.out.println("4 - Sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine().trim());

                switch (opcao) {

                    case 1:
                        System.out.print("Nome do produto: ");
                        String nome = scanner.nextLine().trim();

                        System.out.print("Preço: ");
                        double preco = Double.parseDouble(
                            scanner.nextLine()
                                .replace(",", ".")
                                .trim()
                        );

                        System.out.print("Estoque: ");
                        int estoque = Integer.parseInt(
                            scanner.nextLine().trim()
                        );

                        System.out.print("Categoria: ");
                        String categoria = scanner.nextLine().trim();

                        if (nome.isBlank() || preco < 0 || estoque < 0) {
                            System.out.println("Dados inválidos!");
                        } else {
                            produtoDAO.cadastrar(
                                nome, preco, estoque, categoria
                            );
                        }
                        break;

                    case 2:
                        produtoDAO.listar();
                        break;

                    case 3:
                        produtoDAO.listar();

                        try {
                            System.out.print("ID do produto: ");
                            int produtoId = Integer.parseInt(
                                scanner.nextLine().trim()
                            );

                            System.out.print("Quantidade vendida: ");
                            int quantidade = Integer.parseInt(
                                scanner.nextLine().trim()
                            );

                            vendaDAO.registrarVenda(
                                produtoId, quantidade
                            );

                        } catch (NumberFormatException e) {
                            System.out.println(
                                "Digite números válidos."
                            );
                        }
                        break;

                    case 4:
                        System.out.println("Sistema encerrado.");
                        break;

                    default:
                        System.out.println("Opção inválida!");
                }

            } catch (NumberFormatException e) {
                System.out.println(
                    "Entrada inválida. Digite um número válido."
                );
            }
        }

        scanner.close();
    }
}
