package Dominio;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class ListaPreferiti {
    private static List<Preferito> listaPreferiti;

    //Costruttore
    public ListaPreferiti(){
        this.listaPreferiti = new ArrayList<>();
        ricavaPreferitiDaCSV();
    }

    //Metodo per leggere i preferiti da CSV
    private void ricavaPreferitiDaCSV(){
        try {
            FileReader reader = new FileReader("src/Data/Preferiti.txt");
            BufferedReader bufferedReader = new BufferedReader(reader);
            String riga;
            while ((riga = bufferedReader.readLine()) != null) {
                if (!riga.trim().isEmpty()) {  // Verifica che la riga non sia vuota
                    String[] dati = riga.split(",");
                    if (dati.length == 2) {  // Verifica che ci siano tutti i campi necessari
                        String emailUtente = dati[0];
                        String nomeRistorante = dati[1];
                        listaPreferiti.add(new Preferito(emailUtente, nomeRistorante));
                    } else {
                        System.out.println("Avviso: Riga del file non valida (campi insufficienti): " + riga);
                    }
                }
            }
            bufferedReader.close();
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File Preferiti.txt non trovato. Verrà creata una nuova lista preferiti.");
        } catch (IOException e) {
            System.out.println("Errore durante la lettura del file: " + e.getMessage());
        }
    }

    //Metodo per salvare su CSV
    public void salvaPreferitiSuCSV(){
        File file = new File("src/Data/Preferiti.txt");
        file.getParentFile().mkdirs(); // Crea le directory se non esistono

        try (FileWriter writer = new FileWriter(file)) {  // Uso del try-with-resources
            for(Preferito preferito : listaPreferiti){
                writer.append(preferito.toString()).append("\n");
            }
        } catch (IOException e){
            System.out.println("Errore durante il salvataggio: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //Metodo per aggiungere un preferito
    public void aggiungiPreferito(Utente utenteCorrente, ListaRistoranti listaRistoranti){
        Preferito nuovoPreferito = new Preferito(utenteCorrente, listaRistoranti);
        if(nuovoPreferito != null && nuovoPreferito.getNomeRistorante() != null && !preferitoDuplicato(nuovoPreferito)) {
            listaPreferiti.add(nuovoPreferito);
            salvaPreferitiSuCSV();
            System.out.println("Ristorante aggiunto ai preferiti con successo.");
        } else {
            System.out.println("Impossibile aggiungere il ristorante ai preferiti.");
        }
    }

    //Controllo del duplicato
    public boolean preferitoDuplicato(Preferito nuovoPreferito){
        for(Preferito p : listaPreferiti){
            if(p.getNomeRistorante().equals(nuovoPreferito.getNomeRistorante()) && p.getEmailUtente().equals(nuovoPreferito.getEmailUtente())){
                return true;
            }
        }
        return false;
    }

    //Metodo per mostrare i preferiti
    public boolean mostraPreferiti(Utente utenteCorrente){
        List<Preferito> preferitiUtente = preferitiUtente(utenteCorrente);
        if (preferitiUtente.isEmpty()) {
            System.out.println("\nNon hai ancora aggiunto ristoranti ai preferiti.");
            return false;
        } else {
            System.out.println("\nI tuoi ristoranti preferiti:");
            for (Preferito p : preferitiUtente) {
                System.out.println(" - " + p.getNomeRistorante());
            }
        }
        return true;
    }

    // Metodo per rimuovere i preferiti
    public void rimuoviPreferito(Utente utente, ListaRistoranti listaRistoranti){
        if(mostraPreferiti(utente)){
            Ristorante ristorante = listaRistoranti.cercaPerNome("Inserisci il nome del ristorante da rimuovere dai preferiti: ");
            if(ristorante != null){
                // Utilizziamo Iterator per evitare ConcurrentModificationException
                Iterator<Preferito> iterator = listaPreferiti.iterator();
                boolean rimosso = false;
                while(iterator.hasNext()) {
                    Preferito p = iterator.next();
                    if(p.getNomeRistorante().equals(ristorante.getNome()) && 
                       p.getEmailUtente().equals(utente.getEmail())){
                        iterator.remove();
                        rimosso = true;
                        break;
                    }
                }
                if(rimosso) {
                    System.out.println("Ristorante rimosso dai preferiti.");
                    salvaPreferitiSuCSV(); // Salva le modifiche su file
                } else {
                    System.out.println("Il ristorante non è presente nei tuoi preferiti.");
                }
            }
        }
    }

    //Metodo per filtrare e restituire solo i preferiti dell'utente corrente
    public List<Preferito> preferitiUtente (Utente utenteCorrente){
        List<Preferito> preferitiUtente = new ArrayList<>();
        for (Preferito p : listaPreferiti) {
            if(p.getEmailUtente().equals(utenteCorrente.getEmail())){
                preferitiUtente.add(p);
            }
        }
        return preferitiUtente;
    }
}