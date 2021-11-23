import javax.swing.*;
import java.awt.*;

public class PowerUp {
    //Fields
    public static Image iconSlowDown = new ImageIcon("Image/icon/iconslowdown.png").getImage();
    public static final int TYPE_POWERUP = 1;
    public static final int TYPE_LIFE = 2;
    public static final int TYPE_SLOW_DOWN = 3;
    public static final int TYPE_ENERGY_SHIELD = 4;
    private Image image;
    private double x;
    private double y;
    private int r;


    private int type;
    // 1 -- +1 power
    // 2 -- +1 life
    // 3 -- slowdown
    // 4 -- energy shield


    //Constructor

    public PowerUp(int type, double x, double y) {
        this.type = type;
        this.x = x;
        this.y = y;
        r = 20;

        switch (type) {
            case TYPE_POWERUP: {
                System.out.println("POWERUP");
                image = new ImageIcon("Image/icon/iconpowerup.png").getImage();
                break;
            }
            case TYPE_LIFE : {
                System.out.println("LIFE");
                image = new ImageIcon("Image/icon/iconlife.png").getImage();
                break;
            }
            case TYPE_SLOW_DOWN: {
                image = new ImageIcon("Image/icon/iconslowdown.png").getImage();
                break;
            }
            case TYPE_ENERGY_SHIELD: {
                image = new ImageIcon("Image/icon/iconenergyshield.png").getImage();
            }
        }


    }


    //Functions
    public boolean update() {
        y += 2;
        return y > GamePanel.HEIGHT + r;
    }


    public void draw(Graphics2D g) {
        g.drawImage(image, (int) x - r, (int) y - r, null);



    }


    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getR() {
        return r;
    }

    public int getType() {
        return type;
    }
}
