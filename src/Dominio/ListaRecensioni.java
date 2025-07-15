package Dominio;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ListaRecensioni {

    private List<Recensione> listaRecensioni = new ArrayList<>();

    //Costruttore
    public ListaRecensioni() {
        if (listaRecensioni.isEmpty()){
            ricavaRecensioniDaCSV();
        }
    }

    //Metodi Get e Set
    public List<Recensione> getListaRecensione() {
        return listaRecensioni;
    }
    public void setListaRecensione(List<Recensione> listaRecensione) {
        this.listaRecensioni = listaRecensione;
    }

    //Metodo per salvare su CSV
    public void salvaRecensioniSuCSV() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Recensione.FILE_RECENSIONI))){
            for (Recensione r :listaRecensioni){
                writer.write(r.toString());
                writer.newLine();
            }
    } catch(IOException e){
            System.err.println("Errore durante il salvataggio della recensione: " + e.getMessage());
        }
    }

    //Metodo per ricavare da CSV
    public void ricavaRecensioniDaCSV() {
        try (BufferedReader reader = new BufferedReader(new FileReader(Recensione.FILE_RECENSIONI))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {  // Ignora le righe vuote
                    String[] riga = line.split("\\*");  // Usa \\* come separatore
                    if (riga.length >= 5) {
                        String email = riga[0];
                        String nomeRistorante = riga[1];
                        Integer valutazione = Integer.parseInt(riga[2]);
                        String recensione = riga[3];
                        String risposta = riga[4];
                        Recensione r = new Recensione(email, nomeRistorante, valutazione, recensione, risposta);
                        listaRecensioni.add(r);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Errore nel caricamento delle recensioni: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Errore nel parsing della valutazione: " + e.getMessage());
        }
    }

    //Metodo per aggiungere una recensione al file di recensioni
    public void inserisciRecensione(Utente utente, ListaRistoranti listaRistoranti){
        Recensione recensione = new Recensione(utente, listaRistoranti);
        if (recensioneDuplicato(recensione)){
            System.out.println("Hai già lasciato una recensione a questo ristorante!");
        } else {
            listaRecensioni.add(recensione);
            salvaRecensioniSuCSV();
            System.out.println("Recensione aggiunta con successo!");
        }
    }
    
    //Metodo per il controllo della duplicazione delle recensioni
    public boolean recensioneDuplicato(Recensione recensione){
        for(Recensione r : listaRecensioni){
            if(r.getEmail().equals(recensione.getEmail()) && r.getNomeRistorante().equals(recensione.getNomeRistorante())){
                return true;
            }
        }
        return false;
    }

    //Metodo per modificare una recensione
    public void modificaRecensione(Utente utente) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Inserisci il nome del ristorante da modificare: ");
        String nomeRistorante = scanner.nextLine();
        System.out.print("Inserisci il nuovo testo della recensione: ");
        String nuovoTesto = scanner.nextLine();
        System.out.print("Inserisci il nuovo numero di stelle (1-5): ");
        int nuoveStelle = scanner.nextInt();
        scanner.nextLine(); // Pulizia buffer
        String emailUtente = utente.getEmail();

        try {
            if (nuoveStelle < 1 || nuoveStelle > 5) {
                System.out.print("Il numero di stelle deve essere tra 1 e 5\n");
                return;
            }

            boolean recensioneTrovata = false;
            for (Recensione r : listaRecensioni) {
                if (r.getEmail().equals(emailUtente) && r.getNomeRistorante().equals(nomeRistorante)) {
                    r.setRecensione(nuovoTesto);
                    r.setValutazione(nuoveStelle);
                    recensioneTrovata = true;
                    break;
                }
            }

            if (!recensioneTrovata) {
                System.out.print("Recensione non trovata\n");
                return;
            }

            salvaRecensioniSuCSV();
            System.out.print("Recensione modificata con successo\n");
        } catch (IllegalArgumentException e) {
            System.out.print("Errore: " + e.getMessage());
        }
    }
    
    //Metodo per eliminare una recensione
    public void eliminaRecensione(Utente utente) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Inserisci il nome del ristorante di cui eliminare la recensione: ");
        String nomeRistorante = scanner.nextLine();
        String email = utente.getEmail();

        boolean recensioneTrovata = false;
        for (Recensione r : listaRecensioni) {
            if (r.getEmail().equals(email) && r.getNomeRistorante().equals(nomeRistorante)) {
                listaRecensioni.remove(r);
                recensioneTrovata = true;
                break;
            }
        }

        if (recensioneTrovata) {
            salvaRecensioniSuCSV();
            System.out.println("Recensione eliminata con successo\n");
        } else {
            System.out.println("Recensione non trovata\n");
        }
    }

    //Metodo per visuallizare le recensioni del ristorante selezionato dopo il cerca RiSTORANTE
    public void mostraRecensioniRistorante(Ristorante ristorante) {
        List<Recensione> recensioniRistorante = recensioniRistorante(ristorante.getNome());

        if (recensioniRistorante == null || recensioniRistorante.isEmpty()) {
            System.out.println("\nNon ci sono ancora recensioni per questo ristorante.");
            return;
        }

        System.out.println("\n=== Recensioni del ristorante ===");
        for (Recensione recensione : recensioniRistorante) {
            System.out.println("\n" + recensione.stampaRecensione());
            System.out.println("----------------------------------------");
        }
    }

    //Metodo per la stampa delle recensioni per il menu utente log
    public void mostraRecensioniUtente(Utente utente) {
        List<Recensione> recensioni = recensioniUtente(utente);
        if (recensioni == null || recensioni.isEmpty()) {
            System.out.println("Non hai ancora scritto recensioni.");
        } else {
            System.out.println("\n=== Le tue recensioni ===");
            for (Recensione r : recensioni) {
                System.out.println("\nRistorante: " + r.getNomeRistorante());
                System.out.println(r.stampaRecensione());
                System.out.println("------------------------");
            }
        }
    }


    //Metodo per filtrare e restituire solo per le recensioni appartenenti al ristorante inserito
    public List<Recensione> recensioniRistorante(String nomeRistorante){
        List<Recensione> recensioniRistorante = new ArrayList<>();
        for(Recensione r : listaRecensioni){
            if(r.getNomeRistorante().equals(nomeRistorante)){
                recensioniRistorante.add(r);
            }
        }
        if(recensioniRistorante.isEmpty()){
            return null;
        } else {
            return recensioniRistorante;
        }
    }

    //Metodo per filtrare e restituire solo le recensioni dell'utente corrente
    public List<Recensione> recensioniUtente(Utente utente) {
        List<Recensione> recensioniUtente = new ArrayList<>();
        for (Recensione r : listaRecensioni) {
            if (r.getEmail().equals(utente.getEmail())) {
                recensioniUtente.add(r);
            }
        }
        if (recensioniUtente.isEmpty()) {
            return null;
        } else {
            return recensioniUtente;
        }
    }
}