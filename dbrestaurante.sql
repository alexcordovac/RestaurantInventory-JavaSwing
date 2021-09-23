
DROP DATABASE IF EXISTS dbrestaurante;

CREATE DATABASE dbrestaurante
	CHARACTER SET utf8
	COLLATE utf8_general_ci;

USE dbrestaurante;


CREATE TABLE usuarios (
  idUsuario INT(11) NOT NULL AUTO_INCREMENT,
  usuario VARCHAR(50) NOT NULL,
  contrasena VARCHAR(50) NOT NULL,
  fecha TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (idUsuario)
)
ENGINE = INNODB,
CHARACTER SET utf8,
COLLATE utf8_general_ci;


CREATE TABLE nomina (
  idNomina INT(11) NOT NULL AUTO_INCREMENT,
  matricula VARCHAR(30) NOT NULL,
  area VARCHAR(50) NOT NULL,
  salario INT(11) NOT NULL,
  incentivo INT(11) NOT NULL,
  dias SMALLINT(6) NOT NULL,
  descuento INT(11) NOT NULL,
  total INT(11) NOT NULL,
  PRIMARY KEY (idNomina)
)
ENGINE = INNODB,
CHARACTER SET utf8,
COLLATE utf8_general_ci;


CREATE TABLE empleados (
  idEmpleado INT(11) NOT NULL AUTO_INCREMENT,
  matricula VARCHAR(30) NOT NULL,
  nombre VARCHAR(100) NOT NULL,
  apellidos VARCHAR(100) NOT NULL,
  fechaNacimiento DATE NOT NULL,
  curp VARCHAR(50) NOT NULL,
  rfc VARCHAR(50) NOT NULL,
  sueldo INT(11) NOT NULL,
  puesto VARCHAR(100) NOT NULL,
  sucursal VARCHAR(100) NOT NULL,
  fechaIngreso TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (idEmpleado)
)
ENGINE = INNODB,
AUTO_INCREMENT = 8,
AVG_ROW_LENGTH = 5461,
CHARACTER SET utf8,
COLLATE utf8_general_ci;


CREATE TABLE consulta (
  idConsulta INT(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (idConsulta)
)
ENGINE = INNODB,
CHARACTER SET utf8,
COLLATE utf8_general_ci;


CREATE TABLE articulos (
  idArticulo INT(11) NOT NULL AUTO_INCREMENT,
  codigo INT(11) NOT NULL,
  articulo VARCHAR(100) NOT NULL,
  existencia INT(11) NOT NULL,
  sucursal VARCHAR(100) NOT NULL,
  marca VARCHAR(100) NOT NULL,
  fecha TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (idArticulo)
)
ENGINE = INNODB,
AUTO_INCREMENT = 20,
AVG_ROW_LENGTH = 2340,
CHARACTER SET utf8,
COLLATE utf8_general_ci;


INSERT INTO nomina VALUES
(6, '10001', 'General', 10000, 300, 15, 200, 12000),
(7, '10002', 'Cocina', 5000, 500, 5, 400, 5600),
(8, '10003', 'Gerencia', 17000, 0, 0, 0, 17000),
(9, '10004', 'Cocina', 14000, 600, 4, 700, 16500),
(10, '10005', 'Gerencia', 15000, 900, 2, 400, 16000),
(11, '10006', 'Ventas', 12000, 400, 5, 0, 14000),
(12, '10007', 'Gerencia', 19000, 800, 6, 0, 22000),
(13, '10008', 'Cocina', 10000, 600, 3, 400, 13000),
(14, '10009', 'IT', 16000, 1000, 3, 500, 19000),
(15, '10010', 'Intedencia', 14000, 300, 7, 300, 16500);


INSERT INTO empleados VALUES
(9, '10001', 'Sebastian', 'Jiménez', '1985-10-23', 'SJME19232313GTVA2', 'SJME123D21', 10000, 'General', 'TAB', '2020-09-04 23:48:20'),
(10, '10002', 'Andrés', 'López', '1995-03-15', 'ANDR13123', 'AND21R1J', 5000, 'Asistente', 'TAB', '2020-09-04 23:49:47'),
(11, '10003', 'Felipe', 'Karim', '1990-01-18', 'FELIP291023FJ', 'FELIP1421HC', 17000, 'Gerente', 'TAB', '2020-09-04 23:50:56'),
(12, '10004', 'Marisol', 'Herrera', '1988-10-03', 'MARI129310GR129', 'MAR219FHY', 14000, 'Chef', 'TAB', '2020-09-04 23:52:01'),
(13, '10005', 'Ana', 'Romero', '2000-12-31', 'AROM91202391HT218', 'ANAR1213HTY', 15000, 'Asesor', 'TAB', '2020-09-04 23:53:00'),
(14, '10006', 'Ricardo', 'García', '1999-04-14', 'RICG2913019SDHTC', 'RICRD19HT', 12000, 'Ventas', 'TAB', '2020-09-04 23:53:44'),
(15, '10007', 'Gabriela', 'Valenzuela', '2000-05-23', 'GARV000523HTC313EO', 'GRBLHDT12D2O', 19000, 'Manager', 'TAB', '2020-09-04 23:54:37'),
(16, '10008', 'Melvin', 'Torres', '1989-11-11', 'MELVR12821UHC', 'MELV61N', 10000, 'Cocina', 'TAB', '2020-09-04 23:55:42'),
(17, '10009', 'César', 'Rodriguez', '1996-06-30', 'CERO82192HCT218J', 'CESR2CEHT3O', 16000, 'IT', 'TAB', '2020-09-04 23:56:39'),
(18, '10010', 'Paola', 'Ramírez', '1994-02-13', 'PARA219282GRYK2L', 'PARA128CHT', 14000, 'Intendente', 'TAB', '2020-09-04 23:57:31');


INSERT INTO articulos VALUES
(2, 10007, 'Quesadilla', 10, 'MXTAB', 'MX', '2020-08-18 00:01:10'),
(8, 10010, 'Tacos de milanesa', 15, 'Comalcalco', 'TXM', '2020-08-18 03:51:36'),
(15, 10001, 'Fajitas', 50, 'CDMX S1 E.', 'MX', '2020-08-18 13:16:03'),
(16, 10002, 'Enchiladas', 100, 'QRO S2', 'FOOD', '2020-08-18 13:16:52'),
(17, 10005, 'Pozole', 56, 'TAB CARR S5', 'MXTAB', '2020-08-18 14:52:16'),
(18, 0, '', 0, '', '', '2020-08-26 15:42:49'),
(19, 0, '', 0, '', '', '2020-08-26 15:42:59'),
(20, 10056, 'Quesadillas sin queso', 200, 'YTAN SUC3 MTS', 'FUD', '2020-09-03 00:06:31');

