DELETE FROM Butacas;
DELETE FROM Complementos;

INSERT INTO Butacas (id, nombre, tipo, precio, filaButaca, columnaButaca, tipoButaca, estadoButaca, ocupacionButaca, created_at, updated_at)
VALUES ('A1', 'Butaca', 'Butaca', 5.0, 0, 0, 'NORMAL', 'ACTIVA', 'LIBRE', '2024-04-28', '2024-04-28');

INSERT INTO Complementos (id, nombre, tipo, precio, stock, categoria, created_at, updated_at)
VALUES ('1', 'Palomitas', 'Complemento', 3.0, 20, 'Comida', '2024-04-28', '2024-04-28');