package Dominio;

import java.io.*;

public class Utenti {
    public static final String FILE_UTENTI = "Utenti.txt";
    private String nome;
    private String cognome;
    private final String email;
    private String provincia;
    private final boolean ristoratore;
    private String passwordHash;

    public Utenti(String email,String nome,String cognome, String provincia, boolean ristoratore, String passwordHash) throws RuntimeException{
        controllaUtenteDuplicato(email.trim());
        this.email = email.trim();
        controllaUtenteDuplicato(email);
        this.nome = nome.trim();
        this.cognome = cognome.trim();
        this.provincia = provincia.trim();
        this.ristoratore = ristoratore;
        this.passwordHash = passwordHash;
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
    public void controllaUtenteDuplicato(String email) throws RuntimeException{
        try{
            FileReader fileReader = new FileReader(FILE_UTENTI);
            BufferedReader br = new BufferedReader(fileReader);
            String stringa = br.readLine();
            while(stringa!=null){
                String[] arrayStringaUtente = stringa.split(",");
                if(arrayStringaUtente[0].equals(email))
                    throw new RuntimeException("Utente non puo' essere duplicato");
                else
                    stringa = br.readLine();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File non trovato");
        } catch (IOException e) {
            throw new RuntimeException("IO Exception");
        }
    }
    public void salvaUtente(){
        File fileUtenti = new File(FILE_UTENTI);
        try{
            FileWriter fileWriter = new FileWriter(fileUtenti, true);
            String stringaUtente = this.email + ',' + this.nome + ',' + this.cognome + ',' + this.provincia + ',' + this.ristoratore + ',' + this.passwordHash;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}