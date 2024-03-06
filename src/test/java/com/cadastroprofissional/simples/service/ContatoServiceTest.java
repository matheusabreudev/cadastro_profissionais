package com.cadastroprofissional.simples.service;

import com.cadastroprofissional.simples.model.Contato;
import com.cadastroprofissional.simples.model.Profissional;
import com.cadastroprofissional.simples.model.dto.ContatoDTO;
import com.cadastroprofissional.simples.model.input.ContatoInput;
import com.cadastroprofissional.simples.model.input.ContatoUpdateInput;
import com.cadastroprofissional.simples.repository.ContatoRepository;
import com.cadastroprofissional.simples.util.exception.EntidadeNaoExistenteException;
import com.cadastroprofissional.simples.util.exception.TelefoneInvalidoException;
import com.cadastroprofissional.simples.util.exception.TelefoneJaCadastradoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ContatoServiceTest {

    private ContatoRepository repository;
    private ProfissionalService profissionalService;
    private ContatoService contatoService;

    @BeforeEach
    void setUp() {
        repository = mock(ContatoRepository.class);
        profissionalService = mock(ProfissionalService.class);
        contatoService = new ContatoService(repository, profissionalService);
    }

    @Test
    @DisplayName("Testa se encontra um contato pelo ID")
    void testFindContatoById_WhenExists() {
        Long contatoId = 1L;
        Contato expectedContato = new Contato();
        expectedContato.setId(contatoId);
        when(repository.findContatoByIdAndProfissionalAtivoIsTrue(contatoId)).thenReturn(Optional.of(expectedContato));

        Contato actualContato = contatoService.findContatoById(contatoId);

        assertNotNull(actualContato);
        assertEquals(contatoId, actualContato.getId());
    }

    @Test
    @DisplayName("Testa se joga a exceção quando não encontrado o contato")
    void testFindContatoById_WhenNotExists() {
        Long contatoId = 1L;
        when(repository.findContatoByIdAndProfissionalAtivoIsTrue(contatoId)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoExistenteException.class, () -> {
            contatoService.findContatoById(contatoId);
        });
    }

    @Test
    @DisplayName("Testa retornar todos os campos quando fields é null")
    void testFindAllContatos_WhenFieldsNull() {
        String q = "search";
        List<String> fields = null;
        List<Contato> contatosFiltrados = new ArrayList<>();
        contatosFiltrados.add(createContato(1L, "Nome 1", "Contato 1", LocalDate.now()));
        when(repository.findByAnyColumn(q)).thenReturn(contatosFiltrados);

        List<ContatoDTO> result = contatoService.findAllContatos(q, fields);

        assertEquals(1, result.size());
        assertEquals("Nome 1", result.get(0).getNome());
        assertEquals("Contato 1", result.get(0).getContato());
    }

    @Test
    @DisplayName("Testa retornar todos os campos quando fields é um array vazio")
    void testFindAllContatos_WhenFieldsEmpty() {
        String q = "search";
        List<String> fields = new ArrayList<>();
        List<Contato> contatosFiltrados = new ArrayList<>();
        contatosFiltrados.add(createContato(1L, "Nome 1", "Contato 1", LocalDate.now()));
        when(repository.findByAnyColumn(q)).thenReturn(contatosFiltrados);

        List<ContatoDTO> result = contatoService.findAllContatos(q, fields);

        assertEquals(1, result.size());
        assertEquals("Nome 1", result.get(0).getNome());
        assertEquals("Contato 1", result.get(0).getContato());
    }

    @Test
    @DisplayName("Testa criar contato")
    void testCreateContato() throws Exception {
        String telefone = "1234567890";
        ContatoInput input = new ContatoInput("Nome", telefone, 1L);

        Method validaTelefoneExistenteMethod = ContatoService.class.getDeclaredMethod("validaTelefoneExistente", String.class);
        validaTelefoneExistenteMethod.setAccessible(true);
        when(validaTelefoneExistenteMethod.invoke(contatoService, telefone)).thenReturn(false);

        Contato contatoSalvo = new Contato(1L, "Nome", telefone, LocalDate.now(), null);
        when(repository.save(any(Contato.class))).thenReturn(contatoSalvo);

        Contato contatoCriado = contatoService.createContato(input);

        assertNotNull(contatoCriado);
        assertEquals("Nome", contatoCriado.getNome());
        assertEquals(telefone, contatoCriado.getContato());
        assertEquals(LocalDate.now(), contatoCriado.getCreatedDate());

        verify(repository, times(1)).save(any(Contato.class));
    }

    @Test
    @DisplayName("Testa jogar a exceção de telefone inválido quando criar um contato")
    void testCreateContatoWithInvalidPhone() {
        ContatoInput input = new ContatoInput();
        input.setNome("Teste");
        input.setContato("123");

        assertThrows(TelefoneInvalidoException.class, () -> {
            contatoService.createContato(input);
        });
        verify(repository, never()).save(any(Contato.class));
    }

    @Test
    @DisplayName("Testa jogar a exceção de telefone já existente quando criar um contato")
    void testCreateContatoWithExistingPhone() {
        ContatoInput input = new ContatoInput();
        input.setNome("Teste");
        input.setContato("1234567890");
        input.setProfissional(1L);

        when(profissionalService.findProfissionalById(input.getProfissional())).thenReturn(new Profissional());
        when(repository.existsContatoByContato(input.getContato())).thenReturn(true);

        assertThrows(TelefoneJaCadastradoException.class, () -> {
            contatoService.createContato(input);
        });
        verify(repository, never()).save(any(Contato.class));
    }

    @Test
    @DisplayName("Testa jogar a exceção de telefone inválido ao atualizar contato")
    void testUpdateContatoWithInvalidPhone() {
        Long contatoId = 1L;
        ContatoUpdateInput input = new ContatoUpdateInput();
        input.setContato("123"); // Número de telefone inválido

        when(repository.findContatoByIdAndProfissionalAtivoIsTrue(anyLong())).thenReturn(Optional.of(new Contato())); // Simula a existência do contato

        assertThrows(TelefoneInvalidoException.class, () -> {
            contatoService.updateContato(contatoId, input);
        });
        verify(repository, never()).save(any(Contato.class));
    }

    @Test
    @DisplayName("Testa atualizar contato")
    void testUpdateContato() {
        Long contatoId = 1L;
        ContatoUpdateInput input = new ContatoUpdateInput();
        input.setContato("1234567890");

        Contato contatoExistente = new Contato(1L, "teste", "1234567890", LocalDate.now(), new Profissional());
        when(repository.findContatoByIdAndProfissionalAtivoIsTrue(anyLong())).thenReturn(Optional.of(contatoExistente));
        when(repository.save(contatoExistente)).thenReturn(new Contato());

        Contato updatedContato = contatoService.updateContato(contatoId, input);

        assertNotNull(updatedContato);
        assertEquals(input.getContato(), contatoExistente.getContato());
        verify(repository, times(1)).save(contatoExistente);
    }

    @Test
    @DisplayName("Testa deletar contato")
    void testDeleteContato() {
        Long contatoId = 1L;

        Contato contatoExistente = new Contato();
        when(repository.findContatoByIdAndProfissionalAtivoIsTrue(anyLong())).thenReturn(Optional.of(contatoExistente));

        contatoService.deleteContato(contatoId);

        verify(repository, times(1)).findContatoByIdAndProfissionalAtivoIsTrue(contatoId);
        verify(repository, times(1)).deleteById(contatoId);
    }

    @Test
    @DisplayName("Testa jogar a exceção de entidade não encontrada ao deletar contato")
    void testDeleteContatoWithNonExistentId() {
        Long contatoId = 1L;

        when(repository.findContatoByIdAndProfissionalAtivoIsTrue(anyLong())).thenReturn(Optional.empty()); // Simula que o contato não existe

        assertThrows(EntidadeNaoExistenteException.class, () -> {
            contatoService.deleteContato(contatoId);
        });
        verify(repository, never()).deleteById(anyLong());
    }

    private Contato createContato(Long id, String nome, String contato, LocalDate createdDate) {
        Contato contatoObj = new Contato();
        contatoObj.setId(id);
        contatoObj.setNome(nome);
        contatoObj.setContato(contato);
        contatoObj.setCreatedDate(createdDate);
        return contatoObj;
    }
}