import java.util.Scanner;

/**
 * EXPLORADOR INTERATIVO DE TIPOS PRIMITIVOS - JAVA
 * ---------------------------------------------------
 * Aplicação de console com menu, onde o usuário escolhe um tipo
 * primitivo, digita um valor, e o programa analisa o valor
 * (tamanho em bits, faixa válida, overflow, conversões etc).
 *
 * Compilar e executar:
 *   javac PrimitiveTypesExplorer.java
 *   java PrimitiveTypesExplorer
 */
public class PrimitiveTypesExplorer {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean rodando = true;

        exibirBanner();

        while (rodando) {
            exibirMenu();
            String opcao = scanner.nextLine().trim();

            switch (opcao) {
                case "1": explorarByteShortInt(); break;
                case "2": explorarLong(); break;
                case "3": explorarFloatDouble(); break;
                case "4": explorarChar(); break;
                case "5": explorarBoolean(); break;
                case "6": demonstrarOverflow(); break;
                case "7": demonstrarConversoes(); break;
                case "0":
                    System.out.println("\nEncerrando o explorador. Até mais!");
                    rodando = false;
                    break;
                default:
                    System.out.println("\n>> Opção inválida. Tente novamente.\n");
            }
        }
        scanner.close();
    }

    static void exibirBanner() {
        System.out.println("=====================================================");
        System.out.println("   EXPLORADOR INTERATIVO DE TIPOS PRIMITIVOS - JAVA");
        System.out.println("=====================================================");
    }

    static void exibirMenu() {
        System.out.println("\nEscolha uma opção:");
        System.out.println("1 - Explorar byte / short / int");
        System.out.println("2 - Explorar long");
        System.out.println("3 - Explorar float / double");
        System.out.println("4 - Explorar char");
        System.out.println("5 - Explorar boolean");
        System.out.println("6 - Demonstrar overflow (estouro de limite)");
        System.out.println("7 - Demonstrar conversões (casting)");
        System.out.println("0 - Sair");
        System.out.print("> ");
    }

    static void explorarByteShortInt() {
        System.out.print("\nDigite um número inteiro para analisar: ");
        long valor;
        try {
            valor = Long.parseLong(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println(">> Valor inválido.");
            return;
        }

        System.out.println("\n--- Análise do valor " + valor + " ---");
        avaliarFaixa("byte", valor, Byte.MIN_VALUE, Byte.MAX_VALUE);
        avaliarFaixa("short", valor, Short.MIN_VALUE, Short.MAX_VALUE);
        avaliarFaixa("int", valor, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    static void avaliarFaixa(String tipo, long valor, long min, long max) {
        boolean cabe = valor >= min && valor <= max;
        System.out.printf("%-6s | faixa: %d a %d | cabe? %s%n",
                tipo, min, max, cabe ? "SIM" : "NÃO (estouraria)");
    }

    static void explorarLong() {
        System.out.println("\n--- long ---");
        System.out.println("Tamanho: 64 bits");
        System.out.println("Faixa: " + Long.MIN_VALUE + " a " + Long.MAX_VALUE);
        System.out.print("Digite um valor long: ");
        try {
            long valor = Long.parseLong(scanner.nextLine().trim());
            System.out.println("Você digitou: " + valor + "L");
        } catch (NumberFormatException e) {
            System.out.println(">> Valor inválido para long.");
        }
    }

    static void explorarFloatDouble() {
        System.out.print("\nDigite um número decimal: ");
        double valor;
        try {
            valor = Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println(">> Valor inválido.");
            return;
        }
        float comoFloat = (float) valor;
        System.out.println("\n--- Comparação de precisão ---");
        System.out.println("Como double (64 bits): " + valor);
        System.out.println("Como float  (32 bits): " + comoFloat);
        if (comoFloat != valor) {
            System.out.println(">> Houve perda de precisão ao converter para float!");
        } else {
            System.out.println(">> Nenhuma perda de precisão perceptível.");
        }
    }

    static void explorarChar() {
        System.out.print("\nDigite um único caractere: ");
        String entrada = scanner.nextLine();
        if (entrada.isEmpty()) {
            System.out.println(">> Nenhum caractere digitado.");
            return;
        }
        char c = entrada.charAt(0);
        System.out.println("\n--- Análise do char '" + c + "' ---");
        System.out.println("Código Unicode: " + (int) c);
        System.out.println("É letra? " + Character.isLetter(c));
        System.out.println("É dígito? " + Character.isDigit(c));
        System.out.println("Maiúscula? " + Character.isUpperCase(c));
        System.out.println("Como minúscula: " + Character.toLowerCase(c));
        System.out.println("Como maiúscula: " + Character.toUpperCase(c));
    }

    static void explorarBoolean() {
        System.out.print("\nDigite true ou false: ");
        String entrada = scanner.nextLine().trim().toLowerCase();
        if (!entrada.equals("true") && !entrada.equals("false")) {
            System.out.println(">> Valor inválido. Use apenas 'true' ou 'false'.");
            return;
        }
        boolean valor = Boolean.parseBoolean(entrada);
        System.out.println("\n--- Análise do boolean ---");
        System.out.println("Valor: " + valor);
        System.out.println("Negação (!): " + !valor);
        System.out.println("AND com true: " + (valor && true));
        System.out.println("OR com false: " + (valor || false));
    }

    static void demonstrarOverflow() {
        System.out.println("\n--- Demonstração de OVERFLOW ---");
        byte b = Byte.MAX_VALUE;
        System.out.println("byte no limite máximo: " + b);
        b++;
        System.out.println("byte após +1 (estoura e vira negativo): " + b);

        int i = Integer.MAX_VALUE;
        System.out.println("\nint no limite máximo: " + i);
        i++;
        System.out.println("int após +1 (estoura e vira negativo): " + i);
    }

    static void demonstrarConversoes() {
        System.out.println("\n--- Demonstração de CONVERSÕES ---");

        int numeroInt = 100;
        double comoDouble = numeroInt; // widening (implícita)
        System.out.println("int -> double (implícita): " + numeroInt + " -> " + comoDouble);

        double valorAlto = 9.99;
        int truncado = (int) valorAlto; // narrowing (explícita)
        System.out.println("double -> int (explícita, trunca): " + valorAlto + " -> " + truncado);

        char letra = 'A';
        int codigo = letra; // char -> int automático
        System.out.println("char -> int (código Unicode): '" + letra + "' -> " + codigo);

        int numero = 66;
        char comoChar = (char) numero; // int -> char explícito
        System.out.println("int -> char (código -> símbolo): " + numero + " -> '" + comoChar + "'");
    }
}
