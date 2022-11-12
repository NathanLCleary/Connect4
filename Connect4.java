import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import static javax.swing.JOptionPane.showMessageDialog;

public class Connect4 extends JFrame {

    private ArrayList<Player> players = new ArrayList<>();

    private PlayArea playArea =  new PlayArea();
    
    enum Player{
        ONE, TWO
    }


    public Connect4(){
        setSize(750,750);
        setTitle("Connect 4");


    }

    public void init()
    {
        add(playArea);
        this.setVisible(true);
    }

    class PlayArea extends JPanel implements MouseListener {

        private char [][] values = new char[6][6];

        private int w, h;

        private Player player = Player.ONE;

        private boolean winner = false;
        private boolean verticalFull = false;

        public PlayArea()
        {

            addMouseListener(this);
            setBackground(Color.gray);
        }

        public void drawGrid(Graphics g)
        {
            Graphics2D g2d = (Graphics2D) g;

            int offset = 2;
            g2d.setStroke(new BasicStroke(offset * 2));

            //Horizontal lines
            for(int i = 1; i < values.length; i++)
            {
                g2d.drawLine(((w/values.length)*i)+offset, 0, ((w/values.length)*i)+offset, h);
            }

            // vertical Lines
            for(int i = 1; i < values[0].length; i++)
            {
                g2d.drawLine(0, ((h/values[0].length)*i)+offset, w, ((h/values[0].length)*i)+offset);
            }


        }

        public void drawValues(Graphics g)
        {
            Graphics2D g2d = (Graphics2D) g;

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);



            int offsetAcross = w/values.length;
            int offsetDown = h/values[0].length;

            Font f = new Font("TimesRoman", Font.BOLD, offsetAcross);

            FontMetrics metrics = g2d.getFontMetrics(f);

            g2d.setFont(f);

            for(int i = 0; i < values.length; i++)
            {
                for (int j = 0; j < values[i].length; j++)
                {
                    if(values[i][j] == 'X')
                    {
                        g2d.setColor(Color.RED);
                    }
                    else
                    {
                        g2d.setColor(Color.BLUE);
                    }

                    int x = (j*offsetAcross) + ((offsetAcross - metrics.stringWidth("X"))/2);
                    int y = (i*offsetDown) +  ((offsetDown - metrics.getHeight()) / 2) + metrics.getAscent();

                    //g2d.drawString(""+values[i][j], (j *offsetAcross) + 2,(i*offsetDown) + offsetDown - 2);

                    g2d.drawString(""+values[i][j], x,y);
                }
            }

        }

        public void clickedGridValue(MouseEvent e)
        {
            Point p = e.getPoint();

            int horizontalIndex = values[0].length -1;
            int verticalIndex = values.length - 1;

            for(int i = 0; i < values.length - 1; i++)
            {
                if (p.getX() >=  (w/values.length)*i && p.getX() < (w/values.length)*(i+1))
                {
                    horizontalIndex = i;
                    break;
                }
            }

            for(int i = 5; i >= 0; i--)
            {
                if(values[i][horizontalIndex] == 0){
                    verticalIndex = i;
                    verticalFull = false;
                    break;
                }
                if(i == 0 && verticalIndex == 5){
                    verticalFull = true;
                }
            }

            if(player == Player.ONE && !verticalFull)
            {
                values[verticalIndex][horizontalIndex] = 'X';
                repaint();

                if(checkWin())
                {
                    MainConnect4.MainMenu.updateScoreBoard(1);
                    System.out.println("Player one has won");
                    showMessageDialog(null, "Player One has won");
                }

                player = Player.TWO;
            }
            else if(player == Player.TWO && !verticalFull)
            {
                values[verticalIndex][horizontalIndex] = 'O';
                repaint();

                if(checkWin())
                {
                    MainConnect4.MainMenu.updateScoreBoard(2);
                    System.out.println("Player two has won");
                    showMessageDialog(null, "Player Two has won");

                }
                player = Player.ONE;
            }
        }

        @Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);

            w = this.getWidth();

            h = this.getHeight();

            drawGrid(g);

            drawValues(g);

        }

        public boolean checkWin()
        {
            if (checkDiagonal())
                return true;

            if (checkAcross())
                return true;


            if (checkDown())
                return true;

            return  false;
        }

        public boolean checkDiagonal()
        {
            final int maxx = 6;
            final int maxy = 6;


            int[][] directions = {{1,0}, {1,-1}, {1,1}, {0,1}};
            for (int[] d : directions) {
                int dx = d[0];
                int dy = d[1];
                for (int x = 0; x < maxx; x++) {
                    for (int y = 0; y < maxy; y++) {
                        int lastx = x + 3*dx;
                        int lasty = y + 3*dy;
                        if (0 <= lastx && lastx < maxx && 0 <= lasty && lasty < maxy) {
                            char w = values[x][y];
                            if (w != 0 && w == values[x+dx][y+dy] && w == values[x+2*dx][y+2*dy] && w == values[lastx][lasty]) {
                                return true;
                            }
                        }
                    }
                }
            }
            return false; // no winner
        }


        public boolean checkAcross()
        {
            int counter = 0;
            for(int i = 0; i < values.length; i++) {
                for (int j = 5; j > 0; j--) {
                    char current = values[i][j];
                    char next = values[i][j-1];

                    if (current != 0) {
                        if (current == next) {
                            counter++;
                            if (counter == 3) {
                                return true;
                            }
                        }
                        else
                            counter = 0;
                    }

                }
            }
            return false;
        }

        public boolean checkDown()
        {
            int counter = 0;
            for(int i = 0; i < values.length; i++) {
                for (int j = 5; j > 0; j--) {
                    char current = values[j][i];
                    char next = values[j - 1][i];

                    if (current != 0) {
                        if (current == next) {
                            counter++;
                            if (counter == 3) {
                                return true;
                            }
                        }
                        else
                            counter = 0;
                    }

                }
            }
            return false;
        }

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

            clickedGridValue(e);


        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}

