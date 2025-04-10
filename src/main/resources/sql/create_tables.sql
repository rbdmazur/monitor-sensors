CREATE TABLE IF NOT EXISTS ranges (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    "from" INTEGER NOT NULL DEFAULT 0 CHECK ("from" >= 0),
    "to" INTEGER NOT NULL CHECK("to" >= 0),
    CHECK ("from" < "to")
);


CREATE TABLE IF NOT EXISTS sensors(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    model VARCHAR(15) NOT NULL,
    range_id BIGINT NOT NULL,
    type sensor_type NOT NULL,
    unit units,
    location VARCHAR(40),
    description VARCHAR(200),
    CHECK (length(name) >= 3),
    FOREIGN KEY (range_id) REFERENCES ranges(id)
);

CREATE TABLE IF NOT EXISTS users(
    id BIGINT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR NOT NULL,
    role VARCHAR NOT NULL
);

CREATE INDEX sensors_name_index ON sensors USING BTREE(name);
CREATE INDEX sensors_model_index ON sensors USING BTREE(model);