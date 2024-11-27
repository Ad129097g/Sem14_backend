package com.tecsup.controller;

import com.tecsup.exception.ResourceNotFoundException;
import com.tecsup.model.Empleado;
import com.tecsup.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin(origins = "http://localhost:4200")
public class EmpleadoController {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @GetMapping("/empleados")
    public List<Empleado> listarTodosLosEmpleados(){
        return empleadoRepository.findAll();
    }

    @PostMapping("/empleados")
    public Empleado guardarEmpleados (@RequestBody Empleado empleado){
        return empleadoRepository.save(empleado);
    }

    @GetMapping("/empleados/{id}")
    public ResponseEntity obtenerEmpleadosPorId(@PathVariable Long id){
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("No existe el empleado con el ID: " + id));
        return ResponseEntity.ok(empleado);
    }

    @PutMapping("/empleados/{id}")
    public ResponseEntity<Empleado> actualizarEmpleado (@PathVariable Long id, @RequestBody Empleado detallesEmpleado){
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("No existe el empleado con el ID: " + id));
        empleado.setNombre(detallesEmpleado.getNombre());
        empleado.setApellidos(detallesEmpleado.getApellidos());
        empleado.setEmail(detallesEmpleado.getEmail());

        Empleado empleadoActualizado = empleadoRepository.save(empleado);
        return ResponseEntity.ok(empleadoActualizado);

    }

    @DeleteMapping("/empleados/{id}")
    public ResponseEntity<Map<String,Boolean>> eliminarEmpleado (@PathVariable Long id){
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("No existe el empleado con el ID: " + id));

        empleadoRepository.delete(empleado);
        Map<String,Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminar", Boolean.TRUE);
        return ResponseEntity.ok(respuesta);
    }
}
