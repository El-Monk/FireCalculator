import java.io.BufferedReader;
import java.io.InputStreamReader;

public class FireCalculator {
    static double maxPercent;

    public void solver() throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Введите дату:");
        int currentYear = Integer.parseInt(bufferedReader.readLine());

        if (currentYear < 2002) {
            throw new Exception("Введена некорректная дата");
        }
        for (double i = 0.5; i <= 100; i += 0.5) {
            definition(currentYear, i);
        }

        System.out.println(maxPercent);
        bufferedReader.close();
    }


    public static void definition(int year, double i) {
        int currentYear = year - 2002;

        double capital = Costants.MOEX_RATE[currentYear];
        double expenses = capital * i / 100;
        capital = remainingCapital(capital, expenses);
        expenses = indexationOfExpenses(expenses, currentYear + 1);

        if (currentYear > 0) {
            capital = capitalChange(capital, currentYear);
        }

        currentYear++;

        while (currentYear <= 19) {
            capital = remainingCapital(capital, expenses);
            expenses = indexationOfExpenses(expenses, currentYear + 1);
            capital = capitalChange(capital, currentYear);
            currentYear++;
        }

        if (capital - expenses > 0) {
            maxPercent = i;
        }
    }

    public static double remainingCapital(double capital, double expenses) { // остаток капитала после изъятия
        return capital - expenses;
    }

    public static double indexationOfExpenses(double expenses, int nextYear) { //индекация расходов (изъятия)
        double inflationRateNextYear = Costants.INFLATION_RATE[nextYear];
        return expenses * (1 + inflationRateNextYear / 100);
    }

    public static double capitalChange(double capital, int currentYear) { //"прирост" капитала
        double moexRateCurrentYear = Costants.MOEX_RATE[currentYear];
        double moexRateNextYear = Costants.MOEX_RATE[currentYear + 1];
        return capital * moexRateNextYear / moexRateCurrentYear;
    }
}
