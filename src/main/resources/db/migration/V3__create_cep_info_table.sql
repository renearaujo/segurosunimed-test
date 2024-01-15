CREATE TABLE CEP_INFO
(
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,
    cep        VARCHAR(8)   NOT NULL UNIQUE,
    logradouro VARCHAR(400) NOT NULL,
    localidade VARCHAR(255) NOT NULL,
    uf         VARCHAR(2)   NOT NULL,
    bairro     VARCHAR(255) NOT NULL
);

-- Create index on cep column
CREATE INDEX idx_cep_info_cep_unq ON CEP_INFO (cep);
