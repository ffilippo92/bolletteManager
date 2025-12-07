package com.example.bollettemanager.service;

import com.example.bollettemanager.dto.AssetAccountDTO;
import com.example.bollettemanager.entity.AssetAccountEntity;
import com.example.bollettemanager.entity.UserEntity;
import com.example.bollettemanager.repository.AssetAccountRepository;
import com.example.bollettemanager.repository.UserRepository;
import com.example.bollettemanager.security.CurrentUserService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssetAccountServiceImpl implements AssetAccountService {

    private final AssetAccountRepository assetAccountRepository;
    private final UserRepository userRepository;
    private final CurrentUserService currentUserService;

    @Override
    public AssetAccountDTO createAccount(AssetAccountDTO dto) {
        Long currentUserId = currentUserService.getCurrentUserId();
        UserEntity user = userRepository
                .findById(currentUserId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + currentUserId));
        AssetAccountEntity entity = mapToEntity(dto);
        entity.setUser(user);
        AssetAccountEntity savedEntity = assetAccountRepository.save(entity);
        return mapToDto(savedEntity);
    }

    @Override
    public AssetAccountDTO updateAccount(Long id, AssetAccountDTO dto) {
        Long currentUserId = currentUserService.getCurrentUserId();
        AssetAccountEntity entity = assetAccountRepository
                .findByIdAndUserId(id, currentUserId)
                .orElseThrow(() -> new RuntimeException("Asset account not found with id: " + id));
        entity.setName(dto.getName());
        entity.setType(dto.getType());
        entity.setCurrency(dto.getCurrency());
        entity.setActive(dto.isActive());
        AssetAccountEntity updatedEntity = assetAccountRepository.save(entity);
        return mapToDto(updatedEntity);
    }

    @Override
    public void disableAccount(Long id) {
        Long currentUserId = currentUserService.getCurrentUserId();
        AssetAccountEntity entity = assetAccountRepository
                .findByIdAndUserId(id, currentUserId)
                .orElseThrow(() -> new RuntimeException("Asset account not found with id: " + id));
        entity.setActive(false);
        assetAccountRepository.save(entity);
    }

    @Override
    public AssetAccountDTO getAccountById(Long id) {
        Long currentUserId = currentUserService.getCurrentUserId();
        AssetAccountEntity entity = assetAccountRepository
                .findByIdAndUserId(id, currentUserId)
                .orElseThrow(() -> new RuntimeException("Asset account not found with id: " + id));
        return mapToDto(entity);
    }

    @Override
    public List<AssetAccountDTO> getAllAccounts() {
        Long currentUserId = currentUserService.getCurrentUserId();
        return assetAccountRepository.findByUserId(currentUserId).stream()
                .filter(AssetAccountEntity::isActive)
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private AssetAccountDTO mapToDto(AssetAccountEntity entity) {
        return AssetAccountDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .type(entity.getType())
                .currency(entity.getCurrency())
                .active(entity.isActive())
                .build();
    }

    private AssetAccountEntity mapToEntity(AssetAccountDTO dto) {
        return AssetAccountEntity.builder()
                .id(dto.getId())
                .name(dto.getName())
                .type(dto.getType())
                .currency(dto.getCurrency())
                .active(dto.isActive())
                .build();
    }
}