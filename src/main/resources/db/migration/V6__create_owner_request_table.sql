CREATE TABLE owner_request (
                               id BIGSERIAL PRIMARY KEY,
                               user_id BIGINT NOT NULL,
                               status VARCHAR(50) NOT NULL,
                               approved_by BIGINT,
                               created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                               CONSTRAINT fk_owner_request_user
                                   FOREIGN KEY (user_id)
                                       REFERENCES users(id)
                                       ON DELETE CASCADE,

                               CONSTRAINT fk_owner_request_approved_by
                                   FOREIGN KEY (approved_by)
                                       REFERENCES users(id)
                                       ON DELETE SET NULL
);