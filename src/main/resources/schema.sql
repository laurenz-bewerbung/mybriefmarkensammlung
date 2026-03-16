 DROP TABLE IF EXISTS "role" CASCADE;
 DROP TABLE IF EXISTS "user" CASCADE;
 DROP TABLE IF EXISTS category CASCADE;
 DROP TABLE IF EXISTS collection CASCADE;
 DROP TABLE IF EXISTS image CASCADE;
 DROP TABLE IF EXISTS collection_image CASCADE;

CREATE TABLE IF NOT EXISTS "role" (
    id SERIAL PRIMARY KEY,
    authority VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS "user" (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role_id BIGINT NOT NULL
);

CREATE TABLE IF NOT EXISTS category (
    id SERIAL PRIMARY KEY,
    category VARCHAR(255),
    parent_id BIGINT
);

CREATE TABLE IF NOT EXISTS collection (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255),
    category_id BIGINT REFERENCES category(id) ON DELETE CASCADE,
    description TEXT,
    is_exhibition BOOLEAN,
    exhibition_class VARCHAR(255)
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