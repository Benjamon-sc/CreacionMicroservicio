package cl.nttdata.personas.Controller;

import cl.nttdata.personas.model.Persona;
import cl.nttdata.personas.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personas")
public class PersonaController {

    @Autowired
    private PersonaRepository personaRepository;

    // GET: Obtener todas las personas
    @GetMapping
    public List<Persona> obtenerTodas() {
        return personaRepository.findAll();
    }

    // GET: Obtener una persona por RUT
    @GetMapping("/{rut}")
    public ResponseEntity<Persona> obtenerPorRut(@PathVariable String rut) {
        return personaRepository.findById(rut)
                .map(persona -> ResponseEntity.ok().body(persona))
                .orElse(ResponseEntity.notFound().build());
    }

    // POST: Guardar una nueva persona
    @PostMapping
    public Persona guardarPersona(@RequestBody Persona persona) {
        return personaRepository.save(persona);
    }

    // DELETE: Eliminar por RUT
    @DeleteMapping("/{rut}")
    public ResponseEntity<Void> eliminarPersona(@PathVariable String rut) {
        if (personaRepository.existsById(rut)) {
            personaRepository.deleteById(rut);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}