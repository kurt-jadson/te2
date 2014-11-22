CREATE TABLE DESENHO (
	ID INTEGER IDENTITY PRIMARY KEY,
	TITULO VARCHAR(200) NOT NULL,
	VOLUME INTEGER NULL,
	TEMPO INTEGER NULL,
	COR VARCHAR(30) NULL,
	ANOLANCAMENTO INTEGER NULL,
	RECOMENDACAO VARCHAR(20) NULL,
	REGIAODVD INTEGER NULL,
	LEGENDA VARCHAR(30),
	FORMATOTELA VARCHAR(30),
	PAISORIGEM VARCHAR(60),
	DESCRICAO LONGVARCHAR,
	PRECO DECIMAL(10, 2) NOT NULL
);