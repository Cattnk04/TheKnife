package Dominio;

import java.io.*;
import java.util.Locale;
import java.util.Scanner;

public class Utente {
    public static final String FILE_UTENTI = "src/Data/Utenti.txt";
    private String nome;
    private String cognome;
    private String email;
    private String nazione;
    private String citta;
    private boolean ristoratore;
    private String password;

    public Utente() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n=== Registrazione ===");
        do{
            System.out.print("Inserisci il tuo nome: ");
            this.nome = scanner.nextLine();
            if(this.nome.length()<=1)
                System.out.println("Nome non valido");
        }while (this.nome.length()<=1);
        do{
            System.out.print("Inserisci il tuo cognome: ");
            this.cognome = scanner.nextLine();
            if(this.cognome.length()<=1){
                System.out.println("Cognome non valido");
            }
        } while (this.cognome.length()<=1);

        System.out.print("Inserisci la Nazione: ");
        this.nazione = scanner.nextLine();

        System.out.print("Inserisci la provincia di domicilio: ");
        this.citta = scanner.nextLine();

        boolean valido = false;
        do{
            valido = true;
            System.out.print("Sei proprietario di un ristorante? [s/n]: ");
            String risposta = scanner.nextLine().trim().toLowerCase(); // Salva l'input in una variabile
            if(risposta.equals("s"))
                this.ristoratore = true;
            else if (risposta.equals("n"))
                this.ristoratore = false;
             else
                valido = false;

        } while (!valido);

        String email = "";
        do{
            valido = true;
            System.out.print("Inserisci la tua email: ");
            this.email = scanner.nextLine().toLowerCase();
            if(!this.email.contains("@") || !email.contains(".")){
                valido = false;
                System.out.println("Email non valida");
            }
        } while (!valido);
        String password = "";
        do{
            valido = true;
            System.out.print("Inserisci la tua password: ");
            this.password = scanner.nextLine();
            //VEDERE COME FAR VISUALIZZARE GLI ASTERISCHI INVECE DELLA STRINGA
            if(password.length()<8){
                valido = false;
                System.out.println("Password troppo corta, inserirne una più lunga.");
            }
        } while (!valido);

        scanner.close();
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
        return email.trim().toLowerCase() + "," + nome.trim() + "," + cognome.trim() + "," + password.trim() + ","+ nazione.trim().toLowerCase() + ","+ citta.trim().toLowerCase() + "," + ristoratore;
    }

}