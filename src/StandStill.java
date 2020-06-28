import bos.NoMove;
import bos.RelativeMove;

import java.util.Arrays;
import java.util.List;

public class StandStill implements Behaviour {
    @Override
    public List<RelativeMove> chooseMoves(Character mover) {
        return Arrays.asList(new NoMove(Stage.getInstance().grid, mover));
    }
}
