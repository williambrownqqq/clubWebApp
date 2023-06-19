import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Lab8 {
     private final static Double a =  0.0;
     private final static Double b = 10.0;
     private final static Double eps = 0.01;

     private static double[] kValues = null;
    private static List<Double> ard1 = null;
    private static List<Double> ard2 = null;
    private static double[] ard3 = null;
    private static double[] ard4 = null;
    private static double[] ard5 = null;



    public static double polynomialFunction(List<Double> argTheCoefficients, double xValue) {
        int polynomialLength = argTheCoefficients.size();
        double result = 0.0;
        for (int indexAndPower = 0; indexAndPower < polynomialLength; indexAndPower++) {
            result += argTheCoefficients.get(polynomialLength - indexAndPower - 1) * Math.pow(xValue, indexAndPower);
        }
        return result;
    }

     private static double[] intervalHalving(Double argAValue, Double argBValue, Double argEps, List<Double> argTheCoefficients){
         List<Double> allAValues = new ArrayList<>();
         List<Double> allBValues = new ArrayList<>();

         allAValues.add(argAValue);
         allBValues.add(argBValue);

         double aValue = argAValue;
         double bValue = argBValue;
         int kValue = 0;
         double internalLength = Math.abs(aValue - bValue);

         List<Double> allXValues = new ArrayList<>();
         allXValues.add((aValue + bValue) / 2);

         while (internalLength > argEps) {
             double funcMiddleValue = polynomialFunction(argTheCoefficients, allXValues.get(allXValues.size() - 1));

             double leftMiddleValue = aValue + internalLength / 4;
             double rightMiddleValue = bValue - internalLength / 4;
             double funcLeftMiddle = polynomialFunction(argTheCoefficients, leftMiddleValue);
             double funcRightMiddle = polynomialFunction(argTheCoefficients, rightMiddleValue);

             if (funcLeftMiddle < funcMiddleValue) {
                 allAValues.add(allAValues.get(allAValues.size() - 1));
                 allBValues.add(allXValues.get(allXValues.size() - 1));

                 bValue = allXValues.get(allXValues.size() - 1);
                 allXValues.add(leftMiddleValue);
             } else if (funcRightMiddle < funcMiddleValue) {
                 allAValues.add(allXValues.get(allXValues.size() - 1));
                 allBValues.add(allBValues.get(allBValues.size() - 1));

                 aValue = allXValues.get(allXValues.size() - 1);
                 allXValues.add(rightMiddleValue);
             } else {
                 allAValues.add(leftMiddleValue);
                 allBValues.add(rightMiddleValue);
                 aValue = leftMiddleValue;
                 bValue = rightMiddleValue;

                 allXValues.add(allXValues.get(allXValues.size() - 1));
             }

             internalLength = Math.abs(aValue - bValue);
             kValue++;
         }

         double[] result = new double[6];
         ard1 = Collections.singletonList(allXValues.get(allXValues.size() - 1));
         ard2 = Collections.singletonList(polynomialFunction(argTheCoefficients, allXValues.get(allXValues.size() - 1)));
         kValues = new double[]{kValue};

         double[] xValuesArray = new double[allXValues.size()];
         for (int i = 0; i < allXValues.size(); i++) {
             xValuesArray[i] = allXValues.get(i);
         }
         ard3 = xValuesArray;

         double[] aValuesArray = new double[allAValues.size()];
         for (int i = 0; i < allAValues.size(); i++) {
             aValuesArray[i] = allAValues.get(i);
         }
         ard4 = aValuesArray;

         double[] bValuesArray = new double[allBValues.size()];
         for (int i = 0; i < allBValues.size(); i++) {
             bValuesArray[i] = allBValues.get(i);
         }
         ard5 = bValuesArray;

         return result;
     }
    private static void pressEnterToContinue()
    {
        System.out.println("Press Enter key to continue...");
        try
        {
            System.in.read();
        }
        catch(Exception e)
        {}
    }
    public static void showTable(int argKValues, double[] argAllXValues, double[] argAllAValues, double[] argAllBValues) {
        System.out.println("-".repeat(123));
        System.out.printf("|  %2s  ", "k");
        System.out.printf("|  %15s  ", "Ak");
        System.out.printf("|  %15s  ", "Bk");
        System.out.printf("|  %15s  ", "Xk(c)");
        System.out.printf("|  %30s  ", "L2k = [Ak; Bk]");
        System.out.printf("|  %15s  ", "|L2k|");
        System.out.println("|");
        System.out.println("-".repeat(123));

        for (int oneRow = 0; oneRow < argKValues + 1; oneRow++) {
            System.out.printf("|  %2d  ", oneRow);
            System.out.printf("|  %15s  ", argAllAValues[oneRow]);
            System.out.printf("|  %15s  ", argAllBValues[oneRow]);
            System.out.printf("|  %15s  ", argAllXValues[oneRow]);
            System.out.printf("|  %30s  ", "[" + argAllAValues[oneRow] + "; " + argAllBValues[oneRow] + "]");
            System.out.printf("|  %15s  ", (argAllBValues[oneRow] - argAllAValues[oneRow]) / 2);
            System.out.println("|");
            System.out.println("-".repeat(123));
        }
    }
    public static void PressAnyKey() {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Press any key...");
        try {
            input.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        List<Double> coefficients = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);

        System.out.println("d1: "); // 1
        coefficients.add(scanner.nextDouble());
        System.out.println("d2: "); // -6
        coefficients.add(scanner.nextDouble());
        System.out.println("d3: "); // -15
        coefficients.add(scanner.nextDouble());
        System.out.println("d4: "); // 105
        coefficients.add(scanner.nextDouble());
        scanner.close();

        intervalHalving(a, b, eps, coefficients);
        System.out.println("Оптимальне значення х = " + ard1);
        System.out.println("Мінімальне значення функції f(x) = " + ard2);
        showTable(10,ard3, ard4, ard5);


        System.out.println("Exiting the program...");
    }
}
