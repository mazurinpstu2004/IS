package lab1;

import java.util.Scanner;
import java.util.regex.Pattern;

public class PassSecurity {

    public static void main(String[] args) {
        PassSecurity sec = new PassSecurity();
        Scanner sc = new Scanner(System.in);
        System.out.print("Пароль: ");
        String password = sc.nextLine();
        System.out.print("Скорость перебора паролей в секунду: ");
        int s = sc.nextInt();
        System.out.print("Количество неправильных попыток: ");
        int m = sc.nextInt();
        System.out.print("Пауза после неправильных попыток: ");
        int v = sc.nextInt();
        int alphabetPower = sec.checkAlphabetPower(password);
        System.out.println("Мощность алфавита N = " + alphabetPower);
        int passwordLength = password.length();
        System.out.println("Длина пароля l = " + passwordLength);
        long combinationCount = sec.findCombinationCount(alphabetPower, passwordLength);
        System.out.println("Количество возможных комбинаций: " + combinationCount);
        long bruteForceTime = sec.findBruteForceTime(combinationCount, s, m, v);
        sec.formatBruteForceTime(bruteForceTime);
    }

    public int checkAlphabetPower(String pass) {
        int power = 0;
        boolean isNumber = Pattern.compile("[0-9]").matcher(pass).find();
        boolean isLowerLetter = Pattern.compile("[a-z]").matcher(pass).find();
        boolean isUpperLetter = Pattern.compile("[A-Z]").matcher(pass).find();
        boolean isSpecialSymbol = Pattern.compile("[\\W_]").matcher(pass).find();

        if (isNumber && !isLowerLetter && !isUpperLetter && !isSpecialSymbol) {
            power = 10;
        } else if (!isNumber && isLowerLetter && !isUpperLetter && !isSpecialSymbol) {
            power = 26;
        } else if (!isNumber && !isLowerLetter && isUpperLetter && !isSpecialSymbol) {
            power = 26;
        } else if (isNumber && isLowerLetter && !isUpperLetter && !isSpecialSymbol) {
            power = 36;
        } else if (isNumber && !isLowerLetter && isUpperLetter && !isSpecialSymbol) {
            power = 36;
        } else if (isNumber || isLowerLetter || isUpperLetter || isSpecialSymbol) {
            power = 95;
        }
        return power;
    }

    public long findCombinationCount(int power, int length) {
        return (long) Math.pow(power, length);
    }

    public long findBruteForceTime(long passwordCount, int s, int m, int v) {
        if (m == 0 || v == 0) {
            return passwordCount / s;
        } else {
            return (passwordCount / s) + ((passwordCount - 1) / m) * v;
        }
    }

    public void formatBruteForceTime(long time) {
        long seconds = time % 60;
        long minutes = (time / 60) % 60;
        long hours = (time / 3600) % 24;
        long days = (time / (3600 * 24)) % 30;
        long months = (time / (3600 * 24 * 30)) % 12;
        long years = time / (3600 * 24 * 365);
        System.out.print(years + " лет ");
        System.out.print(months + " месяцев ");
        System.out.print(days + " дней ");
        System.out.print(hours + " часов ");
        System.out.print(minutes + " минут ");
        System.out.print(seconds + " секунд ");
    }
}
