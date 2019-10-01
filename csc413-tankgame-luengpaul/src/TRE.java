/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;



public class TRE extends JPanel  {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int WORLD_WIDTH=1980;
    public static final int WORLD_HEIGHT=1080;
    public static final int SCREEN_WIDTH = 1024;
    public static final int SCREEN_HEIGHT = 768;
    private BufferedImage world;
    private Graphics2D buffer;
    private JFrame jf;
    public static Tank t1;
    public static Tank t2;
    private Image background;
    private Image lefthalf;
    private Image righthalf;
    private Image minimap;
    private Image wall;
    private Image breakableWallimg;
    public static ArrayList<BreakableWall> breakableWalls= new ArrayList<BreakableWall>();
    public static Bullet bullet1;
    public static boolean twcollision=false;
    public Image bulletimg;
    public static boolean gameOver=false;
    public static boolean gameOver2=false;
    public Image gameOverImg;
    public static ArrayList<UnBreakableWall> unBreakableWalls= new ArrayList<UnBreakableWall>();
    public static ArrayList<PowerUp> powerUps= new ArrayList<PowerUp>();
    public static int oldxT1;
    public static int oldyT1;
    public static int oldxT2;
    public static int oldyT2;

    public static void main(String[] args) {
        Thread x;
        TRE trex = new TRE();
        trex.init();
        try {

            while (true) {
            	oldxT1=trex.t1.getx();
            	oldyT1=trex.t1.gety();
            	oldxT2=trex.t2.getx();
            	oldyT2=trex.t2.gety();
                trex.t1.update();
                trex.t2.update();
                mapCollision(t1,oldxT1, oldyT1);
                tbWCollision(t1, oldxT1, oldyT1);
                tbWCollision(t2, oldxT2, oldyT2);
                tubWCollision(t1, oldxT1, oldyT1);
                tubWCollision(t2, oldxT2, oldyT2);
                ubwCollision(t1);
                ubwCollision(t2);
                tPCollision(t1);
                tPCollision(t2);
                bwCollision(trex.t1);
                bwCollision(trex.t2);
                tbCollision(trex.t1,trex.t2);
                tbCollision(trex.t2,trex.t1);
                gameOver=trex.t1.gameOverSet();
                gameOver2=trex.t2.gameOverSet();
                trex.repaint();
                System.out.println(trex.t1);
                System.out.println(trex.t2);
                Thread.sleep(1000 / 144);

                if(gameOver){
                    return;
                }
                if(gameOver2){
                    return;
                }

            }
        } catch (InterruptedException ignored) {

        }

    }


