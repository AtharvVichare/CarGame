import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.Random;

public class CarGame extends JPanel implements ActionListener, KeyListener {

    private Timer timer;
    private LinkedList<Car> cars;
    private Car playerCar;
    private BufferedImage roadImage, playerCarImage, enemyCar1, enemyCar2, enemyCar3;
    private LinkedList<Road> roadSegments;
    private boolean isGameOver;
    private int score;

    public CarGame() {

        isGameOver = false;
        score = 0;

        try {
            playerCarImage = ImageIO.read(new File("C:\\Users\\ASUS\\IdeaProjects\\CarGame\\src\\player_car.png"));
            enemyCar1 = ImageIO.read(new File("C:\\Users\\ASUS\\IdeaProjects\\CarGame\\src\\enemy_car1.png"));
            enemyCar2 = ImageIO.read(new File("C:\\Users\\ASUS\\IdeaProjects\\CarGame\\src\\enemy_car2.png"));
            enemyCar3 = ImageIO.read(new File("C:\\Users\\ASUS\\IdeaProjects\\CarGame\\src\\enemy_car3.png"));
            roadImage = ImageIO.read(new File("C:\\Users\\ASUS\\IdeaProjects\\CarGame\\src\\road.png"));

            roadImage = resizeImage(roadImage, 800, 400);
            playerCarImage = resizeImage(playerCarImage, 80, 160);
            enemyCar1 = resizeImage(enemyCar1, 80, 160);
            enemyCar2 = resizeImage(enemyCar2, 80, 160);
            enemyCar3 = resizeImage(enemyCar3, 80, 160);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.cars = new LinkedList<>();
        this.playerCar = new Car(500, 0, playerCarImage);
        this.cars.add(playerCar);

        this.cars.add(new Car(-200, 15, enemyCar1));
        this.cars.add(new Car(-500, 15, enemyCar2));
        this.cars.add(new Car(-800, 15, enemyCar3));

        roadSegments = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            roadSegments.add(new Road(0, i * 400));
        }

        timer = new Timer(1000 / 60, this);
        timer.start();

        addKeyListener(this);
        setFocusable(true);
    }

    public BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        Image tmp = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resizedImage;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!isGameOver) {
            Node<Road> roadNode = roadSegments.getHead();
            while (roadNode != null) {
                roadNode.data.update();
                roadNode = roadNode.next;
            }
            Node<Car> carNode = cars.getHead();
            while (carNode != null) {
                carNode.data.update();
                score++;
                carNode = carNode.next;
            }

            checkCollisions();
            repaint();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!isGameOver) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT) {
                playerCar.moveLeft();
            }
            if (key == KeyEvent.VK_RIGHT) {
                playerCar.moveRight();
            }
            if (key == KeyEvent.VK_UP) {
                playerCar.moveDown();
            }
            if (key == KeyEvent.VK_DOWN) {
                playerCar.moveUp();
            }
        } else {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                restartGame();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Node<Road> roadNode = roadSegments.getHead();
        while (roadNode != null) {
            roadNode.data.draw(g, roadImage);
            roadNode = roadNode.next;
        }
        Node<Car> carNode = cars.getHead();
        while (carNode != null) {
            carNode.data.draw(g);
            carNode = carNode.next;
        }
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("Score: " + score, 20, 30);
        if (isGameOver) {
            g.setFont(new Font("Arial", Font.BOLD, 36));
            g.drawString("Game Over! Your final score is " + score + ". Press Enter to restart.", 100, 300);
        }
    }

    public void restartGame() {
        isGameOver = false;
        score = 0;
        playerCar = new Car(500, 0, playerCarImage);
        cars.clear();
        cars.add(playerCar);

        cars.add(new Car(-200, 5, enemyCar1));
        cars.add(new Car(-500, 5, enemyCar2));
        cars.add(new Car(-800, 5, enemyCar3));

        for (int i = 0; i < 5; i++) {
            roadSegments.set(i, new Road(0, i * 400));
        }

        timer.start();
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}

    public void checkCollisions() {
        Rectangle playerRect = playerCar.getBounds();
        Node<Car> carNode = cars.getHead();
        int index = 0;
        while (carNode != null) {
            if (index > 0) {
                Car enemyCar = carNode.data;
                Rectangle enemyRect = enemyCar.getBounds();
                if (playerRect.intersects(enemyRect)) {
                    isGameOver = true;
                    timer.stop();
                    break;
                } else if (enemyCar.getY() > 1200) {
                    score++;
                }
            }
            index++;
            carNode = carNode.next;
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("2D Car Game");
        CarGame game = new CarGame();
        frame.add(game);
        frame.setSize(800, 1200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

class Car {
    Random rand = new Random();
    private int x, y, speed;
    private BufferedImage image;

    public Car(int y, int speed, BufferedImage image ) {
        this.x = rand.nextInt(600) + 100;
        this.y = y;
        this.speed = speed;
        this.image = image;
    }

    public void draw(Graphics g ) {
        g.drawImage(image, x, y, null);
    }

    public void update() {
        y += speed;
        if (y > 1200) {
            y = -160;
        }
    }

    public void moveLeft() {
        x -= 10;
        if (x < 100) x = 100;
    }

    public void moveRight() {
        x += 10;
        if (x > 600) x = 600;
    }

    public void moveUp() {
        y += 10;
        if (y > 600) y = 600;
    }

    public void moveDown() {
        y -= 10;
        if (y < 0) y = 0;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, image.getWidth(), image.getHeight());
    }

    public int getY() {
        return y;
    }
}

class Road {
    private int x, y;
    private int speed = 30;

    public Road(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g, BufferedImage roadImage) {
        g.drawImage(roadImage, x, y, null);
    }

    public void update() {
        y += speed;
        if (y >= 1200) {
            y = -400;
            speed++;
        }
    }
}

class Node<T> {
    T data;
    Node<T> next;

    public Node(T data) {
        this.data = data;
        this.next = null;
    }
}

class LinkedList<T> {
    private Node<T> head;

    public LinkedList() {
        this.head = null;
    }

    public void add(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
        } else {
            Node<T> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }

    public T get(int index) {
        Node<T> current = head;
        int count = 0;
        while (current != null) {
            if (count == index) {
                return current.data;
            }
            count++;
            current = current.next;
        }
        return null;
    }

    public int size() {
        int count = 0;
        Node<T> current = head;
        while (current != null) {
            count++;
            current = current.next;
        }
        return count;
    }

    public void clear() {
        head = null;
    }

    public void set(int index, T data) {
        Node<T> current = head;
        int count = 0;
        while (current != null) {
            if (count == index) {
                current.data = data;
                return;
            }
            count++;
            current = current.next;
        }
    }

    public Node<T> getHead() {
        return head;
    }
}
