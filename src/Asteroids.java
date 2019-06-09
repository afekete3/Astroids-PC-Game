import java.applet.Applet;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.Timer;


public class Asteroids extends Applet implements KeyListener, ActionListener {
    
    Button startButton, exitButton;
    int x1, x2, y1, y2, backgroundX, backgroundY, Rocks, TrackerRocks, BigRocks, BossRocks, Level, LevelIntermissionCount, SwitchCount;
    boolean left, right, up, down, spacebar, one, two, three, shipExplode, rockExplode, startMenu, LevelIntermission;
    double xTrackTo, yTrackTo;
    Random random;
    Spacecraft ship;
    Timer timer;
    Image offScreen;
    Graphics offG;
    
    /*
    * ArrayLists of  all Guns
    */
    ArrayList <Minigun> bulletLeft, bulletRight, grenadeBullets;
    ArrayList <EnergyBlaster> energyBlasterList;
    ArrayList <GrenadeLauncher> grenadeList;
    
    /*
    * ArratLists of all Rocks
    */
    ArrayList <Debris> debrisList;
    ArrayList <Rock> rockList;
    ArrayList <TrackerRock> trackerRockList;
    ArrayList <Rock> bigRockList;
    ArrayList <BossRock> bossRockList;
    ActionListener startMenuActionListener;
   

