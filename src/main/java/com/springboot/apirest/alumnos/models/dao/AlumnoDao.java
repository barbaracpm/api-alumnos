package com.springboot.apirest.alumnos.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.springboot.apirest.alumnos.models.entity.Alumno;

public interface AlumnoDao extends CrudRepository<Alumno, Long>  {

}
