package Dominio;

import java.io.*;

public class Utente {
    public static final String FILE_UTENTI = "Utente.txt";
    private String nome;
    private String cognome;
    private final String email;
    private String provincia;
    private final boolean ristoratore;
    private String password;

    public Utente() {
        this.nome = "";
        this.cognome = "";
        this.email = "";
        this.provincia = "";
        this.ristoratore = false;
        this.password = "";
    }
    public Utente(String email, String nome, String cognome, String password, String provincia, boolean ristoratore) throws RuntimeException{
        this.email = email.trim();
        this.nome = nome.trim();
        this.cognome = cognome.trim();
        this.provincia = provincia.trim();
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
    public String getProvincia(){
        return  this.provincia;
    }
    public void setProvincia(String provincia){
        this.provincia = provincia;
    }
    public boolean getRistoratore(){
        return  this.ristoratore;
    }
    public String getPassword(){return this.password;}

    public String toString(){
        String stringa = email.trim().toLowerCase() + "," + nome.trim() + "," + cognome.trim() + "," + password.trim() + ","+ provincia.trim() + "," + ristoratore;
        return stringa;
    }

}