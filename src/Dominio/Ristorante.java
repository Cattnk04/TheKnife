package Dominio;

public class Ristorante {
    String nome;
    String nazione;
    String citta;
    String indirizzo;
    int fasciaPrezzo;
    boolean servizioDelivery;
    boolean servizioPrenotazioneOnline;
    String tipoCucina;

    Ristorante(String nome, String nazione, String citta, String indirizzo, int fasciaPrezzo, boolean servizioDelivery, boolean servizioPrenotazioneOnline, String tipoCucina){
        this.nome = nome;
        this.nazione = nazione;
        this.citta = citta;
        this.indirizzo = indirizzo;
        this.fasciaPrezzo = fasciaPrezzo;
        this.servizioDelivery = servizioDelivery;
        this.servizioPrenotazioneOnline = servizioPrenotazioneOnline;
        this.tipoCucina = tipoCucina;
    }

    public String getNome(){
        return nome;
    }
    public String getNazione(){
        return nazione;
    }
    public String getCitta(){
        return citta;
    }
    public String getIndirizzo(){
        return indirizzo;
    }
    public int getFasciaPrezzo(){
        return fasciaPrezzo;
    }
    public boolean getServizioDelivery(){
        return servizioDelivery;
    }
    public boolean getServizioPrenotazioneOnline(){
        return servizioPrenotazioneOnline;
    }
    public String getTipoCucina(){
        return tipoCucina;
    }




}
