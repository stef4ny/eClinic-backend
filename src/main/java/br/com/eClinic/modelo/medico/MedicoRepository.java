package br.com.eClinic.modelo.medico;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicoRepository  extends JpaRepository<Medico, Long> {

    List<Medico> findByEspecialidadeId(Long idEspecialidade);

    Medico findByCrm(String crm);
  
}

