package Dominio;

import java.io.*;
import java.util.*;

public class MenuUtenteLog {

    private ListaPreferiti listaPreferiti = new ListaPreferiti();
    //private ListaRecensioni listaRecensioni = new ListaRecensioni();

    private Map<String, List<String>> preferiti;
    private Map<String, Map<String, Recensione>> recensioni;
    private static final String FILE_PREFERITI = "src/Data/Preferiti.txt";
    private static final String FILE_RECENSIONI = "src/Data/Recensioni.txt";
    private Utente utenteCorrente;
    private ListaRistoranti listaRistoranti;



    public MenuUtenteLog(Utente utente) { // Aggiungi i parametri
        listaPreferiti = new ListaPreferiti();
        this.preferiti = new HashMap<>();
        this.recensioni = new HashMap<>();
        this.utenteCorrente = utente;
        this.listaRistoranti = new ListaRistoranti();

    }

    public void mostraMenuUtente(){
        int scelta = 0;
        Scanner scanner = new Scanner(System.in);
        do{
            try{ //il try catch è inutile in quanto c'è gia il caso di default
                System.out.println("\n=== Menu Utente ===");
                System.out.println("1. Cerca un ristorante");
                System.out.println("2. Visualizza i tuoi preferiti");
                System.out.println("3. Aggiungi ristorante ai preferiti");
                System.out.println("4. Rimuovi ristorante dai preferiti");
                System.out.println("5. Visualizza le tue recensioni");
                System.out.println("6. Aggiungi recensione");
                System.out.println("7. Modifica recensione");
                System.out.println("8. Elimina recensione");
                System.out.println("0. Logout");
                System.out.print("La tua scelta: ");

                scelta = scanner.nextInt();
                scanner.nextLine(); // Pulizia buffer

                switch (scelta) {
                    case 1:
                        listaRistoranti.cercaRistorante();
                        break;
                    case 2:
                        mostraPreferiti();
                        break;
                    case 3:
                        aggiungiPreferito();
                        break;
                    case 4:
                        rimuoviPreferito();
                        break;
                    case 5:
                        mostraRecensioni();
                        break;
                    case 6:
                        aggiungiRecensione();
                        break;
                    case 7:
                        modificaRecensione();
                        break;
                    case 8:
                        eliminaRecensione();
                        break;
                    case 0:
                        System.out.println("Logout effettuato con successo!");
                        break;
                    default:
                        System.out.println("Scelta non valida!");

                }
            }
            catch (InputMismatchException e) {
                System.out.println("Inserire un numero valido!");
                scanner.nextLine(); // Pulizia buffer
                scelta = -1;
            }
        } while (scelta != 0);
        scanner.close();
    }

    //Mostra i ristoranti preferiti dell'utente
    private void mostraPreferiti() {
        List<Preferito> preferitiUtente = listaPreferiti.preferitiUtente(utenteCorrente);
        if (preferitiUtente.isEmpty()) {
            System.out.print("Non hai ancora aggiunto ristoranti ai preferiti.");
        } else {
            System.out.print("\nI tuoi ristoranti preferiti:");
            for (Preferito p : preferitiUtente) {
                System.out.print("- " + p.getNomeRistorante());
            }
        }
    }

    //Controllo se il ristorante esiste nel file
    private boolean esisteRistorante(String nomeRistorante) {
        // Debug: stampa il nome ricercato
        System.out.println("Cercando ristorante: '" + nomeRistorante + "'");
        
        return listaRistoranti.getListaRistoranti().stream()
                .peek(r -> System.out.println("Confrontando con: '" + r.getNome() + "'"))
                .anyMatch(r -> r.getNome().trim().equalsIgnoreCase(nomeRistorante.trim()));
    }

    // Aggiunta ristorante ai preferiti dell'utente con controllo duplicati
    public void aggiungiPreferito() {

        listaPreferiti.aggiungiPreferito(utenteCorrente, listaRistoranti);

        // quiesto va fatto bnel gestore della lista dei preferiti non nella classe menù
        //Le liste si gestiscono nelle loro classi
        /*
        System.out.print("Inserisci il nome del ristorante da aggiungere ai preferiti: ");
        String nomeRistorante = scanner.nextLine();

        String email = utenteCorrente.getEmail();

        if (email == null || nomeRistorante == null || email.trim().isEmpty() || nomeRistorante.trim().isEmpty()) {
            throw new IllegalArgumentException("Email e nome ristorante non possono essere vuoti \n");
        }
        // Controllo esistenza del ristorante
        if (!esisteRistorante(nomeRistorante)) {
            System.out.print("Il ristorante specificato non esiste nel sistema \n");
            return;
        }

        List<String> listaPreferiti = preferiti.computeIfAbsent(email, k -> new ArrayList<>());

        // Controllo duplicati
        if (!listaPreferiti.contains(nomeRistorante)) {
            listaPreferiti.add(nomeRistorante);
            salvaPreferitiSuFile(); // Salva su file dopo ogni aggiunta
            System.out.print("Ristorante aggiunto ai preferiti con successo\n");
        } else {
            System.out.print("Il ristorante è già presente nei preferiti\n");
        }*/
    }

