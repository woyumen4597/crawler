package ui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import auth.Auth;
import crawer.Pixivision;
import crawer.Rank;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import search.Search;
import user.User;
import utils.DuplicateRemover;

public class UI extends Application {
	private List<String> urls = new ArrayList<String>();
	private Stage primaryStage;
	private Pagination pagination;
	private AnchorPane anchor;
	private Image favicon = new Image(getClass().getResourceAsStream("pixiv.jpg"));
	private BackgroundImage bImage = new BackgroundImage(new Image(getClass().getResourceAsStream("background.jpg")),
			BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

	public int itemsPerpage() {
		return 4;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		primaryStage.setTitle("Pixiv4J");
		BorderPane pane = new BorderPane();
		anchor = new AnchorPane();
		// 左边搜索框
		TextField searchTextField = new TextField();
		Button searchButton = new Button("Search");
		searchButton.setOnAction((ActionEvent e) -> {
			Thread thread = new Thread(new Runnable() {
				public void run() {
					Platform.runLater(() -> {
						String keyword = searchTextField.getText();
						Search search = new Search();
						urls = search.searchImages(keyword, "safe", "date_d");
						createPagination();
					});
				}
			});
			thread.start();

		});
		HBox hBox = new HBox();
		hBox.setSpacing(5);
		hBox.getChildren().addAll(searchTextField, searchButton);

		// 左边工具栏
		VBox vBox = new VBox();
		vBox.setEffect(new DropShadow());
		vBox.setSpacing(5);
		vBox.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, new CornerRadii(5), new Insets(0))));
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
		// User模块
		TreeItem userItem = new TreeItem("User");
		userItem.setExpanded(true);
		HBox box = new HBox();
		TextField userIdTextField = new TextField();
		userIdTextField.setMaxWidth(100);
		Label label = new Label("UserId");
		Button idButton = new Button("Go");
		idButton.setOnAction((ActionEvent e) -> {
			Thread thread = new Thread(new Runnable() {
				public void run() {
					Platform.runLater(() -> {
						String id = userIdTextField.getText();
						User user = new User(id);
						urls = user.illust(5);
						urls = DuplicateRemover.removeDuplicate(urls);
						createPagination();
					});
				}
			});
			thread.start();

		});

		box.setSpacing(5);
		box.getChildren().addAll(label, userIdTextField, idButton);
		TreeItem user_id = new TreeItem(box);
		HBox pxvbox = new HBox();
		pxvbox.setSpacing(5);
		TextField pxvid = new TextField();
		pxvid.setMaxWidth(100);
		Label label2 = new Label("PxvId");
		Button pxvidButton = new Button("Go");
		pxvidButton.setOnAction((ActionEvent e) -> {
			Thread thread = new Thread(new Runnable() {
				public void run() {
					Platform.runLater(() -> {
						String id = pxvid.getText();
						Pixivision pixivision = new Pixivision();
						urls = pixivision.pixivisionImage(id);
						urls = DuplicateRemover.removeDuplicate(urls);
						createPagination();
					});
				}
			});
			thread.start();

		});
		pxvbox.getChildren().addAll(label2, pxvid, pxvidButton);
		TreeItem pixivision = new TreeItem(pxvbox);
		userItem.getChildren().addAll(user_id, pixivision);
		TreeView userTree = new TreeView(userItem);
		vBox.getChildren().addAll(hBox, rankTree, userTree);
		// stage

		pane.setBackground(new Background(bImage));
		pane.setLeft(vBox);
		pane.setCenter(anchor);
		Scene scene = new Scene(pane);
		primaryStage.setScene(scene);

		primaryStage.getIcons().add(favicon);
		primaryStage.setResizable(true);
		primaryStage.setMaximized(true);
		primaryStage.show();
	}

	/**
	 * 动态加载rank后的图片
	 * 
	 * @param mode
	 */
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
							createPagination();
						} catch (Exception e) {
							e.printStackTrace();
						}
					});

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

	/**
	 * @param pageIndex
	 * @return 创建每一个分页
	 */
	public VBox createPage(Integer pageIndex) {
		VBox box = new VBox(5);
		int page = pageIndex * itemsPerpage();
		for (int i = page; i < page + itemsPerpage(); i++) {
			String name = urls.get(i).substring(urls.get(i).lastIndexOf("/")+1);
			Image image = new Image(urls.get(i));
			ContextMenu cm = new ContextMenu();
			if (!image.isError()) {
				ImageView imageView = new ImageView(image);
				imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
					if (e.getButton() == MouseButton.SECONDARY) {
						cm.show(imageView, e.getScreenX(), e.getScreenY());
					}
				});
				MenuItem cmItem = new MenuItem("Save Image");
				cm.getItems().add(cmItem);
				cmItem.setOnAction((ActionEvent e) -> {
					FileChooser fileChooser1 = new FileChooser();
					fileChooser1.setTitle("Save Image");
					FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("JPG FILES","*.jpg");
					FileChooser.ExtensionFilter filter2 = new FileChooser.ExtensionFilter("PNG FILES","*.png");
					fileChooser1.getExtensionFilters().addAll(filter,filter2);
					fileChooser1.setInitialFileName(name);
					fileChooser1.setInitialDirectory(new File(System.getProperty("user.dir")));
					File file = fileChooser1.showSaveDialog(primaryStage);
					if (file != null) {
						try {
							ImageIO.write(SwingFXUtils.fromFXImage(image, null), "jpg", file);
						} catch (IOException ex) {
							System.out.println(ex.getMessage());
						}
					}

				});

				imageView.setFitHeight(150);
				imageView.setFitWidth(150);

				box.getChildren().add(imageView);
				box.setAlignment(Pos.CENTER);
			}

		}

		return box;
	}

	/**
	 * 创建分页
	 */
	public void createPagination() {
		pagination = new Pagination(urls.size() / itemsPerpage(), 0);
		pagination.setPageFactory((Integer pageIndex) -> createPage(pageIndex));
		AnchorPane.setTopAnchor(pagination, 10.0);
		AnchorPane.setRightAnchor(pagination, 10.0);
		AnchorPane.setBottomAnchor(pagination, 10.0);
		AnchorPane.setLeftAnchor(pagination, 10.0);
		anchor.getChildren().addAll(pagination);
	}
}
