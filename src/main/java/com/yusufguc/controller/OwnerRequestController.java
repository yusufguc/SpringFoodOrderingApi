package com.yusufguc.controller;

import com.yusufguc.dto.response.OwnerRequestResponse;
import org.springframework.http.ResponseEntity;

public interface OwnerRequestController {

    public ResponseEntity<OwnerRequestResponse> createRequest();

    public ResponseEntity<OwnerRequestResponse> approveRequest(Long requestId);

    public ResponseEntity<OwnerRequestResponse> rejectRequest(Long requestId);



}
