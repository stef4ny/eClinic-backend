package br.com.eClinic.modelo.administrador.GerenciamentoPaciente;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import jakarta.transaction.Transactional;




@Service
public class GerenciamentoPacienteService {


   @Autowired
    private GerenciamentoPacienteRepository repository;


    @Transactional
    public GerenciadorPaciente save(GerenciadorPaciente gerenciadorpaciente) {

        gerenciadorpaciente.setHabilitado(Boolean.TRUE);
        return repository.save(gerenciadorpaciente);
    }

    public List<GerenciadorPaciente> listarTodos() {

        return repository.findAll();
    }

    public GerenciadorPaciente obterPorID(Long id) {
      return repository.findById(id)
              .orElseThrow(() -> new RuntimeException("Paciente não encontrado com ID: " + id));
  }

  @Transactional
  public void update(Long id, GerenciadorPaciente gerenciadorPacienteAlterado) {

      GerenciadorPaciente gerenciadorpaciente = repository.findById(id).get();
      gerenciadorpaciente.setNomeCompleto(gerenciadorPacienteAlterado.getNomeCompleto());

      repository.save(gerenciadorpaciente);
  }

  public List<GerenciadorPaciente> filtroPaciente(Long id, String nome) {
    if (id != null) {
        return repository.findById(id).map(List::of).orElse(List.of());
    }
    if (nome != null && !nome.isEmpty()) {
        return repository.findByNomeCompletoContaining(nome);
    }
    return listarTodos();
}

@Transactional
public void delete(Long id) {

    GerenciadorPaciente gerenciadorPaciente = repository.findById(id).get();
    gerenciadorPaciente.setHabilitado(Boolean.FALSE);

    repository.save(gerenciadorPaciente);
}

}
  

