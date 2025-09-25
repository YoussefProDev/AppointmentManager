package it.eforhum;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GestioneAgende {

    List<String> agende;    
    int scelta=0;
    Scanner scanner=new Scanner(System.in);

    public void menu() {
        boolean exit = false;
 
    while (!exit) {
        System.out.println("Menu Gestione Agende");
        System.out.println("1. Crea Agenda");
        System.out.println("2.Visualizza Appuntamenti tutte le agende");
        System.out.println("3. Visualizza Agende");
        System.out.println("4. Modifica Agenda");
        System.out.println("5. Elimina Agenda");
        System.out.println("6. Esci");
 
            int scelta = numberInput("Seleziona un'opzione: ", 1, 6);
 
            switch (scelta) {
                case 1 -> creaAgenda();
                case 2 -> visualizzaAppuntamentiTutteLeAgende();
                case 3 -> visualizzaAgende();
                case 4 -> eliminaAgendaDaFile();
                case 5 -> exit = true;
            }
        }
    }

    private int numberInput(String text, int min, int max) {
        while (true) {
            System.out.print(text);
            try {
                int selection = Integer.parseInt(scanner.nextLine());
                
                if (selection >= min && selection <= max){
                 
                    return selection;
                
                }
                else{
                    System.out.println("Numero fuori dal range, riprova.");
                }
            } catch (NumberFormatException e) {
            
                System.out.println("Valore non valido, inserisci un numero.");
            
            }
        }
    }  
    public void creaAgenda() {
      
        System.out.println("Digita il nome della nuova agenda:");
        String nome=scanner.next();  
        try {
        
            scriviSuFIle(nome);
            System.out.println("Agenda creata con successo.");
        
        } catch (Exception e) {
        
            System.out.println("Errore nella scrittura del file: " + e.getMessage());
        
        }
    }
    public void scriviSuFIle(String nomeFile) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("ListaAgende.txt",true))){
               
            bw.write("Agenda: " + nomeFile + "\n");
            
        } catch (IOException e) {
               
            e.printStackTrace();

        }
    }
    public void visualizzaAppuntamentiTutteLeAgende() {
        boolean exit = false;        
        
        while (!exit) {

        System.out.println("1) Visualizzi l'impegni del mese");
        System.out.println("2) Esci");
        int scelta = numberInput("Seleziona un'opzione: ", 1, 2);
            switch (scelta) {
                case 1:
                    
                    System.out.println("Digita l'anno (es. 2024):");
                    int anno=scanner.nextInt();
                    scanner.nextLine()
                    System.out.println("Digita il mese (1-12):");
                    int mese=scanner.nextInt();
                    scanner.nextLine();
                    TerminalCalendar.renderMonth(anno,mese,riempiArray());

                break;
                case 2:
                 exit=true;
                 break;
            }
        }
    }
    public void visualizzaAgende() {
        int scl=0;

        try (BufferedReader br = new BufferedReader(new FileReader("ListaAgende.txt"))) {
            String line;
            System.out.println("Elenco Agende:");
            while ((line = br.readLine()) != null) {
                agende.add(line);
            }
        } catch (IOException e) {
            System.out.println("Errore nella lettura del file: " + e.getMessage());
        }  
        if(!agende.isEmpty()){
            for(int i=0;i<agende.size();i++){
                System.out.println((i+1)+") "+agende.get(i));
            }
            scelta=scanner.nextInt();
            scanner.nextLine();
            
            Agenda agenda=new Agenda(agende.get(scelta-1));
            agenda.menu();    
        } else {
            System.out.println("Non ci sono agende da visualizzare.");        
        }   
    }
    private String[] riempiArray(){
        try (BufferedReader br = new BufferedReader(new FileReader("ListaAgende.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                agende.add(line);
            }
        } catch (IOException e) {
            System.out.println("Errore nella lettura del file: " + e.getMessage());
        }  

        String[] arrayAgende = new String[agende.size()];
        for(int i=0;i<agende.size();i++){
            arrayAgende[i]=agende.get(i);
        }
        return arrayAgende;

    }
    public void eliminaAgendaDaFile() {

        String [] agendeArray = riempiArray();
        System.out.println("Digita il nome dell'agenda da eliminare:");
        String nomeAgenda = scanner.nextLine();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("ListaAgende.txt"))) {
            for (String agenda : agendeArray) {
                if (!agenda.equals("Agenda: " + nomeAgenda)) {
                    bw.write(agenda + "\n");
                }
            }
            System.out.println("Agenda eliminata con successo.");
        } catch (IOException e) {
            System.out.println("Errore nella scrittura del file: " + e.getMessage());
        }
        

    }

}


