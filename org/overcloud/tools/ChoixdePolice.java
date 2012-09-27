package org.overcloud.tools;

import java.awt.* ;
import java.awt.event.* ;
import javax.swing.* ;
import javax.swing.event.* ;
 
@SuppressWarnings("serial")
class ChoixDePolice extends JDialog{
	public ChoixDePolice(final Font f){
		super((JFrame)null, "Choose the font", true);
		polices = new Polices(f, this);
		scroll_police = new JScrollPane(polices);
		add(scroll_police);
		polices.setter(f);
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				polices.setter(f);
			}
		});
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
 
	public Font fontGetter(){
		return polices.getter();
	}
	JScrollPane scroll_police; static Polices polices;
}
 
	@SuppressWarnings("serial")
class Polices extends JPanel implements ActionListener{
		public Polices(Font f, JDialog appel){
			arg0 = f;
			arg1 = appel;
 
			exemple = new JTextField("It is the better font ?", 24);
			exemple.setEditable(false);
 
			tailles = new String[]{"6","8","10","11","12","14","16","18","20","22","24","26","28","30","32","34","36","40","44","48"};
			styles = new String[]{"Plain", "Bold", "Italic", "Bold and Italic"};
			polices = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
 
			fonte = new Vertical(0, polices, false);
			style = new Vertical(0, styles, false);
			size = new Vertical(0, tailles, true);
 
			annuler = new JButton("Cancel");
			annuler.addActionListener(this);
			ok = new JButton("OK");
			ok.addActionListener(this);
 
			prov_valid = Box.createHorizontalBox();
			prov_valid.add(annuler);
			prov_valid.add(Box.createHorizontalStrut(5));
			prov_valid.add(ok);
 
			entier = Box.createVerticalBox();
 
			valid = Box.createHorizontalBox();
			valid.add(Box.createGlue());
			valid.add(prov_valid);

			settings = Box.createHorizontalBox();
			settings.add(fonte);
			settings.add(Box.createHorizontalStrut(5));
			settings.add(style);
			settings.add(Box.createHorizontalStrut(5));
			settings.add(size);
 
			entier.add(settings);
			entier.add(Box.createVerticalStrut(5));
			entier.add(exemple);
			entier.add(Box.createVerticalStrut(5));
			entier.add(valid);
 
			setter(f);
			exemple();
 
			size.resultat.addActionListener(this);
 
			add(entier);
		}
 
		public void setter(Font f){
			fonte.resultat.setText(f.getName());
			size.resultat.setText(String.valueOf(f.getSize()));
			int prov = f.getStyle();
			String prov2 = "";
			if(prov==Font.PLAIN){prov2 = "Plain";}
			else if(prov==Font.BOLD){prov2 = "BOLD";}
			else if(prov==Font.ITALIC){prov2 = "Italic";}
			else if(prov==Font.BOLD+Font.ITALIC){prov2 = "Bold and Italic";}
			style.resultat.setText(prov2);
		}
 
		public Font getter(){
			String prov = style.resultat.getText();
			if(prov.equals("Plain")){
				styl = Font.PLAIN;
			}
			else if(prov.equals("Bold")){
				styl = Font.BOLD;
			}
			else if(prov.equals("Italic")){
				styl = Font.ITALIC;
			}
			else if(prov.equals("Bold and Italic")){
				styl = Font.BOLD+Font.ITALIC;
			}
			if(size.resultat.getText().equals("")==false){
				font = new Font(fonte.resultat.getText(), styl, Integer.parseInt(size.resultat.getText()));
				return font;
			}
			else{
				JOptionPane.showMessageDialog(null, "You must choose a size !!", "Error", JOptionPane.ERROR_MESSAGE);
				return null;
			}
		}
 
		public void exemple(){
			Font prov_font = getter();
			if(prov_font != null){
				exemple.setFont(prov_font);
			}
			else{
				System.out.println("Error : Couldn't show sample");}
		}
 
		public void actionPerformed(ActionEvent e){
			if(e.getSource()==ok){
				arg1.setVisible(false);
			}
			else if(e.getSource()==annuler){
				setter(arg0);
				arg1.setVisible(false);
			}
			else if(e.getSource()==size.resultat){
				exemple();
			}
		}
 
		String [] tailles, polices, styles; Box settings, entier, valid, prov_valid; Vertical fonte, style, size;
		Font font; int styl; JTextField exemple; JButton ok, annuler; Font arg0; JDialog arg1;
}
 
@SuppressWarnings("serial")
class Vertical extends Box implements ListSelectionListener{
	public Vertical(int i, String[] contenu, boolean edition){
		super(1);
		resultat = new JTextField();
		resultat.setEditable(edition);
		choix = new JList<String>(contenu);
		choix.setSelectionMode(0);
		choix.setVisibleRowCount(10);
		choix.addListSelectionListener(this);
		defil = new JScrollPane(choix);
 
		add(resultat);
		add(Box.createVerticalStrut(3));
		add(defil);
	}
 
	public void valueChanged(ListSelectionEvent e){
		if(!e.getValueIsAdjusting()){
			resultat.setText((String)choix.getSelectedValue());
			ChoixDePolice.polices.exemple();
		}
	}
	JTextField resultat; JList<String> choix; JScrollPane defil;
}


