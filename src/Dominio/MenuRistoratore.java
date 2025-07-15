package Dominio;

import java.util.*;

public class MenuRistoratore {

    private Utente utenteCorrente;
    private ListaRistoranti listaRistoranti;
    private ListaRecensioni listaRecensioni;
    private static final Scanner scanner = new Scanner(System.in);

    //Costruttore
    public MenuRistoratore(Utente utente) {
        this.utenteCorrente = utente;
        this.listaRistoranti = new ListaRistoranti();
        this.listaRecensioni = new ListaRecensioni();
        mostraMenuRistoratore();
    }

    //Metodo per la "costruzione" del menu
    public void mostraMenuRistoratore() {
        int scelta = 0;
        do {
            try {
                System.out.println("\n=== Menu Ristoratore ===");
                System.out.println("1. Aggiungi ristorante");
                System.out.println("2. Visualizza i miei ristoranti");
                System.out.println("3. Visualizza riepilogo recensioni di tutti i ristoranti");
                System.out.println("4. Visualizza dettagli recensioni");
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

    //Metodo per aggiungere un ristorante
    private void aggiungiRistorante() {
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
        System.out.println("Ristorante aggiunto con successo!");
    }

    //Metodo per visualizzare i ristoranti del ristoratore
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

    //Metodo per la visualizzazione nel dettaglio le recensioni di un ristorante nello specifico
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
        System.out.print("\nInserisci il nome del tuo ristorante del quale vuoi vedere le recensioni: ");
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
    }

    //Metodo per la risposta delle recensioni
    private void rispostaRecensioni() {
        System.out.println("\n=== Rispondi alle Recensioni ===");

        // Lista dei ristoranti del proprietario corrente
        List<Ristorante> mieiRistoranti = new ArrayList<>();
        int contatore = 1;

        // Mostra la lista dei ristoranti del proprietario
        System.out.println("I tuoi ristoranti:");
        for (Ristorante r : listaRistoranti.getListaRistoranti()) {
            if (r.getEmailRistoratore().equals(utenteCorrente.getEmail())) {
                System.out.printf("%d. %s\n", contatore++, r.getNome());
                mieiRistoranti.add(r);
            }
        }

        if (mieiRistoranti.isEmpty()) {
            System.out.println("Non possiedi ancora nessun ristorante.");
            return;
        }

        System.out.print("\nInserisci il nome del ristorante: ");
        String nomeRistorante = scanner.nextLine();

        // Verifica che il ristorante appartenga al proprietario
        if (!appartienePropietario(nomeRistorante)) {
            System.out.println("Il ristorante specificato non ti appartiene!");
            return;
        }

        boolean trovateRecensioni = false;

        for (Recensione recensione : listaRecensioni.getListaRecensione()) {
            if (recensione.getNomeRistorante().equals(nomeRistorante) &&
                (recensione.getRisposta() == null || recensione.getRisposta().equals("null"))) {

                trovateRecensioni = true;
                System.out.println("\n----------------------------------------");
                System.out.printf("Recensione di: %s\n", recensione.getEmail());
                System.out.printf("Valutazione: %d/5\n", recensione.getValutazione());
                System.out.printf("Testo: %s\n", recensione.getRecensione());

                System.out.print("\nVuoi rispondere a questa recensione? (s/n): ");
                String scelta = scanner.nextLine().trim().toLowerCase();

                if (scelta.equals("s")) {
                    System.out.print("Inserisci la tua risposta: ");
                    String risposta = scanner.nextLine();
                    recensione.risposta = risposta;
                    listaRecensioni.salvaRecensioniSuCSV();
                    System.out.println("Risposta aggiunta con successo!");
                }
            }
        }

        if (!trovateRecensioni) {
            System.out.println("Non ci sono nuove recensioni da rispondere per questo ristorante.");
        }
    }

    //Metodo per visualizzare il riepilogo delle recensioni di tutti i ristoranti (la media e il numero di recensioni)
    private void visualizzaRiepilogo() {
        System.out.println("\n=== Riepilogo Recensioni ===");

        // Per ogni ristorante del proprietario
        for (Ristorante ristorante : listaRistoranti.getListaRistoranti()) {
            if (ristorante.getEmailRistoratore().equals(utenteCorrente.getEmail())) {

                // Variabili per calcolare le statistiche
                int numeroRecensioni = 0;
                double sommaStelle = 0;

                // Cerca tutte le recensioni per questo ristorante
                List<Recensione> recensioniRistorante = new ArrayList<>();
                for (Recensione recensione : listaRecensioni.getListaRecensione()) {
                    if (recensione.getNomeRistorante().equals(ristorante.getNome())) {
                        recensioniRistorante.add(recensione);
                        numeroRecensioni++;
                        sommaStelle += recensione.getValutazione();
                    }
                }

                // Stampa le statistiche del ristorante
                System.out.printf("\nRistorante: %s\n", ristorante.getNome());

                if (numeroRecensioni > 0) {
                    double mediaStelle = sommaStelle / numeroRecensioni;
                    System.out.printf("Numero recensioni: %d\n", numeroRecensioni);
                    System.out.printf("Media stelle: %.1f\n", mediaStelle);
                } else {
                    System.out.println("Nessuna recensione presente");
                }
            }
        }

        // Se non ci sono ristoranti per il proprietario
        if (!listaRistoranti.getListaRistoranti().stream()
                .anyMatch(r -> r.getEmailRistoratore().equals(utenteCorrente.getEmail()))) {
            System.out.println("Non possiedi ancora nessun ristorante.");
        }
    }
    //controllo appartenenza ristorante al ristoratore
    private boolean appartienePropietario(String nomeRistorante) {
        return listaRistoranti.getListaRistoranti().stream()
                .anyMatch(r -> r.getNome().equals(nomeRistorante) &&
                        r.getEmailRistoratore().equals(utenteCorrente.getEmail()));
    }
}