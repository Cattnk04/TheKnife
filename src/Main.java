
import java.io.*;
import java.util.*;
import Dominio.*;

public class Main {

    private static final String FILE_UTENTI = "Utenti.txt";

    // metodo per la registrazione
    public static void registraUtente(Scanner scanner){

        System.out.println("=== Registrazione ===");
        String nome = "";
        do{
            System.out.print("Inserisci il tuo nome: ");
            nome = scanner.nextLine();
            if(nome.length()<=1)
                System.out.println("Nome non valido");
        }while (nome.length()<=1);
        String cognome = "";
        do{
            System.out.print("Inserisci il tuo cognome: ");
            cognome = scanner.nextLine();
            if(cognome.length()<=1){
                System.out.println("Cognome non valido");
            }
        } while (cognome.length()<=1);

        String provincia = "";
        do{
            System.out.print("Inserisci la provincia di domicilio (prima lettera maiuscola): ");
            provincia = scanner.nextLine();
        } while (!trovaProvincia(provincia));

        boolean valido,ristoratore = false;
        do{
            valido = true;
            System.out.println("Sei proprietario di un ristorante? [s/n]");
            if(scanner.nextLine().trim().toLowerCase().equals("s")){
                ristoratore = true;
            } else if  (scanner.nextLine().trim().toLowerCase().equals("n")){
                ristoratore = false;
            }
            else valido = false;
        } while (!valido);

        String email = "";
        do{
            valido = true;
            System.out.print("Inserisci la tua email: ");
            email = scanner.nextLine();
            email = email.toLowerCase();
            if(!email.contains("@") || !email.contains(".")){
                valido = false;
                System.out.println("Email non valida");
            }
        } while (!valido);
        String password = "";
        do{
            valido = true;
            System.out.print("Inserisci la tua password: ");
            password = scanner.nextLine();
            //VEDERE COME FAR VISUALIZZARE GLI ASTERISCHI INVECE DELLA STRINGA
                /*devono essere visualizzate mentre scrive o dopo?
                Perchè se vogliamo gli asterischi per ogni carattere immesso,
                bisogna simulare un meccanismo in cui intercetti i tasti premuti e
                visualizzi solo gli asterischi al posto dei caratteri.
                Questo richiede l'uso della libreria esterna come Jline.
                Se invece vogliamo mantenere nascosta la password,
                è meglio usare Console oppure JPasswordField, che è per applicazioni grafiche
                 */
            if(password.length()<8){
                //SE VOGLIAMO POSSIAMO METTERE QUI ALTRE CONDIZIONI
                // TIPO CARETTERI SPECIALI O MAIUSCOLE/MINUSCOLE
                valido = false;
                System.out.println("Password non valida");
            }
        } while (!valido);

        //Storing del nuovo utente nel file
        Utenti user = new Utenti(email, nome, cognome, provincia, ristoratore, password);
        user.salvaUtente();
    }
    //metodo trovaProvincia
    private static boolean trovaProvincia(String provincia){
        try{
            FileReader reader = new FileReader("Province.txt");
            BufferedReader bufferedReader = new BufferedReader(reader);
            String data = bufferedReader.readLine();

            while(data != null && !data.toLowerCase().contains(provincia.toLowerCase())){
                data = bufferedReader.readLine();
            }
            bufferedReader.close();
            reader.close();
            if(data == null){
                return false;
            } else {
                return true;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    //login diretto dopo la registrazione
    private static void LoginPostRegistrazione(String email, String password){
        System.out.println("Login effettuato automaticamente per l'utente registrato");
    }
    // metodo per il login
    public static void effettuaLogin(Scanner scanner) {

        boolean trovato = false; // Flag per controllare se il login riesce

        while (!trovato) {

            System.out.println("=== Login ===");
            System.out.print("Inserisci il tuo email: ");
            String email = scanner.nextLine().trim(); // Rimuove spazi inutili
            System.out.print("Inserisci la tua password: ");
            String password = scanner.nextLine().trim(); // Rimuove spazi inutili

            // Lettura del file txt
            try (BufferedReader reader = new BufferedReader(new FileReader(FILE_UTENTI))) {
                String line;

                while ((line = reader.readLine()) != null) {
                    // Rimuove gli spazi dai dati del file prima del confronto
                    String[] credenziali = line.trim().split(",");
                    if (credenziali.length >= 4) { // Assicura che ci siano abbastanza campi
                        if (credenziali[3].trim().equals(email) && credenziali[4].trim().equals(password)) {
                            trovato = true;

                            // Recupera il nome dell'utente per il messaggio di benvenuto
                            String nome = credenziali[0].trim(); // Afferra il primo campo come nome
                            System.out.println("Login avvenuto con successo! \nBenvenuto/a " + nome + "!");
                            break; // Esci dal ciclo una volta trovata la corrispondenza
                        }
                    }
                }

                // Controllo finale dopo il ciclo
                if (!trovato) { // Se non trovato
                    System.out.println("Email o password errati!");
                    System.out.print("Vuoi riprovare? (sì/no): ");
                    String risposta = scanner.nextLine().trim();
                    if (risposta.equalsIgnoreCase("no")) { // Gestisce "No" in maiuscolo/minuscolo
                        System.out.println("Grazie per aver usato il nostro servizio!");
                        scanner.close();
                        return;
                    }
                }
            } catch (IOException e) { // Gestione eccezione per problemi con il file
                System.out.println("Errore durante il login. Riprova più tardi o effettua la registrazione.");
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
                    sessioneAttiva = false;
                    return;
                default:
                    System.out.println("Scelta non valida!");
            } // chiusura switch
        } //chiusura while
    } // chiusura main
}