package br.gov.sp.etec;

import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Exemplo de Animações e Transitions
 * Mostra várias transições do JavaFX e como combiná-las.
 */
public class Principal extends Application {

    @Override
    public void start(Stage stage) {
        Rectangle rect = new Rectangle(120, 80, Color.CORNFLOWERBLUE);
        rect.setArcWidth(20);
        rect.setArcHeight(20);

        Circle circle = new Circle(30, Color.SALMON);

        // Fade transition (retângulo)
        FadeTransition fade = new FadeTransition(Duration.seconds(1.5), rect);
        fade.setFromValue(1.0);
        fade.setToValue(0.2);
        fade.setAutoReverse(true);
        fade.setCycleCount(2);

        // Translate transition (círculo)
        TranslateTransition translate = new TranslateTransition(Duration.seconds(1.2), circle);
        translate.setByX(180);
        translate.setAutoReverse(true);
        translate.setCycleCount(2);

        // Rotate transition (retângulo)
        RotateTransition rotate = new RotateTransition(Duration.seconds(1.2), rect);
        rotate.setByAngle(360);

        // Scale transition (retângulo)
        ScaleTransition scale = new ScaleTransition(Duration.seconds(1.0), rect);
        scale.setToX(1.4);
        scale.setToY(1.4);
        scale.setAutoReverse(true);
        scale.setCycleCount(2);

        // Path transition (círculo ao longo de um caminho)
        Path path = new Path();
        path.getElements().add(new MoveTo(0, 0));
        path.getElements().add(new LineTo(80, -60));
        path.getElements().add(new LineTo(180, 0));
        PathTransition pathTrans = new PathTransition(Duration.seconds(2.0), path, circle);
        pathTrans.setAutoReverse(true);
        pathTrans.setCycleCount(2);

        // Sequential and Parallel transitions
        SequentialTransition seq = new SequentialTransition(rect, scale, rotate, fade);
        ParallelTransition par = new ParallelTransition(rect, rotate, scale);

        // Timeline example (pulsar opacidade do círculo)
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(circle.opacityProperty(), 1.0)),
                new KeyFrame(Duration.seconds(0.6), new KeyValue(circle.opacityProperty(), 0.3)),
                new KeyFrame(Duration.seconds(1.2), new KeyValue(circle.opacityProperty(), 1.0)));
        timeline.setCycleCount(2);

        // Botões para controlar
        Button btnFade = new Button("Fade");
        btnFade.setOnAction(e -> fade.playFromStart());

        Button btnTranslate = new Button("Translate");
        btnTranslate.setOnAction(e -> translate.playFromStart());

        Button btnRotate = new Button("Rotate");
        btnRotate.setOnAction(e -> rotate.playFromStart());

        Button btnScale = new Button("Scale");
        btnScale.setOnAction(e -> scale.playFromStart());

        Button btnPath = new Button("Path");
        btnPath.setOnAction(e -> pathTrans.playFromStart());

        Button btnSeq = new Button("Sequential");
        btnSeq.setOnAction(e -> seq.playFromStart());

        Button btnPar = new Button("Parallel");
        btnPar.setOnAction(e -> par.playFromStart());

        Button btnTimeline = new Button("Timeline");
        btnTimeline.setOnAction(e -> timeline.playFromStart());

        Button btnReset = new Button("Reset");
        btnReset.setOnAction(e -> {
            fade.stop();
            translate.stop();
            rotate.stop();
            scale.stop();
            pathTrans.stop();
            seq.stop();
            par.stop();
            timeline.stop();
            rect.setOpacity(1);
            rect.setRotate(0);
            rect.setScaleX(1);
            rect.setScaleY(1);
            rect.setTranslateX(0);
            rect.setTranslateY(0);
            circle.setOpacity(1);
            circle.setTranslateX(0);
            circle.setTranslateY(0);
        });

        HBox box = new HBox(10, rect, circle);
        box.setAlignment(Pos.CENTER);

        ToolBar toolbar = new ToolBar(btnFade, btnTranslate, btnRotate, btnScale, btnPath, btnSeq, btnPar,
                btnTimeline, btnReset);

        BorderPane root = new BorderPane();
        root.setTop(toolbar);
        root.setCenter(box);
        BorderPane.setMargin(box, new Insets(20));

        Scene scene = new Scene(root, 640, 320);
        stage.setTitle("Animations & Transitions");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
