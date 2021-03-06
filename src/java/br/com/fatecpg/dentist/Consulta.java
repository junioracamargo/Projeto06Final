package br.com.fatecpg.dentist;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;


public class Consulta {
    private long id_consulta;
    private Date dia;
    private Date hr_inicio;
    private Date hr_fim;
    private String status;
    private double preco;
    private String obs;
    private long id_dentista;
    private long id_cliente;

    private String nomeCliente;
    private String nomeDentista;
    private String croDentista;

    public Consulta(Date dia, Date hr_inicio, Date hr_fim, String status, String obs, String nomeCliente) {
        this.dia = dia;
        this.hr_inicio = hr_inicio;
        this.hr_fim = hr_fim;
        this.status = status;
        this.obs = obs;
        this.nomeCliente = nomeCliente;
    }

    public Consulta(Long id_consulta, Date dia, Date hr_inicio, Date hr_fim, String status, double preco, String obs, String nomeCliente, String nomeDentista, String croDentista) {
        this.id_consulta = id_consulta;
        this.dia = dia;
        this.hr_inicio = hr_inicio;
        this.hr_fim = hr_fim;
        this.status = status;
        this.preco = preco;
        this.obs = obs;
        this.nomeCliente = nomeCliente;
        this.nomeDentista = nomeDentista;
        this.croDentista = croDentista;
    }
    
    public long getId_consulta() {
        return id_consulta;
    }

    public void setId_consulta(long id_consulta) {
        this.id_consulta = id_consulta;
    }

    public Date getHr_inicio() {
        return hr_inicio;
    }

    public void setHr_inicio(Date hr_inicio) {
        this.hr_inicio = hr_inicio;
    }

    public Date getHr_fim() {
        return hr_fim;
    }

    public void setHr_fim(Date hr_fim) {
        this.hr_fim = hr_fim;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public long getId_dentista() {
        return id_dentista;
    }

    public void setId_dentista(long id_dentista) {
        this.id_dentista = id_dentista;
    }

    public long getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(long id_cliente) {
        this.id_cliente = id_cliente;
    }
    public Date getDia() {
        return dia;
    }

    public void setDia(Date dia) {
        this.dia = dia;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getNomeDentista() {
        return nomeDentista;
    }

    public void setNomeDentista(String nomeDentista) {
        this.nomeDentista = nomeDentista;
    }
    
    public String getCroDentista() {
        return croDentista;
    }

    public void setCroDentista(String croDentista) {
        this.croDentista = croDentista;
    }

    public static ArrayList<Consulta> getConsulta() throws Exception{
        String SQL = "SELECT a.id_consulta, a.dia, a.hr_inicio, a.hr_fim,  a.status, a.preco, a.obs, usuariocli.nome, usuariodent.nome, dentista.cro FROM tb_consulta a, tb_usuario usuariocli, tb_usuario usuariodent, tb_dentista dentista, tb_cliente cliente WHERE dentista.ID_DENTISTA = a.ID_DENTISTA and a.ID_CLIENTE = cliente.ID_CLIENTE and usuariocli.ID_USUARIO = cliente.ID_USUARIO and usuariodent.ID_USUARIO = dentista.ID_USUARIO ORDER BY a.DIA";
        ArrayList<Consulta> consultas = new ArrayList<>();
        ArrayList<Object[]> list = DatabaseConnector.getQuery(SQL, new Object[]{});
        for(int i = 0; i < list.size(); i++){
            Object row[] = list.get(i);
            Consulta c = new Consulta(
                    (Long) row[0]
                    , (Date) row[1]
                    , (Date) row[2]
                    , (Date) row[3]
                    , (String) row[4]
                    , (Double) row[5]
                    , (String) row[6]
                    , (String) row[7]
                    , (String) row[8]
                    , (String) row[9]);
            consultas.add(c);
        }
        return consultas;
    }
    public static ArrayList<Consulta> getConsultaUsuariosDentista(Long dentista) throws Exception{
        String SQL = "SELECT a.dia, a.hr_inicio, a.hr_fim, a.status, a.obs, b.nome FROM tb_consulta a, tb_usuario b, tb_cliente c, tb_dentista dentista WHERE a.ID_CLIENTE = c.ID_CLIENTE and c.ID_USUARIO = b.ID_USUARIO and dentista.ID_DENTISTA = ?";
        ArrayList<Consulta> consultas = new ArrayList<>();
        ArrayList<Object[]> list = DatabaseConnector.getQuery(SQL, new Object[]{dentista});
        for(int i = 0; i < list.size(); i++){
            Object row[] = list.get(i);
            Consulta c = new Consulta(
                    (Date) row[0]
                    , (Date) row[1]
                    , (Date) row[2]
                    , (String) row[3]
                    , (String) row[4]
                    , (String) row[5]);
            consultas.add(c);
        }
        return consultas;
    }
    
    public static void addConsulta(String dia, String horaInicio, String horaFim, double preco, String observacao, Long dentista, Long cliente) throws Exception{
        String SQL = "INSERT INTO tb_consulta VALUES("
                + "default"
                + ", ?"
                + ", ?"
                + ", ?"
                + ", default"
                + ", ?"
                + ", ?"
                + ", ?"
                + ", ?"
                + ")";
        Object parameters[] = {dia, horaInicio, horaFim, preco, observacao, dentista, cliente};
        DatabaseConnector.execute(SQL, parameters);
    }
    
    public static void removeConsulta(long id_consulta) throws Exception{
        String SQL = "DELETE FROM tb_consulta WHERE id_consulta = ?";
        Object parameters[] = {id_consulta};
        DatabaseConnector.execute(SQL, parameters);
    }
    
    public static void alterConsulta(long id_usuario, String nome, String telefone, String login, long hashSenha) throws Exception {
        String SQL = "UPDATE tb_usuario SET "
                + "nome = '" + nome
                + "', telefone = '" + telefone
                + "', login = '" + login
                + "', hashSenha = " + hashSenha
                + " WHERE id_usuario = " + id_usuario;
        Object parameters[] = {};
        DatabaseConnector.execute(SQL, parameters);
    }
    public static void cancelarConsulta(Long id_consulta) throws Exception {
        String SQL = "UPDATE tb_consulta SET status = 'CANCELADA' WHERE id_consulta = ?";
        Object parameters[] = {id_consulta};
        DatabaseConnector.execute(SQL, parameters);
    }
}
