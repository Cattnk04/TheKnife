package Dominio;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Utente {
    public static final String FILE_UTENTI = "./Data/Utenti.txt";
    private static final Scanner scanner = new Scanner(System.in);
    private String nome;
    private String cognome;
    private String email;
    private String nazione;
    private String citta;
    private boolean ristoratore;
    private String password;

    public Utente(String email, String nome, String cognome, String password, String nazione, String citta, boolean ristoratore) throws RuntimeException{
        this.email = email.trim();
        this.nome = nome.trim();
        this.cognome = cognome.trim();
        this.nazione = nazione.trim();
        this.citta = citta.trim();
        this.ristoratore = ristoratore;
        this.password = password;
    }

    //Metodo Get
    public String getNome(){
        return  this.nome;
    }
    public String getCognome(){
        return  this.cognome;
    }
    public String getEmail(){
        return  this.email;
    }
    public String getNazione(){
        return  this.nazione;
    }
    public String getCitta(){
        return  this.citta;
    }
    public boolean getRistoratore(){
        return  this.ristoratore;
    }
    public String getPassword(){return this.password;}

    //Metodo Set
    public void setNome(String nome){
        this.nome = nome;
    }
    public void setCognome(String cognome){
        this.cognome = cognome;
    }
    public void setNazione(String nazione){
        this.nazione = nazione;
    }
    public void setCitta(String citta){
        this.citta = citta;
    }

    //Metodo to String
    @Override
    public String toString(){
        return email.trim().toLowerCase() + "," + nome.trim() + "," + cognome.trim() + "," + password.trim() + ","+ nazione.trim().toLowerCase() + ","+ citta.trim().toLowerCase() + "," + ristoratore;
    }

    //Metodo per la registrazione
    public Utente() {
        System.out.println("\n\n=== Registrazione ===");
        do{
            System.out.print("Inserisci il tuo nome: ");
            this.nome = scanner.nextLine().trim();
            if(this.nome.length()<=1)
                System.out.println("Nome non valido");
        }while (this.nome.length()<=1);
        do{
            System.out.print("Inserisci il tuo cognome: ");
            this.cognome = scanner.nextLine().trim();
            if(this.cognome.length()<=1){
                System.out.println("Cognome non valido");
            }
        } while (this.cognome.length()<=1);

        System.out.print("Inserisci la Nazione: ");
        this.nazione = scanner.nextLine().trim();

        System.out.print("Inserisci la provincia di domicilio: ");
        this.citta = scanner.nextLine().trim();

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
            email = scanner.nextLine().trim().toLowerCase();
            if(!email.contains("@") || !email.contains(".")){
                valido = false;
                System.out.println("Email non valida");
            }
        } while (!valido);
        this.email = email;
        String password = "";
        do{
            valido = true;
            System.out.print("Inserisci la tua password: ");
            password = scanner.nextLine();
            if(password.length()<8){
                valido = false;
                System.out.println("Password troppo corta, inserirne una più lunga.");
            }
        } while (!valido);


        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            // Converti i byte in una stringa esadecimale
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            this.password = hexString.toString().trim();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}