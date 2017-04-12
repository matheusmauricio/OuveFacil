INSERT INTO ufs (sigla, nome) VALUES ('ES','Espírito Santo');

INSERT INTO cidades (nome, sigla) VALUES ('Cachoeiro de Itapemirim','ES');

INSERT INTO bairros (nome, cdCidade) VALUES ('Aquidaban','1');

INSERT INTO clientes (nome, cpf, debito, telefone, dataNascimento, rua, numero, cdBairro) VALUES ('Rafael','111.222.333-44', '0', '(28) 3552-3552', '1985-06-02', 'Samuel Levy', '10', '1');

INSERT INTO tiposdefilmes (nome, diasDePrazo, preco) VALUES('Tipo A - Lançamento','3','4.0');
INSERT INTO tiposdefilmes (nome, diasDePrazo, preco) VALUES('Tipo B - Médio Prazo','4','3.0');
INSERT INTO tiposdefilmes (nome, diasDePrazo, preco) VALUES('Tipo C - Antigos','5','2.0');

INSERT INTO filmes (titulo, genero, tempoDuracao, cdTipoDeFilme) VALUES('Bravura Indômita','Faroeste','2', '1');
INSERT INTO filmes (titulo, genero, tempoDuracao, cdTipoDeFilme) VALUES('Gladiador','Aventura','2', '2');

INSERT INTO fitas (danificada, disponivel, cdFilme) VALUES('0','1','1');
INSERT INTO fitas (danificada, disponivel, cdFilme) VALUES('0','1','1');
INSERT INTO fitas (danificada, disponivel, cdFilme) VALUES('0','1','2');
INSERT INTO fitas (danificada, disponivel, cdFilme) VALUES('0','1','2');

INSERT INTO emprestimos (data, valor, pago, cdCliente) VALUES('2011-01-01','7','1','1');

INSERT INTO itensdeemprestimo (dataDeEntrega, valor, cdFita, cdEmprestimo) VALUES('2011-01-04','4.0','1','1');
INSERT INTO itensdeemprestimo (dataDeEntrega, valor, cdFita, cdEmprestimo) VALUES('2011-01-05','3.0','3','1');