package Dominio;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class ListaUtenti {
    public ListaUtenti(){}
    public static List<Utente> listaUtenti = new ArrayList<Utente>();
    public List<Utente> getListaUtenti(){
        return listaUtenti;
    }

    public void setListaUtenti(List<Utente> listaUtenti) {
        listaUtenti = listaUtenti;
    }

    public void salvaUtentiSuCSV(){
        //accesso al file e salvataggio della lista su file di testo Utenti.txt
    }

    public void ricavaUtentiDaCSV(){
        //lettura del file CSV e salvataggio dei dati sulla lista
    }

    public void registraUtente(Utente nuovoUtente){
        //listaUtenti.add(nuovoUtente);
        //registrazioneUtente
    }
}