    private void init() {
        this.jf = new JFrame("Tank Rotation");
        this.world = new BufferedImage(TRE.SCREEN_WIDTH, TRE.SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Image t1img = null;
        Image t2img = null;

        
            System.out.println(System.getProperty("user.dir"));
            /*
             * note class loaders read files from the out folder (build folder in netbeans) and not the
             * current working directory.
             */
            //tank image set and bullet
            t1img = ResourceLoader.getImage("tank1.png");
            t2img= ResourceLoader.getImage("tank1.png");
            breakableWallimg=ResourceLoader.getImage("wall2.png");
            bulletimg=ResourceLoader.getImage("new_bullet.png");
            wall= ResourceLoader.getImage("wall.png");
            gameOverImg= ResourceLoader.getImage("new_bullet.png");
        
        //background load
            background = ResourceLoader.getImage("Background.png");

       
        powerUps.add(new PowerUp(800,200));
        powerUps.add(new PowerUp(800,600));
        powerUps.add(new PowerUp(200,600));
        powerUps.add(new PowerUp(200,400));
        //wall load
        //unbreakable walls
        int i=0;
        while( i<TRE.SCREEN_WIDTH){
            unBreakableWalls.add(new UnBreakableWall(i,0));
            i=i+30;
        }
        i=0;
        while( i<TRE.SCREEN_HEIGHT){
            unBreakableWalls.add(new UnBreakableWall(0,i));
            i=i+30;
        }
        i=0;
        while( i<TRE.SCREEN_HEIGHT){
            unBreakableWalls.add(new UnBreakableWall(TRE.SCREEN_WIDTH-30,i));
            i=i+30;
        }
        i=0;
        while( i<TRE.SCREEN_WIDTH){
            unBreakableWalls.add(new UnBreakableWall(i,TRE.SCREEN_HEIGHT-30));
            i=i+30;
        }
        i=0;
        while(i<300){
            unBreakableWalls.add(new UnBreakableWall(400,470+i));
            i=i+30;
        }
        i=0;
        while(i<300){
            unBreakableWalls.add(new UnBreakableWall(700,470+i));
            i=i+30;
        }
        i=0;
        while(i<300){
            unBreakableWalls.add(new UnBreakableWall(500,i));
            i=i+30;
        }
        

        //breakable walls
        int p=0;
        while( p<TRE.SCREEN_HEIGHT/2){
            breakableWalls.add(new BreakableWall(400,p));
            p=p+30;
        }
         p=0;
        while( p<TRE.SCREEN_WIDTH-720){
            breakableWalls.add(new BreakableWall(p,300));
            p=p+30;
        }
        p=0;
        while( p<TRE.SCREEN_WIDTH-720){
            breakableWalls.add(new BreakableWall(p,500));
            p=p+30;
        }
        p=0;
        while( p<TRE.SCREEN_HEIGHT/2){
            breakableWalls.add(new BreakableWall(600,p));
            p=p+30;
        }
        p=0;
        while( p<TRE.SCREEN_HEIGHT/2){
            breakableWalls.add(new BreakableWall(500,400+p));
            p=p+30;
        }

        p=0;
        while( p<390){
            breakableWalls.add(new BreakableWall(650+p,400));
            p=p+30;
        }


        //tank1 position
        t1 = new Tank(240, 200, 0, 0, 0, t1img);


        TankControl tc1 = new TankControl(t1, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);
        //tank2 position
        t2 = new Tank(740, 200, 0, 0, 0, t2img);


        TankControl tc2 = new TankControl(t2, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
        //wall


        this.jf.setLayout(new BorderLayout());
        this.jf.add(this);


        this.jf.addKeyListener(tc1);
        this.jf.addKeyListener(tc2);

        this.jf.setSize(TRE.SCREEN_WIDTH, TRE.SCREEN_HEIGHT + 30);
        this.jf.setResizable(false);
        jf.setLocationRelativeTo(null);

        this.jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jf.setVisible(true);


    }

    public static void bwCollision(Tank aTank){
        for(int g=0; g<aTank.getBullets().size(); g++){
            Rectangle rectangle1=aTank.getBullets().get(g).bounds();
            for(int t=0; t<breakableWalls.size(); t++){
                Rectangle rectangle2=breakableWalls.get(t).bounds();
                if(rectangle1.intersects(rectangle2)){
                    breakableWalls.get(t).getsHit();
                    aTank.getBullets().remove(g);
                    if(breakableWalls.get(t).healthZero()){
                        breakableWalls.remove(t);
                    }
                    break;
                }
            }
        }


    }


    public static void ubwCollision(Tank aTank){
        for(int g=0; g<aTank.getBullets().size(); g++){
            aTank.getBullets().get(g).update();
            Rectangle rectangle1=aTank.getBullets().get(g).bounds();
            for(int t=0; t<unBreakableWalls.size(); t++){
                Rectangle rectangle2=unBreakableWalls.get(t).bounds();
                if(rectangle1.intersects(rectangle2)){
                    aTank.getBullets().remove(g);
                    break;
                }
            }
        }
    }
    public static void tPCollision(Tank aTank){
        Rectangle rectangle1= aTank.bounds();
        for(int c=0; c<powerUps.size(); c++){
            Rectangle rectangle2= powerUps.get(c).bounds();
            if(rectangle1.intersects(rectangle2)){
                powerUps.get(c).getPowerUp(aTank);
                powerUps.remove(c);

            }
        }
    }
    public static void tbCollision(Tank aTank, Tank aTank2){
       Rectangle rectangle2 =aTank2.bounds();
        for(int q=0; q< aTank.getBullets().size(); q++ ){
            Rectangle rectangle1=aTank.getBullets().get(q).bounds();
            if(rectangle2.intersects(rectangle1)){
                aTank2.getsHit();
                aTank.getBullets().get(q).setExplosion();
                aTank.getBullets().remove(q);

            }
            }
        }
    
    
    public static void tbWCollision(Tank aTank,int x, int y) {
    	Rectangle rectangle1= aTank.bounds();
    	for(int q=0; q<breakableWalls.size();q++) {
    		Rectangle rectangle2= breakableWalls.get(q).bounds();
    		if(rectangle1.intersects(rectangle2)) {
    		breakableWalls.get(q).setTank(aTank,x, y);
    		}
    	}
    }
    
    public static void tubWCollision(Tank aTank,int x, int y) {
    	Rectangle rectangle1= aTank.bounds();
    	for(int q=0; q<unBreakableWalls.size();q++) {
    		Rectangle rectangle2= unBreakableWalls.get(q).bounds();
    		if(rectangle1.intersects(rectangle2)) {
    		unBreakableWalls.get(q).setTank(aTank,x, y);
    		}
    	}
    }
    
    public static void mapCollision(Tank aTank,int x, int y) {
    	Rectangle rectangle1= aTank.bounds();
    	Rectangle rectangle2= new Rectangle(TRE.SCREEN_WIDTH/2-125,TRE.SCREEN_HEIGHT-200,250,200);
    		if(rectangle1.intersects(rectangle2)) {
    			aTank.setx(x);
    	    	aTank.sety(y);
    		}
    	
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        buffer = world.createGraphics();
        // draw map background set
        buffer.drawImage(background,0,0,TRE.SCREEN_WIDTH,TRE.SCREEN_HEIGHT,null);

        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, TRE.SCREEN_WIDTH, TRE.SCREEN_HEIGHT);
        //draw tanks
        this.t1.drawImage(buffer);
        this.t2.drawImage(buffer);
        lefthalf=loadScreen(0,0,t1,world);
        for(int b=0; b<powerUps.size();b++){
            powerUps.get(b).render(buffer);
        }

        for(int e=0; e<unBreakableWalls.size(); e++){
            unBreakableWalls.get(e).render(buffer);
        }

        for(int n=0; n< breakableWalls.size();n++){
            breakableWalls.get(n).render(buffer);

        }

        // t1 bullet render
        for(int b=0; b<t1.getBullets().size();b++){
            t1.getBullets().get(b).render(buffer);
            System.out.println(t1.getBullets().size());

        }
        //t2 bullet render
        for(int b=0; b<t2.getBullets().size();b++){
            t2.getBullets().get(b).render(buffer);
        }
       minimap=world.getScaledInstance(TRE.SCREEN_WIDTH,TRE.SCREEN_HEIGHT,1);
        //draw screens player one screen
        //lefthalf=loadScreen(0,0,t1,world);
        //righthalf=loadScreen(0,0,t2,world);
        buffer.drawImage(minimap,TRE.SCREEN_WIDTH/2-125,TRE.SCREEN_HEIGHT-200,250,200,null);
       //buffer.drawImage(lefthalf,0,0,TRE.SCREEN_WIDTH/2,TRE.SCREEN_HEIGHT,null);
     // buffer.drawImage(righthalf,TRE.SCREEN_WIDTH/2,0,TRE.SCREEN_WIDTH/2,TRE.SCREEN_HEIGHT,null);
     

       //\minimap

       
 
        if(gameOver==true){
            buffer.drawImage(gameOverImg,100,50,800,700,null);
        }
        if(gameOver2==true){
            buffer.drawImage(gameOverImg,100,50,800,700,null);
        }




        //lefthalf=world.getSubimage(0,0,TRE.SCREEN_WIDTH/2,TRE.SCREEN_HEIGHT/2);
       // g2.drawImage(lefthalf,0,0,null);
        g2.drawImage(world,0,0,null);

    }


    public BufferedImage loadScreen(int x, int y, Tank aTank,BufferedImage camera){
    	if(aTank.getx()-240<0) {
    		camera=world.getSubimage(0, 0, TRE.SCREEN_WIDTH/2, TRE.SCREEN_HEIGHT);
    	}
    	else if(aTank.getx()+270>TRE.SCREEN_WIDTH) {
    		camera=world.getSubimage(TRE.SCREEN_WIDTH/2, 0, TRE.SCREEN_WIDTH/2, TRE.SCREEN_HEIGHT);
    	}
    	
    	else {
    		 camera=world.getSubimage(aTank.getx()-240,0,TRE.SCREEN_WIDTH/2,TRE.SCREEN_HEIGHT);
    	}
            return camera;

    }




}
