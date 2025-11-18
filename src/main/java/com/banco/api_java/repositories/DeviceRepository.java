package com.banco.api_java.repositories;

import com.banco.api_java.models.DeviceModel;
import com.banco.api_java.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeviceRepository extends JpaRepository<DeviceModel, Long> {
    List<DeviceModel> findByUser(UserModel user);
    Optional<DeviceModel> findByUserAndDeviceName(UserModel user, String deviceName);
}
