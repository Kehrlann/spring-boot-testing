-- TODO: include text in ts_vector
CREATE TABLE IF NOT EXISTS todo_item
(
    id             SERIAL PRIMARY KEY NOT NULL,
    text           TEXT               NOT NULL,
    description    TEXT DEFAULT NULL,
    description_ts TEXT GENERATED ALWAYS AS (
        setweight(to_tsvector('english', text), 'A') ||
        setweight(to_tsvector('english', description), 'B')
        ) STORED
);
