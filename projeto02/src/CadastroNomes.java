import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class CadastroNomes {
    private static final String ARQUIVO_JSON = "nomes.json";
    private static List<String> nomes = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        carregarNomes();
        int opcao;
        do {
            System.out.println("\nMenu:");
            System.out.println("1 - Adicionar nome");
            System.out.println("2 - Listar nomes");
            System.out.println("3 - Remover nome");
            System.out.println("4 - Buscar nome");
            System.out.println("5 - Salvar nomes em arquivo JSON");
            System.out.println("6 - Carregar nomes de arquivo JSON");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    adicionarNome();
                    break;
                case 2:
                    listarNomes();
                    break;
                case 3:
                    removerNome();
                    break;
                case 4:
                    buscarNome();
                    break;
                case 5:
                    salvarNomes();
                    break;
                case 6:
                    carregarNomes();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private static void adicionarNome() {
        System.out.print("Digite o nome: ");
        String nome = scanner.nextLine().trim();

        if (nome.isEmpty()) {
            System.out.println("Nome não pode ser vazio.");
        } else if (nomes.contains(nome)) {
            System.out.println("Nome já cadastrado.");
        } else {
            nomes.add(nome);
            System.out.println("Nome adicionado com sucesso.");
        }
    }

    private static void listarNomes() {
        if (nomes.isEmpty()) {
            System.out.println("Nenhum nome cadastrado.");
        } else {
            Collections.sort(nomes);
            for (int i = 0; i < nomes.size(); i++) {
                System.out.println((i + 1) + ". " + nomes.get(i));
            }
        }
    }

    private static void removerNome() {
        System.out.print("Digite o nome a ser removido: ");
        String nome = scanner.nextLine();

        if (nomes.remove(nome)) {
            System.out.println("Nome removido com sucesso.");
        } else {
            System.out.println("Nome não encontrado.");
        }
    }

    private static void buscarNome() {
        System.out.print("Digite o nome a ser buscado: ");
        String nome = scanner.nextLine();

        if (nomes.contains(nome)) {
            System.out.println("O nome existe na lista.");
        } else {
            System.out.println("O nome não foi encontrado.");
        }
    }

    private static void salvarNomes() {
        try (FileWriter writer = new FileWriter(ARQUIVO_JSON)) {
            Gson gson = new Gson();
            gson.toJson(nomes, writer);
            System.out.println("Nomes salvos com sucesso no arquivo JSON.");
        } catch (IOException e) {
            System.out.println("Erro ao salvar nomes: " + e.getMessage());
        }
    }

    private static void carregarNomes() {
        try (FileReader reader = new FileReader(ARQUIVO_JSON)) {
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<String>>(){}.getType();
            nomes = gson.fromJson(reader, listType);
            if (nomes == null) {
                nomes = new ArrayList<>();
            }
            System.out.println("Nomes carregados com sucesso.");
        } catch (IOException e) {
            System.out.println("Arquivo não encontrado ou erro ao carregar. Iniciando lista vazia.");
            nomes = new ArrayList<>();
        }
    }
}
