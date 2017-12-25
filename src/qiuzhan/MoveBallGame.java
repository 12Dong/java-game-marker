package qiuzhan;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.*;
import java.awt.Image;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MoveBallGame extends JFrame {

    //敌方小球容器类
    public static ArrayList<MoveThread> list = new ArrayList<MoveThread>();
    //随机器
    public Random r = new Random();

    public MyBall myball;
    public BallMouseListener b1;

    public MoveBallGame jf;

    public JPanel panel1, overPanel;

    public int score = 0;
    //检测游戏是否结束的标志
    public boolean overFlag = false;
    //检测添加小球的线程是否结束
    public boolean over = false;

    public MoveBallGame() {
        myball = new MyBall(0, 500, 1, 60);
        b1 = new BallMouseListener(myball);
        //添加背景音乐/
        //com.huaxin.music.Util.startMusic("music/bg1.wav");
    }

    //窗口初始化
    public void initFrame() {
        jf = this;
        this.setSize(800, 600);
        this.setDefaultCloseOperation(3);
        this.setResizable(false);
        this.setTitle("球球大作战");
        /*
        public void setLocationRelativeTo(Component c)
设置窗口相对于指定组件的位置。

如果组件当前未显示或者 c 为 null，则此窗口将置于屏幕的中央。
         */
        this.setLocationRelativeTo(null);
        panel1 = new JPanel();
        overPanel = new JPanel();
        this.add(panel1);
        this.setVisible(true);

        //拿到窗体的画笔
        Graphics g = panel1.getGraphics();
        // 添加监听
        BallKeyListener k1 = new BallKeyListener(myball);
        panel1.addKeyListener(k1);
        panel1.addMouseMotionListener(b1);


        checkBallCount();
    }
    //paint 方法画小球 敌方小球和我方小球 并利用双缓冲解决闪频问题
    public void paint(Graphics g) {
        //创建一张图片
        Image buffImage = this.createImage(800, 600);
        //拿到这张图片的画笔
        Graphics buffg = buffImage.getGraphics();
        //设置背景图片
        ImageIcon bgimg = new ImageIcon("F:\\yanjiu\\java\\gamemaker_qiuqiudazuozhan\\src\\qiuzhan\\img\\pic1.jpg");
        buffg.drawImage(bgimg.getImage(), 0, 0, null);
        //循环将容器里面的小球画出来
        for (int i = 0; i < list.size(); i++) {
            MoveThread mt = list.get(i);
            buffg.setColor(mt.color);
            buffg.fillOval(mt.x, mt.y, mt.width, mt.width);
        }
        buffg.setColor(Color.orange);
        //filloval()是用颜色填充椭圆
        buffg.fillOval(myball.x,myball.y,myball.width,myball.width);

        //利用窗体的画笔将整张图片画进窗体
        g.drawImage(buffImage,0,0,null);
    }

    public void checkBallCount() {
        new Thread() {
            public void run() {
                while (!over) {
                    while (!overFlag) {
                        //如果屏幕上没有敌方小球了 那么重新画留个小球
                        if (MoveBallGame.list.size() == 0) {
                            for (int j = 0; j < 6; j++) {
                                MoveThread mt = new MoveThread(jf);
                                mt.x += 70 * j;
                                mt.y += 50 * j;
                                mt.xSpeed = 5;
                                mt.ySpeed = 5;
                                mt.width = jf.r.nextInt(20) + 50;
                                mt.color = new Color(jf.r.nextInt(1000));
                                MoveBallGame.list.add(mt);
                                mt.start();
                            }
                        }
                        //线程休眠
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    //结束页面
                    if (overFlag) {
                        jf.remove(panel1);
                        jf.add(overPanel);
                        jf.repaint();
                        jf.setVisible(true);


                        Graphics g = overPanel.getGraphics();
                        Graphics2D g1 = (Graphics2D) g;
                        ImageIcon overimg = new ImageIcon("F:\\yanjiu\\java\\gamemaker_qiuqiudazuozhan\\src\\qiuzhan\\img\\pic2.jpg");
                        g1.drawImage(overimg.getImage(), 0, 0, null);
                        g1.setColor(Color.red);
                        g1.setStroke(new BasicStroke(5));
                        g1.drawString("游戏结束", 300, 200);
                        g.drawString("得分:" + score, 350, 200);

                        over = true;
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


}