import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String input, output;
        Scanner scan = new Scanner(System.in);
        System.out.println("Input:");
        input = scan.nextLine();
        output = calc(input);
        System.out.println("Output:\n" + output);

    }

    public static String calc(String input) {

        String [] romanTens = {"X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC", "C"};
        String [] romanUnits = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
        int i = -1, arg1 = 0, arg2 = 0, k;
        String resLine = new String();
        String[] strings = new String[2];
        boolean checkRoman = false;

        char[] operator = {'+', '-', '*', '/'};
        for (char element: operator) {
            i = input.indexOf(element);
            if(i>=0) {
                String str = new StringBuilder().append("\\").append(element).toString();
                strings = input.split(str, 2);
                break;
            }
        }
        if( i < 0)
            try {
                throw new IOException();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        String num1 = strings[0];
        String num2 = strings[1];
        num1 = num1.trim();
        num2 = num2.trim();
        for ( k=0; k<romanUnits.length; k++) {
            checkRoman = num1.equalsIgnoreCase(romanUnits[k]);
            if(checkRoman) {
                arg1 = k+1;
                break;
            }
        }
        if(checkRoman)
            for (k=0; k<romanUnits.length; k++) {
                checkRoman = num2.equalsIgnoreCase(romanUnits[k]);
                if(checkRoman) {
                    arg2 = k+1;
                    break;
                }
            }
        if(!checkRoman) {
            try {
                Integer int1 = Integer.valueOf(num1);
                arg1 = int1;
            } catch (NumberFormatException e) {
                throw new RuntimeException(e);
            }
            try {
                Integer int2 = Integer.valueOf(num2);
                arg2 = int2;
            } catch (NumberFormatException e) {
                throw new RuntimeException(e);
            }

            if(arg1 < 1 || arg1 > 10 || arg2 < 1 || arg2 > 10)
                try {
                    throw new IOException();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
        }

        int result = 0;
        switch(input.charAt(i)) {
            case '+':
                result = arg1 + arg2;
                break;
            case '-':
                result = arg1 - arg2;
                break;
            case '*':
                result = arg1 * arg2;
                break;
            case'/':
                result = arg1 / arg2;
                break;
        }

        if(checkRoman)
        {
            if(result < 1)
                try {
                    throw new IOException();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            int multiplier;
            multiplier = result/10;
            if(multiplier > 0)
                resLine += romanTens[multiplier -1];
            multiplier = result % 10;
            if(multiplier > 0)
                resLine += romanUnits[multiplier -1];
            return resLine;
        }

        resLine = Integer.toString(result);
        return resLine;
    }

}