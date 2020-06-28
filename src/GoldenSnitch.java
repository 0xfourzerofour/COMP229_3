import bos.*;

import java.awt.*;
import java.util.Optional;
import java.util.List;
public class GoldenSnitch extends Character {


    public GoldenSnitch(Cell location, Behaviour behaviour) {
        super(location, behaviour);
        description = "golden snitch";
        display = Optional.of(Color.yellow);
    }

    public void paintFace(Graphics g){
        g.setColor(Color.blue);
        g.fillOval(location.x + location.width/3, location.y + location.height/3, location.width/3, location.height/3);
    }

}
