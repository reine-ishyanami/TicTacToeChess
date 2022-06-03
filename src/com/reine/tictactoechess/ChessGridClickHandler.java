package com.reine.tictactoechess;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义格子点击监听器
 *
 * @author reine
 * 2022/6/3 6:44
 */
public class ChessGridClickHandler implements EventHandler<ActionEvent> {
    public ChessGrid chessGrid;
    public Button playerColor;

    public ChessGridClickHandler(ChessGrid chessGrid, Button playColor) {
        this.chessGrid = chessGrid;
        this.playerColor = playColor;
    }

    /**
     * 格子点击事件
     * @param event 鼠标事件
     */
    @Override
    public void handle(ActionEvent event) {
        // 获得当前玩家的颜色
        String[] style = playerColor.getStyle().split(";");
        List<String> color = Arrays.stream(style).filter(s -> s.startsWith("-fx-background-color:")).collect(Collectors.toList());
        int start = color.get(0).indexOf(":");
        String currentColor = color.get(0).substring(start + 1);
        // 按照当前玩家颜色给点击的按钮设置颜色
        switch (currentColor.trim()) {
            case "red":
                // 设置按钮颜色
                chessGrid.setStyle("-fx-background-color: red;");
                // 触发playColor的点击事件
                playerColor.fire();
                // 修改当前玩家颜色
                playerColor.setStyle("-fx-background-color: blue;");
                chessGrid.setDisable(true);
                break;
            case "blue":
                chessGrid.setStyle("-fx-background-color: blue;");
                chessGrid.setDisable(true);
                playerColor.setStyle("-fx-background-color: red;");
                playerColor.fire();
                break;
            default:
        }
    }
}
