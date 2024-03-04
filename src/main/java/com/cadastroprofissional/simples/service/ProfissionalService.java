package com.cadastroprofissional.simples.service;

import com.cadastroprofissional.simples.model.Profissional;
import com.cadastroprofissional.simples.model.input.ProfissionalInput;
import com.cadastroprofissional.simples.repository.ProfissionalRepository;
import com.cadastroprofissional.simples.util.MensagemUtil;
import com.cadastroprofissional.simples.util.enums.CargoEnum;
import com.cadastroprofissional.simples.util.exception.EntidadeNaoExistenteException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProfissionalService {

    private final ProfissionalRepository repository;

    public Profissional findProfissionalById(Long profissionalId) {
        return this.repository.findProfissionalByIdAndAtivoIsTrue(profissionalId)
                .orElseThrow(() -> new EntidadeNaoExistenteException(MensagemUtil.MSG_ENTITY_PROFISSIONAL_NOT_EXISTS));
    }

    public List<Profissional> findAllProfissionais(String q, List<String> fields) {

        List<Profissional> profissionaisFiltrados = this.repository.findAll();

        List<Profissional> profissionaisSelecionados = new ArrayList<>();

        if(fields != null && !fields.isEmpty()){
            for (Profissional profissional : profissionaisFiltrados) {

                Profissional profissionalSelecionado = new Profissional();

                for (String field : fields) {
                    switch (field) {
                        case "id" -> profissionalSelecionado.setId(profissional.getId());
                        case "nome" -> profissionalSelecionado.setNome(profissional.getNome());
                        case "cargo" -> profissionalSelecionado.setCargo(profissional.getCargo());
                        case "dataNascimento" ->
                                profissionalSelecionado.setDataNascimento(profissional.getDataNascimento());
                        case "createdDate" -> profissionalSelecionado.setCreatedDate(profissional.getCreatedDate());
                    }
                }
                profissionaisSelecionados.add(profissionalSelecionado);
            }
        }

        if(profissionaisSelecionados != null && !profissionaisSelecionados.isEmpty()) {
            return profissionaisSelecionados;
        } else {
            return profissionaisFiltrados;
        }

    }

    public Profissional createProfissional(ProfissionalInput input) {
        Profissional profissional = new Profissional(input);
        profissional.setCreatedDate(LocalDate.now());
        return this.repository.save(profissional);
    }

    public Profissional updateProfissional(Long profissionalId, ProfissionalInput input) {
        Profissional ProfissionalExistente = findProfissionalById(profissionalId);

        if(input.getNome() != null) {
            ProfissionalExistente.setNome(input.getNome());
        }

        if(input.getCargo() != null) {
            ProfissionalExistente.setCargo(CargoEnum.valueOf(input.getCargo()));
        }

        if(input.getDataNascimento() != null) {
            ProfissionalExistente.setDataNascimento(input.getDataNascimento());
        }

        return this.repository.save(ProfissionalExistente);
    }

    public void deleteProfissional(Long ProfissionalId) {
        Profissional profissional = this.findProfissionalById(ProfissionalId);
        profissional.setAtivo(false);
        this.repository.save(profissional);
    }

}
