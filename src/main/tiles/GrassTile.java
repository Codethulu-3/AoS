package main.tiles;

import java.awt.Graphics;
import main.Handler;
import main.gfx.Assets;
import static main.tiles.Tile.TILEHEIGHT;
import static main.tiles.Tile.TILEWIDTH;

public class GrassTile extends Tile {

    public GrassTile(Handler handler, int worldX, int worldY) {
        super(handler, worldX, worldY, TileID.Grass);
    }

    @Override
    public void render(Graphics g, int x, int y) {
        g.drawImage(Assets.grass, x, y, TILEWIDTH, TILEHEIGHT, null);
    }
}
