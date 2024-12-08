package br.com.eClinic.modelo.medico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.regex.Pattern;

@Service
public class MedicoService {

    @Autowired
    private MedicoRepository repository;

    @Transactional
    public Medico save(Medico medico) throws Exception {
        validarCadastroMedico(medico);
        medico.setHabilitado(true);
        return repository.save(medico);
    }

    public void validarCadastroMedico(Medico medico) throws Exception {
       
        if (!validarNomeCompleto(medico.getNomeCompleto())) {
            throw new IllegalArgumentException("Formato inválido para o nome completo.");
        }

       
        if (repository.existsByCrm(medico.getCrm())) {
            throw new IllegalArgumentException("CRM já registrado.");
        }

      
        if (!validarSenha(medico.getSenha())) {
            throw new IllegalArgumentException(
                    "Senha inválida. Somente letras (a-z) e números, e no mínimo 8 caracteres.");
        }

        
        if (!validarDataNascimento(medico.getDataNascimento())) {
            throw new IllegalArgumentException("Data de nascimento inválida. A data deve ser no passado.");
        }

       
        if (!validarEmail(medico.getEmail())) {
            throw new IllegalArgumentException("E-mail inválido. O e-mail deve estar em um formato válido.");
        }

        
        if (!validarEnderecoCidade(medico.getEnderecoCidade())) {
            throw new IllegalArgumentException("Nome da cidade inválido. A cidade deve ter entre 3 e 50 caracteres.");
        }

       
        if (!validarEnderecoUf(medico.getEnderecoUf())) {
            throw new IllegalArgumentException("UF inválido. A UF deve ter exatamente 2 caracteres.");
        }

      
        if (!validarDescricao(medico.getDescricao())) {
            throw new IllegalArgumentException("Descrição muito longa. A descrição deve ter no máximo 500 caracteres.");
        }

     
        if (!validarEspecialidades(medico.getEspecialidades())) {
            throw new IllegalArgumentException(
                    "Especialidades muito longas. O campo deve ter no máximo 200 caracteres.");
        }

    }

    private boolean validarNomeCompleto(String nomeCompleto) {
        String regex = "^[A-Za-z\\s]+$"; // somente letras e espaços
        return nomeCompleto.matches(regex);
    }

    private boolean validarSenha(String senha) {
        String regex = "^[A-Za-z0-9]{8,}$"; // pelo menos 8 caracteres, somente letras e números
        return senha.matches(regex);
    }

    private boolean validarDataNascimento(LocalDate dataNascimento) {
        return dataNascimento != null && dataNascimento.isBefore(LocalDate.now()); // A data de nascimento deve ser  antes de hoje
                                                                                   
    }

    private boolean validarEmail(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"; // Formato deemail válido
                                                                                                          
        return email != null && email.matches(regex);
    }

    private boolean validarEnderecoCidade(String cidade) {
        return cidade != null && cidade.length() >= 3 && cidade.length() <= 50; // Cidade deve ter entre 3 e 50 caraccteres
    }
                                                                               

    private boolean validarEnderecoUf(String uf) {
        String regex = "^[A-Za-z]{2}$"; // UF deve ter exatamente 2 caracteres
        return uf != null && uf.matches(regex);
    }

    private boolean validarDescricao(String descricao) {
        return descricao != null && descricao.length() <= 500; // Descrição não pode ter mais que 500 caracteres
    }

    private boolean validarEspecialidades(String especialidades) {
        return especialidades != null && especialidades.length() <= 200; // Especialidades não pode ter mais que 200 caracteres
                                                                         
    }
}