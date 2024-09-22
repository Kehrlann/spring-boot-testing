CREATE TABLE IF NOT EXISTS todo_item
(
    id          SERIAL PRIMARY KEY NOT NULL,
    text        TEXT               NOT NULL,
    description TEXT DEFAULT NULL,
    ts          tsvector GENERATED ALWAYS AS (
        setweight(to_tsvector('english', text), 'A') ||
        setweight(to_tsvector('english', description), 'B')
        ) STORED
);

CREATE INDEX IF NOT EXISTS todo_ts_idx ON todo_item USING GIN (ts);