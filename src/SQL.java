import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.spire.data.table.DataTable;
import com.spire.data.table.common.JdbcAdapter;
import com.spire.xls.ExcelVersion;
import com.spire.xls.Workbook;
import com.spire.xls.Worksheet;

public class SQL 
{
	private ResultSet result;
	//private ResultSet result2;
	//private ResultSet result3;
	//private ResultSet result4;
	private JdbcAdapter jdbcAdapter;
	private JdbcAdapter jdbcAdapter2;
	private JdbcAdapter jdbcAdapter3;
	private JdbcAdapter jdbcAdapter4;
	private DataTable datatable;
	private DataTable datatable2;
	private DataTable datatable3;
	private DataTable datatable4;
	private Workbook workbook;
	private Statement cstmt;
	private Connection con;
	
	void kiir(String osszefuzott, String osszefuzott2, String osszefuzott3, String osszefuzott4, File menteshelye)
	{
		try
		{
			//Registering the Driver
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());														//jdbc mysql driver meghÃ­vÃ¡sa
				
			//Getting the connection
			String mysqlUrl = "jdbc:mysql://172.20.22.6/";																		//mysql szerver ipcÃ­mÃ©hez valÃ³ csatlakozÃ¡s  172.20.22.6
			con = DriverManager.getConnection(mysqlUrl, "quality", "Qua25!");											//a megadott ip-re csatlakozik a jelszÃ³ felhasznÃ¡lÃ³ nÃ©vvel "quality", "Qua25!"
			System.out.println("Connection established......");
			
			//SQL_lekerdezo.progressBar.setValue(10);
			//Preparing a CallableStatement to call a procedure
			// CallableStatement cstmt = con.prepareCall("{call videoton.veas_avmheti_teszt(?)}");
			
			String sql = "select 	videoton.fkov.azon, videoton.fkov.hely,videoton.fkovsor.nev, videoton.fkov.ido, videoton.fkov.panel, cast(videoton.fkov.alsor as char(5)) as Teszterszam,"
					+ "if(videoton.fkov.ok in ('-1', '1'), \"Rendben\", \"Hiba\") as eredmeny,"
					+ "videoton.fkov.hibakod, videoton.fkov.kod2, videoton.fkov.torolt, "
					+ "videoton.fkov.szeriaszam, videoton.fkov.tesztszam, videoton.fkov.poz, videoton.fkov.teljesszam, videoton.fkov.failtestnames, videoton.fkov.error,"
					+ "videoton.fkov.dolgozo \n"
					+ "from videoton.fkov  \n"
					+ "inner join videoton.fkovsor on videoton.fkovsor.azon = videoton.fkov.hely "
					+ "left join videoton.FKOVADAT on videoton.FKOVADAT.FKOV = videoton.fkov.azon "					
					+ " where panel in (" + osszefuzott +") ";
			
			/*String sql = "select 	videoton.fkov.azon, videoton.fkov.hely,videoton.fkovsor.nev, videoton.fkov.ido, videoton.fkov.panel, cast(videoton.fkov.alsor as char(5)) as Teszterszam,"
					+ "if(videoton.fkov.ok in ('-1', '1'), \"Rendben\", \"Hiba\") as eredmeny,"
					+ "videoton.fkov.hibakod, videoton.fkov.kod2, videoton.fkov.torolt, "
					+ "videoton.fkov.szeriaszam, videoton.fkov.tesztszam, videoton.fkov.poz, videoton.fkov.teljesszam, videoton.fkov.failtestnames, videoton.fkov.error,"
					+ "videoton.fkov.dolgozo \n"
					+ "from	videoton.fkov \n"
					+ "inner join videoton.fkovsor on videoton.fkovsor.azon = videoton.fkov.hely \n"
					+ " where ido in (" + osszefuzott +") ";
			
			String sql2 = "select 	videoton.fkov.azon, videoton.fkov.hely,videoton.fkovsor.nev, videoton.fkov.ido, videoton.fkov.panel, cast(videoton.fkov.alsor as char(5)) as Teszterszam,"
					+ "if(videoton.fkov.ok in ('-1', '1'), \"Rendben\", \"Hiba\") as eredmeny, "
					+ "videoton.fkov.hibakod, videoton.fkov.kod2, videoton.fkov.torolt, "
					+ "videoton.fkov.szeriaszam, videoton.fkov.tesztszam, videoton.fkov.poz, videoton.fkov.teljesszam, videoton.fkov.failtestnames, videoton.fkov.error,"
					+ "videoton.fkov.dolgozo \n"
					+ "from	videoton.fkov \n"
					+ "inner join videoton.fkovsor on videoton.fkovsor.azon = videoton.fkov.hely \n"
					+ " where panel in (" + osszefuzott2 +")";
			/*String sql = "select Fkov, ExtractValue(adat, '//current_EM2_value') from videoton.FKOVADAT \r\n"
					+ "WHERE 3 = 3\r\n"
					+ "and FKOV in ("+ osszefuzott +")";*/
			//String sql = "SELECT * FROM videoton.kov where panel  in ("+ osszefuzott +")";					//alaktrész beépülés
			//String sql = "select * from videoton.fkovavm WHERE panel in ("+ osszefuzott +")";					//Firmware keresés
			/*String sql = "select *\r\n"
					+ "from videoton.fkov\r\n"
					+ "inner join	fkovsor on fkovsor.azon = fkov.hely\r\n"
					+ "where 3=3\r\n"
					+ "and ido in ('2023.07.14 05:27:50','2023.07.14 09:12:19','2023.07.14 09:12:50','2023.07.22 05:47:36','2023.07.22 07:25:32','2023.08.11 14:54:48','2023.08.11 14:56:39','2023.08.11 14:57:17',\r\n"
					+ "'2023.08.11 14:58:31','2023.08.11 15:00:24','2023.08.11 15:01:01','2023.08.15 14:39:47','2023.08.15 14:40:24','2023.08.15 14:41:02','2023.08.15 14:50:00','2023.08.15 14:51:14',\r\n"
					+ "'2023.08.15 14:51:52', '2023.08.15 14:52:29','2023.08.15 14:53:06','2023.08.15 14:53:44','2023.08.15 14:54:22')\r\n"
					+ "and hely = '50'";*/
			//String sql = "select * from videoton.fkov where 3=3 and ido >= '2023.06.21 00:00:00' and ido <= '2023.08.29 20:41:00' and hely = '59'";
			
			//String sql = "select * from videoton.fkov where 3=3 and ido in("+ osszefuzott +") and hely = '50'";
			
			String sql2 = "select 	videoton.fkov.azon, videoton.fkov.hely,videoton.fkovsor.nev, videoton.fkov.ido, videoton.fkov.panel, cast(videoton.fkov.alsor as char(5)) as Teszterszam,"
					+ "if(videoton.fkov.ok in ('-1', '1'), \"Rendben\", \"Hiba\") as eredmeny,"
					+ "videoton.fkov.hibakod, videoton.fkov.kod2, videoton.fkov.torolt, "
					+ "videoton.fkov.szeriaszam, videoton.fkov.tesztszam, videoton.fkov.poz, videoton.fkov.teljesszam, videoton.fkov.failtestnames, videoton.fkov.error,"
					+ "videoton.fkov.dolgozo \n"
					+ "from videoton.fkov  \n"
					+ "inner join videoton.fkovsor on videoton.fkovsor.azon = videoton.fkov.hely "
					+ "left join videoton.FKOVADAT on videoton.FKOVADAT.FKOV = videoton.fkov.azon "					
					+ " where panel in (" + osszefuzott2 +") ";
			/*String sql2 = "select Fkov, ExtractValue(adat, '//current_EM2_value') from videoton.FKOVADAT \r\n"
					+ "WHERE 3 = 3\r\n"
					+ "and FKOV in ("+ osszefuzott2 +")";   if(videoton.fkov.hibakod = '(XML)', videoton.FKOVADAT.adat,  videoton.fkov.hibakod)*/
			
			String sql3 = "select 	videoton.fkov.azon, videoton.fkov.hely,videoton.fkovsor.nev, videoton.fkov.ido, videoton.fkov.panel, cast(videoton.fkov.alsor as char(5)) as Teszterszam,"
					+ "if(videoton.fkov.ok in ('-1', '1'), \"Rendben\", \"Hiba\") as eredmeny,"
					+ "videoton.fkov.hibakod, videoton.fkov.kod2, videoton.fkov.torolt, "
					+ "videoton.fkov.szeriaszam, videoton.fkov.tesztszam, videoton.fkov.poz, videoton.fkov.teljesszam, videoton.fkov.failtestnames, videoton.fkov.error,"
					+ "videoton.fkov.dolgozo \n"
					+ "from videoton.fkov  \n"
					+ "inner join videoton.fkovsor on videoton.fkovsor.azon = videoton.fkov.hely "
					+ "left join videoton.FKOVADAT on videoton.FKOVADAT.FKOV = videoton.fkov.azon "					
					+ " where panel in (" + osszefuzott3 +") ";
			
			String sql4 = "select 	videoton.fkov.azon, videoton.fkov.hely,videoton.fkovsor.nev, videoton.fkov.ido, videoton.fkov.panel, cast(videoton.fkov.alsor as char(5)) as Teszterszam,"
					+ "if(videoton.fkov.ok in ('-1', '1'), \"Rendben\", \"Hiba\") as eredmeny,"
					+ "videoton.fkov.hibakod, videoton.fkov.kod2, videoton.fkov.torolt, "
					+ "videoton.fkov.szeriaszam, videoton.fkov.tesztszam, videoton.fkov.poz, videoton.fkov.teljesszam, videoton.fkov.failtestnames, videoton.fkov.error,"
					+ "videoton.fkov.dolgozo \n"
					+ "from videoton.fkov  \n"
					+ "inner join videoton.fkovsor on videoton.fkovsor.azon = videoton.fkov.hely "
					+ "left join videoton.FKOVADAT on videoton.FKOVADAT.FKOV = videoton.fkov.azon "					
					+ " where panel in (" + osszefuzott4 +") ";
			
			Statement cstmt = con.createStatement(
	                ResultSet.TYPE_SCROLL_INSENSITIVE,
	                ResultSet.CONCUR_UPDATABLE);
			
			/*Statement cstmt2 = con.createStatement(
	                ResultSet.TYPE_SCROLL_INSENSITIVE,
	                ResultSet.CONCUR_UPDATABLE);
			
			Statement cstmt3 = con.createStatement(
	                ResultSet.TYPE_SCROLL_INSENSITIVE,
	                ResultSet.CONCUR_UPDATABLE);
			
			Statement cstmt4 = con.createStatement(
	                ResultSet.TYPE_SCROLL_INSENSITIVE,
	                ResultSet.CONCUR_UPDATABLE);*/
			
			System.out.println("Kezdődik");
			cstmt.execute(sql);																										//sql llekérdezés futtatása
			
			result = cstmt.getResultSet();																								//az sql lekÃ©rdezÃ©s tartalmÃ¡t odaadja egy result set vÃ¡ltozÃ³nak
			System.out.println("Lefutott");
			datatable = new DataTable();
			datatable2 = new DataTable();
			datatable3 = new DataTable();
			datatable4 = new DataTable();
			workbook = new Workbook();
			workbook.setVersion(ExcelVersion.Version2016); 
			jdbcAdapter = new JdbcAdapter();
			jdbcAdapter2 = new JdbcAdapter();
			jdbcAdapter3 = new JdbcAdapter();
			jdbcAdapter4 = new JdbcAdapter();

			jdbcAdapter.fillDataTable(datatable, result);
			System.out.println("datatabléban");
			//Get the first worksheet
			Worksheet sheet = workbook.getWorksheets().get(0);
			sheet.insertDataTable(datatable, true, 1, 1);
			sheet.getAutoFilters().setRange(sheet.getCellRange("A1:P1"));
			sheet.getAllocatedRange().autoFitColumns();
			sheet.getAllocatedRange().autoFitRows();
			    
			sheet.getCellRange("A1:Z1").getCellStyle().getExcelFont().isBold(true);                          // fÃ©lkÃ¶vÃ©r beÃ¡llÃ­tÃ¡s
			
			System.out.println("Első SQL");
			
			if(osszefuzott2 != "")
        	{
				cstmt.execute(sql2);																										//sql lejkÃ©rdezÃ©s futtatÃ¡sa
				
				result = cstmt.getResultSet();																								//az sql lekÃ©rdezÃ©s tartalmÃ¡t odaadja egy result set vÃ¡ltozÃ³nak
				
				jdbcAdapter2.fillDataTable(datatable2, result);
				//Get the first worksheet
				Worksheet sheet2 = workbook.getWorksheets().get(1);
				sheet2.insertDataTable(datatable2, true, 1, 1);
				sheet2.getAutoFilters().setRange(sheet2.getCellRange("A1:P1"));
				sheet2.getAllocatedRange().autoFitColumns();
				sheet2.getAllocatedRange().autoFitRows();
				    
				sheet2.getCellRange("A1:Z1").getCellStyle().getExcelFont().isBold(true);                          // fÃ©lkÃ¶vÃ©r beÃ¡llÃ­tÃ¡s
				
				System.out.println("Második SQL");
				
				int a = workbook.getWorksheets().get(1).getLastRow();
				int b = workbook.getWorksheets().get(1).getLastColumn();
				
				sheet2.getCellRange(2, 1, a, b).copy(sheet.getCellRange(sheet.getLastRow()+1, 1, a + sheet.getLastRow(), b));
				sheet2.remove();
        	}
			
			if(osszefuzott3 != "")
        	{
				cstmt.execute(sql3);																										//sql lejkÃ©rdezÃ©s futtatÃ¡sa

				result = cstmt.getResultSet();																								//az sql lekÃ©rdezÃ©s tartalmÃ¡t odaadja egy result set vÃ¡ltozÃ³nak
				
				jdbcAdapter3.fillDataTable(datatable3, result);
				//Get the first worksheet
				Worksheet sheet3 = workbook.getWorksheets().get(1);
				sheet3.insertDataTable(datatable3, true, 1, 1);
				sheet3.getAutoFilters().setRange(sheet3.getCellRange("A1:P1"));
				sheet3.getAllocatedRange().autoFitColumns();
				sheet3.getAllocatedRange().autoFitRows();
				    
				sheet3.getCellRange("A1:Z1").getCellStyle().getExcelFont().isBold(true);                          // fÃ©lkÃ¶vÃ©r beÃ¡llÃ­tÃ¡s
				
				System.out.println("Harmadik SQL");
				
				int a = workbook.getWorksheets().get(1).getLastRow();
				int b = workbook.getWorksheets().get(1).getLastColumn();
				
				sheet3.getCellRange(2, 1, a, b).copy(sheet.getCellRange(sheet.getLastRow()+1, 1, a + sheet.getLastRow(), b));
				sheet3.remove();
        	}
			
			if(osszefuzott4 != "")
        	{
				cstmt.execute(sql4);																										//sql lejkÃ©rdezÃ©s futtatÃ¡sa
				result = cstmt.getResultSet();																								//az sql lekÃ©rdezÃ©s tartalmÃ¡t odaadja egy result set vÃ¡ltozÃ³nak				
				jdbcAdapter4.fillDataTable(datatable4, result);
				//Get the first worksheet
				Worksheet sheet4 = workbook.getWorksheets().get(0);
				sheet4.insertDataTable(datatable4, true, 1, 1);
				sheet4.getAutoFilters().setRange(sheet4.getCellRange("A1:P1"));
				sheet4.getAllocatedRange().autoFitColumns();
				sheet4.getAllocatedRange().autoFitRows();
				    
				sheet4.getCellRange("A1:Z1").getCellStyle().getExcelFont().isBold(true);                          // fÃ©lkÃ¶vÃ©r beÃ¡llÃ­tÃ¡s
				
				System.out.println("Negyedik SQL");
				
				int a = workbook.getWorksheets().get(0).getLastRow();
				int b = workbook.getWorksheets().get(0).getLastColumn();
				
				sheet4.getCellRange(2, 1, a, b).copy(sheet.getCellRange(sheet.getLastRow()+1, 1, a + sheet.getLastRow(), b));
				//sheet3.remove();
        	}
			System.out.println("Excelbe másolva");
			result.close();
			cstmt.close();
			con.close();
			workbook.setActiveSheetIndex(0); 
			workbook.saveToFile(menteshelye.getAbsolutePath(), ExcelVersion.Version2016);
			System.out.println("Excel mentve");
			FileInputStream fileStream = new FileInputStream(menteshelye.getAbsolutePath());
			try (XSSFWorkbook workbook = new XSSFWorkbook(fileStream)) 
			{
				for(int i = workbook.getNumberOfSheets()-1; i>0 ;i--)
				{    
					workbook.removeSheetAt(i); 
	            }      
				FileOutputStream output = new FileOutputStream(menteshelye.getAbsolutePath());
				workbook.write(output);
				output.close();
			}
			JOptionPane.showMessageDialog(null, "Mentve az asztalra Eredmények.xlsx néven", "Infó", 1);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			String hibauzenet2 = e.toString();
			JOptionPane.showMessageDialog(null, hibauzenet2, "Hiba üzenet", 2);
		}
		finally                                                                     //finally rÃ©sz mindenkÃ©ppen lefut, hogy hiba esetÃ©n is lezÃ¡rja a kacsolatot
        {
            try 
            {
              if (cstmt != null)
                 con.close();
            } 
            catch (SQLException se) {}
            try 
            {
              if (con != null)
                 con.close();
            } 
            catch (SQLException se) 
            {
              se.printStackTrace();
            }  
        }	
	}
	
	void szeriaszam(String osszefuzott, File menteshelye)
	{
		try
		{
			//Registering the Driver
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());														//jdbc mysql driver meghÃ­vÃ¡sa
				
			//Getting the connection
			String mysqlUrl = "jdbc:mysql://192.168.5.145/";																		//mysql szerver ipcÃ­mÃ©hez valÃ³ csatlakozÃ¡s
			con = DriverManager.getConnection(mysqlUrl, "quality", "Qua25!");											//a megadott ip-re csatlakozik a jelszÃ³ felhasznÃ¡lÃ³ nÃ©vvel
			System.out.println("Connection established......");

			//SQL_lekerdezo.progressBar.setValue(10);
			//Preparing a CallableStatement to call a procedure
			// CallableStatement cstmt = con.prepareCall("{call videoton.veas_avmheti_teszt(?)}");									//tÃ¡rolt eljÃ¡rÃ¡s meghÃ­vÃ¡sa      videoton.veas_avmheti_teszt
			String sql = "select 	videoton.fkov.azon, videoton.fkov.hely,videoton.fkovsor.nev, videoton.fkov.ido, videoton.fkov.panel, if(videoton.fkov.ok in ('-1', '1'), \"Rendben\", \"Hiba\") as eredmeny, "
					+ "videoton.fkov.hibakod, videoton.fkov.kod2, videoton.fkov.torolt, "
					+ "videoton.fkov.szeriaszam, videoton.fkov.tesztszam, videoton.fkov.poz, videoton.fkov.teljesszam, videoton.fkov.failtestnames, videoton.fkov.error,"
					+ "videoton.fkov.dolgozo \n"
					+ "from	videoton.fkov \n"
					+ "inner join videoton.fkovsor on videoton.fkovsor.azon = videoton.fkov.hely \n"
					+ " where szeriaszam in (" + osszefuzott +")";
			
			
			
			Statement cstmt = con.createStatement(
	                ResultSet.TYPE_SCROLL_INSENSITIVE,
	                ResultSet.CONCUR_UPDATABLE);
			
			cstmt.execute(sql);																										//sql lejkÃ©rdezÃ©s futtatÃ¡sa
			result = cstmt.getResultSet();																								//az sql lekÃ©rdezÃ©s tartalmÃ¡t odaadja egy result set vÃ¡ltozÃ³nak
			
			datatable = new DataTable();
			
			workbook = new Workbook();
			workbook.setVersion(ExcelVersion.Version2013); 
			jdbcAdapter = new JdbcAdapter();
			jdbcAdapter.fillDataTable(datatable, result);
			
			//Get the first worksheet
			Worksheet sheet = workbook.getWorksheets().get(0);
			Worksheet sheet2 = workbook.getWorksheets().get(1);
			Worksheet sheet3 = workbook.getWorksheets().get(2);
			sheet.insertDataTable(datatable, true, 1, 1);
			sheet.getAutoFilters().setRange(sheet.getCellRange("A1:P1"));
			sheet.getAllocatedRange().autoFitColumns();
			sheet.getAllocatedRange().autoFitRows();
			    
			sheet.getCellRange("A1:Z1").getCellStyle().getExcelFont().isBold(true);                          // fÃ©lkÃ¶vÃ©r beÃ¡llÃ­tÃ¡s
			sheet2.remove();
			sheet3.remove();
			result.close();
			cstmt.close();
			con.close();
			workbook.saveToFile(menteshelye.getAbsolutePath(), ExcelVersion.Version2016);
			
			FileInputStream fileStream = new FileInputStream(menteshelye.getAbsolutePath());
			try (XSSFWorkbook workbook = new XSSFWorkbook(fileStream)) 
			{
				for(int i = workbook.getNumberOfSheets()-1; i>0 ;i--)
				{    
					workbook.removeSheetAt(i); 
	            }      
				FileOutputStream output = new FileOutputStream(menteshelye.getAbsolutePath());
				workbook.write(output);
				output.close();
			}
			
			JOptionPane.showMessageDialog(null, "Mentve az asztalra Eredmények.xlsx néven", "Infó", 1);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			String hibauzenet2 = e.toString();
			JOptionPane.showMessageDialog(null, hibauzenet2, "Hiba üzenet", 2);
		}
		finally                                                                     //finally rÃ©sz mindenkÃ©ppen lefut, hogy hiba esetÃ©n is lezÃ¡rja a kacsolatot
        {
            try 
            {
              if (cstmt != null)
                 con.close();
            } 
            catch (SQLException se) {}
            try 
            {
              if (con != null)
                 con.close();
            } 
            catch (SQLException se) 
            {
              se.printStackTrace();
            }  
        }	
	}
	
	
	void reszleges(String osszefuzott, File menteshelye)
	{
		try
		{
			//Registering the Driver
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());														//jdbc mysql driver meghÃ­vÃ¡sa
	
			//Getting the connection
			String mysqlUrl = "jdbc:mysql://192.168.5.145/";																		//mysql szerver ipcÃ­mÃ©hez valÃ³ csatlakozÃ¡s
			Connection con = DriverManager.getConnection(mysqlUrl, "quality", "Qua25!");											//a megadott ip-re csatlakozik a jelszÃ³ felhasznÃ¡lÃ³ nÃ©vvel
			System.out.println("Connection established......");
			
			Statement cstmt = con.createStatement();
			
			String sql = "select 	videoton.fkov.azon, videoton.fkov.hely,videoton.fkovsor.nev, videoton.fkov.ido, videoton.fkov.panel, if(videoton.fkov.ok in ('-1', '1'), \"Rendben\", \"Hiba\") as eredmeny, "
					+ "videoton.fkov.hibakod, videoton.fkov.kod2, videoton.fkov.torolt, "
					+ "videoton.fkov.szeriaszam, videoton.fkov.tesztszam, videoton.fkov.poz, videoton.fkov.teljesszam, videoton.fkov.failtestnames, videoton.fkov.error,"
					+ "videoton.fkov.dolgozo \n"
					+ "from	videoton.fkov \n"
					+ "inner join videoton.fkovsor on videoton.fkovsor.azon = videoton.fkov.hely \n"
					+ " where " + osszefuzott +" and hely = '50'";
			/*
			String sql = "select videoton.fkov.*\r\n"
					+ "from \r\n"
					+ "(select videoton.fkov.ido\r\n"
					+ "from videoton.fkov\r\n"
					+ "-- inner join	fkovsor on fkovsor.azon = fkov.hely\r\n"
					+ "where 3=3\r\n"
					+ "and " + osszefuzott +"\r\n"
					+ "and videoton.fkov.hely = '50') belso,\r\n"
					+ "videoton.fkov\r\n"
					+ "inner join videoton.fkovsor on videoton.fkovsor.azon = videoton.fkov.hely\r\n"
					+ "\r\n"
					+ "where 3 = 3\r\n"
					+ "and fkov.ido = belso.ido\r\n"
					+ "";
			*/
			cstmt.execute(sql);																										//sql lejkÃ©rdezÃ©s futtatÃ¡sa
			
			result = cstmt.getResultSet();
			
			datatable = new DataTable();
			
			workbook = new Workbook();
			workbook.setVersion(ExcelVersion.Version2016); 
			jdbcAdapter = new JdbcAdapter();
			
			jdbcAdapter.fillDataTable(datatable, result);
			
			//Get the first worksheet
			Worksheet sheet = workbook.getWorksheets().get(0);
			sheet.insertDataTable(datatable, true, 1, 1);
			/*int cellaszam = 1;
			sheet.getRange().get("A" + cellaszam).setText("Hely");
			sheet.getRange().get("B" + cellaszam).setText("Idő");
			sheet.getRange().get("C" + cellaszam).setText("Panel");
			cellaszam++;
			while(result.next())
			{
				sheet.getRange().get("A" + cellaszam).setText(result.getString(2));
				sheet.getRange().get("B" + cellaszam).setText(result.getString(3));
				sheet.getRange().get("C" + cellaszam).setText(result.getString(4));
				cellaszam++;
			}*/
			sheet.getAutoFilters().setRange(sheet.getCellRange("A1:P1"));
			sheet.getAllocatedRange().autoFitColumns();
			sheet.getAllocatedRange().autoFitRows();
			    
			sheet.getCellRange("A1:Z1").getCellStyle().getExcelFont().isBold(true);                          // fÃ©lkÃ¶vÃ©r beÃ¡llÃ­tÃ¡s
			
			result.close();
			cstmt.close();
			con.close();
			workbook.saveToFile(menteshelye.getAbsolutePath(), ExcelVersion.Version2016);
			
			FileInputStream fileStream = new FileInputStream(menteshelye.getAbsolutePath());
			try (XSSFWorkbook workbook = new XSSFWorkbook(fileStream)) 
			{
				for(int i = workbook.getNumberOfSheets()-1; i>0 ;i--)
				{    
					workbook.removeSheetAt(i); 
	            }      
				FileOutputStream output = new FileOutputStream(menteshelye.getAbsolutePath());
				workbook.write(output);
				output.close();
			}
			
			JOptionPane.showMessageDialog(null, "Mentve az asztalra Eredmények.xlsx néven", "Infó", 1);
		}
		catch (Exception e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
			String hibauzenet = e1.toString();  
            JOptionPane.showMessageDialog(null, hibauzenet, "Hiba üzenet", 2);
		} 
	}
}
