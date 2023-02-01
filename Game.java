import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Game extends JPanel implements ActionListener
{
    int WIDTH = 500;
    int HEIGHT = 500;
    int DOT = 10;
    int DELAY = 200;
    int head_x = WIDTH/(DOT * 2);
    int head_y = WIDTH/(DOT * 2);
    ArrayList<Integer> x = new ArrayList<>();
    ArrayList<Integer> y = new ArrayList<>();

    int x_limit = WIDTH/DOT;
    int y_limit = WIDTH/DOT;
    boolean IS_ALIVE = true;
    boolean left = false;
    boolean right = true;
    boolean up = false;
    boolean down = false;

    Timer timer;
    Random r;
    Image snake_food;
    Image snake_head;
    Image snake_tail;
    int food_x = head_x + 5;
    int food_y = head_y;
    int food_eaten = 0;
    boolean justEaten = false;
    static JFrame frame = new JFrame();
    public Game() throws IOException
    {
        r = new Random();
        addKeyListener(new SnakeAdapter());
        setBackground(Color.BLACK);
        setFocusable(true);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        snake_food = ImageIO.read(Game.class.getClassLoader().getResourceAsStream("snake_food.png"));
        snake_head = ImageIO.read(Game.class.getClassLoader().getResourceAsStream("snake_head.png"));
        snake_tail = ImageIO.read(Game.class.getClassLoader().getResourceAsStream("snake_body.png"));

    }

    public static void main(String[] args) throws IOException
    {
        Game game = new Game();

        frame.add(game);
        frame.setTitle("Snake Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        game.timer = new Timer(game.DELAY, game);
        game.timer.start();
    }

    public void tick()
    {
        if(IS_ALIVE)
        {
            if(checkBounds() && !checkBodyCollision())
            {
                if(checkFood())
                {
                    food_eaten++;
                    frame.setTitle("Snake Game   SCORE: " + food_eaten);
                    updateFood();
                }
                updateSnakeBody();
                updateSnakeHead();
            }
            else
            {
                x.clear();
                y.clear();
                food_eaten = 0;
                frame.setTitle("Snake Game   SCORE: " + food_eaten);
                head_x = WIDTH/(DOT * 2);
                head_y = WIDTH/(DOT * 2);
                left = false;
                right = true;
                up = false;
                down = false;
            }
        }
    }

    public boolean checkBounds()
    {
        if((head_x > -1) && (head_x < (x_limit)) && (head_y > -1) && (head_y < (y_limit)))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean checkFood()
    {
        if((head_x == food_x) && (head_y == food_y))
        {
            justEaten = true;
            return true;
        }
        else
        {
            return false;
        }
    }

    public void updateFood()
    {
        if (head_x == food_x && head_y == food_y)
        {

            food_x = r.nextInt(x_limit);
            food_y = r.nextInt(y_limit);


            while (x.contains(food_x) && y.contains(food_y))
            {
                food_x = r.nextInt(x_limit);
                food_y = r.nextInt(y_limit);
            }
        }
    }

    public boolean checkBodyCollision()
    {
        boolean isCollision = false;
        if(x.size() > 2)
        {
            for (int i = 0; i < x.size(); i++)
            {
                if (((head_x == x.get(i)) && (head_y == y.get(i))))
                {
                    isCollision = true;
                    break;
                }
            }
        }

        return isCollision;
    }

    public void updateSnakeBody()
    {
        if(x.size() == 0)
        {
            if(justEaten)
            {
                x.add(head_x);
                y.add(head_y);
                justEaten = false;
            }
        }

        if(x.size() == 1)
        {
            if(justEaten)
            {
                x.add(x.get(0));
                y.add(y.get(0));
                justEaten = false;
            }
            x.set(0, head_x);
            y.set(0, head_y);

        }

        if(x.size() > 1)
        {
            if (justEaten)
            {
                x.add(x.get(x.size() - 1));
                y.add(y.get(y.size() - 1));
                justEaten = false;
            }

            for (int i = x.size() - 1; i > 0; i--)
            {
                x.set(i, x.get(i - 1));
                y.set(i, y.get(i - 1));
            }
            x.set(0, head_x);
            y.set(0, head_y);
        }
    }

    public void updateSnakeHead()
    {

        if(checkBounds())
        {
            if(left)
            {
                head_x--;
            }
            if(right)
            {
                head_x++;
            }
            if(up)
            {
                head_y--;
            }
            if(down)
            {
                head_y++;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        tick();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(snake_food, food_x * DOT, food_y * DOT, this);
        g.drawImage(snake_head, head_x * DOT, head_y * DOT, this);

        for(int i = 0; i < x.size(); i++)
        {
            g.drawImage(snake_tail, x.get(i) * DOT, y.get(i) * DOT, this);
        }
    }


    private class SnakeAdapter extends KeyAdapter
    {
        @Override
        public void keyPressed(KeyEvent e)
        {
            int key_code = e.getKeyCode();
            if((key_code == KeyEvent.VK_LEFT) && !right)
            {
                left = true;
                up = false;
                down = false;
            }
            if((key_code == KeyEvent.VK_RIGHT) && !left)
            {
                right = true;
                up = false;
                down = false;
            }
            if((key_code == KeyEvent.VK_UP) && !down)
            {
                up = true;
                right = false;
                left = false;
            }
            if((key_code == KeyEvent.VK_DOWN) && !up)
            {
                down = true;
                right = false;
                left = false;
            }
        }
    }
}
