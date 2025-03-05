package com.example.employee_app;

import com.example.employee_app.model.Employee;
import com.example.employee_app.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeService mockEmployeeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCalcularSalarioAnual() {
        double salarioMensual = 3000.0;
        double salarioAnualEsperado = 36000.0;
        double salarioAnual = employeeService.calculateAnnualSalary(salarioMensual);
        assertEquals(salarioAnualEsperado, salarioAnual, "El salario anual no es correcto");
    }

    @Test
    public void testGetEmployeeById() {
        int employeeId = 1;
        Employee employeeMock = new Employee(employeeId, "John Doe", 3000.0, 25, "");
        when(mockEmployeeService.getEmployeeById(employeeId)).thenReturn(Optional.of(employeeMock));
        Optional<Employee> employee = mockEmployeeService.getEmployeeById(employeeId);
        assertEquals(employeeMock, employee.orElse(null), "El empleado retornado no es el esperado");
    }
}

