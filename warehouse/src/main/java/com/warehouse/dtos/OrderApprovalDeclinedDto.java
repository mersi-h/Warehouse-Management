package com.warehouse.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderApprovalDeclinedDto {
    private Long orderId;
    private boolean approve;
    private String declineReason;
}
