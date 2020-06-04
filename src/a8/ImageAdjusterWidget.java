package a8;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ImageAdjusterWidget extends JPanel implements ChangeListener {

	private PictureView picture_view;
	private JLabel blurL;
	private static JSlider blurS;
	private JLabel saturationL;
	private static JSlider saturationS;
	private JLabel brightnessL;
	private static JSlider brightnessS;
	static Picture picture;
	private static Picture changedPicture;
	public ImageAdjusterWidget(Picture picture) {
		ImageAdjusterWidget.picture = picture;
		setLayout(new BorderLayout());

		picture_view = new PictureView(picture.createObservable());

		add(picture_view, BorderLayout.CENTER);

		JPanel image_adjuster = new JPanel();
		image_adjuster.setPreferredSize(new Dimension(150, 100));
		image_adjuster.setLayout(new GridLayout(3,2));
		add(image_adjuster, BorderLayout.SOUTH);
		//JLabels + JSliders

		blurL = new JLabel("Blur: ");
		blurS = new JSlider(JSlider.HORIZONTAL, 0,5,0);
		saturationL = new JLabel("Saturation: ");
		saturationS = new JSlider(JSlider.HORIZONTAL,-100,100,0);
		brightnessL = new JLabel("Brightness: ");
		brightnessS = new JSlider(JSlider.HORIZONTAL,-100,100,0);


		blurS.setMajorTickSpacing(1);
		blurS.setPaintTicks(true);
		blurS.setPaintLabels(true);
		blurS.setSnapToTicks(true);
		saturationS.setMinorTickSpacing(5);
		saturationS.setMajorTickSpacing(25);
		saturationS.setPaintTicks(true);
		saturationS.setPaintLabels(true);
		brightnessS.setMinorTickSpacing(5);
		brightnessS.setMajorTickSpacing(25);
		brightnessS.setPaintTicks(true);
		brightnessS.setPaintLabels(true);
		blurS.addChangeListener(this);
		saturationS.addChangeListener(this);
		brightnessS.addChangeListener(this);

		image_adjuster.add(blurL);
		image_adjuster.add(blurS);
		image_adjuster.add(saturationL);
		image_adjuster.add(saturationS);
		image_adjuster.add(brightnessL);
		image_adjuster.add(brightnessS);
	}


	public static Picture Blur(Picture picture){
		Picture pic = new PictureImpl(picture.getWidth(),picture.getHeight());
		int amount = blurS.getValue();
		for(int i = 0; i < picture.getWidth();i++){
			for(int j = 0; j < picture.getHeight();j++){
				double redTotal = 0;
				double greenTotal = 0;
				double blueTotal = 0;
				int count = 0;
				if (amount == 0) {
					return picture;
				}
				for(int k = 1; k <= amount;k++){
					if (i-k >= 0) {
						redTotal += picture.getPixel(i-k, j).getRed();
						greenTotal += picture.getPixel(i-k, j).getGreen();
						blueTotal += picture.getPixel(i-k, j).getBlue();
						count++;
					}
					if (i+k < picture.getWidth()) {
						redTotal += picture.getPixel(i+k, j).getRed();
						greenTotal += picture.getPixel(i+k, j).getGreen();
						blueTotal += picture.getPixel(i+k, j).getBlue();
						count++;
					}
					if (j-k >= 0) {
						redTotal += picture.getPixel(i, j-k).getRed();
						greenTotal += picture.getPixel(i, j-k).getGreen();
						blueTotal += picture.getPixel(i, j-k).getBlue();
						count++;
					}
					if (j+k < picture.getHeight()) {
						redTotal += picture.getPixel(i, j+k).getRed();
						greenTotal += picture.getPixel(i, j+k).getGreen();
						blueTotal += picture.getPixel(i, j+k).getBlue();
						count++;
					}
				}
				pic.setPixel(i, j, new ColorPixel((redTotal/count), (greenTotal/count), (blueTotal/count)));;
			}
		}
		return pic;
	}
	public static Picture Brightness(Picture picture){
		Picture pic = new PictureImpl(picture.getWidth(),picture.getHeight());
		
		if(saturationS.getValue() != 0){
			
		}
		double amount = ((double)brightnessS.getValue() / 100.0);
		ColorPixel pix;
		Pixel light;
		Pixel dark;
		for(int i = 0; i < picture.getWidth();i++){
			for(int j = 0; j < picture.getHeight();j++){
				double red = picture.getPixel(i, j).getRed();
				double green = picture.getPixel(i, j).getGreen();
				double blue = picture.getPixel(i, j).getBlue();
				pix = new ColorPixel(red, green, blue);
				if(amount > 0.0) {
					light = pix.lighten(amount);
					pic.setPixel(i, j, light);
				} else if(amount < 0.0) {
					dark = pix.darken(Math.abs(amount));
					pic.setPixel(i, j, dark);
				} else {
					pic.setPixel(i, j, pix);	
				}
			}
		}
		return pic;
	}
	public Picture Saturation(Picture picture){
		Picture pic = new PictureImpl(picture.getWidth(),picture.getHeight());
		double factor = ((double)saturationS.getValue());
		ColorPixel pix;
		Pixel case1;
		Pixel case2;
		double a;

		for(int i = 0; i < picture.getWidth();i++){
			for(int j = 0; j < picture.getHeight();j++){
				double red = picture.getPixel(i, j).getRed();
				double green = picture.getPixel(i, j).getGreen();
				double blue = picture.getPixel(i, j).getBlue();
				pix = new ColorPixel(red, green, blue);
				if(factor < 0.0) {
					case1 = new ColorPixel(red * (1.0 + (factor / 100.0) ) - (pix.getIntensity() * factor / 100.0),green * (1.0 + (factor / 100.0) ) - (pix.getIntensity() * factor / 100.0),blue * (1.0 + (factor / 100.0) ) - (pix.getIntensity() * factor / 100.0));
					pic.setPixel(i, j, case1);
				} else if(factor > 0.0) {

					if(red >= green && red >= blue)
						a = red;
					else if(green >= red && green >= blue)
						a = green;
					else
						a = blue;	
					case2 = new ColorPixel(red * ((a + ((1.0 - a) * (factor / 100.0))) / a),green * ((a + ((1.0 - a) * (factor / 100.0))) / a),blue * ((a + ((1.0 - a) * (factor / 100.0))) / a));
					pic.setPixel(i, j, case2);
				} else {
					pic.setPixel(i, j, pix);	
				}

			}
		}
		return pic;

	}
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void stateChanged(ChangeEvent e) {
		changedPicture = Blur(picture);
		changedPicture = Saturation(changedPicture);
		changedPicture = Brightness(changedPicture);
		picture_view.setPicture(changedPicture.createObservable());
	}

}

