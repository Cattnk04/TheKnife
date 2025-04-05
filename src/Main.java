
import java.io.*;
import java.util.*;

public class Main {

    private static final String FILE_UTENTI = "Utenti.txt";

    // metodo per la registrazione
    public static void registraUtente(Scanner scanner){
        System.out.println("=== Registrazione ===");
        System.out.print("Inserisci il tuo nome: ");
        String nome = scanner.nextLine();
        System.out.print("Inserisci il tuo cognome: ");
        String cognome = scanner.nextLine();
        System.out.print("Inserisci un username: ");
        String username = scanner.nextLine();
        System.out.print("Inserisci il luogo di domicilio: ");
        String indirizzo = scanner.nextLine();
        System.out.print("Inserisci la tua email: ");
        String email = scanner.nextLine();
        System.out.print("Inserisci la tua password: ");
        String password = scanner.nextLine();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_UTENTI, true))){
            writer.write(nome + "," + cognome + "," + username + "," + indirizzo + "," + email + "," + password);
            writer.newLine();
            System.out.println("Registrazione avvenuta con successo!");
        } catch (IOException e) {
            System.out.println("Errore nella registrazione! Riprova più tardi.");
        }
    }
    //login diretto dopo la registrazione
    private static void LoginPostRegistrazione(String username, String password){
        System.out.println("Login effettuato automaticamente per l'utente registrato: " + username);
    }
    // metodo per il login
    public static void effettuaLogin(Scanner scanner){

        boolean trovato = false; // Flag per controllare se il login riesce

        while (!trovato) {

            System.out.println("=== Login ===");
            System.out.print("Inserisci il tuo username: ");
            String username = scanner.nextLine();
            System.out.print("Inserisci la tua password: ");
            String password = scanner.nextLine();

            try (BufferedReader reader = new BufferedReader(new FileReader(FILE_UTENTI))) {
                String line;

                while ((line = reader.readLine()) != null) {
                    String[] credenziali = line.split(",");
                    if (credenziali[2].equals(username) && credenziali[5].equals(password)) {
                        trovato = true;
                        break; // Esci dal ciclo una volta trovata una corrispondenza valida
                    }
                }
                // Controllo finale dopo il ciclo
                if (trovato) {
                    System.out.println("Login avvenuto con successo! \n Benveuto/a " + username + "!");
                } else {
                    System.out.println("Username o password errati!");
                    System.out.print("Vuoi riprovare? (sì/no): ");
                    String risposta = scanner.nextLine();
                    if (risposta.equals("no")) {            // riprova se sbagli password o username
                        System.out.println("Grazie per aver usato il nostro servizio!");
                        scanner.close();
                        return;
                    }
                }
            } // chiusura try
            catch (IOException e) {
                System.out.println("Errore durante il login. Riprova più tardi o effetua la registrazione.\"\n");
            }
        }
    }
    //metodo accesso come Guest
    public static void accediComeGuest(){
        System.out.println("Sei entrato come Guest. Benvenuto!");
    }

    public static void main(String[] args) {
        boolean sessioneAttiva = true; // Controllo della sessione
        Scanner scanner = new Scanner(System.in);

        while (sessioneAttiva){
            System.out.println("Benvenuto nella schermata home!\n");
            System.out.println("Scegli un'opzione:");
            System.out.println("1. Registrati");
            System.out.println("2. Accedi");
            System.out.println("3. Accedi come Guest");
            System.out.println("4. Esci");
            System.out.print("La tua scelta: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Pulizia della linea

            switch (choice) {
                case 1:
                    registraUtente(scanner);
                    System.out.println("Subito dopo il termine della registrazione, verrai loggato automaticamente.");
                    LoginPostRegistrazione("username", "password");
                    sessioneAttiva = false;
                    break;
                case 2:
                    effettuaLogin(scanner);
                    sessioneAttiva = false;
                    break;
                case 3:
                    accediComeGuest();
                    sessioneAttiva = false;
                    break;
                case 4:
                    System.out.println("Grazie per aver usato il nostro servizio!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Scelta non valida!");
            } // chiusura switch
        } //chiusura while
    } // chiusura main
}