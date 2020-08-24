package com.ivanilsonjr.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ivanilsonjr.model.dao.ClienteDao;
import com.ivanilsonjr.model.entities.Cliente;
import com.ivanilsonjr.view.util.Alerts;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert.AlertType;

public class ClienteJDBC implements ClienteDao{
	private Connection conn;
	
	public ClienteJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public ObservableList<Cliente> listar() {
        String sql = "SELECT * FROM tbcliente";
        List<Cliente> listaClientes = new ArrayList<>();
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet resultado = st.executeQuery();
            while (resultado.next()) {
                Cliente cliente = new Cliente();
                cliente.setNome(resultado.getString("nome"));
                cliente.setCpf(resultado.getString("cpf"));
                cliente.setDataNascimento(resultado.getDate("datanascimento"));
                listaClientes.add(cliente);
            }
            ObservableList<Cliente> listaRetorno = FXCollections.observableArrayList(listaClientes);
            return listaRetorno;
        } catch (SQLException e) {
            System.err.println("ERRO: "+e.getMessage());
        }
        return null;
    }

	@Override
	public ObservableList<Cliente> listarFiltro(String tipoFiltro, String filtro) {
		List<Cliente> listaClientes = new ArrayList<>();
		try {
			String sql = "";
			switch(tipoFiltro) {
			case "nome": sql = "SELECT * FROM tbcliente WHERE nome ILIKE ?"; break;
			case "cpf": sql = "SELECT * FROM tbcliente WHERE cpf LIKE ?"; break;
			case "datanascimento": sql = "SELECT * FROM tbcliente WHERE datanascimento = ?::date"; break;
			}
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, "%" + filtro + "%");
            ResultSet resultado = st.executeQuery();
            while (resultado.next()) {
                Cliente cliente = new Cliente();
                cliente.setNome(resultado.getString("nome"));
                cliente.setCpf(resultado.getString("cpf"));
                cliente.setDataNascimento(resultado.getDate("datanascimento"));
                listaClientes.add(cliente);
            	}
            ObservableList<Cliente> listaRetorno = FXCollections.observableArrayList(listaClientes);
            return listaRetorno;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
        }
		return null;
	}

	@Override
	public boolean adicionarCliente(Cliente cliente) {
		  String sql = "INSERT INTO tbcliente (nome, cpf, datanascimento) VALUES (?,?,?::date)";
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, cliente.getNome());
			st.setString(2, cliente.getCpf());
			st.setString(3, cliente.getDataNascimento());
			st.executeUpdate();
			return true;
		}catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return false;
	}

	@Override
	public boolean verificarCpfRepetido(String cpf) {
		String sql = "SELECT * FROM tbcliente WHERE cpf = ?";
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, cpf);
			ResultSet resultado = st.executeQuery();
			while(resultado.next()) {
				if(resultado.getString("cpf").hashCode() == cpf.hashCode()) {
					return true;
				}
			}
		}catch (SQLException e) {
			e.getMessage();
			}
		return false;
	}

	@Override
	public Cliente procurarPorCpf(String cpf) {
		String sql = "SELECT * FROM tbcliente WHERE cpf = ?";
		Cliente cliente = new Cliente();
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, cpf);
			ResultSet resultado = st.executeQuery();
			if(resultado.next()) {
				cliente.setNome(resultado.getString("nome"));
				cliente.setCpf(resultado.getString("cpf"));
				cliente.setDataNascimento(resultado.getDate("datanascimento"));
				return cliente;
			}else {
				Alerts.showAlert(null, null, "Nenhum cliente com esse cpf cadastrado no sistema", AlertType.ERROR);
				return null;
			}
		}catch (SQLException e) {
			e.getMessage();
			return null;
		}
	}

	@Override
	public boolean removerCliente(String cpf) {
		String sql = "DELETE FROM tbcliente WHERE cpf = ?";
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, cpf);
			st.executeQuery();
			return true;
		}catch(SQLException e) {
			e.getMessage();
		}
		return false;
	}

}
