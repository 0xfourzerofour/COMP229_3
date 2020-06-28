import bos.RelativeMove;

import java.util.List;

public interface Behaviour {
    public List<RelativeMove> chooseMoves(Character mover);
}
