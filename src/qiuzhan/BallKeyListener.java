package qiuzhan;

//233333333333333333
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javafx.scene.input.KeyCode;
public class BallKeyListener implements KeyListener {
    public MyBall ball;
    public BallKeyListener(MyBall ball){
            this.ball = ball;
    }
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        if(key==37){
            ball.x-=3;
        }
        if(key==38){
            ball.y-=3;
        }
        if(key==39){
            ball.x+=5;
        }
        if(key==40){
            ball.y+=5;
        }
    }
    public void keyReleased(KeyEvent e){

    }
    public void keyTyped(KeyEvent e){

    }
}
