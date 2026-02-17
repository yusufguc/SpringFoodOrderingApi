

CREATE TABLE orders (
                        id BIGSERIAL PRIMARY KEY,

                        user_id BIGINT NOT NULL,
                        restaurant_id BIGINT NOT NULL,

                        total_price NUMERIC(10,2) NOT NULL DEFAULT 0,

                        status VARCHAR(50) NOT NULL,

                        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP
);

ALTER TABLE orders
    ADD CONSTRAINT fk_orders_user
        FOREIGN KEY (user_id) REFERENCES users(id);

ALTER TABLE orders
    ADD CONSTRAINT fk_orders_restaurant
        FOREIGN KEY (restaurant_id) REFERENCES restaurants(id);




CREATE TABLE order_items (
                             id BIGSERIAL PRIMARY KEY,

                             order_id BIGINT NOT NULL,
                             product_id BIGINT NOT NULL,

                             quantity INTEGER NOT NULL,

                             price NUMERIC(10,2) NOT NULL
);

ALTER TABLE order_items
    ADD CONSTRAINT fk_order_items_order
        FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE;

ALTER TABLE order_items
    ADD CONSTRAINT fk_order_items_product
        FOREIGN KEY (product_id) REFERENCES products(id);
