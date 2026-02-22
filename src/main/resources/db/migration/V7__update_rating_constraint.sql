UPDATE restaurants
SET rating = NULL
WHERE rating = 0;

ALTER TABLE restaurants
DROP CONSTRAINT IF EXISTS rating_range;

ALTER TABLE restaurants
    ADD CONSTRAINT rating_range
        CHECK (rating IS NULL OR rating BETWEEN 1 AND 5);