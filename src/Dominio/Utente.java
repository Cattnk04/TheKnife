package Dominio;

import java.io.*;

public class Utente {
    public static final String FILE_UTENTI = "Utente.txt";
    private String nome;
    private String cognome;
    private final String email;
    private String nazione;
    private String citta;
    private final boolean ristoratore;
    private String password;

    public Utente() {
        this.nome = "";
        this.cognome = "";
        this.email = "";
        this.nazione = "";
        this.citta = "";
        this.ristoratore = false;
        this.password = "";
    }
    public Utente(String email, String nome, String cognome, String password, String nazione, String citta, boolean ristoratore) throws RuntimeException{
        this.email = email.trim();
        this.nome = nome.trim();
        this.cognome = cognome.trim();
        this.nazione = nazione.trim();
        this.citta = citta.trim();
        this.ristoratore = ristoratore;
        this.password = password;
    }

    public String getNome(){
        return  this.nome;
    }
    public void setNome(String nome){
        this.nome = nome;
    }
    public String getCognome(){
        return  this.cognome;
    }
    public void setCognome(String cognome){
        this.cognome = cognome;
    }
    public String getEmail(){
        return  this.email;
    }
    public String getNazione(){
        return  this.nazione;
    }
    public void setNazione(String nazione){
        this.nazione = nazione;
    }
    public String getCitta(){
        return  this.citta;
    }
    public void setCitta(String citta){
        this.citta = citta;
    }
    public boolean getRistoratore(){
        return  this.ristoratore;
    }
    public String getPassword(){return this.password;}

    @Override
    public String toString(){
        String stringa = email.trim().toLowerCase() + "," + nome.trim() + "," + cognome.trim() + "," + password.trim() + ","+ nazione.trim().toLowerCase() + ","+ citta.trim().toLowerCase() + "," + ristoratore;
        return stringa;
    }

}