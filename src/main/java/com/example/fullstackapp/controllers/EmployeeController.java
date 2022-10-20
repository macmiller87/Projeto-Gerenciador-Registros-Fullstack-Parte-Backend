package com.example.fullstackapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.fullstackapp.repository.EmployeeRepository;
import com.example.fullstackapp.service.SequenceGeneratorService;
import com.example.fullstackapp.models.Employee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.example.fullstackapp.exceptions.ResourceNotFoundException;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class EmployeeController {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;
	
	@GetMapping("/employees")
	public List<Employee> getAllEmployees() {
		
		return employeeRepository.findAll();
		
	}
	
	@GetMapping("/employee/{id}")
	public ResponseEntity<Employee> getEmployee(@PathVariable(value = "id") Long employeeId)
		
		throws ResourceNotFoundException {
			
			Employee employee = employeeRepository.findById(employeeId)
					.orElseThrow(() -> new ResourceNotFoundException("Colaborador não encontrado: " + employeeId));
			
			return  ResponseEntity.ok().body(employee);
		
	}
	
	@PostMapping("/employee")
	public Employee createEmployee(@Valid @RequestBody Employee employee) {
		
		employee.setId(sequenceGeneratorService.generateSequence(Employee.SEQUENCE_NAME));
		return employeeRepository.save(employee);
		
	}
	
	@PutMapping("/employee/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Long employeeId, @Valid @RequestBody Employee employeeDetails) throws ResourceNotFoundException {
		
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Colaborador não encontrado: " + employeeId));
		
		employee.setName(employeeDetails.getName());
		employee.setDepartment(employeeDetails.getDepartment());
		employee.setHiringDate(employeeDetails.getHiringDate());
		
		final Employee updatEmployee = employeeRepository.save(employee);
		
		return ResponseEntity.ok(updatEmployee);
		
	}
	
	@DeleteMapping("/employee/{id}")
	public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeId)
		
		throws ResourceNotFoundException {
			
			Employee employee = employeeRepository.findById(employeeId)
					.orElseThrow(() -> new ResourceNotFoundException("Colaborador não encontrado: " + employeeId));
			
			employeeRepository.delete(employee);
			Map<String, Boolean> response = new HashMap<>();
			response.put("excluido", Boolean.TRUE);
			
			return response;
		
	}
	
}
