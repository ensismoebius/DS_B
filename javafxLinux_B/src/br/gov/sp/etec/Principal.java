package br.gov.sp.etec;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Principal extends Application {
	@Override
	public void start(Stage palco) throws Exception {
		VBox layout = new VBox(15);
		Button somar = new Button("Somar");
		Button novaJanela = new Button("Nova Janela");
		
		Label descricaoNumero01 = new Label("Primeiro número");
		TextField numero01 = new TextField();

		Label descricaoNumero02 = new Label("Segundo número");
		TextField numero02 = new TextField();

		Label descricaoResultado = new Label("Resultado");
		TextField resultado = new TextField();

		layout.getChildren().add(descricaoNumero01);
		layout.getChildren().add(numero01);

		layout.getChildren().add(descricaoNumero02);
		layout.getChildren().add(numero02);

		layout.getChildren().add(descricaoResultado);
		layout.getChildren().add(resultado);

		layout.getChildren().add(somar);

		somar.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				float num01 = Float.parseFloat(numero01.getText());
				float num02 = Float.parseFloat(numero02.getText());

				float res = num01 + num02;
				resultado.setText(String.valueOf(res));
			}
		});
		
		novaJanela.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				SelecaoBasica e = new SelecaoBasica();
				e.batataFrita();
			}
		});

		HBox caixasDeSelecao = new HBox(12);

		CheckBox laranja = new CheckBox("Laranja");
		CheckBox melao = new CheckBox("Melão");
		CheckBox limao = new CheckBox("Limão");
		CheckBox manga = new CheckBox("Manga");
		CheckBox kiwi = new CheckBox("Kiwi");

		caixasDeSelecao.getChildren().addAll( //
				laranja, //
				melao, //
				limao, //
				manga, //
				kiwi //
		);

		HBox caixasDeEscolha = new HBox(12);

		RadioButton ig = new RadioButton("Instagram");
		RadioButton fb = new RadioButton("Facebook");
		RadioButton bk = new RadioButton("Blue Sky");
		RadioButton ok = new RadioButton("Orkut");
		RadioButton ko = new RadioButton("Koo");

		ToggleGroup grupoRedeSocial = new ToggleGroup();
		ig.setToggleGroup(grupoRedeSocial);
		fb.setToggleGroup(grupoRedeSocial);
		bk.setToggleGroup(grupoRedeSocial);
		ok.setToggleGroup(grupoRedeSocial);
		ko.setToggleGroup(grupoRedeSocial);

		caixasDeEscolha.getChildren().addAll( //
				ig, //
				fb, //
				bk, //
				ok, //
				ko //
		);

		layout.getChildren().add(caixasDeSelecao);
		layout.getChildren().add(caixasDeEscolha);

		Label descricaoComboBox = new Label("Entrega");
		ComboBox<String> locaisDeEntrega = new ComboBox<>();
		locaisDeEntrega.getItems().addAll("Vila Constança", "Botafogo", "Perus", "Recando dos Humildes",
				"Jardim D'Abril", "Vila Mariana");

		locaisDeEntrega.setPromptText("Selecione um bairro");
		locaisDeEntrega.setPrefWidth(200);

		layout.getChildren().addAll(descricaoComboBox, locaisDeEntrega, novaJanela);

		Scene cena = new Scene(layout);
		palco.setScene(cena);
		palco.show();
	}

	public static void main(String args[]) {
		launch(args);
	}
}
