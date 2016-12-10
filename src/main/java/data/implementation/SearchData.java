package data.implementation;

import data.dataservice.SearchDataService;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import po.HotelPO;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by apple on 2016/12/5.
 */
public class SearchData implements SearchDataService {

	private int dataSize = 13;
	private String sourceFile = "HotelData.xls";private Workbook book;
	private Sheet sheet;
	private WritableWorkbook wBook;
	private WritableSheet wSheet;

	/**
	 *
	 * @param city
	 * @param district
	 * @return
	 */
	public ArrayList<HotelPO> getHotelListByCityDistrict(String city, String district) {
		createWritableSheet();
		sheet = book.getSheet(0);
		int col = 0;
		int row = hash(city+district);
		ArrayList<HotelPO> result = new ArrayList<HotelPO>();
		while(wSheet.getCell(col, row).getContents()!=""){
			if(!wSheet.getCell(col, row).getContents().equals("-1")){
				result.add(getHotelByPosition(col, row));
			}
			col+=dataSize;
		}
		close();
		if(result.size()==0) return null;       //There is no hotel sighed in the district of the city.
		return result;
	}

	/**
	 *
	 * @param lowScore
	 * @param highScore
	 * @param city
	 * @param district
	 * @return
	 */
	public ArrayList<HotelPO> getHotelListSortedByScore(double lowScore, double highScore, String city, String district) {
		ArrayList<HotelPO> temp = getHotelListByCityDistrict(city, district);
		ArrayList<HotelPO> result = new ArrayList<>();
		if(temp==null) return null;       //There is no hotel sighed in the district of the city.
		for (HotelPO thisHotel: temp) {
			if(thisHotel.getScore()>=lowScore&&thisHotel.getScore()<=highScore){
				result.add(thisHotel);
			}
		}
		if(result.size()==0) return null;       //There is no hotel sighed in the district of the city whose score is between lowScore and highScore.
		return result;
	}

	/**
	 *
	 * @param level
	 * @param city
	 * @param district
	 * @return
	 */
	public ArrayList<HotelPO> getHotelListFilteredByLevel(int level, String city, String district) {
		ArrayList<HotelPO> temp = getHotelListByCityDistrict(city, district);
		ArrayList<HotelPO> result = new ArrayList<>();
		if(temp==null) return null;       //There is no hotel sighed in the district of the city.
		for (HotelPO thisHotel: temp) {
			if(thisHotel.getLevel()==level){
				result.add(thisHotel);
			}
		}
//		for (int i = 0; i < result.size(); i++) {
//			if(result.get(i).getLevel()!=level){
//				result.remove(i);
//			}
//		}
		if(result.size()==0) return null;       //There is no hotel sighed in the district of the city whose level equals to "level".
		return result;
	}

	/**
	 *
	 * @param lowPrice
	 * @param highPrice
	 * @param city
	 * @param district
	 * @return
	 */
	public ArrayList<HotelPO> getHotelListFilteredByPrice(double lowPrice, double highPrice, String city, String district) {
		ArrayList<HotelPO> temp = getHotelListByCityDistrict(city, district);
		ArrayList<HotelPO> result = new ArrayList<>();
		if(temp==null) return null;       //There is no hotel sighed in the district of the city.
		RoomData rooms = new RoomData();
		for (HotelPO thisHotel: temp) {
			if(rooms.hasSuitableRoom(lowPrice, highPrice, thisHotel.getUserID())){
				result.add(thisHotel);
			}
		}
		if(result.size()==0) return null;  //There is no hotel fits the condition.
		return result;
	}
	/**
	 *
	 */
	private void close(){
		write();
		try {
			wBook.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}
		book.close();
	}


	/**
	 *
	 */
	private void write(){
		try {
			wBook.write();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 * @param hotelScope
	 * @return
	 */
	private int hash(String hotelScope){
		int result = hotelScope.hashCode();
		if(result<0)result = 0-result;
		result%=10;
		return result;
	}

	/**
	 *
	 * @param col
	 * @param row
	 * @return
	 */
	private HotelPO getHotelByPosition(int col, int row){
		String ID = sheet.getCell(col, row).getContents();
		col++;
		String password = sheet.getCell(col, row).getContents();
		col++;
		String name = sheet.getCell(col, row).getContents();
		col++;
		String city = sheet.getCell(col, row).getContents();
		col++;
		String district = sheet.getCell(col, row).getContents();
		col++;
		String address = sheet.getCell(col, row).getContents();
		col++;
		String service = sheet.getCell(col, row).getContents();
		col++;
		String introduction = sheet.getCell(col, row).getContents();
		col++;
		String managerName = sheet.getCell(col, row).getContents();
		col++;
		String managerTel = sheet.getCell(col, row).getContents();
		col++;
		int level = (int)((NumberCell)sheet.getCell(col, row)).getValue();
		col++;
		double score = ((NumberCell)sheet.getCell(col, row)).getValue();
		col++;
		String totalEnterprise = sheet.getCell(col, row).getContents();
		String[] temp = totalEnterprise.split(";");
		ArrayList<String> enterprise = new ArrayList<String>();
		for (String anEnterprise: temp) {
			enterprise.add(anEnterprise);
		}
		return new HotelPO(ID,password,name,address,district,city,level,score,service,introduction,managerName,managerTel,enterprise);
	}

	/**
	 * 用来初始化sheet
	 *
	 */
	private void createSheet(){
		try {
			try {
				book=Workbook.getWorkbook(new File(sourceFile));
				sheet = book.getSheet(0);
			} catch (BiffException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 *用来初始化wSheet
	 *
	 */
	private void createWritableSheet(){
		try {
			try {
				book=Workbook.getWorkbook(new File(sourceFile));
				wBook = Workbook.createWorkbook(new File(sourceFile),book);
				wSheet = wBook.getSheet(0);
			} catch (BiffException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
