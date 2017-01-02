package driver;

import data.implementation.HotelData;
import po.HotelPO;

import java.util.ArrayList;

/**
 * Created by apple on 2016/12/8.
 */
public class DriverForHotelData {

	private HotelData test = new HotelData();

	public static void main(String[] args){
		DriverForHotelData driver = new DriverForHotelData();
		ArrayList<String> enterprise = new ArrayList<String>();
		enterprise.add("刚轻厂");
		enterprise.add("新悦集团");
//		System.out.println(driver.testAdd(new HotelPO(driver.testGetID(),"kunkun","CroffHotel", "南京市", "新街口", "珠江路",5, 4.8,"健身房、游泳池、spa","是一家专门针对高端人群的酒店。"
//				,"张新悦", "15205153967",enterprise, false)));
//		System.out.println(driver.testAdd(new HotelPO(driver.testGetID(),"jingjing","亚婧大酒店", "南京市", "新街口", "石鼓路",5, 4.8,"健身房、自助餐厅、温泉","是亲子度假首选。"
//				,"徐亚婧", "15205153967",enterprise, false)));
//		System.out.println(driver.testAdd(new HotelPO(driver.testGetID(),"helloworld","JavaHotel", "南京市", "仙林中心", "仙林大道",5, 4.8,"健身房、游泳池、spa","是一家专门针对高端人群的酒店"
//				,"eclipse", "15205153967",enterprise, false)));
//		System.out.println(driver.testDelete("000007"));
//		System.out.println(driver.testUpdate(new HotelPO("000001","jingjing","亚婧大酒店", "南京市", "新街口", "石鼓路", 3, 4.0,"健身房、自助餐厅、温泉","是亲子度假首选。"
//				,"徐亚婧", "15205153967",enterprise, false)));
//		driver.testGetHotelByID("000001");
//		driver.testGetHotelByName("JavaHotel");
//		System.out.println(driver.testGetHotelByID("000101"));
		System.out.println(driver.testUpdate(new HotelPO("000004","inlinefunction","CppHotel++", "南京市", "新街口", "helloworld大道",4, 4.5,"健身房、游泳池、spa","是一家专门针对有梦想的程序员的酒店"
				,"vs", "13151522158",enterprise, false)));
		System.out.println(driver.testGetHotelByName("CppHotel++"));
	}

	/**
	 *
	 * @param hotel
	 * @return
	 */
	public boolean testAdd(HotelPO hotel){
		System.out.println("Add a hotel named "+hotel.getName());
		return test.addHotel(hotel);
	}

	/**
	 *
	 * @param hotelID
	 * @return
	 */
	public boolean testDelete(String hotelID){
		System.out.println("Delete a hotel whose ID is "+hotelID);
		return test.deleteHotel(hotelID);
	}

	/**
	 *
	 * @param hotel
	 * @return
	 */
	public boolean testUpdate(HotelPO hotel){
		System.out.println("Update a hotel named "+hotel.getName());
		return test.updateHotel(hotel);
	}

	/**
	 *
	 * @param hotelID
	 */
	public boolean testGetHotelByID(String hotelID){
		System.out.println("Find a hotel whose ID is "+hotelID);
		HotelPO result = test.getHotelByID(hotelID);
		if(result==null) return false;
		outputHotel(result);
		return true;
	}

	/**
	 *
	 * @param hotelName
	 */
	public boolean testGetHotelByName(String hotelName){
		System.out.println("Find a hotel whose name is "+hotelName);
		HotelPO result = test.getHotelByName(hotelName);
		if(result==null) return false;
		outputHotel(result);
		return true;
	}

	public String testGetID(){
		System.out.println("Get a new ID for hotel");
		String result = test.getAvailableHotelID();
		System.out.println(result);
		return result;
	}

	/**
	 *
	 * @param hotel
	 */
	private void outputHotel(HotelPO hotel){
		System.out.println("hotelID: "+hotel.getUserID());
		System.out.println("password: "+hotel.getPassword());
		System.out.println("name: "+hotel.getName());
		System.out.println("city: "+hotel.getCity());
		System.out.println("district: "+hotel.getDistrict());
		System.out.println("address: "+hotel.getAddress());
		System.out.println("introduction: "+hotel.getIntroduction());
		System.out.println("service: "+hotel.getService());
		System.out.println("level: "+hotel.getLevel());
		System.out.println("score: "+hotel.getScore());
		System.out.println("managerName: "+hotel.getManagerName());
		System.out.println("managerTel: "+hotel.getManagerTel());
		System.out.print("enterprise: ");
		ArrayList<String> enterprise = hotel.getEnterpriseList();
		for (String temp: enterprise) {
			System.out.print(temp+" ");
		}
		System.out.println();
	}
}
