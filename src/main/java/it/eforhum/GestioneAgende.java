package it.eforhum;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

public class GestioneAgende {
    
    List<Agenda> agende;    
    int scelta=0;
    Scanner scanner=new Scanner(System.in);

    public void menu() {
        do{
        System.out.println("Menu Gestione Agende");
        System.out.println("1. Crea Agenda");
        System.out.println("2.Visualizza Appuntamenti tutte le agende");
        System.out.println("3. Visualizza Agende");
        System.out.println("4. Modifica Agenda");
        System.out.println("5. Elimina Agenda");
        System.out.println("0. Esci");
        scelta=scanner.nextInt();
        }while(scelta<0 || scelta>5);
        switch(scelta){
            case 1:
                creaAgenda();
                break;
            case 2:
                visualizzaAppuntamenti();
                break;
            case 3:
                visualizzaAgende();
                break;
            case 4:
                modificaAgenda();
                break;
            case 5:
                eliminaAgenda();
                break;
            case 0:
                System.out.println("Uscita in corso...");
                break;
        }
    
    public void creaAgenda() {
        System.out.println("Digita il nome della nuova agenda:");
        String nome=scanner.next();
        
    }
    public void scriviSuFIle() {
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
                

    }

}
