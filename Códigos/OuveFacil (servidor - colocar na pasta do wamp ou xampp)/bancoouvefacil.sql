-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: 06-Out-2017 às 23:40
-- Versão do servidor: 10.1.25-MariaDB
-- PHP Version: 7.1.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bancoouvefacil`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `administrador`
--

CREATE TABLE `administrador` (
  `codAdministrador` int(11) NOT NULL,
  `nome` varchar(45) NOT NULL,
  `login` varchar(30) NOT NULL,
  `senha` varchar(32) NOT NULL,
  `cpfCnpj` varchar(18) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `administrador`
--

INSERT INTO `administrador` (`codAdministrador`, `nome`, `login`, `senha`, `cpfCnpj`) VALUES
(1, 'Matheus Mauricio', 'matheusm', 'e10adc3949ba59abbe56e057f20f883e', '15587122751'),
(3, 'Cesar', 'cesar', 'e10adc3949ba59abbe56e057f20f883e', '01010101010'),
(4, 'Andre', 'andre', 'e10adc3949ba59abbe56e057f20f883e', '1122233412'),
(5, 'Maria', 'maria', 'e10adc3949ba59abbe56e057f20f883e', '123520005'),
(6, 'Joao', 'joao', 'e10adc3949ba59abbe56e057f20f883e', '4500178630'),
(7, 'Ana', 'ana', 'e10adc3949ba59abbe56e057f20f883e', '1274728248'),
(8, 'Flavio', 'flavio', 'e10adc3949ba59abbe56e057f20f883e', '2537271637'),
(9, 'Carlos Eduardo', 'dedubr', 'e10adc3949ba59abbe56e057f20f883e', '12332112331'),
(10, 'Leticia', 'lele', 'e10adc3949ba59abbe56e057f20f883e', '123467363'),
(11, 'Kelly', 'kelly', 'e10adc3949ba59abbe56e057f20f883e', '12245544212'),
(12, 'Juliana', 'juliana', 'e10adc3949ba59abbe56e057f20f883e', '16648594928'),
(13, 'Davi', 'davi', 'e10adc3949ba59abbe56e057f20f883e', '1112299991');

-- --------------------------------------------------------

--
-- Estrutura da tabela `bairro`
--

CREATE TABLE `bairro` (
  `codBairro` int(11) NOT NULL,
  `nome` varchar(30) NOT NULL,
  `codCidade` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `bairro`
--

INSERT INTO `bairro` (`codBairro`, `nome`, `codCidade`) VALUES
(1, 'Aeroporto', 1),
(3, 'BNH', 1),
(4, 'Ilha do Governador', 2),
(5, 'IBC', 1),
(6, 'Morro Grande', 1),
(8, 'São Geraldo', 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `categoria`
--

CREATE TABLE `categoria` (
  `codCategoria` int(11) NOT NULL,
  `nome` varchar(30) NOT NULL,
  `codSubCategoria` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `categoria`
--

INSERT INTO `categoria` (`codCategoria`, `nome`, `codSubCategoria`) VALUES
(1, 'Buraco', 1),
(2, 'Entulho', 2),
(3, 'Semáforo com Defeito', 1),
(4, 'Água Parada', 2);

-- --------------------------------------------------------

--
-- Estrutura da tabela `cidade`
--

CREATE TABLE `cidade` (
  `codCidade` int(11) NOT NULL,
  `nome` varchar(40) NOT NULL,
  `sigla` char(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `cidade`
--

INSERT INTO `cidade` (`codCidade`, `nome`, `sigla`) VALUES
(1, 'Cachoeiro de Itapemirim', 'ES'),
(2, 'Rio de Janeiro', 'RJ'),
(4, 'Marataizes', 'ES');

-- --------------------------------------------------------

--
-- Estrutura da tabela `denuncia`
--

CREATE TABLE `denuncia` (
  `codDenuncia` int(11) NOT NULL,
  `descricao` varchar(200) NOT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `anonimato` tinyint(1) DEFAULT NULL,
  `complementoStatus` varchar(200) DEFAULT NULL,
  `codBairro` int(11) NOT NULL,
  `codCategoria` int(11) NOT NULL,
  `codUsuario` int(11) DEFAULT NULL,
  `codAdministrador` int(11) DEFAULT NULL,
  `codStatus` int(11) NOT NULL,
  `data` date NOT NULL,
  `hora` time NOT NULL,
  `midia1` varchar(100) NOT NULL,
  `midia2` varchar(100) DEFAULT NULL,
  `midia3` varchar(100) DEFAULT NULL,
  `midia4` varchar(100) DEFAULT NULL,
  `existe` int(11) NOT NULL DEFAULT '0',
  `naoExiste` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `denuncia`
--

INSERT INTO `denuncia` (`codDenuncia`, `descricao`, `latitude`, `longitude`, `anonimato`, `complementoStatus`, `codBairro`, `codCategoria`, `codUsuario`, `codAdministrador`, `codStatus`, `data`, `hora`, `midia1`, `midia2`, `midia3`, `midia4`, `existe`, `naoExiste`) VALUES
(1, 'Buraco enorme na rua, perto da farmácia', -20.831404, -41.174105, 0, 'Complemento status teste', 1, 1, 1, 3, 2, '2017-07-31', '09:00:00', '/midias/buraco1.jpg', NULL, NULL, NULL, 5, 0),
(2, 'Entulho jogado na calçada há mais de 2 meses', -20.815013, -41.158545, 0, 'Complemento status', 6, 2, 5, 1, 5, '2017-08-02', '09:00:00', '/midias/entulho1.jpg', NULL, NULL, NULL, 0, 0),
(3, 'Semáforo com as 3 luzes acesas ao mesmo tempo', -20.789037, -41.154219, 0, 'Complemento status', 6, 3, 3, 1, 1, '2017-08-02', '10:27:08', '/midias/semaforo1.jpg', NULL, NULL, NULL, 0, 0),
(7, 'Descricao teste', -20.8314764, -41.1738851, 1, 'Complemento Status', 1, 3, 1, 4, 3, '2017-08-03', '13:05:07', '/midias/semaforo1.jpg', NULL, NULL, NULL, 0, 0),
(8, 'Pneu com água parada', -20.850377, -41.148989, 0, 'Complemento Status', 1, 4, 1, 4, 2, '2017-08-04', '16:29:25', '/midias/aguaparada1.jpg', NULL, NULL, NULL, 0, 0),
(9, 'Sjeueudx', -20.8032817, -41.1548239, 0, 'Complemento Status', 6, 2, 5, 4, 4, '2017-08-06', '07:10:00', '/midias/entulho1.jpg', NULL, NULL, NULL, 0, 0),
(10, 'Fjdjdjs', -20.832132, -41.157431, 0, 'Complemento Status', 1, 3, 3, 1, 2, '2017-08-13', '21:22:04', '/midias/semaforo1.jpg', NULL, NULL, NULL, 0, 2),
(11, 'Xncmv', -20.8026897, -41.1547281, 1, 'Complemento Status', 6, 2, 5, 6, 2, '2017-08-14', '14:53:49', '/midias/semaforo1.jpg', NULL, NULL, NULL, 0, 1),
(23, 'Aeeehhooooo', -20.831483, -41.1738949, 0, 'Complemento Status', 1, 1, 5, 1, 3, '2017-09-03', '18:40:34', '/midias/img-03092017-184034.png', NULL, NULL, NULL, 4, 22),
(24, 'Vvv', -20.8314876, -41.1738894, 0, 'Complemento Status', 1, 1, 6, 9, 2, '2017-09-27', '17:46:48', '/midias/img-27092017-174648.png', NULL, NULL, NULL, 0, 0),
(25, 'Hhh', -20.8315277, -41.1739434, 0, 'Complemento Status', 1, 1, 3, 1, 7, '2017-09-27', '18:07:00', '/midias/img-27092017-180700.png', NULL, NULL, NULL, 0, 0),
(26, '?gua parada no pneu dentro de um terreno baldio', -20.83151, -41.1739487, 0, 'Complemento Status', 1, 4, 1, 8, 6, '2017-09-30', '23:39:03', '/midias/img-30092017-233903.png', NULL, NULL, NULL, 0, 0),
(27, 'P', -20.8314351, -41.173831, 0, 'Complemento Status', 1, 1, 1, 5, 6, '2017-10-06', '18:25:02', '/midias/img-06102017-182502.png', NULL, NULL, NULL, 0, 0);

-- --------------------------------------------------------

--
-- Estrutura da tabela `status`
--

CREATE TABLE `status` (
  `codStatus` int(11) NOT NULL,
  `nome` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `status`
--

INSERT INTO `status` (`codStatus`, `nome`) VALUES
(1, 'Concluída'),
(2, 'Não Concluída'),
(3, 'Concluída por Negação Popular'),
(4, 'Em Análise'),
(5, 'Em Fase de Correção'),
(6, 'Aguardando Aprovação'),
(7, 'Reprovada');

-- --------------------------------------------------------

--
-- Estrutura da tabela `subcategoria`
--

CREATE TABLE `subcategoria` (
  `codSubCategoria` int(11) NOT NULL,
  `nome` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `subcategoria`
--

INSERT INTO `subcategoria` (`codSubCategoria`, `nome`) VALUES
(1, 'Sub-categoria 1'),
(2, 'Sub-categoria 2');

-- --------------------------------------------------------

--
-- Estrutura da tabela `uf`
--

CREATE TABLE `uf` (
  `sigla` char(2) NOT NULL,
  `nome` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `uf`
--

INSERT INTO `uf` (`sigla`, `nome`) VALUES
('ES', 'Espirito Santo'),
('RJ', 'Rio de Janeiro');

-- --------------------------------------------------------

--
-- Estrutura da tabela `usuario`
--

CREATE TABLE `usuario` (
  `codUsuario` int(11) NOT NULL,
  `nome` varchar(45) NOT NULL,
  `login` varchar(30) NOT NULL,
  `senha` varchar(32) NOT NULL,
  `cpfCnpj` varchar(18) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `usuario`
--

INSERT INTO `usuario` (`codUsuario`, `nome`, `login`, `senha`, `cpfCnpj`) VALUES
(1, 'Matheus Mauricio', 'matheus', 'e10adc3949ba59abbe56e057f20f883e', '11123366781'),
(2, 'Guilherme', 'gui', 'e10adc3949ba59abbe56e057f20f883e', '123747371'),
(3, 'Vinicius', 'vivi', 'e10adc3949ba59abbe56e057f20f883e', '153637372737'),
(5, 'Lucas', 'lucas', 'e10adc3949ba59abbe56e057f20f883e', '11277777442'),
(6, 'João Paulo', 'jp', 'e10adc3949ba59abbe56e057f20f883e', '97884632784');

-- --------------------------------------------------------

--
-- Estrutura da tabela `usuariodenuncia`
--

CREATE TABLE `usuariodenuncia` (
  `codUsuarioDenuncia` int(11) NOT NULL,
  `codUsuario` int(11) NOT NULL,
  `codDenuncia` int(11) NOT NULL,
  `colaboracao` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `usuariodenuncia`
--

INSERT INTO `usuariodenuncia` (`codUsuarioDenuncia`, `codUsuario`, `codDenuncia`, `colaboracao`) VALUES
(1, 1, 23, 'Não Existe'),
(2, 5, 11, 'N?o existe'),
(3, 1, 10, 'N?o Existe');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `administrador`
--
ALTER TABLE `administrador`
  ADD PRIMARY KEY (`codAdministrador`);

--
-- Indexes for table `bairro`
--
ALTER TABLE `bairro`
  ADD PRIMARY KEY (`codBairro`),
  ADD KEY `fk_bairro_cidade` (`codCidade`);

--
-- Indexes for table `categoria`
--
ALTER TABLE `categoria`
  ADD PRIMARY KEY (`codCategoria`),
  ADD KEY `fk_categoria_subCategoria` (`codSubCategoria`);

--
-- Indexes for table `cidade`
--
ALTER TABLE `cidade`
  ADD PRIMARY KEY (`codCidade`),
  ADD KEY `fk_cidade_uf` (`sigla`);

--
-- Indexes for table `denuncia`
--
ALTER TABLE `denuncia`
  ADD PRIMARY KEY (`codDenuncia`),
  ADD KEY `fk_denuncia_bairro` (`codBairro`),
  ADD KEY `fk_denuncia_categoria` (`codCategoria`),
  ADD KEY `fk_denuncia_usuario` (`codUsuario`),
  ADD KEY `fk_denuncia_administrador` (`codAdministrador`),
  ADD KEY `fk_denuncia_status` (`codStatus`);

--
-- Indexes for table `status`
--
ALTER TABLE `status`
  ADD PRIMARY KEY (`codStatus`);

--
-- Indexes for table `subcategoria`
--
ALTER TABLE `subcategoria`
  ADD PRIMARY KEY (`codSubCategoria`);

--
-- Indexes for table `uf`
--
ALTER TABLE `uf`
  ADD PRIMARY KEY (`sigla`);

--
-- Indexes for table `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`codUsuario`);

