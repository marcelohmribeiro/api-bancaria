package com.banco.api_java.controllers;

import com.banco.api_java.dtos.DepositDTO;
import com.banco.api_java.dtos.UserCreateDTO;
import com.banco.api_java.dtos.UserDTO;
import com.banco.api_java.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public List<UserDTO> listarUsuarios() {
        return userService.listarUsuarios();
    }

    @GetMapping("/{id}")
    public Optional<UserDTO> buscarUserPorId(@PathVariable Long id) {
        return userService.buscarUsuarioPorID(id);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> criarUsuario(@Valid @RequestBody UserCreateDTO user) {
        UserDTO savedUser = userService.criarUsuario(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUsuario(@PathVariable Long id) {
        boolean deleted = userService.deleteUsuario(id);
        if (deleted) {
            return ResponseEntity.ok("Usuário deletado com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado!");
    }

    @PutMapping("/deposit")
    public ResponseEntity<Map<String, String>> depositarSaldo(@RequestBody DepositDTO dto) {

        BigDecimal novoSaldo = userService.depositarSaldo(dto);
        Map<String, String> body = new HashMap<>();

        if (novoSaldo != null) {
            body.put("message", "Depósito realizado com sucesso!");
            body.put("balance", novoSaldo.toString());
            return ResponseEntity.ok(body);
        }

        body.put("message", "Conta não encontrada!");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @PutMapping("/withdraw")
    public ResponseEntity<Map<String, String>> sacarSaldo(
            @RequestParam String agency,
            @RequestParam String account,
            @RequestParam double amount
    ) {
        BigDecimal novoSaldo = userService.sacarSaldo(agency, account, amount);

        Map<String, String> body = new HashMap<>();

        if (novoSaldo != null) {
            body.put("message", "Saque realizado com sucesso!");
            body.put("balance", novoSaldo.toString());
            return ResponseEntity.ok(body);
        }

        body.put("message", "Saldo insuficiente ou conta não encontrada!");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}
