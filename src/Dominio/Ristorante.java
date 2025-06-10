package Dominio;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Ristorante {

    private String nome;
    private String emailRistoratore;
    private String nazione;
    private String citta;
    private String indirizzo;
    private double fasciaPrezzo;
    private boolean servizioDelivery;
    private boolean servizioPrenotazioneOnline;
    private String tipoCucina;

    //Costruttore 1
    public Ristorante(String nome, String emailRistoratore, String nazione, String citta, String indirizzo, double fasciaPrezzo, boolean servizioDelivery, boolean servizioPrenotazioneOnline, String tipoCucina){
        this.nome = nome.trim();
        this.emailRistoratore = emailRistoratore.trim();
        this.nazione = nazione.trim();
        this.citta = citta.trim();
        this.indirizzo = indirizzo.trim();
        this.fasciaPrezzo = fasciaPrezzo;
        this.servizioDelivery = servizioDelivery;
        this.servizioPrenotazioneOnline = servizioPrenotazioneOnline;
        this.tipoCucina = tipoCucina.trim();
    }

    public Ristorante(Utente utenteRistoratore){
        this.emailRistoratore = utenteRistoratore.getEmail();
        inserisciDatiRistorante();
    }

    // Metodo get
    public String getNome(){
        return this.nome;
    }
    public String getNazione(){
        return this.nazione;
    }
    public String getCitta(){
        return this.citta;
    }
    public String getIndirizzo(){
        return this.indirizzo;
    }
    public double getFasciaPrezzo(){
        return this.fasciaPrezzo;
    }
    public boolean getServizioDelivery(){
        return this.servizioDelivery;
    }
    public boolean getServizioPrenotazioneOnline(){
        return this.servizioPrenotazioneOnline;
    }
    public String getTipoCucina(){
        return this.tipoCucina;
    }
    public Object getEmailRistoratore() {
        return emailRistoratore;
    }

    //Metodo set
    public void setNome(String nome){
        this.nome = nome;
    }
    public void setNazione(String nazione){
        this.nazione = nazione;
    }
    public void setCitta(String citta){
        this.citta = citta;
    }
    public void setIndirizzo(String indirizzo){
        this.indirizzo = indirizzo;
    }
    public void setFasciaPrezzo(double fasciaPrezzo){
        this.fasciaPrezzo = fasciaPrezzo;
    }
    public void setServizioDelivery(boolean servizioDelivery){
        this.servizioDelivery = servizioDelivery;
    }
    public void setServizioPrenotazioneOnline(boolean servizioPrenotazioneOnline){
        this.servizioPrenotazioneOnline = servizioPrenotazioneOnline;
    }
    public void setTipoCucina(String tipoCucina){
        this.tipoCucina = tipoCucina;
    }

    //Metodo to string
    @Override
    public String toString(){
        return this.nome + ',' + this.emailRistoratore + ',' + this.nazione + ',' + this.citta + ',' + this.indirizzo + ',' + this.fasciaPrezzo + ','+ this.servizioDelivery + ',' + this.servizioPrenotazioneOnline + ',' + this.tipoCucina;
    }

    //Metodo per creare un ristorante
    private void inserisciDatiRistorante(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Inserisci il nome del ristorante:");
        this.setNome(scanner.nextLine());
        System.out.print("Inserisci il nazione del ristorante:");
        this.setNazione(scanner.nextLine());
        System.out.print("Inserisci la citta del ristorante:");
        this.setCitta(scanner.nextLine());
        System.out.print("Inserisci l'indirizzo del ristorante:");
        this.setIndirizzo(scanner.nextLine());
        System.out.print("Inserisci il prezzo medio del ristorante:");
        this.setFasciaPrezzo(scanner.nextDouble());
        boolean valido;
        do{
            valido = true;
            System.out.print("Il ristorante fornisce il servizio delivery? [s/n]");
            String risposta = scanner.nextLine().trim().toLowerCase(); // Salva l'input in una variabile
            if(risposta.equals("s")){
                this.setServizioDelivery(true);
            } else if (risposta.equals("n")){
                this.setServizioDelivery(false);
            } else {
                System.out.println("Devi inserire 's' o 'n'");
                valido = false;
            }
        } while (!valido);
        do{
            valido = true;
            System.out.println("Il ristorante accetta prenotazioni online? [s/n]");
            String risposta = scanner.nextLine().trim().toLowerCase();
            if(risposta.equals("s")){
                this.setServizioPrenotazioneOnline(true);
            } else if (risposta.equals("n")){
                this.setServizioPrenotazioneOnline(false);
            } else {
                System.out.println("Devi inserire 's' o 'n'");
                valido = false;
            }
        } while (!valido);
        System.out.println("Inserisci il tipo di cucina del ristorante: ");
        this.setTipoCucina(scanner.nextLine());
    }
}
