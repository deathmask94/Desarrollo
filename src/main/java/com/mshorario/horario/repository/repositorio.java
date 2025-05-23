package com.mshorario.horario.repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.mshorario.horario.model.Horario;

@Repository
public interface repositorio extends MongoRepository<Horario, String> {
    Optional<Horario> findByIdCarrera(String idCarrera);
    Optional<Horario> findByDia(String dia);
    Optional<Horario> findByAsignatura(String asignatura);
    boolean existsByHora(String hora);
    void deleteByHora(String hora);
}
