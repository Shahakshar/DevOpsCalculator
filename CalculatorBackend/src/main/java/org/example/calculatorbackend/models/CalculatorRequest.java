package org.example.calculatorbackend.models;

public class CalculatorRequest {

    private final String operation;

    CalculatorRequest(String operation) {
        this.operation = operation;
    }

    public String getOperation() {
        return operation;
    }

}
