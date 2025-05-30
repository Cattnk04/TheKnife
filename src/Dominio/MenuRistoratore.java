package Dominio;

import java.util.Scanner;

public class MenuRistoratore {
    private Utente utenteCorrente;
    private Scanner scanner;

    public MenuRistoratore(Utente utente, Scanner scanner) {
        this.utenteCorrente = utente;
        this.scanner = scanner;
        mostraMenuRistoratore();
    }
    public void mostraMenuRistoratore() {
        // Implementa qui la logica del menu ristoratore
        System.out.println("\n Menu Ristoratore - Da implementare");
    }


}