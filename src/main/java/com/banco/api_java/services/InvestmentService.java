package com.banco.api_java.services;

import com.banco.api_java.models.AccountModel;
import com.banco.api_java.models.InvestmentModel;
import com.banco.api_java.repositories.AccountRepository;
import com.banco.api_java.repositories.InvestmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class InvestmentService {

    private final InvestmentRepository investmentRepository;
    private final AccountRepository accountRepository;

    public InvestmentService(InvestmentRepository investmentRepository,
                             AccountRepository accountRepository) {
        this.investmentRepository = investmentRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public InvestmentModel criarInvestimento(Long userId,
                                             BigDecimal amount,
                                             BigDecimal monthlyRate,
                                             String type) {

        if (amount == null || amount.signum() <= 0) {
            throw new IllegalArgumentException("Valor do investimento deve ser maior que zero.");
        }

        AccountModel account = accountRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Conta do usuário não encontrada."));

        if (account.getBalance().compareTo(amount) < 0) {
            throw new IllegalStateException("Saldo insuficiente para investir.");
        }

        // debita da conta
        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);

        InvestmentModel inv = new InvestmentModel();
        inv.setAccount(account);
        inv.setAmount(amount);                 // valor aplicado
        inv.setCurrentAmount(amount);          // começa igual
        inv.setMonthlyRate(monthlyRate);       // ex: 0.02 = 2% a.m.
        inv.setType(type);
        inv.setStatus("ACTIVE");
        inv.setLastYieldDate(LocalDateTime.now());

        return investmentRepository.save(inv);
    }

    // Rendimento do dinheiro
    @Transactional
    public InvestmentModel aplicarRendimento(InvestmentModel inv) {
        LocalDateTime agora = LocalDateTime.now();

        long dias = Duration.between(inv.getLastYieldDate(), agora).toDays();
        long meses = dias / 30;

        if (meses <= 0) {
            return inv; // nada pra atualizar
        }

        BigDecimal atual = inv.getCurrentAmount();
        BigDecimal taxaMensal = inv.getMonthlyRate(); // ex: 0.02

        for (int i = 0; i < meses; i++) {
            atual = atual.multiply(BigDecimal.ONE.add(taxaMensal));
        }

        inv.setCurrentAmount(atual);
        inv.setLastYieldDate(inv.getLastYieldDate().plusDays(meses * 30));

        return investmentRepository.save(inv);
    }

    // Lista os investimentos do usúario
    public List<InvestmentModel> listarPorUsuario(Long userId) {
        List<InvestmentModel> lista = investmentRepository.findByAccountUserId(userId);

        return lista.stream()
                .map(this::aplicarRendimento)
                .toList();
    }

    // Resgata o investimento
    @Transactional
    public InvestmentModel resgatar(Long investmentId) {

        InvestmentModel inv = investmentRepository.findById(investmentId)
                .orElseThrow(() -> new IllegalArgumentException("Investimento não encontrado."));

        if (!"ACTIVE".equalsIgnoreCase(inv.getStatus())) {
            throw new IllegalStateException("Investimento já foi resgatado ou não está ativo.");
        }

        inv = aplicarRendimento(inv);

        AccountModel conta = inv.getAccount();
        conta.setBalance(conta.getBalance().add(inv.getCurrentAmount()));
        accountRepository.save(conta);

        inv.setStatus("REDEEMED");

        return investmentRepository.save(inv);
    }
}