import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import javax.swing.JProgressBar;
import javax.swing.ProgressMonitorInputStream;
import javax.swing.SwingUtilities;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.JRadioButton;



public class SQL_lekerdezo 
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
	private File menteshelye;
	private static Long timer_start;
	private JButton like;
	private JRadioButton panelszam;
	private JRadioButton szeriaszam;
	static JProgressBar progressBar;
	static int szazalek = 50;
	
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
		frame.setTitle("SQL kereső");
		
		JButton start = new JButton("Start");
		start.addActionListener(new SQLKereses());
		
		megnyit = new JButton("Fájl megnyitás");
		megnyit.addActionListener(new Megnyitas());
		
		fc = new JFileChooser();
		
		mentes = new JButton("Mentés helye");
		mentes.addActionListener(new Mentes());
		
		reszleges = new JButton("Részleges panel");
		reszleges.addActionListener(new Reszlegessen());
		
		like = new JButton("Like");
		like.addActionListener(new SQLReszlegesKereses());
		
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setValue(0);
		
		JLabel lblNewLabel = new JLabel("Fájl megniytása:");
		
		JLabel lblNewLabel_1 = new JLabel("Részleges panelszám megniytása:");
		
		JLabel lblNewLabel_2 = new JLabel("Normál start");
		
		JLabel lblNewLabel_3 = new JLabel("Részleges panelszám keresés");
		
		JLabel lblNewLabel_4 = new JLabel("Mentés helye:");
		
		JLabel lblNewLabel_5 = new JLabel("Mire keresel?");
		
		panelszam = new JRadioButton("Panelszám");
		panelszam.setSelected(true);
		
		szeriaszam = new JRadioButton("Szériaszám");
		
		ButtonGroup csoport = new ButtonGroup();
		csoport.add(szeriaszam);
		csoport.add(panelszam);
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addGap(51)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(megnyit, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED, 189, Short.MAX_VALUE)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_1)
								.addComponent(reszleges, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE))
							.addGap(107))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(93)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(like, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_3)
								.addComponent(lblNewLabel_2)
								.addComponent(start, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE))
							.addGap(130)))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(mentes, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_4)
						.addComponent(lblNewLabel_5)
						.addComponent(panelszam)
						.addComponent(szeriaszam))
					.addGap(70))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(212)
					.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(331, Short.MAX_VALUE))
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
						.addComponent(lblNewLabel_3)
						.addComponent(lblNewLabel_5))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(like)
						.addComponent(panelszam))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(36)
							.addComponent(lblNewLabel_2)
							.addGap(18)
							.addComponent(start)
							.addGap(37)
							.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(18)
							.addComponent(szeriaszam)))
					.addGap(41))
		);
		frame.getContentPane().setLayout(groupLayout);
	}
	
	class SQLKereses implements ActionListener																						//kereső gom megnoymáskor hívodik meg
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
				
				SwingUtilities.invokeLater(new Runnable() 
				{
				    public void run() 
				    {
				        //This will be called on the EDT
				    	progressBar.setValue(szazalek);
				    }
				});
				
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
			}	 
	}
	
	class SQLReszlegesKereses implements ActionListener																						//kereső gom megnoymáskor hívodik meg
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
	
	
	
	class Megnyitas implements ActionListener																		//megnyitó osztály
	{
		public void actionPerformed(ActionEvent e)
		 {
			try
			{
				if (e.getSource() == megnyit) 
				{
					osszefuzott = "";
					osszefuzott2 = "";
					osszefuzott3 = "";
					osszefuzott4 = "";
					
					int returnVal = fc.showOpenDialog(frame);														//fájl megniytásának adbalak megnyit
		 
					if (returnVal == JFileChooser.APPROVE_OPTION) 
					{
						File file = fc.getSelectedFile();															//fájl változó megkpja azt a fájlt amit kiválsztottunk a filechooserrel
						
						progressBar.setValue(10);
						
						
						measureTime(true);																			//időmérő indítása
		            	InputStream fis = new BufferedInputStream(
		            		    new ProgressMonitorInputStream(
		            		            frame,
		            		            "Reading " + file,
		            		            new FileInputStream(file)));											//inputstream osztály példányosítása
		            	try (XSSFWorkbook workbook = new XSSFWorkbook(fis)) 
		            	{
							XSSFSheet sheet = workbook.getSheetAt(0);
							Iterator<Row> itr = sheet.iterator();    													//interator példányosítása 
							progressBar.setValue(50);	
							
							int szam = 1;
									
							while (itr.hasNext())                 
							{  
								Row row = itr.next();  
								Iterator<Cell> cellIterator = row.cellIterator();   									//iterating over each column  
								while (cellIterator.hasNext())   
								{
									if(szam < 10000)
									{
										Cell cell = cellIterator.next();
										osszefuzott += ("\"" + cell.getStringCellValue() +"\",");							//cella tartalmát összefűzi egy stiringé, hogy az elejére és a végére tesz idézőjelet illetve egy vesszűt a végére
									}
									else if(szam >= 10000 && szam < 20000)
									{
										Cell cell = cellIterator.next();
										osszefuzott2 += ("\"" + cell.getStringCellValue() +"\",");							//cella tartalmát összefűzi egy stiringé, hogy az elejére és a végére tesz idézőjelet illetve egy vesszűt a végére				
									}
									else if(szam >= 20000  && szam < 30000)
									{
										Cell cell = cellIterator.next();
										osszefuzott3 += ("\"" + cell.getStringCellValue() +"\",");							//cella tartalmát összefűzi egy stiringé, hogy az elejére és a végére tesz idézőjelet illetve egy vesszűt a végére				
									}
									else
									{
										Cell cell = cellIterator.next();
										osszefuzott4 += ("\"" + cell.getStringCellValue() +"\",");							//cella tartalmát összefűzi egy stiringé, hogy az elejére és a végére tesz idézőjelet illetve egy vesszűt a végére				
									}
									
									szam++;
									
								}
								 
							}
							progressBar.setValue(90);
						}
		            	osszefuzott = osszefuzott.substring(0, osszefuzott.length() - 1);							//az utolsó vessző levágása a stringről
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
					progressBar.setValue(100);
		 
				}
				//System.out.println(osszefuzott);
				JOptionPane.showMessageDialog(null, "Összefűzés kész", "Tájékoztató Üzenet", 1);		//String összefűzés végén  végén megjelenő üzenet
			}
			catch(IOException e1)
			{
				JOptionPane.showMessageDialog(null, "Olvasási hiba történt", "Hibaüzenet", 2);
			}
		 }		
	}
	
	class Reszlegessen implements ActionListener																		//megnyitó osztály
	{
		public void actionPerformed(ActionEvent e)
		 {
			try
			{
				if (e.getSource() == reszleges) 
				{
					osszefuzott = "";
					osszefuzott2 = "";
					int returnVal = fc.showOpenDialog(frame);														//fájl megniytásának adbalak megnyit
					
					//fc.setCurrentDirectory(System.getProperty("user.home"));
				
		 
					if (returnVal == JFileChooser.APPROVE_OPTION) 
					{
						File file = fc.getSelectedFile();															//fájl változó megkpja azt a fájlt amit kiválsztottunk a filechooserrel
						
						measureTime(true);																			//időmérő indítása
		            	FileInputStream fis = new FileInputStream(file);											//inputstream osztály példányosítása
		            	try (XSSFWorkbook workbook = new XSSFWorkbook(fis)) {
							XSSFSheet sheet = workbook.getSheetAt(0);
							Iterator<Row> itr = sheet.iterator();    													//interator példányosítása 
							
							while (itr.hasNext())                 
							{  
								Row row = itr.next();  
								Iterator<Cell> cellIterator = row.cellIterator();   									//iterating over each column  
								while (cellIterator.hasNext())   
								{  
									Cell cell = cellIterator.next();
									osszefuzott += ("panel like \"" + cell.getStringCellValue() +"%\" or ");							//cella tartalmát összefűzi egy stiringé, hogy az elejére és a végére tesz idézőjelet illetve egy vesszűt a végére
									
								}  
								 
							}
						}
		            	osszefuzott = osszefuzott.substring(0, osszefuzott.length() - 3);							//az utolsó vessző levágása a stringről
		            	System.out.println("Az összefűzés ideje: " + (measureTime(false) / 1000000) + "ms");
					} 
					
		 
				}
				//System.out.println(osszefuzott);
				JOptionPane.showMessageDialog(null, "Összefűzés kész", "Tájékoztató Üzenet", 1);		//String összefűzés végén  végén megjelenő üzenet
			}
			catch(IOException e1)
			{
				JOptionPane.showMessageDialog(null, "Olvasási hiba történt", "Hibaüzenet", 2);
			}
		 }		
	}
	
	static public float measureTime(boolean run)					//idõmérõ metódus
	{
		long current_time = System.nanoTime();						//a rendszeridõt nekiadjuk egy változónak
				
		if (run == true)											//ha igazra állítjuk elindul
		{
				timer_start = System.nanoTime();					//idõzítõ indulási értéke a rendszer aktuális ideje
				return (-1.0f);
		}
		else
		{
			long elapsed_time = current_time - timer_start;			//ha false lesz az érték
			return (elapsed_time);									//visszatér a különbséggel
		}
	}
	
	class Mentes implements ActionListener																	//megnyitó osztály
	{
		public void actionPerformed(ActionEvent e)
		 {
			
		 
			try
			{
				if (e.getSource() == mentes) 
				{
					progressBar.setValue(0);
					int returnVal = fc.showOpenDialog(frame);														//fájl megniytásának adbalak megnyit
		 
					if (returnVal == JFileChooser.APPROVE_OPTION) 
					{
						menteshelye = fc.getSelectedFile();															//fájl változó megkpja azt a fájlt amit kiválsztottunk a filechooserrel
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
}
