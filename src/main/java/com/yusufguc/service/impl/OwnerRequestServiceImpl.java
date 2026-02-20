package com.yusufguc.service.impl;

import com.yusufguc.dto.response.OwnerRequestResponse;
import com.yusufguc.exception.base.BaseException;
import com.yusufguc.exception.message.ErrorMessage;
import com.yusufguc.exception.message.MessageType;
import com.yusufguc.mapper.OwnerRequestMapper;
import com.yusufguc.model.OwnerRequest;
import com.yusufguc.model.User;
import com.yusufguc.model.enums.RequestStatus;
import com.yusufguc.model.enums.Role;
import com.yusufguc.repository.OwnerRequestRepository;
import com.yusufguc.repository.UserRepository;
import com.yusufguc.security.CurrentUserService;
import com.yusufguc.service.OwnerRequestService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class OwnerRequestServiceImpl implements OwnerRequestService {

    private final OwnerRequestRepository ownerRequestRepository;
    private final OwnerRequestMapper ownerRequestMapper;
    private final CurrentUserService currentUserService;
    private final UserRepository userRepository;

    @Override
    public OwnerRequestResponse createRequest() {

        User user = currentUserService.getCurrentUser();

        if (user.getRole()== Role.RESTAURANT_OWNER){
            throw  new BaseException(new ErrorMessage(MessageType.ALREADY_RESTAURANT_OWNER,null));
        }

        boolean exists = ownerRequestRepository
                .existsByUserAndStatus(user, RequestStatus.PENDING);

        if (exists){
            throw  new BaseException(new ErrorMessage(MessageType.PENDING_OWNER_REQUEST_EXISTS,null));
        }

        OwnerRequest ownerRequest = OwnerRequest.builder()
                .user(user)
                .status(RequestStatus.PENDING)
                .build();
        OwnerRequest saved = ownerRequestRepository.save(ownerRequest);
        return ownerRequestMapper.toResponse(saved);
    }

    @Override
    public OwnerRequestResponse approveRequest(Long requestId, User admin) {
        OwnerRequest ownerRequest = ownerRequestRepository.findById(requestId)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.REQUEST_NOT_FOUND, requestId.toString())));

        if (ownerRequest.getStatus() !=RequestStatus.PENDING){
            throw  new BaseException(new ErrorMessage(MessageType.REQUEST_ALREADY_PROCESSED,ownerRequest.getStatus().toString()));
        }

        ownerRequest.setStatus(RequestStatus.APPROVED);
        ownerRequest.setApprovedBy(admin);

        User user = ownerRequest.getUser();
        user.setRole(Role.RESTAURANT_OWNER);
        userRepository.save(user);

        OwnerRequest saved = ownerRequestRepository.save(ownerRequest);
        return ownerRequestMapper.toResponse(saved) ;
    }

    @Override
    public OwnerRequestResponse rejectRequest(Long requestId, User admin) {
        OwnerRequest ownerRequest = ownerRequestRepository.findById(requestId)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.REQUEST_NOT_FOUND, requestId.toString())));

        if (ownerRequest.getStatus() !=RequestStatus.PENDING){
            throw  new BaseException(new ErrorMessage(MessageType.REQUEST_ALREADY_PROCESSED,ownerRequest.getStatus().toString()));
        }

        ownerRequest.setStatus(RequestStatus.REJECTED);
        ownerRequest.setApprovedBy(admin);
        OwnerRequest saved = ownerRequestRepository.save(ownerRequest);
        return ownerRequestMapper.toResponse(saved) ;
    }
}

