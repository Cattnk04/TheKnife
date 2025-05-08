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
        this.email = email.trim();
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

    //Metodo per controllo duplicato utente
    public void controllaUtenteDuplicato() throws RuntimeException{
        File fileUtenti = new File("src/Dominio/Utenti.txt");

        // Verifica se il file esiste, se no, lo crea
        if (!fileUtenti.exists()) {
            try {
                fileUtenti.createNewFile();  // Crea il file se non esiste
            } catch (IOException e) {
                System.out.println("Errore durante la creazione del file Utenti.txt.");
                e.printStackTrace();
            }
        }

        // Aggiungi la logica per verificare se l'utente esiste già
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileUtenti));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] credenziali = line.split(",");
                if (credenziali.length >= 6 && credenziali[4].trim().equals(email)) {
                    throw new RuntimeException("Utente già registrato con questa email!");
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Errore durante la lettura del file Utenti.txt.");
            e.printStackTrace();
        }
    }

    //Metodo di salvataggio utente
    public void salvaUtente(){
        // Usa il percorso relativo per aggiungere l'utente al file "src/Dominio/Utenti.txt"
        try {
            FileWriter writer = new FileWriter("src/Dominio/Utenti.txt", true);  // Aggiungi l'utente al file esistente
            writer.write(email + "," + nome + "," + cognome + "," + provincia + "," + ristoratore + "," + passwordHash + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Errore durante il salvataggio dell'utente.");
            e.printStackTrace();
        }
    }
}