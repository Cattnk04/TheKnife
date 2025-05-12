package Dominio;

import java.util.List;
import java.util.ArrayList;

public class ListaUtenti {
    public ListaUtenti(){
        ricavaUtentiDaCSV();
    }
    public static List<Utente> listaUtenti = new ArrayList<Utente>();
    public List<Utente> getListaUtenti(){
        return listaUtenti;
    }

    public void setListaUtenti(List<Utente> listaUtenti) {
        this.listaUtenti = listaUtenti;
    }

    public void salvaUtentiSuCSV(){
        //accesso al file e salvataggio della lista su file di testo Utenti.txt
    }

    public void ricavaUtentiDaCSV(){
        //lettura del file CSV e salvataggio dei dati sulla lista
    }

    public void aggiungiUtente(Utente nuovoUtente){
        //listaUtenti.add(nuovoUtente);
        //registrazioneUtente
    }

    public Utente trovaUtente(String email){
        //scorri la lista e trova l'utente
        return null;
    }
    public boolean utenteDuplicato(Utente nuovoUtente){
        //scorrere la lista e verificare se esitono altri utenti con la stessa email del nuovo utente e in caso tornare true
        return false;
    }
}
