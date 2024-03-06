package com.cadastroprofissional.simples.controller;

import com.cadastroprofissional.simples.controller.openApi.ProfissionalApi;
import com.cadastroprofissional.simples.model.Profissional;
import com.cadastroprofissional.simples.model.dto.ProfissionalDTO;
import com.cadastroprofissional.simples.model.input.ProfissionalInput;
import com.cadastroprofissional.simples.service.ProfissionalService;
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
@RequestMapping("/profissional")
@AllArgsConstructor
public class ProfissionalController implements ProfissionalApi {

    private final ProfissionalService service;

    /**
     * Retorna uma lista de todos os profissionais.
     *
     * @author Matheus Abreu Magalhães
     * @param q      Uma string de consulta opcional para filtrar os profissionais.
     * @param fields Uma lista opcional de campos para incluir na resposta.
     * @return Um objeto ResponseEntity com uma lista de ProfissionalDTO no corpo da resposta.
     */
    @Override
    @GetMapping
    public ResponseEntity<List<ProfissionalDTO>> findAllProfissionais(@RequestParam(required = false) String q, @RequestParam(required = false) List<String> fields) {
        List<ProfissionalDTO> profissionais = service.findAllProfissionais(q, fields);
        return ResponseEntity.status(HttpStatus.OK).body(profissionais);
    }

    /**
     * Retorna um profissional com base no ID fornecido.
     *
     * @author Matheus Abreu Magalhães
     * @param id O ID do profissional a ser recuperado.
     * @return Um objeto ResponseEntity com o ProfissionalDTO correspondente no corpo da resposta, se encontrado.
     *         Retorna status HTTP 200 (OK) se o profissional for encontrado.
     *         Retorna status HTTP 404 (Not Found) se o profissional não for encontrado.
     */
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ProfissionalDTO> findProfissionalById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.findProfissionalById(id).toDTO());
    }

    /**
     * Cria um novo profissional com base nos dados fornecidos no corpo da solicitação.
     *
     * @author Matheus Abreu Magalhães
     * @param input Os dados do profissional a serem utilizados para a criação.
     * @return Um objeto ResponseEntity com uma mensagem indicando o sucesso da criação e o ID do profissional criado.
     *         Retorna status HTTP 201 (Created) se o profissional for criado com sucesso.
     */
    @Override
    @PostMapping
    public ResponseEntity<String> createProfissional(@RequestBody ProfissionalInput input) {
        Profissional prof = this.service.createProfissional(input);
        return ResponseEntity.status(HttpStatus.CREATED).body(String.format("Profissional com id %s criado com sucesso.", prof.getId().toString()));
    }

    /**
     * Atualiza as informações de um profissional existente com base no ID fornecido.
     *
     * @author Matheus Abreu Magalhães
     * @param id    O ID do profissional a ser atualizado.
     * @param input Os novos dados do profissional.
     * @return Um objeto ResponseEntity com uma mensagem indicando o sucesso da atualização e o ID do profissional atualizado.
     *         Retorna status HTTP 201 (Created) se o profissional for atualizado com sucesso.
     */
    @Override
    @PutMapping("/{id}")
    public ResponseEntity<String> updateProfissional(@PathVariable Long id, @RequestBody ProfissionalInput input) {
        Profissional prof = this.service.updateProfissional(id, input);
        return ResponseEntity.status(HttpStatus.CREATED).body(String.format("Profissional com id %s atualizado com sucesso.", prof.getId().toString()));
    }

    /**
     * Exclui um profissional existente com base no ID fornecido.
     *
     * @author Matheus Abreu Magalhães
     * @param id O ID do profissional a ser excluído.
     * @return Um objeto ResponseEntity com uma mensagem indicando o sucesso da exclusão.
     *         Retorna status HTTP 204 (No Content) se o profissional for excluído com sucesso.
     */
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProfissional(@PathVariable Long id) {
        this.service.deleteProfissional(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Profissional excluído com sucesso.");
    }
}
