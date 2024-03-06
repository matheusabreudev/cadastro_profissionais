package com.cadastroprofissional.simples.controller;

import com.cadastroprofissional.simples.controller.openApi.ContatoApi;
import com.cadastroprofissional.simples.model.Contato;
import com.cadastroprofissional.simples.model.dto.ContatoDTO;
import com.cadastroprofissional.simples.model.input.ContatoInput;
import com.cadastroprofissional.simples.model.input.ContatoUpdateInput;
import com.cadastroprofissional.simples.service.ContatoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/contato")
@AllArgsConstructor
public class ContatoController implements ContatoApi {

    private final ContatoService service;

    /**
     * Retorna uma lista de todos os contatos ou uma lista filtrada de contatos com base nos parâmetros fornecidos.
     *
     * @author Matheus Abreu Magalhães
     * @param q      Uma string de consulta opcional para filtrar os contatos.
     * @param fields Uma lista opcional de campos para filtrar os contatos.
     * @return Um objeto ResponseEntity contendo a lista de contatos encontrados.
     *         Retorna status HTTP 200 (OK) se a operação for bem-sucedida.
     */
    @Override
    @GetMapping
    public ResponseEntity<List<ContatoDTO>> findAllContatos(@RequestParam(required = false) String q, @RequestParam(required = false) List<String>fields) {
        List<ContatoDTO> contatos = service.findAllContatos(q, fields);
        return ResponseEntity.status(HttpStatus.OK).body(contatos);
    }

    /**
     * Busca um contato pelo ID especificado.
     *
     * @author Matheus Abreu Magalhães
     * @param id O ID do contato a ser encontrado.
     * @return Um objeto ResponseEntity contendo o contato encontrado.
     *         Retorna status HTTP 200 (OK) se o contato for encontrado com sucesso.
     *         Retorna status HTTP 404 (Not Found) se o contato não for encontrado.
     */
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ContatoDTO> findContatoById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.findContatoById(id).toDto());
    }

    /**
     * Cria um novo contato com base nos dados fornecidos.
     *
     * @author Matheus Abreu Magalhães
     * @param input Os dados do novo contato a serem criados.
     * @return Um objeto ResponseEntity contendo uma mensagem indicando o sucesso da criação do contato.
     *         Retorna status HTTP 201 (Created) se o contato for criado com sucesso.
     */
    @Override
    @PostMapping
    public ResponseEntity<String> createContato(@RequestBody ContatoInput input) {
        Contato cont = this.service.createContato(input);
        return ResponseEntity.status(HttpStatus.CREATED).body(String.format("Contato com id %s criado com sucesso.", cont.getId().toString()));
    }

    /**
     * Atualiza um contato existente com base no ID fornecido e nos dados atualizados.
     *
     * @author Matheus Abreu Magalhães
     * @param id O ID do contato a ser atualizado.
     * @param input Os novos dados do contato.
     * @return Um objeto ResponseEntity contendo uma mensagem indicando o sucesso da atualização do contato.
     *         Retorna status HTTP 201 (Created) se o contato for atualizado com sucesso.
     */
    @Override
    @PutMapping("/{id}")
    public ResponseEntity<String> updateContato(@PathVariable Long id, @RequestBody ContatoUpdateInput input) {
        Contato cont = this.service.updateContato(id, input);
        return ResponseEntity.status(HttpStatus.CREATED).body(String.format("Contato com id %s atualizado com sucesso.", cont.getId().toString()));
    }

    /**
     * Exclui um contato com base no ID fornecido.
     *
     * @author Matheus Abreu Magalhães
     * @param id O ID do contato a ser excluído.
     * @return Um objeto ResponseEntity contendo uma mensagem indicando o sucesso da exclusão do contato.
     *         Retorna status HTTP 204 (No Content) se o contato for excluído com sucesso.
     */
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteContato(@PathVariable Long id) {
        this.service.deleteContato(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Contato excluído com sucesso.");
    }
}
