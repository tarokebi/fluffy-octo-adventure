CREATE TABLE IF NOT EXISTS AppUser (
    id INT NOT NULL,
    name VARCHAR(250) NOT NULL,
    email VARCHAR(250) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS product (
    id INT NOT NULL,
    name VARCHAR(250) NOT NULL,
    price INT NOT NULL,
    PRIMARY KEY(id)
);
