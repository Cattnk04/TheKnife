package Dominio;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Ristorante {
    private String nome;
    private String emailRistoratore;
    private String nazione;
    private String citta;
    private String indirizzo;
    private Integer fasciaPrezzo;
    private boolean servizioDelivery;
    private boolean servizioPrenotazioneOnline;
    private String tipoCucina;

    Ristorante(String nome, String emailRistoratore, String nazione, String citta, String indirizzo, Integer fasciaPrezzo, boolean servizioDelivery, boolean servizioPrenotazioneOnline, String tipoCucina){
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
    public Integer getFasciaPrezzo(){
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
    public void setFasciaPrezzo(Integer fasciaPrezzo){
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
    public void InserisciRistorante() {
        File fileRistoranti = new File("src/Dominio/Ristoranti.txt");

        try {
            FileWriter writer = new FileWriter(fileRistoranti, true);
            String stringa = this.nome + + ',' + this.emailRistoratore + ',' + this.nazione + ',' + this.citta + ',' + this.indirizzo + ',' + this.fasciaPrezzo.toString() + ','+ this.servizioDelivery + ',' + this.servizioPrenotazioneOnline + ',' + this.tipoCucina;
            writer.write(stringa);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
