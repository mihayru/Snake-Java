import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GameField extends JPanel implements ActionListener {
    private final int SIZE = 320;
    private final int DOT_SIZE = 16;
    private final int ALL_DOTS = 400;
    private Image dot;
    private Image apple;
    private int appleX;
    private int appleY;
    private int[] x = new int[400];
    private int[] y = new int[400];
    private int dots;
    private Timer timer;
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;

    public GameField() {
        this.setBackground(Color.black);
        this.loadImages();
        this.initGame();
        this.addKeyListener(new FieldKeyListener());
        this.setFocusable(true);
    }

    public void initGame() {
        this.dots = 3;

        for(int i = 0; i < this.dots; ++i) {
            this.x[i] = 48 - i * 16;
            this.y[i] = 48;
        }

        this.timer = new Timer(250, this);
        this.timer.start();
        this.createApple();
    }

    public void createApple() {
        this.appleX = (new Random()).nextInt(20) * 16;
        this.appleY = (new Random()).nextInt(20) * 16;
    }

    public void loadImages() {
        ImageIcon iia = new ImageIcon("apple.png");
        this.apple = iia.getImage();
        ImageIcon iid = new ImageIcon("dot.png");
        this.dot = iid.getImage();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.inGame) {
            g.drawImage(this.apple, this.appleX, this.appleY, this);

            for(int i = 0; i < this.dots; ++i) {
                g.drawImage(this.dot, this.x[i], this.y[i], this);
            }
        } else {
            String str = "Game Over";
            g.setColor(Color.white);
            g.drawString(str, 125, 160);
        }

    }

    public void move() {
        for(int i = this.dots; i > 0; --i) {
            this.x[i] = this.x[i - 1];
            this.y[i] = this.y[i - 1];
        }

        int[] var10000;
        if (this.left) {
            var10000 = this.x;
            var10000[0] -= 16;
        }

        if (this.right) {
            var10000 = this.x;
            var10000[0] += 16;
        }

        if (this.up) {
            var10000 = this.y;
            var10000[0] -= 16;
        }

        if (this.down) {
            var10000 = this.y;
            var10000[0] += 16;
        }

    }

    public void checkApple() {
        if (this.x[0] == this.appleX && this.y[0] == this.appleY) {
            ++this.dots;
            this.createApple();
        }

    }

    public void checkCollisions() {
        for(int i = this.dots; i > 0; --i) {
            if (i > 4 && this.x[0] == this.x[i] && this.y[0] == this.y[i]) {
                this.inGame = false;
            }
        }

        if (this.x[0] > 320) {
            this.inGame = false;
        }

        if (this.x[0] < 0) {
            this.inGame = false;
        }

        if (this.y[0] > 320) {
            this.inGame = false;
        }

        if (this.y[0] < 0) {
            this.inGame = false;
        }

    }

    public void actionPerformed(ActionEvent e) {
        if (this.inGame) {
            this.checkApple();
            this.checkCollisions();
            this.move();
        }

        this.repaint();
    }

    public class FieldKeyListener extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_LEFT && !right){
                left = true;
                up = false;
                down = false;
            }if(key == KeyEvent.VK_RIGHT && !left){
                right = true;
                up = false;
                down = false;
            }
            if(key == KeyEvent.VK_UP && !down){
                right = false;
                up = true;
                left = false;
            }
            if(key == KeyEvent.VK_DOWN && !up){
                right = false;
                down = true;
                left = false;
            }

        }
    }
}
