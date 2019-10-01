import java.awt.*;

public class BreakableWall {
    private int x,y;
    private int health;
    Image image;
    Image image2;

    public BreakableWall(int x, int y){
        health=4;
        this.x=x;
        this.y=y;
        
            image= ResourceLoader.getImage("wall2.png");
            image2= ResourceLoader.getImage("wall3.png");

    }

    public void getsHit(){
        health--;

    }

    public boolean healthZero(){
        if(this.health==0){
            return true;
        }
        else{
            return false;
        }
    }
    
    public void setTank(Tank aTank, int x, int y) {
    	aTank.setx(x);
    	aTank.sety(y);
    }

    public Rectangle bounds(){
        return (new Rectangle(x,y,30,30));
    }

    public void render(Graphics g){

         if(health>2){
            g.drawImage(image,x,y,30,30,null);
        }
        else {
            g.drawImage(image2, x, y, 30, 30, null);
        }
    }

}
