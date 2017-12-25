package qiuzhan;

import java.awt.Color;
import javax.swing.JOptionPane;

public class MoveThread extends Thread {
    public MoveBallGame mb;
    public int x=100,y=100;
    public int xSpeed = 5;
    public int ySpeed = 5;
    public int width;
    public Color color ;
    public boolean isBegin = true;
    public MoveThread(MoveBallGame mb){
        this.mb = mb;
    }
    public void run(){
        while (!mb.overFlag) {
            while(isBegin){
                //控制敌方小球移动
                x+=xSpeed;
                y+=ySpeed;
                //判断地方小球是否出界
                if(this.x<10 || this.x>800-this.width-10){
                    xSpeed = -xSpeed;
                }
                if(this.y<30 || this.y>600-this.width-10){
                    ySpeed = -ySpeed;
                }
                if(mb.myball.x<4){
                    mb.myball.x=4;
                }
                if(mb.myball.y<28){
                    mb.myball.y=28;
                }
                if(mb.myball.x>800-mb.myball.width-4){
                    mb.myball.x = 800 - mb.myball.width-4;
                }
                if(mb.myball.y>600-mb.myball.width-4){
                    mb.myball.y = 600-mb.myball.width-4;
                }
                //碰撞检测
                isHit();
                //repaint()是重要概念，它是在图形线程后追加一段重绘操作，是安全的！是系统真正调用的重绘！
                //所以如果你需要某个部件刷新一下界面，记得调用repaint()，千万不要直接调用paint()！
                mb.repaint();
                //线程休眠
                try {
                    Thread.sleep(50);
                }
                catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
            //线程休眠
            try{
                Thread.sleep(50);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
    public void isHit(){
        int x = ((this.x+this.width)-(mb.myball.x+mb.myball.width))*((this.x+this.width)-(mb.myball.x+mb.myball.width));
        int y = ((this.y+this.width)-(mb.myball.y+mb.myball.width))*((this.y+this.width)-(mb.myball.y+mb.myball.width));
        int r = (int)Math.sqrt(x+y);
        if(r<=(this.width/2+mb.myball.width/2)){
            if(this.width>mb.myball.width){
                isBegin = false;
                for(int i=0;i<MoveBallGame.list.size();i++){
                    MoveBallGame.list.get(i).isBegin = false;
                }
                JOptionPane.showMessageDialog(null,"你被大球吃掉了!");
                mb.overFlag = true;
                return;
            }
            if(this.width<=mb.myball.width){
                mb.myball.width+=2;
                mb.score+=this.width;
                this.isBegin = false;
                MoveBallGame.list.remove(this);
                if(mb.myball.width>=300){
                    isBegin = false;
                    MoveBallGame.list.remove(this);
                    for(int i=0;i<MoveBallGame.list.size();i++){
                        MoveBallGame.list.get(i).isBegin = false;
                    }
                    JOptionPane.showMessageDialog(null,"恭喜你赢了!");
                    mb.overFlag = true;
                }
            }
        }
        for(int i=0;i<MoveBallGame.list.size();i++){
            MoveThread mtb = MoveBallGame.list.get(i);
            if(mtb==this){
                continue;
            }
            else{
                int x1 = ((mtb.x + mtb.width) - (this.x + this.width)) * ((mtb.x + mtb.width) - (this.x + this.width));
                int y1 = ((mtb.y + mtb.width) - (this.y + this.width)) * ((mtb.y + mtb.width) - (this.y + this.width));
                int r1 = (int) Math.sqrt(x1 + y1);
                if(r1<=(mtb.width/2+this.width/2)){
                    mtb.xSpeed = -mtb.xSpeed;
                    mtb.ySpeed = -mtb.ySpeed;
                    this.xSpeed = -this.xSpeed;
                    this.ySpeed = -this.ySpeed;
                }
            }
        }
    }
}
