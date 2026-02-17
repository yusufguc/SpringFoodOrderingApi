

CREATE TABLE carts (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    restaurant_id BIGINT NOT NULL,
    total_price NUMERIC(10,2) NOT NULL,
    CONSTRAINT fk_cart_user
        FOREIGN KEY (user_id)
        REFERENCES users (id)
        ON DELETE CASCADE,
    CONSTRAINT fk_cart_restaurant
        FOREIGN KEY (restaurant_id)
        REFERENCES restaurants (id)
        ON DELETE CASCADE
);

CREATE INDEX idx_cart_restaurant_id
    ON carts (restaurant_id);


CREATE TABLE cart_items (
    id BIGSERIAL PRIMARY KEY,
    cart_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INTEGER NOT NULL,
    price NUMERIC(10,2) NOT NULL,
    CONSTRAINT fk_cartitem_cart
        FOREIGN KEY (cart_id)
        REFERENCES carts (id)
        ON DELETE CASCADE,
    CONSTRAINT fk_cartitem_product
        FOREIGN KEY (product_id)
        REFERENCES products (id)
        ON DELETE RESTRICT
);

CREATE INDEX idx_cartitem_cart_id
    ON cart_items (cart_id);

CREATE INDEX idx_cartitem_product_id
    ON cart_items (product_id);
