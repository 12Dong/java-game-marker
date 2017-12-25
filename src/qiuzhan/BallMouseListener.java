package qiuzhan;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.*;
public class BallMouseListener implements MouseMotionListener{
    public MyBall ball;
    public BallMouseListener(MyBall ball){
        this.ball = ball;
    }
    public void mouseDragged(MouseEvent e){

    }
    public void mouseMoved(MouseEvent e){
        int x = e.getX();
        int y = e.getY();
        ball.x = x - ball.width/2;
        ball.y = y;
    }

}
