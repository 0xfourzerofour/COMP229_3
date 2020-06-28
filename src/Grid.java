import bos.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Grid implements GameBoard<Cell> {

    static int size = 20;
    static int gridSize = 35;

    private Cell[][] cells = new Cell[size][size];

    private int x;
    private int y;

    private Point lastSeenMousePos;
    private long stillMouseTime;

    public Grid(int x, int y) {
        this.x = x;
        this.y = y;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[i][j] = new Cell(x + j * gridSize, y + i * gridSize, gridSize);
            }
        }
    }

    public void blockCell(int row, int col){
        cells[row][col].traversable = false;
    }

    public void paint(Graphics g, Point mousePosition) {
        if (lastSeenMousePos != null && lastSeenMousePos.equals(mousePosition)) {
            stillMouseTime++;
        } else {
            stillMouseTime = 0;
        }
        doToEachCell((c) -> {
            c.paint(g, c.contains(mousePosition));
        });
        doToEachCell((c) -> {
            if (c.contains(mousePosition)){
                if (stillMouseTime > 20){
                    g.setColor(Color.YELLOW);
                    g.fillRoundRect(mousePosition.x + 20, mousePosition.y + 20, 50, 15, 3, 3);
                    g.setColor(Color.BLACK);
                    g.drawString("grass: "+ c.getGrassHeight(), mousePosition.x + 20, mousePosition.y + 32);
                }
            }
        });
        lastSeenMousePos = mousePosition;
    }

    public Cell getRandomCell() {
        java.util.Random rand = new java.util.Random();
        return cells[rand.nextInt(size)][rand.nextInt(size)];
    }

    private bos.Pair<Integer, Integer> indexOfCell(Cell c) {
        for (int y = 0; y < size; ++y) {
            for (int x = 0; x < size; ++x) {
                if (cells[y][x] == c) {
                    return new bos.Pair(y, x);
                }
            }
        }
        return null;
    }

    public Pair<Integer, Integer> findAmongstCells(Predicate<Cell> predicate) {
        for (int y = 0; y < size; ++y) {
            for (int x = 0; x < size; ++x) {
                if (predicate.test(cells[y][x]))
                    return new Pair(y, x);
            }
        }
        return null;
    }

    public Optional<Pair<Integer, Integer>> safeFindAmongstCells(Predicate<Cell> predicate) {
        for (int y = 0; y < size; ++y) {
            for (int x = 0; x < size; ++x) {
                if (predicate.test(cells[y][x]))
                    return Optional.of(new Pair(y, x));
            }
        }
        return Optional.empty();

    }

    private void doToEachCell(Consumer<Cell> func) {
        for (int y = 0; y < size; ++y) {
            for (int x = 0; x < size; ++x) {
                func.accept(cells[y][x]);
            }
        }
    }

    @Override
    public Optional<Cell> below(Cell relativeTo) {
        return safeFindAmongstCells((c) -> c == relativeTo)
                .filter((pair) -> pair.first < size-1)
                .map((pair) -> cells[pair.first + 1][pair.second]);
    }

    @Override
    public Optional<Cell> above(Cell relativeTo) {
        return safeFindAmongstCells((c) -> c == relativeTo)
                .filter((pair) -> pair.first > 0)
                .map((pair) -> cells[pair.first - 1][pair.second]);
    }

    @Override
    public Optional<Cell> rightOf(Cell relativeTo) {
        return safeFindAmongstCells((c) -> c == relativeTo)
                .filter((pair) -> pair.second < size-1)
                .map((pair) -> cells[pair.first][pair.second + 1]);
    }

    @Override
    public Optional<Cell> leftOf(Cell relativeTo) {
        return safeFindAmongstCells((c) -> c == relativeTo)
                .filter((pair) -> pair.second > 0)
                .map((pair) -> cells[pair.first][pair.second - 1]);
    }

    public Cell cellAtRowCol(Integer row, Integer col) {
        return cells[row][col];
    }


    @Override
    public synchronized List<RelativeMove> movesBetween(Cell from, Cell to, GamePiece<Cell> mover) {
        System.out.println(((Character)mover).description + ": calculating a moves between: " + from + " -> " + to);
        class Node extends bos.Pair<Cell, List<RelativeMove>>{public Node(Cell a, List<RelativeMove> b){super(a, b);}} // first cell is the node, second is the cell that led to it

        // clear all ticks from cells
        doToEachCell(c -> c.ticked = false);
        java.util.Queue<Node> frontier = new java.util.LinkedList<Node>();

        frontier.add(new Node(from, new java.util.ArrayList()));

        Node curr = frontier.remove();
        while(curr.first != to){
            Optional<Cell> lo = leftOf(curr.first);
            if (lo.isPresent() && !lo.get().ticked && lo.get().traversable){
                lo.get().ticked = true;
                List<RelativeMove> sofar = new ArrayList(curr.second);
                sofar.add(new MoveLeft(this, mover));
                frontier.add(new Node(lo.get(), sofar));
            }

            Optional<Cell> ro = rightOf(curr.first);
            if (ro.isPresent() && !ro.get().ticked && ro.get().traversable){
                ro.get().ticked = true;
                List<RelativeMove> sofar = new ArrayList(curr.second);
                sofar.add(new MoveRight(this, mover));
                frontier.add(new Node(ro.get(), sofar));
            }

            Optional<Cell> bo = below(curr.first);
            if (bo.isPresent() && !bo.get().ticked && bo.get().traversable){
                bo.get().ticked = true;
                List<RelativeMove> sofar = new ArrayList(curr.second);
                sofar.add(new MoveDown(this, mover));
                frontier.add(new Node(bo.get(), sofar));
            }

            Optional<Cell> ao = above(curr.first);
            if (ao.isPresent()&& !ao.get().ticked && ao.get().traversable){
                ao.get().ticked = true;
                List<RelativeMove> sofar = new ArrayList(curr.second);
                sofar.add(new MoveUp(this, mover));
                frontier.add(new Node(ao.get(), sofar));
            }

            if(frontier.size() > 0)
                curr = frontier.remove();
            else
                break;
        }

        return curr.second;
    }
}