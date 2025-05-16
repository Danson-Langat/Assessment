CREATE TABLE conversions (
    id SERIAL PRIMARY KEY,
    from_currency VARCHAR(3) NOT NULL,
    to_currency VARCHAR(3) NOT NULL,
    original_amount DECIMAL(19,4) NOT NULL,
    converted_amount DECIMAL(19,4) NOT NULL,
    timestamp TIMESTAMP NOT NULL DEFAULT NOW()
);
