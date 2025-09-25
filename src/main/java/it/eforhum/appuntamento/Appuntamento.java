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

    public LocalDate getData() {
        return data;
    }

    public LocalTime getOraInizio() {
        return oraInizio;
    }

    public LocalTime getOraFine() {
        return oraFine;
    }

    public String getDescrizione(){
        return descrizione;
    }

    public String getPosizione() {
        return posizione;
    }

    public boolean isStato() {
        return stato;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
    
    public void setOraInizio(LocalTime oraInizio) {
        this.oraInizio = oraInizio;
    }

    public void setOraFine(LocalTime oraFine) {
        this.oraFine = oraFine;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setPosizione(String posizione) {
        this.posizione = posizione;
    }

    public void setStato(boolean stato) {
        this.stato = stato;
    }

    public String show(){
        return data + " " + oraInizio + " " + oraFine + " " + descrizione + " " + posizione + " " + stato;
    }

    public String prepear(){
        return "-||-" + data + "|--|" + oraInizio + "|--|" + oraFine + "|--|" + descrizione + "|--|" + posizione + "|--|" + stato + "-||-";
    }
}
