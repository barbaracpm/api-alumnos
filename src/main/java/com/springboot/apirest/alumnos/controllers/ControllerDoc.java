package com.springboot.apirest.alumnos.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.apirest.alumnos.models.entity.Alumno;

import io.swagger.annotations.ApiOperation;

public interface ControllerDoc {

	@ApiOperation(value = "Lista todos los alumnos.")
	ResponseEntity<?> readAll();

	@ApiOperation(value = "Lista un alumno.")
	ResponseEntity<?> read(Long id);

	@ApiOperation(value = "Crea un alumno.")
	ResponseEntity<?> create(Alumno alumno);

	@ApiOperation(value = "Actualiza los datos de un alumno.")
	ResponseEntity<?> update(Alumno alumno, Long id);

	@ApiOperation(value = "Borra un alumno.")
	ResponseEntity<?> delete(Long id);

	@ApiOperation(value = "Carga una imagen.")
	ResponseEntity<?> upload(MultipartFile archivo, Long id);

	@ApiOperation(value = "Obtiene una imagen.")
	ResponseEntity<?> getImage(String name);
	
}
