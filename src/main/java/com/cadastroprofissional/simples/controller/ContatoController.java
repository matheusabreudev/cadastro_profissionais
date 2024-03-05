package com.cadastroprofissional.simples.controller;

import com.cadastroprofissional.simples.controller.openApi.ContatoApi;
import com.cadastroprofissional.simples.model.Contato;
import com.cadastroprofissional.simples.model.dto.ContatoDTO;
import com.cadastroprofissional.simples.model.input.ContatoInput;
import com.cadastroprofissional.simples.model.input.ContatoUpdateInput;
import com.cadastroprofissional.simples.service.ContatoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/contato")
@AllArgsConstructor
public class ContatoController implements ContatoApi {

    private final ContatoService service;

    @Override
    @GetMapping
    public ResponseEntity<List<ContatoDTO>> findAllContatos(@RequestParam(required = false) String q, @RequestParam(required = false) List<String>fields) {
        List<ContatoDTO> contatos = service.findAllContatos(q, fields);
        return ResponseEntity.status(HttpStatus.OK).body(contatos);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ContatoDTO> findContatoById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.findContatoById(id).toDto());
    }

    @Override
    @PostMapping
    public ResponseEntity<String> createContato(@RequestBody ContatoInput input) {
        Contato cont = this.service.createContato(input);
        return ResponseEntity.status(HttpStatus.CREATED).body(String.format("Contato com id %s criado com sucesso.", cont.getId().toString()));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<String> updateContato(@PathVariable Long id, @RequestBody ContatoUpdateInput input) {
        Contato cont = this.service.updateContato(id, input);
        return ResponseEntity.status(HttpStatus.CREATED).body(String.format("Contato com id %s atualizado com sucesso.", cont.getId().toString()));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteContato(@PathVariable Long id) {
        this.service.deleteContato(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Contato excluído com sucesso.");
    }
}
