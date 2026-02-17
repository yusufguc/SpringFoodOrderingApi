
CREATE TABLE categories (
                            id BIGSERIAL PRIMARY KEY,
                            name VARCHAR(255) NOT NULL,
                            restaurant_id BIGINT NOT NULL,
                            CONSTRAINT fk_category_restaurant
                                FOREIGN KEY (restaurant_id)
                                    REFERENCES restaurants (id)
                                    ON DELETE CASCADE
);

CREATE INDEX idx_category_restaurant_id
    ON categories (restaurant_id);



CREATE TABLE products (
                          id BIGSERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          description VARCHAR(250),
                          price NUMERIC(10,2) NOT NULL,
                          stock INTEGER NOT NULL,
                          category_id BIGINT NOT NULL,
                          active BOOLEAN NOT NULL,
                          CONSTRAINT fk_product_category
                              FOREIGN KEY (category_id)
                                  REFERENCES categories (id)
                                  ON DELETE RESTRICT
);

CREATE INDEX idx_product_category_id
    ON products (category_id);
