package it.eforhum.presentazione;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import it.eforhum.appuntamento.Appuntamento;

public class TestCalendar {
        public static void main(String[] args) {
                // ✅ Creo lista appuntamenti di esempio
                ArrayList<Appuntamento> appts = new ArrayList<>();

                appts.add(new Appuntamento(
                                LocalDate.of(2025, 9, 25),
                                LocalTime.of(9, 0),
                                LocalTime.of(10, 0),
                                "Riunione progetto",
                                "Milano",
                                true));

                appts.add(new Appuntamento(
                                LocalDate.of(2025, 9, 25),
                                LocalTime.of(14, 0),
                                LocalTime.of(15, 0),
                                "Call con cliente",
                                "Remoto",
                                true));

                appts.add(new Appuntamento(
                                LocalDate.of(2025, 9, 28),
                                LocalTime.of(16, 30),
                                LocalTime.of(17, 0),
                                "Dentista",
                                "Studio Rossi",
                                false));

                appts.add(new Appuntamento(
                                LocalDate.of(2025, 10, 2),
                                LocalTime.of(11, 0),
                                LocalTime.of(13, 0),
                                "Esame università",
                                "Politecnico",
                                true));

                // ✅ Stampa calendario del mese (Settembre 2025)
                // System.out.println("=== Calendario del mese ===");
                // TerminalCalendar.renderMonth(2025, 9, appts);

                // ✅ Stampa prossimi 14 giorni da oggi
                System.out.println("\n=== Prossimi 14 giorni ===");
                // TerminalCalendar.renderCurrentWeek(appts);
                TerminalCalendar.renderDays(30, new String[] {});
        }
}
