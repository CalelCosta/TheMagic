CREATE database themagicg;

CREATE SCHEMA cardgame;

CREATE TABLE cardgame.jogadores
(
	id bigserial NOT NULL PRIMARY KEY,
	nome character varying(150) NOT NULL,
	email character varying(100) NOT NULL,
	senha character varying(20) NOT NULL
);

CREATE TABLE cardgame.cartas
(
	id bigserial NOT NULL PRIMARY KEY,
	nome character varying(150) NOT NULL,
	edicao character varying(50) NOT NULL,
	valor numeric(16,2) NOT NULL,
	laminada boolean DEFAULT false NOT NULL,
	idioma character varying(20) CHECK (idioma in ('PORTUGUES', 'INGLES', 'JAPONES')) NOT NULL,
	caracteristica character varying(20) CHECK (caracteristica in ('TRIBAL', 'CRIATURA', 'ARTEFATO','TERRENO', 'FEITICO', 'ENCANTAMENTO')) NOT NULL,
	id_jogadores bigint REFERENCES cardgame.jogadores (id) NOT NULL
);
