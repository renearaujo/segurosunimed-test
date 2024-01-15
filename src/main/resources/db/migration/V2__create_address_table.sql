CREATE TABLE CUSTOMER_ADDRESS
(
    id            BIGINT PRIMARY KEY AUTO_INCREMENT,
    street        VARCHAR(400) NOT NULL,
    city          VARCHAR(255) NOT NULL,
    state         VARCHAR(2)   NOT NULL,
    zip_code      VARCHAR(8)   NOT NULL,
    neighbourhood VARCHAR(255) NOT NULL,
    number        VARCHAR(255) NOT NULL,
    complement    VARCHAR(400),
    customer_id   BIGINT       NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES CUSTOMER (id)
);
