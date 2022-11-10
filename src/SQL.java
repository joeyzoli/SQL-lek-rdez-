import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;
import com.spire.data.table.DataTable;
import com.spire.data.table.common.JdbcAdapter;
import com.spire.xls.ExcelVersion;
import com.spire.xls.Workbook;
import com.spire.xls.Worksheet;

public class SQL 
{
	private ResultSet result;
	private ResultSet result2;
	private ResultSet result3;
	private ResultSet result4;
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
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());														//jdbc mysql driver meghívása
				
			//Getting the connection
			String mysqlUrl = "jdbc:mysql://192.168.5.145/";																		//mysql szerver ipcíméhez való csatlakozás
			con = DriverManager.getConnection(mysqlUrl, "quality", "Qua25!");											//a megadott ip-re csatlakozik a jelszó felhasználó névvel
			System.out.println("Connection established......");
			
			SQL_lekerdezo.szazalek = 70;
			//SQL_lekerdezo.progressBar.setValue(10);
			//Preparing a CallableStatement to call a procedure
			// CallableStatement cstmt = con.prepareCall("{call videoton.veas_avmheti_teszt(?)}");									//tárolt eljárás meghívása      videoton.veas_avmheti_teszt
			String sql = "select 	videoton.fkov.azon, videoton.fkov.hely,videoton.fkovsor.nev, videoton.fkov.ido, videoton.fkov.panel, if(videoton.fkov.ok in ('-1', '1'), \"Rendben\", \"Hiba\") as eredmeny, "
					+ "videoton.fkov.hibakod, videoton.fkov.kod2, videoton.fkov.torolt, "
					+ "videoton.fkov.szeriaszam, videoton.fkov.tesztszam, videoton.fkov.poz, videoton.fkov.teljesszam, videoton.fkov.failtestnames, videoton.fkov.error,"
					+ "videoton.fkov.dolgozo \n"
					+ "from	videoton.fkov \n"
					+ "inner join videoton.fkovsor on videoton.fkovsor.azon = videoton.fkov.hely \n"
					+ " where panel in (" + osszefuzott +")";
			
			String sql2 = "select 	videoton.fkov.azon, videoton.fkov.hely,videoton.fkovsor.nev, videoton.fkov.ido, videoton.fkov.panel, if(videoton.fkov.ok in ('-1', '1'), \"Rendben\", \"Hiba\") as eredmeny, "
					+ "videoton.fkov.hibakod, videoton.fkov.kod2, videoton.fkov.torolt, "
					+ "videoton.fkov.szeriaszam, videoton.fkov.tesztszam, videoton.fkov.poz, videoton.fkov.teljesszam, videoton.fkov.failtestnames, videoton.fkov.error,"
					+ "videoton.fkov.dolgozo \n"
					+ "from	videoton.fkov \n"
					+ "inner join videoton.fkovsor on videoton.fkovsor.azon = videoton.fkov.hely \n"
					+ " where panel in (" + osszefuzott2 +")";
			
			String sql3 = "select 	videoton.fkov.azon, videoton.fkov.hely,videoton.fkovsor.nev, videoton.fkov.ido, videoton.fkov.panel, if(videoton.fkov.ok in ('-1', '1'), \"Rendben\", \"Hiba\") as eredmeny, "
					+ "videoton.fkov.hibakod, videoton.fkov.kod2, videoton.fkov.torolt, "
					+ "videoton.fkov.szeriaszam, videoton.fkov.tesztszam, videoton.fkov.poz, videoton.fkov.teljesszam, videoton.fkov.failtestnames, videoton.fkov.error,"
					+ "videoton.fkov.dolgozo \n"
					+ "from	videoton.fkov \n"
					+ "inner join videoton.fkovsor on videoton.fkovsor.azon = videoton.fkov.hely \n"
					+ " where panel in (" + osszefuzott3 +")";
			
			String sql4 = "select 	videoton.fkov.azon, videoton.fkov.hely,videoton.fkovsor.nev, videoton.fkov.ido, videoton.fkov.panel, if(videoton.fkov.ok in ('-1', '1'), \"Rendben\", \"Hiba\") as eredmeny, "
					+ "videoton.fkov.hibakod, videoton.fkov.kod2, videoton.fkov.torolt, "
					+ "videoton.fkov.szeriaszam, videoton.fkov.tesztszam, videoton.fkov.poz, videoton.fkov.teljesszam, videoton.fkov.failtestnames, videoton.fkov.error,"
					+ "videoton.fkov.dolgozo \n"
					+ "from	videoton.fkov \n"
					+ "inner join videoton.fkovsor on videoton.fkovsor.azon = videoton.fkov.hely \n"
					+ " where panel in (" + osszefuzott4 +")";
			
			Statement cstmt = con.createStatement(
	                ResultSet.TYPE_SCROLL_INSENSITIVE,
	                ResultSet.CONCUR_UPDATABLE);
			
			Statement cstmt2 = con.createStatement(
	                ResultSet.TYPE_SCROLL_INSENSITIVE,
	                ResultSet.CONCUR_UPDATABLE);
			
			Statement cstmt3 = con.createStatement(
	                ResultSet.TYPE_SCROLL_INSENSITIVE,
	                ResultSet.CONCUR_UPDATABLE);
			
			Statement cstmt4 = con.createStatement(
	                ResultSet.TYPE_SCROLL_INSENSITIVE,
	                ResultSet.CONCUR_UPDATABLE);
			
			cstmt.execute(sql);																										//sql lejkérdezés futtatása
			
			SQL_lekerdezo.szazalek = 20;
			result = cstmt.getResultSet();																								//az sql lekérdezés tartalmát odaadja egy result set változónak
			
			datatable = new DataTable();
			datatable2 = new DataTable();
			datatable3 = new DataTable();
			datatable4 = new DataTable();
			workbook = new Workbook();
			workbook.setVersion(ExcelVersion.Version2013); 
			jdbcAdapter = new JdbcAdapter();
			jdbcAdapter2 = new JdbcAdapter();
			jdbcAdapter3 = new JdbcAdapter();
			jdbcAdapter4 = new JdbcAdapter();
			
			SQL_lekerdezo.szazalek = 40;
			jdbcAdapter.fillDataTable(datatable, result);
			SQL_lekerdezo.szazalek = 50;
			
			//Get the first worksheet
			Worksheet sheet = workbook.getWorksheets().get(0);
			sheet.insertDataTable(datatable, true, 1, 1);
			sheet.getAutoFilters().setRange(sheet.getCellRange("A1:P1"));
			sheet.getAllocatedRange().autoFitColumns();
			sheet.getAllocatedRange().autoFitRows();
			    
			sheet.getCellRange("A1:Z1").getCellStyle().getExcelFont().isBold(true);                          // félkövér beállítás
			
			System.out.println("Első SQL");
			
			if(osszefuzott2 != "")
        	{
				cstmt2.execute(sql2);																										//sql lejkérdezés futtatása
				
				SQL_lekerdezo.szazalek = 70;
				result2 = cstmt2.getResultSet();																								//az sql lekérdezés tartalmát odaadja egy result set változónak
				
				jdbcAdapter2.fillDataTable(datatable2, result2);
				SQL_lekerdezo.szazalek = 80;
				//Get the first worksheet
				Worksheet sheet2 = workbook.getWorksheets().get(1);
				sheet2.insertDataTable(datatable2, true, 1, 1);
				sheet2.getAutoFilters().setRange(sheet2.getCellRange("A1:P1"));
				sheet2.getAllocatedRange().autoFitColumns();
				sheet2.getAllocatedRange().autoFitRows();
				    
				sheet2.getCellRange("A1:Z1").getCellStyle().getExcelFont().isBold(true);                          // félkövér beállítás
				
				System.out.println("Második SQL");
				
				int a = workbook.getWorksheets().get(1).getLastRow();
				int b = workbook.getWorksheets().get(1).getLastColumn();
				
				sheet2.getCellRange(2, 1, a, b).copy(sheet.getCellRange(sheet.getLastRow()+1, 1, a + sheet.getLastRow(), b));
				sheet2.remove();
        	}
			
			if(osszefuzott3 != "")
        	{
				cstmt3.execute(sql3);																										//sql lejkérdezés futtatása
				
				SQL_lekerdezo.szazalek = 70;
				result3 = cstmt3.getResultSet();																								//az sql lekérdezés tartalmát odaadja egy result set változónak
				
				jdbcAdapter3.fillDataTable(datatable3, result3);
				SQL_lekerdezo.szazalek = 80;
				//Get the first worksheet
				Worksheet sheet3 = workbook.getWorksheets().get(1);
				sheet3.insertDataTable(datatable3, true, 1, 1);
				sheet3.getAutoFilters().setRange(sheet3.getCellRange("A1:P1"));
				sheet3.getAllocatedRange().autoFitColumns();
				sheet3.getAllocatedRange().autoFitRows();
				    
				sheet3.getCellRange("A1:Z1").getCellStyle().getExcelFont().isBold(true);                          // félkövér beállítás
				
				System.out.println("Harmadik SQL");
				
				int a = workbook.getWorksheets().get(1).getLastRow();
				int b = workbook.getWorksheets().get(1).getLastColumn();
				
				sheet3.getCellRange(2, 1, a, b).copy(sheet.getCellRange(sheet.getLastRow()+1, 1, a + sheet.getLastRow(), b));
				sheet3.remove();
        	}
			
			if(osszefuzott4 != "")
        	{
				cstmt4.execute(sql4);																										//sql lejkérdezés futtatása
				
				SQL_lekerdezo.szazalek = 70;
				result4 = cstmt4.getResultSet();																								//az sql lekérdezés tartalmát odaadja egy result set változónak
				
				jdbcAdapter4.fillDataTable(datatable4, result4);
				SQL_lekerdezo.szazalek = 80;
				//Get the first worksheet
				Worksheet sheet3 = workbook.getWorksheets().get(0);
				sheet3.insertDataTable(datatable4, true, 1, 1);
				sheet3.getAutoFilters().setRange(sheet3.getCellRange("A1:P1"));
				sheet3.getAllocatedRange().autoFitColumns();
				sheet3.getAllocatedRange().autoFitRows();
				    
				sheet3.getCellRange("A1:Z1").getCellStyle().getExcelFont().isBold(true);                          // félkövér beállítás
				
				System.out.println("Negyedik SQL");
				
				int a = workbook.getWorksheets().get(0).getLastRow();
				int b = workbook.getWorksheets().get(0).getLastColumn();
				
				sheet3.getCellRange(2, 1, a, b).copy(sheet.getCellRange(sheet.getLastRow()+1, 1, a + sheet.getLastRow(), b));
				//sheet3.remove();
        	}
			
			SQL_lekerdezo.szazalek = 100;
			result.close();
			cstmt.close();
			con.close();
			workbook.saveToFile(menteshelye.getAbsolutePath(), ExcelVersion.Version2016);
			
			JOptionPane.showMessageDialog(null, "Mentés sikeres", "Infó", 1);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			String hibauzenet2 = e.toString();
			JOptionPane.showMessageDialog(null, hibauzenet2, "Hiba üzenet", 2);
		}
		finally                                                                     //finally rész mindenképpen lefut, hogy hiba esetén is lezárja a kacsolatot
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
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());														//jdbc mysql driver meghívása
				
			//Getting the connection
			String mysqlUrl = "jdbc:mysql://192.168.5.145/";																		//mysql szerver ipcíméhez való csatlakozás
			con = DriverManager.getConnection(mysqlUrl, "quality", "Qua25!");											//a megadott ip-re csatlakozik a jelszó felhasználó névvel
			System.out.println("Connection established......");
			
			SQL_lekerdezo.szazalek = 70;
			//SQL_lekerdezo.progressBar.setValue(10);
			//Preparing a CallableStatement to call a procedure
			// CallableStatement cstmt = con.prepareCall("{call videoton.veas_avmheti_teszt(?)}");									//tárolt eljárás meghívása      videoton.veas_avmheti_teszt
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
			
			cstmt.execute(sql);																										//sql lejkérdezés futtatása
			
			SQL_lekerdezo.szazalek = 20;
			result = cstmt.getResultSet();																								//az sql lekérdezés tartalmát odaadja egy result set változónak
			
			datatable = new DataTable();
			
			workbook = new Workbook();
			workbook.setVersion(ExcelVersion.Version2013); 
			jdbcAdapter = new JdbcAdapter();
			
			SQL_lekerdezo.szazalek = 40;
			jdbcAdapter.fillDataTable(datatable, result);
			SQL_lekerdezo.szazalek = 50;
			
			//Get the first worksheet
			Worksheet sheet = workbook.getWorksheets().get(0);
			Worksheet sheet2 = workbook.getWorksheets().get(1);
			Worksheet sheet3 = workbook.getWorksheets().get(2);
			sheet.insertDataTable(datatable, true, 1, 1);
			sheet.getAutoFilters().setRange(sheet.getCellRange("A1:P1"));
			sheet.getAllocatedRange().autoFitColumns();
			sheet.getAllocatedRange().autoFitRows();
			    
			sheet.getCellRange("A1:Z1").getCellStyle().getExcelFont().isBold(true);                          // félkövér beállítás
			sheet2.remove();
			sheet3.remove();
			SQL_lekerdezo.szazalek = 100;
			result.close();
			cstmt.close();
			con.close();
			workbook.saveToFile(menteshelye.getAbsolutePath(), ExcelVersion.Version2016);
			
			JOptionPane.showMessageDialog(null, "Mentés sikeres", "Infó", 1);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			String hibauzenet2 = e.toString();
			JOptionPane.showMessageDialog(null, hibauzenet2, "Hiba üzenet", 2);
		}
		finally                                                                     //finally rész mindenképpen lefut, hogy hiba esetén is lezárja a kacsolatot
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
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());														//jdbc mysql driver meghívása
	
			//Getting the connection
			String mysqlUrl = "jdbc:mysql://192.168.5.145/";																		//mysql szerver ipcíméhez való csatlakozás
			Connection con = DriverManager.getConnection(mysqlUrl, "quality", "Qua25!");											//a megadott ip-re csatlakozik a jelszó felhasználó névvel
			System.out.println("Connection established......");
			
			Statement cstmt = con.createStatement();
			
			String sql = "select 	videoton.fkov.azon, videoton.fkov.hely,videoton.fkovsor.nev, videoton.fkov.ido, videoton.fkov.panel, if(videoton.fkov.ok in ('-1', '1'), \"Rendben\", \"Hiba\") as eredmeny, "
					+ "videoton.fkov.hibakod, videoton.fkov.kod2, videoton.fkov.torolt, "
					+ "videoton.fkov.szeriaszam, videoton.fkov.tesztszam, videoton.fkov.poz, videoton.fkov.teljesszam, videoton.fkov.failtestnames, videoton.fkov.error,"
					+ "videoton.fkov.dolgozo \n"
					+ "from	videoton.fkov \n"
					+ "inner join videoton.fkovsor on videoton.fkovsor.azon = videoton.fkov.hely \n"
					+ " where " + osszefuzott +"";
			
			cstmt.execute(sql);																										//sql lejkérdezés futtatása
			
			result = cstmt.getResultSet();
			
			datatable = new DataTable();
			
			workbook = new Workbook();
			workbook.setVersion(ExcelVersion.Version2013); 
			jdbcAdapter = new JdbcAdapter();
			
			SQL_lekerdezo.progressBar.setValue(40);
			jdbcAdapter.fillDataTable(datatable, result);
			SQL_lekerdezo.progressBar.setValue(50);
			
			//Get the first worksheet
			Worksheet sheet = workbook.getWorksheets().get(0);
			sheet.insertDataTable(datatable, true, 1, 1);
			sheet.getAutoFilters().setRange(sheet.getCellRange("A1:P1"));
			sheet.getAllocatedRange().autoFitColumns();
			sheet.getAllocatedRange().autoFitRows();
			    
			sheet.getCellRange("A1:Z1").getCellStyle().getExcelFont().isBold(true);                          // félkövér beállítás
			
			SQL_lekerdezo.progressBar.setValue(100);
			result.close();
			cstmt.close();
			con.close();
			workbook.saveToFile(menteshelye.getAbsolutePath(), ExcelVersion.Version2016);
			
			JOptionPane.showMessageDialog(null, "Mentés sikeres", "Infó", 1);
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
