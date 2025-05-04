package testepraticosaam.model;

import java.util.Date;

public class Funcionario {
    private int id;
    private String nome;
    private Date dataAdmissao;
    private double salario;
    private boolean status;

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public Date getDataAdmissao() { return dataAdmissao; }
    public void setDataAdmissao(Date dataAdmissao) { this.dataAdmissao = dataAdmissao; }
    public double getSalario() { return salario; }
    public void setSalario(double salario) { this.salario = salario; }
    public boolean isStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }
}