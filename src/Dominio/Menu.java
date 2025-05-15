package Dominio;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;

public class Menu {
    private static final String FILE_UTENTI = "src/Dominio/Utente.txt";

    public Menu(){
        //Creazione delle diverse liste per l'accesso ai dati
        ListaUtenti listaUtenti = new ListaUtenti();
        ListaRistoranti listaRistoranti = new ListaRistoranti();
        int scelta = 0; //scelta del menu
        do{
            menuGuest(new Scanner(System.in));
            switch(scelta){
                case 1:
                    registraUtente(listaUtenti); //chiamerà la funzione nella lista per aggiungere l'utente li
                    //LoginPostRegistrazione("username", "password"); //vedere poi come farlo se tornare subito l'utente appena creato
                    break;
                case 2:
                    loginUtente(listaUtenti);
                    break;
                case 3:
                    listaRistoranti.cercaRistorante();
                    break;
                case 4:
                    System.out.println("Grazie per aver usato il nostro servizio!");
                    return;
                default:
                    System.out.println("Scelta non valida!");
            } // chiusura switch

        }while (scelta != 0);
    }

    public static int menuGuest(Scanner scanner){
        int choice;
        System.out.println("Benvenuto nella schermata home ospite!\n");
        System.out.println("Scegli un'opzione:");
        System.out.println("1. Registrati");
        System.out.println("2. Accedi");
        System.out.println("3. Cerca ristorante");
        System.out.println("0. Esci dall'applicazione");
        System.out.print("La tua scelta: ");

        choice = scanner.nextInt();

        scanner.nextLine(); // Pulizia della linea
        return choice;
    }
    // metodo per la registrazione
    public static void registraUtente(ListaUtenti listaUtenti){
        Scanner scanner = new Scanner(System.in); //per inserire i dati
        System.out.println("=== Registrazione ===");
        String nome = "";
        do{
            System.out.print("Inserisci il tuo nome: ");
            nome = scanner.nextLine();
            if(nome.length()<=1)
                System.out.println("Nome non valido");
        }while (nome.length()<=1);
        String cognome = "";
        do{
            System.out.print("Inserisci il tuo cognome: ");
            cognome = scanner.nextLine();
            if(cognome.length()<=1){
                System.out.println("Cognome non valido");
            }
        } while (cognome.length()<=1);

        String provincia = "";
        do{
            System.out.print("Inserisci la provincia di domicilio (prima lettera maiuscola): ");
            provincia = scanner.nextLine();
        } while (!trovaProvincia(provincia));

        boolean valido,ristoratore = false;
        do{
            valido = true;
            System.out.print("Sei proprietario di un ristorante? [s/n]: ");
            if(scanner.nextLine().trim().toLowerCase().equals("s")){
                ristoratore = true;
            } else if  (scanner.nextLine().trim().toLowerCase().equals("n")){
                ristoratore = false;
            }
            else valido = false;
        } while (!valido);

        String email = "";
        do{
            valido = true;
            System.out.print("Inserisci la tua email: ");
            email = scanner.nextLine();
            email = email.toLowerCase();
            if(!email.contains("@") || !email.contains(".")){
                valido = false;
                System.out.println("Email non valida");
            }
        } while (!valido);
        String password = "";
        do{
            valido = true;
            System.out.print("Inserisci la tua password: ");
            password = scanner.nextLine();
            //VEDERE COME FAR VISUALIZZARE GLI ASTERISCHI INVECE DELLA STRINGA
                /*devono essere visualizzate mentre scrive o dopo?
                Perchè se vogliamo gli asterischi per ogni carattere immesso,
                bisogna simulare un meccanismo in cui intercetti i tasti premuti e
                visualizzi solo gli asterischi al posto dei caratteri.
                Questo richiede l'uso della libreria esterna come Jline.
                Se invece vogliamo mantenere nascosta la password,
                è meglio usare Console oppure JPasswordField, che è per applicazioni grafiche
                 */
            if(password.length()<8){
                //SE VOGLIAMO POSSIAMO METTERE QUI ALTRE CONDIZIONI
                // TIPO CARETTERI SPECIALI O MAIUSCOLE/MINUSCOLE
                valido = false;
                System.out.println("Password non valida");
            }
        } while (!valido);
        scanner.close();
        //Storing del nuovo utente nel file
        Utente nuovoUtente = new Utente(email, nome, cognome, password, provincia, ristoratore);
        if(!listaUtenti.utenteDuplicato(nuovoUtente)){
            listaUtenti.aggiungiUtente(nuovoUtente);
        }
    }
    //metodo trovaProvincia per verificare se la provincia inserita dall'utente esiste
    private static boolean trovaProvincia(String provincia){
        try{
            File fileProvince = new File("src/Dominio/Province.txt");
            if (fileProvince.exists()){
                System.out.println("Esiste");
            }else{
                System.out.println("Non esiste");
            }
            FileReader reader = new FileReader(fileProvince);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String data = bufferedReader.readLine();

            while(data != null && !data.toLowerCase().contains(provincia.toLowerCase())){
                data = bufferedReader.readLine();
            }
            bufferedReader.close();
            reader.close();
            if(data == null){
                return false;
            } else {
                return true;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // metodo per il login
    public static Utente loginUtente(ListaUtenti listaUtenti) {
        boolean trovato = false; // Flag per controllare se il login riesce
        Scanner scanner = new Scanner(System.in);
        do {
            //inserimento dei dati
            System.out.println("=== Login ===");
            System.out.print("Inserisci la tua e-mail: ");
            String email = scanner.nextLine().trim();
            System.out.print("Inserisci la tua password: ");
            //va fatta la cosa degli asterischi qui
            String password = scanner.nextLine().trim();
            //Fine inserimento dati da cercare

            //SCORRERE LA LISTA PER VEDERE SE ESISTE UN UTNTE CON QUESTI DATI
            for(Utente utente : listaUtenti.getListaUtenti()){
                if(utente.getEmail() == email && utente.getPassword() == password){
                    return utente;
                }
            }

            //altrimenti
            if (!trovato) {
                System.out.println("Email o password errati!");
                System.out.print("Vuoi riprovare? (sì/no): ");
                String risposta = scanner.nextLine().trim();
                if (risposta.equalsIgnoreCase("no")) {
                    System.out.println("Grazie per aver usato il nostro servizio!");
                    scanner.close();
                    return null;
                }
            }
        }while(!trovato);
        return null;
    }


}
