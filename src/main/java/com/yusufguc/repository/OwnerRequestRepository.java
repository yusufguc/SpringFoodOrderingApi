package com.yusufguc.repository;

import com.yusufguc.model.OwnerRequest;
import com.yusufguc.model.User;
import com.yusufguc.model.enums.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRequestRepository extends JpaRepository<OwnerRequest, Long> {

    boolean existsByUserAndStatus(User user, RequestStatus status);
}