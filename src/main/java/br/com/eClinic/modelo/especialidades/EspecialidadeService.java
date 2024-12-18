package br.com.eClinic.modelo.especialidades;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class EspecialidadeService {

    @Autowired
    private EspecialidadeRepository especialidadeRepository;

    @Transactional
    public Especialidade save(Especialidade especialidade) {

        especialidade.setHabilitado(Boolean.TRUE);
        return especialidadeRepository.save(especialidade);
    }

    public List<Especialidade> listarTodos() {

        return especialidadeRepository.findAll();
    }

    public Especialidade obterPorID(Long id) {

        return especialidadeRepository.findById(id).get();
    }

    @Transactional
    public void update(Long id, Especialidade especialidadeAlterado) {

        Especialidade especialidade = especialidadeRepository.findById(id).get();
        especialidade.setNome(especialidadeAlterado.getNome());
        especialidade.setDescricao(especialidadeAlterado.getDescricao());

        especialidadeRepository.save(especialidade);
    }

}