package main.entities.pathfinding;

import java.util.ArrayList;
import main.level.Level;
import main.tiles.Tile;

/**
 *
 * @author Matthew Galan
 */
public class AStarPathfinding {
    private Level level;
    
    public AStarPathfinding(Level level) {
        this.level = level;
    }
    
    public ArrayList<Tile> getPath(Tile start, Tile end) {
        //Returns the path, with the first element being the start and the last element being the end
        ArrayList<PathingNode> openList = new ArrayList<>();
        ArrayList<PathingNode> closedList = new ArrayList<>();
        openList.add(new PathingNode(start, end, null)); //Add start to openList
        while (!openList.isEmpty()) {
            PathingNode currentNode = openList.get(0); //currentNode = node w/ lowest f score
            for (PathingNode p : openList) {
                if (p.getF() < currentNode.getF()) {
                    currentNode = p;
                }
            }
            closedList.add(currentNode); //Add currentNode to closedList
            openList.remove(currentNode); //and remove it from openList
            if (currentNode.getTile() == end) {
                break;
            }
            for (Tile neighborTile : level.getWalkableNeighbors(currentNode.getTile())) {
                if (containsTile(closedList, neighborTile)) {
                    continue;
                }
                if (!containsTile(openList, neighborTile)) {
                    openList.add(new PathingNode(neighborTile, end, currentNode));
                } else {
                    findNodeWithTile(openList, neighborTile).updateG(currentNode.getG() + 1, currentNode);
                }
            }
        }
        PathingNode head = closedList.get(closedList.size() - 1);
        ArrayList<Tile> path = new ArrayList<>();
        while (head.getParent() != null) {
            path.add(head.getTile());
            head = head.getParent();
        }
        path.add(head.getTile());
        return path;
    }
    
    private boolean containsTile(ArrayList<PathingNode> list, Tile target) {
        for (PathingNode n : list) {
            if (n.getTile() == target) {
                return true;
            }
        }
        return false;
    }
    
    private PathingNode findNodeWithTile(ArrayList<PathingNode> list, Tile target) {
        for (PathingNode n : list) {
            if (n.getTile() == target) {
                return n;
            }
        }
        System.err.println("findNodeWithTile couldn't find anything!");
        return null;
    }
}
