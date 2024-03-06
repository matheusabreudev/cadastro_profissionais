package com.cadastroprofissional.simples.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.cadastroprofissional.simples.model.dto.ContatoDTO;
import com.cadastroprofissional.simples.repository.ContatoRepository;
import com.cadastroprofissional.simples.service.ContatoService;
import com.cadastroprofissional.simples.service.ProfissionalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ContatoControllerTest {

    public static final String URL = "http://localhost:8080/contato";

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private MockMvc mockMvc;

    private ContatoRepository repository;
    private ProfissionalService profissionalService;
    private ContatoService service;
    private ContatoController controller;

    public ContatoControllerTest(){
        repository = mock(ContatoRepository.class);
        profissionalService = mock(ProfissionalService.class);
        service = new ContatoService(repository, profissionalService);
        controller = new ContatoController(service);
    }

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice().build();
    }

    @Test
    void testFindAllContatos() throws Exception {
        // Dados de entrada para o teste
        String q = "search_query";
        List<String> fields = List.of("id", "nome");

        // Mock dos contatos que serão retornados pelo serviço
        List<ContatoDTO> contatos = new ArrayList<>();
        ContatoDTO contato1 = new ContatoDTO();
        contato1.setId(1L);
        contato1.setNome("Contato 1");
        contatos.add(contato1);
        ContatoDTO contato2 = new ContatoDTO();
        contato2.setId(2L);
        contato2.setNome("Contato 2");
        contatos.add(contato2);

        // Configuração do comportamento esperado do método findAllContatos
        when(service.findAllContatos(q, fields)).thenReturn(contatos);

        // Execução do teste
        mockMvc.perform(get("/contatos")
                        .param("q", q)
                        .param("fields", "id", "nome")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].nome", is("Contato 1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].nome", is("Contato 2")));

        // Verificação se o método findAllContatos foi chamado no serviço com os parâmetros corretos
        verify(service, times(1)).findAllContatos(q, fields);
    }
}