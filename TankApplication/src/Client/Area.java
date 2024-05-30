package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Area extends JFrame implements KeyListener {
    private String colorTank;
    private Graphics graphics;
    private Tank tankRed;
    private Tank tankBlue = new Tank(500, 200, "src/Assets/tank100BlueLeft.png", "left");
    private boolean isLeft = false;
    private boolean isRight = false;
    private boolean isUp = false;
    private boolean isDown = false;
    private boolean event = false;
    private String filePath = new String();

    public boolean getEvent() { return event; }
    public void setEvent(boolean e) { event = e; }
    public Tank getTankRed() { return tankRed; }
    public Tank getTankBlue() { return tankBlue; }
    public String getColor() { return colorTank; }
    public String getPath() { return filePath; }

    public Area(String colorTank) {
        super();
        // Определение положение окна на экране
        setLocation(200, 100);
        // Определение размера окна
        setSize (600, 600);
        // Открываем окно
        setVisible(true);
        this.colorTank = colorTank;

        if (colorTank.equals("Red")) {
            filePath = "src/Assets/tank100" + colorTank + "Right.png";
            tankRed = new Tank(0, 200, "src/Assets/tank100" + colorTank + "Right.png", "right");
            tankBlue = new Tank(500, 200, "src/Assets/tank100BlueLeft.png", "left");
        }
        else {
            filePath = "src/Assets/tank100" + colorTank + "Left.png";
            tankRed = new Tank(500, 200, "src/Assets/tank100" + colorTank + "Left.png", "left");
            tankBlue = new Tank(0, 200, "src/Assets/tank100RedRight.png", "right");
        }

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(this);
        new MoveThread(this).start();

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()==KeyEvent.VK_LEFT) {
            isLeft = true;
            filePath = "src/Assets/tank100" + colorTank + "Left.png";
            event = true;
        }
        if (e.getKeyCode()==KeyEvent.VK_RIGHT) {
            isRight = true;
            filePath = "src/Assets/tank100" + colorTank + "Right.png";
            event = true;
        }
        if (e.getKeyCode()==KeyEvent.VK_UP) {
            isUp = true;
            filePath = "src/Assets/tank100" + colorTank + "Up.png";
            event = true;
        }
        if (e.getKeyCode()==KeyEvent.VK_DOWN) {
            isDown = true;
            filePath = "src/Assets/tank100" + colorTank + "Down.png";
            event = true;
        }
        if (e.getKeyCode()==KeyEvent.VK_SPACE) {
            FireInfo(tankRed.Fire(), tankRed.getDir());
            event = true;
        }
    }

   @Override
    public void keyReleased(KeyEvent e) {}

    private void FireInfo(Integer[] pos, String dir) {
        switch (dir) {
            case "up":
                for (int i = pos[1]; i >= 0; i--) {
                    if ((tankBlue.getY() <= i && i <= tankBlue.getY() + 100) &&
                            (tankBlue.getX() <= pos[0] && pos[0] <= tankBlue.getX() + 100))
                    {
                        repaint();
                        tankRed.setWin("w");
                        break;
                    }
                }
                break;

            case "down":
                for (int i = pos[1]; i < this.getHeight(); i++) {
                    if ((tankBlue.getY() <= i && i <= tankBlue.getY() + 100) &&
                            (tankBlue.getX() <= pos[0] && pos[0] <= tankBlue.getX() + 100))
                    {
                        repaint();
                        tankRed.setWin("w");
                        break;
                    }
                }
                break;

            case "right":
                for (int i = pos[0]; i < this.getWidth(); i++) {
                    if ((tankBlue.getX() <= i && i <= tankBlue.getX() + 100) &&
                            (tankBlue.getY() <= pos[1] && pos[1] <= tankBlue.getY() + 100))
                    {
                        repaint();
                        tankRed.setWin("w");
                        break;
                    }
                }
                break;

            case "left":
                for (int i = pos[0]; i >= 0; i--) {
                    if ((tankBlue.getX() <= i && i <= tankBlue.getX() + 100) &&
                            (tankBlue.getY() <= pos[1] && pos[1] <= tankBlue.getY() + 100))
                    {
                        repaint();
                        tankRed.setWin("w");
                        break;
                    }
                }
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent arg0) {}

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);

        if (tankRed.getWin().equals("w")) {
            JOptionPane.showMessageDialog(this, "You win");
            tankRed.setWin("l");
        }
        if (tankBlue.getWin().equals("w")){
            JOptionPane.showMessageDialog(this, "You lose");
            tankBlue.setWin("l");
            event = true;
        }

        g.drawImage(new ImageIcon(filePath).getImage(), tankRed.getX(), tankRed.getY(), null);
        g.drawImage(new ImageIcon(tankBlue.getPath()).getImage(), tankBlue.getX(), tankBlue.getY(), null);
    }

    private void animate() {
        if (isLeft) {
            isLeft = false;
            tankRed.Left();
            this.repaint();
        }
        if (isRight) {
            isRight = false;
            tankRed.Right();
            this.repaint();
        }
        if (isUp) {
            isUp = false;
            tankRed.Up();
            this.repaint();
        }
        if (isDown) {
            isDown = false;
            tankRed.Down();
            this.repaint();
        }
    }


    private class MoveThread extends Thread{
        Area runKeyboard;

        public MoveThread(Area runKeyboard) {
            super("MoveThread");
            this.runKeyboard = runKeyboard;
        }

        public void run(){
            while(true) {
                runKeyboard.animate();
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

