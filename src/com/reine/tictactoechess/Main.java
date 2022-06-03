package com.reine.tictactoechess;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Arrays;

/**
 * 主程序
 *
 * @author reine
 * 2022/6/3 6:07
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // 创建一个网格布局放置9个格子
        ChessGrid[][] chessGrids = new ChessGrid[3][3];
        GridPane gridPane = new GridPane();
        gridPane.setStyle("-fx-background-color: lightyellow;");
        gridPane.setMaxWidth(170);
        gridPane.setMaxHeight(170);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                chessGrids[i][j] = new ChessGrid();
                gridPane.add(chessGrids[i][j], j, i);
            }
        }

        // 顶部提示区域
        Label player = new Label("当前选手");
        player.setTextFill(Color.PURPLE);
        player.setDisable(true);
        player.setFont(new Font(20));
        Button playerColor = new Button("        ");
        playerColor.setStyle("-fx-background-color: red;");
        HBox hBox = new HBox(10);
        hBox.setPrefWidth(400);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(player, playerColor);

        // 底部终局提示
        Label win = new Label("");
        win.setFont(new Font(17));
        win.setVisible(false);
        Button restart = new Button("重新开始");
        restart.setFont(new Font(17));
        restart.setVisible(false);
        HBox result = new HBox(10);
        result.setAlignment(Pos.CENTER);
        result.getChildren().addAll(win, restart);

        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: skyblue;");
        borderPane.setTop(hBox);
        borderPane.setCenter(gridPane);
        borderPane.setBottom(result);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("井字棋");
        Image ico = new Image("images/logo.png");
        primaryStage.getIcons().add(ico);
        primaryStage.setWidth(400);
        primaryStage.setHeight(400);
        primaryStage.show();

        // 给每一个格子设置点击事件
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                ChessGridClickHandler chessGridClickHandler = new ChessGridClickHandler(chessGrids[i][j], playerColor);
                chessGrids[i][j].setOnAction(chessGridClickHandler);
            }
        }

        // 判断游戏是否结束
        playerColor.setOnAction(event -> {
            // 每个格子的颜色信息
            String[][] color = new String[3][3];
            // 胜者
            final String[] winner = {""};
            // 获取每个格子的颜色信息，如果是没有点击过的格子，则设置其颜色为undefined
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    String colorOfGrid = chessGrids[i][j].getStyle().split(";")[0];
                    if (!colorOfGrid.equals("")) {
                        int start = colorOfGrid.indexOf(":");
                        color[i][j] = colorOfGrid.substring(start + 1).trim();
                    } else {
                        color[i][j] = "undefined";
                    }
                }
            }
            // 判断胜者
            for (int i = 0; i < 3; i++) {
                // 行相同
                if (color[i][0].equals(color[i][1]) && color[i][1].equals(color[i][2]) && !color[i][0].equals("undefined") && !color[i][1].equals("undefined") && !color[i][2].equals("undefined")) {
                    winner[0] = color[i][0];
                    break;
                }
                // 列相同
                if (color[0][i].equals(color[1][i]) && color[1][i].equals(color[2][i]) && !color[0][i].equals("undefined") && !color[1][i].equals("undefined") && !color[2][i].equals("undefined")) {
                    winner[0] = color[0][i];
                    break;
                }
            }
            // 撇相同
            if (color[0][0].equals(color[1][1]) && color[1][1].equals(color[2][2]) && !color[0][0].equals("undefined")) {
                winner[0] = color[0][0];
            }
            // 捺相同
            if (color[0][2].equals(color[1][1]) && color[1][1].equals(color[2][0]) && !color[0][2].equals("undefined")) {
                winner[0] = color[0][2];
            }

            // 拷贝二维数组到一维数组
            String[] colors = new String[3 * 3];
            int index = 0;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    colors[index++] = color[i][j];
                }
            }
            // 判断是否点击了所有格子
            Arrays.stream(colors).map(c -> !c.equals("undefined")).reduce(Boolean::logicalAnd).ifPresent(aBoolean -> {
                // 如果是，并且winner为被赋值，则表示没有胜者，视为平局
                if (aBoolean && winner[0].equals("")) {
                    winner[0] = "none";
                }
            });

            // 处理底部游戏终局提示
            switch (winner[0]) {
                case "red":
                    win.setText("红方获胜");
                    win.setTextFill(Color.RED);
                    win.setVisible(true);
                    restart.setVisible(true);
                    gridPane.getChildren().forEach(node -> node.setDisable(true));
                    break;
                case "blue":
                    win.setText("蓝方获胜");
                    win.setTextFill(Color.BLUE);
                    win.setVisible(true);
                    restart.setVisible(true);
                    gridPane.getChildren().forEach(node -> node.setDisable(true));
                    break;
                case "none":
                    win.setText("平局");
                    win.setTextFill(Color.GREEN);
                    win.setVisible(true);
                    restart.setVisible(true);
                    gridPane.getChildren().forEach(node -> node.setDisable(true));
                    break;
                default:
            }
        });

        // 重新开始游戏点击事件
        restart.setOnAction(event -> {
            // 重新控件
            gridPane.getChildren().forEach(node -> {
                node.setDisable(false);
                node.setStyle("");
            });
            playerColor.setStyle("-fx-background-color: red;");
            win.setVisible(false);
            win.setTextFill(null);
            restart.setVisible(false);
        });
    }
}

