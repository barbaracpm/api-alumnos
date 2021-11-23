package com.springboot.apirest.alumnos.controllers;

import java.util.HashMap;

import javax.persistence.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

	@GetMapping("/alumnos/{id}")
	public ResponseEntity<?> read(@PathVariable Long id) {
		Alumno alumno = null;
		HashMap<String,Object> response = new HashMap<>();

		try {
			alumno = alumnoService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<HashMap<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if(alumno == null) {
			response.put("mensaje", "El alumno ".concat(id.toString().concat(" no existe en la base de datos.")));
			return new ResponseEntity<HashMap<String,Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Alumno>(alumno, HttpStatus.OK);
	}

	@Override
	@PostMapping("/alumnos")
	public ResponseEntity<?> create(@RequestBody Alumno alumno) {
		HashMap<String,Object> response = new HashMap<>();

		try {
			alumnoService.save(alumno);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al insertar los datos en la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<HashMap<String,Object>>(response, HttpStatus.BAD_REQUEST);
		}

		response.put("mensaje", "Alumno creado con éxito.");
		response.put("alumno", alumno);
		return new ResponseEntity<HashMap<String,Object>>(response, HttpStatus.CREATED);
	}

	@Override
	@PutMapping("/alumnos/{id}")
	public ResponseEntity<?> update(@RequestBody Alumno alumno, @PathVariable Long id) {
		HashMap<String,Object> response = new HashMap<>();
		
		Alumno alumnoUpdated = alumnoService.findById(id);
		if (alumnoUpdated == null) {
			response.put("mensaje", "El alumno ".concat(id.toString().concat(" no existe en la base de datos.")));
			return new ResponseEntity<HashMap<String,Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			alumnoUpdated.setNombre(alumno.getNombre());
			alumnoUpdated.setApellido(alumno.getApellido());
			alumnoUpdated.setEmail(alumno.getEmail());
			alumnoUpdated.setTelefono(alumno.getTelefono());
			alumnoUpdated.setDireccion(alumno.getDireccion());
			alumnoUpdated.setCodigoPostal(alumno.getCodigoPostal());
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el alumno en base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<HashMap<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		alumnoService.save(alumnoUpdated);

		response.put("mensaje", "El alumno se ha actualizado con éxito.");
		response.put("alumno", alumnoUpdated);
		return new ResponseEntity<HashMap<String,Object>>(response, HttpStatus.OK);
	}

	@Override
	@DeleteMapping("/alumnos/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		HashMap<String,Object> response = new HashMap<>();

		if (alumnoService.findById(id) == null) {
			response.put("mensaje", "El alumno ".concat(id.toString().concat(" no existe en la base de datos.")));
			return new ResponseEntity<HashMap<String,Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			alumnoService.deleteById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el alumno en la base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<HashMap<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El alumno se ha eliminado con éxito.");
		return new ResponseEntity<HashMap<String,Object>>(response, HttpStatus.NO_CONTENT);
	}

}
