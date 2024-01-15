CREATE TABLE CUSTOMER
(
    id     BIGINT PRIMARY KEY AUTO_INCREMENT,
    name   VARCHAR(255) NOT NULL,
    email  VARCHAR(255) NOT NULL UNIQUE,
    gender VARCHAR(255) NOT NULL
);

-- Create index on email column
CREATE INDEX idx_customer_email_unq ON CUSTOMER (email);
