package org.example.calculatorbackend.controllers;

import org.example.calculatorbackend.models.CalculatorRequest;
import org.example.calculatorbackend.models.CalculatorResponse;
import org.example.calculatorbackend.services.CalculatorServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/v1")
@RestController
public class CalculatorController {

    private final CalculatorServices calculatorServices;

    @Autowired
    public CalculatorController(CalculatorServices calculatorServices) {
        this.calculatorServices = calculatorServices;
    }

    @PostMapping("/calculator/operations")
    public ResponseEntity<CalculatorResponse> calculate(@RequestBody CalculatorRequest calculatorRequest) {
        String operation = calculatorRequest.getOperation();
        return ResponseEntity.ok().body(
                calculatorServices.calculate(operation)
        );
    }

}
