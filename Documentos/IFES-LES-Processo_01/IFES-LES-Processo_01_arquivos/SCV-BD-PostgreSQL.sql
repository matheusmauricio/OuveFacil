CREATE TABLE perfis(
   cdPefil serial     NOT NULL,
   descricao  varchar(50) NOT NULL,
   CONSTRAINT pk_perfis
      PRIMARY KEY(cdPerfil)
);

CREATE TABLE ufs(
   sigla varchar(2)     NOT NULL,
   nome varchar(50) NOT NULL,
   CONSTRAINT pk_ufs
      PRIMARY KEY(sigla)
);

CREATE TABLE cidades(
   cdCidade serial      NOT NULL,
   nome varchar(50) NOT NULL,
   sigla varchar(2)     NOT NULL,
   CONSTRAINT pk_cidades
      PRIMARY KEY(cdCidade),
   CONSTRAINT fk_cidades_ufs
      FOREIGN KEY(sigla)
      REFERENCES ufs(sigla)
);

CREATE TABLE bairros(
   cdBairro serial      NOT NULL,
   nome varchar(50) NOT NULL,
   cdCidade int     NOT NULL,
   CONSTRAINT pk_bairros
      PRIMARY KEY(cdBairro),
   CONSTRAINT fk_bairros_cidades
      FOREIGN KEY(cdCidade)
      REFERENCES cidades(cdCidade)
);

CREATE TABLE clientes(
   cdCliente serial      NOT NULL,
   nome varchar(50) NOT NULL,
   cpf varchar(50) NOT NULL,
   debito float NOT NULL,
   telefone varchar(50) NOT NULL,
   dataNascimento date NOT NULL,
   rua varchar(50),
   numero int,   
   cdBairro int     NOT NULL,
   CONSTRAINT pk_clientes
      PRIMARY KEY(cdCliente),
   CONSTRAINT fk_clientes_bairros
      FOREIGN KEY(cdBairro)
      REFERENCES bairros(cdBairro)
);

ALTER TABLE clientes ALTER COLUMN debito SET DEFAULT '0';

CREATE TABLE funcionarios(
   cdFuncionario serial      NOT NULL,
   nome varchar(50) NOT NULL,
   cpf varchar(50) NOT NULL,
   login varchar(50) NOT NULL,
   senha varchar(50) NOT NULL,
   rua varchar(50),
   numero int,
   cdBairro int     NOT NULL,
   cdPerfil int NOT NULL,
   CONSTRAINT pk_funcionarios
      PRIMARY KEY(cdFuncionario),
   CONSTRAINT fk_funcionarios_bairros
      FOREIGN KEY(cdBairro)
      REFERENCES bairros(cdBairro),
    CONSTRAINT fk_funcionarios_perfis
      FOREIGN KEY(cdPerfil)
      REFERENCES perfis(cdPerfil)
);

CREATE TABLE tiposdefilmes(
   cdTipoDeFilme serial NOT NULL,
   nome varchar(50) NOT NULL,
   diasDePrazo int,
   preco float,
   CONSTRAINT pk_tiposdefilmes
      PRIMARY KEY(cdTipoDeFilme)
);

CREATE TABLE fimes(
   cdFilme serial NOT NULL,
   titulo varchar(50) NOT NULL,
   genero varchar(50) NOT NULL,
   tempoDuracao float,
   cdTipoDeFilme int,
   CONSTRAINT pk_filmes
      PRIMARY KEY(cdFilme),
   CONSTRAINT fk_filmes_tiposdefilmes
      FOREIGN KEY(cdTipoDeFilme)
      REFERENCES tiposdefilmes(cdTipoDeFilme)
);

CREATE TABLE fitas(
   cdFita serial NOT NULL,
   danificada boolean,
   disponivel boolean,
   cdFilme int,
   CONSTRAINT pk_fitas
      PRIMARY KEY(cdFita),
   CONSTRAINT fk_fitas_filmes
      FOREIGN KEY(cdFilme)
      REFERENCES filmes(cdFilme)
);

CREATE TABLE reservas(
   cdReserva serial NOT NULL,
   data date NOT NULL,
   valor float NOT NULL,
   cdCliente int,
   cdFita int,
   CONSTRAINT pk_reservas
      PRIMARY KEY(cdReserva),
   CONSTRAINT fk_reservas_clientes
      FOREIGN KEY(cdCliente)
      REFERENCES clientes(cdCliente),
   CONSTRAINT fk_reservas_fitas
      FOREIGN KEY(cdFita)
      REFERENCES fitas(cdFita)
);

CREATE TABLE emprestimos(
   cdEmprestimo serial NOT NULL,
   data date NOT NULL,
   valor float NOT NULL,
   pago boolean NOT NULL,
   cdCliente int,
   CONSTRAINT pk_emprestimos
      PRIMARY KEY(cdEmprestimo),
   CONSTRAINT fk_emprestimos_clientes
      FOREIGN KEY(cdCliente)
      REFERENCES clientes(cdCliente)
);

CREATE TABLE itensdeemprestimo(
   cdItemDeEmprestimo serial NOT NULL,
   dataDeEntrega date NOT NULL,
   valor float NOT NULL,
   cdFita int,
   cdEmprestimo int,
   CONSTRAINT pk_itensdeemprestimo
      PRIMARY KEY(cdItemDeEmprestimo),
   CONSTRAINT fk_itensdeemprestimo_fitas
      FOREIGN KEY(cdFita)
      REFERENCES fitas(cdFita),
   CONSTRAINT fk_itensdeemprestimo_emprestimos
      FOREIGN KEY(cdEmprestimo)
      REFERENCES emprestimos(cdEmprestimo)
);

CREATE TABLE itensdeemprestimo (
cdItemDeEmprestimo serial not null primary key,
dataDeEntrega date not null,
valor float not null,
cdFita int references fitas,
cdEmprestimo int references emprestimos
);