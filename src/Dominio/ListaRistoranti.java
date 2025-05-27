package Dominio;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    public void salvaRistorantiSuCSV(){
        //Accesso al file Ristoranti.txt e scrittura dei dati dalla lista listaRistoranti
        try {
            FileWriter writer = new FileWriter("Ristoranti.txt");
            for(Ristorante r : listaRistoranti){
                writer.append(r.toString() + "\n");
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void ricavaRistorantiDaCSV(){
        //lettura del file Ristoranti.txt e salvataggio nella lista listaRistoranti
        List<Ristorante> listaRistoranti = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/Persistenza/Ristoranti.txt"))){
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
    public Ristorante cercaRistorante(){
        //funzione per la ricerca del ristorante nella lista
        return null;
    }
}
