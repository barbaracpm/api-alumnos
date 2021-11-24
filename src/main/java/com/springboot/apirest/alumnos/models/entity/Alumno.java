package com.springboot.apirest.alumnos.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "alumnos")
public class Alumno implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(notes = "ID del alumno", name = "id")
	private long id;
	
	@Column(nullable = false)
	@ApiModelProperty(notes = "Nombre del alumno", name = "nombre", required = true, example = "Lore")
	private String nombre;
	
	@ApiModelProperty(notes = "Apellido del alumno", name = "apellido", example = "Ipsum")
	private String apellido;
	
	@Column(nullable = false, unique = true)
	@ApiModelProperty(notes = "DNI del alumno", name = "dni", required = true, example = "850488194J")
	private String dni;
	
	@Column(nullable = false, unique = true)
	@ApiModelProperty(notes = "Correo electrónico del alumno", name = "email", required = true, example = "lore@ipsum.com")
	private String email;
	
	@ApiModelProperty(notes = "Teléfono de contacto", name = "telefono", example = "666339922")
	private int telefono;
	
	@ApiModelProperty(notes = "Dirección", name = "direccion", example = "Calle Capitán")
	private String direccion;
	
	@ApiModelProperty(notes = "Código postal", name = "codigoPostal", example = "28300")
	private int codigoPostal;
	
	@ApiModelProperty(notes = "Foto de perfil del alumno", name = "imagen")
	private String imagen;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getTelefono() {
		return telefono;
	}
	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public int getCodigoPostal() {
		return codigoPostal;
	}
	public void setCodigoPostal(int codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	public String getImagen() {
		return imagen;
	}
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

}
