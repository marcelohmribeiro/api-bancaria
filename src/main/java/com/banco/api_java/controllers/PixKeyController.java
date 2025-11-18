package com.banco.api_java.controllers;

import com.banco.api_java.dtos.PixKeyRequestDTO;
import com.banco.api_java.dtos.PixKeyResponseDTO;
import com.banco.api_java.models.PixKeyModel;
import com.banco.api_java.services.PixKeyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pix-keys")
public class PixKeyController {
    private final PixKeyService pixKeyService;
    public PixKeyController(PixKeyService pixKeyService) {
        this.pixKeyService = pixKeyService;
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<PixKeyResponseDTO> criarChave(
            @PathVariable Long userId,
            @RequestBody PixKeyRequestDTO dto
    ) {
        PixKeyModel key = pixKeyService.criarChavePix(
                userId,
                dto.type(),
                dto.valueKey()
        );

        PixKeyResponseDTO resp = new PixKeyResponseDTO(
                key.getId(),
                key.getType(),
                key.getValueKey(),
                key.getAccount().getAccountNumber(),
                key.getAccount().getUser().getId()
        );

        return ResponseEntity.ok(resp);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PixKeyResponseDTO>> listarChaves(@PathVariable Long userId) {

        List<PixKeyModel> chaves = pixKeyService.listarChavesDoUsuario(userId);

        List<PixKeyResponseDTO> resp = chaves.stream()
                .map(key -> new PixKeyResponseDTO(
                        key.getId(),
                        key.getType(),
                        key.getValueKey(),
                        key.getAccount().getAccountNumber(),
                        key.getAccount().getUser().getId()
                ))
                .toList();

        return ResponseEntity.ok(resp);
    }

    @DeleteMapping("/{keyId}")
    public ResponseEntity<Void> deletar(@PathVariable Long chaveId) {
        pixKeyService.deletarChave(chaveId);
        return ResponseEntity.noContent().build();
    }
}
