package com.cadastroprofissional.simples.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class MensagemUtilTest {

    @Test
    @DisplayName("Testa mensagens")
    void testMensagens() {
        assertEquals("Esse registro de contato não existe na nossa base de dados.", MensagemUtil.MSG_ENTITY_CONTATO_NOT_EXISTS);
        assertEquals("Esse registro de profissional não existe na nossa base de dados.", MensagemUtil.MSG_ENTITY_PROFISSIONAL_NOT_EXISTS);
        assertEquals("Telefone inválido, telefone precisa ter entre 10 e 11 caracteres e não pode ter caracteres especiais nem letras", MensagemUtil.MSG_TELEFONE_INVALIDO);
        assertEquals("Telefone já cadastrado na base de dados", MensagemUtil.MSG_TELEFONE_JA_CADASTRADO);
        assertEquals("O cargo não foi digitado corretamente, cargos disponiveis: Desenvolvedor, Designer, Suporte, Tester", MensagemUtil.CARGO_INVALIDO);
    }

}