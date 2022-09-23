import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import org.apache.poi.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import com.mysql.cj.jdbc.result.ResultSetMetaData;

public class SQL_lekerdezo 
{

	private JFrame frame;
	private JFileChooser fc;
	private String osszefuzott;
	private JButton megnyit;
	private JButton mentes;
	private JButton reszleges;
	private File megnyitottfajl;
	private File menteshelye;
	private static Long timer_start;
	private JButton like;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() {
				try {
					SQL_lekerdezo window = new SQL_lekerdezo();
					window.frame.setVisible(true);
				} catch (Exception e) {
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
		like.addActionListener(new ReszlegesKereses());
		frame.getContentPane().add(like);
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
				//Registering the Driver
				DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());														//jdbc mysql driver meghívása
				
		      //Getting the connection
		      String mysqlUrl = "jdbc:mysql://192.168.5.145/";																		//mysql szerver ipcíméhez való csatlakozás
		      Connection con = DriverManager.getConnection(mysqlUrl, "quality", "Qua25!");											//a megadott ip-re csatlakozik a jelszó felhasználó névvel
		      System.out.println("Connection established......");
		      
		      //Preparing a CallableStatement to call a procedure
		      CallableStatement cstmt = con.prepareCall("{call videoton.veas_avmheti_teszt(?)}");									//tárolt eljárás meghívása
		      
		      cstmt.setString(1, osszefuzott);																						//tárolt eljárás paparméterénk megadása
		      measureTime(true);
		      cstmt.execute();																										//sql lejkérdezés futtatása
		      
		      System.out.println("Az SQL lekérdezésének ideje: " + (measureTime(false) / 1000000) + "ms");
		      
		      System.out.println("Stored Procedure executed successfully");
		      
				ResultSet result2 = cstmt.getResultSet();																			//az sql lekérdezés tartalmát odaadja egy result set változónak

				XSSFWorkbook workbook = new XSSFWorkbook();																			//excel tipusú osztály létrehjozása
				XSSFSheet sheet = workbook.createSheet("Eredmények");																//excel osztályban egy tábla létrehozása a megadott névvel

				writeHeaderLine(sheet);																								//fejlécet lekészítő metódus meghívása

				writeDataLines(result2, workbook, sheet);																			//tábla tartalmát beírja	

				FileOutputStream outputStream = new FileOutputStream(menteshelye);		//file tipusú változó létrehozása a megadott helyen
				workbook.write(outputStream);																						//adatok kiírása egy fájlba amit elöbb megadtunk
				workbook.close();																									//adatofolyam lezárása
				outputStream.close();																								//fájl lezárása

				//statement.close();
				JOptionPane.showMessageDialog(null, "SQL lekérdezés kész", "Tájékoztató Üzenet", 1);								//String összefűzés végén  végén megjelenő üzenet
		     
			}
			 
