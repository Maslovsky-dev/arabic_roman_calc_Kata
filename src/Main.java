/**
 * @author Valerii Maslovskii
 */

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter an expression " + " ");
        String input = in.nextLine(); input = input.trim(); //entering string
        String answerString = calc(input);
        System.out.println("Result of evaluation = "+answerString);
    }
    public static String calc (String input) {
        ArrayList parts = new ArrayList();
        int answerInt =0;
        String answerString="";
        try {
            validSymbols(input);
        } catch (Exception e) {
            System.out.println("You entered an invalid character, arabic and aoman numerals from 1 to 10 inclusive, and operators + - / * are allowed");
            System.exit(0);
        }
        try {
            validMinus(input);
        } catch (Exception e) {
            System.out.println("You entered a negative number");
            System.exit(0);
        }
        try {
            parts = Parts(input);
        } catch (Exception e) {
            System.out.println("The format of the mathematical operation does not satisfy the task - two operands and one operator (+, -, /, *)");
            System.exit(0);
        }
        char operator = parts.get(2).toString().toCharArray()[0];
        try {
            int numbersType = validExpType(parts);
            if (numbersType == 1){  //Arabic numeral system
                int num1 = Integer.parseInt (parts.get(0).toString()); int num2 = Integer.parseInt (parts.get(1).toString());
                if (num1>10|num2>10){
                    System.out.println("Error: Entered number is greater than 10"); System.exit(0);
                }
                answerInt = evaluateExp(num1,num2,operator); answerString=Integer.toString(answerInt);
            }
            else if (numbersType==2) { //Roman numeral system
                int num1=romanToNum(parts.get(0).toString()); int num2=romanToNum(parts.get(1).toString());
                answerInt = evaluateExp(num1,num2,operator);
                if (answerInt <1) {
                    System.out.println("Error: There are no negative numbers in the roman system and 0");
                    System.exit(0);}
                answerString = numToRoman(answerInt);
            }
        } catch (Exception e) {
            System.out.println("Error: Mixed data or invalid input (only integers from 1 to 10 inclusive)");
            System.exit(0);
        }
        return answerString;
    }
    public static void validSymbols (String input) throws Exception { // Checks a string for forbidden characters
        String regex = ".*[^0-9\\+\\-\\*\\/IVX\\s].*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) throw new Exception();
    }
    public static void validMinus (String input) throws Exception { // Checks if a string has a negative numbers
        if (input.charAt(0)=='-') throw new Exception();
    }
    public static ArrayList Parts (String input) throws Exception { // Checks a string for format matching, highlights numbers and operator
        int inputLength = input.length();
        int k = 0;
        ArrayList entryIndexArray = new ArrayList();
        String[] parts = new String[0];
        for (int i = 0; i < inputLength; i++) {
            char a = input.charAt(i);
            if ((a == '-' | a == '+' | a == '/' | a == '*')&(i==0|i==inputLength-1)) throw new Exception();
            else if (a == '-' | a == '+' | a == '/' | a == '*') {
                k = k + 1;
                entryIndexArray.add(k-1, i);
            }
        }
        if (k>1|k==0) throw new Exception();
        for (int j = 0; j < inputLength; j++) {
            parts = input.split("[\\+\\*\\/\\-]");
        }
        parts[0]=parts[0].trim(); parts[1]=parts[1].trim();
        ArrayList partsFull = new ArrayList();
        partsFull.add(parts[0].trim()); partsFull.add(parts[1].trim()); partsFull.add(input.charAt((Integer) entryIndexArray.get(0)));
        return partsFull;
    }
    public static int validExpType (ArrayList args) throws Exception { // Specifies the input data type (roman or arabic)
        int numType =0;
        String regexArab = "^[123456789][0]{0,1}$";
        String regexRoman = "^(I|II|III|IV|V|VI|VII|VIII|IX|X)$";
        Pattern patternArab = Pattern.compile(regexArab);
        Pattern patternRoman = Pattern.compile(regexRoman);
        Matcher matcherArab1 = patternArab.matcher(args.get(0).toString()); boolean boolArab1 = matcherArab1.matches();
        Matcher matcherArab2 = patternArab.matcher(args.get(1).toString()); boolean boolArab2 = matcherArab2.matches();
        Matcher matcherRoman1 = patternRoman.matcher(args.get(0).toString()); boolean boolRoman1 = matcherRoman1.matches();
        Matcher matcherRoman2 = patternRoman.matcher(args.get(1).toString()); boolean boolRoman2 = matcherRoman2.matches();
        if (boolArab1 & boolArab2){
            numType =1;}
        else if (boolRoman1 & boolRoman2) {
            numType =2;}
        else if (boolArab1==boolRoman2|boolArab2==boolRoman1) throw new Exception();
        else if (boolArab1==boolArab2==boolRoman1==boolRoman2) throw new Exception();
        return numType;
    }
    public static int evaluateExp (int num1, int num2, char operator) throws Exception { // Evaluate expression
        int answer;
        if (operator=='+'){answer = num1+num2;}
        else if (operator=='-'){answer = num1-num2;}
        else if (operator=='*'){answer = num1*num2;}
        else if (operator=='/'){answer = num1/num2;}
        else {answer=0; throw new Exception();}
        return answer;
    }
    public static int romanToNum (String roman) throws Exception { // Convert roman to integer
        int convResult;
        if (roman.equals("I")){convResult=1;}
        else if (roman.equals("II")){convResult=2;}
        else if (roman.equals("III")){convResult=3;}
        else if (roman.equals("IV")){convResult=4;}
        else if (roman.equals("V")){convResult=5;}
        else if (roman.equals("VI")){convResult=6;}
        else if (roman.equals("VII")){convResult=7;}
        else if (roman.equals("VIII")){convResult=8;}
        else if (roman.equals("IX")){convResult=9;}
        else if (roman.equals("X")){convResult=10;}
        else {convResult=0; throw new Exception();}
        return convResult;
    }
    public static String numToRoman (int num) throws Exception { // Convert integer to roman
        String convResult="";
        int units = num%10;
        int tens = num/10;
        switch (tens) {
            case 1: convResult = "X"; break;
            case 2: convResult = "XX"; break;
            case 3: convResult = "XXX"; break;
            case 4: convResult = "XL"; break;
            case 5: convResult = "L"; break;
            case 6: convResult = "LX"; break;
            case 7: convResult = "LXX"; break;
            case 8: convResult = "LXXX"; break;
            case 9: convResult = "XC"; break;
            case 10: convResult = "C"; break;
        }
        switch (units) {
            case 1: convResult = convResult + "I"; break;
            case 2: convResult = convResult + "II"; break;
            case 3: convResult = convResult + "III"; break;
            case 4: convResult = convResult + "IV"; break;
            case 5: convResult = convResult + "V"; break;
            case 6: convResult = convResult + "VI"; break;
            case 7: convResult = convResult + "VII"; break;
            case 8: convResult = convResult + "VIII"; break;
            case 9: convResult = convResult + "IX"; break;
        }
        return convResult;
    }
}