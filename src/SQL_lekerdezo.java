import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import com.spire.data.table.DataTable;
import com.spire.xls.Workbook;
import com.spire.xls.Worksheet;

import javax.swing.JProgressBar;
import javax.swing.SwingWorker;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.JRadioButton;



public class SQL_lekerdezo implements ActionListener
{
	private JFrame frame;
	private JFileChooser fc;
	private String osszefuzott;
	private String osszefuzott2;
	private String osszefuzott3;
	private String osszefuzott4;
	private JButton megnyit;
	private JButton mentes;
	private JButton reszleges;
	private JButton start;
	private File menteshelye;
	private static Long timer_start;
	private JButton like;
	private JRadioButton panelszam;
	private JRadioButton szeriaszam;
	static JProgressBar progressBar;
	static int szazalek = 0;
	
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
		frame.setTitle("SQL keresés");
		
		start = new JButton("Start");
		start.addActionListener(new SQLKereses());
		
		megnyit = new JButton("F\u00E1jl megniyt\u00E1sa");
		megnyit.addActionListener(new Megnyitas());
		
		fc = new JFileChooser();
		
		mentes = new JButton("Ment\u00E9s helye");
		mentes.addActionListener(new Mentes());
		
		reszleges = new JButton("R\u00E9szleges panel");
		reszleges.addActionListener(new Reszlegessen());
		
		like = new JButton("Like");
		like.addActionListener(new SQLReszlegesKereses());
		
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setValue(0);
		
		JLabel lblNewLabel = new JLabel("F\u00E1jl megnyit\u00E1sa:");
		
		JLabel lblNewLabel_1 = new JLabel("R\u00E9szleges panelsz\u00E1m megniyt\u00E1s:");
		
		JLabel lblNewLabel_2 = new JLabel("Norm\u00E1l start");
		
		JLabel lblNewLabel_3 = new JLabel("R\u00E9szleges panelsz\u00E1m keres\u00E9s");
		
		JLabel lblNewLabel_4 = new JLabel("Ment\u00E9s helye:");
		
		JLabel lblNewLabel_5 = new JLabel("Mire keresel?");
		
		panelszam = new JRadioButton("Panelsz\u00E1m");
		panelszam.setSelected(true);
		
		szeriaszam = new JRadioButton("Sz\u00E9riasz\u00E1m");
		
		ButtonGroup csoport = new ButtonGroup();
		csoport.add(szeriaszam);
		csoport.add(panelszam);
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(51)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(megnyit, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel))
					.addPreferredGap(ComponentPlacement.RELATED, 100, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(start, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_1)
								.addComponent(reszleges, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_3)
								.addComponent(like, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_2))
							.addGap(107)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(mentes, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_4)
								.addComponent(lblNewLabel_5)
								.addComponent(panelszam)
								.addComponent(szeriaszam))))
					.addGap(70))
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addGap(233)
					.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(304, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(37)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(lblNewLabel_4)
						.addComponent(lblNewLabel_1))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(megnyit)
						.addComponent(mentes)
						.addComponent(reszleges))
					.addGap(37)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_5)
						.addComponent(lblNewLabel_3))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(panelszam)
						.addComponent(like))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(36)
							.addComponent(lblNewLabel_2)
							.addGap(18)
							.addComponent(start))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(18)
							.addComponent(szeriaszam)))
					.addGap(32)
					.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
					.addGap(96))
		);
		frame.getContentPane().setLayout(groupLayout);
	}
	
	class SQLKereses implements ActionListener																						//keresÅ‘ gom megnoymÃ¡skor hÃ­vodik meg
	{
		public void actionPerformed(ActionEvent e)
		 {		
			try 
			{
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				Task task = new Task();
				task.addPropertyChangeListener(new Figyelo());
		        task.execute();
				szazalek = 0;
				if(menteshelye == null)
				{
					
					JOptionPane.showMessageDialog(null, "Nincs kiválasztva a mentés helye", "Hiba üzenet", 2);
					return;
				}
				
				
				
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
				if(menteshelye == null)
				{
					JOptionPane.showMessageDialog(null, "Nincs kiválasztva a mentés helye", "Hiba üzenet", 2);
					return;
				}
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
				progressBar.setIndeterminate(true);
				Task task = new Task();
		        task.addPropertyChangeListener(new Figyelo());
		        task.execute();
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
						
						szazalek = 10;
						
						
						measureTime(true);																			//idÅ‘mÃ©rÅ‘ indÃ­tÃ¡sa
		            	
						Workbook excel = new Workbook();
						excel.loadFromFile(file.getAbsolutePath());
						Worksheet tabla = excel.getWorksheets().get(0);
						DataTable dataTable;
						dataTable = tabla.exportDataTable(tabla.getAllocatedRange(), false, false );
						
							szazalek = 35;
							
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
							szazalek = 90;
						
		            	
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
				progressBar.setValue(100);
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
	
	class Task extends SwingWorker<Void, Void> 
	{
        /*
         * Main task. Executed in background thread.
         */
        @Override
        public Void doInBackground() 
        {
            Random random = new Random();
            int progress = 0;
            //Initialize progress property.
            setProgress(0);
            while (progress < 100) 
            {
                //Sleep for up to one second.
                try 
                {
                    Thread.sleep(random.nextInt(1000));
                } 
                catch (InterruptedException ignore) {}
                //Make random progress.
                
                progress += random.nextInt(10);									//random.nextInt(10);
                setProgress(Math.min(progress, 100));
            }
            return null;
        }

        /*
         * Executed in event dispatching thread
         */
        @Override
        public void done() 
        {
            Toolkit.getDefaultToolkit().beep();
            start.setEnabled(true);
            //setCursor(null); //turn off the wait cursor
        }
    }
	
	class Figyelo implements PropertyChangeListener
	{
		public void propertyChange(PropertyChangeEvent evt)
		{
	        if ("progress" == evt.getPropertyName()) 
	        {
	        	progressBar.setIndeterminate(false);
	            int progress = (Integer) evt.getNewValue();
	            progressBar.setValue(progress);
	            
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
	
	class Mentes implements ActionListener																	//megnyitÃ³ osztÃ¡ly
	{
		public void actionPerformed(ActionEvent e)
		 {
			
		 
			try
			{
				if (e.getSource() == mentes) 
				{
					progressBar.setValue(0);
					int returnVal = fc.showOpenDialog(frame);														//fÃ¡jl megniytÃ¡sÃ¡nak adbalak megnyit
		 
					if (returnVal == JFileChooser.APPROVE_OPTION) 
					{
						menteshelye = fc.getSelectedFile();															//fÃ¡jl vÃ¡ltozÃ³ megkpja azt a fÃ¡jlt amit kivÃ¡lsztottunk a filechooserrel
					}
				}
				
			}
			catch (Exception e1) 
			{
				// TODO Auto-generated catch block
				String hibauzenet = e1.toString();  
                JOptionPane.showMessageDialog(null, hibauzenet, "Hiba üzenet", 2);
			}
		 }
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		// TODO Auto-generated method stub
		
	}
}
