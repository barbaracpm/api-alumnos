package com.springboot.apirest.alumnos.controllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.apirest.alumnos.models.entity.Alumno;
import com.springboot.apirest.alumnos.services.AlumnoService;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
public class AlumnoRestController implements ControllerDoc {

	@Autowired
	private AlumnoService alumnoService;

	@Override
	@GetMapping("/alumnos")
	public ResponseEntity<?> readAll() {
		HashMap<String,Object> response = new HashMap<>();
		List<Alumno> list = new LinkedList<>();
		try {
			list = alumnoService.findAll();
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en base de datos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<HashMap<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("alumnos", list);
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
			if (alumno.getNombre() != null && !alumno.getNombre().isEmpty()) {
				alumnoUpdated.setNombre(alumno.getNombre());
			}
			if (alumno.getApellido() != null && !alumno.getApellido().isEmpty()) {
				alumnoUpdated.setApellido(alumno.getApellido());
			}
			if (alumno.getEmail() != null && !alumno.getEmail().isEmpty()) {
				alumnoUpdated.setEmail(alumno.getEmail());
			}
			String telefono = Integer.toString(alumno.getTelefono());
			if (telefono.length() == 9) {
				alumnoUpdated.setTelefono(alumno.getTelefono());
			} else {
				response.put("mensaje", "Error al introducir el número de teléfono.");
				return new ResponseEntity<HashMap<String,Object>>(response, HttpStatus.BAD_REQUEST);
			}
			if (alumno.getDireccion() != null && !alumno.getDireccion().isEmpty()) {
				alumnoUpdated.setDireccion(alumno.getDireccion());
			}
			String codigoPostal = Integer.toString(alumno.getCodigoPostal());
			if (codigoPostal.length() == 5) {
				alumnoUpdated.setCodigoPostal(alumno.getCodigoPostal());
			} else {
				response.put("mensaje", "Error al introducir el código postal.");
				return new ResponseEntity<HashMap<String,Object>>(response, HttpStatus.BAD_REQUEST);
			}
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

	@Override
	@PostMapping("/alumnos/upload")
	public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") Long id) {
		HashMap<String,Object> response = new HashMap<>();
		Alumno alumno = alumnoService.findById(id);

		if (alumno == null) {
			response.put("mensaje", "El alumno ".concat(id.toString().concat(" no existe en la base de datos.")));
			return new ResponseEntity<HashMap<String,Object>>(response, HttpStatus.NOT_FOUND);
		}

		if (archivo != null && !archivo.isEmpty()) {
			String name = UUID.randomUUID().toString()+"_"+archivo.getOriginalFilename().replace(" ", "");
			Path path = Paths.get("images").resolve(name).toAbsolutePath();

			try {
				Files.copy(archivo.getInputStream(), path);

				String oldName = alumno.getImagen();
				if (oldName != null && oldName.length() > 0) {
					Path oldPath = Paths.get("images").resolve(oldName).toAbsolutePath();
					File oldImage = oldPath.toFile();

					if (oldImage.exists() && oldImage.canRead()) {
						oldImage.delete();
					}
				}

				alumno.setImagen(name);
				alumnoService.save(alumno);
			} catch (IOException e) {
				response.put("mensaje", "Error al subir la imagen del alumno.");
				response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
				return new ResponseEntity<HashMap<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			response.put("alumno", alumno);
			response.put("mensaje", "Has subido correctamente la imagen: ".concat(name));
		}
		return new ResponseEntity<HashMap<String,Object>>(response, HttpStatus.CREATED);
	}

	@Override
	@GetMapping("/images/{name:.+}")
	public ResponseEntity<?> getImage(@PathVariable String name) {
		HashMap<String,Object> response = new HashMap<>();
		Path path = Paths.get("images").resolve(name).toAbsolutePath();
		Resource recurso = null;

		try {
			recurso = new UrlResource(path.toUri());
		} catch (MalformedURLException e) {
			response.put("mensaje", "Error al obtener la imagen. Revisa la URL introducida.");
			response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
			return new ResponseEntity<HashMap<String,Object>>(response, HttpStatus.BAD_REQUEST);
		}

		if (!recurso.exists() && !recurso.isReadable()) {
			throw new RuntimeException("No se puede cargar la imagen ".concat(name));
		}

		HttpHeaders cabecera = new HttpHeaders();
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\""+recurso.getFilename()+"\"");
		return new ResponseEntity<Resource>(recurso, cabecera, HttpStatus.OK);
	}

}
