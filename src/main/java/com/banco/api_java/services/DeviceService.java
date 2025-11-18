package com.banco.api_java.services;

import com.banco.api_java.models.DeviceModel;
import com.banco.api_java.models.UserModel;
import com.banco.api_java.repositories.DeviceRepository;
import com.banco.api_java.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final UserRepository userRepository;
    public DeviceService(DeviceRepository deviceRepository, UserRepository userRepository) {
        this.deviceRepository = deviceRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void registrarAcesso(Long userId,
                                String deviceName,
                                String ip,
                                String userAgent) {

        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));

        DeviceModel device = deviceRepository
                .findByUserAndDeviceName(user, deviceName)
                .orElseGet(() -> {
                    DeviceModel novo = new DeviceModel();
                    novo.setUser(user);
                    novo.setDeviceName(deviceName);
                    novo.setIp(ip);
                    novo.setUserAgent(userAgent);
                    return novo;
                });

        device.setIp(ip);
        device.setUserAgent(userAgent);
        device.setLastAccess(LocalDateTime.now());

        deviceRepository.save(device);
    }

    public List<DeviceModel> listarDispositivosDoUsuario(Long userId) {
        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));
        return deviceRepository.findByUser(user);
    }
}
