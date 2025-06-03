package Dominio;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ListaRistoranti {
    public ListaRistoranti(){
        ricavaRistorantiDaCSV();
    }
    public List<Ristorante> listaRistoranti = new ArrayList<>();

    public List<Ristorante> getListaRistoranti() {
        return listaRistoranti;
    }

    public void setListaRistoranti(List<Ristorante> listaRistoranti){
        this.listaRistoranti = listaRistoranti;
    }

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

    public void ricavaRistorantiDaCSV(){
        //lettura del file Ristoranti.txt e salvataggio nella lista listaRistoranti
        List<Ristorante> listaRistoranti = new ArrayList<>();
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
    public void inserisciRistorante(Ristorante ristorante) {
        //funzione per l'inserimento di un nuovo ristorante nella lista
        listaRistoranti.add(ristorante);
    }
    public Ristorante cercaPerNome(){
        //Funzione per cercare un ristorante in base al suo nome
        Scanner scanner = new Scanner(System.in);
        System.out.print("Inserisci il nome del ristorante che vuoi cercare: ");
        String nomeRistorante = scanner.nextLine();
        for (Ristorante r : listaRistoranti) {
            if(nomeRistorante.equals(r.getNome())){
                return r;
            }
        }
        System.out.print("Il nome del ristorante che hai inserito non esiste!");
        return null;

    }
    public List<Ristorante> cercaRistorante(){
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
        if(sn == "s"){
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
        if(sn == "s" && !filtrati.isEmpty()){
            filtraPerDelivery(filtrati);
        }
        System.out.print("Vuoi cercare ristoranti con solo prenotazione online? [s/n]: ");
        do{
            sn = scanner.nextLine().trim().toLowerCase();
        }while(!sn.equals("s") && !sn.equals("n"));
        if(sn == "s" && !filtrati.isEmpty()){
            filtraPerPrenotazioneOnline(filtrati);
        }
        System.out.print("Vuoi cercare solo i ristoranti con un tipo di cucina specifico? [s/n]: ");
        do{
            sn = scanner.nextLine().trim().toLowerCase();
        } while(!sn.equals("s") && !sn.equals("n"));
        if(sn == "s" && !filtrati.isEmpty()){
            System.out.print("Inserisci il tipo di cucina specifico: ");
            String tipoCucina = scanner.nextLine().trim().toLowerCase();
            filtraPerTipoCucina(filtrati, tipoCucina);
        }
        scanner.close();
        return filtrati;
    }
    private void filtraPerCitta(List<Ristorante> filtrati, String citta){
        for(Ristorante r : listaRistoranti){
            if(r.getCitta().equals(citta)){
                filtrati.add(r);
            }
        }
        System.out.println("Filtro per città inserito.");
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