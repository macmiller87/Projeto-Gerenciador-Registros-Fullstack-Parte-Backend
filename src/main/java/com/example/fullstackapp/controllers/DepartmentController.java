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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;

import com.example.fullstackapp.exceptions.ResourceNotFoundException;
import com.example.fullstackapp.models.Department;
import com.example.fullstackapp.repository.DepartmentRepository;
import com.example.fullstackapp.service.SequenceGeneratorService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class DepartmentController {

	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;
	
	@GetMapping("/departments")
	public List<Department> getAllDepartments() {
		
		return departmentRepository.findAll();
		
	}
	
	@GetMapping("/department/{id}")
	public ResponseEntity<Department> getDepartment(@PathVariable(value = "id") Long departmentId)
		
		throws ResourceNotFoundException {
			
			Department department = departmentRepository.findById(departmentId)
					.orElseThrow(() -> new ResourceNotFoundException("Departamento não encontrado: " + departmentId));
			
			return  ResponseEntity.ok().body(department);
			
		}
	
	@PostMapping("/department")
	public Department CreateDepartment(@Valid @RequestBody Department department) {
		
		department.setId(sequenceGeneratorService.generateSequence(Department.SEQUENCE_NAME));
		return departmentRepository.save(department);
		
	}
	
	@PutMapping("/department/{id}")
	public ResponseEntity<Department> updateDepartment(@PathVariable(value = "id") Long departmentId, @Valid @RequestBody Department departmentDetails) throws ResourceNotFoundException {
		
		Department department = departmentRepository.findById(departmentId)
				.orElseThrow(() -> new ResourceNotFoundException("Departamento não encontrado: " + departmentId));
		
		department.setNameDepartment(departmentDetails.getNameDepartment());
		
		final Department updateDepartment = departmentRepository.save(department);
		
		return ResponseEntity.ok(updateDepartment);
		
	}
	
	@DeleteMapping("/department/{id}")
	public Map<String, Boolean> deleteDepartment(@PathVariable(value = "id") Long departmentId)
		
		throws ResourceNotFoundException {
		
		Department department = departmentRepository.findById(departmentId)
				.orElseThrow(() -> new ResourceNotFoundException("Departamento não encontrado: " + departmentId));
		
		departmentRepository.delete(department);
		Map<String, Boolean> response = new HashMap<>();
		response.put("excluido", Boolean.TRUE);
		
		return response;
		
	}
	
}
