package com.cadastroprofissional.simples.service;

import com.cadastroprofissional.simples.model.Profissional;
import com.cadastroprofissional.simples.model.dto.ProfissionalDTO;
import com.cadastroprofissional.simples.model.input.ProfissionalInput;
import com.cadastroprofissional.simples.repository.ProfissionalRepository;
import com.cadastroprofissional.simples.util.MensagemUtil;
import com.cadastroprofissional.simples.util.enums.CargoEnum;
import com.cadastroprofissional.simples.util.exception.EntidadeNaoExistenteException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ProfissionalServiceTest {

    private ProfissionalRepository repository;
    private ProfissionalService service;


    @BeforeEach
    void setUp() {
        repository = mock(ProfissionalRepository.class);
        service = new ProfissionalService(repository);
    }

    @Test
    @DisplayName("Testa se encontra um profissional pelo ID")
    void testFindProfissionalById() {
        Long profissionalId = 1L;
        Profissional profissional = new Profissional();
        profissional.setId(profissionalId);

        when(repository.findProfissionalByIdAndAtivoIsTrue(profissionalId)).thenReturn(java.util.Optional.of(profissional));

        Profissional result = service.findProfissionalById(profissionalId);

        assertEquals(profissional, result);
    }

    @Test
    @DisplayName("Testa jogar a exceção de entidade não encontrada quando buscar pelo ID")
    void testFindProfissionalByIdNotFound() {
        Long profissionalId = 1L;

        when(repository.findProfissionalByIdAndAtivoIsTrue(profissionalId)).thenReturn(java.util.Optional.empty());

        EntidadeNaoExistenteException exception = assertThrows(EntidadeNaoExistenteException.class, () -> {
            service.findProfissionalById(profissionalId);
        });
        assertEquals(MensagemUtil.MSG_ENTITY_PROFISSIONAL_NOT_EXISTS, exception.getMessage());
    }

    @Test
    @DisplayName("Testa listar os profissionais se fields for um array vazio")
    void testFindAllProfissionaisNoFields() {
        String q = "teste";
        List<String> fields = Collections.emptyList();
        Profissional profissional = new Profissional();
        profissional.setId(1L);
        profissional.setNome("Teste");
        profissional.setCargo(CargoEnum.DESENVOLVEDOR);
        profissional.setDataNascimento(LocalDate.of(1990, 1, 1));
        profissional.setCreatedDate(LocalDate.now());
        List<Profissional> profissionais = Arrays.asList(profissional);

        when(repository.findByAnyColumn(q)).thenReturn(profissionais);

        List<ProfissionalDTO> result = service.findAllProfissionais(q, fields);

        assertEquals(1, result.size());
        assertEquals(profissional.getId(), result.get(0).getId());
        assertEquals(profissional.getNome(), result.get(0).getNome());
        assertEquals(profissional.getCargo(), CargoEnum.DESENVOLVEDOR);
        assertEquals(profissional.getDataNascimento(), result.get(0).getDataNascimento());
        assertEquals(profissional.getCreatedDate(), result.get(0).getCreatedDate());
    }

    @Test
    @DisplayName("Testa criar um profissional")
    void testCreateProfissional() {
        ProfissionalInput input = new ProfissionalInput();
        input.setNome("Teste");
        input.setCargo("Desenvolvedor");
        input.setDataNascimento(LocalDate.of(1990, 1, 1));

        Profissional profissionalCriado = new Profissional();
        profissionalCriado.setId(1L);
        profissionalCriado.setNome(input.getNome());
        profissionalCriado.setCargo(CargoEnum.DESENVOLVEDOR);
        profissionalCriado.setDataNascimento(input.getDataNascimento());
        profissionalCriado.setCreatedDate(LocalDate.now());

        when(repository.save(any(Profissional.class))).thenReturn(profissionalCriado);

        Profissional profissionalCriadoNoMetodo = service.createProfissional(input);

        assertNotNull(profissionalCriadoNoMetodo);
        assertEquals(input.getNome(), profissionalCriadoNoMetodo.getNome());
        assertEquals(CargoEnum.DESENVOLVEDOR, profissionalCriadoNoMetodo.getCargo());
        assertEquals(input.getDataNascimento(), profissionalCriadoNoMetodo.getDataNascimento());
        assertNotNull(profissionalCriadoNoMetodo.getCreatedDate());
        assertEquals(LocalDate.now(), profissionalCriadoNoMetodo.getCreatedDate());
        verify(repository, times(1)).save(any(Profissional.class));
    }

    @Test
    @DisplayName("Testa atualizar um profissional")
    void testUpdateProfissional() {
        ProfissionalInput input = new ProfissionalInput();

        Long profissionalId = 1L;

        input.setNome("Novo Nome");
        input.setCargo("Desenvolvedor");
        input.setDataNascimento(LocalDate.of(1990, 1, 1));

        Profissional profissionalExistente = new Profissional();
        profissionalExistente.setId(1L);
        profissionalExistente.setNome("Nome Antigo");
        profissionalExistente.setCargo(CargoEnum.TESTER);
        profissionalExistente.setAtivo(true);
        profissionalExistente.setDataNascimento(LocalDate.of(1980, 1, 1));

        when(repository.findProfissionalByIdAndAtivoIsTrue(1L)).thenReturn(Optional.of(profissionalExistente));
        when(repository.save(profissionalExistente)).thenReturn(profissionalExistente);

        Profissional profissionalAtualizado = service.updateProfissional(profissionalId, input);

        assertNotNull(profissionalAtualizado);
        assertEquals(profissionalId, profissionalAtualizado.getId());
        assertEquals(input.getNome(), profissionalAtualizado.getNome());
        assertEquals(CargoEnum.DESENVOLVEDOR, profissionalAtualizado.getCargo());
        assertEquals(input.getDataNascimento(), profissionalAtualizado.getDataNascimento());

        verify(repository, times(1)).save(profissionalAtualizado);
    }

    @Test
    @DisplayName("Testa deletar um profissional")
    void testDeleteProfissional() {
        Long profissionalId = 1L;

        Profissional profissionalExistente = new Profissional();
        profissionalExistente.setId(profissionalId);
        profissionalExistente.setNome("Teste");
        profissionalExistente.setCargo(CargoEnum.DESENVOLVEDOR);
        profissionalExistente.setDataNascimento(LocalDate.of(1990, 1, 1));
        profissionalExistente.setCreatedDate(LocalDate.now());
        profissionalExistente.setAtivo(true);

        when(repository.findProfissionalByIdAndAtivoIsTrue(profissionalId)).thenReturn(Optional.of(profissionalExistente));

        service.deleteProfissional(profissionalId);

        verify(repository, times(1)).save(profissionalExistente);
    }
}