CREATE TABLE
  destinos (
    id SERIAL PRIMARY KEY,
    name varchar(255) NOT NULL,
    imgs varchar(255),
    price NUMERIC NOT NULL,
    meta varchar(255) NOT NULL,
    description varchar(450)
  );