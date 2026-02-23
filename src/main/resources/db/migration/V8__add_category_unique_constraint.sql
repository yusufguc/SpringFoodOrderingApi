ALTER TABLE categories
    ADD CONSTRAINT uk_category_name_restaurant
        UNIQUE (name, restaurant_id);