package com.example.fullstackapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.fullstackapp.models.Employee;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, Long> {}
