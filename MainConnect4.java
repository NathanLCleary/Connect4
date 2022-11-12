import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainConnect4 {
    private static int player1Score = 0;
    private static int player2Score = 0;

    static class MainMenu extends JFrame implements ActionListener
    {


        private JButton play = new JButton("Play"), exit = new JButton("Exit");
        private static JLabel player1 = new JLabel("Player 1: " + player1Score);
        private static JLabel player2 = new JLabel("Player 2: " + player2Score);

        public MainMenu()
        {
                setLayout(new GridLayout(2, 2));
                setSize(400, 400);
                setTitle("Connect 4 Menu");

                play.addActionListener(this);
                exit.addActionListener(this);

                add(player1);
                add(player2);
                add(play);
                add(exit);


                this.setVisible(true);

        }
        public static void updateScoreBoard(int winner){
            if(winner == 1){
                player1Score++;
                player1.setText("Player 1: " + player1Score);
            }

            else{
                player2Score++;
                player2.setText("Player 2: " + player2Score);
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == play) {
                new Connect4().init();
            }
            else
            {
                System.exit(0);
            }

        }
    }

    public static void main(String[] args) {
        new MainMenu();
    }
}
