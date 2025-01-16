package br.com.eClinic.modelo.especialidades;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface EspecialidadeRepository extends JpaRepository<Especialidade, Long> {

  @Query(value = "SELECT e FROM Especialidade e WHERE e.nome ilike %:nome%  ORDER BY e.nome")
  List<Especialidade> consultarPorNome (String nome);
}