package br.com.eClinic.api.especialidade;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.eClinic.modelo.especialidades.Especialidade;
import br.com.eClinic.modelo.especialidades.EspecialidadeService;

@RestController
@RequestMapping("/api/especialidade")
public class EspecialidadeController {

    @Autowired
    private EspecialidadeService especialidadeService;

    @GetMapping
    public List<Especialidade> listarEspecialidades() {
        return especialidadeService.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Especialidade> buscarEspecialidade(@PathVariable Long id) {
        Optional<Especialidade> especialidade = especialidadeService.buscarPorId(id);
        return especialidade.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Especialidade criarEspecialidade(@RequestBody Especialidade especialidade) {
        return especialidadeService.criar(especialidade);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Especialidade> atualizarEspecialidade(@PathVariable Long id, @RequestBody Especialidade especialidade) {
        Especialidade updatedEspecialidade = especialidadeService.atualizar(id, especialidade);
        return updatedEspecialidade != null ? ResponseEntity.ok(updatedEspecialidade) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEspecialidade(@PathVariable Long id) {
        especialidadeService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}