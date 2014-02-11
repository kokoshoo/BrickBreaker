package breaker;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BreakerMain applet = new BreakerMain();
		applet.init();
		applet.start();
		
		JLabel label = new JLabel();
		Container container = new Container();
		String text = new String();
		JPanel panel = new JPanel(new BorderLayout());
		
		
		text = "Controls: Use the arrow keys up down left and right.";

		
		applet.setPreferredSize(applet.getSize());
		
		label.setText(text);
		

		
		panel.setLayout(new BorderLayout());
		
		panel.add(applet, BorderLayout.LINE_START);
		
		panel.add(label, BorderLayout.AFTER_LAST_LINE);
		
		JFrame window = new JFrame("Brick Breaker");
		window.setContentPane(panel);
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		window.setPreferredSize(new Dimension(500,450));
		
		window.pack();
		window.setVisible(true);
	}

}
