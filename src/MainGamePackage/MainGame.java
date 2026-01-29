package MainGamePackage;


import java.awt.Color; // for JButton colors
import javax.swing.JButton; // for JButton
import java.awt.GridLayout; // for grid layout
import java.awt.event.ActionEvent; // for action events to allow each button to do a thing

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 * @file    MainGame.java
 * 
 * @author  Murphy Creamer s0....
 * 
 * @date    2026-01-24 to 2026-01-30
 * 
 * @brief   A three player game of connect 4
 * 
 * 
 * @Todo    Fix reset of board, add view of current turn, general code 
 improvement
 * 
 */
public class MainGame extends javax.swing.JFrame {
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MainGame.class.getName());
    
    //Vars
    JButton[][] grid = new JButton[8][8];
    char currentTurn = '1';
    Player player1 = new Player(), player2 = new Player(), player3 = new Player();
    
    /**
     * 
     * This class allows the creation of a player object, with methods to check
     * if someone has won, place a tile on a empty space, and ...
     */
    public class Player{
        Color playerColor = Color.white; // set to prevent pre-selection play
        //Constructor
        Player() { /* do nothing */ }
            
        //Member functions
        /*
        * @brief    function to check all tiles for victory or tie
        * @param    none
        *
        * @pre      grid must be declared in gameScreen
        * @post     returned bool if player has won or tied
        * @return   true if game is tied or a winner has been found, false otherwise
        */
        public boolean checkWin() {
            boolean hasWon = false;
            int winScore = 0;
            int checkForTie = 0;
                
            // check background for tie
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    if (grid[row][col].getBackground() != Color.white) {
                        checkForTie++;
                        if (checkForTie >= 64) { hasWon = true; }
                    } else {
                        checkForTie = 0;
                    }
                }
            }
                
            // check for player victory
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    if(grid[row][col].getBackground() == playerColor) {
                        winScore++;
                        if (winScore >= 4) { hasWon = true; }
                    } else { 
                        winScore = 0; 
                    }
                }
            }
            for (int col = 0; col < 8; col++) {
                for (int row = 0; row < 8; row++) {
                    if(grid[row][col].getBackground() == playerColor) {
                        winScore++;
                        if (winScore >= 4) { hasWon = true; }
                    } else { 
                        winScore = 0; 
                    }
                }
            }
            for (int d1 = 0; d1 < 8; d1++) {
                if(grid[d1][d1].getBackground() == playerColor) {
                    winScore++;
                    if (winScore >= 4) { hasWon = true; }
                } else { 
                    winScore = 0; 
                }
            }
              
            for (int col = 0; col < 8; col++) {
                if(grid[7 - col][col].getBackground() == playerColor) {
                    winScore++;
                    if (winScore >= 4) { hasWon = true; }
                } else { 
                    winScore = 0; 
                }
            }
            return hasWon;
        }
        
        /*
        * @brief        place tiles on unclicked buttons
        * @param[in]    int 'row' that is the row of the selected tile
        * @param[in]    int 'col' that is the col of the selected tile
        *
        * @pre          grid must be declared in gameScreen, user must click on a tile
        * @post         untouched tile changes to players selected color, turn order changes
        * @return       none
        */
        public void placeTile(int row, int col) {
            Color checkTile = grid[row][col].getBackground();
            
            if (checkTile == Color.white) {
                grid[row][col].setBackground(playerColor);
                switch(currentTurn) {
                    case '1'->{ 
                        if (!checkWin()) {
                            currentTurn = '2';
                            jTextField1.setText("Player 1");
                            jTextField2.setText("PLAYING");
                            jTextField3.setText("Player 2");
                        } else {
                            currentTurn = '4';
                            jTextField1.setText("WINNER");
                            jTextField2.setText("Player 2");
                            jTextField3.setText("Player 3");
                        }
                    }
                    case '2'->{ 
                        if (!checkWin()) {
                            currentTurn = '3';
                            jTextField1.setText("Player 1");
                            jTextField2.setText("Player 2");
                            jTextField3.setText("PLAYING");
                        } else {
                            currentTurn = '4';
                            jTextField1.setText("Player 1");
                            jTextField2.setText("WINNER");
                            jTextField3.setText("Player 3");
                        }
                    }
                    case '3'->{ 
                        /* currentTurn = checkWin() ? '4' : '1'; */ 
                        if (!checkWin()) {
                            currentTurn = '1';
                            jTextField1.setText("PLAYING");
                            jTextField2.setText("Player 2");
                            jTextField3.setText("Player 3");
                        } else {
                            currentTurn = '4';
                            jTextField1.setText("Player 1");
                            jTextField2.setText("Player 2");
                            jTextField3.setText("WINNER");
                        }
                    }
                    default->{ System.out.println("Error occured"); }
                }
            }
        }
    }
    
    /**
     * Creates new form MainGame
     * 
     * Main class of program, that defines fills the jButton grid, and adds it to
    the display grid onscreen
     */
    public MainGame() {
        initComponents();
        gameScreen.setLayout(new GridLayout(8, 8, 4, 4));
        
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                int currentRow = row; // need to delare these vars here for them to be final, yet changing values
                int currentCol = col;
                grid[row][col] = new JButton();
                grid[row][col].setBackground(Color.white);
                gameScreen.add(grid[row][col]);
                grid[row][col].addActionListener((ActionEvent e) -> { 
                       switch (currentTurn) {
                           case '1'->{player1.placeTile(currentRow, currentCol);}
                           case '2'->{player2.placeTile(currentRow, currentCol);}
                           case '3'->{player3.placeTile(currentRow, currentCol);}
                           case '4'->{ System.out.println("Game is Over"); }
                           default->{System.out.println("An error has occured");}
                       }
                });
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

        gameScreen.setLayout(new java.awt.GridLayout(1, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(gameScreen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField1)
                            .addComponent(jComboBox1, 0, 123, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField2)
                            .addComponent(jComboBox2, 0, 129, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField3)
                            .addComponent(jComboBox3, 0, 124, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(gameScreen, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /*
    * @brief        a button that when pressed starts the game and assigns colors, or resets the game
    * @param[in]    ActionEvent 'evt' that triggers when a button is pressed
    *
    * @pre          grid must be declared in gameScreen, user must click on the jButton
    * @post         will either start the game assigning colors to each player baced
    on there combo box choice, or will clear the board.
    * @return       none
    */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        player1.playerColor = getClr((String)jComboBox1.getSelectedItem());
        player2.playerColor = getClr((String)jComboBox2.getSelectedItem());
        player3.playerColor = getClr((String)jComboBox3.getSelectedItem());
        
        if (player1.playerColor != player2.playerColor && 
                    player2.playerColor != player3.playerColor) {
            switch (jButton1.getText()) {
                case "Start Game"->{
                    jButton1.setText("Reset Game");
                    jTextField1.setText("Player 1");
                }
                case "Reset Game"->{
                    jButton1.setText("Start Game");
                    for (int row = 0; row < 8; row ++) {
                        for (int col = 0; col < 8; col++) {
                            grid[row][col].setBackground(Color.white);
                        }
                    }
                    player1.playerColor = Color.white;
                    player2.playerColor = Color.white;
                    player3.playerColor = Color.white;
                    currentTurn = '1';
                    jTextField1.setText("Player 1");
                    jTextField2.setText("Player 2");
                    jTextField3.setText("Player 3");
                }
                default->{ System.out.println("An error has occured."); }
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed
   
    /*
    * @brief        take the string from a players combo box, and return correct Color.
    * @param[in]    String 'colorName' is the selected color in the combo box
    *
    * @pre          grid must be declared in gameScreen, user must select a color in the combo box
    * @post         will return color baceed on string receved
    * @return       Color baced on string
    */
    public Color getClr(String colorName) {
        switch (colorName) {
            case "Red"->{ return Color.red; }
            case "Green"->{ return Color.green; }
            case "Blue"->{ return Color.blue; }
            default->{System.out.println("An error has occured"); return null;}
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
