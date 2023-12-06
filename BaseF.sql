CREATE DATABASE FACTURACION;
USE FACTURACION;

CREATE TABLE Tipo_Producto(
	ID_CodTipo int AUTO_INCREMENT PRIMARY KEY,
	nombreTipo varchar(38)
);

CREATE TABLE Productos(
	CodProducto varchar(18) PRIMARY KEY,
	Descripción varchar(40),
	precioCompra float,
	precioVenta float,
	ID_CodTipo int,
	FOREIGN KEY(ID_CodTipo) REFERENCES Tipo_Producto(ID_CodTipo)
);

CREATE TABLE Ventas(
	CodVenta varchar(45) PRIMARY KEY,
	importe float,
	fecha dateTime
);

CREATE TABLE DetalleVenta(
	CodVenta varchar(45) NOT NULL,
	CodProducto varchar(18) NOT NULL,
	cantidad int,
	FOREIGN KEY (CodVenta) REFERENCES Ventas(CodVenta),
	FOREIGN KEY (CodProducto) REFERENCES Productos(CodProducto),
	PRIMARY KEY(CodVenta,CodProducto)
);