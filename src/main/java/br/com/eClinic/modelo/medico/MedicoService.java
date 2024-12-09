package br.com.eClinic.modelo.medico;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import jakarta.transaction.Transactional;


@Service
public class MedicoService   {
    @Autowired
    private MedicoRepository repository;

    @Transactional
    public Medico save(Medico medico) {

        medico.setHabilitado(Boolean.TRUE);
        return repository.save(medico);
    }
}
