package br.com.eClinic.modelo.paciente;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
@Service
public class PacienteService {

    @Autowired
   private PacienteRepository repository;

   @Transactional
   public Paciente save(Paciente paciente) {

       paciente.setHabilitado(Boolean.TRUE);
       return repository.save(paciente);
   }

}
