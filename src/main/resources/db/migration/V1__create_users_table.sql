CREATE TABLE users
(
    id             SERIAL PRIMARY KEY,
    user_uuid      UUID                NOT NULL,
    short_url      VARCHAR(100) UNIQUE NOT NULL,
    url            VARCHAR(2048)       NOT NULL,
    count_redirect INT       DEFAULT 0 NOT NULL,
    expired_at     TIMESTAMP           NOT NULL,
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);