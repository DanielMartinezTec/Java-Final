import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;



public class MainPanel extends JPanel implements ActionListener {
	private int modo;
	private boolean remove;
	
	private JButton mSencillo;
	
	private JLabel titulo,
					background;
	private ImageIcon fondo;
	
 	public MainPanel() {
		super();
		this.setPreferredSize(new Dimension(400,500));
		//this.setBounds(0, 0, 350, 420);
		this.setLayout(null);
		this.setBackground(new Color(199, 188, 206 ));

		this.modo=0;
		this.remove=false;
		
		this.titulo=new JLabel();
		this.titulo.setText("Problema del vendedor viajero");
		this.titulo.setBounds(75, 100, 300, 50);
		this.titulo.setFont(new Font("Times",20,20));
		this.titulo.setForeground(Color.WHITE);
		this.titulo.setVisible(true);
		this.add(titulo);
		
		this.mSencillo=new JButton("Viajero sencillo");
		this.mSencillo.setName("modoSencillo");
		this.mSencillo.setBounds(125, 160, 150, 30);
		this.mSencillo.addActionListener(this);
		this.add(mSencillo);

 	}
 	public int getModo() {
 		return this.modo;
 	}
 	public boolean getRemove() {
 		System.out.checkError();
 		return this.remove;
 	}
 	public void dissapear() {
 		this.titulo.setVisible(false);
 		this.mSencillo.setVisible(false);
 	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() instanceof JButton&&((JButton)e.getSource()).getName().equals("modoSencillo")){
			this.modo=1;
			this.remove=true;
	
		}
	}
}