--
-- Indexes for table `usuariodenuncia`
--
ALTER TABLE `usuariodenuncia`
  ADD PRIMARY KEY (`codUsuarioDenuncia`),
  ADD KEY `fk_usuarioDenuncia_usuario` (`codUsuario`),
  ADD KEY `fk_usuarioDenuncia_denuncia` (`codDenuncia`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `administrador`
--
ALTER TABLE `administrador`
  MODIFY `codAdministrador` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;
--
-- AUTO_INCREMENT for table `bairro`
--
ALTER TABLE `bairro`
  MODIFY `codBairro` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT for table `categoria`
--
ALTER TABLE `categoria`
  MODIFY `codCategoria` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `cidade`
--
ALTER TABLE `cidade`
  MODIFY `codCidade` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `denuncia`
--
ALTER TABLE `denuncia`
  MODIFY `codDenuncia` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;
--
-- AUTO_INCREMENT for table `status`
--
ALTER TABLE `status`
  MODIFY `codStatus` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT for table `subcategoria`
--
ALTER TABLE `subcategoria`
  MODIFY `codSubCategoria` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `usuario`
--
ALTER TABLE `usuario`
  MODIFY `codUsuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `usuariodenuncia`
--
ALTER TABLE `usuariodenuncia`
  MODIFY `codUsuarioDenuncia` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- Constraints for dumped tables
--

--
-- Limitadores para a tabela `bairro`
--
ALTER TABLE `bairro`
  ADD CONSTRAINT `fk_bairro_cidade` FOREIGN KEY (`codCidade`) REFERENCES `cidade` (`codCidade`);

--
-- Limitadores para a tabela `categoria`
--
ALTER TABLE `categoria`
  ADD CONSTRAINT `fk_categoria_subCategoria` FOREIGN KEY (`codSubCategoria`) REFERENCES `subcategoria` (`codSubCategoria`);

--
-- Limitadores para a tabela `cidade`
--
ALTER TABLE `cidade`
  ADD CONSTRAINT `fk_cidade_uf` FOREIGN KEY (`sigla`) REFERENCES `uf` (`sigla`);

--
-- Limitadores para a tabela `denuncia`
--
ALTER TABLE `denuncia`
  ADD CONSTRAINT `fk_denuncia_administrador` FOREIGN KEY (`codAdministrador`) REFERENCES `administrador` (`codAdministrador`),
  ADD CONSTRAINT `fk_denuncia_bairro` FOREIGN KEY (`codBairro`) REFERENCES `bairro` (`codBairro`),
  ADD CONSTRAINT `fk_denuncia_categoria` FOREIGN KEY (`codCategoria`) REFERENCES `categoria` (`codCategoria`),
  ADD CONSTRAINT `fk_denuncia_status` FOREIGN KEY (`codStatus`) REFERENCES `status` (`codStatus`),
  ADD CONSTRAINT `fk_denuncia_usuario` FOREIGN KEY (`codUsuario`) REFERENCES `usuario` (`codUsuario`);

--
-- Limitadores para a tabela `usuariodenuncia`
--
ALTER TABLE `usuariodenuncia`
  ADD CONSTRAINT `fk_usuarioDenuncia_denuncia` FOREIGN KEY (`codDenuncia`) REFERENCES `denuncia` (`codDenuncia`),
  ADD CONSTRAINT `fk_usuarioDenuncia_usuario` FOREIGN KEY (`codUsuario`) REFERENCES `usuario` (`codUsuario`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
