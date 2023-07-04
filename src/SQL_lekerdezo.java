import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import com.spire.data.table.DataTable;
import com.spire.xls.Workbook;
import com.spire.xls.Worksheet;
import javax.swing.JSeparator;
import java.awt.Color;
import java.awt.Font;



public class SQL_lekerdezo
{
	private JFrame frame;
	private JFileChooser fc;
	private String osszefuzott;
	private String osszefuzott2;
	private String osszefuzott3;
	private String osszefuzott4;
	private JButton megnyit;
	private JButton reszleges;
	private JButton start;
	private File menteshelye = new File(System.getProperty("user.home") + "\\Desktop\\Eredmények.xlsx");
	private static Long timer_start;
	private JButton like;
	private JRadioButton panelszam;
	private JRadioButton szeriaszam;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					SQL_lekerdezo window = new SQL_lekerdezo();
					window.frame.setVisible(true);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});	
	}

	/**
	 * Create the application.
	 */
	public SQL_lekerdezo() 
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 699, 415);
		frame.setPreferredSize(new Dimension(1024, 768));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setTitle("SQL keresés   V1.2");
		
		start = new JButton("Start");
		start.setBounds(470, 48, 89, 23);
		start.addActionListener(new SQLKereses());
		
		megnyit = new JButton("F\u00E1jl megniyt\u00E1sa");
		megnyit.setBounds(178, 48, 114, 23);
		megnyit.addActionListener(new Megnyitas());
		
		fc = new JFileChooser();
		fc.setCurrentDirectory(new java.io.File(System.getProperty("user.home") + "\\Desktop\\"));
		reszleges = new JButton("R\u00E9szleges panel");
		reszleges.setBounds(248, 161, 128, 23);
		reszleges.addActionListener(new Reszlegessen());
		
		like = new JButton("Like");
		like.setBounds(571, 161, 89, 23);
		like.addActionListener(new SQLReszlegesKereses());
		
		JLabel lblNewLabel = new JLabel("F\u00E1jl megnyit\u00E1sa:");
		lblNewLabel.setBounds(66, 52, 102, 14);
		
		JLabel lblNewLabel_1 = new JLabel("R\u00E9szleges panelsz\u00E1m megniyt\u00E1s:");
		lblNewLabel_1.setBounds(66, 165, 172, 14);
		
		JLabel lblNewLabel_2 = new JLabel("Norm\u00E1l start");
		lblNewLabel_2.setBounds(363, 52, 89, 14);
		
		JLabel lblNewLabel_3 = new JLabel("R\u00E9szleges panelsz\u00E1m keres\u00E9s");
		lblNewLabel_3.setBounds(386, 165, 173, 14);
		
		JLabel lblNewLabel_5 = new JLabel("Mire keresel?");
		lblNewLabel_5.setBounds(290, 260, 86, 14);
		
		panelszam = new JRadioButton("Panelsz\u00E1m");
		panelszam.setBounds(290, 290, 102, 23);
		panelszam.setSelected(true);
		
		szeriaszam = new JRadioButton("Sz\u00E9riasz\u00E1m");
		szeriaszam.setBounds(290, 323, 102, 23);
		
		ButtonGroup csoport = new ButtonGroup();
		csoport.add(szeriaszam);
		csoport.add(panelszam);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(megnyit);
		frame.getContentPane().add(lblNewLabel);
		frame.getContentPane().add(start);
		frame.getContentPane().add(lblNewLabel_1);
		frame.getContentPane().add(reszleges);
		frame.getContentPane().add(lblNewLabel_3);
		frame.getContentPane().add(like);
		frame.getContentPane().add(lblNewLabel_2);
		frame.getContentPane().add(lblNewLabel_5);
		frame.getContentPane().add(panelszam);
		frame.getContentPane().add(szeriaszam);
		
		JLabel lblNewLabel_4 = new JLabel("Normál keresés");
		lblNewLabel_4.setForeground(Color.BLUE);
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_4.setBounds(66, 11, 141, 14);
		frame.getContentPane().add(lblNewLabel_4);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(23, 109, 633, 14);
		frame.getContentPane().add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(23, 225, 633, 14);
		frame.getContentPane().add(separator_1);
		
		JLabel lblNewLabel_6 = new JLabel("Részleges panelszám keresés");
		lblNewLabel_6.setForeground(Color.BLUE);
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_6.setBounds(66, 134, 226, 14);
		frame.getContentPane().add(lblNewLabel_6);
	}
	
	class SQLKereses implements ActionListener																						//keresÅ‘ gom megnoymÃ¡skor hÃ­vodik meg
	{
		public void actionPerformed(ActionEvent e)
		 {		
			try 
			{
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));				
				SQL kiiro = new SQL();
				
				if(panelszam.isSelected())
				{
					kiiro.kiir(osszefuzott, osszefuzott2, osszefuzott3, osszefuzott4, menteshelye);	
				}
				else
				{
					kiiro.szeriaszam(osszefuzott, menteshelye);
				}
				
			}
			catch (Exception e1) 
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
				String hibauzenet2 = e1.toString();
				JOptionPane.showMessageDialog(null, hibauzenet2, "Hiba üzenet", 2);
			}
			frame.setCursor(null);
		}	 
	}
	
	class SQLReszlegesKereses implements ActionListener																						//keresÅ‘ gom megnoymÃ¡skor hÃ­vodik meg
	{
		public void actionPerformed(ActionEvent e)
		 {		
			try 
			{				
				SQL kiiro = new SQL();
				kiiro.reszleges(osszefuzott, menteshelye);
			}
			catch (Exception e1) 
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
				String hibauzenet2 = e1.toString();
				JOptionPane.showMessageDialog(null, hibauzenet2, "Hiba üzenet", 2);
			}
			}	 
	}
	
	
	
	class Megnyitas implements ActionListener																		//megnyitÃ³ osztÃ¡ly
	{
		public void actionPerformed(ActionEvent e)
		 {
			try
			{				
				//Task task = new Task();
		        //task.addPropertyChangeListener(new Figyelo());
		        //task.execute();
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				if (e.getSource() == megnyit) 
				{
					osszefuzott = "";
					osszefuzott2 = "";
					osszefuzott3 = "";
					osszefuzott4 = "";
					
					int returnVal = fc.showOpenDialog(frame);														//fÃ¡jl megniytÃ¡sÃ¡nak adbalak megnyit
		 
					if (returnVal == JFileChooser.APPROVE_OPTION) 
					{
						File file = fc.getSelectedFile();															//fÃ¡jl vÃ¡ltozÃ³ megkpja azt a fÃ¡jlt amit kivÃ¡lsztottunk a filechooserrel

						measureTime(true);																			//idÅ‘mÃ©rÅ‘ indÃ­tÃ¡sa
		            	
						Workbook excel = new Workbook();
						excel.loadFromFile(file.getAbsolutePath());
						Worksheet tabla = excel.getWorksheets().get(0);
						DataTable dataTable;
						dataTable = tabla.exportDataTable(tabla.getAllocatedRange(), false, false );
							
							int szam = 0;
									
							while (szam < dataTable.getRows().size())                 
							{  
															
								if(szam < 10000)
								{
									
									osszefuzott += ("\"" + dataTable.getRows().get(szam).getString(0) +"\",");							//cella tartalmÃ¡t Ã¶sszefÅ±zi egy stiringÃ©, hogy az elejÃ©re Ã©s a vÃ©gÃ©re tesz idÃ©zÅ‘jelet illetve egy vesszÅ±t a vÃ©gÃ©re
								}
								else if(szam >= 10000 && szam < 20000)
								{
									
									osszefuzott2 += ("\"" + dataTable.getRows().get(szam).getString(0) +"\",");							//cella tartalmÃ¡t Ã¶sszefÅ±zi egy stiringÃ©, hogy az elejÃ©re Ã©s a vÃ©gÃ©re tesz idÃ©zÅ‘jelet illetve egy vesszÅ±t a vÃ©gÃ©re				
								}
								else if(szam >= 20000  && szam < 30000)
								{
									
									osszefuzott3 += ("\"" + dataTable.getRows().get(szam).getString(0) +"\",");							//cella tartalmÃ¡t Ã¶sszefÅ±zi egy stiringÃ©, hogy az elejÃ©re Ã©s a vÃ©gÃ©re tesz idÃ©zÅ‘jelet illetve egy vesszÅ±t a vÃ©gÃ©re				
								}
								else
								{
									
									osszefuzott4 += ("\"" + dataTable.getRows().get(szam).getString(0) +"\",");							//cella tartalmÃ¡t Ã¶sszefÅ±zi egy stiringÃ©, hogy az elejÃ©re Ã©s a vÃ©gÃ©re tesz idÃ©zÅ‘jelet illetve egy vesszÅ±t a vÃ©gÃ©re				
								}
								
								szam++;
									
								
								 
							}
		            	
		            	osszefuzott = osszefuzott.substring(0, osszefuzott.length() - 1);							//az utolsÃ³ vesszÅ‘ levÃ¡gÃ¡sa a stringrÅ‘l
		            	if(osszefuzott2 != "")
		            	{	
		            		osszefuzott2 = osszefuzott2.substring(0, osszefuzott2.length() - 1);
		            	}
		            	
		            	if(osszefuzott3 != "")
		            	{	
		            		osszefuzott3 = osszefuzott3.substring(0, osszefuzott3.length() - 1);
		            	}
		            	
		            	if(osszefuzott4 != "")
		            	{	
		            		osszefuzott4 = osszefuzott4.substring(0, osszefuzott4.length() - 1);
		            	}
		            	
		            	System.out.println("Az összefűzés ideje: " + (measureTime(false) / 1000000) + "ms");
		            	
					} 
				}
				JOptionPane.showMessageDialog(null, "Összefűzés kész", "Tájékoztató üzenet", 1);		//String Ã¶sszefÅ±zÃ©s vÃ©gÃ©n  vÃ©gÃ©n megjelenÅ‘ Ã¼zenet
			}
			catch(Exception e1)
			{
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "Olvasási hiba történt", "Hiba üzenet", 2);
			}
			frame.setCursor(null);
		 }
	}
	
	class Reszlegessen implements ActionListener																		//megnyitÃ³ osztÃ¡ly
	{
		public void actionPerformed(ActionEvent e)
		 {
			try
			{
				if (e.getSource() == reszleges) 
				{
					osszefuzott = "";
					osszefuzott2 = "";
					int returnVal = fc.showOpenDialog(frame);														//fÃ¡jl megniytÃ¡sÃ¡nak adbalak megnyit
		 
					if (returnVal == JFileChooser.APPROVE_OPTION) 
					{
						File file = fc.getSelectedFile();															//fÃ¡jl vÃ¡ltozÃ³ megkpja azt a fÃ¡jlt amit kivÃ¡lsztottunk a filechooserrel
						
						measureTime(true);																			//idÅ‘mÃ©rÅ‘ indÃ­tÃ¡sa
						Workbook excel = new Workbook();
						excel.loadFromFile(file.getAbsolutePath());
						Worksheet tabla = excel.getWorksheets().get(0);
						DataTable dataTable;
						dataTable = tabla.exportDataTable(tabla.getAllocatedRange(), false, false );   													
						int szam = 0;	
						while (szam < dataTable.getRows().size())                 
						{  	
							osszefuzott += ("panel like \"" + dataTable.getRows().get(szam).getString(0) +"%\" or ");							//cella tartalmÃ¡t Ã¶sszefÅ±zi egy stiringÃ©, hogy az elejÃ©re Ã©s a vÃ©gÃ©re tesz idÃ©zÅ‘jelet illetve egy vesszÅ±t a vÃ©gÃ©re		
							szam++;
						}
		            	osszefuzott = osszefuzott.substring(0, osszefuzott.length() - 3);							//az utolsÃ³ vesszÅ‘ levÃ¡gÃ¡sa a stringrÅ‘l
		            	System.out.println("Az összefűzés ideje: " + (measureTime(false) / 1000000) + "ms");
					} 
					
		 
				}
				//System.out.println(osszefuzott);
				JOptionPane.showMessageDialog(null, "Összefűzés kész", "Info", 1);		//String Ã¶sszefÅ±zÃ©s vÃ©gÃ©n  vÃ©gÃ©n megjelenÅ‘ Ã¼zenet
			}
			catch(Exception e1)
			{
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "Olvasási hiba történt", "Hiba üzenet", 2);
			}
		 }		
	}
	
	static public float measureTime(boolean run)					//idÃµmÃ©rÃµ metÃ³dus
	{
		long current_time = System.nanoTime();						//a rendszeridÃµt nekiadjuk egy vÃ¡ltozÃ³nak
				
		if (run == true)											//ha igazra Ã¡llÃ­tjuk elindul
		{
				timer_start = System.nanoTime();					//idÃµzÃ­tÃµ indulÃ¡si Ã©rtÃ©ke a rendszer aktuÃ¡lis ideje
				return (-1.0f);
		}
		else
		{
			long elapsed_time = current_time - timer_start;			//ha false lesz az Ã©rtÃ©k
			return (elapsed_time);									//visszatÃ©r a kÃ¼lÃ¶nbsÃ©ggel
		}
	}
}
