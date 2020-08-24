package com.ivanilsonjr.controller;

import com.ivanilsonjr.model.dao.ClienteDao;
import com.ivanilsonjr.model.dao.DaoFactory;
import com.ivanilsonjr.model.entities.Cliente;
import com.ivanilsonjr.view.util.Alerts;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;

public class RemoverClienteController {

	@FXML
	private TextField campoCpf;
	@FXML
	private Button procurarBt;
	@FXML
	private TextArea nomeEncontrado;
	@FXML
	private Pane dadosEncontrados;
	@FXML
	private TextArea dataNascimentoEncontrada;
	@FXML
	private Button deletarCliente;

	ClienteDao clienteDao = DaoFactory.createClienteDao();

	public void procurarDadosBanco() {
		Cliente cliente = clienteDao.procurarPorCpf(campoCpf.getText());
		if (cliente != null) {
			dadosEncontrados.setVisible(true);
			nomeEncontrado.setText(cliente.getNome());
			dataNascimentoEncontrada.setText(cliente.getDataNascimento());
		}
	}

	public void deletarCliente() {
		Cliente cliente = clienteDao.procurarPorCpf(campoCpf.getText());
		if(cliente != null) {
			clienteDao.removerCliente(cliente.getCpf());
			Alerts.showAlert(null, null, "Cliente removido do sistema!!", AlertType.INFORMATION);
			dadosEncontrados.setVisible(false);
			campoCpf.setText("");
		}
		
	}

}
