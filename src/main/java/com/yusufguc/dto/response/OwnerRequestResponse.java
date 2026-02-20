package com.yusufguc.dto.response;

import com.yusufguc.model.enums.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OwnerRequestResponse {
    private Long id;
    private Long userId;
    private String username;
    private RequestStatus status;
}
