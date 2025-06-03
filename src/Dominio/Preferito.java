package Dominio;

public class Preferito {
    String emailUtente;
    String nomeRistorante;
    public Preferito(Utente utente, ListaRistoranti listaRistoranti) {
        this.emailUtente = utente.getEmail();
        if(listaRistoranti.cercaPerNome() != null)
            this.nomeRistorante = listaRistoranti.cercaPerNome().getNome();
        else
            this.nomeRistorante = null;
    }
    public Preferito(String emailUtente, String nomeRistorante) {
        this.emailUtente = emailUtente;
        this.nomeRistorante = nomeRistorante;
    }
    public String getEmailUtente() {
        return emailUtente;
    }
    public String getNomeRistorante() {
        return nomeRistorante;
    }
    @Override
    public String toString(){
        return this.emailUtente + "," + nomeRistorante;
    }
}
