create database scv;

use scv;

CREATE TABLE perfis (
cdPerfil serial not null primary key,
descricao varchar(50) not null
);

CREATE TABLE ufs (
sigla varchar(2) not null primary key,
nome varchar(50) not null
);

CREATE TABLE cidades (
cdCidade serial not null primary key,
nome varchar(50) not null,
sigla varchar(2) references ufs
);

CREATE TABLE bairros (
cdBairro serial not null primary key,
nome varchar(50) not null,
cdCidade int references cidades
);

CREATE TABLE clientes (
cdCliente serial not null primary key,
nome varchar(50) not null,
cpf varchar(50) not null,
debito float not null,
telefone varchar(50) not null,
dataNascimento date not null,
rua varchar(50),
numero int,
cdBairro int references bairros
);

ALTER TABLE clientes ALTER COLUMN debito SET DEFAULT '0';

CREATE TABLE funcionarios (
cdFuncionario serial not null primary key,
nome varchar(50) not null,
cpf varchar(50) not null,
login varchar(50) not null,
senha varchar(50) not null,
rua varchar(50),
numero int,
cdPerfil int references perfis,
cdBairro int references bairros
);

CREATE TABLE tiposdefilmes (
cdTipoDeFilme serial not null primary key,
nome varchar(50) not null,
diasDePrazo int,
preco float
);

CREATE TABLE filmes (
cdFilme serial not null primary key,
titulo varchar(50) not null,
genero varchar(50) not null,
tempoDuracao float,
cdTipoDeFilme int references tiposdefilmes
);

CREATE TABLE fitas (
cdFita serial not null primary key,
danificada boolean,
disponivel boolean,
cdFilme int references filmes
);

CREATE TABLE reservas (
cdReserva serial not null primary key,
data date not null,
valor float not null,
cdCliente int references clientes,
cdFita int references fitas
);

CREATE TABLE emprestimos (
cdEmprestimo serial not null primary key,
data date not null,
valor float not null,
pago boolean not null,
cdCliente int references clientes
);

CREATE TABLE itensdeemprestimo (
cdItemDeEmprestimo serial not null primary key,
dataDeEntrega date not null,
valor float not null,
cdFita int references fitas,
cdEmprestimo int references emprestimos
);