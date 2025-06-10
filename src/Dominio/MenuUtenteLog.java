package Dominio;

import java.util.*;

public class MenuUtenteLog {

    private static final Scanner scanner = new Scanner(System.in);
    private ListaPreferiti listaPreferiti = new ListaPreferiti();
    private ListaRecensioni listaRecensioni = new ListaRecensioni();
    private static final String FILE_PREFERITI = "src/Data/Preferiti.txt";
    private static final String FILE_RECENSIONI = "src/Data/Recensioni.txt";
    private Utente utenteCorrente;
    private ListaRistoranti listaRistoranti;



    public MenuUtenteLog(Utente utente) { // Aggiungi i parametri
        listaPreferiti = new ListaPreferiti();
        listaRecensioni = new ListaRecensioni();
        this.utenteCorrente = utente;
        this.listaRistoranti = new ListaRistoranti();

    }

    public void mostraMenuUtente(){
        int scelta = 0;
        do{
            try{ //il try catch è inutile in quanto c'è gia il caso di default
                System.out.println("\n=== Menu Utente ===");
                System.out.println("1. Cerca un ristorante");
                System.out.println("2. Visualizza i tuoi preferiti");
                System.out.println("3. Aggiungi ristorante ai preferiti");
                System.out.println("4. Rimuovi ristorante dai preferiti");
                System.out.println("5. Visualizza le tue recensioni");
                System.out.println("6. Aggiungi recensione");
                System.out.println("7. Modifica recensione");
                System.out.println("8. Elimina recensione");
                System.out.println("0. Logout");
                System.out.print("La tua scelta: ");

                scelta = scanner.nextInt();
                scanner.nextLine(); // Pulizia buffer

                switch (scelta) {
                    case 1:
                        listaRistoranti.cercaRistorante();
                        break;
                    case 2:
                        mostraPreferiti();
                        break;
                    case 3:
                        aggiungiPreferito();
                        break;
                    case 4:
                        rimuoviPreferito();
                        break;
                    case 5:
                        mostraRecensioni();
                        break;
                    case 6:
                        aggiungiRecensione();
                        break;
                    case 7:
                        modificaRecensione();
                        break;
                    case 8:
                        eliminaRecensione();
                        break;
                    case 0:
                        System.out.println("Logout effettuato con successo!");
                        break;
                    default:
                        System.out.println("Scelta non valida!");

                }
            }
            catch (InputMismatchException e) {
                System.out.println("Inserire un numero valido!");
                scanner.nextLine(); // Pulizia buffer
                scelta = -1;
            }
        } while (scelta != 0);
    }

    //Mostra i ristoranti preferiti dell'utente
    private void mostraPreferiti() {
        //List<Preferito> preferitiUtente = listaPreferiti.preferitiUtente(utenteCorrente);
        listaPreferiti.mostraPreferiti(utenteCorrente);
    }

    // Aggiunta ristorante ai preferiti dell'utente con controllo duplicati
    public void aggiungiPreferito() {
        listaPreferiti.aggiungiPreferito(utenteCorrente, listaRistoranti);
    }

    // Rimuovi ristorante dai preferiti dell'utente
    public void rimuoviPreferito() {
        listaPreferiti.rimuoviPreferito(utenteCorrente, listaRistoranti);
    }

    /* Salva recensioni su file
    private void salvaRecensioniSuFile() { listaRecensioni.ricavaRecensioniDaCSV();}
    non sembra servire */

    //Mostra le recensioni dell'utente
    private void mostraRecensioni() { listaRecensioni.mostraRecensioniUtente(utenteCorrente);}

    // Aggiunta recensione
    public void aggiungiRecensione() { listaRecensioni.inserisciRecensione(utenteCorrente, listaRistoranti);}

    // Modifica recensione
    public void modificaRecensione() { listaRecensioni.modificaRecensione(utenteCorrente);}

    // Elimina recensione
    public void eliminaRecensione() { listaRecensioni.eliminaRecensione(utenteCorrente);}
}