import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


import static javax.imageio.ImageIO.read;

public class UnBreakableWall {
    private int x,y;
    Image image;
    public UnBreakableWall(int x, int y){
        this.x=x;
        this.y=y;
            image= ResourceLoader.getImage("wall.png");


    }

    public Rectangle bounds(){
        return (new Rectangle(x,y,30,30));
    }
    
    public void setTank(Tank aTank, int x, int y) {
    	aTank.setx(x);
    	aTank.sety(y);
    }

    public void render(Graphics g){

            g.drawImage(image,x,y,30,30,null);

    }

}

