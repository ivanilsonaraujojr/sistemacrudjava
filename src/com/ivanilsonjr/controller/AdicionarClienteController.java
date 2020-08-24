package com.ivanilsonjr.controller;

import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;

import com.ivanilsonjr.model.dao.ClienteDao;
import com.ivanilsonjr.model.dao.DaoFactory;
import com.ivanilsonjr.model.entities.Cliente;
import com.ivanilsonjr.view.util.Alerts;

import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class AdicionarClienteController {
	@FXML
	private TextField campoNome;
	@FXML
	private TextField campoCpf;
	@FXML
	private DatePicker campoDataNascimento;
	@FXML
	private Button botaoAdicionar;

	ClienteDao clienteDao = DaoFactory.createClienteDao();

	public void pegarDadosInseridos() {
		if (validarCamposInseridos()) {
			LocalDate localdate = campoDataNascimento.getValue();
			Date dataNascimento = Date.from(localdate.atStartOfDay(ZoneId.systemDefault()).toInstant());
			Cliente cliente = new Cliente();
			cliente.setNome(campoNome.getText());
			if (!(clienteDao.verificarCpfRepetido(campoCpf.getText()))) {
				cliente.setCpf(campoCpf.getText());
				cliente.setDataNascimento(dataNascimento);
				if (clienteDao.adicionarCliente(cliente)) {
					Alerts.showAlert(null, null, "Cliente adicionado", AlertType.INFORMATION);
				}
			} else {
				Alerts.showAlert(null, null, "CPF ja cadastrado no sistema", AlertType.ERROR);
			}

		}
	}

	public boolean validarCamposInseridos() {
		if ((!(campoNome.getText().matches("^[\\p{L} \\.'\\-]+$"))) || campoNome.getText().length() < 6) {
			Alerts.showAlert(null, null, "Formato de nome errado", AlertType.ERROR);
			return false;
		} else if (!(campoCpf.getText().matches("^[0-9]+$")) || campoCpf.getText().length() > 11) {
			Alerts.showAlert(null, null, "Formato do CPF errado", AlertType.ERROR);
			return false;
		} else if (campoDataNascimento.getValue() == null) {
			Alerts.showAlert(null, null, "Insira a data de nascimento", AlertType.ERROR);
			return false;
		}
		return true;
	}
}
