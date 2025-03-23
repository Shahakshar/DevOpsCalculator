package org.example.calculatorbackend.services;

import org.example.calculatorbackend.models.CalculatorResponse;
import org.example.calculatorbackend.services.interfaces.CalculatorFunctionImplementation;
import org.springframework.stereotype.Service;

import java.util.Stack;

@Service
public class CalculatorServices implements CalculatorFunctionImplementation {
    @Override
    public CalculatorResponse calculate(String s) {
        String error = null;
        double result = 0;
        try {
            s = s.replaceAll("\\s", "");
            result = evaluateExpression(s);
        } catch (Exception e) {
            error = "Error: " + e.getMessage();
        }
        return new CalculatorResponse(result, error, error == null ? "200" : "400");
    }

    private double evaluateExpression(String s) {
        if (s.matches(".*(sin|cos|tan|sqrt|loge|log10|log2).*")) {
            return evaluateFunction(s);
        }
        return evaluateBasic(s);
    }

    private double evaluateBasic(String s) {
        Stack<Double> numbers = new Stack<>();
        Stack<Character> operators = new Stack<>();

        int i = 0;
        while (i < s.length()) {
            if (Character.isDigit(s.charAt(i)) || s.charAt(i) == '.') {
                StringBuilder sb = new StringBuilder();
                while (i < s.length() && (Character.isDigit(s.charAt(i)) || s.charAt(i) == '.')) {
                    sb.append(s.charAt(i++));
                }
                numbers.push(Double.parseDouble(sb.toString()));
                continue;
            }

            if (s.charAt(i) == '^') {
                i++;
                double base = numbers.pop();
                double exponent = 0;
                while (i < s.length() && (Character.isDigit(s.charAt(i)) || s.charAt(i) == '.')) {
                    exponent = exponent * 10 + Character.getNumericValue(s.charAt(i++));
                }
                numbers.push(Math.pow(base, exponent));
                continue;
            }

            while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(s.charAt(i))) {
                numbers.push(applyOp(operators.pop(), numbers.pop(), numbers.pop()));
            }

            operators.push(s.charAt(i));
            i++;
        }

        while (!operators.isEmpty()) {
            numbers.push(applyOp(operators.pop(), numbers.pop(), numbers.pop()));
        }

        return numbers.pop();
    }

    private double evaluateFunction(String s) {
        if (s.startsWith("sqrt(")) return safeSqrt(parseInsideParentheses(s));
        if (s.startsWith("sin(")) return Math.sin(Math.toRadians(parseInsideParentheses(s)));
        if (s.startsWith("cos(")) return Math.cos(Math.toRadians(parseInsideParentheses(s)));
        if (s.startsWith("tan(")) return safeTan(parseInsideParentheses(s));
        if (s.startsWith("loge(")) return safeLog(parseInsideParentheses(s));
        if (s.startsWith("log10(")) return safeLog10(parseInsideParentheses(s));
        if (s.startsWith("log2(")) return safeLog2(parseInsideParentheses(s));
        return 0;
    }

    private double safeSqrt(double value) {
        if (value < 0) {
            throw new ArithmeticException("Square root of negative number");
        }
        return Math.sqrt(value);
    }

    private double safeTan(double value) {
        if (value % 90 == 0 && value % 180 != 0) {
            return Double.NaN; // Undefined for 90, 270, etc.
        }
        return Math.tan(Math.toRadians(value));
    }

    private double safeLog(double value) {
        if (value <= 0) {
            throw new ArithmeticException("Logarithm undefined for non-positive values");
        }
        return Math.log(value);
    }

    private double safeLog10(double value) {
        if (value <= 0) {
            throw new ArithmeticException("Logarithm undefined for non-positive values");
        }
        return Math.log10(value);
    }

    private double safeLog2(double value) {
        if (value <= 0) {
            throw new ArithmeticException("Logarithm undefined for non-positive values");
        }
        return Math.log(value) / Math.log(2);
    }

    private double parseInsideParentheses(String s) {
        return Double.parseDouble(s.substring(s.indexOf('(') + 1, s.indexOf(')')));
    }

    private int precedence(char op) {
        if (op == '^') return 3;
        if (op == '*' || op == '/' || op == '%') return 2;
        if (op == '+' || op == '-') return 1;
        return 0;
    }

    private double applyOp(char op, double b, double a) {
        return switch (op) {
            case '+' -> a + b;
            case '-' -> a - b;
            case '*' -> a * b;
            case '/' -> {
                if (b == 0) {
                    yield Double.NaN;
                }
                yield a / b;
            }
            case '%' -> {
                if (b == 0) {
                    yield Double.NaN;
                }
                yield a % b;
            }
            case '^' -> Math.pow(a, b);
            default -> // Handle power operation
                    0;
        };
    }
}
