CREATE TABLE transactions (
    id               UUID PRIMARY KEY,
    amount           DOUBLE PRECISION NOT NULL,
    description      TEXT NOT NULL,
    transaction_date DATE NOT NULL,
    is_income        BOOLEAN NOT NULL
);