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
     * Lista os contatos com base nos parâmetros opcionais.
     *
     * @author Matheus Abreu Magalhães
     * @param q      Opcional. Uma string de consulta para filtrar os contatos.
     * @param fields Opcional. Uma lista de campos para incluir na resposta.
     * @return Um objeto ResponseEntity contendo uma lista de objetos ContatoDTO.
     *         Retorna status HTTP 200 (OK) se a listagem for encontrada com sucesso.
     *         Retorna status HTTP 400 (Bad Request) se houver um problema ao buscar a lista.
     *         Retorna status HTTP 404 (Not Found) se os contatos não forem encontrados.
     */
    @Operation(summary = "Lista os contatos",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Listagem encontrada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Houve um problema ao buscar a lista"),
                    @ApiResponse(responseCode = "404", description = "Contatos não encontrados")
            })
    ResponseEntity<List<ContatoDTO>> findAllContatos(@RequestParam(required = false) String q, @RequestParam(required = false) List<String>fields);

    /**
     * Busca um contato pelo ID.
     *
     * @author Matheus Abreu Magalhães
     * @param id O ID do contato a ser buscado.
     * @return Um objeto ResponseEntity contendo o contato encontrado.
     *         Retorna status HTTP 200 (OK) se o contato for encontrado com sucesso.
     *         Retorna status HTTP 400 (Bad Request) se houver um problema ao buscar o contato.
     *         Retorna status HTTP 404 (Not Found) se o contato não for encontrado.
     */
    @Operation(summary = "Busca contato pelo id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Contato encontrado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Houve um problema ao buscar o contato"),
                    @ApiResponse(responseCode = "404", description = "Contato não encontrado")
            })
    ResponseEntity<ContatoDTO> findContatoById(@PathVariable Long id);

    /**
     * Cria um novo contato com base nos dados fornecidos no corpo da solicitação.
     *
     * @author Matheus Abreu Magalhães
     * @param input O objeto ContatoInput contendo os dados do novo contato a ser criado.
     * @return Um objeto ResponseEntity contendo uma mensagem indicando o sucesso da criação do contato.
     *         Retorna status HTTP 201 (Created) se o contato for criado com sucesso.
     *         Retorna status HTTP 400 (Bad Request) se houver um problema ao criar o contato.
     *         Retorna status HTTP 404 (Not Found) se houver um problema ao buscar a página.
     */
    @Operation(summary = "Cria um contato",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Contato criado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Houve um problema ao criar o contato"),
                    @ApiResponse(responseCode = "404", description = "Houve um problema ao buscar a página")
            })
    ResponseEntity<String> createContato(@RequestBody ContatoInput input);

    /**
     * Atualiza um contato existente com base no ID fornecido e nos dados fornecidos no corpo da solicitação.
     *
     * @author Matheus Abreu Magalhães
     * @param id    O ID do contato a ser atualizado.
     * @param input O objeto ContatoUpdateInput contendo os novos dados para atualização do contato.
     * @return Um objeto ResponseEntity contendo uma mensagem indicando o sucesso da atualização do contato.
     *         Retorna status HTTP 201 (Created) se o contato for atualizado com sucesso.
     *         Retorna status HTTP 400 (Bad Request) se houver um problema ao atualizar o contato.
     *         Retorna status HTTP 404 (Not Found) se o contato não for encontrado.
     */
    @Operation(summary = "Atualiza um contato",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Contato atualizado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Houve um problema ao atualizar o contato"),
                    @ApiResponse(responseCode = "404", description = "Contato não encontrado")
            })
    ResponseEntity<String> updateContato(@PathVariable Long id, @RequestBody ContatoUpdateInput input);

    /**
     * Exclui um contato existente com base no ID fornecido.
     *
     * @author Matheus Abreu Magalhães
     * @param id O ID do contato a ser excluído.
     * @return Um objeto ResponseEntity contendo uma mensagem indicando o sucesso da exclusão do contato.
     *         Retorna status HTTP 204 (No Content) se o contato for excluído com sucesso.
     *         Retorna status HTTP 400 (Bad Request) se houver um problema ao buscar o contato.
     *         Retorna status HTTP 404 (Not Found) se o contato não for encontrado.
     */
    @Operation(summary = "Exclui um contato pelo ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Contato excluído com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Houve um problema ao buscar o contato"),
                    @ApiResponse(responseCode = "404", description = "Contato não encontrado")
            })
    ResponseEntity<String> deleteContato(@PathVariable Long id);

}
