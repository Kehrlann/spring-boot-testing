CREATE TABLE IF NOT EXISTS todo_item
(
    id          SERIAL PRIMARY KEY NOT NULL,
    text        TEXT               NOT NULL,
    description TEXT DEFAULT NULL
);
