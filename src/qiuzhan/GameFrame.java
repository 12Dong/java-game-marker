package qiuzhan;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameFrame extends JFrame{
    public boolean flag = true;
    public JPanel panel1,panel2;
    public Graphics g;

    public ImageIcon bgimg = new ImageIcon("img/pic1.jpg");
    public void initFrame(){
        this.setTitle("球球大作战");
        this.setSize(800,600);
        this.setDefaultCloseOperation(3);
        this.setLocationRelativeTo(null);
        panel1 = new JPanel();
        panel2 = new JPanel();
        panel1.setBackground(Color.red);
        panel2.setBackground(Color.green);
        changePanel(this);
        this.add(panel1);
        this.setVisible(true);
        g = panel1.getGraphics();
        System.out.println("this:"+g);
    }

    public void changePanel(GameFrame f){
        new Thread(){
            public void run(){
                while(true){
                    if(!flag){
                        f.remove(panel1);
                        f.add(panel2);
                        f.repaint();
                        f.setVisible(true);
                    }
                    if(flag){
                        f.remove(panel2);
                        f.add(panel1);
                        f.repaint();
                        f.setVisible(true);
                    }
                    try{
                            sleep(2000);
                            flag = !flag;
                }
                    catch(InterruptedException e) {
                        e.printStackTrace();
                    }
                    }
            }
        }.start();
    }

}
