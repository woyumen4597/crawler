package ui;

import java.util.ArrayList;
import java.util.List;

import auth.Auth;
import crawer.Rank;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class UI extends Application {
	private List<String> urls = new ArrayList<String>();
	private Pagination pagination;
	private AnchorPane anchor;
	private String[] fonts = new String[] {};

	public int itemsPerpage() {
		return 4;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Pixiv4J");
		fonts = Font.getFamilies().toArray(fonts);
		BorderPane pane = new BorderPane();
		anchor = new AnchorPane();
		// 左边搜索框
		TextField search = new TextField();
		Button searchButton = new Button("Search");
		HBox hBox = new HBox();
		hBox.setSpacing(5);
		hBox.getChildren().addAll(search, searchButton);

		// 左边工具栏
		VBox vBox = new VBox();
		vBox.setSpacing(5);
		vBox.setPadding(new Insets(6));
		TreeItem rankItem = new TreeItem("Rank");
		rankItem.setExpanded(true);
		TreeItem daily = new TreeItem(new Hyperlink("Daily"));
		showResult(daily);
		TreeItem weekly = new TreeItem(new Hyperlink("Weekly"));
		showResult(weekly);
		TreeItem monthly = new TreeItem(new Hyperlink("Monthly"));
		showResult(monthly);
		TreeItem male = new TreeItem(new Hyperlink("Male"));
		showResult(male);
		TreeItem female = new TreeItem(new Hyperlink("Female"));
		showResult(female);
		TreeItem r18 = new TreeItem(new Hyperlink("R18"));
		showResult(r18);
		rankItem.getChildren().addAll(daily, weekly, monthly, male, female, r18);
		TreeView rankTree = new TreeView(rankItem);

		TreeItem userItem = new TreeItem("User");
		HBox box = new HBox();
		TextField userIdTextField = new TextField();
		userIdTextField.setMaxWidth(100);
		Label label = new Label("UserId");
		Button idButton = new Button("Go");
		box.setSpacing(5);
		box.getChildren().addAll(label, userIdTextField, idButton);
		TreeItem user_id = new TreeItem(box);
		userItem.getChildren().add(user_id);
		TreeView userTree = new TreeView(userItem);
		vBox.getChildren().addAll(hBox, rankTree, userTree);

		

		// stage
		pane.setLeft(vBox);
		pane.setCenter(anchor);
		Scene scene = new Scene(pane);
		primaryStage.setScene(scene);
		Image favicon = new Image(getClass().getResourceAsStream("pixiv.jpg"));
		primaryStage.getIcons().add(favicon);
		primaryStage.setResizable(true);
		primaryStage.setMaximized(true);
		primaryStage.show();
	}
	// 中间显示
	@SuppressWarnings("rawtypes")
	private void showResult(TreeItem mode) {
		Hyperlink link = (Hyperlink) mode.getValue();
		link.setOnAction((ActionEvent) -> {
			Thread thread = new Thread(new Runnable() {
				public void run() {
					Platform.runLater(() -> {
						try {
							if (link.getText().equalsIgnoreCase("r18")) {
								Auth auth = new Auth();
								urls = auth.r18_rank_result("daily");
							} else {
								Rank rank = new Rank();
								urls = rank.rankResult(link.getText().toLowerCase());
							}
							pagination = new Pagination(urls.size() / itemsPerpage(), 0);
							pagination.setPageFactory((Integer pageIndex) -> createPage(pageIndex, urls));
							AnchorPane.setTopAnchor(pagination, 10.0);
							AnchorPane.setRightAnchor(pagination, 10.0);
							AnchorPane.setBottomAnchor(pagination, 10.0);
							AnchorPane.setLeftAnchor(pagination, 10.0);
							anchor.getChildren().addAll(pagination);
						} catch (Exception e) {
							e.printStackTrace();
						}
					});

				}

				private VBox createPage(Integer pageIndex, List<String> list) {
					VBox box = new VBox(5);
					int page = pageIndex * itemsPerpage();
					for (int i = page; i < page + itemsPerpage(); i++) {
						Image image = new Image(list.get(i));
						ImageView imageView = new ImageView(image);
						imageView.setFitHeight(150);
						imageView.setFitWidth(150);
						box.getChildren().add(imageView);
						box.setAlignment(Pos.CENTER);
					}
					return box;
				}
			});
			thread.start();
		});
	}

	public static void main(String[] args) {
		launch(args);
	}

	public List<String> getUrls() {
		return urls;
	}

	public void setUrls(List<String> urls) {
		this.urls = urls;
	}
}
