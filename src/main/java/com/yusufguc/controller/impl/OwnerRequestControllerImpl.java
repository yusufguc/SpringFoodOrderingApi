package com.yusufguc.controller.impl;

import com.yusufguc.controller.OwnerRequestController;
import com.yusufguc.dto.response.OwnerRequestResponse;
import com.yusufguc.model.User;
import com.yusufguc.security.CurrentUserService;
import com.yusufguc.service.OwnerRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/owner-requests")
@RequiredArgsConstructor
public class OwnerRequestControllerImpl implements OwnerRequestController {

    private final OwnerRequestService ownerRequestService;
    private final CurrentUserService currentUserService;

    @PostMapping()
    @PreAuthorize("hasRole('USER')")
    @Override
    public ResponseEntity<OwnerRequestResponse> createRequest() {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ownerRequestService.createRequest());
    }

    @PutMapping("/approve/{requestId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public ResponseEntity<OwnerRequestResponse> approveRequest(
            @PathVariable Long requestId) {

        User admin = currentUserService.getCurrentUser();

        return ResponseEntity.ok(ownerRequestService.approveRequest(requestId,admin));
    }

    @PutMapping("/reject/{requestId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public ResponseEntity<OwnerRequestResponse> rejectRequest(
            @PathVariable Long requestId) {

        User admin = currentUserService.getCurrentUser();

        return ResponseEntity.ok(ownerRequestService.rejectRequest(requestId,admin));
    }
}
