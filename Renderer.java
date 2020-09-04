import javax.swing.*;
import java.awt.*;

public class Renderer extends JPanel{
	private static final long serialVersionUID = 1L;
	ImprovedNoise noise;

	Timer timer = new Timer(30, e -> repaint());
	public Renderer(){

		noise = new ImprovedNoise();
		timer.start();
	}
	protected void paintComponent(Graphics g){
		super.paintComponent(g);

		g.drawImage(noise.getNoiseImage(), 0, 0, this);
	}
}
