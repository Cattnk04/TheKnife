package Dominio;


import java.util.Scanner;

public class Menu {
    private static final String FILE_UTENTI = "src/Data/Utenti.txt";

    public Menu(){
        //Creazione delle diverse liste per l'accesso ai dati
        ListaUtenti listaUtenti = new ListaUtenti();
        ListaRistoranti listaRistoranti = new ListaRistoranti();
        int scelta = 0; //scelta del menu
        Utente utenteCorrente;
        do{
            scelta = menuGuest(new Scanner(System.in));
            switch(scelta){
                case 1:
                    utenteCorrente = registraUtente(listaUtenti); //chiamerà la funzione nella lista per aggiungere l'utente li
                    if(utenteCorrente != null){             //apertura menù utente
                        System.out.println("Registrazione avvenuta con successo!");
                        MenuUtenteLog menuUtenteLog = new MenuUtenteLog();
                    }
                    break;
                case 2:
                    utenteCorrente = loginUtente(listaUtenti);
                    if(utenteCorrente != null){             //apertura menù utente
                        System.out.println("Login avvenuto con successo!");
                        MenuUtenteLog menuUtenteLog = new MenuUtenteLog();
                    }
                    break;
                case 3:
                    listaRistoranti.cercaRistorante();
                    break;
                case 0:
                    System.out.println("Grazie per aver usato il nostro servizio!");
                    return;
                default:
                    System.out.println("Scelta non valida!");
            } // chiusura switch

        }while (scelta != 0);
    }

    public static int menuGuest(Scanner scanner){
        int choice;
        System.out.println("Benvenuto nella schermata home ospite!\n");
        System.out.println("Scegli un'opzione:");
        System.out.println("1. Registrati");
        System.out.println("2. Accedi");
        System.out.println("3. Cerca ristorante");
        System.out.println("0. Esci dall'applicazione");
        System.out.print("La tua scelta: ");

        choice = scanner.nextInt();

        scanner.nextLine(); // Pulizia della linea
        return choice;
    }
    // metodo per la registrazione
    public static Utente registraUtente(ListaUtenti listaUtenti){
        Scanner scanner = new Scanner(System.in); //per inserire i dati
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

        String nazione = "";
        System.out.print("Inserisci la Nazione: ");
        nazione = scanner.nextLine();

        String citta = "";
        System.out.print("Inserisci la provincia di domicilio (prima lettera maiuscola): ");
        citta = scanner.nextLine();

        boolean valido,ristoratore = false;
        do{
            valido = true;
            System.out.print("Sei proprietario di un ristorante? [s/n]: ");
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
    public static Utente loginUtente(ListaUtenti listaUtenti) {
        boolean trovato = false; // Flag per controllare se il login riesce
        Utente utente;
        Scanner scanner = new Scanner(System.in);
        do {
            //inserimento dei dati
            System.out.println("=== Login ===");
            System.out.print("Inserisci la tua e-mail: ");
            String email = scanner.nextLine().trim();
            System.out.print("Inserisci la tua password: ");
            //va fatta la cosa degli asterischi qui
            String password = scanner.nextLine().trim();
            //Fine inserimento dati da cercare

            //SCORRERE LA LISTA PER VEDERE SE ESISTE UN UTNTE CON QUESTI DATI
            utente = listaUtenti.trovaUtente(email, password);

            //altrimenti
            if (utente == null) {
                System.out.println("Email o password errati!");
                System.out.print("Vuoi riprovare? (sì/no): ");
                String risposta = scanner.nextLine().trim();
                if (risposta.equalsIgnoreCase("no")) {
                    System.out.println("Grazie per aver usato il nostro servizio!");
                    scanner.close();
                    return null;
                }
            }
        }while(!trovato);
        return null;
    }
}
