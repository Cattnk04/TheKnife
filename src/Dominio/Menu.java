package Dominio;


import java.util.Scanner;
import java.util.InputMismatchException;

public class Menu {
    private static final String FILE_UTENTI = "src/Data/Utenti.txt";

    public Menu(){
    //Creazione delle diverse liste per l'accesso ai dati
    Scanner scanner = new Scanner(System.in);  // Creiamo un solo Scanner
    ListaUtenti listaUtenti = new ListaUtenti();
    ListaRistoranti listaRistoranti = new ListaRistoranti();
    int scelta;
    Utente utenteCorrente;
    
    do {
        try {
            scelta = menuGuest();
            switch(scelta){
                case 1:
                    utenteCorrente = registraUtente(listaUtenti);
                    if(utenteCorrente != null){
                        System.out.println("Registrazione avvenuta con successo!");
                        if(utenteCorrente.getRistoratore()) {
                            MenuRistoratore menuRistoratore = new MenuRistoratore(utenteCorrente, scanner);
                        } else {
                            MenuUtenteLog menuUtenteLog = new MenuUtenteLog(utenteCorrente, scanner);
                        }
                    }
                    break;
                case 2:
                    utenteCorrente = loginUtente(listaUtenti, scanner);
                    if(utenteCorrente != null){
                        System.out.println("Login avvenuto con successo!");
                        if(utenteCorrente.getRistoratore()) {
                            MenuRistoratore menuRistoratore = new MenuRistoratore(utenteCorrente, scanner);
                        } else {
                            MenuUtenteLog menuUtenteLog = new MenuUtenteLog(utenteCorrente, scanner);
                        }
                    }
                    break;
                case 3:
                    listaRistoranti.cercaRistorante();
                    break;
                case 0:
                    System.out.println("Grazie per aver usato il nostro servizio!");
                    break;
                default:
                    System.out.println("Scelta non valida!");
            }
        } catch (InputMismatchException e) {
            System.out.println("Inserire un numero valido!");
            scanner.nextLine(); // Pulizia del buffer
            scelta = -1;
        }
    } while (scelta != 0);
    
    scanner.close(); // Chiudiamo lo Scanner solo alla fine del programma
}
    // metodo per la scelta dell'utente ospite
    public static int menuGuest() {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        System.out.println("Benvenuto nella schermata home ospite!\n");
        System.out.println("Scegli un'opzione:");
        System.out.println("1. Registrati");
        System.out.println("2. Accedi");
        System.out.println("3. Cerca ristorante");
        System.out.println("0. Esci dall'applicazione");
        System.out.print("La tua scelta: ");

        if (scanner.hasNextInt()) {
            choice = scanner.nextInt();
        } else {
            System.out.println("Input non valido!");
            scanner.nextLine(); // pulizia del buffer
        }

        scanner.nextLine(); // pulizia della linea
        scanner.close();
        return choice;
    }

    // metodo per la registrazione
    public static Utente registraUtente(ListaUtenti listaUtenti){
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n=== Registrazione ===");
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

        String nazione = "";
        System.out.print("Inserisci la Nazione: ");
        nazione = scanner.nextLine();

        String citta = "";
        System.out.print("Inserisci la provincia di domicilio: ");
        citta = scanner.nextLine();

        boolean valido,ristoratore = false;
        do{
            valido = true;
            System.out.print("Sei proprietario di un ristorante? [s/n]: ");
            String risposta = scanner.nextLine().trim().toLowerCase(); // Salva l'input in una variabile
            if(risposta.equals("s")){
                ristoratore = true;
            } else if (risposta.equals("n")){
                ristoratore = false;
            } else {
                valido = false;
            }
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

        scanner.close();
        //Storing del nuovo utente nel file
        Utente nuovoUtente = new Utente(email, nome, cognome, password, nazione, citta, ristoratore);
        if(!listaUtenti.utenteDuplicato(nuovoUtente)){
            listaUtenti.aggiungiUtente(nuovoUtente);
            return nuovoUtente;
        }
        return null;
    }

    // metodo per il login
    public static Utente loginUtente(ListaUtenti listaUtenti, Scanner scanner) {
    while (true) {  // Sostituiamo il do-while con un while(true)
        System.out.println("\n=== Login ===");
        System.out.print("Inserisci la tua e-mail: ");
        String email = scanner.nextLine().trim();
        System.out.print("Inserisci la tua password: ");
        String password = scanner.nextLine().trim();

        Utente utente = listaUtenti.trovaUtente(email, password);

        if (utente != null) {
            return utente;  // Ritorniamo l'utente se trovato
        }

        System.out.println("Email o password errati!");
        System.out.print("Vuoi riprovare? (sì/no): ");
        String risposta = scanner.nextLine().trim();
        if (risposta.equalsIgnoreCase("no")) {
            System.out.println("Grazie per aver usato il nostro servizio!");
            return null;
        }
    }
}
}