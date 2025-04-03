import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class FroggerMain extends JFrame {
    private int frogX, frogY;
    private final int MOVE_STEP = 57;
    private final int MOVE_HORIZONTAL = 40;
    private JLabel frogLabel, bgLabel;
    private JPanel greenRectangle;
    private Timer timer;
    private int timeLeft;
    private final int TOTAL_TIME = 30;
    private final int RECTANGLE_WIDTH = 300;
    private JLabel[] staticFrogs;
    private int lives = 3;
    private ArrayList<Integer> success = new ArrayList<>();
    private boolean gameOverLabelVisible = false;
    private JLabel gameOverLabel = new JLabel();
    private boolean isGameOver = false;
    private boolean crossedSafeLine = false;
    private final int SCREEN_WIDTH = 1165;
    private final int SCREEN_HEIGHT = 800;
    private ArrayList<JLabel> movingCars1 = new ArrayList<>();
    private ArrayList<JLabel> movingCars2 = new ArrayList<>();
    private ArrayList<JLabel> movingCars3 = new ArrayList<>();
    private ArrayList<JLabel> movingCars4 = new ArrayList<>();
    private ArrayList<JLabel> movingCars5 = new ArrayList<>();
    private ArrayList<JLabel> movingLogs1 = new ArrayList<>();
    private ArrayList<JLabel> movingLogs2 = new ArrayList<>();
    private ArrayList<JLabel> movingLogs3 = new ArrayList<>();
    private Timer spawnTimer2;
    private Timer moveCarsTimer2;
    private Timer spawnTimer1;
    private Timer moveCarsTimer1;
    private Timer moveLogsTimer1;
    private Timer spawnLogTimer1;
    private Timer moveLogsTimer2;
    private Timer spawnLogTimer2;
    private Timer moveLogsTimer3;
    private Timer spawnLogTimer3;
    private Timer spawnTimer3;
    private Timer moveCarsTimer3;
    private Timer spawnTimer4;
    private Timer moveCarsTimer4;
    private Timer spawnTimer5;
    private Timer moveCarsTimer5;
    private final int CAR_SPEED1 = 10;
    private final int CAR_SPEED2 = 15;
    private final int CAR_SPEED3 = 10;
    private final int CAR_SPEED4 = 7;
    private final int CAR_SPEED5 = 10;
    private final int LOG_SPEED = 7;
    private boolean notSafe = true;
    private String[] carImages = {
            "Images/18WheelerRight.png",
            "Images/LimoRight.png",
            "Images/NormalCarRight.png",
            "Images/SportsCarRight.png"
    };
    private String[] carImagesLeft = {
            "Images/18WheelerLeft.png",
            "Images/LimoLeft.png",
            "Images/NormalCarLeft.png",
            "Images/SportsCarLeft.png"
    };
    private String[] logImages = {
            "Images/LogImage1.png",
            "Images/LogImage2.png",
            "Images/LogImage3.png"
    };
    public boolean isAlive = false;
    private boolean isInvincible = false;
    private final int INVINCIBILITY_DURATION = 1000;

    public FroggerMain() {
        setTitle("Frogger Test");
        setSize(1165, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        ImageIcon bgIcon = new ImageIcon("Images/Frogger_Map_Updated.png");
        Image bgImage = bgIcon.getImage().getScaledInstance(1165, 800, Image.SCALE_SMOOTH);
        ImageIcon resizedBgIcon = new ImageIcon(bgImage);

        bgLabel = new JLabel(resizedBgIcon);
        bgLabel.setBounds(0, 0, 1165, 800);
        add(bgLabel);

        ImageIcon originalIcon = new ImageIcon("Images/Frog_Icon.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(65, 65, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledImage);

        frogLabel = new JLabel(resizedIcon);
        frogX = 1165 / 2 - 32;
        frogY = 690;
        frogLabel.setBounds(frogX, frogY, 65, 65);
        bgLabel.add(frogLabel);

        staticFrogs = new JLabel[lives];
        for (int i = 0; i < lives; i++) {
            staticFrogs[i] = new JLabel(resizedIcon);
            staticFrogs[i].setBounds(170 + (i * 65), 745, 65, 65);
            bgLabel.add(staticFrogs[i]);
        }

        bgLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int clickedX = e.getX();
                int clickedY = e.getY();
                System.out.println("Clicked at: (" + clickedX + ", " + clickedY + ")");
            }
        });

        greenRectangle = new JPanel();
        greenRectangle.setBackground(Color.GREEN);
        greenRectangle.setBounds(805, 756, RECTANGLE_WIDTH, 30);
        bgLabel.add(greenRectangle);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                if(isGameOver){
                } else{
                    int key = e.getKeyCode();
                    if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
                        frogY = Math.max(frogY - MOVE_STEP, 4);
                    } else if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
                        frogY = Math.min(frogY + MOVE_STEP, 805 - 115);
                    } else if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
                        frogX = Math.max(frogX - MOVE_HORIZONTAL, 0);
                    } else if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
                        frogX = Math.min(frogX + MOVE_HORIZONTAL, 1165 - 65);
                    }
                    frogLabel.setBounds(frogX, frogY, 65, 65);
                    System.out.println("Frog Position: (" + frogX + ", " + frogY + ")");
                    if(frogY<=348){
                        crossedSafeLine = true;
                    }

                    if (!success.contains(1) && frogX >= 110 && frogX <= 190 && frogY >= 0 && frogY <= 10) {
                        crossedSafeLine = false;
                        placeStaticFrog(150, 6);
                        success.add(1);
                        resetFrogPosition();
                        resetTimer();
                        System.out.println("Frog reached destination!");
                        if (success.size() == 4) {
                            timer.stop();
                            gameOver("Win");
                        }
                    }
                    if (!success.contains(2) && frogX >= 390 && frogX <= 470 && frogY >= 0 && frogY <= 10) {
                        crossedSafeLine = false;
                        placeStaticFrog(430, 4);
                        success.add(2);
                        resetFrogPosition();
                        resetTimer();
                        System.out.println("Frog reached destination!");
                        if (success.size() == 4) {
                            timer.stop();
                            gameOver("Win");
                        }
                    }
                    if (!success.contains(3) && frogX >= 670 && frogX <= 725 && frogY >= 0 && frogY <= 10) {
                        crossedSafeLine = false;
                        placeStaticFrog(698, 6);
                        success.add(3);
                        resetFrogPosition();
                        resetTimer();
                        System.out.println("Frog reached destination!");
                        if (success.size() == 4) {
                            timer.stop();
                            gameOver("Win");
                        }
                    }
                    if (!success.contains(4) && frogX >= 910 && frogX <= 990 && frogY >= 0 && frogY <= 10) {
                        crossedSafeLine = false;
                        placeStaticFrog(950, 6);
                        success.add(4);
                        resetFrogPosition();
                        resetTimer();
                        System.out.println("Frog reached destination!");
                        if (success.size() == 4) {
                            System.out.println("Sucess!!!");
                            timer.stop();
                            gameOver("Win");
                        }
                    }

                    if (frogY > 60 && frogY < 300 && notSafe) {
//                        new Thread(() -> {
//                            try {
//                                Thread.sleep(200);
//                            } catch (InterruptedException e1) {
//                                e1.printStackTrace();
//                            }
//                            loseLife("Hit");
//                        }).start();
                        //loseLife("Hit");
                    }
                }

            }
        });
        startCarMovement();
        logMovement();


        setVisible(true);
        startTimer();
    }
    private void logMovement() {
        moveLogsTimer1 = new Timer(50, _ -> moveLogs1());
        moveLogsTimer1.start();
        spawnLogTimer1 = new Timer(3000, _ -> spawnLogs1());
        spawnLogTimer1.start();

        Timer delayTimer2 = new Timer(2000, _ -> {
            moveLogsTimer2 = new Timer(50, _ -> moveLogs2());
            moveLogsTimer2.start();
            spawnLogTimer2 = new Timer(2900, _ -> spawnLogs2());
            spawnLogTimer2.start();
        });
        delayTimer2.setRepeats(false);
        delayTimer2.start();

        Timer delayTimer1 = new Timer(1000, _ -> {
            moveLogsTimer3 = new Timer(50, _ -> moveLogs3());
            moveLogsTimer3.start();
            spawnLogTimer3 = new Timer(3100, _ -> spawnLogs3());
            spawnLogTimer3.start();
        });
        delayTimer1.setRepeats(false);
        delayTimer1.start();
    }

    private void spawnLogs1() {
        Random rand = new Random();
        int randomLogIndex = rand.nextInt(3);
        String logPath = logImages[randomLogIndex];

        System.out.println("Spawning car at index: " + randomLogIndex);
        JLabel logLabel = new JLabel();
        if(randomLogIndex==0){
            ImageIcon logIcon = new ImageIcon(logPath);
            Image logImage = logIcon.getImage().getScaledInstance(250, 125, Image.SCALE_SMOOTH);
            logLabel = new JLabel(new ImageIcon(logImage));
            int laneY = 36;
            logLabel.setBounds(-250, laneY, 300, 115);
        }
        if(randomLogIndex==1){
            ImageIcon logIcon = new ImageIcon(logPath);
            Image logImage = logIcon.getImage().getScaledInstance(200, 100, Image.SCALE_SMOOTH);
            logLabel = new JLabel(new ImageIcon(logImage));
            int laneY = 20;
            logLabel.setBounds(-250, laneY, 300, 155);
        }
        if(randomLogIndex==2){
            ImageIcon logIcon = new ImageIcon(logPath);
            Image logImage = logIcon.getImage().getScaledInstance(250, 100, Image.SCALE_SMOOTH);
            logLabel = new JLabel(new ImageIcon(logImage));
            int laneY = 55;
            logLabel.setBounds(-250, laneY, 250, 70);
        }


        bgLabel.add(logLabel);
        movingLogs1.add(logLabel);

        System.out.println("Total cars: " + movingLogs1.size());

        revalidate();
        repaint();
    }
    private void moveLogs1() {
        //System.out.println(lives);
        for (int i = movingLogs1.size() - 1; i >= 0; i--) {
            JLabel log = movingLogs1.get(i);
            log.setLocation(log.getX() + LOG_SPEED, log.getY());

            Rectangle frogBounds = new Rectangle(frogLabel.getBounds());
            frogBounds.grow(0, -32);

            Rectangle logBounds = log.getBounds();
            logBounds.grow(0,-35);
            //System.out.println("Frog Bounds: " + frogBounds);
            //System.out.println("Car Bounds: " + carBounds);


            if(frogBounds.intersects(log.getBounds())){
                System.out.println("Touch!!!");
            }
//            int topR = 462-20;
//            int bottomR = 462+30;
//            //System.out.println("Frog's Y " + frogLabel.getY());
//            if (!isInvincible && frogBounds.intersects(logBounds.getBounds()) && frogLabel.getY()>topR && frogLabel.getY() <bottomR) {
//                System.out.println("Collision detected precisely!");
//                loseLife("Hit");
//            }

            if (log.getX() > SCREEN_WIDTH) {
                remove(log);
                movingLogs1.remove(i);
            }
        }
        repaint();
    }

    private void spawnLogs2() {
        Random rand = new Random();
        int randomLogIndex = rand.nextInt(3);
        String logPath = logImages[randomLogIndex];

        System.out.println("Spawning car at index: " + randomLogIndex);
        JLabel logLabel = new JLabel();
        if(randomLogIndex==0){
            ImageIcon logIcon = new ImageIcon(logPath);
            Image logImage = logIcon.getImage().getScaledInstance(250, 125, Image.SCALE_SMOOTH);
            logLabel = new JLabel(new ImageIcon(logImage));
            int laneY = 36+167;
            logLabel.setBounds(-250, laneY, 300, 115);
        }
        if(randomLogIndex==1){
            ImageIcon logIcon = new ImageIcon(logPath);
            Image logImage = logIcon.getImage().getScaledInstance(200, 100, Image.SCALE_SMOOTH);
            logLabel = new JLabel(new ImageIcon(logImage));
            int laneY = 20+167;
            logLabel.setBounds(-250, laneY, 300, 155);
        }
        if(randomLogIndex==2){
            ImageIcon logIcon = new ImageIcon(logPath);
            Image logImage = logIcon.getImage().getScaledInstance(250, 100, Image.SCALE_SMOOTH);
            logLabel = new JLabel(new ImageIcon(logImage));
            int laneY = 55+169;
            logLabel.setBounds(-250, laneY, 250, 70);
        }


        bgLabel.add(logLabel);
        movingLogs2.add(logLabel);

        System.out.println("Total cars: " + movingLogs2.size());

        revalidate();
        repaint();
    }
    private void moveLogs2() {
        //System.out.println(lives);
        for (int i = movingLogs2.size() - 1; i >= 0; i--) {
            JLabel log = movingLogs2.get(i);
            log.setLocation(log.getX() + LOG_SPEED, log.getY());

            Rectangle frogBounds = new Rectangle(frogLabel.getBounds());
            frogBounds.grow(0, -32);

            Rectangle logBounds = log.getBounds();
            logBounds.grow(0,-35);
            //System.out.println("Frog Bounds: " + frogBounds);
            //System.out.println("Car Bounds: " + carBounds);


            if(frogBounds.intersects(log.getBounds())){
                System.out.println("Touch!!!");
            }
//            int topR = 462-20;
//            int bottomR = 462+30;
//            //System.out.println("Frog's Y " + frogLabel.getY());
//            if (!isInvincible && frogBounds.intersects(logBounds.getBounds()) && frogLabel.getY()>topR && frogLabel.getY() <bottomR) {
//                System.out.println("Collision detected precisely!");
//                loseLife("Hit");
//            }

            if (log.getX() > SCREEN_WIDTH) {
                remove(log);
                movingLogs2.remove(i);
            }
        }
        repaint();
    }

    private void spawnLogs3() {
        Random rand = new Random();
        int randomLogIndex = rand.nextInt(3);
        String logPath = logImages[randomLogIndex];

        System.out.println("Spawning car at index: " + randomLogIndex);
        JLabel logLabel = new JLabel();
        if(randomLogIndex==0){
            ImageIcon logIcon = new ImageIcon(logPath);
            Image logImage = logIcon.getImage().getScaledInstance(250, 125, Image.SCALE_SMOOTH);
            logLabel = new JLabel(new ImageIcon(logImage));
            int laneY = 36+110;
            logLabel.setBounds(-250, laneY, 300, 115);
        }
        if(randomLogIndex==1){
            ImageIcon logIcon = new ImageIcon(logPath);
            Image logImage = logIcon.getImage().getScaledInstance(200, 100, Image.SCALE_SMOOTH);
            logLabel = new JLabel(new ImageIcon(logImage));
            int laneY = 20+110;
            logLabel.setBounds(-250, laneY, 300, 155);
        }
        if(randomLogIndex==2){
            ImageIcon logIcon = new ImageIcon(logPath);
            Image logImage = logIcon.getImage().getScaledInstance(250, 100, Image.SCALE_SMOOTH);
            logLabel = new JLabel(new ImageIcon(logImage));
            int laneY = 55+110;
            logLabel.setBounds(-250, laneY, 250, 70);
        }


        bgLabel.add(logLabel);
        movingLogs3.add(logLabel);

        System.out.println("Total cars: " + movingLogs3.size());

        revalidate();
        repaint();
    }
    private void moveLogs3() {
        //System.out.println(lives);
        for (int i = movingLogs3.size() - 1; i >= 0; i--) {
            JLabel log = movingLogs3.get(i);
            log.setLocation(log.getX() + LOG_SPEED, log.getY());

            Rectangle frogBounds = new Rectangle(frogLabel.getBounds());
            frogBounds.grow(0, -32);

            Rectangle logBounds = log.getBounds();
            logBounds.grow(0,-35);
            //System.out.println("Frog Bounds: " + frogBounds);
            //System.out.println("Car Bounds: " + carBounds);


            if(frogBounds.intersects(log.getBounds())){
                System.out.println("Touch!!!");
            }
//            int topR = 462-20;
//            int bottomR = 462+30;
//            //System.out.println("Frog's Y " + frogLabel.getY());
//            if (!isInvincible && frogBounds.intersects(logBounds.getBounds()) && frogLabel.getY()>topR && frogLabel.getY() <bottomR) {
//                System.out.println("Collision detected precisely!");
//                loseLife("Hit");
//            }

            if (log.getX() > SCREEN_WIDTH) {
                remove(log);
                movingLogs3.remove(i);
            }
        }
        repaint();
    }

    private void startCarMovement() {
        moveCarsTimer1 = new Timer(50, e -> moveCars1());
        moveCarsTimer1.start();
        spawnTimer1 = new Timer(3100, e -> spawnCar1());
        spawnTimer1.start();
        moveCarsTimer2 = new Timer(50, e -> moveCars2());
        moveCarsTimer2.start();
        spawnTimer2 = new Timer(3200, e -> spawnCar2());
        spawnTimer2.start();
        moveCarsTimer3 = new Timer(50, e -> moveCars3());
        moveCarsTimer3.start();
        spawnTimer3 = new Timer(3000, e -> spawnCar3());
        spawnTimer3.start();
        moveCarsTimer4 = new Timer(50, e -> moveCars4());
        moveCarsTimer4.start();
        spawnTimer4 = new Timer(3100, e -> spawnCar4());
        spawnTimer4.start();
        moveCarsTimer5 = new Timer(50, e -> moveCars5());
        moveCarsTimer5.start();
        spawnTimer5 = new Timer(2950, e -> spawnCar5());
        spawnTimer5.start();
    }

    private void spawnCar1() {
        Random rand = new Random();
        int randomCarIndex = rand.nextInt(4);
        String carPath = carImagesLeft[randomCarIndex];

        System.out.println("Spawning car at index: " + randomCarIndex);
        JLabel carLabel = new JLabel();
        if(randomCarIndex==0){
            ImageIcon carIcon = new ImageIcon(carPath);
            Image carImage = carIcon.getImage().getScaledInstance(235, 125, Image.SCALE_SMOOTH);
            carLabel = new JLabel(new ImageIcon(carImage));
            int laneY = 367;
            carLabel.setBounds(1300, laneY, 235, 125);
        }
        if(randomCarIndex==1){
            ImageIcon carIcon = new ImageIcon(carPath);
            Image carImage = carIcon.getImage().getScaledInstance(160, 150, Image.SCALE_SMOOTH);
            carLabel = new JLabel(new ImageIcon(carImage));
            int laneY = 352;
            carLabel.setBounds(1300, laneY, 165, 155);
        }
        if(randomCarIndex==2){
            ImageIcon carIcon = new ImageIcon(carPath);
            Image carImage = carIcon.getImage().getScaledInstance(110, 70, Image.SCALE_SMOOTH);
            carLabel = new JLabel(new ImageIcon(carImage));
            int laneY = 392;
            carLabel.setBounds(1300, laneY, 110, 70);
        }
        if(randomCarIndex==3){
            ImageIcon carIcon = new ImageIcon(carPath);
            Image carImage = carIcon.getImage().getScaledInstance(100, 60, Image.SCALE_SMOOTH);
            carLabel = new JLabel(new ImageIcon(carImage));
            int laneY = 393;
            carLabel.setBounds(1300, laneY, 100, 60);
        }


        bgLabel.add(carLabel);
        movingCars1.add(carLabel);

        System.out.println("Total cars: " + movingCars1.size());

        revalidate();
        repaint();
    }


    private void moveCars1() {
        for (int i = movingCars1.size() - 1; i >= 0; i--) {
            JLabel car = movingCars1.get(i);

            car.setLocation(car.getX() - CAR_SPEED1, car.getY());

            Rectangle frogBounds = new Rectangle(frogLabel.getBounds());
            frogBounds.grow(0, -32);

            Rectangle carBounds = car.getBounds();
            carBounds.grow(0,-35);

            if (frogBounds.intersects(car.getBounds())) {
                System.out.println("Hitt");
            }

            int topR = 405 - 20;
            int bottomR = 405 + 30;

            if (!isInvincible && frogBounds.intersects(car.getBounds()) && frogLabel.getY() > topR && frogLabel.getY() < bottomR) {
                System.out.println("Collision detected precisely!");
                loseLife("Hit");
            }

            if (car.getX() + car.getWidth() < 0) {
                remove(car);
                movingCars1.remove(i);
            }
        }
        repaint();
    }


    private void spawnCar2() {
        Random rand = new Random();
        int randomCarIndex = rand.nextInt(4);
        String carPath = carImages[randomCarIndex];

        System.out.println("Spawning car at index: " + randomCarIndex);
        JLabel carLabel = new JLabel();
        if(randomCarIndex==0){
            ImageIcon carIcon = new ImageIcon(carPath);
            Image carImage = carIcon.getImage().getScaledInstance(235, 125, Image.SCALE_SMOOTH);
            carLabel = new JLabel(new ImageIcon(carImage));
            int laneY = 425;
            carLabel.setBounds(-200, laneY, 235, 125);
        }
        if(randomCarIndex==1){
            ImageIcon carIcon = new ImageIcon(carPath);
            Image carImage = carIcon.getImage().getScaledInstance(160, 150, Image.SCALE_SMOOTH);
            carLabel = new JLabel(new ImageIcon(carImage));
            int laneY = 410;
            carLabel.setBounds(-200, laneY, 165, 155);
        }
        if(randomCarIndex==2){
            ImageIcon carIcon = new ImageIcon(carPath);
            Image carImage = carIcon.getImage().getScaledInstance(110, 70, Image.SCALE_SMOOTH);
            carLabel = new JLabel(new ImageIcon(carImage));
            int laneY = 455;
            carLabel.setBounds(-200, laneY, 110, 70);
        }
        if(randomCarIndex==3){
            ImageIcon carIcon = new ImageIcon(carPath);
            Image carImage = carIcon.getImage().getScaledInstance(100, 60, Image.SCALE_SMOOTH);
            carLabel = new JLabel(new ImageIcon(carImage));
            int laneY = 455;
            carLabel.setBounds(-200, laneY, 100, 60);
        }


        bgLabel.add(carLabel);
        movingCars2.add(carLabel);

        System.out.println("Total cars: " + movingCars2.size());

        revalidate();
        repaint();
    }


    private void moveCars2() {
        //System.out.println(lives);
        for (int i = movingCars2.size() - 1; i >= 0; i--) {
            JLabel car = movingCars2.get(i);
            car.setLocation(car.getX() + CAR_SPEED2, car.getY());

            Rectangle frogBounds = new Rectangle(frogLabel.getBounds());
            frogBounds.grow(0, -32);

            Rectangle carBounds = car.getBounds();
            carBounds.grow(0,-35);
            //System.out.println("Frog Bounds: " + frogBounds);
            //System.out.println("Car Bounds: " + carBounds);


            if(frogBounds.intersects(car.getBounds())){
                System.out.println("Hitt");
            }
            int topR = 462-20;
            int bottomR = 462+30;
            //System.out.println("Frog's Y " + frogLabel.getY());
            if (!isInvincible && frogBounds.intersects(car.getBounds()) && frogLabel.getY()>topR && frogLabel.getY() <bottomR) {
                System.out.println("Collision detected precisely!");
                loseLife("Hit");
            }

            if (car.getX() > SCREEN_WIDTH) {
                remove(car);
                movingCars2.remove(i);
            }
        }
        repaint();
    }

    private void spawnCar3() {
        Random rand = new Random();
        int randomCarIndex = rand.nextInt(4);
        String carPath = carImagesLeft[randomCarIndex];

        System.out.println("Spawning car at index: " + randomCarIndex);
        JLabel carLabel = new JLabel();
        if(randomCarIndex==0){
            ImageIcon carIcon = new ImageIcon(carPath);
            Image carImage = carIcon.getImage().getScaledInstance(235, 125, Image.SCALE_SMOOTH);
            carLabel = new JLabel(new ImageIcon(carImage));
            int laneY = 480;
            carLabel.setBounds(1300, laneY, 235, 125);
        }
        if(randomCarIndex==1){
            ImageIcon carIcon = new ImageIcon(carPath);
            Image carImage = carIcon.getImage().getScaledInstance(160, 150, Image.SCALE_SMOOTH);
            carLabel = new JLabel(new ImageIcon(carImage));
            int laneY = 470;
            carLabel.setBounds(1300, laneY, 165, 155);
        }
        if(randomCarIndex==2){
            ImageIcon carIcon = new ImageIcon(carPath);
            Image carImage = carIcon.getImage().getScaledInstance(110, 70, Image.SCALE_SMOOTH);
            carLabel = new JLabel(new ImageIcon(carImage));
            int laneY = 510;
            carLabel.setBounds(1300, laneY, 110, 70);
        }
        if(randomCarIndex==3){
            ImageIcon carIcon = new ImageIcon(carPath);
            Image carImage = carIcon.getImage().getScaledInstance(100, 60, Image.SCALE_SMOOTH);
            carLabel = new JLabel(new ImageIcon(carImage));
            int laneY = 510;
            carLabel.setBounds(1300, laneY, 100, 60);
        }


        bgLabel.add(carLabel);
        movingCars3.add(carLabel);

        System.out.println("Total cars: " + movingCars3.size());

        revalidate();
        repaint();
    }


    private void moveCars3() {
        for (int i = movingCars3.size() - 1; i >= 0; i--) {
            JLabel car = movingCars3.get(i);

            car.setLocation(car.getX() - CAR_SPEED3, car.getY());

            Rectangle frogBounds = new Rectangle(frogLabel.getBounds());
            frogBounds.grow(0, -32);

            Rectangle carBounds = car.getBounds();
            carBounds.grow(0,-35);

            if (frogBounds.intersects(car.getBounds())) {
                System.out.println("Hitt");
            }

            int topR = 519 - 20;
            int bottomR = 519 + 30;

            if (!isInvincible && frogBounds.intersects(car.getBounds()) && frogLabel.getY() > topR && frogLabel.getY() < bottomR) {
                System.out.println("Collision detected precisely!");
                loseLife("Hit");
            }

            if (car.getX() + car.getWidth() < 0) {
                remove(car);
                movingCars3.remove(i);
            }
        }
        repaint();
    }

    private void spawnCar4() {
        Random rand = new Random();
        int randomCarIndex = rand.nextInt(4);
        String carPath = carImages[randomCarIndex];

        System.out.println("Spawning car at index: " + randomCarIndex);
        JLabel carLabel = new JLabel();
        if(randomCarIndex==0){
            ImageIcon carIcon = new ImageIcon(carPath);
            Image carImage = carIcon.getImage().getScaledInstance(235, 125, Image.SCALE_SMOOTH);
            carLabel = new JLabel(new ImageIcon(carImage));
            int laneY = 540;
            carLabel.setBounds(-200, laneY, 235, 125);
        }
        if(randomCarIndex==1){
            ImageIcon carIcon = new ImageIcon(carPath);
            Image carImage = carIcon.getImage().getScaledInstance(160, 150, Image.SCALE_SMOOTH);
            carLabel = new JLabel(new ImageIcon(carImage));
            int laneY = 528;
            carLabel.setBounds(-200, laneY, 165, 155);
        }
        if(randomCarIndex==2){
            ImageIcon carIcon = new ImageIcon(carPath);
            Image carImage = carIcon.getImage().getScaledInstance(110, 70, Image.SCALE_SMOOTH);
            carLabel = new JLabel(new ImageIcon(carImage));
            int laneY = 570;
            carLabel.setBounds(-200, laneY, 110, 70);
        }
        if(randomCarIndex==3){
            ImageIcon carIcon = new ImageIcon(carPath);
            Image carImage = carIcon.getImage().getScaledInstance(100, 60, Image.SCALE_SMOOTH);
            carLabel = new JLabel(new ImageIcon(carImage));
            int laneY = 570;
            carLabel.setBounds(-200, laneY, 100, 60);
        }


        bgLabel.add(carLabel);
        movingCars4.add(carLabel);

        System.out.println("Total cars: " + movingCars4.size());

        revalidate();
        repaint();
    }


    private void moveCars4() {
        //System.out.println(lives);
        for (int i = movingCars4.size() - 1; i >= 0; i--) {
            JLabel car = movingCars4.get(i);
            car.setLocation(car.getX() + CAR_SPEED4, car.getY());

            Rectangle frogBounds = new Rectangle(frogLabel.getBounds());
            frogBounds.grow(0, -32);

            Rectangle carBounds = car.getBounds();
            carBounds.grow(0,-35);
            //System.out.println("Frog Bounds: " + frogBounds);
            //System.out.println("Car Bounds: " + carBounds);


            if(frogBounds.intersects(car.getBounds())){
                System.out.println("Hitt");
            }
            int topR = 576-20;
            int bottomR = 576+30;
            //System.out.println("Frog's Y " + frogLabel.getY());
            if (!isInvincible && frogBounds.intersects(car.getBounds()) && frogLabel.getY()>topR && frogLabel.getY() <bottomR) {
                System.out.println("Collision detected precisely!");
                loseLife("Hit");
            }

            if (car.getX() > SCREEN_WIDTH) {
                remove(car);
                movingCars4.remove(i);
            }
        }
        repaint();
    }

    private void spawnCar5() {
        Random rand = new Random();
        int randomCarIndex = rand.nextInt(4);
        String carPath = carImagesLeft[randomCarIndex];

        System.out.println("Spawning car at index: " + randomCarIndex);
        JLabel carLabel = new JLabel();
        if(randomCarIndex==0){
            ImageIcon carIcon = new ImageIcon(carPath);
            Image carImage = carIcon.getImage().getScaledInstance(235, 125, Image.SCALE_SMOOTH);
            carLabel = new JLabel(new ImageIcon(carImage));
            int laneY = 598;
            carLabel.setBounds(1300, laneY, 235, 125);
        }
        if(randomCarIndex==1){
            ImageIcon carIcon = new ImageIcon(carPath);
            Image carImage = carIcon.getImage().getScaledInstance(160, 150, Image.SCALE_SMOOTH);
            carLabel = new JLabel(new ImageIcon(carImage));
            int laneY = 580;
            carLabel.setBounds(1300, laneY, 165, 155);
        }
        if(randomCarIndex==2){
            ImageIcon carIcon = new ImageIcon(carPath);
            Image carImage = carIcon.getImage().getScaledInstance(110, 70, Image.SCALE_SMOOTH);
            carLabel = new JLabel(new ImageIcon(carImage));
            int laneY = 625;
            carLabel.setBounds(1300, laneY, 110, 70);
        }
        if(randomCarIndex==3){
            ImageIcon carIcon = new ImageIcon(carPath);
            Image carImage = carIcon.getImage().getScaledInstance(100, 60, Image.SCALE_SMOOTH);
            carLabel = new JLabel(new ImageIcon(carImage));
            int laneY = 630;
            carLabel.setBounds(1300, laneY, 100, 60);
        }


        bgLabel.add(carLabel);
        movingCars5.add(carLabel);

        System.out.println("Total cars: " + movingCars5.size());

        revalidate();
        repaint();
    }


    private void moveCars5() {
        for (int i = movingCars5.size() - 1; i >= 0; i--) {
            JLabel car = movingCars5.get(i);

            car.setLocation(car.getX() - CAR_SPEED5, car.getY());

            Rectangle frogBounds = new Rectangle(frogLabel.getBounds());
            frogBounds.grow(0, -32);

            Rectangle carBounds = car.getBounds();
            carBounds.grow(0,-35);

            if (frogBounds.intersects(car.getBounds())) {
                System.out.println("Hitt");
            }

            int topR = 633 - 20;
            int bottomR = 633 + 30;

            if (!isInvincible && frogBounds.intersects(car.getBounds()) && frogLabel.getY() > topR && frogLabel.getY() < bottomR) {
                System.out.println("Collision detected precisely!");
                loseLife("Hit");
            }

            if (car.getX() + car.getWidth() < 0) {
                remove(car);
                movingCars5.remove(i);
            }
        }
        repaint();
    }


    private void startTimer() {
        timeLeft = TOTAL_TIME;
        if (timer != null) {
            timer.stop();
        }
        timer = new Timer(1000, e -> {
            timeLeft--;
            int newWidth = (int) ((timeLeft / (double) TOTAL_TIME) * RECTANGLE_WIDTH);
            greenRectangle.setBounds(805, 756, newWidth, 30);

            if (timeLeft <= 10) {
                greenRectangle.setBackground(Color.RED);
            } else if (timeLeft <= 30) {
                greenRectangle.setBackground(Color.YELLOW);
            } else {
                greenRectangle.setBackground(Color.GREEN);
            }

            if (timeLeft <= 0) {
                loseLife("Timer");
            }
        });
        timer.start();
    }

    private void placeStaticFrog(int x, int y) {
        ImageIcon originalIcon = new ImageIcon("Images/Frog_Icon.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(65, 65, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledImage);
        JLabel staticFrog = new JLabel(resizedIcon);
        staticFrog.setBounds(x, y, 65, 65);
        bgLabel.add(staticFrog);
        bgLabel.repaint();
    }

    private void resetFrogPosition() {
        frogX = 1165 / 2 - 32;
        frogY = 690;
        frogLabel.setBounds(frogX, frogY, 65, 65);
        isAlive = true;
    }
    private void resetFrogPositionUpper(){
        frogLabel.setBounds(550,348,65,65);
        isAlive = true;
    }

    private void resetTimer() {
        startTimer();
    }

    private void loseLife(String variableTemp) {
        System.out.println("I have been summoned");
        isAlive = false;
        if (lives > 0) {
            bgLabel.remove(staticFrogs[lives - 1]);
            bgLabel.repaint();
            lives--;
        }
        if (lives == 0) {
            resetFrogPosition();
            gameOver("Lose");
        } else {
            if(variableTemp.equals("Timer")){
                resetFrogPosition();
            }
            else if(variableTemp.equals("Hit")){
                if(crossedSafeLine){
                    resetFrogPositionUpper();
                }else{
                    resetFrogPosition();
                }
            }
            isInvincible = true;
            new Timer(INVINCIBILITY_DURATION, e -> isInvincible = false).start();
            resetTimer();
        }
    }

    private void gameOver(String status) {
        timer.stop();
        moveCarsTimer1.stop();
        spawnTimer1.stop();
        moveCarsTimer2.stop();
        spawnTimer2.stop();
        moveCarsTimer3.stop();
        spawnTimer3.stop();
        moveCarsTimer4.stop();
        spawnTimer4.stop();
        moveCarsTimer5.stop();
        spawnTimer5.stop();

        isGameOver = true;

        Font font = new Font("Georgia", Font.BOLD, 30);

        if(status.equals("Win")){
            gameOverLabel = new JLabel("<html><h1>You Win!<br>Press 'N' for New Game or 'X' to Exit.</h1></html>");
        } else if(status.equals("Lose")){
            gameOverLabel = new JLabel("<html><h1>You Lose!<br>Press 'N' for New Game or 'X' to Exit.</h1></html>");
        }
        gameOverLabel.setBounds(350, 300, 600, 150);
        gameOverLabel.setFont(font);
        gameOverLabel.setForeground(Color.RED);
        gameOverLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gameOverLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        gameOverLabel.setOpaque(true);
        gameOverLabel.setBackground(Color.WHITE);

        bgLabel.add(gameOverLabel);
        gameOverLabelVisible = true;
        bgLabel.repaint();

        if(gameOverLabelVisible) {
            this.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    int key = e.getKeyCode();

                    if (key == KeyEvent.VK_N) {
                        restartGame();
                        bgLabel.remove(gameOverLabel);
                        bgLabel.repaint();
                    }
                    else if (key == KeyEvent.VK_X) {
                        System.exit(0);
                    }
                }
            });
        }


        this.setFocusable(true);
        this.requestFocusInWindow();
    }

    private void restartGame() {
        isInvincible = false;
        isGameOver = false;
        crossedSafeLine = false;
        success.clear();
        lives = 3;
        frogX = 1165 / 2 - 32;
        frogY = 690;
        frogLabel.setBounds(frogX, frogY, 65, 65);
        movingCars1.clear();
        movingCars2.clear();
        movingCars3.clear();
        movingCars4.clear();
        movingCars5.clear();
        movingLogs1.clear();
        movingLogs2.clear();
        movingLogs3.clear();
        moveCarsTimer1.stop();
        spawnTimer1.stop();
        moveCarsTimer2.stop();
        spawnTimer2.stop();
        moveCarsTimer3.stop();
        spawnTimer3.stop();
        moveCarsTimer4.stop();
        spawnTimer4.stop();
        moveCarsTimer5.stop();
        spawnTimer5.stop();
        moveLogsTimer1.stop();
        spawnLogTimer1.stop();
        moveLogsTimer2.stop();
        spawnLogTimer2.stop();
        moveLogsTimer3.stop();
        spawnLogTimer3.stop();
        startCarMovement();
        logMovement();

        bgLabel.removeAll();
        bgLabel.add(frogLabel);
        for (int i = 0; i < lives; i++) {
            bgLabel.add(staticFrogs[i]);
        }

        greenRectangle.setBounds(805, 756, RECTANGLE_WIDTH, 30);
        bgLabel.add(greenRectangle);

        bgLabel.repaint();

        resetTimer();
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(FroggerMain::new);
    }
}