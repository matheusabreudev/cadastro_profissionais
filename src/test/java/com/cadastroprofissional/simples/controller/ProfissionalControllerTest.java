package com.cadastroprofissional.simples.controller;

import com.cadastroprofissional.simples.model.Profissional;
import com.cadastroprofissional.simples.model.dto.ProfissionalDTO;
import com.cadastroprofissional.simples.model.input.ProfissionalInput;
import com.cadastroprofissional.simples.repository.ProfissionalRepository;
import com.cadastroprofissional.simples.service.ProfissionalService;
import com.cadastroprofissional.simples.util.enums.CargoEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ProfissionalControllerTest {

    private MockMvc mockMvc;
    private ProfissionalRepository repository;
    private ProfissionalService service;
    private ProfissionalController controller;

    public ProfissionalControllerTest(){
        repository = mock(ProfissionalRepository.class);
        service = mock(ProfissionalService.class);
        controller = new ProfissionalController(service);
    }

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice().build();
    }

    @Test
    @DisplayName("Testa trazer a lista de profissionais filtradas por q e por fields")
    void testFindAllProfissionais() {
        String q = "search_query";
        List<String> fields = List.of("id", "nome");

        ProfissionalDTO profissionalDTO = new ProfissionalDTO();
        profissionalDTO.setId(1L);
        profissionalDTO.setNome("Profissional 1");

        when(service.findAllProfissionais(q, fields)).thenReturn(Collections.singletonList(profissionalDTO));

        ResponseEntity<List<ProfissionalDTO>> response = controller.findAllProfissionais(q, fields);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(service, times(1)).findAllProfissionais(q, fields);

        assertEquals(Collections.singletonList(profissionalDTO), response.getBody());
    }

    @Test
    @DisplayName("Testa encontrar um profissional pelo ID")
    void testFindProfissionalById() {
        Long profissionalId = 1L;

        Profissional profissional = new Profissional();
        profissional.setId(profissionalId);
        profissional.setNome("Profissional 1");
        profissional.setCargo(CargoEnum.DESENVOLVEDOR);
        profissional.setDataNascimento(LocalDate.now());
        profissional.setCreatedDate(LocalDate.now());

        when(service.findProfissionalById(profissionalId)).thenReturn(profissional);

        ResponseEntity<ProfissionalDTO> response = controller.findProfissionalById(profissionalId);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(service, times(1)).findProfissionalById(profissionalId);

        ProfissionalDTO expectedDTO = profissional.toDTO();
        ProfissionalDTO actualDTO = response.getBody();

        assertEquals(expectedDTO.getId(), actualDTO.getId());
        assertEquals(expectedDTO.getNome(), actualDTO.getNome());
        assertEquals(expectedDTO.getCargo(), actualDTO.getCargo());
        assertEquals(expectedDTO.getDataNascimento(), actualDTO.getDataNascimento());
        assertEquals(expectedDTO.getCreatedDate(), actualDTO.getCreatedDate());
    }

    @Test
    @DisplayName("Testa criar um profissional")
    void testCreateProfissional() {
        ProfissionalInput input = new ProfissionalInput();
        input.setNome("Teste");
        input.setCargo("Desenvolvedor");

        Profissional profissionalCriado = new Profissional();
        profissionalCriado.setId(1L);
        profissionalCriado.setNome("Teste");
        profissionalCriado.setCargo(CargoEnum.DESENVOLVEDOR);

        when(service.createProfissional(input)).thenReturn(profissionalCriado);

        ResponseEntity<String> response = controller.createProfissional(input);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        verify(service, times(1)).createProfissional(input);

        assertEquals("Profissional com id 1 criado com sucesso.", response.getBody());
    }

    @Test
    @DisplayName("Testa atualizar um profissional")
    void testUpdateProfissional() {
        Long profissionalId = 1L;
        ProfissionalInput input = new ProfissionalInput();

        Profissional profissionalAtualizado = new Profissional();
        profissionalAtualizado.setId(profissionalId);

        when(service.updateProfissional(profissionalId, input)).thenReturn(profissionalAtualizado);

        ResponseEntity<String> response = controller.updateProfissional(profissionalId, input);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        verify(service, times(1)).updateProfissional(profissionalId, input);

        assertEquals("Profissional com id 1 atualizado com sucesso.", response.getBody());
    }

    @Test
    @DisplayName("Testa deletar um profissional")
    void testDeleteProfissional() {
        Long profissionalId = 1L;

        doNothing().when(service).deleteProfissional(profissionalId);

        ResponseEntity<String> response = controller.deleteProfissional(profissionalId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(service, times(1)).deleteProfissional(profissionalId);

        assertEquals("Profissional excluído com sucesso.", response.getBody());
    }
}