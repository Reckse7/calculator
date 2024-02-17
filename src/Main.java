import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Input:");
        String input = scan.nextLine();
        System.out.println("Output:\n" + calc(input));
    }

    public static String calc(String input) {
        String[] exp = Calculator.getExpression(input);
        boolean isRoman = Calculator.checkRoman(exp[0], exp[2]);
        int result = Calculator.calculate(isRoman, exp);
        return Calculator.toString(isRoman, result);
    }
}

class Calculator {

    private final static String[] romanTens = {"X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC", "C"};
    private final static String[] romanUnits = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
    private final static String[] operators = {"-", "*", "/", "+"};

    public static String[] getExpression(String input) {
        String operator = "";
        String[] strings = input.split("[+\\-*/]", 2);
        for (String op : operators) {
            if (input.contains(op)) {
                operator = op;
                break;
            }
        }
        if (operator.isEmpty()) {
            throw new RuntimeException("Недопустимое выражение");
        }
        return new String[] {strings[0].trim(), operator, strings[1].trim()};
    }

    public static boolean checkRoman(String... args) {
        for (String arg : args) {
            boolean isRoman = false;
            for (String romanUnit : romanUnits) {
                if (arg.equalsIgnoreCase(romanUnit)) {
                    isRoman = true;
                    break;
                }
            }
            if (!isRoman) {
                return false;
            }
        }
        return true;
    }

    public static int calculate(boolean isRoman, String[] expression) {
        if (expression.length != 3) {
            throw new RuntimeException();
        }
        int num1 = convert(expression[0], isRoman);
        int num2 = convert(expression[2], isRoman);
        return switch (expression[1]) {
            case "+" -> num1 + num2;
            case "-" -> num1 - num2;
            case "*" -> num1 * num2;
            case "/" -> num1 / num2;
            default -> throw new RuntimeException("Недопустимый оператор");
        };
    }

    private static int convert(String arg, boolean isRoman) {
        return isRoman ? convertToRoman(arg) : convertToArabic(arg);
    }

    private static int convertToRoman(String arg) {
        for (int k = 0; k < romanUnits.length; k++) {
            if (arg.equalsIgnoreCase(romanUnits[k])) {
                return k + 1;
            }
        }
        throw new RuntimeException("Ошибка конвертации");
    }

    private static int convertToArabic(String arg) {
        int i = Integer.parseInt(arg);
        if (i < 1 || i > 10) {
            throw new RuntimeException("Допустимы числа от 1 до 10");
        }
        return i;
    }

    public static String toString(boolean isRoman, int arg) {
        return isRoman ? toRomanString(arg) : Integer.toString(arg);
    }

    private static String toRomanString(int arg) {
        int multiplier;
        StringBuilder result = new StringBuilder();
        if (arg < 1) {
            throw new RuntimeException("Результат должен быть больше нуля");
        }
        multiplier = arg / 10;
        if (multiplier > 0) {
            result.append(romanTens[multiplier - 1]);
        }
        multiplier = arg % 10;
        if (multiplier > 0) {
            result.append(romanUnits[multiplier - 1]);
        }
        return result.toString();
    }
}