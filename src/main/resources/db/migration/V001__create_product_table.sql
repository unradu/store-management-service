CREATE TABLE product (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(64) NOT NULL,
    description VARCHAR(256),
    price DECIMAL(10, 2) NOT NULL CHECK (price > 0),
    currency CHAR(3) NOT NULL,
    quantity INT NOT NULL CHECK (quantity >= 0),
    version INT UNSIGNED NOT NULL DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(64) NOT NULL,
    modified_by VARCHAR(64) DEFAULT NULL,
    CONSTRAINT check_currency CHECK (currency IN ('RON', 'EUR', 'USD'))
);