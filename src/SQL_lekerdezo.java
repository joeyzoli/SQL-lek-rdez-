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
	private File megnyitottfajl;
	private static Long timer_start;

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
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("SQL keres??");
		
		JButton start = new JButton("Start");
		start.setBounds(164, 182, 89, 23);
		start.addActionListener(new SQLKereses());
		frame.getContentPane().add(start);
		
		megnyit = new JButton("F??jl megnyit??s");
		megnyit.addActionListener(new Megnyitas());
		megnyit.setBounds(164, 37, 89, 23);
		
		fc = new JFileChooser();
		
		frame.getContentPane().add(megnyit);
		
		JButton csomagolt = new JButton("Csomagolt");
		csomagolt.setBounds(164, 110, 89, 23);
		csomagolt.addActionListener(new SQLKeresesCsomagolt());
		frame.getContentPane().add(csomagolt);
	}
	
	class SQLKereses implements ActionListener																						//keres?? gom megnoym??skor h??vodik meg
	{
		public void actionPerformed(ActionEvent e)
		 {
			try 
			{
				//Registering the Driver
				DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());														//jdbc mysql driver megh??v??sa
				
		      //Getting the connection
		      String mysqlUrl = "jdbc:mysql://192.168.5.145/";																		//mysql szerver ipc??m??hez val?? csatlakoz??s
		      Connection con = DriverManager.getConnection(mysqlUrl, "quality", "Qua25!");											//a megadott ip-re csatlakozik a jelsz?? felhaszn??l?? n??vvel
		      System.out.println("Connection established......");
		      
		      //Preparing a CallableStatement to call a procedure
		      CallableStatement cstmt = con.prepareCall("{call videoton.veas_avmheti_teszt(?)}");									//t??rolt elj??r??s megh??v??sa
		      
		      cstmt.setString(1, osszefuzott);																						//t??rolt elj??r??s paparm??ter??nk megad??sa
		      measureTime(true);
		      cstmt.execute();																										//sql lejk??rdez??s futtat??sa
		      
		      System.out.println("Az SQL lek??rdez??s??nek ideje: " + (measureTime(false) / 1000000) + "ms");
		      
		      System.out.println("Stored Procedure executed successfully");
		      
				ResultSet result2 = cstmt.getResultSet();																			//az sql lek??rdez??s tartalm??t odaadja egy result set v??ltoz??nak

				XSSFWorkbook workbook = new XSSFWorkbook();																			//excel tipus?? oszt??ly l??trehjoz??sa
				XSSFSheet sheet = workbook.createSheet("Eredm??nyek");																//excel oszt??lyban egy t??bla l??trehoz??sa a megadott n??vvel

				writeHeaderLine(sheet);																								//fejl??cet lek??sz??t?? met??dus megh??v??sa

				writeDataLines(result2, workbook, sheet);																			//t??bla tartalm??t be??rja	

				FileOutputStream outputStream = new FileOutputStream("c:\\Users\\kovacs.zoltan\\Desktop\\hib??s_panelek.xlsx");		//file tipus?? v??ltoz?? l??trehoz??sa a megadott helyen
				workbook.write(outputStream);																						//adatok ki??r??sa egy f??jlba amit el??bb megadtunk
				workbook.close();																									//adatofolyam lez??r??sa
				outputStream.close();																								//f??jl lez??r??sa

				//statement.close();
				JOptionPane.showMessageDialog(null, "SQL lek??rdez??s k??sz", "T??j??koztat?? ??zenet", 1);								//String ??sszef??z??s v??g??n  v??g??n megjelen?? ??zenet
		     
			}
			 
			catch (SQLException e1) 
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (EncryptedDocumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			}
			
	}
	
	private void writeHeaderLine(XSSFSheet sheet) 																				//t??bla fejl??c??t elk??sz??t?? met??dus
	{

		Row headerRow = sheet.createRow(0);

		Cell headerCell = headerRow.createCell(0);
		headerCell.setCellValue("Azonos??t??");

		headerCell = headerRow.createCell(1);
		headerCell.setCellValue("hely");

		headerCell = headerRow.createCell(2);
		headerCell.setCellValue("Id??");

		headerCell = headerRow.createCell(3);
		headerCell.setCellValue("Panel");

		headerCell = headerRow.createCell(4);
		headerCell.setCellValue("Ok");
		
		headerCell = headerRow.createCell(5);
		headerCell.setCellValue("Hibak??d");
		
		headerCell = headerRow.createCell(6);
		headerCell.setCellValue("Alsor");
		
		headerCell = headerRow.createCell(7);
		headerCell.setCellValue("K??d2");
		
		headerCell = headerRow.createCell(8);
		headerCell.setCellValue("Sz??riasz??m");
		
		headerCell = headerRow.createCell(9);
		headerCell.setCellValue("Tesztsz??m");
		
		headerCell = headerRow.createCell(10);
		headerCell.setCellValue("Pozici??");
		
		headerCell = headerRow.createCell(11);
		headerCell.setCellValue("Teljessz??m");
		
		headerCell = headerRow.createCell(12);
		headerCell.setCellValue("Teszt kezdete");
		
		headerCell = headerRow.createCell(13);
		headerCell.setCellValue("Teszt v??ge");
		
		headerCell = headerRow.createCell(14);
		headerCell.setCellValue("Hibak??d");
		
		headerCell = headerRow.createCell(15);
		headerCell.setCellValue("Error");
		
		headerCell = headerRow.createCell(16);
		headerCell.setCellValue("M??rt ??rt??k");
		
		headerCell = headerRow.createCell(17);
		headerCell.setCellValue("Dolgoz??");
		
		headerCell = headerRow.createCell(18);
		headerCell.setCellValue("N??v");
		
		headerCell = headerRow.createCell(19);
		headerCell.setCellValue("Megnevez??s");
	}

	private void writeDataLines(ResultSet result, XSSFWorkbook workbook, 									//t??bla tartalm??t felt??lt?? met??dus
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
	
	class Megnyitas implements ActionListener																		//megnyit?? oszt??ly
	{
		public void actionPerformed(ActionEvent e)
		 {
			try
			{
				if (e.getSource() == megnyit) 
				{
					osszefuzott = "";
					int returnVal = fc.showOpenDialog(frame);														//f??jl megniyt??s??nak adbalak megnyit
					String sor;
		 
					if (returnVal == JFileChooser.APPROVE_OPTION) 
					{
						File file = fc.getSelectedFile();															//f??jl v??ltoz?? megkpja azt a f??jlt amit kiv??lsztottunk a filechooserrel
						
						measureTime(true);																			//id??m??r?? ind??t??sa
		            	FileInputStream fis = new FileInputStream(file);											//inputstream oszt??ly p??ld??nyos??t??sa
		            	XSSFWorkbook workbook = new XSSFWorkbook(fis);  											//excel oszt??ly l??tr??hoz??sa a beolvasott f??jlal
		            	XSSFSheet sheet = workbook.getSheetAt(0);
		            	Iterator<Row> itr = sheet.iterator();    													//interator p??ld??nyos??t??sa 
						
		            	while (itr.hasNext())                 
		            	{  
			            	Row row = itr.next();  
			            	Iterator<Cell> cellIterator = row.cellIterator();   									//iterating over each column  
			            	while (cellIterator.hasNext())   
			            	{  
			            		Cell cell = cellIterator.next();
			            		osszefuzott += ("\"" + cell.getStringCellValue() +"\",");							//cella tartalm??t ??sszef??zi egy stiring??, hogy az elej??re ??s a v??g??re tesz id??z??jelet illetve egy vessz??t a v??g??re
			            		
			            	}  
			            	 
		            	}  
		
		            	osszefuzott = osszefuzott.substring(0, osszefuzott.length() - 1);							//az utols?? vessz?? lev??g??sa a stringr??l
		            	System.out.println("Az ??sszef??z??s ideje: " + (measureTime(false) / 1000000) + "ms");
		            	System.out.println("??sszef??zott panelek sz??ma: " + osszefuzott.length());
					} 
					
		 
				}
				//System.out.println(osszefuzott);
				JOptionPane.showMessageDialog(null, "??sszef??z??s k??sz", "T??j??koztat?? ??zenet", 1);		//String ??sszef??z??s v??g??n  v??g??n megjelen?? ??zenet
			}
			catch(IOException e1)
			{
				JOptionPane.showMessageDialog(null, "Olvas??si hiba t??rt??nt", "Hiba??zenet", 2);
			}
		 }		
	}
	
	class SQLKeresesCsomagolt implements ActionListener																						//keres?? gom megnoym??skor h??vodik meg
	{
		public void actionPerformed(ActionEvent e)
		 {
			try 
			{
				//Registering the Driver
				DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());														//jdbc mysql driver megh??v??sa
				measureTime(true);
		      //Getting the connection
		      String mysqlUrl = "jdbc:mysql://192.168.5.145/";																		//mysql szerver ipc??m??hez val?? csatlakoz??s
		      Connection con = DriverManager.getConnection(mysqlUrl, "quality", "Qua25!");											//a megadott ip-re csatlakozik a jelsz?? felhaszn??l?? n??vvel
		      System.out.println("Connection established......");
		      
		      //Preparing a CallableStatement to call a procedure
		      CallableStatement cstmt = con.prepareCall("{call videoton.veas_avm_csomagolt(?)}");									//t??rolt elj??r??s megh??v??sa
		     
		      cstmt.setString(1, osszefuzott);																						//t??rolt elj??r??s paparm??ter??nk megad??sa
		      System.out.println("Kapcsol??d??s ideje: " + (measureTime(false) / 1000000) + "ms");
		      measureTime(true);
		      cstmt.execute();																										//sql lejk??rdez??s futtat??sa
		      System.out.println("Az SQL lek??rdez??s??nek ideje: " + (measureTime(false) / 1000000) + "ms");
		      
		      System.out.println("Stored Procedure executed successfully");
		      
				ResultSet result2 = cstmt.getResultSet();																			//az sql lek??rdez??s tartalm??t odaadja egy result set v??ltoz??nak

				XSSFWorkbook workbook = new XSSFWorkbook();																			//excel tipus?? oszt??ly l??trehjoz??sa
				XSSFSheet sheet = workbook.createSheet("Eredm??nyek");																//excel oszt??lyban egy t??bla l??trehoz??sa a megadott n??vvel

				Fejlec(sheet);																								//fejl??cet lek??sz??t?? met??dus megh??v??sa

				Excelbeiro(result2, workbook, sheet);																			//t??bla tartalm??t be??rja	

				FileOutputStream outputStream = new FileOutputStream("c:\\Users\\kovacs.zoltan\\Desktop\\csomagolt_panelek.xlsx");				//file tipus?? v??ltoz?? l??trehoz??sa a megadott helyen
				workbook.write(outputStream);																						//adatok ki??r??sa egy f??jlba amit el??bb megadtunk
				workbook.close();																									//adatofolyam lez??r??sa
				outputStream.close();																								//f??jl lez??r??sa

				//statement.close();
				JOptionPane.showMessageDialog(null, "SQL lek??rdez??s k??sz", "T??j??koztat?? ??zenet", 1);		//String ??sszef??z??s v??g??n  v??g??n megjelen?? ??zenet
		     
			}
			 
			catch (SQLException e1) 
			{
				// TODO Auto-generated catch block
				String hibauzenet = e1.toString();  
                JOptionPane.showMessageDialog(null, hibauzenet, "Hiba ??zenet", 2);
			} 
			catch (EncryptedDocumentException e1) 
			{
				// TODO Auto-generated catch block
				String hibauzenet = e1.toString();  
                JOptionPane.showMessageDialog(null, hibauzenet, "Hiba ??zenet", 2);
			} 
			catch (FileNotFoundException e1) 
			{
				// TODO Auto-generated catch block
				String hibauzenet = e1.toString();  
                JOptionPane.showMessageDialog(null, hibauzenet, "Hiba ??zenet", 2);
			} 
			catch (IOException e1) 
			{
				// TODO Auto-generated catch block
				String hibauzenet = e1.toString();  
                JOptionPane.showMessageDialog(null, hibauzenet, "Hiba ??zenet", 2);
			}
			}
			
	}
	
	private void Fejlec(XSSFSheet sheet) 																				//t??bla fejl??c??t elk??sz??t?? met??dus
	{

		Row headerRow = sheet.createRow(0);

		Cell headerCell = headerRow.createCell(0);
		headerCell.setCellValue("Panel");

		headerCell = headerRow.createCell(1);
		headerCell.setCellValue("Hely");
		
	}

	private void Excelbeiro(ResultSet result, XSSFWorkbook workbook, 													//t??bla tartalm??t felt??lt?? met??dus
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
	
	static public float measureTime(boolean run)					//id??m??r?? met??dus
	{
		long current_time = System.nanoTime();						//a rendszerid??t nekiadjuk egy v??ltoz??nak
				
		if (run == true)											//ha igazra ??ll??tjuk elindul
		{
				timer_start = System.nanoTime();					//id??z??t?? indul??si ??rt??ke a rendszer aktu??lis ideje
				return (-1.0f);
		}
		else
		{
			long elapsed_time = current_time - timer_start;			//ha false lesz az ??rt??k
			return (elapsed_time);									//visszat??r a k??l??nbs??ggel
		}
	}
	
}
