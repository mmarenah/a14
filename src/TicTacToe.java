import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TicTacToe extends Application {
    private boolean playerXTurn = true;
    private Button[][] board = new Button[5][5];
    private Label resultLabel = new Label("Player X's turn");

    @Override
    public void start(Stage primaryStage) {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                Button button = new Button();
                button.setMinSize(80, 80);
                final int rowIndex = i;
                final int colIndex = j;
                button.setOnAction(e -> buttonClicked(button, rowIndex, colIndex));
                board[i][j] = button;
                gridPane.add(button, j, i);
            }
        }

        StackPane root = new StackPane();
        root.getChildren().addAll(gridPane, resultLabel);

        Scene scene = new Scene(root, 500, 500);

        primaryStage.setTitle("TicTacToe");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void buttonClicked(Button button, int row, int col) {
        if (button.getText().isEmpty()) {
            if (playerXTurn) {
                button.setText("X");
                resultLabel.setText("Player O's turn");
            } else {
                button.setText("O");
                resultLabel.setText("Player X's turn");
            }

            if (checkWin(row, col)) {
                if (playerXTurn) {
                    resultLabel.setText("Player X wins!");
                } else {
                    resultLabel.setText("Player O wins!");
                }
                disableButtons();
            } else if (checkDraw()) {
                resultLabel.setText("It's a draw!");
                disableButtons();
            } else {
                playerXTurn = !playerXTurn;
            }
        }
    }

    private boolean checkWin(int row, int col) {
        String mark = playerXTurn ? "X" : "O";

        // check row
        int count = 0;
        for (int i = 0; i < board[row].length; i++) {
            if (board[row][i].getText().equals(mark)) {
                count++;
                if (count == 5) {
                    return true;
                }
            } else {
                count = 0;
            }
        }

        // check column
        count = 0;
        for (int i = 0; i < board.length; i++) {
            if (board[i][col].getText().equals(mark)) {
                count++;
                if (count == 5) {
                    return true;
                }
            } else {
                count = 0;
            }
        }

        // check diagonal (top-left to bottom-right)
        count = 0;
        final int min = Math.min(row, col);
        int i = row - min;
        int j = col - min;
        while (i < board.length && j < board[i].length) {
            if (board[i][j].getText().equals(mark)) {
                count++;
                if (count == 5) {
                    return true;
                }
            } else {
                count = 0;
            }
            i++;
            j++;
        }

        return false;
    }

    private boolean checkDraw() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void disableButtons() {
        for (Button[] buttons : board) {
            for (Button button : buttons) {
                button.setDisable(true);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}