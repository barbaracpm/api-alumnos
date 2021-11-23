package com.springboot.apirest.alumnos.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.springboot.apirest.alumnos.models.entity.Alumno;

public interface ControllerDoc {
	
	@GetMapping("/alumnos")
	public ResponseEntity<?> readAll();
	
	@GetMapping("/alumnos/{id}")
	public ResponseEntity<?> read(@PathVariable Long id);
	
	@PostMapping("/alumnos")
	public ResponseEntity<?> create(@RequestBody Alumno alumno);
	
	@PutMapping("/alumnos/{id}")
	public ResponseEntity<?> update(@RequestBody Alumno alumno, @PathVariable Long id);
	
	@DeleteMapping("/alumnos/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id);
	
}
