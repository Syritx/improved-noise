import javax.swing.JFrame;

public class WindowDisplay extends JFrame {

    private static final long serialVersionUID = 1L;

    static int WIDTH = 1000;
	static int HEIGHT = 720;
    Renderer panel = new Renderer();
    
	public WindowDisplay(){
        add(panel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WIDTH, HEIGHT);
		setVisible(true);
	}
}
