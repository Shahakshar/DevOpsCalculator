package org.example.calculatorbackend.models;

public class CalculatorResponse {

    private double result;
    private String error;
    private String code;

    public CalculatorResponse(double result, String error, String code) {
        this.result = result;
        this.error = error;
        this.code = code;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
