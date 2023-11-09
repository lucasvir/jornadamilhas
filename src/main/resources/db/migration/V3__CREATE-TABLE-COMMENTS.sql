CREATE TABLE
  comments (
    id bigint NOT NULL AUTO_INCREMENT,
    date datetime(6) DEFAULT NULL,
    text varchar(255) DEFAULT NULL,
    user_id bigint DEFAULT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
    ON DELETE CASCADE
  );