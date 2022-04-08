import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowListener;

public class TicTacToeFrame extends JFrame{
    private final int ROW = 3;
    private final int COL = 3;
    private String board[][] = new String[ROW][COL];
    private JButton visBoard[][] = new JButton[ROW][COL];
    private int imgSize = 75;
    private String currPlayer = "X";
    private boolean gameRunning = true;
    private int numTurns = 0;

    private JPanel mainPnl;
    private JPanel moveSltPnl;

    private ImageIcon xImg = new ImageIcon(new ImageIcon("src/Images/x.png").getImage().getScaledInstance(imgSize, imgSize, Image.SCALE_FAST));
    private ImageIcon oImg = new ImageIcon(new ImageIcon("src/Images/o.png").getImage().getScaledInstance(imgSize, imgSize, Image.SCALE_FAST));
    private ImageIcon blankImg = new ImageIcon(new ImageIcon("src/Images/blank.png").getImage().getScaledInstance(imgSize, imgSize, Image.SCALE_FAST));

    public TicTacToeFrame(){
        mainPnl = new JPanel();
        mainPnl.setLayout(new BorderLayout());

        clearBoard();

        createMoveSltPnl();
        mainPnl.add(moveSltPnl);
        display();

        JButton quitBtn = new JButton("Quit");
        quitBtn.setSize(100, 50);
        quitBtn.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(this, "Are you sure you want to quit?") == JOptionPane.YES_OPTION){
                System.exit(0);
            }
        });
        add(quitBtn, BorderLayout.AFTER_LAST_LINE);

        add(mainPnl);
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }


    private void createMoveSltPnl(){
        moveSltPnl = new JPanel();
        moveSltPnl.setLayout(new GridLayout(3, 3));
        moveSltPnl.setSize(400, 400);
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                visBoard[i][j] = new JButton(blankImg);
                final int finalI = i;
                final int finalJ = j;
                visBoard[i][j].addActionListener(e -> playTurn(finalI, finalJ));
                moveSltPnl.add(visBoard[i][j]);
            }
        }

    }

    //Acts as game reset
    private void clearBoard(){
        currPlayer = "X";
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                board[i][j] = " ";
            }
        }
        numTurns = 0;
        gameRunning = true;
    }

    private void display(){
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                if(board[i][j] == "X"){
                    visBoard[i][j].setIcon(xImg);
                }
                else if(board[i][j] == "O"){
                    visBoard[i][j].setIcon(oImg);
                }
                else{
                    visBoard[i][j].setIcon(blankImg);
                }
            }
        }
    }

    private void playTurn(int inRow, int inCol){
        if (isValidMove(inRow, inCol) && gameRunning){
            board[inRow][inCol] = currPlayer;
            display();

            if(numTurns >= 7 && isTie()){
                gameRunning = false;
                int cont = JOptionPane.showConfirmDialog(this, "The game ended in a tie!\nWould you like to play again?");
                if(cont == JOptionPane.YES_OPTION){
                    clearBoard();
                    display();
                }
                else if(cont == JOptionPane.NO_OPTION){
                    System.exit(0);
                }
            }
            else if(isWin(currPlayer)) {
                gameRunning = false;
                int cont = JOptionPane.showConfirmDialog(this, "Player " + currPlayer + " won!\nWould you like to play again?");
                if(cont == JOptionPane.YES_OPTION){
                    clearBoard();
                    display();
                }
                else if(cont == JOptionPane.NO_OPTION){
                    System.exit(0);
                }
            }
            else{
                numTurns++;

                if(currPlayer == "X"){
                    currPlayer = "O";
                }
                else{
                    currPlayer = "X";
                }
            }
        }
        else
            JOptionPane.showMessageDialog(this, "Not a valid move!");


    }

    private boolean isValidMove(int row, int col){
        return board[row][col].equals(" ");
    }

    private boolean isWin(String player){
        return (isColWin(player)||isRowWin(player)||isDiagnalWin(player));
    }

    private boolean isColWin(String player){
        for (int i = 0; i < COL; i++) {
            if(board[0][i].equals(player) && board[1][i].equals(player) && board[2][i].equals(player)){
                return true;
            }
        }
        return false;
    }

    private boolean isRowWin(String player){
        for (int i = 0; i < ROW; i++) {
            if(board[i][0].equals(player) && board[i][1].equals(player) && board[i][2].equals(player)){
                return true;
            }
        }
        return false;
    }

    private boolean isDiagnalWin(String player){
        return (board[0][0].equals(player) && board[1][1].equals(player) && board[2][2].equals(player)) || (board[0][2].equals(player) && board[1][1].equals(player) && board[2][0].equals(player));
    }

    private boolean isTie(){
        boolean tie = true;
        for (int i = 0; i < 7; i++) {
            if(!lineBoth(i)){
                tie = false;
                break;
            }
        }
        return tie;
    }

    private boolean lineBoth(int line){
        boolean contX = false;
        boolean contO = false;
        switch (line){
            case 0:
            case 1:
            case 2:
                for (int i = 0; i < 3; i++) {
                    if (board[i][line].equals("X")){
                        contX = true;
                    }
                    else if(board[i][line].equals("O")){
                        contO = true;
                    }
                }
                break;
            case 3:
            case 4:
            case 5:
                for (int i = 0; i < 3; i++) {
                    if (board[line-3][i].equals("X")){
                        contX = true;
                    }
                    else if(board[line-3][i].equals("O")){
                        contO = true;
                    }
                }
                break;
            case 6:
                for (int i = 0; i < 3; i++) {
                    if (board[i][i].equals("X")){
                        contX = true;
                    }
                    else if(board[i][i].equals("O")){
                        contO = true;
                    }
                }
                break;
            case 7:
                for (int i = 0; i < 3; i++) {
                    if (board[i][2-i].equals("X")){
                        contX = true;
                    }
                    else if(board[i][2-i].equals("O")){
                        contO = true;
                    }
                }
                break;
        }
        return (contX && contO);
    }
}
