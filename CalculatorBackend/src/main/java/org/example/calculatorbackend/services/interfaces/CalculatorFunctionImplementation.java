package org.example.calculatorbackend.services.interfaces;

import org.example.calculatorbackend.models.CalculatorResponse;
import org.springframework.stereotype.Component;

@Component
public interface CalculatorFunctionImplementation {
    CalculatorResponse calculate(String s);
}
