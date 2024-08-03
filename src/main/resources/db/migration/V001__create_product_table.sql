CREATE TABLE product (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(64) NOT NULL UNIQUE,
    description VARCHAR(256),
    price DECIMAL(10, 2) NOT NULL CHECK (price > 0),
    discounted_price DECIMAL(10, 2) CHECK (discounted_price > 0),
    product_state VARCHAR(20) NOT NULL
     CHECK (product_state IN ('AVAILABLE', 'OUT_OF_STOCK', 'REMOVED')),
    quantity INT NOT NULL CHECK (quantity >= 0),
    version INT UNSIGNED NOT NULL DEFAULT 0,
    discount INT CHECK (discount BETWEEN 1 AND 99),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(64) NOT NULL,
    modified_by VARCHAR(64) DEFAULT NULL
);