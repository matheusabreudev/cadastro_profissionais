package com.cadastroprofissional.simples.controller;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cadastroprofissional.simples.model.Contato;
import com.cadastroprofissional.simples.model.Profissional;
import com.cadastroprofissional.simples.model.input.ContatoInput;
import com.cadastroprofissional.simples.model.input.ContatoUpdateInput;
import com.cadastroprofissional.simples.repository.ContatoRepository;
import com.cadastroprofissional.simples.service.ContatoService;
import com.cadastroprofissional.simples.service.ProfissionalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ContatoControllerTest {

    private MockMvc mockMvc;
    private ContatoRepository repository;
    private ProfissionalService profissionalService;
    private ContatoService service;
    private ContatoController controller;

    public ContatoControllerTest(){
        repository = mock(ContatoRepository.class);
        profissionalService = mock(ProfissionalService.class);
        service = mock(ContatoService.class);
        controller = new ContatoController(service);
    }

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice().build();
    }

    @Test
    @DisplayName("Testa encontrar contato por string q e fields")
    void testFindAllContatos() throws Exception {
        String q = "search_query";
        List<String> fields = List.of("id", "nome");

        Contato contato = new Contato();
        contato.setId(1L);
        contato.setContato("1234567890");
        contato.setCreatedDate(LocalDate.now());
        contato.setProfissional(new Profissional(1L));
        contato.setNome("Telefone");

        when(repository.findByAnyColumn(q)).thenReturn(Collections.singletonList(contato));

        mockMvc.perform(get("/contato")
                        .param("q", q)
                        .param("fields", "id", "nome")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service, times(1)).findAllContatos(q, fields);
    }

    @Test
    @DisplayName("Testa encontrar um contato pelo ID")
    void testFindContatoById() throws Exception {
        Contato contato = new Contato();
        contato.setId(1L);
        contato.setContato("1234567890");
        contato.setCreatedDate(LocalDate.now());
        contato.setProfissional(new Profissional(1L));
        contato.setNome("Telefone");

        when(service.findContatoById(contato.getId())).thenReturn(contato);

        mockMvc.perform(get("/contato/{id}", contato.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(contato.getId().intValue())));
    }

    @Test
    @DisplayName("Testa criar um contato")
    void testCreateContato() {
        ContatoInput input = new ContatoInput();
        input.setNome("Teste");
        input.setContato("1234567890");

        Contato contatoCriado = new Contato();
        contatoCriado.setId(1L);
        contatoCriado.setNome("Teste");
        contatoCriado.setContato("1234567890");

        when(service.createContato(input)).thenReturn(contatoCriado);

        ResponseEntity<String> response = controller.createContato(input);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        verify(service, times(1)).createContato(input);

        assertEquals("Contato com id 1 criado com sucesso.", response.getBody());
    }

    @Test
    @DisplayName("Testa atualizar um contato")
    void testUpdateContato() {
        Long contatoId = 1L;
        ContatoUpdateInput input = new ContatoUpdateInput();
        input.setNome("Novo Nome");
        input.setContato("1234567890");

        Contato contatoAtualizado = new Contato();
        contatoAtualizado.setId(contatoId);
        contatoAtualizado.setNome("Novo Nome");
        contatoAtualizado.setContato("1234567890");

        when(service.updateContato(contatoId, input)).thenReturn(contatoAtualizado);

        ResponseEntity<String> response = controller.updateContato(contatoId, input);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        verify(service, times(1)).updateContato(contatoId, input);

        assertEquals("Contato com id 1 atualizado com sucesso.", response.getBody());
    }


    @Test
    @DisplayName("Testa deletar um contato")
    void testDeleteContato() throws Exception {
        Long contatoId = 1L;

        doNothing().when(service).deleteContato(contatoId);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(delete("/contato/{id}", contatoId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(service, times(1)).deleteContato(contatoId);
    }
}