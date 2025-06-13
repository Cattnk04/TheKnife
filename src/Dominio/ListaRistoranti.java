package Dominio;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ListaRistoranti {

    public List<Ristorante> listaRistoranti = new ArrayList<>();

    //Costruttore
    public ListaRistoranti(){
        if(listaRistoranti.isEmpty())
            ricavaRistorantiDaCSV();
    }

    //Metodo Get
    public List<Ristorante> getListaRistoranti() {
        return this.listaRistoranti;
    }

    //Metodo per salvare sul CSV
    public void salvaRistorantiSuCSV() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/Data/Ristoranti.txt"))) {
            for (Ristorante r : listaRistoranti) {
                writer.write(r.toString());
                writer.newLine();
            }
            writer.flush();
        } catch (IOException e) {
            System.err.println("Errore durante il salvataggio dei ristoranti: " + e.getMessage());
        }
    }

    //Metodo per ricavare il ristorante dal CSV
    public void ricavaRistorantiDaCSV(){
        //lettura del file Ristoranti.txt e salvataggio nella lista listaRistoranti
        listaRistoranti.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/Data/Ristoranti.txt"))){
            String line;
            while((line = reader.readLine()) != null){
                String[] dati = line.split(",");
                Ristorante r = new Ristorante(
                        dati[0],    // nome
                        dati[1],    // email ristoratore
                        dati[2],    // nazione
                        dati[3],    //città
                        dati[4],    //indirizzo
                        Double.parseDouble(dati[5]),  //fascia prezzo
                        Boolean.parseBoolean(dati[6]),  //delivery
                        Boolean.parseBoolean(dati[7]),  //prenotazione online
                        dati[8]     // tipo cucina
                );
                listaRistoranti.add(r);
            }
        }
        catch (IOException e) {
            System.out.println("Errore nel caricamento dei ristoranti: " + e.getMessage());
        }
    }

    //Metodo per inserire un ristorante
    public void inserisciRistorante(Ristorante ristorante) {
        //funzione per l'inserimento di un nuovo ristorante nella lista
        if(ristorante != null && !ristoranteDuplicato(ristorante))
            listaRistoranti.add(ristorante);
        else
            System.out.println("impossibile aggiungere il ristorante");
    }

    private boolean ristoranteDuplicato(Ristorante ristorante) {
        for (Ristorante r : listaRistoranti) {
            if(r.getNome().equals(ristorante.getNome()))
                return true;
        }
        return false;
    }

    //Cerca ristorante con filtri
    public Ristorante cercaPerNome(String messaggio){
        //Funzione per cercare un ristorante in base al suo nome
        Scanner scanner = new Scanner(System.in);
        System.out.print(messaggio);
        String nomeRistorante = scanner.nextLine();
        for (Ristorante r : listaRistoranti) {
            if(nomeRistorante.equals(r.getNome())){
                return r;
            }
        }
        System.out.print("Il nome del ristorante che hai inserito non esiste!");
        scanner.close();
        return null;
    }

    //Metodo per stampare i ristoranti filtrati
    private void stampaRistorantiFiltrati(List<Ristorante> ristoranti) {
        if (ristoranti.isEmpty()) {
            System.out.println("\nNessun ristorante trovato con i criteri specificati.");
            return;
        }

        System.out.println("\n=== Ristoranti trovati ===");
        for (Ristorante r : ristoranti) {
            System.out.println("\nNome: " + r.getNome());
            System.out.println("Città: " + r.getCitta());
            System.out.println("Indirizzo: " + r.getIndirizzo());
            System.out.println("Fascia di prezzo: " + r.getFasciaPrezzo() + "€");
            System.out.println("Tipo di cucina: " + r.getTipoCucina());
            System.out.println("Servizio delivery: " + (r.getServizioDelivery() ? "Sì" : "No"));
            System.out.println("Prenotazione online: " + (r.getServizioPrenotazioneOnline() ? "Sì" : "No"));
            System.out.println("----------------------------------------");
        }
    }

    //Metodo per la ricerca dei ristoranti
    /*public List<Ristorante> cercaRistorante(){
        //funzione per la ricerca del ristorante nella lista
        List<Ristorante> filtrati = new ArrayList<>();
        //Inserimento filtro della località del ristorante
        Scanner scanner = new Scanner(System.in);
        String sn;

        System.out.print("\nInserici la città in cui vuoi cercare il ristorante: ");
        String citta = scanner.nextLine().trim().toLowerCase();
        filtraPerCitta(filtrati, citta);

        System.out.print("Vuoi cercare per fascia di prezzo? [s/n]: ");
        do{
            sn = scanner.nextLine().trim().toLowerCase();
        }while(!sn.equals("s") && !sn.equals("n"));

        double prezzoMin, prezzoMax;
        if(sn.equals("s")){
            do{
                System.out.print("Inserisci il prezzo minimo: ");
                prezzoMin = scanner.nextDouble();
                System.out.print("Inserisci il prezzo massimo: ");
                prezzoMax = scanner.nextDouble();
                if(prezzoMin > prezzoMax){
                    System.out.print("Il prezzo minimo non può essere maggiore del prezzo massimo.");
                }
            }while(prezzoMax < prezzoMin);
            filtraPerPrezzo(filtrati, prezzoMax, prezzoMin);
        }

        System.out.print("Vuoi cercare solo i ristoranti con servizio delivery? [s/n]: ");
        do{
            sn = scanner.nextLine().trim().toLowerCase();
        }while(!sn.equals("s") && !sn.equals("n"));
        if(sn.equals("s")&& !filtrati.isEmpty()){
            filtraPerDelivery(filtrati);
        }

        System.out.print("Vuoi cercare ristoranti con solo prenotazione online? [s/n]: ");
        do{
            sn = scanner.nextLine().trim().toLowerCase();
        }while(!sn.equals("s") && !sn.equals("n"));
        if(sn.equals("s") && !filtrati.isEmpty()){
            filtraPerPrenotazioneOnline(filtrati);
        }

        System.out.print("Vuoi cercare solo i ristoranti con un tipo di cucina specifico? [s/n]: ");
        do{
            sn = scanner.nextLine().trim().toLowerCase();
        } while(!sn.equals("s") && !sn.equals("n"));
        if(sn.equals("s") && !filtrati.isEmpty()){
            System.out.print("Inserisci il tipo di cucina specifico: ");
            String tipoCucina = scanner.nextLine().trim().toLowerCase();
            filtraPerTipoCucina(filtrati, tipoCucina);
        }
        stampaRistorantiFiltrati(filtrati);
        return filtrati;
    }*/
    public List<Ristorante> cercaRistorante(){
        List<Ristorante> filtrati = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        String sn;

        // Filtro per città (obbligatorio)
        boolean cittaTrovata = false;
        while (!cittaTrovata) {
            System.out.print("\nInserici la città in cui vuoi cercare il ristorante: ");
            String citta = scanner.nextLine().trim().toLowerCase();
            filtrati.clear(); // Puliamo la lista prima di ogni nuovo tentativo

            if (!filtraPerCitta(filtrati, citta)) {
                System.out.println("Nessun ristorante trovato in questa città.");
                System.out.print("Vuoi cercare in un'altra città? [s/n]: ");
                String risposta;
                do {
                    risposta = scanner.nextLine().trim().toLowerCase();
                } while (!risposta.equals("s") && !risposta.equals("n"));

                if (risposta.equals("n")) {
                    return new ArrayList<>(); // Ritorna una lista vuota se l'utente non vuole continuare
                }
                // Se l'utente risponde 's', il ciclo continua e chiede una nuova città
            } else {
                cittaTrovata = true;
            }
        }
        // Filtro per prezzo
        System.out.print("Vuoi cercare per fascia di prezzo? [s/n]: ");
        do{
            sn = scanner.nextLine().trim().toLowerCase();
        }while(!sn.equals("s") && !sn.equals("n"));

        if(sn.equals("s")){
            double prezzoMin, prezzoMax;
            do{
                System.out.print("Inserisci il prezzo minimo: ");
                prezzoMin = scanner.nextDouble();
                System.out.print("Inserisci il prezzo massimo: ");
                prezzoMax = scanner.nextDouble();
                if(prezzoMin > prezzoMax){
                    System.out.println("Il prezzo minimo non può essere maggiore del prezzo massimo.");
                }
            }while(prezzoMax < prezzoMin);
            filtraPerPrezzo(filtrati, prezzoMax, prezzoMin);
            scanner.nextLine(); // Consumare il newline rimasto
        }
        // Filtro per delivery
        System.out.print("Vuoi cercare solo i ristoranti con servizio delivery? [s/n]: ");
        do{
            sn = scanner.nextLine().trim().toLowerCase();
        }while(!sn.equals("s") && !sn.equals("n"));
        if(sn.equals("s")){  // Rimosso il controllo !filtrati.isEmpty()
            filtraPerDelivery(filtrati);
        }
        // Filtro per prenotazione online
        System.out.print("Vuoi cercare ristoranti con solo prenotazione online? [s/n]: ");
        do{
            sn = scanner.nextLine().trim().toLowerCase();
        }while(!sn.equals("s") && !sn.equals("n"));
        if(sn.equals("s")){  // Rimosso il controllo !filtrati.isEmpty()
            filtraPerPrenotazioneOnline(filtrati);
        }
        // Filtro per tipo cucina
        System.out.print("Vuoi cercare solo i ristoranti con un tipo di cucina specifico? [s/n]: ");
        do{
            sn = scanner.nextLine().trim().toLowerCase();
        }while(!sn.equals("s") && !sn.equals("n"));
        if(sn.equals("s")){  // Rimosso il controllo !filtrati.isEmpty()
            System.out.print("Inserisci il tipo di cucina specifico: ");
            String tipoCucina = scanner.nextLine().trim().toLowerCase();
            filtraPerTipoCucina(filtrati, tipoCucina);
        }

        stampaRistorantiFiltrati(filtrati);
        return filtrati;
    }

    //Filtri
    /*private void filtraPerCitta(List<Ristorante> filtrati, String citta){
        for(Ristorante r : listaRistoranti){
            if(r.getCitta().toLowerCase().equals(citta)){
                filtrati.add(r);
            }
        }
        System.out.println("Filtro per città inserito.");
        if(filtrati.isEmpty()) {
            System.out.println("Non sono stati trovati ristoranti nella città: " + citta);
        } else {
            System.out.println("Trovati " + filtrati.size() + " ristoranti in " + citta);
        }
    }*/
    private boolean filtraPerCitta(List<Ristorante> filtrati, String citta){
        for(Ristorante r : listaRistoranti){
            if(r.getCitta().toLowerCase().equals(citta)){
                filtrati.add(r);
            }
        }
        System.out.println("Filtro per prezzo medio inserito.");
        if(filtrati.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
    private void filtraPerPrezzo(List<Ristorante> filtrati, double prezzoMax, double prezzoMin){
        filtrati.removeIf(r -> r.getFasciaPrezzo() < prezzoMin || r.getFasciaPrezzo() > prezzoMax);
        System.out.println("Filtro per prezzo medio inserito.");
    }
    private void filtraPerDelivery(List<Ristorante> filtrati){
        filtrati.removeIf(r -> !r.getServizioDelivery());
        System.out.println("Filtro per delivery medio inserito.");
    }
    private void filtraPerPrenotazioneOnline(List<Ristorante> filtrati){
        filtrati.removeIf(r -> !r.getServizioPrenotazioneOnline());
        System.out.println("Filtro per prenotazione online inserito.");
    }
    private void filtraPerTipoCucina(List<Ristorante> filtrati, String tipoCucina){
        filtrati.removeIf(r -> !r.getTipoCucina().equals(tipoCucina));
        System.out.println("Filtro per tipo cucina inserito.");
    }

}