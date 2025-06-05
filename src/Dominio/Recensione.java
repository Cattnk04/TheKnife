package Dominio;

import java.util.Scanner;

public class Recensione {
    String email;
    String nomeRistorante;
    int valutazione;
    String recensione;
    String risposta;

    public Recensione(Utente utente, ListaRistoranti ristoranti) {
        this.email = utente.getEmail();
        this.nomeRistorante = ristoranti.cercaPerNome("Inserisci il nome del ristorante a cui vuoi lasciare una recensione: ").getNome();
        this.valutazione = valutazione();
        this.recensione = recensione();
        this.risposta = null;
    }
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
        do{
            System.out.print("Puoi inserire una valutazione da 1 a 5: ");
            int valutazione = scanner.nextInt();
        } while (valutazione < 1 || valutazione > 5);
        scanner.close();
        return valutazione;
    }
    //metodo per inserire recensione
    public String recensione(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Puoi inserire una recensione a questo ristorante: ");
        String recensione = scanner.nextLine();
        scanner.close();
        return recensione;
    }
    @Override
    public String toString(){
        return this.email + '*' + this.nomeRistorante + '*' + this.valutazione + '*' + this.recensione + '*' + this.risposta;
    }

}
