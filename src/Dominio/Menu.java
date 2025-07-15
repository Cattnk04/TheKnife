package Dominio;


import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * @author Catelli Elena, Pellegrini Gaia, Tancredi Giacomo, Rizzi Camilla
 * @version 1.0
 *
 * La classe {@code Menu} rappresenta il menu principale dell'applicazione.
 * Gestisce la logica per utenti ospiti, registrazione, login e accesso ai sottomenu
 * per ristoratori o utenti registrati.
 *
 * Il menu offre le seguenti opzioni:
 * <ul>
 *   <li>Registrazione nuovo utente</li>
 *   <li>Login utente esistente</li>
 *   <li>Ricerca ristorante</li>
 *   <li>Uscita dall'applicazione</li>
 * </ul>
 *
 *
 */

public class Menu {

    /** Scanner condiviso per l'interazione da console */
    private static final Scanner scanner = new Scanner(System.in);

    private MenuRistoratore menuRistoratore;
    private MenuUtenteLog menuUtenteLog;

    /**
     * Costruttore principale della classe.
     * Inizializza le liste di utenti e ristoranti e gestisce l'interazione utente in base alla scelta effettuata.
     */
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
                            menuRistoratore = new MenuRistoratore(utenteCorrente);
                        } else {
                            menuUtenteLog = new MenuUtenteLog(utenteCorrente);
                        }
                    }
                    break;
                case 2:
                    utenteCorrente = loginUtente(listaUtenti);
                    if(utenteCorrente != null){
                        System.out.println("Login avvenuto con successo!");
                        if(utenteCorrente.getRistoratore()) {
                            menuRistoratore = new MenuRistoratore(utenteCorrente);
                        } else {
                            menuUtenteLog = new MenuUtenteLog(utenteCorrente);
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
        } catch (Exception e) {
            System.out.println("Errore imprevisto: " + e.getMessage());
            scelta = -1;
        }
    } while (scelta != 0);

}   /**
     * Visualizza il menu per l'utente ospite e gestisce l'input.
     *
     * @return un intero corrispondente alla scelta dell'utente
     */
    //Metodo per la scelta fatta dell'utente ospite
    public static int menuGuest() {
        int choice = -1;
        System.out.println("\nBenvenuto nella schermata home ospite!");
        System.out.println("Scegli un'opzione:");
        System.out.println("1. Registrati");
        System.out.println("2. Accedi");
        System.out.println("3. Cerca ristorante");
        System.out.println("0. Esci dall'applicazione");
        System.out.print("La tua scelta: ");

        try {
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
            } else {
                throw new InputMismatchException("Input non numerico.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Input non valido! Inserisci un numero.");
        } finally {
            scanner.nextLine(); // pulizia del buffer
        }
        scanner.nextLine(); // pulizia della linea
        return choice;
    }
    /**
     * Registra un nuovo utente chiedendo i dati necessari e aggiungendolo alla lista.
     *
     * @param listaUtenti la lista contenente tutti gli utenti registrati
     * @return l'oggetto {@code Utente} appena registrato, oppure {@code null} se si verifica un errore
     */
    //Metodo per la registrazione
    public static Utente registraUtente(ListaUtenti listaUtenti){
        try {
            Utente nuovoUtente = new Utente();
            return listaUtenti.aggiungiUtente(nuovoUtente);
        } catch (Exception e) {
            System.out.println("Errore nella creazione dell'utente: " + e.getMessage());
            return null;
        }
    }
    /**
     * Esegue il login di un utente già registrato.
     *
     * @param listaUtenti la lista di utenti registrati
     * @return l'oggetto {@code Utente} autenticato, oppure {@code null} se si verifica un errore
     */
    //Metodo per il login
    public static Utente loginUtente(ListaUtenti listaUtenti) {
        try {
            return listaUtenti.loginUtente();
        } catch (Exception e) {
            System.out.println("Errore durante il login: " + e.getMessage());
            return null;
        }
    }
}