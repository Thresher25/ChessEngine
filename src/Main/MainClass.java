package Main;

import Pieces.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MainClass extends JPanel implements MouseListener{

    public JFrame frame;
    public Board gameBoard;
    public boolean isWhiteTurn = true;

    public static void main(String...args){

        MainClass mc = new MainClass();

        while(true){
            mc.frame.repaint();
        }

    }

    public MainClass(){

        gameBoard = new Board();

        this.setSize(1920, 1080);
        this.setVisible(true);
        this.setDoubleBuffered(true);
        this.addMouseListener(this);
        frame = new JFrame("JavaRTS");
        frame.setSize(1920, 1080);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(this);
        frame.repaint();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0,0,1920,1080);
        gameBoard.draw(g);
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        float x = e.getX()-230;
        float y = e.getY()-50;
        if(x/106<8 && x/106>=0 && y/105<8 && y/105>=0){

        }else{

        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        float x = e.getX()-230;
        float y = e.getY()-50;
        if(x/106<8 && x/106>=0 && y/105<8 && y/105>=0){

        }else{

        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
