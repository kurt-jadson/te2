CREATE TABLE IDIOMA (
	ID INTEGER IDENTITY PRIMARY KEY,
	NOME VARCHAR(40) NOT NULL,
	SISTEMASOM VARCHAR(60) NULL
);

CREATE TABLE DESENHO_IDIOMA (
	DESENHO_ID INTEGER NOT NULL,
	IDIOMA_ID INTEGER NOT NULL
);
