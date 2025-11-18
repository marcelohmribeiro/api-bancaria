package com.banco.api_java.services;

import com.banco.api_java.models.AddressModel;
import com.banco.api_java.models.UserModel;
import com.banco.api_java.repositories.AddressRepository;
import com.banco.api_java.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    public AddressService(AddressRepository addressRepository,
                           UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public AddressModel adicionarEndereco(Long userId, String rua, String numero, String bairro, String cidade, String estado, String cep) {
        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        AddressModel address = new AddressModel();
        address.setUser(user);
        address.setRua(rua);
        address.setNumero(numero);
        address.setBairro(bairro);
        address.setCidade(cidade);
        address.setEstado(estado);
        address.setCep(cep);

        return addressRepository.save(address);
    }

    public List<AddressModel> listarEnderecos(Long userId) {
        return addressRepository.findByUserId(userId);
    }

    @Transactional
    public void deletarEndereco(Long addressId) {
        addressRepository.deleteById(addressId);
    }
}
