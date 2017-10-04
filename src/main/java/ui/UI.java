package ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UI extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Pixiv4J");
		BorderPane pane = new BorderPane();
		//左边搜索框
		TextField search = new TextField();
		Button searchButton = new Button("Search");
		HBox hBox = new HBox();
		hBox.setSpacing(5);
		hBox.getChildren().addAll(search,searchButton);
		
		//中间显示
		StackPane showPane = new StackPane();
		ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("pixiv.jpg")));
		imageView.setScaleX(0.1);
		imageView.setScaleY(0.1);
		showPane.getChildren().add(imageView);
		
		//左边工具栏
		VBox vBox = new VBox();
		vBox.setSpacing(5);
		vBox.setPadding(new Insets(6));
		TreeItem<String> rank = new TreeItem<String>("Rank");
		TreeItem<String> daily = new TreeItem<String>("Daily");
		TreeItem<String> weekly = new TreeItem<String>("Weekly");
		TreeItem<String> monthly = new TreeItem<String>("Monthly");
		rank.getChildren().addAll(daily,weekly,monthly);
		
		TreeView<String> rankTree = new TreeView<String>(rank);
		vBox.getChildren().addAll(hBox,rankTree);
		pane.setLeft(vBox);
		pane.setCenter(showPane);
		Scene scene = new Scene(pane);
		primaryStage.setScene(scene);
		Image favicon = new Image(getClass().getResourceAsStream("pixiv.jpg"));
		primaryStage.getIcons().add(favicon);
		primaryStage.setResizable(true);
		primaryStage.show();
	}
	public static void main(String[] args) {
		launch(args);
	}
}
