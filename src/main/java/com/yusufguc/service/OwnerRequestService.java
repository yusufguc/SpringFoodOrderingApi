package com.yusufguc.service;

import com.yusufguc.dto.response.OwnerRequestResponse;
import com.yusufguc.model.User;

public interface OwnerRequestService {

    public OwnerRequestResponse createRequest();

    public OwnerRequestResponse approveRequest(Long requestId, User admin);

    public OwnerRequestResponse rejectRequest(Long requestId, User admin);



}
