package it.eforhum.appuntamento;

import java.time.LocalDate;
import java.time.LocalTime;

public class Appuntamento {
    private LocalDate data;
    private LocalTime oraInizio;
    private LocalTime oraFine;
    String descrizione;
    String posizione;
    boolean stato;

    public Appuntamento(LocalDate data, LocalTime oraInizio, LocalTime oraFine, String descrizione, String posizione, boolean stato) {
        this.data = data;
        this.oraInizio = oraInizio;
        this.oraFine = oraFine;
        this.descrizione = descrizione;
        this.posizione = posizione;
        this.stato = stato;
    }

    
}
