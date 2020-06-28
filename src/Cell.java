import java.awt.*;
import java.util.Random;

public class Cell extends Rectangle {

    int size;

    private static Random rand = new Random();
    boolean traversable;
    public boolean ticked;
    Color c;

    public Cell(int x, int y, int size) {
        super(x, y, size, size);
        this.size = size;
        traversable = true;
        ticked = false;
        c = new Color(rand.nextInt(30), rand.nextInt(155)+100, rand.nextInt(30));
    }

    public void paint(Graphics g, Boolean highlighted) {
        if (!traversable) {
            g.setColor(new Color(188,85,15));
            g.fillRect(x, y, size, size);
            g.setColor(new Color(58,24,2));
            for (int i = 0; i <= 35; i += 5){ // a hash pattern
                g.drawLine(x, y + i, x + i, y);
                g.drawLine(x + i, y+size, x+size, y+i);
            }
        } else {
            g.setColor(c);
            g.fillRect(x, y, size, size);
        }
        if (highlighted && traversable) {
            g.setColor(Color.LIGHT_GRAY);
            for(int i = 0; i < 10; i++) {
                g.drawRoundRect(x+1, y+1, size-2, size-2, i, i);
            }
        }
    }

    public void paintOverlay(Graphics g){
        g.setColor(new Color(1.0f,0f,0f, 0.7f));
        g.fillRect(x+size/4, y+size/4, size/2, size/2);
    }

    @Override
    public boolean contains(Point target){
        if (target == null)
            return false;
        return super.contains(target);
    }

    public int getGrassHeight(){
        return c.getGreen()/50;
    }
}
