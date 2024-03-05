package com.cadastroprofissional.simples.controller.openApi;

import com.cadastroprofissional.simples.model.Contato;
import com.cadastroprofissional.simples.model.dto.ContatoDTO;
import com.cadastroprofissional.simples.model.input.ContatoInput;
import com.cadastroprofissional.simples.model.input.ContatoUpdateInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "Contato")
public interface ContatoApi {

    /**
     * Lista os contatos
     * @return Lista os contatos, pode ser baseado em uma palavra especifica, com campos definidos ou completos, caso não tenha parametro algum setado.
     */
    @Operation(summary = "Lista os contatos",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Listagem encontrada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Houve um problema ao buscar a lista"),
                    @ApiResponse(responseCode = "404", description = "Contatos não encontrados")
            })
    ResponseEntity<List<ContatoDTO>> findAllContatos(@RequestParam(required = false) String q, @RequestParam(required = false) List<String>fields);

    /**
     * Busca um contato
     * @return Retorna o contato pelo id;
     */
    @Operation(summary = "Busca contato pelo id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Contato encontrado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Houve um problema ao buscar o contato"),
                    @ApiResponse(responseCode = "404", description = "Contato não encontrado")
            })
    ResponseEntity<ContatoDTO> findContatoById(@PathVariable Long id);

    /**
     * Cria um contato
     * @return Retorna mensagem de sucesso;
     */
    @Operation(summary = "Cria um contato",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Contato criado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Houve um problema ao criar o contato"),
                    @ApiResponse(responseCode = "404", description = "Houve um problema ao buscar a página")
            })
    ResponseEntity<String> createContato(@RequestBody ContatoInput input);

    /**
     * Atualiza um contato pelo id e body
     * @return Retorna mensagem de sucesso;
     */
    @Operation(summary = "Atualiza um contato",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Contato atualizado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Houve um problema ao atualizar o contato"),
                    @ApiResponse(responseCode = "404", description = "Contato não encontrado")
            })
    ResponseEntity<String> updateContato(@PathVariable Long id, @RequestBody ContatoUpdateInput input);

    /**
     * Exclui um contato
     * @return Retorna mensagem de sucesso;
     */
    @Operation(summary = "Exclui um contato pelo ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Contato excluído com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Houve um problema ao buscar o contato"),
                    @ApiResponse(responseCode = "404", description = "Contato não encontrado")
            })
    ResponseEntity<String> deleteContato(@PathVariable Long id);

}
