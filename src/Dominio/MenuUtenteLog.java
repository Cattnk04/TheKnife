package Dominio;

import java.io.*;
import java.util.*;

public class MenuUtenteLog {
    private Map<String, List<String>> preferiti;
    private Map<String, Map<String, Recensione>> recensioni;
    private static final String FILE_PREFERITI = "src/Data/Preferiti.txt";
    private static final String FILE_RECENSIONI = "src/Data/Recensioni.txt";
    private Scanner scanner;
    private Utente utenteCorrente;


    public MenuUtenteLog() {
        this.preferiti = new HashMap<>();
        this.recensioni = new HashMap<>();
        this.utenteCorrente = utente;
        this.scanner = scanner;

        caricaPreferitiDaFile();
        caricaRecensioniDaFile();
        mostraMenuUtente();

        System.out.println("Caricamento preferiti e recensioni completati");
    }

    public void mostraMenuUtente(){
        int scelta = 0;
        do{
            try{
                System.out.println("\n=== Menu Utente ===");
                System.out.println("1. Visualizza i tuoi preferiti");
                System.out.println("2. Aggiungi ristorante ai preferiti");
                System.out.println("3. Rimuovi ristorante dai preferiti");
                System.out.println("4. Visualizza le tue recensioni");
                System.out.println("5. Aggiungi recensione");
                System.out.println("6. Modifica recensione");
                System.out.println("7. Elimina recensione");
                System.out.println("0. Logout");
                System.out.print("La tua scelta: ");

                scelta = scanner.nextInt();
                scanner.nextLine(); // Pulizia buffer

                switch (scelta) {
                    case 1:
                        mostraPreferiti();
                        break;
                    case 2:
                        aggiungiPreferito();
                        break;
                    case 3:
                        rimuoviPreferito();
                        break;
                    case 4:
                        mostraRecensioni();
                        break;
                    case 5:
                        aggiungiNuovaRecensione();
                        break;
                    case 6:
                        modificaRecensioneEsistente();
                        break;
                    case 7:
                        eliminaRecensioneEsistente();
                        break;
                    case 0:
                        System.out.println("Logout effettuato con successo!");
                        break;
                    default:
                        System.out.println("Scelta non valida!");

                }
        }
    }



    // Carica preferiti dal file
    private void caricaPreferitiDaFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PREFERITI))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String email = parts[0].trim();
                    String ristorante = parts[1].trim();
                    preferiti.computeIfAbsent(email, k -> new ArrayList<>()).add(ristorante);
                }
            }
        } catch (IOException e) {
            System.err.println("Errore durante la lettura del file preferiti: " + e.getMessage());
        }
    }

    // Salva preferiti su file
    private void salvaPreferitiSuFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PREFERITI))) {
            for (Map.Entry<String, List<String>> entry : preferiti.entrySet()) {
                String email = entry.getKey();
                for (String ristorante : entry.getValue()) {
                    writer.write(email + "," + ristorante);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Errore durante il salvataggio dei preferiti: " + e.getMessage());
        }
    }

    // Aggiunta ristorante ai preferiti dell'utente con controllo duplicati
    public void aggiungiPreferito(String email, String nomeRistorante) {
        if (email == null || nomeRistorante == null || email.trim().isEmpty() || nomeRistorante.trim().isEmpty()) {
            throw new IllegalArgumentException("Email e nome ristorante non possono essere vuoti");
        }

        List<String> listaPreferiti = preferiti.computeIfAbsent(email, k -> new ArrayList<>());

        // Controllo duplicati
        if (!listaPreferiti.contains(nomeRistorante)) {
            listaPreferiti.add(nomeRistorante);
            salvaPreferitiSuFile(); // Salva su file dopo ogni aggiunta
            System.out.println("Ristorante aggiunto ai preferiti con successo");
        } else {
            System.out.println("Il ristorante è già presente nei preferiti");
        }
    }

    // Rimuovi ristorante dai preferiti dell'utente
    public void rimuoviPreferito(String email, String nomeRistorante) {
        if (preferiti.containsKey(email)) {
            List<String> listaPreferiti = preferiti.get(email);
            if (listaPreferiti.remove(nomeRistorante)) {
                salvaPreferitiSuFile(); // Salva su file dopo ogni rimozione
                System.out.println("Ristorante rimosso dai preferiti con successo");
            } else {
                System.out.println("Il ristorante non era presente nei preferiti");
            }
        }
    }

    //Visualizza i ristoranti preferiti dell'utente
    public List<String> visualizzaPreferiti(String userId) {
        return preferiti.getOrDefault(userId, new ArrayList<>());
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
            System.err.println("Errore durante il salvataggio delle recensioni: " + e.getMessage());
        }
    }

    // Aggiunta recensione con controllo duplicati
    public void aggiungiRecensione(String email, String nomeRistorante, String testo, int stelle) {
        if (email == null || nomeRistorante == null || testo == null ||
                email.trim().isEmpty() || nomeRistorante.trim().isEmpty() || testo.trim().isEmpty()) {
            throw new IllegalArgumentException("Email, nome ristorante e testo non possono essere vuoti");
        }

        if (stelle < 1 || stelle > 5) {
            throw new IllegalArgumentException("Il numero di stelle deve essere tra 1 e 5");
        }

        Map<String, Recensione> recensioniUtente = recensioni.computeIfAbsent(email, k -> new HashMap<>());

        if (recensioniUtente.containsKey(nomeRistorante)) {
            System.out.println("Hai già recensito questo ristorante. Usa modificaRecensione per cambiare la recensione.");
            return;
        }

        recensioniUtente.put(nomeRistorante, new Recensione(testo, stelle));
        salvaRecensioniSuFile();
        System.out.println("Recensione aggiunta con successo");
    }

    // Modifica recensione
    public void modificaRecensione(String email, String nomeRistorante, String nuovoTesto, int nuoveStelle) {
        if (email == null || nomeRistorante == null || nuovoTesto == null ||
                email.trim().isEmpty() || nomeRistorante.trim().isEmpty() || nuovoTesto.trim().isEmpty()) {
            throw new IllegalArgumentException("Email, nome ristorante e testo non possono essere vuoti");
        }

        if (nuoveStelle < 1 || nuoveStelle > 5) {
            throw new IllegalArgumentException("Il numero di stelle deve essere tra 1 e 5");
        }

        if (!recensioni.containsKey(email) || !recensioni.get(email).containsKey(nomeRistorante)) {
            System.out.println("Recensione non trovata");
            return;
        }

        recensioni.get(email).put(nomeRistorante, new Recensione(nuovoTesto, nuoveStelle));
        salvaRecensioniSuFile();
        System.out.println("Recensione modificata con successo");
    }

    // Elimina recensione
    public void eliminaRecensione(String email, String nomeRistorante) {
        if (recensioni.containsKey(email)) {
            if (recensioni.get(email).remove(nomeRistorante) != null) {
                salvaRecensioniSuFile();
                System.out.println("Recensione eliminata con successo");
            } else {
                System.out.println("Recensione non trovata");
            }
        }
    }

    // Visualizza tutte le recensioni di un utente
    public Map<String, Recensione> visualizzaRecensioniUtente(String email) {
        return recensioni.getOrDefault(email, new HashMap<>());
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
