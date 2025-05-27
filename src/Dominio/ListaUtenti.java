package Dominio;

import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.io.FileWriter;

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
        for(Utente utente : listaUtenti){
            try {
                FileWriter writer = new FileWriter("Utenti.txt");
                writer.append(utente.toString() + "\n");
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void ricavaUtentiDaCSV(){
        //lettura del file CSV e salvataggio dei dati sulla lista
        try {
            FileReader reader = new FileReader("../Persistenza/Utenti.txt");
            BufferedReader bufferedReader = new BufferedReader(reader);
            String riga;
            while((riga = bufferedReader.readLine()) != null){
                String[] dati = riga.split(",");
                String email = dati[0];
                String nome = dati[1];
                String cognome = dati[2];
                String password = dati[3];
                String provincia = dati[4];
                String ristoratore = dati[5];
                Utente utente = new Utente(email, nome, cognome, password, provincia, (Boolean.parseBoolean(ristoratore)));
                this.listaUtenti.add(utente);
            }

        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void aggiungiUtente(Utente nuovoUtente){
        listaUtenti.add(nuovoUtente);
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
