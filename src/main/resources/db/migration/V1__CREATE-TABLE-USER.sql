CREATE TABLE
  users (
    id bigint NOT NULL AUTO_INCREMENT,
    email varchar(255) DEFAULT NULL,
    img_url varchar(255) DEFAULT NULL,
    name varchar(255) DEFAULT NULL,
    old_password varchar(255) DEFAULT NULL,
    password varchar(255) DEFAULT NULL,
    PRIMARY KEY (id)
  );