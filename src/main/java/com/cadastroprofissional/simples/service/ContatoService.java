package com.cadastroprofissional.simples.service;

import com.cadastroprofissional.simples.model.Contato;
import com.cadastroprofissional.simples.model.Profissional;
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

    public Contato findContatoById(Long contatoId) {
        return this.repository.findContatoByIdAndProfissionalAtivoIsTrue(contatoId).orElseThrow(() -> new EntidadeNaoExistenteException(MensagemUtil.MSG_ENTITY_CONTATO_NOT_EXISTS));
    }

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

    public Contato updateContato(Long contatoId, ContatoUpdateInput input) {
        Contato contatoExistente = findContatoById(contatoId);

        if(input.getNome() != null) {
            contatoExistente.setNome(input.getNome());
        }

        if(input.getContato() != null) {
            contatoExistente.setContato(input.getContato());
        }

        return this.repository.save(contatoExistente);
    }

    public void deleteContato(Long contatoId) {
        this.findContatoById(contatoId);
        this.repository.deleteById(contatoId);
    }

    public static boolean validarTelefone(String telefone) {
        if (telefone == null || telefone.isEmpty()) {
            return false;
        }
        telefone = telefone.trim();

        return telefone.matches("[0-9]+") && (telefone.length() == 10 || telefone.length() == 11);
    }

    private Boolean validaTelefoneExistente(String contato) {
        return this.repository.existsContatoByContato(contato);
    }

}
