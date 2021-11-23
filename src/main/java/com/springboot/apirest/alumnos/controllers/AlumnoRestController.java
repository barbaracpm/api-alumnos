package com.springboot.apirest.alumnos.controllers;

import java.util.HashMap;

import javax.persistence.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springboot.apirest.alumnos.models.entity.Alumno;
import com.springboot.apirest.alumnos.services.AlumnoService;

@Entity
@RequestMapping("/clase")
public class AlumnoRestController implements ControllerDoc {
	
	@Autowired
	private AlumnoService alumnoService;

	@Override
	@GetMapping("/alumnos")
	public ResponseEntity<?> readAll() {
		HashMap<String,Object> response = new HashMap<>();
		
		try {
			alumnoService.findAll();
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<HashMap<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<HashMap<String,Object>>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> read(Long id) {
		return null;
	}

	@Override
	public ResponseEntity<?> create(Alumno alumno) {
		return null;
	}

	@Override
	public ResponseEntity<?> update(Alumno alumno, Long id) {
		return null;
	}

	@Override
	public ResponseEntity<?> delete(Long id) {
		return null;
	}

}
