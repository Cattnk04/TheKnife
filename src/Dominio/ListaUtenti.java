package Dominio;

import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.io.FileWriter;
import java.util.Scanner;

public class ListaUtenti {

    public static List<Utente> listaUtenti = new ArrayList<Utente>();

    //Costruttore
    public ListaUtenti(){
        if (listaUtenti.isEmpty())
            ricavaUtentiDaCSV();
    }

    //Metodo Get
    public List<Utente> getListaUtenti(){
        return listaUtenti;
    }

    //Metodo per salvare gli utenti sul CSV
    public void salvaUtentiSuCSV(){
        File file = new File(Utente.FILE_UTENTI);
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

    //Metodo per ricavare dal CSV
    private void ricavaUtentiDaCSV() {
        try {
            FileReader reader = new FileReader(Utente.FILE_UTENTI);
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

    //Metodo per aggiungere utente
    public Utente aggiungiUtente(Utente nuovoUtente){
        if(nuovoUtente != null && !utenteDuplicato(nuovoUtente)){
            listaUtenti.add(nuovoUtente);
            salvaUtentiSuCSV();
            return nuovoUtente;
        } else
            return null;
    }

    //Metodo per il login
    public Utente loginUtente(){
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Login ===");
            System.out.print("Inserisci la tua e-mail: ");
            String email = scanner.nextLine().trim();
            System.out.print("Inserisci la tua password: ");
            String password = scanner.nextLine().trim();

            Utente utente = trovaUtente(email, password);

            if (utente != null) {
                return utente;  // Ritorniamo l'utente se trovato
            } else {
                System.out.println("Email o password errati!");
                System.out.print("Vuoi riprovare? (sì/no): ");
                String risposta = scanner.nextLine().trim();

                if (risposta.equalsIgnoreCase("no")) {
                    System.out.println("Grazie per aver usato il nostro servizio!");
                    return null;
                }
            }
        }
    }

    //Metodo per cercare l'utente nel sistema
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

    //Controllo del duplicato
    public boolean utenteDuplicato(Utente nuovoUtente){
        //scorrere la lista e verificare se esistono altri utenti con la stessa email del nuovo utente e in caso tornare true
        for(Utente utente : listaUtenti){
            if(utente.getEmail().equals(nuovoUtente.getEmail())){
                return true;
            }
        }
        return false;
    }
}