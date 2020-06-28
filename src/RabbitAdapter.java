import bos.*;

import java.awt.Color;
import java.util.*;

public class RabbitAdapter extends Character {
    private Rabbit adaptee;

    public RabbitAdapter(Cell location) {
        super(location, null);
        display = Optional.of(Color.GRAY);
        adaptee = new Rabbit();
        description = "rabbit";
    }

    @Override
    public List<RelativeMove> aiMoves(){
        switch (adaptee.nextMove()){
            case 0:
                return Arrays.asList(new MoveDown(Stage.getInstance().grid, this));
            case 1:
                return Arrays.asList(new MoveUp(Stage.getInstance().grid, this));
            case 2:
                return Arrays.asList(new MoveLeft(Stage.getInstance().grid, this));
            case 3:
                return Arrays.asList(new MoveRight(Stage.getInstance().grid, this));
        };
        return Arrays.asList(new MoveRandomly(Stage.getInstance().grid, this));
    }
}
