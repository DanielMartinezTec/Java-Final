import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Ventana extends JFrame{
	
	public Ventana() {
		super("Travelling Salesman Problem");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(400,500));
		this.setVisible(true);
		this.pack();
	}
	public static void main(String[]args) {
		Ventana tsp=new Ventana();
		MainPanel main=new MainPanel();
		ViajeroSencillo vs=new ViajeroSencillo();
		tsp.add(main);
		while(!main.getRemove());
		main.setVisible(false);
		tsp.add(vs);
	}
}