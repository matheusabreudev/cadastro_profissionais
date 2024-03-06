package com.cadastroprofissional.simples.controller.openApi;

import com.cadastroprofissional.simples.model.Profissional;
import com.cadastroprofissional.simples.model.dto.ProfissionalDTO;
import com.cadastroprofissional.simples.model.input.ProfissionalInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "Profissional")
public interface ProfissionalApi {

    /**
     * Lista todos os profissionais com a opção de filtrar por consulta e selecionar campos específicos.
     *
     * @author Matheus Abreu Magalhães
     * @param q      Consulta opcional para filtrar os profissionais.
     * @param fields Lista opcional de campos a serem selecionados para cada profissional.
     * @return Um objeto ResponseEntity contendo uma lista de objetos ProfissionalDTO.
     *         Retorna status HTTP 200 (OK) se a listagem for encontrada com sucesso.
     *         Retorna status HTTP 400 (Bad Request) se houver um problema ao buscar a lista.
     *         Retorna status HTTP 404 (Not Found) se nenhum profissional for encontrado.
     */
    @Operation(summary = "Lista os profissionais",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Listagem encontrada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Houve um problema ao buscar a lista"),
                    @ApiResponse(responseCode = "404", description = "Profissionais não encontrados")
            })
    ResponseEntity<List<ProfissionalDTO>> findAllProfissionais(@RequestParam(required = false) String q, @RequestParam(required = false) List<String>fields);

    /**
     * Busca um profissional pelo ID.
     *
     * @author Matheus Abreu Magalhães
     * @param id O ID do profissional a ser buscado.
     * @return Um objeto ResponseEntity contendo um objeto ProfissionalDTO correspondente ao profissional encontrado.
     *         Retorna status HTTP 200 (OK) se o profissional for encontrado com sucesso.
     *         Retorna status HTTP 400 (Bad Request) se houver um problema ao buscar o profissional.
     *         Retorna status HTTP 404 (Not Found) se o profissional não for encontrado.
     */
    @Operation(summary = "Busca profissional pelo id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Profissional encontrado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Houve um problema ao buscar o profissional"),
                    @ApiResponse(responseCode = "404", description = "Profissional não encontrado")
            })
    ResponseEntity<ProfissionalDTO> findProfissionalById(@PathVariable Long id);

    /**
     * Cria um novo profissional.
     *
     * @author Matheus Abreu Magalhães
     * @param input O objeto ProfissionalInput contendo os detalhes do novo profissional a ser criado.
     * @return Um objeto ResponseEntity contendo uma mensagem indicando o sucesso da operação.
     *         Retorna status HTTP 201 (Created) se o profissional for criado com sucesso.
     *         Retorna status HTTP 400 (Bad Request) se houver um problema ao criar o profissional.
     *         Retorna status HTTP 404 (Not Found) se houver um problema ao buscar a página.
     */
    @Operation(summary = "Cria um profissional",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Profissional criado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Houve um problema ao criar o profissional"),
                    @ApiResponse(responseCode = "404", description = "Houve um problema ao buscar a página")
            })
    ResponseEntity<String> createProfissional(@RequestBody ProfissionalInput input);

    /**
     * Atualiza um profissional existente pelo ID.
     *
     * @author Matheus Abreu Magalhães
     * @param id O ID do profissional a ser atualizado.
     * @param input O objeto ProfissionalInput contendo os novos detalhes do profissional.
     * @return Um objeto ResponseEntity contendo uma mensagem indicando o sucesso da operação.
     *         Retorna status HTTP 201 (Created) se o profissional for atualizado com sucesso.
     *         Retorna status HTTP 400 (Bad Request) se houver um problema ao atualizar o profissional.
     *         Retorna status HTTP 404 (Not Found) se o profissional não for encontrado.
     */
    @Operation(summary = "Atualiza um profissional",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Profissional atualizado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Houve um problema ao atualizar o profissional"),
                    @ApiResponse(responseCode = "404", description = "Profissional não encontrado")
            })
    ResponseEntity<String> updateProfissional(@PathVariable Long id, @RequestBody ProfissionalInput input);

    /**
     * Exclui um profissional pelo ID.
     *
     * @author Matheus Abreu Magalhães
     * @param id O ID do profissional a ser excluído.
     * @return Um objeto ResponseEntity contendo uma mensagem indicando o sucesso da operação.
     *         Retorna status HTTP 204 (No Content) se o profissional for excluído com sucesso.
     *         Retorna status HTTP 400 (Bad Request) se houver um problema ao buscar o profissional.
     *         Retorna status HTTP 404 (Not Found) se o profissional não for encontrado.
     */
    @Operation(summary = "Exclui um profissional pelo ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Profissional excluído com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Houve um problema ao buscar o profissional" ),
                    @ApiResponse(responseCode = "404", description = "rofissional não encontrado")
            })
    ResponseEntity<String> deleteProfissional(@PathVariable Long id);

}
