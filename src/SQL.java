import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
	private JdbcAdapter jdbcAdapter;
	private DataTable datatable;
	private Workbook workbook;
	
	void kiir(String osszefuzott, File menteshelye, int szam)
	{
		try
		{
			//Registering the Driver
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());														//jdbc mysql driver meghívása
				
			//Getting the connection
			String mysqlUrl = "jdbc:mysql://192.168.5.145/";																		//mysql szerver ipcíméhez való csatlakozás
			Connection con = DriverManager.getConnection(mysqlUrl, "quality", "Qua25!");											//a megadott ip-re csatlakozik a jelszó felhasználó névvel
			System.out.println("Connection established......");
				  
			//Preparing a CallableStatement to call a procedure
			// CallableStatement cstmt = con.prepareCall("{call videoton.veas_avmheti_teszt(?)}");									//tárolt eljárás meghívása      videoton.veas_avmheti_teszt
			String sql = "select 	videoton.fkov.azon, videoton.fkov.hely,videoton.fkovsor.nev, videoton.fkov.ido, videoton.fkov.panel, if(videoton.fkov.ok in ('-1', '1'), \"Rendben\", \"Hiba\") as eredmeny, "
					+ "videoton.fkov.hibakod, videoton.fkov.kod2, videoton.fkov.torolt, "
					+ "videoton.fkov.szeriaszam, videoton.fkov.tesztszam, videoton.fkov.poz, videoton.fkov.teljesszam, videoton.fkov.failtestnames, videoton.fkov.error,"
					+ "videoton.fkov.dolgozo \n"
					+ "from	videoton.fkov \n"
					+ "inner join videoton.fkovsor on videoton.fkovsor.azon = videoton.fkov.hely \n"
					+ " where panel in (" +osszefuzott +")";
			
			Statement cstmt = con.createStatement(
	                ResultSet.TYPE_SCROLL_INSENSITIVE,
	                ResultSet.CONCUR_UPDATABLE);
			//cstmt = con.prepareStatement(sql);	  
			//cstmt.setString(1, osszefuzott);			//osszefuzott																			//tárolt eljárás paparméterénk megadása
				  
			cstmt.execute(sql);																										//sql lejkérdezés futtatása
			  
			result = cstmt.getResultSet();																								//az sql lekérdezés tartalmát odaadja egy result set változónak
			//result.next();
			//System.out.println(result.getInt(2));
			
			datatable = new DataTable();
			workbook = new Workbook();
			jdbcAdapter = new JdbcAdapter();
			
			jdbcAdapter.fillDataTable(datatable, result);
			
			//Get the first worksheet
			Worksheet sheet = workbook.getWorksheets().get(szam);
			sheet.insertDataTable(datatable, true, 1, 1);
			sheet.getAutoFilters().setRange(sheet.getCellRange("A1:P1"));
			sheet.getAllocatedRange().autoFitColumns();
			sheet.getAllocatedRange().autoFitRows();
			    
			sheet.getCellRange("A1:Z1").getCellStyle().getExcelFont().isBold(true);                          // félkövér beállítás
			    
			workbook.saveToFile(menteshelye.getAbsolutePath(), ExcelVersion.Version2016);
			result.close();
			cstmt.close();
			con.close();
			JOptionPane.showMessageDialog(null, "Mentés sikeres", "Infó", 1);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			String hibauzenet2 = e.toString();
			JOptionPane.showMessageDialog(null, hibauzenet2, "Hiba üzenet", 2);
		}
	}
}
