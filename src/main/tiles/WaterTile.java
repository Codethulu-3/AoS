package main.tiles;

import java.awt.Graphics;
import main.Handler;
import main.gfx.Assets;
import static main.tiles.Tile.TILEHEIGHT;
import static main.tiles.Tile.TILEWIDTH;

/**
 *
 * @author Alex
 */
public class WaterTile extends Tile {

    public WaterTile(Handler handler, int worldX, int worldY) {
        super(handler, worldX, worldY, TileID.Water);
        setBlocked(true);
    }

    @Override
    public void render(Graphics g, int x, int y) {
        g.drawImage(Assets.water, x, y, TILEWIDTH, TILEHEIGHT, null);
    }
}
