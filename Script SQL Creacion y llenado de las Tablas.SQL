CREATE TABLE clients (
  id integer PRIMARY KEY AUTOINCREMENT,
  name VARCHAR(75),
  lastname VARCHAR(75),
  docnumber VARCHAR(11));
  
  INSERT INTO clients (name, lastname, docnumber) VALUES 
  ('Juan', 'Perez', '87687766'),
  ('Maria', 'Gomez', '87654321'),
  ('Pedro', 'Rodriguez', '98765432'),
  ('Lucia', 'Torres', '23456789'),
  ('David', 'Hernandez', '34567891'),
  ('Laura', 'Sanchez', '45678912'),
  ('Pablo', 'Gonzalez', '56789123'),
  ('Sofia', 'Lopez', '67891234'),
  ('Carlos', 'Diaz', '78912345'),
  ('Ana', 'Martinez', '89123456');
  
  
  
  
  CREATE TABLE invoice (
  id integer PRIMARY KEY AUTOINCREMENT NOT NULL,
  client_id INT NOT NULL,
  created_at DATETIME,
  total DOUBLE,
  FOREIGN KEY (client_id) REFERENCES clients(id)
);

INSERT INTO invoice (client_id, created_at, total)
VALUES 
  (1, '2022-01-01 10:00:00', 100.50),
  (2, '2022-01-02 11:00:00', 200.75),
  (3, '2022-01-03 12:00:00', 150.25),
  (3, '2022-01-04 13:00:00', 300.00),
  (5, '2022-01-05 14:00:00', 75.50),
  (7, '2022-01-06 15:00:00', 50.25),
  (9, '2022-01-07 16:00:00', 100.00),
  (9, '2022-01-08 17:00:00', 250.50),
  (9, '2022-01-09 18:00:00', 175.75),
  (10, '2022-01-10 19:00:00', 90.25);
  
  
  
  
  
  
  CREATE TABLE products (
  id integer PRIMARY KEY AUTOINCREMENT NOT NULL,
  description VARCHAR(150),
  code VARCHAR(50),
  stock INT,
  price DOUBLE
);

INSERT INTO products (description, code, stock, price)
VALUES 
  ('Zapatos de cuero', 'ZC-001', 50, 99.99),
  ('Vestido de fiesta', 'VF-002', 20, 149.99),
  ('Camisa a rayas', 'CR-003', 100, 39.99),
  ('Pantalón de mezclilla', 'PM-004', 80, 59.99),
  ('Blusa con cuello halter', 'BCH-005', 30, 29.99),
  ('Gorra deportiva', 'GD-006', 70, 19.99),
  ('Shorts de algodón', 'SA-007', 90, 34.99),
  ('Jersey de lana', 'JL-008', 40, 79.99),
  ('Falda de flores', 'FF-009', 25, 44.99),
  ('Chamarra de piel', 'CP-010', 15, 249.99);
  
  
  
  
  CREATE TABLE invoice_details (
  invoice_id INT NOT NULL,
  invoice_detail_id integer PRIMARY KEY AUTOINCREMENT NOT NULL,
  amount INT,
  product_id INT NOT NULL,
  price DOUBLE,
  FOREIGN KEY (invoice_id) REFERENCES invoice(id),
  FOREIGN KEY (product_id) REFERENCES products(id)
);


INSERT INTO invoice_details (invoice_id, amount, product_id, price) VALUES
  (1, 2, 3, 25.99),
  (1, 1, 4, 19.99),
  (2, 3, 2, 15.99),
  (2, 1, 1, 9.99),
  (3, 2, 5, 49.99),
  (3, 1, 6, 29.99),
  (4, 1, 7, 10.99),
  (5, 2, 2, 15.99),
  (6, 1, 1, 9.99),
  (7, 3, 5, 49.99);