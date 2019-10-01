import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Rectangle;

import static javax.imageio.ImageIO.read;

/**
 *
 * @author anthony-pc
 */
public class Tank{

    private int x;
    private int y;
    private int vx;
    private int vy;
    private int angle;
    private int health;
    private int lives;

    private final double R = 1.25;
    private final int ROTATIONSPEED = 4;
    private Image img;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean shootPressed;
    public boolean shooting=false;
    private ArrayList<Bullet> bullets=new ArrayList<Bullet>();
    private Image livesImg;
   

    Tank(int x, int y, int vx, int vy, int angle, Image img) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.img = img;
        this.angle = angle;
        health=100;
        lives=3;
            this.livesImg=ResourceLoader.getImage("lives.png");

    }


    void toggleUpPressed() {
        this.UpPressed = true;
    }

    void toggleDownPressed() {
        this.DownPressed = true;
    }

    void toggleRightPressed() {
        this.RightPressed = true;
    }

    void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    void toggleshootPressed(){
        this.shootPressed=true;
    }

    void unToggleUpPressed() {
        this.UpPressed = false;
    }

    void unToggleDownPressed() {
        this.DownPressed = false;
    }

    void unToggleRightPressed() {
        this.RightPressed = false;
    }

    void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    void unToggleShootPressed(){
        this.shootPressed=false;
        shooting=false;
    }


    public void update() {
        if (this.UpPressed) {
            this.moveForwards();
        }
        if (this.DownPressed) {
            this.moveBackwards();
        }

        if (this.LeftPressed) {
            this.rotateLeft();
        }
        if (this.RightPressed) {
            this.rotateRight();
        }
        if(this.shootPressed){


        }

    }

    public void addBullet(){
        bullets.add(new Bullet( this.x,this.y,this.vx,this.vy,this.angle));
    }

    public void setLives(){
        this.lives++;
    }
    private void rotateLeft() {
        this.angle -= this.ROTATIONSPEED;
    }

    private void rotateRight() {
        this.angle += this.ROTATIONSPEED;
    }

    private void moveBackwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x -= vx;
        y -= vy;

    }

    private void moveForwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;

        
    }

    public void collideTank(Tank aTank){
        x=x-5;


    }



    public int getx() {
        return this.x;
    }

    public int gety() {
        return this.y;
    }

    public void setx(int x) {
         this.x= x;
    }
    public void sety(int y) {
        this.y= y;
   }
    
    public int getHealth(){
        return this.health;
    }

    public int getLives(){
        return this.lives;
    }

    public ArrayList<Bullet> getBullets(){
        return bullets;
    }

    public void removeBullet(int x){
        bullets.remove(x);
    }

    public void getsHit(){
        for(int i=0; i<10;i++) {
        	health--;
        }
        if(health==0){
            lives--;
            health=100;
        }
    }

    public boolean gameOverSet(){
        if(this.lives==0){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }


    public Rectangle bounds(){
        return (new Rectangle(x,y,50,50));
    }
    void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getHeight(null) / 2.0, this.img.getHeight(null) / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);
        if(health>=90) {
            g2d.setColor(Color.GREEN);
            g2d.drawRect(this.x, this.y - 30, (health) / 2, 15);
            g2d.fillRect(this.x, this.y - 30, (health) / 2, 15);
        }
        if(health>35 && health<90){
            g2d.setColor(Color.YELLOW);
            g2d.drawRect(this.x, this.y - 30, (health) / 2, 15);
            g2d.fillRect(this.x, this.y - 30, (health) / 2, 15);
        }
        if(health<=35){
            g2d.setColor(Color.RED);
            g2d.drawRect(this.x, this.y - 30, (health) / 2, 15);
            g2d.fillRect(this.x, this.y - 30, (health) / 2, 15);
        }
        for(int i=0;i<lives;i++){
            g2d.drawImage(livesImg,this.x-35+(25*i), this.y+60,20,20,null);
        }
    }



}
