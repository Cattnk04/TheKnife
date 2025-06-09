package Dominio;


import java.util.Scanner;
import java.util.InputMismatchException;

public class Menu {
    private static final String FILE_UTENTI = "src/Data/Utenti.txt";
    private static final Scanner scanner = new Scanner(System.in);

    public Menu(){
    //Creazione delle diverse liste per l'accesso ai dati
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
                            MenuRistoratore menuRistoratore = new MenuRistoratore(utenteCorrente);
                        } else {
                            MenuUtenteLog menuUtenteLog = new MenuUtenteLog(utenteCorrente);
                        }
                    }
                    break;
                case 2:
                    utenteCorrente = loginUtente(listaUtenti);
                    if(utenteCorrente != null){
                        System.out.println("Login avvenuto con successo!");
                        if(utenteCorrente.getRistoratore())
                            new MenuRistoratore(utenteCorrente);
                        else
                            new MenuUtenteLog(utenteCorrente);

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
        return choice;
    }

    // metodo per la registrazione
    public static Utente registraUtente(ListaUtenti listaUtenti){
        //Storing del nuovo utente nel file
        Utente nuovoUtente = new Utente();
        return listaUtenti.aggiungiUtente(nuovoUtente);
    }

    // metodo per il login
    public static Utente loginUtente(ListaUtenti listaUtenti) {
        return listaUtenti.loginUtente();

    }

}