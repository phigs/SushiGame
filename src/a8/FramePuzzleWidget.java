package a8;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FramePuzzleWidget extends JPanel implements MouseListener,KeyListener {


	private Picture currentPicture;
	private PictureView[][] pictureViews = new PictureView[5][5];
	private PictureView pictureView;
	Picture picture;
	private int tileWidth;
	private int tileHeight;
	private Picture whiteTile;
	private int xPos = 4;
	private int yPos = 4;
	public FramePuzzleWidget(Picture picture) {
		this.picture = picture;
		setLayout(new BorderLayout());
		JPanel framePuzzle = new JPanel();
		framePuzzle.setLayout(new GridLayout(5,5));
		add(framePuzzle,BorderLayout.CENTER);
		tileWidth = picture.getWidth()/5;
		tileHeight = picture.getHeight()/5;
		whiteTile = new PictureImpl(tileWidth,tileHeight);

		for(int i = 0; i < tileWidth; i++){
			for(int j = 0; j < tileHeight; j++){
				whiteTile.setPixel(i, j, new ColorPixel(1,1,1));
			}
		}
		int i = 0;
		int j = 0;
		for(int k = 0; k < picture.getWidth()-tileWidth;k+=tileWidth-1){
			for(int x = 0; x < picture.getHeight()-tileHeight;x+=tileHeight-1){
				currentPicture = picture.extract(new Coordinate(k,x), new Coordinate(k+tileWidth,x+tileHeight));
				pictureView = new PictureView(currentPicture.createObservable());
				pictureViews[i][j] = pictureView;
				pictureViews[i][j].addKeyListener(this);
				pictureViews[i][j].addMouseListener(this);
				i++;

			}
			i = 0;
			j++;
			pictureViews[xPos][yPos] = new PictureView(whiteTile.createObservable());

		}
		for(i = 0; i < 5; i++){
			for(j = 0; j < 5; j++){	
				framePuzzle.add(pictureViews[i][j]);
			}
		}
		pictureViews[4][4].addMouseListener(this);
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

		for(int i = 0;i < pictureViews.length;i++){
			for(int j = 0;j < pictureViews[i].length;j++){

				if(e.getSource() == pictureViews[i][j]){

					if(i == xPos){

						if(j < yPos){
							for(int x = 0; x < yPos-j;x++){
								int y = yPos;
								pictureViews[xPos][y-x].setPicture(pictureViews[xPos][y-x-1].getPicture());

							}
							yPos = yPos- (yPos-j);
							pictureViews[xPos][yPos].setPicture(whiteTile.createObservable());

						}
						else if(j > yPos){
							for(int x = 0; x < j-yPos;x++){
								int y = yPos;
								pictureViews[xPos][y+x].setPicture(pictureViews[xPos][y+x+1].getPicture());

							}
							yPos = yPos + (j-yPos);
							pictureViews[xPos][yPos].setPicture(whiteTile.createObservable());
						}

					}
					else if(j == yPos){

						if(i < xPos){
							for(int x = 0; x < xPos-i;x++){
								int y = xPos;
								pictureViews[y-x][yPos].setPicture(pictureViews[y-x-1][yPos].getPicture());

							}
							xPos = xPos- (xPos-i);
							pictureViews[xPos][yPos].setPicture(whiteTile.createObservable());

						}

						else if(i > xPos){
							for(int x = 0; x < i-xPos;x++){
								int y = xPos;
								pictureViews[y+x][yPos].setPicture(pictureViews[y+x+1][yPos].getPicture());

							}
							xPos = xPos+ (i-xPos);
							pictureViews[xPos][yPos].setPicture(whiteTile.createObservable());

						}

					}

				}

			}
		}


	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void keyTyped(KeyEvent e) {

		//loop throguh pictureview array and after that double for loop if picture[j][i] = e.getsource 
	}
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();

		//down
		if(e.getKeyCode() == 37){
			try{
				pictureViews[xPos][yPos].setPicture(pictureViews[xPos][yPos-1].getPicture());
				yPos--;
			}
			catch(RuntimeException r){
			}
		}

		//left
		if(e.getKeyCode() == 38){
			try{
				pictureViews[xPos][yPos].setPicture(pictureViews[xPos-1][yPos].getPicture());
				xPos--;
			}
			catch(RuntimeException r){

			}
		}
		//up
		else if(e.getKeyCode() == 39){
			try{
				pictureViews[xPos][yPos].setPicture(pictureViews[xPos][yPos+1].getPicture());
				yPos++;
			}
			catch(RuntimeException r){

			}
		}

		//right
		if(e.getKeyCode() == 40){
			try{
				pictureViews[xPos][yPos].setPicture(pictureViews[xPos+1][yPos].getPicture());
				xPos++;
			}
			catch(RuntimeException r){

			}
		}
		pictureViews[xPos][yPos].setPicture(whiteTile.createObservable());

	}
}
