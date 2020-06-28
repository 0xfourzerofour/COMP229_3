import bos.GameBoard;

public interface KeyObserver {
    public void notify(char c, GameBoard<Cell> gb);
}
