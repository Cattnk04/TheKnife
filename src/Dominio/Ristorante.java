package Dominio;
import java.io.*;

public class Ristorante {
    private final String nome;
    private String nazione;
    private String provincia;
    private String indirizzo;
    private final String emailPropreitario;
    private Integer prezzoMedio;
    private boolean servizioDelivery;
    private boolean servizioPrenotazioneOnline;
    private String tipoCucina;

    Ristorante(String nome, String nazione, String provincia, String indirizzo, Integer fasciaPrezzo, String emailPropreitario,boolean servizioDelivery, boolean servizioPrenotazioneOnline, String tipoCucina) throws RuntimeException{
        this.nome = nome.trim();
        //check se esiste un altro ristorante con questo nome
        controllaRistoranteDuplicato(nome);
        this.nazione = nazione.trim();
        this.provincia = provincia.trim();
        this.indirizzo = indirizzo.trim();
        this.prezzoMedio = fasciaPrezzo;
        this.emailPropreitario = emailPropreitario.trim();
        this.servizioDelivery = servizioDelivery;
        this.servizioPrenotazioneOnline = servizioPrenotazioneOnline;
        this.tipoCucina = tipoCucina;
    }

    public String getNome(){
        return this.nome;
    }
    public String getNazione(){
        return this.nazione;
    }
    public String getProvincia(){
        return this.provincia;
    }
    public String getIndirizzo(){
        return this.indirizzo;
    }
    public String getEmailPropreitario(){
        return this.emailPropreitario;
    }
    public Integer getPrezzoMedio(){
        return this.prezzoMedio;
    }
    public boolean getServizioDelivery(){
        return this.servizioDelivery;
    }
    public boolean getServizioPrenotazioneOnline(){
        return this.servizioPrenotazioneOnline;
    }
    public String getTipoCucina(){
        return this.tipoCucina;
    }

    public void setNazione(String nazione){
        this.nazione = nazione;
    }
    public void setProvincia(String provincia){
        this.provincia = provincia;
    }
    public void setIndirizzo(String indirizzo){
        this.indirizzo = indirizzo;
    }
    public void setPrezzoMedio(Integer prezzoMedio){
        this.prezzoMedio = prezzoMedio;
    }
    public void setServizioDelivery(boolean servizioDelivery){
        this.servizioDelivery = servizioDelivery;
    }
    public void setServizioPrenotazioneOnline(boolean servizioPrenotazioneOnline){
        this.servizioPrenotazioneOnline = servizioPrenotazioneOnline;
    }
    public void setTipoCucina(String tipoCucina){
        this.tipoCucina = tipoCucina;
    }
    public void salvaRistorante() {
        File fileRistoranti = new File("Ristoranti.txt");

        try {
            FileWriter writer = new FileWriter(fileRistoranti, true);
            String stringa = this.nome + ',' + this.nazione + ',' + this.provincia + ',' + this.indirizzo + ',' + this.prezzoMedio.toString() + ','+ this.servizioDelivery + ',' + this.servizioPrenotazioneOnline + ',' + this.tipoCucina + ',' + this.emailPropreitario.trim();
            writer.write(stringa);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void controllaRistoranteDuplicato(String nomeRistorante) throws RuntimeException{
        try{
            File fileRistoranti = new File("Ristoranti.txt");
            FileReader reader = new FileReader(fileRistoranti);
            BufferedReader br = new BufferedReader(reader);
            String stringa = br.readLine();
            while(stringa!=null){
                String[] arrayStringaRistorante = stringa.split(",");
                if (arrayStringaRistorante[0].equals(nomeRistorante))
                    throw new RuntimeException("Ristorante non puo' essere duplicato");
                else
                    stringa = br.readLine();
            }
        } catch(FileNotFoundException e){
            System.out.println("File non trovato");
        } catch(IOException e){
            System.out.println("IO Exception");
        }
    }
}
