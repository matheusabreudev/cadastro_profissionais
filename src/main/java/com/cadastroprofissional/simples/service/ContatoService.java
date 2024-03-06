package com.cadastroprofissional.simples.service;

import com.cadastroprofissional.simples.model.Contato;
import com.cadastroprofissional.simples.model.dto.ContatoDTO;
import com.cadastroprofissional.simples.model.input.ContatoInput;
import com.cadastroprofissional.simples.model.input.ContatoUpdateInput;
import com.cadastroprofissional.simples.repository.ContatoRepository;
import com.cadastroprofissional.simples.util.MensagemUtil;
import com.cadastroprofissional.simples.util.exception.EntidadeNaoExistenteException;
import com.cadastroprofissional.simples.util.exception.TelefoneInvalidoException;
import com.cadastroprofissional.simples.util.exception.TelefoneJaCadastradoException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ContatoService {

    private final ContatoRepository repository;

    private final ProfissionalService profissionalService;

    /**
     * Retorna um contato com base no ID fornecido, se existir.
     *
     * @author Matheus Abreu Magalhães
     * @param contatoId O ID do contato a ser encontrado.
     * @return O objeto Contato correspondente ao ID fornecido.
     * @throws EntidadeNaoExistenteException Se o contato não existir no repositório.
     */
    public Contato findContatoById(Long contatoId) {
        return this.repository.findContatoByIdAndProfissionalAtivoIsTrue(contatoId).orElseThrow(() -> new EntidadeNaoExistenteException(MensagemUtil.MSG_ENTITY_CONTATO_NOT_EXISTS));
    }

    /**
     * Retorna uma lista de contatos filtrada com base nos parâmetros fornecidos.
     *
     * @author Matheus Abreu Magalhães
     * @param q A string de consulta para filtrar os contatos.
     * @param fields Uma lista de campos pelos quais os contatos devem ser filtrados.
     * @return Uma lista de objetos ContatoDTO filtrada com base nos parâmetros fornecidos.
     */
    public List<ContatoDTO> findAllContatos(String q, List<String> fields) {
//        List<ContatoDTO> contatosFiltrados = this.repository.findByString(q).stream().map(cont -> cont.toDto()).collect(Collectors.toList());

        List<ContatoDTO> contatosFiltrados = this.repository.findAll().stream().map(cont -> cont.toDto()).collect(Collectors.toList());

        List<ContatoDTO> contatosSelecionados = new ArrayList<>();

        if(fields != null && !fields.isEmpty()){
            for (ContatoDTO contato : contatosFiltrados) {

                ContatoDTO contatoSelecionado = new ContatoDTO();

                for (String field : fields) {
                    switch (field) {
                        case "id" -> contatoSelecionado.setId(contato.getId());
                        case "nome" -> contatoSelecionado.setNome(contato.getNome());
                        case "contato" -> contatoSelecionado.setContato(contato.getContato());
                        case "createdDate" -> contatoSelecionado.setCreatedDate(contato.getCreatedDate());
                        case "profissional" -> contatoSelecionado.setProfissional(contato.getProfissional());
                    }
                }
                contatosSelecionados.add(contatoSelecionado);
            }
        }

        if(contatosSelecionados != null && !contatosSelecionados.isEmpty()) {
            return contatosSelecionados;
        }else {
            return contatosFiltrados;
        }
    }

    /**
     * Cria um novo contato com base nos dados fornecidos.
     *
     * @author Matheus Abreu Magalhães
     * @param input O objeto ContatoInput contendo os dados do novo contato a ser criado.
     * @return O objeto Contato recém-criado.
     * @throws TelefoneInvalidoException Se o telefone fornecido for inválido.
     * @throws TelefoneJaCadastradoException Se o telefone fornecido já estiver cadastrado para outro contato.
     */
    public Contato createContato(ContatoInput input) {
        this.profissionalService.findProfissionalById(input.getProfissional());

        if (!this.validarTelefone(input.getContato())) {
            throw new TelefoneInvalidoException(MensagemUtil.MSG_TELEFONE_INVALIDO);
        }

        if(this.validaTelefoneExistente(input.getContato())) {
            throw new TelefoneJaCadastradoException(MensagemUtil.MSG_TELEFONE_JA_CADASTRADO);
        }

        Contato contato = new Contato(input);
        contato.setCreatedDate(LocalDate.now());
        return this.repository.save(contato);
    }

    /**
     * Atualiza um contato existente com base no ID fornecido e nos dados de entrada.
     *
     * @author Matheus Abreu Magalhães
     * @param contatoId O ID do contato a ser atualizado.
     * @param input O objeto ContatoUpdateInput contendo os dados atualizados do contato.
     * @return O objeto Contato atualizado.
     * @throws TelefoneInvalidoException Se o telefone fornecido for inválido.
     * @throws EntidadeNaoExistenteException Se o contato com o ID fornecido não existir.
     */
    public Contato updateContato(Long contatoId, ContatoUpdateInput input) {
        Contato contatoExistente = findContatoById(contatoId);

        if (!this.validarTelefone(input.getContato())) {
            throw new TelefoneInvalidoException(MensagemUtil.MSG_TELEFONE_INVALIDO);
        }

        if(input.getNome() != null) {
            contatoExistente.setNome(input.getNome());
        }

        if(input.getContato() != null) {
            contatoExistente.setContato(input.getContato());
        }

        return this.repository.save(contatoExistente);
    }

    /**
     * Exclui um contato com base no ID fornecido.
     *
     * @author Matheus Abreu Magalhães
     * @param contatoId O ID do contato a ser excluído.
     * @throws EntidadeNaoExistenteException Se o contato com o ID fornecido não existir.
     */
    public void deleteContato(Long contatoId) {
        this.findContatoById(contatoId);
        this.repository.deleteById(contatoId);
    }

    /**
     * Valida um número de telefone.
     *
     * @author Matheus Abreu Magalhães
     * @param telefone O número de telefone a ser validado.
     * @return true se o número de telefone for válido, caso contrário, false.
     */
    public static boolean validarTelefone(String telefone) {
        if (telefone == null || telefone.isEmpty()) {
            return false;
        }
        telefone = telefone.trim();

        return telefone.matches("[0-9]+") && (telefone.length() == 10 || telefone.length() == 11);
    }

    /**
     * Verifica se um número de telefone já está cadastrado no banco de dados.
     *
     * @author Matheus Abreu Magalhães
     * @param contato O número de telefone a ser verificado.
     * @return true se o número de telefone já estiver cadastrado, caso contrário, false.
     */
    private Boolean validaTelefoneExistente(String contato) {
        return this.repository.existsContatoByContato(contato);
    }

}
