package com.banco.api_java.controllers;

import com.banco.api_java.dtos.AddressRequestDTO;
import com.banco.api_java.dtos.AddressResponseDTO;
import com.banco.api_java.services.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<AddressResponseDTO> adicionar(
            @PathVariable Long userId,
            @RequestBody AddressRequestDTO dto
    ) {
        var address = addressService.adicionarEndereco(
                userId,
                dto.rua(),
                dto.numero(),
                dto.bairro(),
                dto.cidade(),
                dto.estado(),
                dto.cep()
        );

        return ResponseEntity.ok(new AddressResponseDTO(
                address.getId(),
                address.getRua(),
                address.getNumero(),
                address.getBairro(),
                address.getCidade(),
                address.getEstado(),
                address.getCep(),
                address.getUser().getId()
        ));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AddressResponseDTO>> listar(
            @PathVariable Long userId
    ) {
        var list = addressService.listarEnderecos(userId)
                .stream()
                .map(address -> new AddressResponseDTO(
                        address.getId(),
                        address.getRua(),
                        address.getNumero(),
                        address.getBairro(),
                        address.getCidade(),
                        address.getEstado(),
                        address.getCep(),
                        address.getUser().getId()
                ))
                .toList();

        return ResponseEntity.ok(list);
    }
}
