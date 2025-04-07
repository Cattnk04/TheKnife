package Dominio;

public class Ristorante {
    private String nome;
    private String nazione;
    private String citta;
    private String indirizzo;
    private int fasciaPrezzo;
    private boolean servizioDelivery;
    private boolean servizioPrenotazioneOnline;
    private String tipoCucina;

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
    public int getFasciaPrezzo(){
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
    public void setFasciaPrezzo(int fasciaPrezzo){
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

}
