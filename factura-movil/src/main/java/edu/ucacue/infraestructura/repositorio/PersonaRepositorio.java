package edu.ucacue.infraestructura.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.ucacue.modelo.Persona;

public interface PersonaRepositorio extends JpaRepository<Persona, Integer>{

}
