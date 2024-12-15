package br.com.eClinic.modelo.especialidades;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EspecialidadeService {

    @Autowired
    private EspecialidadeRepository especialidadeRepository;

    public List<Especialidade> listarTodas() {
        return especialidadeRepository.findAll();
    }

    public Optional<Especialidade> buscarPorId(Long id) {
        return especialidadeRepository.findById(id);
    }

    public Especialidade criar(Especialidade especialidade) {
        return especialidadeRepository.save(especialidade);
    }

    public Especialidade atualizar(Long id, Especialidade especialidade) {
        if (especialidadeRepository.existsById(id)) {
            especialidade.setId(id);
            return especialidadeRepository.save(especialidade);
        }
        return null;
    }

    public void deletar(Long id) {
        especialidadeRepository.deleteById(id);
    }
}

