package com.springboot.apirest.alumnos.services;

import java.util.List;

import com.springboot.apirest.alumnos.models.entity.Alumno;

public interface AlumnoService {

	public List<Alumno> findAll();

	public Alumno findById(Long id);

	public Alumno save(Alumno alumno);

	public void deleteById(Long id);

}
