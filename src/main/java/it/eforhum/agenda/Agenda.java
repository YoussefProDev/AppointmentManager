package it.eforhum.agenda;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class Agenda {
  
    ArrayList<Appuntamento> appuntamenti;
    String nome;
    String filename;

    public Agenda(String nome) throws Exception{
        this.nome = nome;
        this.filename = nome +".txt";
        this. appuntamenti = new ArrayList<Appuntamento>();
        
        try{

            this.riempiDaFile();
        }catch(Exception e){
            
        }

    }

    public void riempiDaFile() throws FileNotFoundException{
    File file = new File(this.filename);
        if (!file.exists()){
           // throw FileNotFoundException; 
           return;
        }
          

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null && !line.isEmpty()) {
                String[] appuntamenti_grezzi = line.split("-\\|\\|-");
                for (String a : appuntamenti_grezzi) {
                    String[] fields = a.split("\\|--\\|");
                    if (fields.length == 7) {
                        appuntamenti.add(new Appuntamento(fields[0], fields[1], fields[2],
                                fields[3], fields[4], fields[5]));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void scriviFile() {
        File file = new File(this.filename);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (int i = 0; i < this.appuntamenti.size(); i++) {
                bw.write(this.appuntamenti.get(i).prepare());
                if (i < this.appuntamenti.size() - 1) {
                    bw.write("-||-");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean chackValidita(LocalDate data, LocalTime ora, LocalTime ora_fine) {
        for (Appuntamento a : this.appuntamenti) {
            if (a.getData().isEqual(data)) {
                if (!ora_fine.isBefore(a.getOra()) && !ora.isAfter(a.getOraFine())) {
                    return false;
                }
            }
        }
        return true;
    }

    public void elimina(int index){
        this.appuntamenti.remove(index);
    }


public void inserisciAppuntamento(LocalDate data, LocalTime ora, LocalTime ora_fine, String posizione, String descrizione){



        if(ora_fine.isBefore(ora)){
            System.out.println("Intervallo temporale non valido");
            return;
        }

        if (!isFree(data, ora, ora_fine)) {
            System.out.println("Intervallo temporale non valido");
            return;
        }
  
        appuntamenti.add(new Appuntamento(data, ora, ora_fine, posizione, false, descrizione));
        sort();
        scriviFile();
    }

    public void menu(){
        boolean errore = false;
        Scanner s = new Scanner(System.in);
        int scelta = 0; 

        do{

            try{
                errore = false;
                System.out.println("seleziona azione");
                System.out.println("1-\tnuovo appuntamento\n2-\tmodifica appuntamento\n3-\telimina appuntamento\n4-\tvisualizza appuntamenti\n0-\tesci");
                scelta = s.nextInt();
                
                if(scelta < 0 || scelta >4){
                    errore = true;
                    System.out.println("input non valido");
                }
            }catch(Exception e){
                System.out.println("input non valido");
                errore = true;
            }

        }while(errore);

        switch(scelta){
            case 1:
            LocalDate data;
            LocalTime ora;
            LocalTime ora_fine;
            String descrizione;
            String posizione;

                
                //------------------DATA
                        do{
                            System.out.println("inserisci data dd-mm-aaaa");
                            try{
                                    errore = false;
                                    String input = s.next();
                                    String[] data_temp = input.split("-");
                                     data = LocalDate.of(Integer.parseInt(data_temp[0]), Month.of(Integer.parseInt(data_temp[1])) , Integer.parseInt(data_temp[2]));

                                }catch(Exception e){
                                    System.out.println("input non valido");
                                    errore = true;
                                }

                         }while(errore);

                //-------------------ORA
                        do{
                            System.out.println("inserisci ora ore:minuti");
                            try{
                                    errore = false;
                                    String input = s.next();
                                    String[] ora_temp = input.split(":");
                                     ora = LocalTime.of(Integer.parseInt(ora_temp[0]),Integer.parseInt(ora_temp[1]));

                                }catch(Exception e){
                                    System.out.println("input non valido");
                                    errore = true;
                                }

                         }while(errore);

                                //-------------------Fine
                        do{
                            System.out.println("inserisci ora ore:minuti");
                            try{
                                    errore = false;
                                    String input = s.next();
                                    String[] oraf_temp = input.split(":");
                                     ora_fine = LocalTime.of(Integer.parseInt(oraf_temp[0]),Integer.parseInt(oraf_temp[1]));
                                     
                                     if(ora.compareTo(ora_fine) < 0){
                                        System.out.println("ora fine non può essere minore di ora inizio");
                                        errore = true;
                                     }

                                }catch(Exception e){
                                    System.out.println("input non valido");
                                    errore = true;
                                }

                         }while(errore);

                    //--------------Descrizione

                        do{
                            System.out.println("inserisci descrizione");
                            try{
                                    errore = false;
                                    String input = s.next();
                                    descrizione = input;


                                }catch(Exception e){
                                    System.out.println("input non valido");
                                    errore = true;
                                }

                         }while(errore);

                    //------------------posizione

                        do{
                            System.out.println("inserisci posizione");
                            try{
                                    errore = false;
                                    String input = s.next();
                                    posizione = input;

                                }catch(Exception e){
                                    System.out.println("input non valido");
                                    errore = true;
                                }
                         }while(errore);


                         this.appuntamenti.add(new Appuntamento(data, ora, ora_fine, descrizione, posizione, false));
                         ordina();
            break;
           
            case 2:

            //--------------------modifica
                        this.mostra();
                         int input = 0;

                       do{
                        
                            System.out.println("scegli numero appuntamento da eliminare");
                            try{
                                    errore = false;
                                     input = s.nextInt();
                                   if(input < 0 || input >= this.appuntamenti.size()){
                                     errore = true;
                                   }

                                }catch(Exception e){
                                    System.out.println("input non valido");
                                    errore = true;
                                }

                         }while(errore);


                        do{
                        
                            System.out.println("1-\tmodifica data \n2-\tmodifica ora inizio\n3-\tmodifica ora fine\n4-\tmodifica descrizione \n5-\tmodifica posizione");
                            
                            try{
                                    errore = false;
                                     input = s.nextInt();
                                   if(input < 0 || input >= this.appuntamenti.size()){
                                     errore = true;
                                   }

                                }catch(Exception e){
                                    System.out.println("input non valido");
                                    errore = true;
                                }

                         }while(errore);

                         switch (input) {
                            case 1:
                                
                                break;
                            case 2:
                                
                                break;
                            case 3:
                                
                                break;
                            case 4:
                                
                                break;
                            case 5:
                                
                                break;
                         
                            default:
                                break;
                         }

                         




            break;
            case 3:
                         mostra();
                         input = 0;


                         //--------------selezione appuntamento
                  do{
                            System.out.println("scegli numero appuntamento da eliminare");
                            try{
                                    errore = false;
                                     input = s.nextInt();
                                   if(input < 0 || input >= this.appuntamenti.size()){
                                     errore = true;
                                   }

                                }catch(Exception e){
                                    System.out.println("input non valido");
                                    errore = true;
                                }

                         }while(errore);

                        elimina(input);


            case 4:
                this.mostra();
            break;

            case 0:
                         return;
            break;

            
        }

       

    }

     private void mostra(){
                for(int i = 0; i < this.appuntamenti.size(); i++){
                    System.out.println("\n\nappuntamento numero "+i);
                    System.out.println(this.appuntamenti.get(i).show());
                }
        }

    private void modifica(int index, LocalDate data, LocalTime ora, LocalTime ora_fine, String posizione, String descrizione){
        this.appuntamenti.replace(index, new  Appuntamento(data, ora, ora_fine, posizione, false, descrizione));

    }


 
    private void ordina() {
        appuntamenti.sort((a1, a2) -> {
            int cmp = a1.getData().compareTo(a2.getData());
            if (cmp != 0)
                return cmp;
            cmp = a1.getOra().compareTo(a2.getOra());
            if (cmp != 0)
                return cmp;
             // urgenza decrescente
        });
    }

    private void getAppuntamentoOn(LocalDate data){

    }






}
