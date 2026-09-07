package cl.nttdata.personas.repository;

import cl.nttdata.personas.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, String> {
   
}