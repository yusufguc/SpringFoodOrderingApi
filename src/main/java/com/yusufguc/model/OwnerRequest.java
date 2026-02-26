package com.yusufguc.model;

import com.yusufguc.model.enums.RequestStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

/**
 * Represents a user's request to become a restaurant owner.
 * Tracks the approval lifecycle from submission to final decision.
 */
@Entity
@Table(name = "owner_request")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OwnerRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The user who submitted the request.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private  User user;

    /**
     * Current status of the request (e.g., PENDING, APPROVED, REJECTED).
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestStatus status;

    /**
     * The admin user who processed and approved/rejected the request.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approved_by")
    private User approvedBy;

    /**
     * Timestamp when the request was initially created.
     */
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
