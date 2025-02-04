package br.com.eClinic.modelo.paciente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PacienteRepository extends JpaRepository<Paciente, Long>{
    @Query("SELECT p FROM Paciente p WHERE p.cpf = :cpf")
    Paciente buscarPorCpf(@Param("cpf") String cpf);
}

