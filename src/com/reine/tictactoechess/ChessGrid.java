package com.reine.tictactoechess;

import javafx.scene.control.Button;

/**
 * 每个格子
 * @author reine
 * 2022/6/3 6:08
 */
public class ChessGrid extends Button {

    public ChessGrid() {
        super();
        super.setPrefWidth(50);
        super.setPrefHeight(50);
        super.setOpacity(1);
        super.setFocusTraversable(false);
    }

    public ChessGrid(String text) {
        super(text);
        super.setPrefWidth(50);
        super.setPrefHeight(50);
        super.setOpacity(1);
        super.setFocusTraversable(false);
    }

}
