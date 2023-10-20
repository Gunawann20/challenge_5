CREATE TABLE users(
      id BIGSERIAL PRIMARY KEY ,
      username VARCHAR(100) NOT NULL ,
      email VARCHAR(100) UNIQUE NOT NULL ,
      password VARCHAR(100) NOT NULL ,
      address VARCHAR(100) NOT NULL
);

SELECT * FROM users;

CREATE TABLE merchants(
      code BIGSERIAL PRIMARY KEY ,
      name VARCHAR(100) NOT NULL ,
      location VARCHAR(100) NOT NULL ,
      open BOOLEAN NOT NULL
);

SELECT * FROM merchants;

CREATE TABLE products(
     code BIGSERIAL PRIMARY KEY ,
     merchant_code BIGINT NOT NULL ,
     name VARCHAR(100) NOT NULL ,
     price INT NOT NULL ,
     CONSTRAINT fk_products_merchants
     FOREIGN KEY (merchant_code) REFERENCES merchants(code)
);

SELECT * FROM products;

CREATE TABLE orders(
       id BIGSERIAL PRIMARY KEY ,
       user_id BIGINT NOT NULL ,
       destination VARCHAR(100) NOT NULL ,
       time TIMESTAMP NOT NULL ,
       completed BOOLEAN NOT NULL ,
       CONSTRAINT fk_orders_users
       FOREIGN KEY (user_id) REFERENCES users(id)
);

SELECT * FROM orders;

CREATE TABLE order_details(
      id BIGSERIAL PRIMARY KEY ,
      order_id BIGINT NOT NULL ,
      product_code BIGINT NOT NULL ,
      quantity INT NOT NULL ,
      total_price BIGINT NOT NULL ,
      CONSTRAINT fk_order_details_orders
      FOREIGN KEY (order_id) REFERENCES orders(id) ,
      CONSTRAINT fk_order_details_products
      FOREIGN KEY (product_code) REFERENCES products(code)
);
Select * from order_details;
