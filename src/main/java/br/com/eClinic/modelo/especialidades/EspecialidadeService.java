package br.com.eClinic.modelo.especialidades;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class EspecialidadeService {

    @Autowired
    private EspecialidadeRepository repository;

    @Transactional
    public Especialidade save(Especialidade especialidade) {

        especialidade.setHabilitado(Boolean.TRUE);
        return repository.save(especialidade);
    }

    public List<Especialidade> listarTodos() {

        return repository.findAll();
    }

    public Especialidade obterPorID(Long id) {
        return repository.findById(id).get();
    }
    

    @Transactional
    public void update(Long id, Especialidade especialidadeAlterado) {

        Especialidade especialidade = repository.findById(id).get();
        especialidade.setNome(especialidadeAlterado.getNome());

        repository.save(especialidade);
    }


    public List<Especialidade> filtrarEspecialidade(String nome) {

        List<Especialidade> listaEspecialidades = repository.findAll();

        if((nome != null && !"".equals(nome))){

            listaEspecialidades = repository.consultarPorNome(nome);
        }
          return listaEspecialidades;
    }

}