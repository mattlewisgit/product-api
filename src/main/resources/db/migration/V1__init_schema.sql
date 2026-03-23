CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    version BIGINT,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    price DOUBLE PRECISION NOT NULL
);

CREATE TABLE customer_orders (
    id BIGSERIAL PRIMARY KEY,
    version BIGINT,
    created_at TIMESTAMP NOT NULL
);

CREATE TABLE order_products (
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    PRIMARY KEY (order_id, product_id),
    CONSTRAINT fk_order_products_order
        FOREIGN KEY (order_id) REFERENCES customer_orders(id),
    CONSTRAINT fk_order_products_product
        FOREIGN KEY (product_id) REFERENCES products(id)
);