    // Rimuovi ristorante dai preferiti dell'utente
    public void rimuoviPreferito() {
        System.out.print("Inserisci il nome del ristorante da rimuovere dai preferiti:");
        String nomeRistorante = scanner.nextLine();
        String email = utenteCorrente.getEmail();

        if (preferiti.containsKey(email)) {
            List<String> listaPreferiti = preferiti.get(email);
            if (listaPreferiti.remove(nomeRistorante)) {
                salvaPreferitiSuFile(); // Salva su file dopo ogni rimozione
                System.out.print("Ristorante rimosso dai preferiti con successo\n");
            } else {
                System.out.print("Il ristorante non era presente nei preferiti\n");
            }
        }
    }


    // Carica recensioni dal file
    private void caricaRecensioniDaFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_RECENSIONI))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String email = parts[0].trim();
                    String ristorante = parts[1].trim();
                    String testo = parts[2].trim();
                    int stelle = Integer.parseInt(parts[3].trim());

                    recensioni.computeIfAbsent(email, k -> new HashMap<>())
                            .put(ristorante, new Recensione(testo, stelle));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Errore durante la lettura del file recensioni: " + e.getMessage());
        }
    }

    // Salva recensioni su file
    private void salvaRecensioniSuFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_RECENSIONI))) {
            for (Map.Entry<String, Map<String, Recensione>> userEntry : recensioni.entrySet()) {
                String email = userEntry.getKey();
                for (Map.Entry<String, Recensione> recensioneEntry : userEntry.getValue().entrySet()) {
                    String ristorante = recensioneEntry.getKey();
                    Recensione recensione = recensioneEntry.getValue();
                    writer.write(String.format("%s,%s,%s,%d",
                            email, ristorante, recensione.testo, recensione.stelle));
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.err.print("Errore durante il salvataggio delle recensioni: " + e.getMessage());
        }
    }

    //Mostra le recensioni dell'utente
    private void mostraRecensioni() {
        Map<String, Recensione> recensioniUtente = visualizzaRecensioniUtente(utenteCorrente.getEmail());
        if (recensioniUtente.isEmpty()) {
            System.out.print("Non hai ancora scritto recensioni.\n");
        } else {
            System.out.print("\nLe tue recensioni:");
            for (Map.Entry<String, Recensione> entry : recensioniUtente.entrySet()) {
                System.out.printf("Ristorante: %s\nValutazione: %d/5\nRecensione: %s\n\n",
                        entry.getKey(), entry.getValue().getStelle(), entry.getValue().getTesto());
            }
        }
    }

    // Aggiunta recensione con controllo duplicati
    public void aggiungiRecensione(String email, String nomeRistorante, String testo, int stelle) {
        if (email == null || nomeRistorante == null || testo == null ||
                email.trim().isEmpty() || nomeRistorante.trim().isEmpty() || testo.trim().isEmpty()) {
            throw new IllegalArgumentException("Email, nome ristorante e testo non possono essere vuoti\n");
        }

        if (stelle < 1 || stelle > 5) {
            throw new IllegalArgumentException("Il numero di stelle deve essere tra 1 e 5\n");
        }

        Map<String, Recensione> recensioniUtente = recensioni.computeIfAbsent(email, k -> new HashMap<>());

        if (recensioniUtente.containsKey(nomeRistorante)) {
            System.out.print("Hai già recensito questo ristorante. Inserisci il numero di modifica recensione per cambiare la recensione.\n");
            return;
        }

        recensioniUtente.put(nomeRistorante, new Recensione(testo, stelle));
        salvaRecensioniSuFile();
        System.out.print("Recensione aggiunta con successo\n");
    }

    // Modifica recensione
    public void modificaRecensione(String email, String nomeRistorante, String nuovoTesto, int nuoveStelle) {
        if (email == null || nomeRistorante == null || nuovoTesto == null ||
                email.trim().isEmpty() || nomeRistorante.trim().isEmpty() || nuovoTesto.trim().isEmpty()) {
            throw new IllegalArgumentException("Email, nome ristorante e testo non possono essere vuoti\n");
        }

        if (nuoveStelle < 1 || nuoveStelle > 5) {
            throw new IllegalArgumentException("Il numero di stelle deve essere tra 1 e 5\n");
        }

        if (!recensioni.containsKey(email) || !recensioni.get(email).containsKey(nomeRistorante)) {
            System.out.print("Recensione non trovata");
            return;
        }

        recensioni.get(email).put(nomeRistorante, new Recensione(nuovoTesto, nuoveStelle));
        salvaRecensioniSuFile();
        System.out.print("Recensione modificata con successo\n");
    }

    // Elimina recensione
    public void eliminaRecensione(String email, String nomeRistorante) {
        if (recensioni.containsKey(email)) {
            if (recensioni.get(email).remove(nomeRistorante) != null) {
                salvaRecensioniSuFile();
                System.out.print("Recensione eliminata con successo\n");
            } else {
                System.out.print("Recensione non trovata\n");
            }
        }
    }

    // Visualizza tutte le recensioni di un utente
    public Map<String, Recensione> visualizzaRecensioniUtente(String email) {
        return recensioni.getOrDefault(email, new HashMap<>());
    }

    private void aggiungiRecensione() {
        System.out.print("Inserisci il nome del ristorante da recensire:");
        String nomeRistorante = scanner.nextLine();
        System.out.print("Inserisci il testo della recensione:");
        String testo = scanner.nextLine();
        System.out.print("Inserisci il numero di stelle (1-5):");
        int stelle = scanner.nextInt();
        scanner.nextLine(); // Pulizia buffer
        
        String email = utenteCorrente.getEmail();
        
        try {
            if (stelle < 1 || stelle > 5) {
                System.out.print("Il numero di stelle deve essere tra 1 e 5\n");
                return;
            }

            Map<String, Recensione> recensioniUtente = recensioni.computeIfAbsent(email, k -> new HashMap<>());

            if (recensioniUtente.containsKey(nomeRistorante)) {
                System.out.print("Hai già recensito questo ristorante. Usa modificaRecensione per cambiare la recensione.\n");
                return;
            }

            recensioniUtente.put(nomeRistorante, new Recensione(testo, stelle));
            salvaRecensioniSuFile();
            System.out.print("Recensione aggiunta con successo\n");
        } catch (IllegalArgumentException e) {
            System.out.print("Errore: " + e.getMessage());
        }
    }

    private void modificaRecensione() {
        System.out.print("Inserisci il nome del ristorante da modificare:");
        String nomeRistorante = scanner.nextLine();
        System.out.print("Inserisci il nuovo testo della recensione:");
        String nuovoTesto = scanner.nextLine();
        System.out.print("Inserisci il nuovo numero di stelle (1-5):");
        int nuoveStelle = scanner.nextInt();
        scanner.nextLine(); // Pulizia buffer
        
        String email = utenteCorrente.getEmail();
        
        try {
            if (nuoveStelle < 1 || nuoveStelle > 5) {
                System.out.print("Il numero di stelle deve essere tra 1 e 5\n");
                return;
            }

            if (!recensioni.containsKey(email) || !recensioni.get(email).containsKey(nomeRistorante)) {
                System.out.print("Recensione non trovata\n");
                return;
            }

            recensioni.get(email).put(nomeRistorante, new Recensione(nuovoTesto, nuoveStelle));
            salvaRecensioniSuFile();
            System.out.print("Recensione modificata con successo\n");
        } catch (IllegalArgumentException e) {
            System.out.print("Errore: " + e.getMessage());
        }
    }

    private void eliminaRecensione() {
        System.out.print("Inserisci il nome del ristorante di cui eliminare la recensione:");
        String nomeRistorante = scanner.nextLine();
        String email = utenteCorrente.getEmail();

        if (recensioni.containsKey(email)) {
            if (recensioni.get(email).remove(nomeRistorante) != null) {
                salvaRecensioniSuFile();
                System.out.println("Recensione eliminata con successo\n");
            } else {
                System.out.println("Recensione non trovata\n");
            }
        }
    }

    // Classe interna Recensione modificata per accesso pubblico
    public static class Recensione {
        private String testo;
        private int stelle;

        public Recensione(String testo, int stelle) {
            this.testo = testo;
            this.stelle = stelle;
        }

        public String getTesto() {
            return testo;
        }
        public void setTesto(String testo) { this.testo = testo;}
        public int getStelle() {
            return stelle;
        }
        public void setStelle(int stelle) { this.stelle = stelle;}
    }
}