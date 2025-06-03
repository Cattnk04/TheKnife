package Dominio;

import java.util.Scanner;

public class Recensione {
    String email;
    String nomeRistorante;
    int valutazione;
    String recensione;

    public Recensione(Utente utente, ListaRistoranti ristoranti) {
        this.email = utente.getEmail();
        this.nomeRistorante = ristoranti.cercaPerNome().getNome();
        this.valutazione = valutazione();
        this.recensione = recensione();
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
    //SET
    public void setRecensione(String recensione){
        this.recensione = recensione;
    }
    public void setValutazione(int valutazione){
        this.valutazione = valutazione;
    }
    //Metodo per inserire valutazione
    public int valutazione(){
        do{
            Scanner scanner = new Scanner(System.in);
            System.out.print("Puoi inserire una valutazione da 1 a 5: ");
            int valutazione = scanner.nextInt();
        } while (valutazione < 1 || valutazione > 5);
        return valutazione;
    }
    //metodo per inserire recensione
    public String recensione(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Puoi inserire una recensione a questo ristorante: ");
        String recensione = scanner.nextLine();
        return recensione;
    }
    @Override
    public String toString(){
        return this.email + '*' + this.nomeRistorante + '*' + this.valutazione + '*' + this.recensione;
    }

}
