-- DROP TABLE IF EXISTS collection_image CASCADE;
-- DROP TABLE IF EXISTS image CASCADE;
-- DROP TABLE IF EXISTS collection CASCADE;

CREATE TABLE IF NOT EXISTS collection (
    id SERIAL PRIMARY KEY,
    category VARCHAR(255),
    description TEXT
);

CREATE TABLE IF NOT EXISTS image (
    id SERIAL PRIMARY KEY,
    filename VARCHAR(255),
    content BYTEA
);

CREATE TABLE IF NOT EXISTS collection_image (
    collection_id BIGINT REFERENCES collection(id) ON DELETE CASCADE,
    image_id BIGINT REFERENCES image(id) ON DELETE CASCADE,
    order_id INT,
    PRIMARY KEY (collection_id, image_id)
);