package br.gov.sp.etec;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SelecaoBasica extends Stage {

	public void batataFrita() {
		VBox layout = new VBox(15);

		HBox nome = new HBox(5);
		HBox bairro = new HBox(5);
		HBox botoes = new HBox(5);

		Label lblNome = new Label("Nome: ");
		Label lblBairro = new Label("Bairro: ");

		TextField txtNome = new TextField();
		ComboBox<String> cmbBairro = new ComboBox<>();

		CheckBox sempre = new CheckBox("Sempre");
		CheckBox tempor = new CheckBox("Temp");

		Button btnSalvar = new Button("Salvar");
		Button btnLimpar = new Button("Limpar");

		layout.getChildren().addAll(nome, bairro, botoes);
		nome.getChildren().addAll(lblNome, txtNome);
		bairro.getChildren().addAll(lblBairro, cmbBairro);
		botoes.getChildren().addAll(btnSalvar, btnLimpar, sempre, tempor);

		Scene cena = new Scene(layout);
		this.setScene(cena);
		this.show();
	}
}
