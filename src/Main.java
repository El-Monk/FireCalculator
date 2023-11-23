import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    static double maxPercent;

    public static void main(String[] args) {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Введите дату:");
            int currentYear = Integer.parseInt(bufferedReader.readLine());

            if (currentYear < 2002) {
                throw new Exception();
            }

            for (double i = 0.5; i < 100; i += 0.5) {
                int year = currentYear;

                double capital = Costants.MOEX_RATE[year - 2002];             // капитал

                double expenses = capital * i / 100;        // расходы (изъятие)
                capital = capital - expenses;

                if (year > 2002) {
                    capital = capital * Costants.MOEX_RATE[year - 2002] / Costants.MOEX_RATE[year - 2003];
                }
                expenses = expenses * (1 + Costants.INFLATION_RATE[year - 2002]/100);  //индекация расходов (изъятия)
                year++;


                while (year <= 2022) {

                    capital = capital - expenses; // остаток капитала после изъятия

                    capital = capital * Costants.MOEX_RATE[year - 2002] / Costants.MOEX_RATE[year - 2003]; //"прирост" капитала относительно конца и начала года

                    expenses = expenses * (1 + Costants.INFLATION_RATE[year - 2002]/100);       //индексация расходов

                    year++;
                }

                if (capital - expenses < 0) {
                    maxPercent = i;

                    break;
                }
            }

            System.out.println(maxPercent);

        } catch (Exception e) {
            System.out.println("xaxa... не правильный год)");
            e.printStackTrace();
        }
    }
}