    /**
     * Initialization method that will be called after the applet is loaded
     * into the browser.
     */
    public void init() {
        startMenuActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              if (e.getActionCommand().equals("START")) {
                  startMenu = false;
                  LevelIntermission = true;
                  LevelIntermissionCount = 0;
                  reset();
                  requestFocusInWindow();
              }
              else if (e.getActionCommand().equals("EXIT")){
                  System.exit(0);
              }
            }
        };
        
         // Activating the Key Listener to listen to the applet
        this.addKeyListener(this);
        startButton = new Button("START");
        exitButton = new Button("EXIT");
        startButton.addActionListener(startMenuActionListener);
        exitButton.addActionListener(startMenuActionListener);
        startMenu = true;
        LevelIntermission = false;
        
         // default weapon is bullets
        one = true;
        two = false;
        three = false;
        
        // Setteing the length and width of the User Imterface
        backgroundX = 1200;
        backgroundY = 650;
        this.setSize(backgroundX, backgroundY);
        
        // Creating backround image
        offScreen = createImage(backgroundX, backgroundY);
        offG = offScreen.getGraphics();
        
        // Starting the timer - 50 frames a seconds
        timer = new Timer(20, this);
    }
    
    public void paint(Graphics g) {
        if (LevelIntermission) {
            exitButton.setVisible(false);
            startButton.setVisible(false);
            offG.setColor(Color.black);
            offG.fillRect(0,0,backgroundX, backgroundY);
            offG.setColor(Color.red);
            PaintLevel();
            g.drawImage(offScreen, x1, y1, this);
        }
        else if (startMenu){
            offG.setColor(Color.black);
            offG.fillRect(0,0,backgroundX, backgroundY);
            offG.setColor(Color.red);
            offG.setFont(new Font ("Harlow Solid Italic",1,50));
            offG.drawString("ASTEROIDS", backgroundX/2 - 200,backgroundY/2 - 200);
            startButton.setBounds(300, 450, 150, 60);
            startButton.setBackground(Color.red);
            startButton.setFont(new Font("TimesRoman",1,20));
            add(startButton);
            exitButton.setBounds(700, 450, 150, 60);
            exitButton.setBackground(Color.red);
            exitButton.setFont(new Font("TimesRoman",1,20));
            add(exitButton);
            g.drawImage(offScreen, x1, y1, this);
        }
        else if(checkGameOver()){
            PaintGameOver();
            g.drawImage(offScreen, x1, y1, this);
        }
        else{
            exitButton.setVisible(false);
            startButton.setVisible(false);
            
            /*
            * Setting up the backround
            */
            offG.setColor(Color.black);
            offG.fillRect(0, 0, backgroundX, backgroundY);
            offG.setColor(Color.GREEN);
            
            /*
            * Calling functions that paint each object
            */
            PaintShip();
            PaintRock();
            PaintTrackerRock();
            PaintBigRock();
            PaintBossRock();
            PaintBullets();
            PaintEnergyBlaster();
            PaintGrenades();
            PaintGrenadeBullets();
            PaintDebris();
            PaintLives();
            PaintWeaponList();
            PaintTailLights();
            
             /*
            * Drawing the UI
            */
             
            g.drawImage(offScreen, 0, 0, this);
             /*
            * This function recalls the paint function
            */
            repaint();
        }
      
    }
    
    public void PaintGameOver(){
        offG.setColor(Color.black);
        offG.fillRect(0,0,backgroundX, backgroundY);
        offG.setColor(Color.red);
        offG.setFont(new Font ("TimesRoman",1,60));
        offG.drawString("GAME OVER!", backgroundX/2 - 200, backgroundY/2);
    }
    
    public void PaintWeaponList(){
        offG.setColor(Color.GRAY);
        offG.setFont(new Font ("Agency FB",1,18));
        if(SwitchCount < 50){
            offG.setColor(Color.black);
        }
        if(one){
            offG.setColor(Color.GREEN);
        }
        offG.drawString("1: MiniGun ", 10, 20);
        offG.setColor(Color.GRAY);
        if(SwitchCount < 50){
            offG.setColor(Color.black);
        }
        if(two){
            offG.setColor(Color.GREEN);
        }
        offG.drawString("2: EnergyBlaster ", 10, 40);
        offG.setColor(Color.GRAY);
        if(SwitchCount < 50){
            offG.setColor(Color.black);
        }
        if(three){
            offG.setColor(Color.GREEN);
        }
        offG.drawString("3: FireWorks", 10, 60);
    }
    
    public void PaintLives(){
        if (ship.lives == 5 || ship.lives == 4) {
            offG.setColor(Color.GREEN);
        }
        if (ship.lives == 3) {
            offG.setColor(Color.YELLOW);
        }
        if (ship.lives == 2) {
            offG.setColor(Color.ORANGE);
        }
        if (ship.lives == 1) {
            offG.setColor(Color.RED);
        }
        offG.setFont(new Font ("TimesRoman",1,20));
        offG.drawString("Lives: " + String.valueOf(ship.lives), 1100, 50);
    }
    
    public void PaintLevel() {
        offG.setFont(new Font ("TimesRoman",1,25));
        offG.drawString("Level " + String.valueOf(Level + 1), backgroundX / 2, backgroundY / 2);
    }
    
    public void PaintBullets(){
        offG.setColor(Color.BLUE);
        for (int i = 0; i < bulletLeft.size(); i++) {
            bulletLeft.get(i).paint(offG);
        }
        for (int i = 0; i < bulletRight.size(); i++) {
            bulletRight.get(i).paint(offG);
        }    
    }
    public void PaintEnergyBlaster(){
       offG.setColor(Color.YELLOW);
        for ( int i = 0; i < energyBlasterList.size(); i++){
            energyBlasterList.get(i).paint(offG);
        }
    }
    public void PaintGrenades(){
       offG.setColor(Color.MAGENTA);
        for ( int i = 0; i < grenadeList.size(); i++){
            offG.fillPolygon(grenadeList.get(i).drawShape);
            grenadeList.get(i).paint(offG);
        }
    }
    public void PaintGrenadeBullets(){
       offG.setColor(Color.MAGENTA);
        for ( int i = 0; i < grenadeBullets.size(); i++){
            offG.fillPolygon(grenadeBullets.get(i).drawShape);
            grenadeBullets.get(i).paint(offG);
        }
    }
    public void PaintShip(){
        if (ship.active) {
             offG.setColor(Color.green);
             //offG.fillPolygon(ship.drawShape);
             ship.paintShip(offG);
        }
    }
    public void PaintTailLights(){
        if(ship.active){
            offG.setColor(Color.GREEN);
            if(down){
                offG.setColor(Color.red);
            }
            offG.drawLine(ship.drawShape.xpoints[1],ship.drawShape.ypoints[1] , ship.drawShape.xpoints[2], ship.drawShape.ypoints[2]);
            offG.drawLine(ship.drawShape.xpoints[2],ship.drawShape.ypoints[2] , ship.drawShape.xpoints[3], ship.drawShape.ypoints[3]);
        }
    }
    public void PaintRock(){
        offG.setColor(Color.RED);
        for (int i = 0; i < rockList.size(); i++) {
            rockList.get(i).paint(offG);
        }     
    }
    public void PaintTrackerRock(){
          offG.setColor(Color.YELLOW);
        for (int i = 0; i < trackerRockList.size(); i++) {
            trackerRockList.get(i).paint(offG);
        }   
    }
      public void PaintBigRock(){
        offG.setColor(Color.CYAN);
        for (int i = 0; i < bigRockList.size(); i++) {
            bigRockList.get(i).paint(offG);
        }     
    }
    public void PaintBossRock(){
        for (int i = 0 ; i < bossRockList.size(); i++){
             /*
              * the following if statements change the color of the BossRock based on its HP
              */
            if (bossRockList.get(i).HP <= 100){
                offG.setColor(Color.WHITE);
            }
            if (bossRockList.get(i).HP <= 75){
                offG.setColor(Color.YELLOW);
            }
            if (bossRockList.get(i).HP <= 50){
                offG.setColor(Color.ORANGE);
            }

            if (bossRockList.get(i).HP <= 25){
                offG.setColor(Color.RED);
            }
            bossRockList.get(i).paint(offG);
            /*
             * Setting the font of the HP
             */
            offG.setFont(new Font("TimesRoman",1,20));
            offG.drawString(String.valueOf(bossRockList.get(i).HP) + "HP",(int)bossRockList.get(i).xPosition - 30 , (int)bossRockList.get(i).yPosition);
        }           
    }
  
    public void PaintDebris(){
        for (int i = 0; i < debrisList.size(); i++) {
            if (rockExplode) {
                offG.setColor(Color.RED);
                debrisList.get(i).paint(offG);
            }
            if (shipExplode) {
               offG.setColor(Color.GREEN);
               debrisList.get(i).paint(offG);
            } 
        }      
    }
    
    public void update(Graphics g) { paint(g);}
    //Starts the timer - invoked by the browser
    public void start() {timer.start();}
    //Stops the timer - invoked by the browser
    public void stop() {timer.stop();}
    public void keyTyped(KeyEvent e) {}

    
    /*
    * This function checks if a single or multiple keys are pressed and act accordingly*
    */
    public void keyCheck() {
        if (right) {
        ship.rotateRight();
        }
        if (left) {
        ship.rotateLeft();
        }
        if (up) {
        ship.accelerate();
        }
        if (down) {
        ship.decelerate();
        }
        if (spacebar) {
            if (one){
                fireMiniGun();
            }
            if (two){
                fireEnergyBlaster();
            }
            if (three) {
                fireGrenadeLauncher();
            }
        }
    }
    
    /*
    * this function is called everytime a key is pressed
    */
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
          right = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
          left = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
          up = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
          down = true;
        }
        // space bar = shoot
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
          spacebar = true;
        }
        // key 3 = Grenade Launcher
        if (e.getKeyCode() == KeyEvent.VK_3){
            if (SwitchCount > 50) {
            if (three == false){
                ship.counter = 75;
                three = true;
                two = false;
                one = false;
            }
            SwitchCount = 0;
            }
        }
         // key 2 = Energy Blaster
        if (e.getKeyCode() == KeyEvent.VK_2){
            if (SwitchCount > 50) {
            if (two == false){
                ship.counter = 175;
                three = false;
                two = true;
                one = false;
            }
            SwitchCount = 0;
            }
        }
        // key 1 = Bullets
        if (e.getKeyCode() == KeyEvent.VK_1){
            if (SwitchCount > 50) {
            if (one == false){
                ship.counter = 75;
                three = false;
                two = false;
                one = true;
            }
            SwitchCount = 0;
            }
        }
    }
    /*
    * this function is called everytime a key is released
    */
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            right = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            left = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            up = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            down = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            spacebar = false;
        }
    }
    
    /*
    * this function is called 50 time per second
    */
    public void actionPerformed(ActionEvent e) {
        keyCheck();
        LevelIntermissionCount++;
        
        if (LevelIntermissionCount > 50 && LevelIntermission == true && checkGameOver() == false) {
            LevelIntermission = false;
            LevelIntermissionCount = 0;
            reset();
        }
        if (startMenu == false && LevelIntermission == false && checkGameOver() == false){
            respawnShip();
             SwitchCount++;
            /*
            * calling functions that update all the positions of objects
            */
            UpdateShip();
            UpdateRock();
            UpdateBigRock();
            UpdateTrackerRock();
            UpdateBossRock();
            UpdateBullets();
            UpdateEnergyBlaster();
            UpdateGrenades();
            UpdateDebris();
            UpdateGrenadeBullets();

            checkDestruction();
            checkGernadeExplosion();
            checkCollisions();
            bossMinions();
            checkBossHP();
            checkWin();
        }
    }
    public void UpdateBullets(){
        for(int i = 0; i < bulletLeft.size(); i++) {
            bulletLeft.get(i).updatePosition();
            if (bulletLeft.get(i).counter > 35 || bulletLeft.get(i).active == false) {
                bulletLeft.remove(i);
            }
        }
        for(int i = 0; i < bulletRight.size(); i++) {
            bulletRight.get(i).updatePosition();
            if (bulletRight.get(i).counter > 35 || bulletRight.get(i).active == false) {
                bulletRight.remove(i);
            }
        }   
    }
    public void UpdateEnergyBlaster(){
       for (int i = 0; i < energyBlasterList.size() ; i ++){
            energyBlasterList.get(i).updatePosition();
             if (energyBlasterList.get(i).counter > 500 || energyBlasterList.get(i).active == false) {
                energyBlasterList.remove(i);
            }
        }
    }
    public void UpdateGrenades(){
       for (int i = 0; i < grenadeList.size() ; i ++){
            grenadeList.get(i).updatePosition();
             if (grenadeList.get(i).counter > 75 || grenadeList.get(i).active == false) {
                grenadeList.remove(i);
            }
        }
    }
    public void UpdateGrenadeBullets(){
       for (int i = 0; i < grenadeBullets.size() ; i ++){
            grenadeBullets.get(i).updatePosition();
             if (grenadeBullets.get(i).counter > 75 || grenadeBullets.get(i).active == false) {
                grenadeBullets.remove(i);
            }
        }
    }
    public void UpdateRock(){
        for(int i = 0; i < rockList.size(); i++) {
            rockList.get(i).updatePosition();
        }
    }
    public void UpdateTrackerRock(){
        for(int i = 0; i < trackerRockList.size(); i++) {

            if (ship.active){
                 if(random.nextBoolean()){
                    yTrackTo= 625;
                }
                else yTrackTo = 0;

                if(random.nextBoolean()){
                    xTrackTo= 1175;
                }
                else xTrackTo = 0;
                trackerRockList.get(i).updatePosition(ship.xPosition,ship.yPosition,trackerRockList.get(i).xPosition,trackerRockList.get(i).yPosition);
            }
            else {
                trackerRockList.get(i).updatePosition(xTrackTo,yTrackTo,trackerRockList.get(i).xPosition,trackerRockList.get(i).yPosition);
            }
        }
    }
    public void UpdateBigRock(){
        for(int i = 0; i < bigRockList.size(); i++) {
            bigRockList.get(i).updatePosition();
        }
    }
    public void UpdateBossRock(){  
        for(int i = 0; i < bossRockList.size(); i++) {
          bossRockList.get(i).updatePosition();
        }     
    }
    public void UpdateShip(){
        ship.updatePosition();
    }
    public void UpdateDebris(){
        for(int i = 0; i < debrisList.size(); i++) {
            debrisList.get(i).updatePosition();
            if (debrisList.get(i).counter > 25) {
                    debrisList.remove(i);
            }
        } 
    }
    public boolean collision(VectorSprite thing1, VectorSprite thing2) {
        int x,y;
        for(int i = 0; i < thing1.drawShape.npoints; i++) {
            x = thing1.drawShape.xpoints [i];
            y = thing1.drawShape.ypoints [i];
            if (thing2.drawShape.contains(x,y)) {
                return true;
            }
        }
        for (int i = 0; i < thing2.drawShape.npoints; i++) {
            x = thing2.drawShape.xpoints [i];
            y = thing2.drawShape.ypoints [i];
            if (thing1.drawShape.contains(x,y)) {
                return true;
            }
        }
        for (int i = 0; i < thing1.drawgunLeft.npoints; i++) {
            x = thing1.drawgunLeft.xpoints [i];
            y = thing1.drawgunLeft.ypoints [i];
            if (thing2.drawShape.contains(x,y)) {
                return true;
            }
            x = thing1.drawgunRight.xpoints [i];
            y = thing1.drawgunRight.ypoints [i];
            if (thing2.drawShape.contains(x,y)) {
                return true;
            }
        }
        return false;
    }
    public boolean bulletCollision(VectorSprite thing1, VectorSprite thing2){
         int x,y;
        for(int i = 0; i < thing1.drawShape.npoints; i++) {
            x = thing1.drawShape.xpoints [i];
            y = thing1.drawShape.ypoints [i];
            if (thing2.drawShape.contains(x,y)) {
                return true;
            }
        }
        for (int i = 0; i < thing2.drawShape.npoints; i++) {
            x = thing2.drawShape.xpoints [i];
            y = thing2.drawShape.ypoints [i];
            if (thing1.drawShape.contains(x,y)) {
                return true;
            }
        }
         return false;
    }
    public void checkCollisions() {
       checkRockCollision();
       checkTrackerRockCollision();
       checkBigRockCollision();
       checkBossRockCollision();                       
    }
    public void checkRockCollision(){
         for (int i = 0; i < rockList.size(); i++) {
             //declaring a random number to use for the size of the debris
            double rnd;
            if (ship.active && collision(ship, rockList.get(i))) {
                shipExplode = true;
                rockExplode = false;
                ship.hit();
                rnd = Math.random()*10 + 25;
                for(int k = 0; k < rnd; k++) {
                debrisList.add(new Debris(ship.xPosition, ship.yPosition));
                } 
            }
                for (int j = 0; j < bulletLeft.size(); j++) {
                    if (bulletCollision(bulletLeft.get(j), rockList.get(i))) {
                        shipExplode = false;
                        rockExplode = true;
                        bulletLeft.get(j).active = false;
                        rockList.get(i).active = false;
                        rnd = Math.random()*5 + 5;
                        for(int k = 0; k < rnd; k++) {
                                debrisList.add(new Debris(bulletLeft.get(j).xPosition, bulletLeft.get(j).yPosition));
                        }
                    }
                }
                for (int j = 0; j < bulletRight.size(); j++) {
                    if (bulletCollision(bulletRight.get(j), rockList.get(i))) {
                        shipExplode = false;
                        rockExplode = true;
                        bulletRight.get(j).active = false;
                        rockList.get(i).active = false;
                        rnd = Math.random()*5 + 5;
                        for(int k = 0; k < rnd; k++) {
                                debrisList.add(new Debris(bulletRight.get(j).xPosition, bulletRight.get(j).yPosition));
                        }
                    }
                }
                for (int j = 0; j < energyBlasterList.size(); j++) {
                    if (bulletCollision(energyBlasterList.get(j), rockList.get(i))) {
                        if (rockList.get(i).rockSize == 1){
                          rockList.get(i).active = false;
                          energyBlasterList.get(j).smallCount++;
                        }
                        else {
                        energyBlasterList.get(j).active = false;
                        rockList.get(i).active = false;
                        }
                        if (energyBlasterList.get(j).smallCount == 8){
                            energyBlasterList.get(j).active = false;
                        }
                    }
                }
                for (int j = 0; j < grenadeList.size(); j++) {
                    if (bulletCollision(grenadeList.get(j), rockList.get(i))) {
                        grenadeList.get(j).active = false;
                        rockList.get(i).active = false;
                    }
                }
                for (int j = 0; j < grenadeBullets.size(); j++) {
                    if (bulletCollision(grenadeBullets.get(j), rockList.get(i))) {
                        grenadeBullets.get(j).active = false;
                        rockList.get(i).active = false;
                    }
                }
        }
    }
    public void checkTrackerRockCollision(){
        for (int i = 0; i < trackerRockList.size(); i++) {
            //declaring a random number to use for the size of the debris
            double rnd;
            if (ship.active && collision(ship, trackerRockList.get(i))) {
                shipExplode = true;
                rockExplode = false;
                ship.hit();
                rnd = Math.random()*10 + 25;
                for(int k = 0; k < rnd; k++) {
                debrisList.add(new Debris(ship.xPosition, ship.yPosition));
                }
            }
                for (int j = 0; j < bulletLeft.size(); j++) {
                    if (bulletCollision(bulletLeft.get(j), trackerRockList.get(i))) {
                        shipExplode = false;
                        rockExplode = true;
                        bulletLeft.get(j).active = false;
                        trackerRockList.get(i).active = false;
                        rnd = Math.random()*5 + 5;
                        for(int k = 0; k < rnd; k++) {
                                debrisList.add(new Debris(bulletLeft.get(j).xPosition, bulletLeft.get(j).yPosition));
                        }
                    }
                }
                for (int j = 0; j < bulletRight.size(); j++) {
                    if (bulletCollision(bulletRight.get(j), trackerRockList.get(i))) {
                        shipExplode = false;
                        rockExplode = true;
                        bulletRight.get(j).active = false;
                        trackerRockList.get(i).active = false;
                        rnd = Math.random()*5 + 5;
                        for(int k = 0; k < rnd; k++) {
                                debrisList.add(new Debris(bulletRight.get(j).xPosition, bulletRight.get(j).yPosition));
                        }
                    }
                }
                for (int j = 0; j < energyBlasterList.size(); j++) {
                    if (bulletCollision(energyBlasterList.get(j), trackerRockList.get(i))) {
                        energyBlasterList.get(j).active = false;
                        trackerRockList.get(i).active = false;
                    }
                }
                for (int j = 0; j < grenadeList.size(); j++) {
                    if (bulletCollision(grenadeList.get(j), trackerRockList.get(i))) {
                        grenadeList.get(j).active = false;
                        trackerRockList.get(i).active = false;
                    }
                }
                for (int j = 0; j < grenadeBullets.size(); j++) {
                    if (bulletCollision(grenadeBullets.get(j), trackerRockList.get(i))) {
                        grenadeBullets.get(j).active = false;
                        trackerRockList.get(i).active = false;
                    }
                }
         }
    }
    public void checkBigRockCollision(){
        for (int i = 0; i < bigRockList.size(); i++) {
            //declaring a random number to use for the size of the debris
            double rnd;
            if (ship.active && collision(ship, bigRockList.get(i))) {
                shipExplode = true;
                rockExplode = false;
                ship.hit();
                rnd = Math.random()*10 + 25;
                for(int k = 0; k < rnd; k++) {
                debrisList.add(new Debris(ship.xPosition, ship.yPosition));
                }
            }
            for (int j = 0; j < bulletLeft.size(); j++) {
                if (bulletCollision(bulletLeft.get(j), bigRockList.get(i))) {
                    shipExplode = false;
                    rockExplode = true;
                    bulletLeft.get(j).active = false;
                    bigRockList.get(i).active = false;
                    rnd = Math.random()*5 + 5;
                    for(int k = 0; k < rnd; k++) {
                            debrisList.add(new Debris(bulletLeft.get(j).xPosition, bulletLeft.get(j).yPosition));
                    }
                }
            }
            for (int j = 0; j < bulletRight.size(); j++) {
                if (bulletCollision(bulletRight.get(j), bigRockList.get(i))) {
                    shipExplode = false;
                    rockExplode = true;
                    bulletRight.get(j).active = false;
                    bigRockList.get(i).active = false;
                    rnd = Math.random()*5 + 5;
                    for(int k = 0; k < rnd; k++) {
                              debrisList.add(new Debris(bulletRight.get(j).xPosition, bulletRight.get(j).yPosition));
                    }
                }
            }
              for (int j = 0; j < energyBlasterList.size(); j++) {
                if (bulletCollision(energyBlasterList.get(j), bigRockList.get(i))) {
                    if (bigRockList.get(i).rockSize == 1){
                      bigRockList.get(i).active = false;
                      energyBlasterList.get(j).smallCount++;
                    }
                    else {
                    energyBlasterList.get(j).active = false;
                    bigRockList.get(i).active = false;
                    }
                    if (energyBlasterList.get(j).smallCount == 8){
                        energyBlasterList.get(j).active = false;
                    }
                }
            }
            for (int j = 0; j < grenadeList.size(); j++) {
                if (bulletCollision(grenadeList.get(j), bigRockList.get(i))){
                    grenadeList.get(j).active = false;
                    bigRockList.get(i).active = false;
                }
            }
            for (int j = 0; j < grenadeBullets.size(); j++) {
                if (bulletCollision(grenadeBullets.get(j), bigRockList.get(i))) {
                    grenadeBullets.get(j).active = false;
                    bigRockList.get(i).active = false;
                }
            }

        }
    }
    public void checkBossRockCollision(){
          for (int i = 0; i < bossRockList.size(); i++) {
            double rnd;
            if (ship.active && collision(ship, bossRockList.get(i))) {
                shipExplode = true;
                rockExplode = false;
                ship.hit();
                rnd = Math.random()*10 + 25;
                for(int k = 0; k < rnd; k++) {
                debrisList.add(new Debris(ship.xPosition, ship.yPosition));
                }
            }
                for (int j = 0; j < bulletLeft.size(); j++) {
                    if (bulletCollision(bulletLeft.get(j), bossRockList.get(i))) {
                        shipExplode = false;
                        rockExplode = true;
                        bulletLeft.get(j).active = false;
                        bossRockList.get(i).HP -= 1;
                        rnd = Math.random()*5 + 5;
                        for(int k = 0; k < rnd; k++) {
                                debrisList.add(new Debris(bulletLeft.get(j).xPosition, bulletLeft.get(j).yPosition));
                        }
                    }
                }
                for (int j = 0; j < bulletRight.size(); j++) {
                    if (bulletCollision(bulletRight.get(j), bossRockList.get(i))) {
                        shipExplode = false;
                        rockExplode = true;
                        bulletRight.get(j).active = false;
                        bossRockList.get(i).HP -= 1;
                        rnd = Math.random()*5 + 5;
                        for(int k = 0; k < rnd; k++) {
                                debrisList.add(new Debris(bulletRight.get(j).xPosition, bulletRight.get(j).yPosition));
                        }
                    }
                }
                for (int j = 0; j < energyBlasterList.size(); j++) {
                    if (bulletCollision(energyBlasterList.get(j), bossRockList.get(i))) {
                        energyBlasterList.get(j).active = false;
                        bossRockList.get(i).HP -= 10;  
                    }
                }
                for (int j = 0; j < grenadeList.size(); j++) {
                    if (bulletCollision(grenadeList.get(j), bossRockList.get(i))) {
                        grenadeList.get(j).active = false;
                        bossRockList.get(i).HP -= 0;
                    }
                }
                for (int j = 0; j < grenadeBullets.size(); j++) {
                    if (bulletCollision(grenadeBullets.get(j), bossRockList.get(i))) {
                        grenadeBullets.get(j).active = false;
                        bossRockList.get(i).HP -= 2;
                    }
                }
        }
    }
    
    
    public void respawnShip() {
        if (ship.active == false && ship.counter > 100 && isRespawnSafe()) {
            ship.reset();
        }
    }
    public boolean isRespawnSafe() {
        int x, y, h;
        for (int i = 0; i < rockList.size(); i++) {
            x = (int)rockList.get(i).xPosition - 600;
            y = (int)rockList.get(i).yPosition - 325;
            h = (int)Math.sqrt(x* x + y* y);
            if (h < 100) {
                return false;
            }
        }
        for (int i = 0; i < trackerRockList.size(); i++) {
            x = (int)trackerRockList.get(i).xPosition - 600;
            y = (int)trackerRockList.get(i).yPosition - 325;
            h = (int)Math.sqrt(x* x + y* y);
            if (h < 100) {
                return false;
            }
        }
            for (int i = 0; i < bigRockList.size(); i++) {
            x = (int)bigRockList.get(i).xPosition - 600;
            y = (int)bigRockList.get(i).yPosition - 325;
            h = (int)Math.sqrt(x* x + y* y);
            if (h < 100) {
                return false;
            }
        }
         for (int i = 0; i < bossRockList.size(); i++) {
            x = (int)bossRockList.get(i).xPosition - 600;
            y = (int)bossRockList.get(i).yPosition - 325;
            h = (int)Math.sqrt(x* x + y* y);
            if (h < 100) {
                return false;
            }
         }
        return true;
    }
    
    public void fireMiniGun() {
        if (ship.counter > 17 && ship.active) {
                bulletLeft.add(new Minigun(ship.drawgunLeft.xpoints [1], ship.drawgunLeft.ypoints [1], ship.angle));
                bulletRight.add(new Minigun(ship.drawgunRight.xpoints [1], ship.drawgunRight.ypoints [1], ship.angle));
                ship.counter = 0;
        }
    }
    public void fireEnergyBlaster(){
          if (ship.counter > 175 && ship.active) {
                energyBlasterList.add(new EnergyBlaster(ship.xPosition, ship.yPosition, ship.angle));
                ship.counter = 0;
        }
    }
    public void fireGrenadeLauncher(){
          if (ship.counter > 70 && ship.active) {
                grenadeList.add(new GrenadeLauncher(ship.xPosition, ship.yPosition, ship.angle));
                ship.counter = 0;
        }
    }
    public void checkDestruction() {
        checkRockDestruction();
        checkTrackerRockDestruction();
        checkBigRockDestruction();
        checkBossRockDestruction();           
    }
    public void checkRockDestruction(){
        for (int i = 0; i < rockList.size(); i++) {
            if (rockList.get(i).active == false) {
                if (rockList.get(i).rockSize > 1) {
                    rockList.add (new Rock(rockList.get(i).xPosition, rockList.get(i).yPosition, rockList.get(i).rockSize - 1));
                    rockList.add (new Rock(rockList.get(i).xPosition, rockList.get(i).yPosition, rockList.get(i).rockSize - 1));
                }
                rockList.remove(i);
            }
        }
    }
    public void checkTrackerRockDestruction(){
        for (int i = 0; i < trackerRockList.size(); i++) {
            if (trackerRockList.get(i).active == false) {
                if (trackerRockList.get(i).rockSize > 1) {
                    trackerRockList.add (new TrackerRock((trackerRockList.get(i).xPosition + 20),
                            trackerRockList.get(i).yPosition, trackerRockList.get(i).rockSize - 1, (double)4));
                    trackerRockList.add (new TrackerRock(trackerRockList.get(i).xPosition - 20,
                            trackerRockList.get(i).yPosition, trackerRockList.get(i).rockSize - 1, (double)6));
                }
                trackerRockList.remove(i);
            }
        }
    }
    public void checkBigRockDestruction(){
        for (int i = 0; i < bigRockList.size(); i++) {
            if (bigRockList.get(i).active == false) {
                if (bigRockList.get(i).rockSize == 5) {
                    bigRockList.add (new Rock(bigRockList.get(i).xPosition, bigRockList.get(i).yPosition, bigRockList.get(i).rockSize - 2));
                    bigRockList.add (new Rock(bigRockList.get(i).xPosition, bigRockList.get(i).yPosition, bigRockList.get(i).rockSize - 2));
                    bigRockList.add (new Rock(bigRockList.get(i).xPosition, bigRockList.get(i).yPosition, bigRockList.get(i).rockSize - 2));
                    bigRockList.add (new Rock(bigRockList.get(i).xPosition, bigRockList.get(i).yPosition, bigRockList.get(i).rockSize - 2));
                }
                else if ( bigRockList.get(i).rockSize == 3){
                    bigRockList.add (new Rock(bigRockList.get(i).xPosition, bigRockList.get(i).yPosition, bigRockList.get(i).rockSize - 2));
                    bigRockList.add (new Rock(bigRockList.get(i).xPosition, bigRockList.get(i).yPosition, bigRockList.get(i).rockSize - 2));
                    bigRockList.add (new Rock(bigRockList.get(i).xPosition, bigRockList.get(i).yPosition, bigRockList.get(i).rockSize - 2));
                    bigRockList.add (new Rock(bigRockList.get(i).xPosition, bigRockList.get(i).yPosition, bigRockList.get(i).rockSize - 2));
                    bigRockList.add (new Rock(bigRockList.get(i).xPosition, bigRockList.get(i).yPosition, bigRockList.get(i).rockSize - 2));
                    bigRockList.add (new Rock(bigRockList.get(i).xPosition, bigRockList.get(i).yPosition, bigRockList.get(i).rockSize - 2));
                    bigRockList.add (new Rock(bigRockList.get(i).xPosition, bigRockList.get(i).yPosition, bigRockList.get(i).rockSize - 2));
                    bigRockList.add (new Rock(bigRockList.get(i).xPosition, bigRockList.get(i).yPosition, bigRockList.get(i).rockSize - 2));
                }
                bigRockList.remove(i);
            }
        }
    }
    public void checkGernadeExplosion(){
        for (int i = 0 ; i < grenadeList.size(); i ++){
            if(grenadeList.get(i).counter > 25){
                grenadeBullets.add(new Minigun(grenadeList.get(i).drawShape.xpoints [0],
                grenadeList.get(i).drawShape.ypoints[0], grenadeList.get(i).angle));
                grenadeBullets.add(new Minigun(grenadeList.get(i).drawShape.xpoints [1],
                grenadeList.get(i).drawShape.ypoints[1], grenadeList.get(i).angle - (Math.PI/4)));
                grenadeBullets.add(new Minigun(grenadeList.get(i).drawShape.xpoints [2],
                grenadeList.get(i).drawShape.ypoints[2], grenadeList.get(i).angle- (Math.PI/2)));
                grenadeBullets.add(new Minigun(grenadeList.get(i).drawShape.xpoints [3],
                grenadeList.get(i).drawShape.ypoints[3], grenadeList.get(i).angle - (3*Math.PI/4)));
                grenadeBullets.add(new Minigun(grenadeList.get(i).drawShape.xpoints [4],
                grenadeList.get(i).drawShape.ypoints[4], grenadeList.get(i).angle - (4*Math.PI/6)));
                grenadeBullets.add(new Minigun(grenadeList.get(i).drawShape.xpoints [5],
                grenadeList.get(i).drawShape.ypoints[5], grenadeList.get(i).angle - Math.PI));
                grenadeBullets.add(new Minigun(grenadeList.get(i).drawShape.xpoints [6],
                grenadeList.get(i).drawShape.ypoints[6], grenadeList.get(i).angle + (4*Math.PI/6)));
                grenadeBullets.add(new Minigun(grenadeList.get(i).drawShape.xpoints [7],
                grenadeList.get(i).drawShape.ypoints[7], grenadeList.get(i).angle + (3*Math.PI/4)));
                grenadeBullets.add(new Minigun(grenadeList.get(i).drawShape.xpoints [8],
                grenadeList.get(i).drawShape.ypoints[8], grenadeList.get(i).angle + (Math.PI/2)));
                grenadeBullets.add(new Minigun(grenadeList.get(i).drawShape.xpoints [9],
                grenadeList.get(i).drawShape.ypoints[9], grenadeList.get(i).angle + (Math.PI/4)));
                grenadeList.remove(i);
            }
        }
    }
    public void checkBossRockDestruction(){
         for (int i = 0; i <bossRockList.size(); i++) {
            if (bossRockList.get(i).active == false) {
                bossRockList.remove(i);
            }
        }
    }
    
    public void checkBossHP(){
        for (int i = 0; i < bossRockList.size(); i++){
            if (bossRockList.get(i).HP <= 0){
                bigRockList.add (new Rock(bossRockList.get(i).xPosition, bossRockList.get(i).yPosition, 5));
                bigRockList.add (new Rock(bossRockList.get(i).xPosition, bossRockList.get(i).yPosition, 5));
                bossRockList.get(i).active = false;
            }
        }
    }
    public void bossMinions(){
        for (int i = 0; i < bossRockList.size(); i++){
            if (bossRockList.get(i).counter > 100 && bossRockList.get(i).active){
                  bigRockList.add (new Rock(bossRockList.get(i).xPosition, bossRockList.get(i).yPosition, 1));
                  bigRockList.add (new Rock(bossRockList.get(i).xPosition, bossRockList.get(i).yPosition, 1));
                  bossRockList.get(i).counter = 0;
            }

        }
    }
    public void reset() {
        setLevel();
        random = new Random();
        // set both eplosion colors to false
        shipExplode = false;
        rockExplode = false;
   
        // restart the timer
        timer.restart();
        
        // initalizing sacecraft
        ship = new Spacecraft();
        // initalizing all weapons
        bulletLeft = new ArrayList();
        bulletRight = new ArrayList();
        energyBlasterList = new ArrayList();
        grenadeList = new ArrayList();
        grenadeBullets = new ArrayList();
        // initalizing all types tyos of rocks
        debrisList = new ArrayList();
        rockList = new ArrayList();
        trackerRockList = new ArrayList();
        bigRockList = new ArrayList();
        bossRockList = new ArrayList();
        
        // adding all the types of rocks based on the current level
        for (int i = 0; i <  Rocks ; i++) {
            rockList.add(new Rock(3));
        }      
        for (int i = 0; i < TrackerRocks; i++) {
            trackerRockList.add(new TrackerRock(2,  4));
        }
        bigRockList = new ArrayList();
        for (int i = 0; i <  BigRocks ; i++ ){
            bigRockList.add(new Rock(5));
        }
        bossRockList = new ArrayList();
        for (int i = 0; i < BossRocks ; i++ ){
            bossRockList.add(new BossRock());
        }
        repaint();
    }
    public void checkWin() {
        // checking to see that there are no rocks left
        if (rockList.isEmpty() && trackerRockList.isEmpty() && bigRockList.isEmpty() && bossRockList.isEmpty()) {
             if (ship.lives < 5) {
                ship.lives++;
            }
            Level++;
            LevelIntermission = true;
            LevelIntermissionCount = 0;
        }
    }
    public void setLevel() {
        if (Level == 0) {
            /* ->How many rocks will spawn -> */
            Rocks = 8;
            /* ->How many tracker rocks will spawn -> */
            TrackerRocks = 0;
            /* ->How many big rocks will spawn -> */
            BigRocks = 0;
            /* ->How many boss rocks will spawn -> */
            BossRocks = 0;
        }
        if (Level == 1) {
   
            /* ->How many rocks will spawn -> */
            Rocks = 8;
            /* ->How many tracker rocks will spawn -> */
            TrackerRocks = 1;
            /* ->How many big rocks will spawn -> */

            BigRocks = 0;
            /* ->How many boss rocks will spawn -> */
            BossRocks = 0;
        }
        if (Level == 2) {
            /* ->How many rocks will spawn -> */
            Rocks = 8;
            /* ->How many tracker rocks will spawn -> */
            TrackerRocks = 0;
            /* ->How many big rocks will spawn -> */
            BigRocks = 1;
            /* ->How many boss rocks will spawn -> */
            BossRocks = 0;
        }
        if (Level == 3) {
           
            /* ->How many rocks will spawn -> */
            Rocks = 10;
            /* ->How many tracker rocks will spawn -> */
            TrackerRocks = 3;
            /* ->How many big rocks will spawn -> */
            BigRocks = 0;
            /* ->How many boss rocks will spawn -> */
            BossRocks = 0;
        }
        if (Level == 4) {
            /* ->How many rocks will spawn -> */
            Rocks = 0;
            /* ->How many tracker rocks will spawn -> */
            TrackerRocks = 1;
            /* ->How many big rocks will spawn -> */
            BigRocks = 0;
            /* ->How many boss rocks will spawn -> */
            BossRocks = 1;
        }
        if (Level == 5) {
           
            /* ->How many rocks will spawn -> */
            Rocks = 8;
            /* ->How many tracker rocks will spawn -> */
            TrackerRocks = 2;
            /* ->How many big rocks will spawn -> */
            BigRocks = 2;
            /* ->How many boss rocks will spawn -> */
            BossRocks = 0;
        }
        if (Level == 6) {
            /* ->How many rocks will spawn -> */
            Rocks = 10;
            /* ->How many tracker rocks will spawn -> */
            TrackerRocks = 1;
            /* ->How many big rocks will spawn -> */
            BigRocks = 3;
            /* ->How many boss rocks will spawn -> */
            BossRocks = 0;
        }
        if (Level == 7) {
            
            /* ->How many rocks will spawn -> */
            Rocks = 8;
            /* ->How many tracker rocks will spawn -> */
            TrackerRocks = 10;
            /* ->How many big rocks will spawn -> */
            BigRocks = 0;
            /* ->How many boss rocks will spawn -> */
            BossRocks = 0;
        }
        if (Level == 8) {
            /* ->How many rocks will spawn -> */
            Rocks = 15;
            /* ->How many tracker rocks will spawn -> */
            TrackerRocks = 5;
            /* ->How many big rocks will spawn -> */
            BigRocks = 1;
            /* ->How many boss rocks will spawn -> */
            BossRocks = 0;
        }
        if (Level == 9) {
           
            /* ->How many rocks will spawn -> */
            Rocks = 0;
            /* ->How many tracker rocks will spawn -> */
            TrackerRocks = 0;
            /* ->How many big rocks will spawn -> */
            BigRocks = 0;
            /* ->How many boss rocks will spawn -> */
            BossRocks = 2;
        }
    }
    public boolean checkGameOver() {
        if (ship.lives <= 0) {
            return true;
        }
        else{
            return false;
        }
    }
}

