CREATE TABLE
  comments (
    id SERIAL PRIMARY KEY,
    date TIMESTAMP,
    text varchar(255) NOT NULL,
    user_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id)
    ON DELETE CASCADE
  );