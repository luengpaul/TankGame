import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static javax.imageio.ImageIO.read;

public class PowerUp {
    private int x;
    private int y;
    private Image image;
    public PowerUp(int x, int y){
        this.x=x;
        this.y=y;
            image=ResourceLoader.getImage("PowerUp.PNG");
 
    }

    public void getPowerUp(Tank aTank){
        aTank.setLives();
    }

    public Rectangle bounds(){
        return (new Rectangle(x,y,50,40));
    }

    public void render(Graphics g){

        g.drawImage(image,x,y,50,40,null);

    }
}
