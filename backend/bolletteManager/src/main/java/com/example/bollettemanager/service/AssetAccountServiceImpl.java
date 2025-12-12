package com.example.bollettemanager.service;

import com.example.bollettemanager.dto.AssetAccountDto;
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
  public AssetAccountDto createAccount(AssetAccountDto dto) {
    Long currentUserId = currentUserService.getCurrentUserId();
    UserEntity user =
        userRepository
            .findById(currentUserId)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + currentUserId));
    AssetAccountEntity entity = mapToEntity(dto);
    entity.setUser(user);
    AssetAccountEntity savedEntity = assetAccountRepository.save(entity);
    return mapToDto(savedEntity);
  }

  @Override
  public AssetAccountDto updateAccount(Long id, AssetAccountDto dto) {
    Long currentUserId = currentUserService.getCurrentUserId();
    AssetAccountEntity entity =
        assetAccountRepository
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
    AssetAccountEntity entity =
        assetAccountRepository
            .findByIdAndUserId(id, currentUserId)
            .orElseThrow(() -> new RuntimeException("Asset account not found with id: " + id));
    entity.setActive(false);
    assetAccountRepository.save(entity);
  }

  @Override
  public AssetAccountDto getAccountById(Long id) {
    Long currentUserId = currentUserService.getCurrentUserId();
    AssetAccountEntity entity =
        assetAccountRepository
            .findByIdAndUserId(id, currentUserId)
            .orElseThrow(() -> new RuntimeException("Asset account not found with id: " + id));
    return mapToDto(entity);
  }

  @Override
  public List<AssetAccountDto> getAllAccounts() {
    Long currentUserId = currentUserService.getCurrentUserId();
    return assetAccountRepository.findByUserId(currentUserId).stream()
        .filter(AssetAccountEntity::isActive)
        .map(this::mapToDto)
        .collect(Collectors.toList());
  }

  private AssetAccountDto mapToDto(AssetAccountEntity entity) {
    return AssetAccountDto.builder()
        .id(entity.getId())
        .name(entity.getName())
        .type(entity.getType())
        .currency(entity.getCurrency())
        .active(entity.isActive())
        .build();
  }

  private AssetAccountEntity mapToEntity(AssetAccountDto dto) {
    return AssetAccountEntity.builder()
        .id(dto.getId())
        .name(dto.getName())
        .type(dto.getType())
        .currency(dto.getCurrency())
        .active(dto.isActive())
        .build();
  }
}