			catch (SQLException e1) 
			{
				// TODO Auto-generated catch block
				String hibauzenet2 = e1.toString();
				JOptionPane.showMessageDialog(null, hibauzenet2, "Hiba üzenet", 2);
				
			} catch (EncryptedDocumentException e1) {
				// TODO Auto-generated catch block
				String hibauzenet2 = e1.toString();
				JOptionPane.showMessageDialog(null, hibauzenet2, "Hiba üzenet", 2);
				
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				String hibauzenet2 = e1.toString();
				JOptionPane.showMessageDialog(null, hibauzenet2, "Hiba üzenet", 2);
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				String hibauzenet2 = e1.toString();
				JOptionPane.showMessageDialog(null, hibauzenet2, "Hiba üzenet", 2);
				
			}
			}
		 
	}
	
	class ReszlegesKereses implements ActionListener																						//kereső gom megnoymáskor hívodik meg
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
				//Registering the Driver
				DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());														//jdbc mysql driver meghívása
				
		      //Getting the connection
		      String mysqlUrl = "jdbc:mysql://192.168.5.145/";																		//mysql szerver ipcíméhez való csatlakozás
		      Connection con = DriverManager.getConnection(mysqlUrl, "quality", "Qua25!");											//a megadott ip-re csatlakozik a jelszó felhasználó névvel
		      System.out.println("Connection established......");
		      
		      //Preparing a CallableStatement to call a procedure
		      CallableStatement cstmt = con.prepareCall("{call videoton.veas_reszleges_panelszam(?)}");									//tárolt eljárás meghívása
		      
		      cstmt.setString(1, osszefuzott);																						//tárolt eljárás paparméterénk megadása
		      measureTime(true);
		      cstmt.execute();																										//sql lejkérdezés futtatása
		      
		      System.out.println("Az SQL lekérdezésének ideje: " + (measureTime(false) / 1000000) + "ms");
		      
		      System.out.println("Stored Procedure executed successfully");
		      
				ResultSet result2 = cstmt.getResultSet();																			//az sql lekérdezés tartalmát odaadja egy result set változónak

				XSSFWorkbook workbook = new XSSFWorkbook();																			//excel tipusú osztály létrehjozása
				XSSFSheet sheet = workbook.createSheet("Eredmények");																//excel osztályban egy tábla létrehozása a megadott névvel

				writeHeaderLine(sheet);																								//fejlécet lekészítő metódus meghívása

				writeDataLines(result2, workbook, sheet);																			//tábla tartalmát beírja	

				FileOutputStream outputStream = new FileOutputStream(menteshelye);		//file tipusú változó létrehozása a megadott helyen
				workbook.write(outputStream);																						//adatok kiírása egy fájlba amit elöbb megadtunk
				workbook.close();																									//adatofolyam lezárása
				outputStream.close();																								//fájl lezárása

				//statement.close();
				JOptionPane.showMessageDialog(null, "SQL lekérdezés kész", "Tájékoztató Üzenet", 1);								//String összefűzés végén  végén megjelenő üzenet
		     
			}
			 
			catch (SQLException e1) 
			{
				// TODO Auto-generated catch block
				String hibauzenet2 = e1.toString();
				JOptionPane.showMessageDialog(null, hibauzenet2, "Hiba üzenet", 2);
				
			} catch (EncryptedDocumentException e1) {
				// TODO Auto-generated catch block
				String hibauzenet2 = e1.toString();
				JOptionPane.showMessageDialog(null, hibauzenet2, "Hiba üzenet", 2);
				
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				String hibauzenet2 = e1.toString();
				JOptionPane.showMessageDialog(null, hibauzenet2, "Hiba üzenet", 2);
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				String hibauzenet2 = e1.toString();
				JOptionPane.showMessageDialog(null, hibauzenet2, "Hiba üzenet", 2);
				
			}
			}
			
	}
	
	private void writeHeaderLine(XSSFSheet sheet) 																				//tábla fejlécét elkészítő metódus
	{

		Row headerRow = sheet.createRow(0);

		Cell headerCell = headerRow.createCell(0);
		headerCell.setCellValue("Azonosító");

		headerCell = headerRow.createCell(1);
		headerCell.setCellValue("hely");

		headerCell = headerRow.createCell(2);
		headerCell.setCellValue("Idő");

		headerCell = headerRow.createCell(3);
		headerCell.setCellValue("Panel");

		headerCell = headerRow.createCell(4);
		headerCell.setCellValue("Ok");
		
		headerCell = headerRow.createCell(5);
		headerCell.setCellValue("Hibakód");
		
		headerCell = headerRow.createCell(6);
		headerCell.setCellValue("Alsor");
		
		headerCell = headerRow.createCell(7);
		headerCell.setCellValue("Kód2");
		
		headerCell = headerRow.createCell(8);
		headerCell.setCellValue("Szériaszám");
		
		headerCell = headerRow.createCell(9);
		headerCell.setCellValue("Tesztszám");
		
		headerCell = headerRow.createCell(10);
		headerCell.setCellValue("Pozició");
		
		headerCell = headerRow.createCell(11);
		headerCell.setCellValue("Teljesszám");
		
		headerCell = headerRow.createCell(12);
		headerCell.setCellValue("Teszt kezdete");
		
		headerCell = headerRow.createCell(13);
		headerCell.setCellValue("Teszt vége");
		
		headerCell = headerRow.createCell(14);
		headerCell.setCellValue("Hibakód");
		
		headerCell = headerRow.createCell(15);
		headerCell.setCellValue("Error");
		
		headerCell = headerRow.createCell(16);
		headerCell.setCellValue("Mért érték");
		
		headerCell = headerRow.createCell(17);
		headerCell.setCellValue("Dolgozó");
		
		headerCell = headerRow.createCell(18);
		headerCell.setCellValue("Név");
		
		headerCell = headerRow.createCell(19);
		headerCell.setCellValue("Megnevezés");
	}

	private void writeDataLines(ResultSet result, XSSFWorkbook workbook, 									//tábla tartalmát feltöltő metódus
			XSSFSheet sheet) throws SQLException 
	{
		int rowCount = 1;

		while (result.next()) {
			String Azonosito = result.getString("azon");
			String hely = result.getString("hely");
			String Ido = result.getString("ido");
			String Panel = result.getString("panel");
			String Ok = result.getString("ok");
			String Hibakod = result.getString("hibakod");
			String alsor = result.getString("alsor");
			String kod2 = result.getString("kod2");
			String szeriaszam = result.getString("szeriaszam");
			String tesztszam = result.getString("tesztszam");
			String poz = result.getString("poz");
			String teljesszam = result.getString("teljesszam");
			String teststarttime = result.getString("teststarttime");
			String testfinishtime = result.getString("testfinishtime");
			String failtestnames = result.getString("failtestnames");
			String error = result.getString("error");
			String measuredvalue = result.getString("measuredvalue");
			String dolgozo = result.getString("dolgozo");
			String nev = result.getString("nev");
			String megnev = result.getString("megnev");
			//float rating = result.getFloat("alsor");
			//Timestamp timestamp = result.getTimestamp("ido");
			//String comment = result.getString("alsor");

			Row row = sheet.createRow(rowCount++);

			int columnCount = 0;
			Cell cell = row.createCell(columnCount++);
			cell.setCellValue(Azonosito);

			cell = row.createCell(columnCount++);
			cell.setCellValue(hely);
			
			cell = row.createCell(columnCount++);
			cell.setCellValue(Ido);
			
			cell = row.createCell(columnCount++);
			cell.setCellValue(Panel);
			
			cell = row.createCell(columnCount++);
			cell.setCellValue(Ok);
			
			cell = row.createCell(columnCount++);
			cell.setCellValue(Hibakod);
			
			cell = row.createCell(columnCount++);
			cell.setCellValue(alsor);
			
			cell = row.createCell(columnCount++);
			cell.setCellValue(kod2);
			
			cell = row.createCell(columnCount++);
			cell.setCellValue(szeriaszam);
			
			cell = row.createCell(columnCount++);
			cell.setCellValue(tesztszam);
			
			cell = row.createCell(columnCount++);
			cell.setCellValue(poz);
			
			cell = row.createCell(columnCount++);
			cell.setCellValue(teljesszam);
			
			cell = row.createCell(columnCount++);
			cell.setCellValue(teststarttime);
			
			cell = row.createCell(columnCount++);
			cell.setCellValue(testfinishtime);
			
			cell = row.createCell(columnCount++);
			cell.setCellValue(failtestnames);
			
			cell = row.createCell(columnCount++);
			cell.setCellValue(error);
			
			cell = row.createCell(columnCount++);
			cell.setCellValue(measuredvalue);
			
			cell = row.createCell(columnCount++);
			cell.setCellValue(dolgozo);
			
			cell = row.createCell(columnCount++);
			cell.setCellValue(nev);
			
			cell = row.createCell(columnCount++);
			cell.setCellValue(megnev);

			cell = row.createCell(columnCount++);

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
					int returnVal = fc.showOpenDialog(frame);														//fájl megniytásának adbalak megnyit
					String sor;
		 
					if (returnVal == JFileChooser.APPROVE_OPTION) 
					{
						File file = fc.getSelectedFile();															//fájl változó megkpja azt a fájlt amit kiválsztottunk a filechooserrel
						
						measureTime(true);																			//időmérő indítása
		            	FileInputStream fis = new FileInputStream(file);											//inputstream osztály példányosítása
		            	XSSFWorkbook workbook = new XSSFWorkbook(fis);  											//excel osztály létráhozása a beolvasott fájlal
		            	XSSFSheet sheet = workbook.getSheetAt(0);
		            	Iterator<Row> itr = sheet.iterator();    													//interator példányosítása 
						
		            	while (itr.hasNext())                 
		            	{  
			            	Row row = itr.next();  
			            	Iterator<Cell> cellIterator = row.cellIterator();   									//iterating over each column  
			            	while (cellIterator.hasNext())   
			            	{  
			            		Cell cell = cellIterator.next();
			            		osszefuzott += ("\"" + cell.getStringCellValue() +"\",");							//cella tartalmát összefűzi egy stiringé, hogy az elejére és a végére tesz idézőjelet illetve egy vesszűt a végére
			            		
			            	}  
			            	 
		            	}  
		
		            	osszefuzott = osszefuzott.substring(0, osszefuzott.length() - 1);							//az utolsó vessző levágása a stringről
		            	System.out.println("Az összefűzés ideje: " + (measureTime(false) / 1000000) + "ms");
		            	System.out.println("Összefűzott panelek száma: " + osszefuzott.length());
		            	System.out.println(osszefuzott);
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
	
	class Reszlegessen implements ActionListener																		//megnyitó osztály
	{
		public void actionPerformed(ActionEvent e)
		 {
			try
			{
				if (e.getSource() == reszleges) 
				{
					osszefuzott = "";
					int returnVal = fc.showOpenDialog(frame);														//fájl megniytásának adbalak megnyit
					
					//fc.setCurrentDirectory(System.getProperty("user.home"));
					String sor;
		 
					if (returnVal == JFileChooser.APPROVE_OPTION) 
					{
						File file = fc.getSelectedFile();															//fájl változó megkpja azt a fájlt amit kiválsztottunk a filechooserrel
						
						measureTime(true);																			//időmérő indítása
		            	FileInputStream fis = new FileInputStream(file);											//inputstream osztály példányosítása
		            	XSSFWorkbook workbook = new XSSFWorkbook(fis);  											//excel osztály létráhozása a beolvasott fájlal
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
		
		            	osszefuzott = osszefuzott.substring(0, osszefuzott.length() - 3);							//az utolsó vessző levágása a stringről
		            	System.out.println("Az összefűzés ideje: " + (measureTime(false) / 1000000) + "ms");
		            	System.out.println("Összefűzott panelek száma: " + osszefuzott.length());
		            	System.out.println(osszefuzott);
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
				//Registering the Driver
				DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());														//jdbc mysql driver meghívása
				measureTime(true);
		      //Getting the connection
		      String mysqlUrl = "jdbc:mysql://192.168.5.145/";																		//mysql szerver ipcíméhez való csatlakozás
		      Connection con = DriverManager.getConnection(mysqlUrl, "quality", "Qua25!");											//a megadott ip-re csatlakozik a jelszó felhasználó névvel
		      System.out.println("Connection established......");
		      
		      //Preparing a CallableStatement to call a procedure
		      CallableStatement cstmt = con.prepareCall("{call videoton.veas_avm_csomagolt(?)}");									//tárolt eljárás meghívása
		     
		      cstmt.setString(1, osszefuzott);																						//tárolt eljárás paparméterénk megadása
		      System.out.println("Kapcsolódás ideje: " + (measureTime(false) / 1000000) + "ms");
		      measureTime(true);
		      cstmt.execute();																										//sql lejkérdezés futtatása
		      System.out.println("Az SQL lekérdezésének ideje: " + (measureTime(false) / 1000000) + "ms");
		      
		      System.out.println("Stored Procedure executed successfully");
		      
				ResultSet result2 = cstmt.getResultSet();																			//az sql lekérdezés tartalmát odaadja egy result set változónak

				XSSFWorkbook workbook = new XSSFWorkbook();																			//excel tipusú osztály létrehjozása
				XSSFSheet sheet = workbook.createSheet("Eredmények");																//excel osztályban egy tábla létrehozása a megadott névvel

				Fejlec(sheet);																								//fejlécet lekészítő metódus meghívása

				Excelbeiro(result2, workbook, sheet);																			//tábla tartalmát beírja	

				FileOutputStream outputStream = new FileOutputStream(menteshelye);				//file tipusú változó létrehozása a megadott helyen
				workbook.write(outputStream);																						//adatok kiírása egy fájlba amit elöbb megadtunk
				workbook.close();																									//adatofolyam lezárása
				outputStream.close();																								//fájl lezárása

				//statement.close();
				JOptionPane.showMessageDialog(null, "SQL lekérdezés kész", "Tájékoztató Üzenet", 1);		//String összefűzés végén  végén megjelenő üzenet
		     
			}
			 
			catch (SQLException e1) 
			{
				// TODO Auto-generated catch block
				String hibauzenet = e1.toString();  
                JOptionPane.showMessageDialog(null, hibauzenet, "Hiba üzenet", 2);
			} 
			catch (EncryptedDocumentException e1) 
			{
				// TODO Auto-generated catch block
				String hibauzenet = e1.toString();  
                JOptionPane.showMessageDialog(null, hibauzenet, "Hiba üzenet", 2);
			} 
			catch (FileNotFoundException e1) 
			{
				// TODO Auto-generated catch block
				String hibauzenet = e1.toString();  
                JOptionPane.showMessageDialog(null, hibauzenet, "Hiba üzenet", 2);
			} 
			catch (IOException e1) 
			{
				// TODO Auto-generated catch block
				String hibauzenet = e1.toString();  
                JOptionPane.showMessageDialog(null, hibauzenet, "Hiba üzenet", 2);
			}
			}
			
	}
	
	private void Fejlec(XSSFSheet sheet) 																				//tábla fejlécét elkészítő metódus
	{

		Row headerRow = sheet.createRow(0);

		Cell headerCell = headerRow.createCell(0);
		headerCell.setCellValue("Panel");

		headerCell = headerRow.createCell(1);
		headerCell.setCellValue("Hely");
		
	}

	private void Excelbeiro(ResultSet result, XSSFWorkbook workbook, 													//tábla tartalmát feltöltő metódus
			XSSFSheet sheet) throws SQLException 
	{
		int rowCount = 1;

		while (result.next()) 
		{
			
			String panel = result.getString("panel");
			
			String nev = result.getString("hely");
			
			Row row = sheet.createRow(rowCount++);

			int columnCount = 0;
			Cell cell = row.createCell(columnCount++);	
			
			cell.setCellValue(panel);
			
			
			cell = row.createCell(columnCount++);
			cell.setCellValue(nev);
			
			
			cell = row.createCell(columnCount++);
			
			
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
					int returnVal = fc.showOpenDialog(frame);														//fájl megniytásának adbalak megnyit
					String sor;
		 
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
