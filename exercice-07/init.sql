USE kennelDB;

CREATE TABLE clients (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nom VARCHAR(100) NOT NULL,
  prenom VARCHAR(100) NOT NULL,
  date_naissance DATE NOT NULL,
  pseudonyme VARCHAR(100) NOT NULL
);

CREATE TABLE adresses (
  id INT AUTO_INCREMENT PRIMARY KEY,
  numero VARCHAR(10) NOT NULL,
  rue VARCHAR(150) NOT NULL,
  code_postal VARCHAR(10) NOT NULL,
  commune VARCHAR(100) NOT NULL
);

CREATE TABLE clients_adresses (
  client_id INT NOT NULL,
  adresse_id INT NOT NULL,
  PRIMARY KEY (client_id, adresse_id),
  FOREIGN KEY (client_id) REFERENCES clients(id),
  FOREIGN KEY (adresse_id) REFERENCES adresses(id)
);

CREATE TABLE chiens (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nom VARCHAR(100) NOT NULL,
  date_naissance DATE NOT NULL,
  race VARCHAR(100) NOT NULL,
  sterilise BOOLEAN NOT NULL,
  client_id INT,
  FOREIGN KEY (client_id) REFERENCES clients(id)
);

CREATE TABLE chats (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nom VARCHAR(100) NOT NULL,
  date_naissance DATE NOT NULL,
  race VARCHAR(100) NOT NULL,
  sterilise BOOLEAN NOT NULL,
  client_id INT,
  FOREIGN KEY (client_id) REFERENCES clients(id)
);

INSERT INTO clients (nom, prenom, date_naissance, pseudonyme) VALUES
('Dupont', 'Marie', '1990-05-12', 'mdupont'),
('Martin', 'Paul', '1985-11-02', 'pmartin');

INSERT INTO adresses (numero, rue, code_postal, commune) VALUES
('12', 'Rue des Fleurs', '75001', 'Paris'),
('5', 'Avenue de la Gare', '69002', 'Lyon');

INSERT INTO clients_adresses (client_id, adresse_id) VALUES
(1, 1),
(2, 2);

INSERT INTO chiens (nom, date_naissance, race, sterilise, client_id) VALUES
('Rex', '2019-03-01', 'Labrador', TRUE, 1),
('Milo', '2021-07-15', 'Beagle', FALSE, 2);

INSERT INTO chats (nom, date_naissance, race, sterilise, client_id) VALUES
('Felix', '2020-01-20', 'Siamois', TRUE, 1),
('Minou', '2022-09-10', 'Persan', FALSE, 2);
