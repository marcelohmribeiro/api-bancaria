package com.banco.api_java.repositories;

import com.banco.api_java.models.NotificationModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<NotificationModel, Long> {

}
