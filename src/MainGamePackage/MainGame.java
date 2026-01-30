package MainGamePackage;

import java.awt.Color; // for JButton colors
import javax.swing.JButton; // for JButton
import java.awt.GridLayout; // for grid layout
import java.awt.event.ActionEvent; // for action events to allow each button to do a thing
import java.awt.event.ActionListener; // for implimenting the action listener into a class to pass int's

/*
* @file    MainGame.java
* @author  Murphy Creamer s0569875
* @date    2026-01-24 to 2026-01-30
* @brief   A three player game of connect 4, with a reset button. Diagonal wins counted as well. Displays winner/tie in jTextFields
* @Todo    General code improvement, submission of assignment
*
* @Note     if a 'if-statment' can be condenced to a single line, I will do so to shorten the program length
*/
public class MainGame extends javax.swing.JFrame {
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MainGame.class.getName());
    
    //Static vars 
    final static int GRID_SIZE = 8;
    final static int WIN_TOTAL = 4;
    JButton[][] grid = new JButton[GRID_SIZE][GRID_SIZE];
    char currentTurn = '1';
    int checkTie = 0;
    Player player1 = new Player();
    Player player2 = new Player();
    Player player3 = new Player();
    /**
     * Creates new form MainGame. Main constructor of the MainGame() class, calls the runGame() function when constructed.
     */
    public MainGame() {
        initComponents();
        // handles all of game functions and gameloop
        runGame();
    }
    
    /*
    * @brief    function to set grid and run gameloop
    * @pre      grid must be declared and unfilled in gameScreen jPanel
    * @post     grid is filled, game is run by a loop, and by calling a custom actionEvent for each button
    */
    private void runGame() {
        gameScreen.setLayout(new GridLayout(GRID_SIZE, GRID_SIZE, 1, 1));
        
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                grid[row][col] = new JButton();
                grid[row][col].setBackground(Color.white);
                gameScreen.add(grid[row][col]);
                grid[row][col].addActionListener(new NumsActionListner(row, col));
            }
        }
    }
    
    /*
    * This class impliments ActionListener so it can be constructed with the rows
    and cols of the runGame() function, and to make all buttons clickable, trying to avoid repition
    * @implements   ActionListener interface
    */
    public class NumsActionListner implements ActionListener {
        final int row;
        final int col;
        
        // Constructor
        public NumsActionListner(int row, int col) {
            this.row = row;
            this.col = col;
        }
        /*
        * @brief    function to detect when a button is clicked, place the correct tile, and end the game if it is a tie.
        * @param    ActionEvent e to detect when the user presses a button in the program        
        * @pre      Grid must be filled, and .addActionListner() called with this class constructor
        * @post     Selected button will be changed to color, and if there is a Tie the game will end
        */
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (currentTurn) {
                case '1'->{ player1.placeTile(row, col); }
                case '2'->{ player2.placeTile(row, col); }
                case '3'->{ player3.placeTile(row, col); }
                case '4'->{ System.out.println("Game is Over"); }
                default->{System.out.println("An error has occured");}
            }
            if (checkTie >= (GRID_SIZE * GRID_SIZE)) {
                jTextField1.setText("TIE");
                jTextField2.setText("TIE");
                jTextField3.setText("TIE");
            }
        }
    }
    
    /**
     * This class allows the creation of a player object, with methods to check
     * if someone has won, and to place a tile on a selected row and col of the grid
     */
    public class Player{
        Color playerColor = Color.white; // set to prevent pre-selection play
        int checkRow = 0; // for diagonal checks
        int checkCol = 0; 
        /*
        * @brief    function to check all tiles for victory or tie
        * @pre      grid must be declared in gameScreen
        * @post     returned bool if player has won or tied
        * @return   true if game is tied or a winner has been found, false otherwise
        */
        public boolean checkWin() {
            boolean hasWon = false;
            int winScore = 0;
            // check cols
            for (int row = 0; row < GRID_SIZE; row++) {
                for (int col = 0; col < GRID_SIZE; col++) {
                    if(grid[row][col].getBackground() == this.playerColor) {
                        winScore++;
                        if (winScore >= WIN_TOTAL) { hasWon = true; }
                    } else { winScore = 0; }
                }
                winScore = 0; // if the final checked block is the playerColor, reset score to prevent false victory
            }
            // check rows
            for (int col = 0; col < GRID_SIZE; col++) {
                for (int row = 0; row < GRID_SIZE; row++) {
                    if(grid[row][col].getBackground() == this.playerColor) {
                        winScore++;
                        if (winScore >= WIN_TOTAL) { hasWon = true; }
                    } else { winScore = 0; }
                }
                winScore = 0;
            }
            // check diagonal wins
            for (int col = 0; col < GRID_SIZE; col++) {
                for (int row = 0; row < GRID_SIZE; row++) {
                    if (grid[row][col].getBackground() == this.playerColor) {
                        checkRow = row;
                        checkCol = col;
                        // check left-right diagonal
                        while (checkRow < GRID_SIZE && checkCol < GRID_SIZE && 
                                grid[checkRow][checkCol].getBackground() == this.playerColor) {
                            winScore++;
                            checkRow++;
                            checkCol++;
                            if (winScore >= WIN_TOTAL) { hasWon = true; }
                        }
                        // check right-left diagonal
                        winScore = 0;
                        checkRow = row;
                        checkCol = col;
                        while (checkRow < GRID_SIZE && checkCol >= 0 && 
                                grid[checkRow][checkCol].getBackground() == this.playerColor) {
                            winScore++;
                            checkRow++;
                            checkCol--;
                            if (winScore >= WIN_TOTAL) { hasWon = true; }
                        }
                        winScore = 0;
                    }
                }
                winScore = 0;
            }
            return hasWon;
        }
        
        /*
        * @brief    place tiles on unclicked buttons
        * @param    int 'row' that is the row of the selected tile
        * @param    int 'col' that is the col of the selected tile
        * @pre      grid must be declared in gameScreen, user must click on a tile
        * @post     untouched tile changes to players selected color, turn order changes
        */
        public void placeTile(int row, int col) {
            Color checkTile = grid[row][col].getBackground();
            // simular code also called in the jButtonActionPreformed() code, this is to prevent score from climing, and winner being delared
            Color p1 = player1.playerColor;
            Color p2 = player2.playerColor;
            Color p3 = player3.playerColor;
            
            if (checkTile == Color.white && p1 != p2 && p2 != p3 && p1 != p3) {
                grid[row][col].setBackground(playerColor);
                checkTie++;
                switch(currentTurn) {
                    case '1'->{ 
                        if (!checkWin()) {
                            currentTurn = '2';
                            jTextField1.setText("Player 1");
                            jTextField2.setText("PLAYING");
                        } else {
                            currentTurn = '4';
                            jTextField1.setText("WINNER");
                        }
                    }
                    case '2'->{ 
                        if (!checkWin()) {
                            currentTurn = '3';
                            jTextField2.setText("Player 2");
                            jTextField3.setText("PLAYING");
                        } else {
                            currentTurn = '4';
                            jTextField2.setText("WINNER");
                        }
                    }
                    case '3'->{ 
                        if (!checkWin()) {
                            currentTurn = '1';
                            jTextField1.setText("PLAYING");
                            jTextField3.setText("Player 3");
                        } else {
                            currentTurn = '4';
                            jTextField3.setText("WINNER");
                        }
                    }
                    default->{ System.out.println("Error occured"); }
                }
            }
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jComboBox3 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        gameScreen = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextField1.setEditable(false);
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.setText("Player 1");

        jTextField2.setEditable(false);
        jTextField2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField2.setText("Player 2");

        jTextField3.setEditable(false);
        jTextField3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField3.setText("Player 3");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Red", "Green", "Blue" }));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Red", "Green", "Blue" }));

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Red", "Green", "Blue" }));

        jButton1.setText("Start Game");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        gameScreen.setAutoscrolls(true);
        gameScreen.setLayout(new java.awt.GridLayout(1, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(gameScreen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox1, 0, 137, Short.MAX_VALUE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE))
                        .addGap(0, 0, 0)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField2)
                            .addComponent(jComboBox2, 0, 138, Short.MAX_VALUE))
                        .addGap(0, 0, 0)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField3)
                            .addComponent(jComboBox3, 0, 125, Short.MAX_VALUE))))
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTextField1))
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(gameScreen, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /*
    * @brief        a button that when pressed starts the game and assigns colors, or resets the game
    * @param[in]    ActionEvent 'evt' that triggers when a button is pressed
    * @pre          grid must be declared in gameScreen, user must click on the jButton
    * @post         will either start the game assigning colors to each player baced on their combo box choice, or will clear the board.
    */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Color p1 = player1.playerColor = getClr((String)jComboBox1.getSelectedItem());
        Color p2 = player2.playerColor = getClr((String)jComboBox2.getSelectedItem());
        Color p3 = player3.playerColor = getClr((String)jComboBox3.getSelectedItem());
        
        switch (jButton1.getText()) {
            case "Start Game"->{
                if (p1 != p2 && p2 != p3 && p1 != p3) {
                    jButton1.setText("Cancel Game");
                    jTextField1.setText("PLAYING");
                }
            }
            case "Cancel Game"->{
                jButton1.setText("Start Game");
                checkTie = 0;
                for (int row = 0; row < GRID_SIZE; row ++) {
                    for (int col = 0; col < GRID_SIZE; col++) { 
                        grid[row][col].setBackground(Color.white);
                    }
                }
                player1.playerColor = Color.white;
                jTextField1.setText("Player 1");
                player2.playerColor = Color.white;
                jTextField2.setText("Player 2");
                player3.playerColor = Color.white;
                jTextField3.setText("Player 3");
                currentTurn = '1';
                }
                default->{ System.out.println("An error has occured."); }
            } 
    }//GEN-LAST:event_jButton1ActionPerformed
   
    /*
    * @brief        take the string from a players combo box, and return correct Color.
    * @param        String 'colorName' is the selected color in the combo box
    * @pre          grid must be declared in gameScreen, user must select a color in the combo box
    * @post         will return color baceed on string receved
    * @return       Color baced on the param String, if color not in switch, return null
    */
    public Color getClr(String colorName) {
        switch (colorName) {
            case "Red"->{ return Color.red; }
            case "Green"->{ return Color.green; }
            case "Blue"->{ return Color.blue; }
            default->{ System.out.println("Error has occured."); return null;}
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new MainGame().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel gameScreen;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
}