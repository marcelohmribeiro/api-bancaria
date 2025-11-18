package com.banco.api_java.services;

import com.banco.api_java.models.AccountModel;
import com.banco.api_java.models.PixKeyModel;
import com.banco.api_java.repositories.AccountRepository;
import com.banco.api_java.repositories.PixKeyRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PixKeyService {

    private final PixKeyRepository pixKeyRepository;
    private final AccountRepository accountRepository;

    public PixKeyService(PixKeyRepository pixKeyRepository,
                         AccountRepository accountRepository) {
        this.pixKeyRepository = pixKeyRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public PixKeyModel criarChavePix(Long userId,
                                     String type,
                                     String valueKey) {

        if (valueKey == null || valueKey.isBlank()) {
            throw new IllegalArgumentException("Valor da chave PIX não pode ser vazio.");
        }

        // verifica se já existe a mesma chave para qualquer conta
        pixKeyRepository.findByValueKey(valueKey).ifPresent(k -> {
            throw new IllegalStateException("Chave PIX já está cadastrada.");
        });

        AccountModel account = accountRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Conta do usuário não encontrada."));

        PixKeyModel pixKey = new PixKeyModel();
        pixKey.setAccount(account);
        pixKey.setType(type.toUpperCase()); // CPF, EMAIL, TELEFONE, ALEATORIA
        pixKey.setValueKey(valueKey);

        return pixKeyRepository.save(pixKey);
    }

    public List<PixKeyModel> listarChavesDoUsuario(Long userId) {
        return pixKeyRepository.findByAccountUserId(userId);
    }

    @Transactional
    public void deletarChave(Long keyId) {
        if (!pixKeyRepository.existsById(keyId)) {
            throw new IllegalArgumentException("Chave PIX não encontrada.");
        }
        pixKeyRepository.deleteById(keyId);
    }
}
