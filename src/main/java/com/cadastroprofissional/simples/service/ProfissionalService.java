package com.cadastroprofissional.simples.service;

import com.cadastroprofissional.simples.model.Profissional;
import com.cadastroprofissional.simples.model.dto.ProfissionalDTO;
import com.cadastroprofissional.simples.model.input.ProfissionalInput;
import com.cadastroprofissional.simples.repository.ProfissionalRepository;
import com.cadastroprofissional.simples.util.MensagemUtil;
import com.cadastroprofissional.simples.util.enums.CargoEnum;
import com.cadastroprofissional.simples.util.exception.CargoInvalidoException;
import com.cadastroprofissional.simples.util.exception.EntidadeNaoExistenteException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProfissionalService {

    private final ProfissionalRepository repository;

    /**
     * Busca um profissional pelo ID, garantindo que esteja ativo.
     *
     * @author Matheus Abreu Magalhães
     * @param profissionalId O ID do profissional a ser buscado.
     * @return O {@link Profissional} encontrado.
     * @throws EntidadeNaoExistenteException Se o profissional não for encontrado.
     */
    public Profissional findProfissionalById(Long profissionalId) {
        return this.repository.findProfissionalByIdAndAtivoIsTrue(profissionalId)
                .orElseThrow(() -> new EntidadeNaoExistenteException(MensagemUtil.MSG_ENTITY_PROFISSIONAL_NOT_EXISTS));
    }

    /**
     * Busca todos os profissionais e filtra os resultados com base nos parâmetros fornecidos.
     *
     * @author Matheus Abreu Magalhães
     * @param q A string de consulta para filtrar os profissionais.
     * @param fields Uma lista de campos a serem incluídos nos resultados filtrados.
     * @return Uma lista de objetos {@code ProfissionalDTO} contendo os profissionais filtrados.
     */
    public List<ProfissionalDTO> findAllProfissionais(String q, List<String> fields) {

        List<ProfissionalDTO> profissionaisFiltrados = this.repository.findByAnyColumn(q).stream().map(prof -> prof.toDTO()).collect(Collectors.toList());

        List<ProfissionalDTO> profissionaisSelecionados = new ArrayList<>();

        if(fields != null && !fields.isEmpty()){

            for (ProfissionalDTO profissional : profissionaisFiltrados) {

                ProfissionalDTO profissionalSelecionado = new ProfissionalDTO();

                for (String field : fields) {
                    if(field != null) {
                        switch (field) {
                            case "id" -> profissionalSelecionado.setId(profissional.getId());
                            case "nome" -> profissionalSelecionado.setNome(profissional.getNome());
                            case "cargo" -> profissionalSelecionado.setCargo(profissional.getCargo());
                            case "dataNascimento" ->
                                    profissionalSelecionado.setDataNascimento(profissional.getDataNascimento());
                            case "createdDate" -> profissionalSelecionado.setCreatedDate(profissional.getCreatedDate());
                        }
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

    /**
     * Cria um novo profissional com base nos dados fornecidos no objeto de entrada.
     * O cargo no objeto de entrada é convertido para maiúsculas antes de criar o profissional.
     * A data de criação é definida como a data atual.
     *
     * @author Matheus Abreu Magalhães
     * @param input O objeto de entrada {@link ProfissionalInput} contendo os dados do novo profissional.
     * @return O {@link Profissional} recém-criado.
     */
    public Profissional createProfissional(ProfissionalInput input) {
        input.setCargo(input.getCargo().toUpperCase());
        try {
            CargoEnum.valueOf(input.getCargo().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new CargoInvalidoException(MensagemUtil.CARGO_INVALIDO);
        }
        Profissional profissional = new Profissional(input);
        profissional.setCreatedDate(LocalDate.now());
        return this.repository.save(profissional);
    }

    /**
     * Atualiza os dados de um profissional existente com base no ID fornecido e nos novos dados fornecidos no objeto de entrada.
     * Se um campo no objeto de entrada for diferente de null, o valor correspondente no profissional existente será atualizado com o novo valor.
     *
     * @author Matheus Abreu Magalhães
     * @param profissionalId O ID do profissional a ser atualizado.
     * @param input O objeto de entrada {@link ProfissionalInput} contendo os novos dados do profissional.
     * @return O {@link Profissional} atualizado.
     * @throws EntidadeNaoExistenteException Se o profissional com o ID fornecido não existir.
     */
    public Profissional updateProfissional(Long profissionalId, ProfissionalInput input) {
        Profissional ProfissionalExistente = findProfissionalById(profissionalId);

        if(input.getNome() != null) {
            ProfissionalExistente.setNome(input.getNome());
        }

        if(input.getCargo() != null) {
            try {
                ProfissionalExistente.setCargo(CargoEnum.valueOf(input.getCargo().toUpperCase()));
            } catch (IllegalArgumentException ex) {
                throw new CargoInvalidoException(MensagemUtil.CARGO_INVALIDO);
            }
        }

        if(input.getDataNascimento() != null) {
            ProfissionalExistente.setDataNascimento(input.getDataNascimento());
        }

        return this.repository.save(ProfissionalExistente);
    }

    /**
     * Desativa um profissional existente com base no ID fornecido, marcando-o como inativo.
     *
     * @author Matheus Abreu Magalhães
     * @param profissionalId O ID do profissional a ser desativado.
     * @throws EntidadeNaoExistenteException Se o profissional com o ID fornecido não existir.
     */
    public void deleteProfissional(Long profissionalId) {
        Profissional profissional = this.findProfissionalById(profissionalId);
        profissional.setAtivo(false);
        this.repository.save(profissional);
    }

}
