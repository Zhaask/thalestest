package com.example.employee_app.controller;

import com.example.employee_app.model.Employee;
import com.example.employee_app.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

 //* Rellenar lista en vista
    @GetMapping("/")
    public String showEmployeeForm(@RequestParam(required = false) Integer id, Model model) {
        if (id != null) {
            try {
                Optional<Employee> employee = employeeService.getEmployeeById(id);
                if (employee.isPresent()) {
                    model.addAttribute("employee", employee.get());
                    double annualSalary = employeeService.calculateAnnualSalary(employee.get().getEmployeeSalary());
                    model.addAttribute("annualSalary", annualSalary);
                } else {
                    model.addAttribute("error", "Empleado no encontrado");
                }
            } catch (RuntimeException e) {
                model.addAttribute("error", e.getMessage());
            }
        } else {
            try {
                List<Employee> employees = employeeService.getAllEmployees();
                model.addAttribute("employees", employees);
                List<Double> annualSalaries = employees.stream()
                        .map(emp -> employeeService.calculateAnnualSalary(emp.getEmployeeSalary()))
                        .collect(Collectors.toList());
                model.addAttribute("annualSalaries", annualSalaries);

            } catch (RuntimeException e) {
                model.addAttribute("error", e.getMessage());
            }
        }
        return "index";
    }
}

