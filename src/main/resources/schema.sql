-- DROP TABLE IF EXISTS image CASCADE;
-- DROP TABLE IF EXISTS collection CASCADE;

CREATE TABLE IF NOT EXISTS collection (
                            id SERIAL PRIMARY KEY,
                            category VARCHAR(255),
                            description TEXT
);

CREATE TABLE IF NOT EXISTS image (
                       id SERIAL PRIMARY KEY,
                       filename VARCHAR(255) NOT NULL,
                       content BYTEA,
                       collection_id BIGINT NOT NULL REFERENCES collection(id) ON DELETE CASCADE,
                       order_index INT NOT NULL DEFAULT 0
);

-- Sequenzen korrekt setzen (nur nötig, wenn Daten existieren)
SELECT setval('collection_id_seq', COALESCE((SELECT MAX(id) FROM collection), 0) + 1, false);
SELECT setval('image_id_seq', COALESCE((SELECT MAX(id) FROM image), 0) + 1, false);