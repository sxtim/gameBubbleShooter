import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class Enemy {
    public static final int TYPE_METEOR_DEFAULT = 1;
    public static final int TYPE_METEOR_ALTERNATIVE = 2;
    public static final int TYPE_FIGHTER = 5;
    public static final int TYPE_FIGTHER_SNIPER = 6;
    public static final int TYPE_MINER = 7;
    private Image image;

    private Point2D pos = new Point2D(0, 0);
    private Point2D acceleration = new Point2D(0, 0);
    private Point2D velocity = new Point2D(0, 0);
    private double angleRotate;
    private int r;
    private double speed;

    private boolean ready;
    private boolean dead;
    public boolean slow;
    private int health;
    private int type;
    private int rank;


    int hitCooldown;


    //Constructor
    public Enemy(int type, int rank) {
        System.out.println("meteor created type=" + type + " rank=" + rank);


        this.type = type;
        this.rank = rank;

        switch (type) {
            case TYPE_METEOR_DEFAULT:
                switch (rank) {
                    //Default
                    case (1): {
                        image = new ImageIcon("Image/meteors/meteor-01-xl.png").getImage();
                        pos.x = Math.random() * GamePanel.WIDTH;
                        pos.y = 0;
                        r = 27;
                        speed = 0.1;
                        health = 5;
                        double angle = Math.toRadians(Math.random() * 360);// угол направления шариков от 0 до 360
                        acceleration.x = Math.sin(angle) * speed; //смещение шариков
                        acceleration.y = Math.cos(angle) * speed;
                        ready = false;
                        dead = false;
                        break;
                    }//Shard. increase speed, decrease health
                    case (2): {
                        image = new ImageIcon("Image/meteors/meteor-01-l.png").getImage();
                        pos.x = Math.random() * GamePanel.WIDTH;
                        pos.y = 0;
                        r = 20;
                        speed = 0.2;
                        health = 2;
                        double angle = Math.toRadians(Math.random() * 360);
                        acceleration.x = Math.sin(angle) * speed; //смещение шариков
                        acceleration.y = Math.cos(angle) * speed;
                        ready = false;
                        dead = false;
                        break;
                    }
                }
                break;

            case TYPE_METEOR_ALTERNATIVE:
                //Default
                switch (rank) {
                    case (1): {
                        image = new ImageIcon("Image/meteors/meteor-02-xl.png").getImage();
                        pos.x = Math.random() * GamePanel.WIDTH;
                        pos.y = 0;
                        r = 25;
                        speed = 0.2;
                        health = 5;
                        double angle = Math.toRadians(Math.random() * 360);// угол направления шариков от 0 до 360
                        acceleration.x = Math.sin(angle) * speed; //смещение шариков
                        acceleration.y = Math.cos(angle) * speed;
                        ready = false;
                        dead = false;
                        break;
                    } //Shard. increase speed, health
                    case (2): {
                        image = new ImageIcon("Image/meteors/meteor-02-l.png").getImage();
                        pos.x = Math.random() * GamePanel.WIDTH;
                        pos.y = 0;
                        r = 17;
                        speed = 0.3;
                        health = 10;
                        double angle = Math.toRadians(Math.random() * 360);// угол направления шариков от 0 до 360
                        acceleration.x = Math.sin(angle) * speed; //смещение шариков
                        acceleration.y = Math.cos(angle) * speed;
                        ready = false;
                        dead = false;
                        break;
                    }
                }
                break;


            case TYPE_FIGTHER_SNIPER:
                switch (rank) {
                    //Default
                    case (1): {
//                        System.out.println("изменяется картинка у первого типа");
                        image = new ImageIcon("Image/enemy/figthersniper.png").getImage();
                        pos.x = Math.random() * GamePanel.WIDTH;
                        pos.y = 0;
                        r = 16;
                        speed = 0.15;
                        health = 2;
                        double angleDirection = Math.toRadians(Math.random() * 360);// угол направления шариков от 0 до 360
                        acceleration.x = Math.sin(angleDirection) * speed; //смещение шариков
                        acceleration.y = Math.cos(angleDirection) * speed;
                        ready = false;
                        dead = false;
                        break;
                    }
                    case (2): {
//                        System.out.println("создаем второй тип метеора");
                        image = new ImageIcon("Image/enemy/figthersniper2.png").getImage();
                        pos.x = Math.random() * GamePanel.WIDTH;
                        pos.y = 0;
                        r = 16;
                        speed = 0.25;
                        health = 1;
                        double angle = Math.toRadians(Math.random() * 360);
                        acceleration.x = Math.sin(angle) * speed; //смещение шариков
                        acceleration.y = Math.cos(angle) * speed;
                        ready = false;
                        dead = false;
                        break;
                    }
                }
                break;
            case TYPE_MINER:
                switch (rank) {
                    //Default - increase HP, speed
                    case (1): {

                        image = new ImageIcon("Image/enemy/miner.png").getImage();
                        pos.x = Math.random() * GamePanel.WIDTH;
                        pos.y = 0;
                        r = 21;
                        speed = 0.24;
                        health = 7;
                        double angleDirection = Math.toRadians(Math.random() * 360);// угол направления шариков от 0 до 360
                        acceleration.x = Math.sin(angleDirection) * speed; //смещение шариков
                        acceleration.y = Math.cos(angleDirection) * speed;
                        ready = false;
                        dead = false;
                        break;
                    }
                    case (2): {

                        image = new ImageIcon("Image/enemy/miner2.png").getImage();
                        pos.x = Math.random() * GamePanel.WIDTH;
                        pos.y = 0;
                        r = 21;
                        speed = 0.3;
                        health = 10;
                        double angle = Math.toRadians(Math.random() * 360);
                        acceleration.x = Math.sin(angle) * speed; //смещение шариков
                        acceleration.y = Math.cos(angle) * speed;
                        ready = false;
                        dead = false;
                        break;
                    }
                }
        }
    }

    //Functions
    public boolean isDead() {
        return dead;
    }


    public void hit(boolean ignoreCooldown, Player player) {//при попадании уменьшаем здоровье
        hitCooldown = 60;

        health--;
        if (health <= 0) {
            dead = true;
        }


        // Отскок
        if (player != null) {
            Point2D delta = player.pos.copy().minus(pos);//расстояние между позициями игрока и метеора
            acceleration.set(speed, 0)  // tmp acceleration
                    .rotate(delta.multiple(-1).angle());
            player.velocity.set(delta.multiple(-1));
            return;
        }
    }

    int fireCooldown = (int) (Math.random() * 320);
    int fireCooldownMine = (int) (Math.random() * 200);

    public void update() {
        if(slow){
            System.out.println("SLOW");
            velocity.multiple(0.7);
            velocity.add(acceleration);
            velocity.clamp(3);
            pos.add(velocity);
        } else {
            velocity.multiple(0.9);
            velocity.add(acceleration);
            velocity.clamp(3);
            pos.add(velocity);
        }


        if (type == TYPE_METEOR_DEFAULT) {
            angleRotate += Math.PI / 360;
        }
        if (type == TYPE_METEOR_ALTERNATIVE) {
            angleRotate -= Math.PI / 360;
        }

        if (hitCooldown > 0) {
            hitCooldown--;
        }

        //проверка выхода за пределы поля
        //еесли враг вышел за пределы поля, то возвращаем его
        if (pos.x < 0 && acceleration.x < 0) acceleration.x = -acceleration.x;
        if (pos.x > GamePanel.WIDTH && acceleration.x > 0) acceleration.x = -acceleration.x;
        if (pos.y < 0 && acceleration.y < 0) acceleration.y = -acceleration.y;
        if (pos.y > GamePanel.HEIGHT && acceleration.y > 0) acceleration.y = -acceleration.y;


        switch (type) {
            case TYPE_FIGTHER_SNIPER: {
                if (fireCooldown == 0) {
                    fireBullet();
                    fireCooldown = 320;
                } else {
                    fireCooldown--;
                }
            }
            break;
            case TYPE_MINER: {
                if (fireCooldownMine == 0) {
                    fireBullet();
                    fireCooldownMine = 200;
                } else {
                    fireCooldownMine--;
                }
            }
        }
    }

    private void fireBullet() {
        if(type == TYPE_MINER){
            GamePanel.bullets.add(new Bullet (Bullet.TYPE_ENEMY_MINE, pos.x, pos.y, 0));
        }else {
            double newAngle = GamePanel.player.pos.copy().minus(pos).multiple(1).angle();
            GamePanel.bullets.add(new Bullet(Bullet.TYPE_ENEMY_BULLET, pos.x, pos.y, newAngle));
        }
    }

    public void explode() {
        if (rank == 1) {
            int amount = 0;
            if (type == TYPE_METEOR_ALTERNATIVE) {
                amount = 4;
            }
            if (type == TYPE_METEOR_DEFAULT) {
                amount = 3;
            }
            for (int i = 0; i < amount; i++) {
                Enemy e = new Enemy(type, 2);
                e.pos.x = this.pos.x;
                e.pos.y = this.pos.y;
                GamePanel.enemies.add(e);
            }
        }
    }


    public void draw(Graphics2D g) {

//                System.out.println("рисуем тип 2");
        AffineTransform origForm1; //создаем объект класса AffineTransform
        origForm1 = g.getTransform();//получаем текущее значение
        AffineTransform newForm1 = (AffineTransform) (origForm1.clone());//клонируем текущее значение
        newForm1.rotate(angleRotate, pos.x, pos.y);//вертим     полученное изображение относительно X и Y
        g.setTransform(newForm1);//
        g.drawImage(image, (int) pos.x - r, (int) pos.y - r, null);
        g.setTransform(origForm1);

    }
//        g.drawImage(enemyImages.get(0), (int) pos.x - 27, (int) pos.y - 27, null);//анимация
    //  g.setColor(Color.CYAN);
    // g.drawOval((int)(x - r), (int)(y - r), r * 2 , r * 2 );
//        g.drawImage(sparksImages.get((animFrame) % sparksImages.size()), (int) pos.x - 32, (int) pos.y - 30, null);//анимация

//        g.drawImage(enemyImages.get((animFrame / 30) % enemyImages.size()), (int) pos.x, (int) pos.y - 30, null);


    //Getters

    public void setSlow(boolean b){
        slow = b;
    }

    public double getX() {
        return pos.x;
    }

    public double getY() {
        return pos.y;
    }

    public int getR() {
        return r;
    }

    public int getHeath() {
        return health;
    }

    public int getType() {
        return type;
    }

    public int getRank() {
        return rank;
    }
}
