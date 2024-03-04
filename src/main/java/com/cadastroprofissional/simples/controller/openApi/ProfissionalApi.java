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
     * Lista os profissionais
     * @return Lista os profissionais, pode ser baseado em uma palavra especifica, com campos definidos ou completos, caso não tenha parametro algum setado.
     */
    @Operation(summary = "Lista os profissionais",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Listagem encontrada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Houve um problema ao buscar a lista"),
                    @ApiResponse(responseCode = "404", description = "Profissionais não encontrados")
            })
    ResponseEntity<List<ProfissionalDTO>> findAllProfissionais(@RequestParam(required = false) String q, @RequestParam(required = false) List<String>fields);

    /**
     * Busca um profissional
     * @return Retorna o profissional pelo id;
     */
    @Operation(summary = "Busca profissional pelo id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Profissional encontrado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Houve um problema ao buscar o profissional"),
                    @ApiResponse(responseCode = "404", description = "Profissional não encontrado")
            })
    ResponseEntity<ProfissionalDTO> findProfissionalById(@PathVariable Long id);

    /**
     * Cria um profissional
     * @return Retorna mensagem de sucesso;
     */
    @Operation(summary = "Cria um profissional",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Profissional criado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Houve um problema ao criar o profissional"),
                    @ApiResponse(responseCode = "404", description = "Houve um problema ao buscar a página")
            })
    ResponseEntity<String> createProfissional(@RequestBody ProfissionalInput input);

    /**
     * Atualiza um profissional pelo id e body
     * @return Retorna mensagem de sucesso;
     */
    @Operation(summary = "Atualiza um profissional",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Profissional atualizado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Houve um problema ao atualizar o profissional"),
                    @ApiResponse(responseCode = "404", description = "Profissional não encontrado")
            })
    ResponseEntity<String> updateProfissional(@PathVariable Long id, @RequestBody ProfissionalInput input);

    /**
     * Exclui um profissional
     * @return Retorna mensagem de sucesso;
     */
    @Operation(summary = "Exclui um profissional pelo ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Profissional excluído com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Houve um problema ao buscar o profissional" ),
                    @ApiResponse(responseCode = "404", description = "rofissional não encontrado")
            })
    ResponseEntity<String> deleteProfissional(@PathVariable Long id);

}
