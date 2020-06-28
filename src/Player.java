import bos.GameBoard;

import java.awt.*;

public class Player implements KeyObserver {

    public Cell location;
    private Boolean inMove;

    public Player(Cell location){
        this.location = location;
        inMove = false;
    }

    public void paint(Graphics g){
        g.setColor(Color.ORANGE);
        g.fillOval(location.x + location.width / 4, location.y + location.height / 4, location.width / 2, location.height / 2);
    }

    public void startMove(){
        inMove = true;
    }

    public Boolean inMove(){
        return inMove;
    }

    public void notify(char c, GameBoard<Cell> gb) {
        if (inMove){
            if (c == 'a') {
                location = gb.leftOf(location).orElse(location);
                inMove = false;
            } else if (c == 'd') {
                location = gb.rightOf(location).orElse(location);
                inMove = false;
            } else if (c == 'w') {
                location = gb.above(location).orElse(location);
                inMove = false;
            } else if (c == 's') {
                location = gb.below(location).orElse(location);
                inMove = false;
            }

        }
    }
}
