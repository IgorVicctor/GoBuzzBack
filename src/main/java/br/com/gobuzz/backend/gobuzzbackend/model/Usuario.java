package br.com.gobuzz.backend.gobuzzbackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String senha;
    private String tipo_usuario;
    private String faculdade;
    private String periodo;
    private String curso;
    private String matricula;
    private String veiculo;
    private String cnh;
    private String validade;
    private String selecionaDIas;
    private String cidade;

    @Column(name = "is_aluno")
    private boolean isAluno;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "motorista_id")
    private Usuario motorista;

    @JsonManagedReference
    @OneToMany(mappedBy = "motorista")
    private List<Usuario> alunos;

    @Column(name = "imagem", columnDefinition = "BLOB")
    private byte[] imagem;


    public byte[] getImagem() {
        return imagem;
    }
    
    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }
    

    public String getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(String veiculo) {
        this.veiculo = veiculo;
    }

    public String getCnh() {
        return cnh;
    }

    public void setCnh(String cnh) {
        this.cnh = cnh;
    }

    public String getValidade() {
        return validade;
    }

    public void setValidade(String validade) {
        this.validade = validade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipo_usuario() {
        return tipo_usuario;
    }

    public void setTipo_usuario(String tipo_usuario) {
        this.tipo_usuario = tipo_usuario;
    }

    public String getFaculdade() {
        return faculdade;
    }

    public void setFaculdade(String faculdade) {
        this.faculdade = faculdade;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    // public String getDiasDeTransporte() {
    //     return dias_de_transporte;
    // }

    // public void setDiasDeTransporte(String dias_de_transporte) {
    //     this.dias_de_transporte = dias_de_transporte;
    // }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public boolean isAluno() {
        return isAluno;
    }

    public void setAluno(boolean isAluno) {
        this.isAluno = isAluno;
    }

    public Usuario getMotorista() {
        return motorista;
    }

    public void setMotorista(Usuario motorista) {
        this.motorista = motorista;
    }

    // public Usuario getDias() {
    //     return motorista;
    // }

    // public void setDias(Usuario motorista) {
    //     this.motorista = motorista;
    // }

    public String getSelecionaDias() {
        return selecionaDIas;
    }

    public void setSelecionaDias(String selecionaDIas) {
        this.selecionaDIas = selecionaDIas;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
    
    public List<Usuario> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<Usuario> alunos) {
        this.alunos = alunos;
    }

    // Método para adicionar um aluno existente ao ônibus do motorista
    public void adicionarAluno(Usuario aluno) {
        if (aluno.isAluno()) {
            if (motorista == null) {
                motorista = aluno; // Atribua o próprio aluno como motorista
                motorista.setAluno(false);
            }
            if (alunos == null) {
                alunos = new ArrayList<>();
            }
            alunos.add(aluno);
            aluno.setMotorista(this); // Configure o motorista do aluno
        }
    }
}