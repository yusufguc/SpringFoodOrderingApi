
CREATE TABLE ratings (
                         id BIGSERIAL PRIMARY KEY,
                         user_id BIGINT NOT NULL,
                         restaurant_id BIGINT NOT NULL,
                         score INTEGER NOT NULL,
                         comment VARCHAR(500),
                         created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                         CONSTRAINT uk_user_restaurant_rating UNIQUE (user_id, restaurant_id),

                         CONSTRAINT chk_rating_score CHECK (score >= 1 AND score <= 5),

                         CONSTRAINT fk_rating_user FOREIGN KEY (user_id) REFERENCES users(id),
                         CONSTRAINT fk_rating_restaurant FOREIGN KEY (restaurant_id) REFERENCES restaurants(id)
);

CREATE INDEX idx_rating_restaurant_id ON ratings(restaurant_id);