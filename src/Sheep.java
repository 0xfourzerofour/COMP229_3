import java.awt.*;
import java.util.List;
import java.util.Optional;
import bos.*;

public class Sheep extends Character {

    public Sheep(Cell location, Behaviour behaviour) {
        super(location, behaviour);
        display = Optional.of(Color.WHITE);
        description = "sheep";
    }
}