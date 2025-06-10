package Dominio;

public class Preferito {
    String emailUtente;
    String nomeRistorante;
    public Preferito(Utente utente, ListaRistoranti listaRistoranti) {
        this.emailUtente = utente.getEmail();
        Ristorante ristorante = listaRistoranti.cercaPerNome("Inserisci il nome del ristorante che vuoi aggiungere alla tua lista dei preferiti: ");
        if(ristorante != null)
            this.nomeRistorante = ristorante.getNome();
        else
            this.nomeRistorante = null;
    }

    //Costruttore
    public Preferito(String emailUtente, String nomeRistorante) {
        this.emailUtente = emailUtente;
        this.nomeRistorante = nomeRistorante;
    }

    //Medoto Get
    public String getEmailUtente() {
        return emailUtente;
    }
    public String getNomeRistorante() {
        return nomeRistorante;
    }

    //Metodo to string
    @Override
    public String toString(){
        return this.emailUtente + "," + nomeRistorante;
    }
}
