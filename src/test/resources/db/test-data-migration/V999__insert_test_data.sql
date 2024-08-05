INSERT INTO product (
    id, name, description, price, discounted_price, product_state, quantity,
    version, discount, created_by, modified_by
) VALUES (
    1,
    'Sample Product',
    'This is a description of the product.',
    99.99,
    89.99,
    'AVAILABLE',
    10,
    1,
    10,
    'admin',
    NULL
);

INSERT INTO product (
    id, name, description, price, discounted_price, product_state, quantity,
    version, discount, created_by, modified_by
) VALUES (
    2,
    'Product For Test',
    'This is a description of the product.',
    65.99,
    NULL,
    'REMOVED',
    10,
    1,
    NULL,
    'admin',
    NULL
);

INSERT INTO product (
    id, name, description, price, discounted_price, product_state, quantity,
    version, discount, created_by, modified_by
) VALUES (
    3,
    'Another Product For Test',
    'This is a description of the product.',
    65.99,
    NULL,
    'OUT_OF_STOCK',
    0,
    1,
    NULL,
    'admin',
    NULL
);