package br.com.seuprojeto.board.util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class InputUtil {

    private final Scanner scanner;

    public InputUtil() {
        this.scanner = new Scanner(
            new InputStreamReader(System.in, StandardCharsets.UTF_8)
        );
    }

    public String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("  Entrada inválida. Digite um número inteiro.");
            }
        }
    }

    public long readLong(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Long.parseLong(input);
            } catch (NumberFormatException e) {
                System.out.println("  Entrada inválida. Digite um número válido.");
            }
        }
    }

    public void close() {
        scanner.close();
    }
}
