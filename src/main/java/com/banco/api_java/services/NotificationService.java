package com.banco.api_java.services;

import com.banco.api_java.models.AccountModel;
import com.banco.api_java.models.NotificationModel;
import com.banco.api_java.models.TransactionModel;
import com.banco.api_java.models.UserModel;
import com.banco.api_java.repositories.NotificationRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final DateTimeFormatter dateTimeFormatter =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void notifyIncomingTransaction(TransactionModel transaction) {
        AccountModel toAccount = transaction.getToAccount();
        AccountModel fromAccount = transaction.getFromAccount();

        UserModel destinatario = toAccount.getUser();

        String nomeRemetente = fromAccount != null && fromAccount.getUser() != null
                ? fromAccount.getUser().getName()
                : "Sistema";

        BigDecimal amount = transaction.getAmount();

        String dataHora = transaction.getCreatedAt() != null
                ? transaction.getCreatedAt().format(dateTimeFormatter)
                : "agora";

        String mensagem = String.format(
                "%s fez um %s de R$ %s para você em %s.",
                nomeRemetente,
                transaction.getType().name(),
                amount.toPlainString(),
                dataHora
        );

        NotificationModel notification = new NotificationModel();
        notification.setUser(destinatario);
        notification.setMessage(mensagem);
        notification.setIsRead(false);
        notification.setTransaction(transaction);

        notificationRepository.save(notification);
    }
}
