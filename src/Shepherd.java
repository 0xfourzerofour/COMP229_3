import bos.*;
import java.awt.*;
import java.util.Optional;

public class Shepherd extends Character {

    public Shepherd(Cell location, Behaviour behaviour) {
        super(location, behaviour);
        display = Optional.of(Color.GREEN);
        description = "shepherd";
    }

}