DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS meals;
DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE users
(
    id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name             VARCHAR                           NOT NULL,
    email            VARCHAR                           NOT NULL,
    password         VARCHAR                           NOT NULL,
    registered       TIMESTAMP           DEFAULT now() NOT NULL,
    enabled          BOOL                DEFAULT TRUE  NOT NULL,
    calories_per_day INTEGER             DEFAULT 2000  NOT NULL
);

CREATE TABLE meals
(
    id INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    date_time TIMESTAMP DEFAULT now() NOT NULL,
    description VARCHAR NOT NULL,
    calories INTEGER DEFAULT 0 NOT NULL,
    user_id INTEGER NOT NULL,
    CONSTRAINT user_meals_idx UNIQUE (user_id, date_time),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE INDEX user_id_index ON meals (user_id);
CREATE INDEX date_time_index ON meals (date_time);

CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE user_roles
(
    user_id INTEGER NOT NULL,
    role    VARCHAR NOT NULL,
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);