package Dominio;

import java.util.*;
import java.io.*;

public class MenuRistorante {

    private List<Ristorante> ristoranti;
    private Utenti utenteCorrente;

    public MenuRistorante(Utenti utente) {
        this.utenteCorrente = utente;
        this.ristoranti = caricaRistoranti();
    }

    // Metodo per caricare i ristoranti dal file
    private List<Ristorante> caricaRistoranti() {
        List<Ristorante> listaRistoranti = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/Dominio/Ristoranti.txt"))){
            String line;
            while((line = reader.readLine()) != null){
                String[] dati = line.split(",");
                Ristorante r = new Ristorante(
                        dati[0],    // nome
                        dati[1],    // nazione
                        dati[2],    //città
                        dati[3],    //indirizzo
                        Integer.parseInt(dati[4]),  //fascia prezzo
                        Boolean.parseBoolean(dati[5]),  //delivery
                        Boolean.parseBoolean(dati[6]),  //prenotazione online
                        dati[7]     // tipo cucina
                        );
                listaRistoranti.add(r);
            }
        }
        catch (IOException e) {
            System.out.println("Errore nel caricamento dei ristoranti: " + e.getMessage());
        }
        return listaRistoranti;
    }

    // Metodo per visualizzare i ristoranti nella località dell'utente
    public List<Ristorante> getRistorantiLocali() {
        String localita = utenteCorrente != null ? utenteCorrente.getProvincia() : null;
        return ristoranti.stream().filter(r -> r.getCitta().equalsIgnoreCase(localita)).toList();
    }

    // Metodi di ricerca per vari parametri
    public List<Ristorante> cercaPerNome(String nome) {
        return ristoranti.stream()
                .filter(r -> r.getNome().toLowerCase().contains(nome.toLowerCase()))
                .toList();
    }
    public List<Ristorante> cercaPerNazione(String nazione) {
        return ristoranti.stream()
                .filter(r -> r.getNazione().equalsIgnoreCase(nazione))
                .toList();
    }
    public List<Ristorante> cercaPerCitta(String citta) {
        return ristoranti.stream()
                .filter(r -> r.getCitta().equalsIgnoreCase(citta))
                .toList();
    }
    public List<Ristorante> cercaPerTipoCucina(String tipoCucina) {
        return ristoranti.stream()
                .filter(r -> r.getTipoCucina().equalsIgnoreCase(tipoCucina))
                .toList();
    }
    public List<Ristorante> cercaPerFasciaPrezzo(Integer fasciaPrezzo) {
        return ristoranti.stream()
                .filter(r -> r.getFasciaPrezzo().equals(fasciaPrezzo))
                .toList();
    }
    public List<Ristorante> cercaPerServizioDelivery() {
        return ristoranti.stream()
                .filter(Ristorante::getServizioDelivery)
                .toList();
    }
    public List<Ristorante> cercaPerPrenotazioneOnline() {
        return ristoranti.stream()
                .filter(Ristorante::getServizioPrenotazioneOnline)
                .toList();
    }



}
