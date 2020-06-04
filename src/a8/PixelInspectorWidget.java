package a8;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PixelInspectorWidget extends JPanel implements MouseListener {
	
	private PictureView picture_view;
	private JLabel x;
	private JLabel y;
	private JLabel red;
	private JLabel green;
	private JLabel blue;
	private JLabel brightness;
	Picture picture;
	public PixelInspectorWidget(Picture picture) {
		this.picture = picture;
		setLayout(new BorderLayout());

		picture_view = new PictureView(picture.createObservable());
		picture_view.addMouseListener(this);

		add(picture_view, BorderLayout.CENTER);
		//JLabel display = new JLabel();
		//add(display, BorderLayout.WEST);

		JPanel pixel_inspector = new JPanel();
		pixel_inspector.setPreferredSize(new Dimension(150, 100));
		pixel_inspector.setLayout(new GridLayout(6,2));
		add(pixel_inspector, BorderLayout.WEST);
		//jLabels
		x = new JLabel("X: ");
		y = new JLabel("Y: ");
		red = new JLabel("Red: ");
		green = new JLabel("Green: ");
		blue = new JLabel("Blue: ");
		brightness = new JLabel("Brightness: ");
		pixel_inspector.add(x);
		pixel_inspector.add(y);
		pixel_inspector.add(red);
		pixel_inspector.add(green);
		pixel_inspector.add(blue);
		pixel_inspector.add(brightness);
	}

	public void mouseClicked(MouseEvent e) {
		x.setText("X: " + e.getX());
		y.setText("Y: " + e.getY());
		red.setText("Red: " + Math.round((picture.getPixel(e.getX(), e.getY()).getRed())*1000.0)/1000.0);
		green.setText("Green: " + Math.round((picture.getPixel(e.getX(), e.getY()).getGreen())*1000.0)/1000.0);
		blue.setText("Blue: " + Math.round((picture.getPixel(e.getX(), e.getY()).getBlue())*1000.0)/1000.0);
		brightness.setText("Brightness: " + Math.round(((picture.getPixel(e.getX(), e.getY()).getIntensity()))*1000.0)/1000.0);
	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

}

