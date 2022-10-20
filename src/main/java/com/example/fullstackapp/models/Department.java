package com.example.fullstackapp.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Department")
public class Department {
	
	@Transient
	public static final String SEQUENCE_NAME = "departments_sequence";
	
	@Id
	private long id;
	
	@NotBlank
	@Size(max = 100)
	@Indexed(unique = true)
	
	private String nameDepartment;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNameDepartment() {
		return nameDepartment;
	}

	public void setNameDepartment(String nameDepartment) {
		this.nameDepartment = nameDepartment;
	}
	
	public Department() {}

	public Department(long id, @NotBlank @Size(max = 100) String nameDepartment) {
		super();
		this.id = id;
		this.nameDepartment = nameDepartment;
	}

	@Override
	public String toString() {
		return "Department [id=" + id + ", nameDepartment=" + nameDepartment + "]";
	}

}
