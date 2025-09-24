package it.eforhum.presentazione;

import java.util.ArrayList;

public class VisualizzaAppuntamenti {

    static void  mostraAppuntamenti(ArrayList<Appuntamento> appuntamenti) {
        for (Appuntamento a : appuntamenti) {
            System.out.println(a);
        }
    }

    
}
