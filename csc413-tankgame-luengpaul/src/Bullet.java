import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static javax.imageio.ImageIO.read;

public class Bullet {
        private double x;
        private double y;
        private int angle;
        private int vx;
        private int vy;
        private double R=1;
        Image image;
        Image explosion;

    public Bullet(double x, double y, int vx, int vy, int angle){
        this.x=x+40;
        this.y=y+ 17;
        this.angle=angle;
        this.vx=vx;
        this.vy=vy;
            this.image= ResourceLoader.getImage("new_bullet.png");;
            explosion= ResourceLoader.getImage("explosion.PNG");
       

    }

    public Rectangle bounds(){
        return (new Rectangle((int)x,(int)y,20,20));
    }

    public double getX(){
        return this.x;
    }

    public double getY(){
        return this.y;
    }
    public void setExplosion(){
        this.image=explosion;
    }


    public void update(){
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle+180)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle+180)));
        x -= vx;
        y -= vy;

    }
    public void render(Graphics g)
    {
        g.drawImage(image,(int)x, (int) y,20,20, null);
    }
}
