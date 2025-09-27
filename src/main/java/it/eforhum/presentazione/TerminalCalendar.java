package it.eforhum.presentazione;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;

import it.eforhum.appuntamento.Appuntamento;

public class TerminalCalendar {

    // Configurazione visuale
    private static final int CELL_WIDTH = 20;
    private static final int CELL_HEIGHT = 8;
    private static final int MAX_APPTS_PER_CELL = CELL_HEIGHT - 1;

    public static void renderMonth(int year, int month, String[] agende) {

        renderMonth(year, month, getAppuntamentiFromAgenda(agende));
    }

    public static void renderMonth(int year, int month, ArrayList<Appuntamento> appts) {
        YearMonth ym = YearMonth.of(year, month);
        LocalDate firstOfMonth = ym.atDay(1);
        DayOfWeek firstWeekday = DayOfWeek.MONDAY;

        TerminalCalendarHeader(ym);

        LocalDate start = firstOfMonth;
        while (start.getDayOfWeek() != firstWeekday) {
            start = start.minusDays(1);
        }

        LocalDate cur = start;
        while (cur.isBefore(firstOfMonth.plusMonths(1)) || cur.getDayOfWeek() != firstWeekday) {
            printWeek(cur, ym, appts);
            cur = cur.plusDays(7);
        }
    }

    public static void renderDays(int days, String[] agende) {

        renderDays(days, getAppuntamentiFromAgenda(agende));
    }

    public static void renderDays(int days, ArrayList<Appuntamento> appts) {
        YearMonth ym = YearMonth.now();
        LocalDate start = LocalDate.now();
        LocalDate end = start.plusDays(days);

        System.out.printf("Appuntamenti dal %s al %s:%n", start, end.minusDays(1));
        System.out.println();
        System.out.printf("     %s %d%n", ym.getMonth(), ym.getYear());

        // Intestazione fissa: Lunedì → Domenica
        for (int i = 0; i < 7; i++) {
            System.out.print(center(start.plusDays(i).getDayOfWeek().toString(), CELL_WIDTH));
        }
        System.out.println();
        System.out.println();

        LocalDate cur = start;

        while (cur.isBefore(end)) {
            // Calcolo quanti giorni stampare in questa "settimana"
            int giorniRimanenti = (int) Math.min(7, end.toEpochDay() - cur.toEpochDay());
            String[][] cells = new String[giorniRimanenti][CELL_HEIGHT];

            // Costruisco solo i giorni effettivi
            for (int d = 0; d < giorniRimanenti; d++) {
                LocalDate cellDate = cur.plusDays(d);
                cells[d] = buildCell(cellDate, ym, appts);
            }

            // Stampa riga per riga
            for (int line = 0; line < CELL_HEIGHT; line++) {
                for (int d = 0; d < giorniRimanenti; d++) {
                    System.out.print(cells[d][line]);
                }
                System.out.println();
            }

            // Separatore
            String horizontalSep = repeat("-", CELL_WIDTH);
            for (int d = 0; d < giorniRimanenti; d++) {
                System.out.print(horizontalSep);
            }
            System.out.println();

            cur = cur.plusDays(giorniRimanenti);
        }
    }

    public static void renderCurrentWeek(String[] agende) {
        renderCurrentWeek(getAppuntamentiFromAgenda(agende));
    }

    public static void renderCurrentWeek(ArrayList<Appuntamento> appts) {
        YearMonth ym = YearMonth.from(LocalDate.now());
        LocalDate start = LocalDate.now();
        DayOfWeek firstWeekday = DayOfWeek.MONDAY;
        while (start.getDayOfWeek() != firstWeekday) {
            start = start.minusDays(1);
        }
        printWeek(start, ym, appts);
    }

    private static ArrayList<Appuntamento> getAppuntamentiFromAgenda(String[] agende) {
        ArrayList<Appuntamento> appts = new ArrayList<>();
        return appts;
    }

    private static void printWeek(LocalDate cur, YearMonth ym, ArrayList<Appuntamento> appts) {
        String[][] cells = new String[7][CELL_HEIGHT];

        for (int d = 0; d < 7; d++) {
            LocalDate cellDate = cur.plusDays(d);
            cells[d] = buildCell(cellDate, ym, appts);
        }

        for (int line = 0; line < CELL_HEIGHT; line++) {
            for (int d = 0; d < 7; d++) {
                System.out.print(cells[d][line]);
            }
            System.out.println();
        }

        String horizontalSep = repeat("-", CELL_WIDTH);
        for (int d = 0; d < 7; d++) {
            System.out.print(horizontalSep);
        }
        System.out.println();
    }

    private static String[] buildCell(LocalDate cellDate, YearMonth ym, ArrayList<Appuntamento> appts) {
        String[] lines = new String[CELL_HEIGHT];
        boolean inMonth = cellDate.getMonth().equals(ym.getMonth());

        String dateStr = String.valueOf(cellDate.getDayOfMonth());
        if (!inMonth) {
            dateStr = "(" + dateStr + ")";
        }

        lines[0] = padRight(dateStr, CELL_WIDTH);

        ArrayList<Appuntamento> list = new ArrayList<>();
        for (Appuntamento a : appts) {
            if (a.getData().equals(cellDate)) {
                list.add(a);
            }
        }

        for (int i = 0; i < MAX_APPTS_PER_CELL; i++) {
            String content = "";
            if (i < list.size()) {
                content = truncate(list.get(i).getOraInizio() + " " + list.get(i).getDescrizione(), CELL_WIDTH);
            }
            lines[i + 1] = padRight(content, CELL_WIDTH);
        }
        return lines;
    }

    private static void TerminalCalendarHeader(YearMonth ym) {
        System.out.printf("     %s %d%n", ym.getMonth(), ym.getYear());
        String[] weekDays = { "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun" };

        for (String wd : weekDays) {
            System.out.print(center(wd, CELL_WIDTH));
        }
        System.out.println();
        System.out.println();
    }

    // Utils
    private static String repeat(String s, int count) {
        return s.repeat(count);
    }

    private static String padRight(String s, int width) {
        if (s.length() >= width)
            return s.substring(0, width);
        return s + " ".repeat(width - s.length());
    }

    private static String center(String s, int width) {
        if (s.length() >= width)
            return s.substring(0, width);
        int left = (width - s.length()) / 2;
        int right = width - s.length() - left;
        return " ".repeat(left) + s + " ".repeat(right);
    }

    private static String truncate(String s, int width) {
        if (s.length() <= width)
            return s;
        if (width <= 3)
            return s.substring(0, width);
        return s.substring(0, width - 3) + "...";
    }
}
