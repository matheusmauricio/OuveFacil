package control;

import dao.CidadeDao;
import dao.ClienteDao;
import dao.EmprestimoDao;
import dao.FilmeDao;
import dao.FitaDao;
import dao.ItemDeEmprestimoDao;
import dao.TipoDeFilmeDao;
import dao.UFDao;
import domain.Cidade;
import domain.Cliente;
import domain.Filme;
import java.util.Date;
import domain.TipoDeFilme;
import domain.Emprestimo;
import domain.Fita;
import domain.ItemDeEmprestimo;
import domain.UF;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SCV {

    public static Connection connection;
    
    public static ArrayList selecionarTodosClientes() {

        ArrayList<Cliente> clientes = new ArrayList();
        try {
            ClienteDao clienteDao = new ClienteDao();
            clienteDao.setConnection(connection);
            clientes = clienteDao.selecionarTodosClientes();
            return clientes;
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(SCV.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return null;
    }

    public static boolean inserirEmprestimo(Emprestimo emprestimo) {
        try {

            //Desabilitando o commit automático (isso pois, caso existam problemas na inserção de itens de emprestimos,
            //mesmo depois de termos inserido um empréstimo. Neste caso, o comando rollback apagaria o empréstimo.
            connection.setAutoCommit(false);

            //Instanciando um Tipo de Filme Dao
            TipoDeFilmeDao tipoDeFilmeDao = new TipoDeFilmeDao();
            tipoDeFilmeDao.setConnection(connection);

            //Instanciando um Filme Dao
            FilmeDao filmeDao = new FilmeDao();
            filmeDao.setConnection(connection);

            //Preenchendo completamente as Fitas (Filmes e Tipos de Filmes)
            ArrayList<ItemDeEmprestimo> itensDeEmprestimo = emprestimo.getItemDeEmprestimo();

            for (ItemDeEmprestimo itemDeEmprestimo : itensDeEmprestimo) {

                //Setando o Filme com todas as informações na Fita
                itemDeEmprestimo.getFita().setFilme(filmeDao.selecionarFilme(itemDeEmprestimo.getFita().getFilme()));

                //Setando o Tipo de Filme com todas as informações na Fita
                itemDeEmprestimo.getFita().getFilme().setTipoDeFilme(tipoDeFilmeDao.selecionarTipoDeFilme(itemDeEmprestimo.getFita().getFilme().getTipoDeFilme()));

                //Setando o Valor do Item de Empréstimo baseado no valor do Tipo de Filme da Fita
                itemDeEmprestimo.setValor(itemDeEmprestimo.getFita().getFilme().getTipoDeFilme().getPreco());
                //Setando o Prazo do Item de Empréstimo baseado no prazo do Tipo de Filme da Fita
                itemDeEmprestimo.setDataDeEntrega(calcularDataEntrega(itemDeEmprestimo.getFita().getFilme().getTipoDeFilme()));
            }

            //Preparando para Inserir um Empréstimo no Banco de Dados
            EmprestimoDao emprestimoDao = new EmprestimoDao();
            emprestimoDao.setConnection(connection);

            //Setando o valor total do empréstimo
            emprestimo.setValor(calcularValorEmprestimo(emprestimo));
            if (emprestimoDao.inserirEmprestimo(emprestimo)) {

                //Instanciando um Item de Emprestimo Dao
                ItemDeEmprestimoDao itemDeEmprestimoDao = new ItemDeEmprestimoDao();
                itemDeEmprestimoDao.setConnection(connection);

                //Obtendo o último empréstimo inserido no Banco de Dados
                Emprestimo ultimoEmprestimo = emprestimoDao.selecionarUltimoEmprestimo();

                //Inserindo os Itens de Empréstimos no Banco de Dados
                for (ItemDeEmprestimo itemDeEmprestimo : itensDeEmprestimo) {
                    //Setando o Emprestimo no Item de Empréstimo
                    itemDeEmprestimo.setEmprestimo(ultimoEmprestimo);

                    //Inserindo Item de Empréstimo
                    itemDeEmprestimoDao.inserirItemDeEmprestimo(itemDeEmprestimo);
                }
                connection.commit();
                return true;
            }
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(SCV.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println(ex1.getMessage());
            }
        }
        return false;
    }

    public static Filme selecionarFilme(Filme filme) {
        try {
            FilmeDao filmeDao = new FilmeDao();
            filmeDao.setConnection(connection);
            filme = filmeDao.selecionarFilme(filme);
            return filme;
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(SCV.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return null;
    }

    public static ArrayList<Filme> selecionarTodosFilmes() {
        try {
            FilmeDao filmeDao = new FilmeDao();
            filmeDao.setConnection(connection);
            return filmeDao.selecionarTodosFilmes();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(SCV.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return null;
    }

    public static ArrayList<Fita> selecionarTodasFitas() {
        try {
            FitaDao fitaDao = new FitaDao();
            fitaDao.setConnection(connection);
            return fitaDao.selecionarTodasFitas();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(SCV.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return null;
    }

    public static TipoDeFilme selecionarTipoDeFilme(TipoDeFilme tipoDeFilme) {
        try {
            TipoDeFilmeDao tipoDeFilmeDao = new TipoDeFilmeDao();
            tipoDeFilmeDao.setConnection(connection);
            tipoDeFilme = tipoDeFilmeDao.selecionarTipoDeFilme(tipoDeFilme);
            return tipoDeFilme;
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(SCV.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return null;
    }

    public static ArrayList<Fita> selecionarFitasPorFilme(Filme filme) {
        try {
            FitaDao fitaDao = new FitaDao();
            fitaDao.setConnection(connection);
            return fitaDao.selecionarFitasPorFilme(filme);
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(SCV.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return null;
    }

    public static Date calcularDataEntrega(TipoDeFilme tipoDeFilme) {
        //Setando o Prazo do Item de Empréstimo baseado no prazo do Tipo de Filme da Fita
        Calendar data = Calendar.getInstance();

        data.add(Calendar.DAY_OF_MONTH, tipoDeFilme.getDiasDePrazo());
        return data.getTime();
    }

    public static float calcularValorEmprestimo(Emprestimo emprestimo) {
        float soma = 0;
        ArrayList<ItemDeEmprestimo> itensDeEmprestimo = emprestimo.getItemDeEmprestimo();

        for (ItemDeEmprestimo itemDeEmprestimo : itensDeEmprestimo) {
            soma = soma + itemDeEmprestimo.getFita().getFilme().getTipoDeFilme().getPreco();
        }
        return soma;
    }

    public static boolean inserirUF(UF uf) {
        try {
            UFDao ufDao = new UFDao();
            ufDao.setConnection(connection);
            ufDao.inserirUF(uf);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(SCV.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static ArrayList selecionarTodasUFs() {
        ArrayList<UF> ufs = new ArrayList();
        try {
            UFDao ufDao = new UFDao();
            ufDao.setConnection(connection);
            ufs = ufDao.selecionarTodasUFs();
            return ufs;
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(SCV.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return null;
    }
    
    public static boolean inserirCidade(Cidade cidade) {
        try {
            CidadeDao cidadeDao = new CidadeDao();
            cidadeDao.setConnection(connection);
            cidadeDao.inserirCidade(cidade);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(SCV.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
