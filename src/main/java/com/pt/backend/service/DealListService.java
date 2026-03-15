package com.pt.backend.service;

import com.pt.backend.domain.DealList;
import com.pt.backend.domain.User;
import com.pt.backend.dto.dealList.CreateDealListRequest;
import com.pt.backend.dto.dealList.DealListView;
import com.pt.backend.dto.dealList.UpdateDealListRequest;
import com.pt.backend.repository.DealListRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class DealListService {

    private final DealListRepository dealListRepository;

    public DealListService(DealListRepository dealListRepository) {
        this.dealListRepository = dealListRepository;
    }

    public DealListView create(CreateDealListRequest request, User currentUser) {
        if (dealListRepository.existsByNameAndUserId(request.name(), currentUser.getId())) {
            throw new IllegalStateException("Deal list already exists for this user");
        }

        DealList dealList = DealList.builder()
                .name(request.name())
                .user(currentUser)
                .build();

        DealList saved = dealListRepository.save(dealList);
        return toView(saved);
    }

    public DealListView getById(Long id, User currentUser) {
        DealList dealList = dealListRepository
                .findByIdAndUserId(id, currentUser.getId())
                .orElseThrow(() -> new EntityNotFoundException("Deal list not found"));

        return toView(dealList);
    }

    public List<DealListView> getAll(User currentUser) {
        return dealListRepository
                .findAllByUserId(currentUser.getId())
                .stream()
                .map(this::toView)
                .toList();
    }

    public DealListView update(Long id, UpdateDealListRequest request, User currentUser) {
        DealList dealList = dealListRepository
                .findByIdAndUserId(id, currentUser.getId())
                .orElseThrow(() -> new EntityNotFoundException("Deal list not found"));

        dealList.setName(request.name());
        return toView(dealList);
    }

    public void delete(Long id, User currentUser) {
        DealList dealList = dealListRepository
                .findByIdAndUserId(id, currentUser.getId())
                .orElseThrow(() -> new EntityNotFoundException("Deal list not found"));

        dealListRepository.delete(dealList);
    }

    private DealListView toView(DealList dealList) {
        return new DealListView(dealList.getId(), dealList.getName());
    }

}