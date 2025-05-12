package Dominio;

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
    }

    public void ricavaRistorantiDaCSV(){
        //lettura del file Ristoranti.txt e salvataggio nella lista listaRistoranti
    }
    public Ristorante cercaRistorante(){
        //funzione per la ricerca del ristorante nella lista
        return null;
    }
}
