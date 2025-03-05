package com.example.employee_app.service;
import com.example.employee_app.model.Employee;
import com.example.employee_app.model.ApiResponse;
import com.example.employee_app.model.ApiIDResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final RestTemplate restTemplate;

    private static final String BASE_URL = "http://dummy.restapiexample.com/api/v1";

    @Autowired
    public EmployeeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    //Metodo para obtener todos los empleados
    public List<Employee> getAllEmployees() {
        CookieStore cookieStore = new BasicCookieStore();
        try (CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultCookieStore(cookieStore)
                .build()) {
            HttpGet request = new HttpGet(BASE_URL + "/employees");
            request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36"); // Headers requeridos para acceso a la API
            request.addHeader("Cookie", "humans_21909=1"); // Cookie requerida para acceso a la APIa
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                String json = EntityUtils.toString(response.getEntity());
                if (json.startsWith("<")) {
                    throw new RuntimeException("Demasiadas solicitudes");
                }
                ObjectMapper mapper = new ObjectMapper();
                ApiResponse apiResponse = mapper.readValue(json, ApiResponse.class);
                if (!"success".equals(apiResponse.getStatus())) {
                    throw new RuntimeException("La API devolvió un estado inesperado: " + apiResponse.getStatus());
                }
                return apiResponse.getData();
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener la lista de empleados: " + e.getMessage(), e);
        }
    }

    //Metodo para obtener un empleado por ID
    public Optional<Employee> getEmployeeById(int id) {
        CookieStore cookieStore = new BasicCookieStore();
        try (CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultCookieStore(cookieStore)
                .build()) {

            HttpGet request = new HttpGet(BASE_URL + "/employee/" + id);
            request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36"); // Headers requeridos para acceso a la API
            request.addHeader("Cookie", "humans_21909=1"); // Cookie requerida para acceso a la APIa

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                String json = EntityUtils.toString(response.getEntity());
                if (json.startsWith("<")) {
                    throw new RuntimeException("Demasiadas solicitudes");
                }
                ObjectMapper mapper = new ObjectMapper();
                ApiIDResponse apiResponse = mapper.readValue(json, ApiIDResponse.class);

                if (!"success".equals(apiResponse.getStatus())) {
                    throw new RuntimeException("La API devolvió un estado inesperado: " + apiResponse.getStatus());
                }
                return Optional.ofNullable(apiResponse.getData());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el empleado por ID: " + e.getMessage(), e);
        }
    }
    // Cálculo de salario anual
    public double calculateAnnualSalary(double monthlySalary) {
        return monthlySalary * 12;
    }
}
