package com.ivanilsonjr.model.entities;

import java.util.Date;
import java.text.SimpleDateFormat;

public class Cliente {
	private String nome;
	private String cpf;
	private Date dataNascimento;
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getDataNascimento() {
		 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); 
		 return sdf.format(dataNascimento);
	}
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	
}
