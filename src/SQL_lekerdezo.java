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



public class SQL_lekerdezo 
{

	private JFrame frame;
	private JFileChooser fc;
	private String osszefuzott;
	private String osszefuzott2;
	//private String osszefuzott3 ="";
	private JButton megnyit;
	private JButton mentes;
	private JButton reszleges;
	private File menteshelye;
	private static Long timer_start;
	private JButton like;
	static JProgressBar progressBar;
	
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
		frame.setBounds(100, 100, 450, 300);
		frame.setPreferredSize(new Dimension(1024, 768));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);
		frame.setTitle("SQL kereső");
		
		JButton start = new JButton("Start");
		start.setBounds(51, 110, 89, 23);
		start.addActionListener(new SQLKereses());
		frame.getContentPane().add(start);
		
		megnyit = new JButton("Fájl megnyitás");
		megnyit.addActionListener(new Megnyitas());
		megnyit.setBounds(51, 37, 89, 23);
		
		fc = new JFileChooser();
		
		frame.getContentPane().add(megnyit);
		
		JButton csomagolt = new JButton("Csomagolt");
		csomagolt.setBounds(289, 110, 89, 23);
		csomagolt.addActionListener(new SQLKeresesCsomagolt());
		frame.getContentPane().add(csomagolt);
		
		mentes = new JButton("Mentés helye");
		mentes.setBounds(289, 37, 89, 23);
		mentes.addActionListener(new Mentes());
		frame.getContentPane().add(mentes);
		
		reszleges = new JButton("Részleges panel");
		reszleges.setBounds(164, 37, 89, 23);
		reszleges.addActionListener(new Reszlegessen());
		frame.getContentPane().add(reszleges);
		
		like = new JButton("Like");
		like.setBounds(164, 110, 89, 23);
		like.addActionListener(new SQLReszlegesKereses());
		frame.getContentPane().add(like);
		
		progressBar = new JProgressBar();
		progressBar.setBounds(135, 201, 146, 23);
		progressBar.setStringPainted(true);
		progressBar.setValue(0);
		frame.getContentPane().add(progressBar);
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
				kiiro.kiir(osszefuzott, osszefuzott2, menteshelye);
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
									if(szam < 8000)
									{
										Cell cell = cellIterator.next();
										osszefuzott += ("\"" + cell.getStringCellValue() +"\",");							//cella tartalmát összefűzi egy stiringé, hogy az elejére és a végére tesz idézőjelet illetve egy vesszűt a végére
									}
									else
									{
										Cell cell = cellIterator.next();
										osszefuzott2 += ("\"" + cell.getStringCellValue() +"\",");							//cella tartalmát összefűzi egy stiringé, hogy az elejére és a végére tesz idézőjelet illetve egy vesszűt a végére				
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
	
	class SQLKeresesCsomagolt implements ActionListener																						//kereső gom megnoymáskor hívodik meg
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
				kiiro.csomagolt(osszefuzott, osszefuzott2, menteshelye);
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
