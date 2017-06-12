-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: 05-Jun-2017 às 23:02
-- Versão do servidor: 10.1.21-MariaDB
-- PHP Version: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
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
  `codAdministrador` int(11) UNSIGNED NOT NULL,
  `nome` varchar(45) NOT NULL,
  `login` varchar(30) NOT NULL,
  `senha` varchar(30) NOT NULL,
  `cpfCnpj` varchar(18) NOT NULL
) ;

--
-- Extraindo dados da tabela `administrador`
--

INSERT INTO `administrador` (`codAdministrador`, `nome`, `login`, `senha`, `cpfCnpj`) VALUES
(1, 'Matheus Mauricio', 'matheus', '202cb962ac59075b964b07152d234b', '15587122751'),
(2, 'gabriel', 'gabriel', '123', '11111111111'),
(3, 'cesar', 'cesar', '123', '01010101010'),
(4, 'Andre', 'andre', '123', '1122233412'),
(5, 'Maria', 'maria', '202cb962ac59075b964b07152d234b', '123520005'),
(6, 'Joao', 'joao', '3dfcab79ed21fd89c9eb25e9864a61', '4500178630'),
(7, 'ana', 'ana', '202cb962ac59075b964b07152d234b', '1274728248'),
(8, 'flavio', 'flavio', '202cb962ac59075b964b07152d234b', '2537271637'),
(9, 'Carlos Eduardo', 'dedubr', '6ebe76c9fb411be97b3b0d48b791a7', '12332112331'),
(10, 'ggdxv', 'ffvvc', '8efd540a99f30fac86fad59ff1a45f', '1234679753'),
(11, 'd', 'f', '8277e0910d750195b448797616e091', '13485345'),
(12, 'Juliana', 'juliana', '3f15a07af5f012c55f31a56ce3e705', '16648594928');

-- --------------------------------------------------------

--
-- Estrutura da tabela `bairro`
--

CREATE TABLE `bairro` (
  `codBairro` int(11) NOT NULL,
  `nome` varchar(30) NOT NULL,
  `codCidade` int(11) NOT NULL
) ;

--
-- Extraindo dados da tabela `bairro`
--

INSERT INTO `bairro` (`codBairro`, `nome`, `codCidade`) VALUES
(1, 'Aeroporto', 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `categoria`
--

CREATE TABLE `categoria` (
  `codCategoria` int(11) NOT NULL,
  `nome` varchar(30) NOT NULL,
  `codSubCategoria` int(11) NOT NULL
) ;

--
-- Extraindo dados da tabela `categoria`
--

INSERT INTO `categoria` (`codCategoria`, `nome`, `codSubCategoria`) VALUES
(1, 'Categoria 1', 1),
(2, 'Categoria 2', 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `cidade`
--

CREATE TABLE `cidade` (
  `codCidade` int(11) NOT NULL,
  `nome` varchar(40) NOT NULL,
  `sigla` char(2) NOT NULL
) ;

--
-- Extraindo dados da tabela `cidade`
--

INSERT INTO `cidade` (`codCidade`, `nome`, `sigla`) VALUES
(1, 'Cachoeiro de Itapemirim', 'ES');

-- --------------------------------------------------------

--
-- Estrutura da tabela `subcategoria`
--

CREATE TABLE `subcategoria` (
  `codSubCategoria` int(11) NOT NULL,
  `nome` varchar(30) NOT NULL
) ;

--
-- Extraindo dados da tabela `subcategoria`
--

INSERT INTO `subcategoria` (`codSubCategoria`, `nome`) VALUES
(1, 'Sub-categoria 1');

-- --------------------------------------------------------

--
-- Estrutura da tabela `uf`
--

CREATE TABLE `uf` (
  `sigla` char(2) NOT NULL,
  `nome` varchar(30) NOT NULL
) ;

--
-- Extraindo dados da tabela `uf`
--

INSERT INTO `uf` (`sigla`, `nome`) VALUES
('ES', 'Espírito Santo'),
('RJ', 'Rio de Janeiro');

-- --------------------------------------------------------

--
-- Estrutura da tabela `usuario`
--

CREATE TABLE `usuario` (
  `codUsuario` int(11) NOT NULL,
  `nome` varchar(45) NOT NULL,
  `login` varchar(30) NOT NULL,
  `senha` varchar(30) NOT NULL,
  `cpfCnpj` varchar(18) NOT NULL
) ;

--
-- Extraindo dados da tabela `usuario`
--

INSERT INTO `usuario` (`codUsuario`, `nome`, `login`, `senha`, `cpfCnpj`) VALUES
(1, 'matheus m', 'matheus', 'e10adc3949ba59abbe56e057f20f88', '11123366781'),
(2, 'Guilherme', 'gui', '81dc9bdb52d04dc20036dbd8313ed0', '123747371');

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
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `administrador`
--
ALTER TABLE `administrador`
  MODIFY `codAdministrador` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;
--
-- AUTO_INCREMENT for table `bairro`
--
ALTER TABLE `bairro`
  MODIFY `codBairro` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `categoria`
--
ALTER TABLE `categoria`
  MODIFY `codCategoria` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `cidade`
--
ALTER TABLE `cidade`
  MODIFY `codCidade` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `subcategoria`
--
ALTER TABLE `subcategoria`
  MODIFY `codSubCategoria` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `usuario`
--
ALTER TABLE `usuario`
  MODIFY `codUsuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
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

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
