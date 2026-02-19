CREATE TABLE refresh_tokens (
                                id BIGSERIAL PRIMARY KEY,
                                token VARCHAR(500) NOT NULL UNIQUE,
                                expire_date TIMESTAMP NOT NULL,
                                user_id BIGINT NOT NULL,
                                CONSTRAINT fk_refresh_token_user
                                    FOREIGN KEY (user_id)
                                        REFERENCES users(id)
                                        ON DELETE CASCADE
);

