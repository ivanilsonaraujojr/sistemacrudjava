package com.ivanilsonjr.model.dao;

import com.ivanilsonjr.model.entities.Cliente;

import javafx.collections.ObservableList;


public interface ClienteDao {
	public ObservableList<Cliente> listar();
	public ObservableList<Cliente> listarFiltro(String tipoFiltro, String filtro);
	public boolean adicionarCliente(Cliente cliente);
	public boolean verificarCpfRepetido(String cpf);
	public Cliente procurarPorCpf(String cpf);
	public boolean removerCliente(String cpf);
}
