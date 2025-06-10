package Dominio;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ListaRecensioni {
    public ListaRecensioni() {
        if (listaRecensioni.isEmpty()){
            ricavaRecensioniDaCSV();
        }

    }
    public List<Recensione> listaRecensioni = new ArrayList<>();

    public List<Recensione> getListaRecensione() {
        return listaRecensioni;
    }
    public void setListaRecensione(List<Recensione> listaRecensione) {
        this.listaRecensioni = listaRecensione;
    }
    public void salvaRecensioniSuCSV() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/Data/Recensioni.txt"))){
            for (Recensione r :listaRecensioni){
                writer.write(r.toString() + "\n");
                writer.newLine();
            }
            writer.flush();
    }catch(IOException e){
            System.err.println("Errore durante il salvataggio della recensione: " + e.getMessage());
        }
    }
    public void ricavaRecensioniDaCSV(){
        List<Recensione> listaRecensioni = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader("src/Data/Recensioni.txt"))){
            String line;
            while((line = reader.readLine()) != null){
                String[] riga = line.split(",");
                String email = riga[0];
                String nomeRistorante = riga[1];
                Integer valutazione = Integer.parseInt(riga[2]);
                String recensione = riga[3];
                String risposta = riga[4];
                Recensione r = new Recensione(email, nomeRistorante, valutazione, recensione, risposta);
                listaRecensioni.add(r);
            }
        }catch(IOException e){
            System.err.println("Errore nel caricamento della recensione: " + e.getMessage());
        }
    }
    
    //Metodo per aggiungere una recensione al file di recensioni
    public void inserisciRecensione(Utente utente, ListaRistoranti listaRistoranti){
        Recensione recensione = new Recensione(utente, listaRistoranti);
        if (recensioneDuplicato(recensione)){
            System.out.println("Hai già lasciato una recensione a questo ristorante!");
        } else {
            listaRecensioni.add(recensione);
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

    // Modifica recensione
    public void modificaRecensione(Utente utente) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Inserisci il nome del ristorante da modificare:");
        String nomeRistorante = scanner.nextLine();
        System.out.print("Inserisci il nuovo testo della recensione:");
        String nuovoTesto = scanner.nextLine();
        System.out.print("Inserisci il nuovo numero di stelle (1-5):");
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
        System.out.print("Inserisci il nome del ristorante di cui eliminare la recensione:");
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

    //Metodo per la stampa delle recensioni per il menu ristoratore
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

    //Metodo per la stampa delle recensioni per il menu utente log
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