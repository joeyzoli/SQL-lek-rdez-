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
	private JdbcAdapter jdbcAdapter;
	private JdbcAdapter jdbcAdapter2;
	private DataTable datatable;
	private DataTable datatable2;
	private Workbook workbook;
	private Statement cstmt;
	private Connection con;
	
	void kiir(String osszefuzott, String osszefuzott2, File menteshelye)
	{
		try
		{
			//Registering the Driver
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());														//jdbc mysql driver meghívása
				
			//Getting the connection
			String mysqlUrl = "jdbc:mysql://192.168.5.145/";																		//mysql szerver ipcíméhez való csatlakozás
			con = DriverManager.getConnection(mysqlUrl, "quality", "Qua25!");											//a megadott ip-re csatlakozik a jelszó felhasználó névvel
			System.out.println("Connection established......");
			
			SQL_lekerdezo.progressBar.setValue(10);
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
			
			Statement cstmt = con.createStatement(
	                ResultSet.TYPE_SCROLL_INSENSITIVE,
	                ResultSet.CONCUR_UPDATABLE);
			
			Statement cstmt2 = con.createStatement(
	                ResultSet.TYPE_SCROLL_INSENSITIVE,
	                ResultSet.CONCUR_UPDATABLE);
			//cstmt = con.prepareStatement(sql);	  
			//cstmt.setString(1, osszefuzott);			//osszefuzott																			//tárolt eljárás paparméterénk megadása
				  
			cstmt.execute(sql);																										//sql lejkérdezés futtatása
			SQL_lekerdezo.progressBar.setValue(20);  
			result = cstmt.getResultSet();																								//az sql lekérdezés tartalmát odaadja egy result set változónak
			//result.next();
			//System.out.println(result.getInt(2));
			
			datatable = new DataTable();
			datatable2 = new DataTable();
			workbook = new Workbook();
			workbook.setVersion(ExcelVersion.Version2013); 
			jdbcAdapter = new JdbcAdapter();
			jdbcAdapter2 = new JdbcAdapter();
			
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
			
			System.out.println("Első SQL");
			
			if(osszefuzott2 != "")
        	{
				cstmt2.execute(sql2);																										//sql lejkérdezés futtatása
				SQL_lekerdezo.progressBar.setValue(70);  
				result2 = cstmt2.getResultSet();																								//az sql lekérdezés tartalmát odaadja egy result set változónak
				//result.next();
				//System.out.println(result.getInt(2));
				
				
				jdbcAdapter2.fillDataTable(datatable2, result2);
				SQL_lekerdezo.progressBar.setValue(80);
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
			
			SQL_lekerdezo.progressBar.setValue(100);
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
			//workbook.saveToFile(menteshelye.getAbsolutePath(), ExcelVersion.Version2016);
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
	
	void fajlbair(File menteshelye)
	{
		workbook.saveToFile(menteshelye.getAbsolutePath(), ExcelVersion.Version2016);
	}
}
