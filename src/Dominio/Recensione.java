package Dominio;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Recensione {

    public static final String FILE_RECENSIONI = "./Data/Recensioni.txt";
    String email;
    String nomeRistorante;
    int valutazione;
    String recensione;
    String risposta;


    //Costruttore 1
    public Recensione(Utente utente, ListaRistoranti ristoranti) {
        this.email = utente.getEmail();
        this.nomeRistorante = ristoranti.cercaPerNome("Inserisci il nome del ristorante a cui vuoi lasciare una recensione: ").getNome();
        this.valutazione = valutazione();
        this.recensione = recensione();
        this.risposta = null;
    }

    //Costruttore 2
    public Recensione(String email, String nomeRistorante, int valutazione, String recensione, String risposta) {
        this.email = email;
        this.nomeRistorante = nomeRistorante;
        this.valutazione = valutazione;
        this.recensione = recensione;
        this.risposta = risposta;
    }

    //GET
    public String getEmail(){
        return email;
    }
    public String getNomeRistorante(){
        return nomeRistorante;
    }
    public int getValutazione(){
        return valutazione;
    }
    public String getRecensione(){
        return recensione;
    }
    public String getRisposta(){
        return risposta;
    }

    //SET
    public void setRecensione(String recensione){
        this.recensione = recensione;
    }
    public void setValutazione(int valutazione){
        this.valutazione = valutazione;
    }

    //Metodo per inserire valutazione
    public int valutazione(){
        Scanner scanner = new Scanner(System.in);
        int valutazione;
        do{
            System.out.print("Puoi inserire una valutazione da 1 a 5: ");
            try {
                valutazione = scanner.nextInt();
                scanner.nextLine(); // consuma il newline
                if (valutazione >= 1 && valutazione <= 5) {
                    return valutazione;
                }
                System.out.println("Per favore inserisci un numero tra 1 e 5.");
            } catch (InputMismatchException e) {
                System.out.println("Per favore inserisci un numero valido.");
                scanner.nextLine(); // pulisce l'input non valido
                valutazione = 0;
            }
        } while (true);
    }

    //Metodo per inserire recensione
    public String recensione(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Puoi inserire una recensione a questo ristorante: ");
        String recensione = scanner.nextLine();
        return recensione;
    }

    //Metodo to String
    @Override
    public String toString(){
        return this.email + '*' + this.nomeRistorante + '*' + this.valutazione + '*' + this.recensione + '*' + this.risposta;
    }

    //Metodo per stampare le recensioni
    public String stampaRecensione(){
        System.out.println("\n----------------------------------------");
        String stringa =  "Valutazione: " + getValutazione() + "\nRecensione: " + getRecensione();
        if(getRisposta() != null){
            stringa += "\nRisposta: " + getRisposta();
        }
        return stringa;
    }

}