package com.ivanilsonjr.controller;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import com.ivanilsonjr.model.dao.ClienteDao;
import com.ivanilsonjr.model.dao.DaoFactory;
import com.ivanilsonjr.model.entities.Cliente;
import com.ivanilsonjr.view.util.Alerts;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;

public class ListarClientesController implements Initializable {

	@FXML
	private RadioButton filtrarNomeRadio;
	@FXML
	private RadioButton filtrarCpfRadio;
	@FXML
	private RadioButton filtrarDataNascimentoRadio;
	@FXML
	private TextField filtroConteudo;
	@FXML
	private Button filtrarBt;
	@FXML
	private TableView<Cliente> tabelaDados;
	@FXML
	private TableColumn<String, Cliente> campoNome;
	@FXML
	private TableColumn<String, Cliente> campoCpf;
	@FXML
	private TableColumn<Date, Cliente> campoDataNascimento;
	@FXML
	private Pagination paginacao;
	@FXML
	private Button removerFiltrosBt;
	private ToggleGroup group = new ToggleGroup();

	ClienteDao clienteDao = DaoFactory.createClienteDao();

	public void pressionouNome() {
		filtrarNomeRadio.setToggleGroup(group);
		filtroConteudo.clear();
		filtroConteudo.setPromptText("Digite um nome");
	}

	public void pressionouCpf() {
		filtrarCpfRadio.setToggleGroup(group);
		filtroConteudo.clear();
		filtroConteudo.setPromptText("Digite um CPF");

	}

	public void pressionouDataNascimento() {
		filtrarDataNascimentoRadio.setToggleGroup(group);
		filtroConteudo.clear();
		filtroConteudo.setPromptText("(dd/mm/AAAA)");

	}

	public boolean validarFiltro() {
		boolean valido = false;
		int hashCodeToggle = 0;
		String textoInserido = filtroConteudo.getText();
		try {
			hashCodeToggle = group.getSelectedToggle().hashCode();
		} catch (RuntimeException e) {
			Alerts.showAlert(null, null, "Você não selecionou nenhum tipo", AlertType.ERROR);
			return false;
		}

		if (hashCodeToggle != 0) {
			if (textoInserido.trim().isEmpty()) {
				Alerts.showAlert(null, null, "Preencha o campo de filtro", AlertType.ERROR);
				valido = false;
			} else {
				if (hashCodeToggle == filtrarNomeRadio.hashCode()) {
					if (validarTextoInserido(textoInserido.trim(), "^[\\p{L} \\.'\\-]+$", "letras")) {
						valido = true;
					}
				} else if (hashCodeToggle == filtrarCpfRadio.hashCode()) {
					if (validarTextoInserido(textoInserido, "^[0-9]+$", "numeros")) {
						valido = true;
					}
				} else if (hashCodeToggle == filtrarDataNascimentoRadio.hashCode()) {
					if (validarTextoInserido(textoInserido,
							"^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$",
							"data de nascimento(AAAA/mm/dd)")) {
						valido = true;
					}
				}
			}
		}
		return valido;
	}

	public boolean validarTextoInserido(String textoInserido, String regex, String tipo) {
		if (!textoInserido.matches(regex)) {
			Alerts.showAlert(null, null, "Permitido apenas " + tipo, AlertType.ERROR);
			return false;
		} else {
			return true;
		}
	}

	public void botaoFiltrar() {
		RadioButton botaoSelecionado = (RadioButton) group.getSelectedToggle();
		if (validarFiltro()) {
			switch (botaoSelecionado.getText()) {
			case "Nome":
				filtrar("nome", filtroConteudo.getText());
				break;
			case "CPF":
				filtrar("cpf", filtroConteudo.getText());
				break;
			case "Data de nascimento":
				filtrar("datanascimento", filtroConteudo.getText());
				break;
			}
		}

	}

	public void filtrar(String tipoFiltro, String filtro) {
		tabelaDados.setItems(clienteDao.listarFiltro(tipoFiltro, filtro));
		removerFiltrosBt.setVisible(true);
	}

	public void removerFiltros() {
		group.selectToggle(null);
		filtroConteudo.setText("");
		filtroConteudo.setPromptText("");
		removerFiltrosBt.setVisible(false);
		tabelaDados.setItems(clienteDao.listar());
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		campoNome.setCellValueFactory(new PropertyValueFactory<String, Cliente>("nome"));
		campoCpf.setCellValueFactory(new PropertyValueFactory<String, Cliente>("cpf"));
		campoDataNascimento.setCellValueFactory(new PropertyValueFactory<Date, Cliente>("dataNascimento"));
		tabelaDados.setItems(clienteDao.listar());
	}
}