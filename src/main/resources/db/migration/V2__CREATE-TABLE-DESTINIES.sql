CREATE TABLE
  destinos (
    id bigint NOT NULL AUTO_INCREMENT,
    name varchar(255) NOT NULL,
    imgs varchar(255),
    price decimal NOT NULL,
    meta varchar(255) NOT NULL,
    description varchar(450),
    PRIMARY KEY (id)
  );