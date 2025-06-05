package Dominio;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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
    public void inserisciRecensione(Utente utente, ListaRistoranti listaRistoranti){
        Recensione recensione = new Recensione(utente, listaRistoranti);
        if (recensioneDuplicato(recensione)){
            System.out.println("Hai già lasciato una recensione a questo ristorante!");
        } else {
            listaRecensioni.add(recensione);
        }
    }
    public boolean recensioneDuplicato(Recensione recensione){
        for(Recensione r : listaRecensioni){
            if(r.getEmail().equals(recensione.getEmail()) && r.getNomeRistorante().equals(recensione.getNomeRistorante())){
                return true;
            }
        }
        return false;
    }

}
