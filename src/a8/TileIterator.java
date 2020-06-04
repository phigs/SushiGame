package a8;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class TileIterator implements Iterator<SubPicture>{
	Picture picture;
	int tile_width;
	int tile_height;
	int curX;
	int curY;
	int maxX;
	int maxY;
	public TileIterator(Picture picture, int tile_width, int tile_height) {
		this.picture = picture;
		this.tile_height = tile_height;
		this.tile_width = tile_width;
		curX = 0;
		curY = 0;
		maxX = picture.getWidth() / tile_width * tile_width - 1;
		maxY = picture.getHeight() / tile_height * tile_height - 1;
	}

	public boolean hasNext() {
		if(curY <= maxY  && curX <= maxX){
			return true;
		}
		else 
			return false;
	}

	public SubPicture next() {
		if(!this.hasNext())
			throw new NoSuchElementException();
		Coordinate a = new Coordinate(curX, curY);
		Coordinate b = new Coordinate(a.getX() + tile_width - 1, a.getY() + tile_height - 1);
		curX += tile_width; 
		if(curX > maxX){
			curX = 0;
			curY += tile_height;
		}
		return picture.extract(a, b);
	}
}
