import bos.NoMove;
import bos.RelativeMove;

import java.util.Arrays;
import java.util.List;

public class MoveTowards implements Behaviour {
    Character target;

    public MoveTowards(Character character){
        this.target = character;
    }

    @Override
    public List<RelativeMove> chooseMoves(Character mover) {
        List<RelativeMove> movesToTarget = Stage.getInstance().grid.movesBetween(mover.location,target.location, mover);
        if (movesToTarget.size() == 0)
            return Arrays.asList(new NoMove(Stage.getInstance().grid, mover));
        else
            return movesToTarget;    }
}
