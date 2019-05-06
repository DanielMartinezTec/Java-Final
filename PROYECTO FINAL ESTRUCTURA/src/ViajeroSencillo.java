import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.NumberFormat;
import java.util.Iterator;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;
 
public class ViajeroSencillo  extends JPanel implements ItemListener,ActionListener,MouseListener {
	private MiListaEnlazada<Arco> arcos;
	private MiListaEnlazada<Vertice>vertices;
	private MiGrafo mapa;
	private JLabel titulo,
					etiquetaP,
					etiquetaN;
	private JButton calcular;
	private JRadioButton vertice,
							arista;
	private JTextField peso,
						nombre; 
	private ButtonGroup b1;
	
	private int xTmp;
	private int yTmp;
	private NodoGF tmpInicio;
	private boolean vert,
					agregarV,
					agregarA,
					arcoIniciado;
	
	public ViajeroSencillo() {
		super();
		this.setPreferredSize(new Dimension(400,500));
		this.setLayout(null);
		this.setBackground(new Color(199, 188, 206 ));
		this.addMouseListener(this);
		this.agregarV=false;
		this.agregarA=false;
		this.vert=true;
		this.arcoIniciado=false;
		this.vertices=new MiListaEnlazada();
		this.arcos=new MiListaEnlazada();
		mapa=new MiGrafo();
		
		
		
		this.titulo=new JLabel();
		this.titulo.setText("Viajero Sencillo");
		this.titulo.setBounds(150, 25, 200, 50);
		this.titulo.setFont(new Font("Times",20,20));
		this.titulo.setForeground(Color.WHITE);
		this.titulo.setVisible(true);
		this.add(this.titulo);
		
		this.etiquetaP=new JLabel();
		this.etiquetaP.setText("Distancia:");
		this.etiquetaP.setBounds(130, 100, 80, 30);
		this.etiquetaP.setForeground(Color.WHITE);
		this.etiquetaP.setVisible(false);
		this.add(this.etiquetaP);
		this.etiquetaN=new JLabel();
		this.etiquetaN.setText("Nodo:");
		this.etiquetaN.setBounds(130, 70, 50, 30);
		this.etiquetaN.setForeground(Color.WHITE);
		this.etiquetaN.setVisible(false);
		this.add(this.etiquetaN);

		this.vertice=new JRadioButton("Vertice");
		this.vertice.setBounds(30, 70, 150, 30);
		this.vertice.setName("vertice");
		this.vertice.setForeground(Color.WHITE);
		this.add(this.vertice);
		this.vertice.addItemListener(this);
		
		this.arista=new JRadioButton("Arista");
		this.arista.setBounds(30, 100, 100, 30);
		this.arista.setName("arista");
		this.arista.setForeground(Color.WHITE);
		this.add(this.arista);
		this.arista.addItemListener(this);
		
		this.b1= new ButtonGroup();
		this.b1.add(this.vertice);
		this.b1.add(this.arista);
		
		this.calcular=new JButton("Calcular");
		this.calcular.setName("calcular");
		this.calcular.setBounds(275, 120, 100, 30);
		this.calcular.addActionListener(this);
		this.add(calcular);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		dibujo(g);
	}
	public void dibujo(Graphics g) {
		pintaPlano(g);
		if(vert&&agregarV) {
			Iterator<Arco> it2 = arcos.iterator();
			//NodoLE<NodoGF> current=this.mapa.getNodos().inicio;
			while(it2.hasNext()) {
				Arco tmp=it2.next();
				//Arco tmp=current.getDato().getLimites().inicio.getDato().getArco();
				g.setColor(Color.RED);
				g.drawLine(tmp.getX1(), tmp.getY1(), tmp.getX2(), tmp.getY2());
				//current=current.getNext();
			}
			NodoLE<NodoGF> current=this.mapa.getNodos().inicio;
			g.setColor(Color.black);
			while(current!=null) {
				Vertice tmp=current.getDato().getLocalizacion();
				g.fillOval(tmp.getX(),tmp.getY(), 16, 16);
				current=current.getNext();
			}
			System.out.println("Entro "+vertices.size());
			this.agregarV=false;
		}
		else if(!vert&&this.agregarA){
			Iterator<Arco> it2 = arcos.iterator();
			g.setColor(Color.RED);
			while(it2.hasNext()) {
				Arco tmp=it2.next();
				g.drawLine(tmp.getX1(), tmp.getY1(), tmp.getX2(), tmp.getY2());
			}
			NodoLE<NodoGF> current=this.mapa.getNodos().inicio;
			g.setColor(Color.black);
			while(current!=null) {
				Vertice tmp=current.getDato().getLocalizacion();
				g.fillOval(tmp.getX(),tmp.getY(), 16, 16);
				current=current.getNext();
			}
			this.agregarA=false;
			System.out.println("Linea "+this.vertices.size());
		}		
	}
	public void pintaPlano(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawRect(30, 150, 340, 300);
		g.setColor(Color.WHITE);
		g.fillRect(31, 151, 338, 298);
	}
	public void createField() {
		NumberFormat format = NumberFormat.getInstance();
		NumberFormatter formatter = new NumberFormatter(format);
		formatter.setValueClass(Integer.class);
		formatter.setMinimum(0);
		formatter.setMaximum(Integer.MAX_VALUE);
		formatter.setAllowsInvalid(false);
		formatter.setCommitsOnValidEdit(true);
		this.peso=new JFormattedTextField(formatter);
		this.peso.setName("peso");
		this.peso.setBounds(210, 100, 60, 30);
		this.add(this.peso);
		
		this.nombre=new JTextField();
		this.nombre.setName("nombre");
		this.nombre.setBounds(190,70,100,30);
		this.add(this.nombre);
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int x=e.getX();
		int y=e.getY();
		if(vert&&030<x&&x<370&&150<y&&y<450&&this.vertice.isSelected()) {
			vertices.insertarFin(new Vertice(x,y));
			this.mapa.addNodo(new NodoGF(nombre.getText(),new Vertice(x,y)));
			System.out.println(this.mapa.getNodos().fin.getDato());
			agregarV=true;
			repaint();
		}
		else if(!vert&&030<x&&x<370&&150<y&&y<450&&this.arista.isSelected()) {
			Iterator<Vertice> it = vertices.iterator();
			NodoLE<NodoGF> current=this.mapa.getNodos().inicio;
			while(it.hasNext()) {
				Vertice tmp=it.next();
				if(tmp.getX()<x&&x<tmp.getX()+16&&tmp.getY()<y&&y<tmp.getY()+16&&this.arcoIniciado==false) {
					this.tmpInicio=current.getDato();
					this.arcoIniciado=true;
					this.xTmp=x;
					this.yTmp=y;
					System.out.println("Capturado Inicio");
				}
				else if(tmp.getX()<x&&x<tmp.getX()+16&&tmp.getY()<y&&y<tmp.getY()+16&&this.arcoIniciado==true) {
					this.tmpInicio.addLimite(new Limite(this.tmpInicio,current.getDato(),Double.parseDouble(this.peso.getText()),new Arco(xTmp,yTmp,x,y)));
					this.arcoIniciado=false;
					this.arcos.insertarFin(new Arco(xTmp,yTmp,x,y));
					this.agregarA=true;
					System.out.println("Capturado Final");
					System.out.println(this.tmpInicio.getLimites());
					repaint();
				}
				current=current.getNext();
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
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void actionPerformed(ActionEvent e,int ee) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		JRadioButton tmp=(JRadioButton)e.getSource();
		if(this.peso==null&&this.nombre==null) {
			createField();
		}
		if(tmp.getName().equals("vertice")&&this.vertice.isSelected()) {
			this.vert=true;
			this.nombre.setText("Nombre");
			this.nombre.setVisible(true);
			this.etiquetaN.setVisible(true);
			this.peso.setText("1");
			this.peso.setVisible(false);
			this.etiquetaP.setVisible(false);
		}
		else if(tmp.getName().equals("arista")&&this.arista.isSelected()) {
			this.vert=false;
			this.nombre.setText("Nombre");
			this.nombre.setVisible(false);
			this.etiquetaN.setVisible(false);
			this.peso.setText("1");
			this.peso.setVisible(true);		
			this.etiquetaP.setVisible(true);
			
			
		}
	}
	
}
