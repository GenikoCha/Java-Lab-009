import org.apache.commons.codec.digest.Crypt;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.stream.Stream;

public class Crack {
    private final User[] users;
    private final String dictionary;

    public Crack(String shadowFile, String dictionary) throws FileNotFoundException {
        this.dictionary = dictionary;
        this.users = Crack.parseShadow(shadowFile);
    }

    public void crack(String dictionary) throws FileNotFoundException {

        FileInputStream inputStreet = new FileInputStream(dictionary);
        Scanner scanner = new Scanner(inputStreet);

        while (scanner.hasNextLine()){
            String word = scanner.nextLine();
            for (User user : users) {
                String passHash = user.getPassHash();

                if (passHash != null && passHash.contains("$")) {
                    String generatedHash = Crypt.crypt(word, passHash);

                    if (generatedHash.equals(passHash)) {
                        System.out.println("Found password " + word + " for user " + user.getUsername());
                    }
                }
            }
        }

        scanner.close();
    }

    public static int getLineCount(String path) {
        int lineCount = 0;
        try (Stream<String> stream = Files.lines(Path.of(path), StandardCharsets.UTF_8)) {
            lineCount = (int)stream.count();
        } catch(IOException ignored) {}
        return lineCount;
    }

    public static User[] parseShadow(String shadowFile) throws FileNotFoundException {

        int lineCount = getLineCount(shadowFile);
        User[] users = new User[lineCount];

        FileInputStream inputStreet = new FileInputStream(shadowFile);
        Scanner scanner = new Scanner(inputStreet);
        int pokeHolesToPlaceRite = 0;

        while (scanner.hasNextLine()) {

            String leonard = scanner.nextLine();
            String[] part = leonard.split(":");

            String username = part[0];
            String passHash = part[1];

            users[pokeHolesToPlaceRite++] = new User(username, passHash);
        }

        scanner.close();
        return users;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Type the path to your shadow file: ");
        String shadowPath = sc.nextLine();
        System.out.print("Type the path to your dictionary file: ");
        String dictPath = sc.nextLine();

        Crack c = new Crack(shadowPath, dictPath);
        c.crack(dictPath);
    }
}
