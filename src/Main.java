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

            for (double i = 0.5; i <= 100; i += 0.5) {
                definition(currentYear, i);
            }

            System.out.println(maxPercent);

        } catch (Exception e) {
            System.out.println("Введена неправильная дата");
        }
    }

    public static void definition(int year, double i) {
        double capital = Costants.MOEX_RATE[year - 2002];             // капитал
        double expenses = capital * i / 100;        // расходы (изъятие)
        capital = capital - expenses;
        expenses = expenses * (1 + Costants.INFLATION_RATE[year - 2001] / 100);  //индекация расходов (изъятия)
        if (year > 2002) {
            capital = capital * Costants.MOEX_RATE[year - 2001] / Costants.MOEX_RATE[year - 2002];
        }


        year++;

        while (year <= 2021) {
            capital = capital - expenses; // остаток капитала после изъятия
            expenses = expenses * (1 + Costants.INFLATION_RATE[year - 2001] / 100);       //индексация расходов
            capital = capital * Costants.MOEX_RATE[year - 2001] / Costants.MOEX_RATE[year - 2002]; //"прирост" капитала относительно конца и начала года

            year++;
        }
        System.out.println(i);
        System.out.println(capital);
        System.out.println(expenses);
        System.out.println();

        if (capital - expenses > 0) {
            maxPercent = i;
        }


    }
}