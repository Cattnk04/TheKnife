package Dominio;

import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.io.FileWriter;

public class ListaUtenti {
    public ListaUtenti(){
        if (listaUtenti.isEmpty())
            ricavaUtentiDaCSV();
    }
    public static List<Utente> listaUtenti = new ArrayList<Utente>();
    public List<Utente> getListaUtenti(){
        return listaUtenti;
    }

    public void salvaUtentiSuCSV(){
        File file = new File("src/Data/Utenti.txt");
        file.getParentFile().mkdirs(); // Crea le directory se non esistono
    
        try (FileWriter writer = new FileWriter(file)) {  // Uso del try-with-resources
            for(Utente utente : listaUtenti){
                writer.append(utente.toString()).append("\n");
            }
        } catch (IOException e){
            System.out.println("Errore durante il salvataggio: " + e.getMessage());
            e.printStackTrace();
        }
    }

private void ricavaUtentiDaCSV() {
    try {
        FileReader reader = new FileReader("src/Data/Utenti.txt");
        BufferedReader bufferedReader = new BufferedReader(reader);
        String riga;
        while ((riga = bufferedReader.readLine()) != null) {
            if (!riga.trim().isEmpty()) {  // Verifica che la riga non sia vuota
                String[] dati = riga.split(",");
                if (dati.length >= 7) {  // Verifica che ci siano tutti i campi necessari
                    String email = dati[0].trim();
                    String nome = dati[1].trim();
                    String cognome = dati[2].trim();
                    String password = dati[3].trim();
                    String nazione = dati[4].trim();
                    String citta = dati[5].trim();
                    boolean ristoratore = Boolean.parseBoolean(dati[6].trim());
                    Utente utente = new Utente(email, nome, cognome, password, nazione, citta, ristoratore);
                    this.listaUtenti.add(utente);
                } else {
                    System.out.println("Avviso: Riga del file non valida (campi insufficienti): " + riga);
                }
            }
        }
        bufferedReader.close();
        reader.close();
    } catch (FileNotFoundException e) {
        System.out.println("File Utenti.txt non trovato. Verrà creata una nuova lista utenti.");
    } catch (IOException e) {
        System.out.println("Errore durante la lettura del file: " + e.getMessage());
    }
}

    public void aggiungiUtente(Utente nuovoUtente){
        listaUtenti.add(nuovoUtente);
        salvaUtentiSuCSV(); // Aggiungi questa riga per salvare su file
    }

public Utente trovaUtente(String email, String password) {
    
    for(Utente utente : listaUtenti) {
        if(utente.getEmail().equals(email)) {
            if(utente.getPassword().equals(password)) {
                return utente;
            } else {
                System.out.println("Password non corretta per l'utente: " + email);
                return null;
            }
        }
    }
    System.out.println("Nessun utente trovato con email: " + email);
    return null;
}
    public boolean utenteDuplicato(Utente nuovoUtente){
        //scorrere la lista e verificare se esitono altri utenti con la stessa email del nuovo utente e in caso tornare true
        for(Utente utente : listaUtenti){
            if(utente.getEmail().equals(nuovoUtente.getEmail())){
                return true;
            }
        }
        return false;
    }
}