package br.com.eClinic.modelo.administrador.GerenciamentoPaciente;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface GerenciamentoPacienteRepository  extends JpaRepository<GerenciadorPaciente, Long>{

   List<GerenciadorPaciente> findByNomeCompletoContaining(String nomeCompleto);
  
}