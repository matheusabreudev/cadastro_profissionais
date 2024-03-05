package com.cadastroprofissional.simples.controller;

import com.cadastroprofissional.simples.controller.openApi.ProfissionalApi;
import com.cadastroprofissional.simples.model.Profissional;
import com.cadastroprofissional.simples.model.dto.ProfissionalDTO;
import com.cadastroprofissional.simples.model.input.ProfissionalInput;
import com.cadastroprofissional.simples.service.ProfissionalService;
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
@RequestMapping("/profissional")
@AllArgsConstructor
public class ProfissionalController implements ProfissionalApi {

    private final ProfissionalService service;

    @Override
    @GetMapping
    public ResponseEntity<List<ProfissionalDTO>> findAllProfissionais(@RequestParam(required = false) String q, @RequestParam(required = false) List<String> fields) {
        List<ProfissionalDTO> profissionais = service.findAllProfissionais(q, fields);
        return ResponseEntity.status(HttpStatus.OK).body(profissionais);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ProfissionalDTO> findProfissionalById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.findProfissionalById(id).toDTO());
    }

    @Override
    @PostMapping
    public ResponseEntity<String> createProfissional(@RequestBody ProfissionalInput input) {
        Profissional prof = this.service.createProfissional(input);
        return ResponseEntity.status(HttpStatus.CREATED).body(String.format("Profissional com id %s criado com sucesso.", prof.getId().toString()));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<String> updateProfissional(@PathVariable Long id, @RequestBody ProfissionalInput input) {
        Profissional prof = this.service.updateProfissional(id, input);
        return ResponseEntity.status(HttpStatus.CREATED).body(String.format("Profissional com id %s atualizado com sucesso.", prof.getId().toString()));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProfissional(@PathVariable Long id) {
        this.service.deleteProfissional(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Profissional excluído com sucesso.");
    }
}
