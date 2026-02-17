CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    phone VARCHAR(50) NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE restaurants (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description VARCHAR(1000),
    address VARCHAR(500) NOT NULL,
    owner_id BIGINT NOT NULL,
    open BOOLEAN NOT NULL,
    rating INTEGER,

    CONSTRAINT fk_restaurant_owner
        FOREIGN KEY (owner_id)
        REFERENCES users (id)
        ON DELETE RESTRICT
);

ALTER TABLE restaurants
    ADD CONSTRAINT rating_range
    CHECK (rating IS NULL OR rating BETWEEN 1 AND 5);
