package Dominio;

public class Utenti {
    private String nome;
    private String cognome;
    private String email;
    private String username;
    private String provincia;
    private boolean ristoratore;
    private String passwordHash;

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
    public void setEmail(String email){
        this.email = email;
    }
    public String getCitta(){
        return  this.provincia;
    }
    public void setCitta(String provincia){
        this.provincia = provincia;
    }
    public boolean getRistoratore(){
        return  this.ristoratore;
    }
}
