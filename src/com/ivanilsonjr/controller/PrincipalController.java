package com.ivanilsonjr.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class PrincipalController {

	@FXML
	private Button listarClientesbt;
	@FXML
	private Button adicionarClientebt;
	@FXML
	private Button removerClientebt;

	@FXML
	public void listarClientes() {
		abrir("/com/ivanilsonjr/view/ListarClientes.fxml", "Listar Clientes");
	}

	@FXML
	public void adicionarCliente() {
		abrir("/com/ivanilsonjr/view/AdicionarCliente.fxml", "Adicionar Cliente");
	}
	
	@FXML
	public void removerCliente() {
		abrir("/com/ivanilsonjr/view/RemoverCliente.fxml", "Remover Cliente");
	}

	private void abrir(String url, String title) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(url));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setResizable(false);
			stage.centerOnScreen();
			stage.setWidth(680);
			stage.setHeight(500);
			stage.setScene(new Scene(root1));
			stage.setTitle(title);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}