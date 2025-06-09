package Dominio;

import java.util.*;
import java.io.*;

public class MenuRistoratore {
    private Utente utenteCorrente;
    private ListaRistoranti listaRistoranti;
    private ListaRecensioni listaRecensioni;
    private Map<String, Map<String, MenuUtenteLog.Recensione>> recensioni;
    private Map<String, String> risposteRecensioni;
    private static final String FILE_RISTORANTI = "src/Data/Ristoranti.txt";
    private static final String FILE_RECENSIONI = "src/Data/Recensioni.txt";
    private static final String FILE_RISPOSTE = "src/Data/RisposteRecensioni.txt";


    public MenuRistoratore(Utente utente) {
        this.utenteCorrente = utente;
        this.listaRistoranti = new ListaRistoranti();
        this.listaRecensioni = new ListaRecensioni();
        mostraMenuRistoratore();
    }

    public void mostraMenuRistoratore() {
        int scelta = 0;
        Scanner scanner = new Scanner(System.in);
        do {
            try {
                System.out.println("\n=== Menu Ristoratore ===");
                System.out.println("1. Aggiungi ristorante");
                System.out.println("2. Visualizza i miei ristoranti");
                System.out.println("3. Visualizza riepilogo recensioni");
                System.out.println("4. Visualizza recensioni ristorante");
                System.out.println("5. Rispondi alle recensioni"); // Nuova opzione
                System.out.println("0. Esci");
                System.out.print("La tua scelta: ");

                scelta = scanner.nextInt();
                scanner.nextLine(); // Consuma il newline

                switch (scelta) {
                    case 1:
                        aggiungiRistorante();
                        break;
                    case 2:
                        visualizzaMieiRistoranti();
                        break;
                    case 3:
                        visualizzaRiepilogo();
                        break;
                    case 4:
                        visualizzaRecensioniRistorante();
                        break;
                    case 5:
                        rispostaRecensioni();
                        break;
                    case 0:
                        System.out.println("Logout effettuato con successo!");
                        break;
                    default:
                        System.out.println("Opzione non valida!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Inserire un numero valido!");
                scanner.nextLine(); // Pulizia buffer
                scelta = -1;
            }
        } while (scelta != 0);
    }

    private void aggiungiRistorante() {
        listaRistoranti.inserisciRistorante(new Ristorante(utenteCorrente));
        /*
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n=== Aggiungi Nuovo Ristorante ===");
        System.out.print("Nome del ristorante: ");
        String nome = scanner.nextLine();

        System.out.print("Nazione: ");
        String nazione = scanner.nextLine();

        System.out.print("Città: ");
        String citta = scanner.nextLine();

        System.out.print("Indirizzo: ");
        String indirizzo = scanner.nextLine();

        System.out.print("Fascia di prezzo: ");
        double fasciaPrezzo = scanner.nextDouble();
        scanner.nextLine();

        boolean delivery = false;
        boolean prenotazioneOnline = false;

        boolean inputValido;
        do {
            System.out.print("Servizio delivery (s/n): ");
            String rispostaDelivery = scanner.nextLine().trim().toLowerCase();
            if (rispostaDelivery.equals("s")) {
                delivery = true;
                inputValido = true;
            } else if (rispostaDelivery.equals("n")) {
                delivery = false;
                inputValido = true;
            } else {
                System.out.println("Inserire 's' per sì o 'n' per no");
                inputValido = false;
            }
        } while (!inputValido);

        do {
            System.out.print("Prenotazione online (s/n): ");
            String rispostaPrenotazione = scanner.nextLine().trim().toLowerCase();
            if (rispostaPrenotazione.equals("s")) {
                prenotazioneOnline = true;
                inputValido = true;
            } else if (rispostaPrenotazione.equals("n")) {
                prenotazioneOnline = false;
                inputValido = true;
            } else {
                System.out.println("Inserire 's' per sì o 'n' per no");
                inputValido = false;
            }
        } while (!inputValido);

        System.out.print("Tipo di cucina: ");
        String tipoCucina = scanner.nextLine();

        Ristorante ristorante = new Ristorante(nome, utenteCorrente.getEmail(), nazione, citta, indirizzo, fasciaPrezzo, delivery, prenotazioneOnline, tipoCucina);
        listaRistoranti.inserisciRistorante(ristorante);
        listaRistoranti.salvaRistorantiSuCSV();
        System.out.println("Ristorante aggiunto con successo!");*/
    }

    // Nuovo metodo per visualizzare i ristoranti del ristoratore
    private void visualizzaMieiRistoranti() {
        System.out.println("\n=== I Miei Ristoranti ===");
        boolean trovati = false;

        for (Ristorante r : listaRistoranti.getListaRistoranti()) {
            if (r.getEmailRistoratore().equals(utenteCorrente.getEmail())) {
                trovati = true;
                System.out.println("\nNome: " + r.getNome());
                System.out.println("Nazione: " + r.getNazione());
                System.out.println("Città: " + r.getCitta());
                System.out.println("Indirizzo: " + r.getIndirizzo());
                System.out.println("Fascia di prezzo: " + r.getFasciaPrezzo());
                System.out.println("Servizio delivery: " + (r.getServizioDelivery() ? "Sì" : "No"));
                System.out.println("Prenotazione online: " + (r.getServizioPrenotazioneOnline() ? "Sì" : "No"));
                System.out.println("Tipo di cucina: " + r.getTipoCucina());
                System.out.println("----------------------------------------");
            }
        }
        if (!trovati) {
            System.out.println("Non hai ancora registrato alcun ristorante.");
        }
    }

    //Metodo per la visualizzazione del riepilogo delle recensioni
    private void visualizzaRiepilogo() {
        System.out.println("\n=== Riepilogo Recensioni ===");
        Map<String, Integer> numeroRecensioni = new HashMap<>();
        Map<String, Double> mediaStelle = new HashMap<>();

        // Calcola statistiche per i ristoranti dell'utente corrente
        for (Map.Entry<String, Map<String, MenuUtenteLog.Recensione>> entry : recensioni.entrySet()) {
            for (Map.Entry<String, MenuUtenteLog.Recensione> recensione : entry.getValue().entrySet()) {
                String nomeRistorante = recensione.getKey();

                // Verifica se il ristorante appartiene al ristoratore corrente
                if (appartienePropietario(nomeRistorante)) {
                    numeroRecensioni.merge(nomeRistorante, 1, Integer::sum);
                    mediaStelle.merge(nomeRistorante,
                            (double) recensione.getValue().getStelle(),
                            Double::sum);
                }
            }
        }
        // Calcola e mostra le medie
        for (String nomeRistorante : numeroRecensioni.keySet()) {
            int numRec = numeroRecensioni.get(nomeRistorante);
            double media = mediaStelle.get(nomeRistorante) / numRec;
            System.out.printf("Ristorante: %s\n", nomeRistorante);
            System.out.printf("Numero recensioni: %d\n", numRec);
            System.out.printf("Media stelle: %.1f\n\n", media);
        }
    }
    
    // Metodo per la visualizzazione alle recensioni nel dettaglio
    private void visualizzaRecensioniRistorante() {
        System.out.println("\n=== Dettaglio Recensioni ===");
        Scanner scanner = new Scanner(System.in);
        int cont = 0;
        for(Ristorante r : listaRistoranti.getListaRistoranti()) {
            if (r.getEmailRistoratore().equals(utenteCorrente.getEmail())) {
                cont++;
                System.out.println(cont + ": " + r.getNome());
            }
        }
        Ristorante ristorante = null;
        System.out.println("Inserisci il nome del tuo ristorante del quale vuoi vedere le recensioni: ");
        String nomeRistorante = scanner.nextLine();
        for(Ristorante r : listaRistoranti.getListaRistoranti()) {
            if (r.getEmailRistoratore().equals(utenteCorrente.getEmail()) && r.getNome().equals(nomeRistorante)) {
                ristorante = r;
            }
        }
        if(ristorante == null) {
            System.out.println("Hai inserito un nome errato");
        } else {
            for(Recensione rec : listaRecensioni.recensioniRistorante(ristorante.getNome())) {
                System.out.println(rec.stampaRecensione());
            }
        }
        /*for (Map.Entry<String, Map<String, MenuUtenteLog.Recensione>> entry : recensioni.entrySet()) {
            for (Map.Entry<String, MenuUtenteLog.Recensione> recensione : entry.getValue().entrySet()) {
                String nomeRistorante = recensione.getKey();

                if (appartienePropietario(nomeRistorante)) {
                    MenuUtenteLog.Recensione rec = recensione.getValue();
                    System.out.printf("Ristorante: %s\n", nomeRistorante);
                    System.out.printf("Utente: %s\n", entry.getKey());
                    System.out.printf("Stelle: %d\n", rec.getStelle());
                    System.out.printf("Recensione: %s\n", rec.getTesto());

                    // Mostra risposta se presente
                    String chiaveRisposta = entry.getKey() + "," + nomeRistorante;
                    if (risposteRecensioni.containsKey(chiaveRisposta)) {
                        System.out.printf("Risposta: %s\n", risposteRecensioni.get(chiaveRisposta));
                    }
                    System.out.println();
                }
            }
        }*/
    }
    //Metodo per la risposta
    private void rispostaRecensioni() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n=== Rispondi alle Recensioni ===");
        System.out.print("Inserisci il nome del ristorante: ");
        String nomeRistorante = scanner.nextLine();

        if (!appartienePropietario(nomeRistorante)) {
            System.out.println("Il ristorante specificato non ti appartiene!");
            return;
        }

        // Mostra recensioni senza risposta
        boolean trovateRecensioni = false;
        for (Map.Entry<String, Map<String, MenuUtenteLog.Recensione>> entry : recensioni.entrySet()) {
            if (entry.getValue().containsKey(nomeRistorante)) {
                String chiaveRisposta = entry.getKey() + "," + nomeRistorante;
                if (!risposteRecensioni.containsKey(chiaveRisposta)) {
                    trovateRecensioni = true;
                    System.out.printf("\nRecensione di %s:\n", entry.getKey());
                    System.out.printf("Stelle: %d\n", entry.getValue().get(nomeRistorante).getStelle());
                    System.out.printf("Testo: %s\n", entry.getValue().get(nomeRistorante).getTesto());

                    System.out.print("Vuoi rispondere a questa recensione? (s/n): ");
                    String scelta = scanner.nextLine();

                    if (scelta.equalsIgnoreCase("s")) {
                        System.out.print("Inserisci la tua risposta: ");
                        String risposta = scanner.nextLine();
                        risposteRecensioni.put(chiaveRisposta, risposta);
                        salvaRisposteSuFile();
                        System.out.println("Risposta aggiunta con successo!");
                    }
                }
            }
        }

        if (!trovateRecensioni) {
            System.out.println("Non ci sono nuove recensioni da rispondere per questo ristorante.");
        }
    }
    //controllo appartenenza ristorante al ristoratore
    private boolean appartienePropietario(String nomeRistorante) {
        return listaRistoranti.getListaRistoranti().stream()
                .anyMatch(r -> r.getNome().equals(nomeRistorante) &&
                        r.getEmailRistoratore().equals(utenteCorrente.getEmail()));
    }
    
    //Metodo per caricare le risposte sul file
    private void caricaRisposteDaFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_RISPOSTE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 3);
                if (parts.length == 3) {
                    String chiave = parts[0] + "," + parts[1];
                    risposteRecensioni.put(chiave, parts[2]);
                }
            }
        } catch (IOException e) {
            System.err.println("Errore durante la lettura delle risposte: " + e.getMessage());
        }
    }
    
    // Metodo per salvare le risposte sul file
    private void salvaRisposteSuFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_RISPOSTE))) {
            for (Map.Entry<String, String> entry : risposteRecensioni.entrySet()) {
                String[] parts = entry.getKey().split(",");
                writer.write(String.format("%s,%s,%s\n", parts[0], parts[1], entry.getValue()));
            }
        } catch (IOException e) {
            System.err.println("Errore durante il salvataggio delle risposte: " + e.getMessage());
        }
    }

}