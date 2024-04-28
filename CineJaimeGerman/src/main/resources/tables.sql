CREATE TABLE IF NOT EXISTS Butacas(
    id TEXT PRIMARY KEY,
    nombre TEXT NOT NULL,
    tipo TEXT NOT NULL,
    precio REAL NOT NULL,
    filaButaca INTEGER NOT NULL,
    columnaButaca INTEGER NOT NULL,
    tipoButaca TEXT NOT NULL,
    estadoButaca TEXT NOT NULL,
    ocupacionButaca TEXT NOT NULL,
    created_at TEXT NOT NULL,
    updated_at TEXT NOT NULL,
    is_deleted INTEGER default 0
);

CREATE TABLE IF NOT EXISTS Complementos(
    id TEXT PRIMARY KEY,
    nombre TEXT NOT NULL UNIQUE,
    tipo TEXT NOT NULL,
    precio REAL NOT NULL,
    stock INTEGER NOT NULL,
    categoria TEXT NOT NULL,
    created_at TEXT NOT NULL,
    updated_at TEXT NOT NULL,
    is_deleted INTEGER default 0
);