CREATE TABLE uf (
  sigla CHAR(2) NOT NULL,
  nome VARCHAR(30) NOT NULL,
  CONSTRAINT pk_uf
    PRIMARY KEY(sigla)
);

CREATE TABLE cidade (
  codCidade SERIAL,
  nome VARCHAR(40) NOT NULL,
  sigla CHAR(2) NOT NULL,  
  CONSTRAINT pk_cidade
    PRIMARY KEY(codCidade),
  CONSTRAINT fk_cidade_uf
    FOREIGN KEY(sigla)
    REFERENCES uf(sigla)
);

CREATE TABLE bairro (
  codBairro SERIAL,
  nome VARCHAR(30) NOT NULL,
  codCidade INTEGER NOT NULL,
  CONSTRAINT pk_bairro
    PRIMARY KEY(codBairro),
  CONSTRAINT fk_bairro_cidade
    FOREIGN KEY(codCidade)
    REFERENCES cidade(codCidade)
);

CREATE TABLE subCategoria (
  codSubCategoria SERIAL,
  nome VARCHAR (30) NOT NULL,
  CONSTRAINT pk_subCategoria
    PRIMARY KEY(codSUbCategoria)
);

CREATE TABLE categoria (
  codCategoria SERIAL,
  nome VARCHAR(30) NOT NULL,
  codSubCategoria INTEGER NOT NULL,
  CONSTRAINT pk_categoria
    PRIMARY KEY(codCategoria),
  CONSTRAINT fk_categoria_subCategoria
    FOREIGN KEY(codSubCategoria)
    REFERENCES subCategoria(codSubCategoria) 
);

CREATE TABLE usuario (
  codUsuario SERIAL,
  nome VARCHAR(45) NOT NULL,
  login VARCHAR(30) NOT NULL,
  senha VARCHAR(30) NOT NULL,
  cpfCnpj VARCHAR(18) NOT NULL,
  CONSTRAINT pk_usuario
    PRIMARY KEY(codUsuario)
);

CREATE TABLE administrador (
  codAdministrador SERIAL,
  nome VARCHAR(45) NOT NULL,
  login VARCHAR(30) NOT NULL,
  senha VARCHAR(30) NOT NULL,
  cpfCnpj VARCHAR(18) NOT NULL,
  CONSTRAINT pk_administrador
    PRIMARY KEY(codAdministrador)
);

CREATE TABLE fotoEOuVideo (
  codFotoVideo SERIAL,
  urlFotoVideo VARCHAR(100) NOT NULL,
  CONSTRAINT pk_fotoEOuVideo
    PRIMARY KEY(codFotoVideo)
);

CREATE TABLE status (
  codStatus SERIAL,
  nome VARCHAR(30) NOT NULL,
  CONSTRAINT pk_status
    PRIMARY KEY(codStatus)
);

CREATE TABLE denuncia (
  codDenuncia SERIAL,
  descricao VARCHAR(200) NOT NULL,
  latitude NUMERIC(10,8) NOT NULL,
  longitude NUMERIC(10,8) NOT NULL,
  anonimato BOOLEAN,
  complementoStatus VARCHAR(200),
  codBairro INTEGER NOT NULL,
  codCategoria INTEGER NOT NULL,
  codUsuario INTEGER,
  codAdministrador INTEGER,
  codFotoVideo INTEGER NOT NULL,
  codStatus INTEGER NOT NULL,
  CONSTRAINT pk_denuncia
    PRIMARY KEY(codDenuncia),
  CONSTRAINT fk_denuncia_bairro
    FOREIGN KEY(codBairro)
    REFERENCES bairro(codBairro),
  CONSTRAINT fk_denuncia_categoria
    FOREIGN KEY(codCategoria)
    REFERENCES categoria(codCategoria),
  CONSTRAINT fk_denuncia_usuario
    FOREIGN KEY(codUsuario)
    REFERENCES usuario(codUsuario),
  CONSTRAINT fk_denuncia_administrador
    FOREIGN KEY(codAdministrador)
    REFERENCES administrador(codAdministrador),
  CONSTRAINT fk_denuncia_fotoEOuVideo
    FOREIGN KEY (codFotoVideo)
    REFERENCES fotoEOuVideo(codFotoVideo),
  CONSTRAINT fk_denuncia_status
    FOREIGN KEY (codStatus)
    REFERENCES status(CodStatus)
);