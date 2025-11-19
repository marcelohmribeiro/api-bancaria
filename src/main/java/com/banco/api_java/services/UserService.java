package com.banco.api_java.services;

import com.banco.api_java.dtos.DepositDTO;
import com.banco.api_java.dtos.UserCreateDTO;
import com.banco.api_java.dtos.UserDTO;
import com.banco.api_java.enums.Role;
import com.banco.api_java.exceptions.EmailAlreadyInUseException;
import com.banco.api_java.models.UserModel;
import com.banco.api_java.models.AccountModel;
import com.banco.api_java.repositories.AccountRepository;
import com.banco.api_java.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.math.BigDecimal;

@Service
public class UserService {
    final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository, AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserDTO> listarUsuarios() {
        List<UserModel> usuariosListados = userRepository.findAll();
        return usuariosListados.stream().map(UserDTO::new).toList();
    }

    public Optional<UserDTO> buscarUsuarioPorID(Long id) {
        return userRepository.findById(id).map(UserDTO::new);
    }

    @Transactional
    public UserDTO criarUsuario(UserCreateDTO dto) {
        var normalizedEmail = dto.email().toLowerCase().trim();
        if (userRepository.existsByEmail(normalizedEmail)) {
            throw new EmailAlreadyInUseException("E-mail já cadastrado");
        }
        var user = new UserModel();
        user.setName(dto.name());
        user.setEmail(normalizedEmail);
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setRole(Role.USER);

        var savedUser = userRepository.save(user);

        var account = new AccountModel();
        account.setUser(savedUser);
        account.setAgencyNumber(generateAgency());
        account.setAccountNumber(generateAccountNumber());
        accountRepository.save(account);

        return new UserDTO(savedUser);
    }

    public boolean deleteUsuario(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<UserDTO> login(String email, String password) {
        return userRepository.findByEmail(email)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()))
                .map(UserDTO::new);
    }
    
    @Transactional
    public boolean depositarSaldo(DepositDTO dto) {
        var conta = accountRepository.findByAgencyNumberAndAccountNumber(
                dto.agency(),
                dto.account()
        );

        if (conta.isEmpty()) {
            return false;
        }

        var account = conta.get();
        BigDecimal novoSaldo = account.getBalance().add(BigDecimal.valueOf(dto.amount()));
        account.setBalance(novoSaldo);

        accountRepository.save(account);
        return true;
    }

    private String generateAccountNumber() {
        return String.format("%010d", Math.abs(UUID.randomUUID().hashCode()) % 1_000_000_0000L);
    }

    private String generateAgency() {
        int num = Math.abs(UUID.randomUUID().hashCode()) % 10_000;
        return String.format("%04d", num);
    }
}
