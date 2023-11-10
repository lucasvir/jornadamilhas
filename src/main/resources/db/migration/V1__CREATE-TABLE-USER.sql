CREATE TABLE
  users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    img_url VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    old_password VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
  );