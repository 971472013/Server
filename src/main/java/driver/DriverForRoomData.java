package driver;

import data.implementation.RoomData;
import helper.RoomType;
import po.RoomPO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by apple on 2016/12/9.
 */
public class DriverForRoomData {

	private RoomData test = new RoomData();

	public static void main(String[] args){
		DriverForRoomData driver = new DriverForRoomData();
//		System.out.println(driver.testAddSingleRoom(new RoomPO(false, true, "314", "豪华代码房", 650, RoomType.BigBed), "000002"));
//		System.out.println(driver.testAddSingleRoom(new RoomPO(false, true, "313", "豪华代码房", 650, RoomType.BigBed), "000002"));
//		System.out.println(driver.testAddSingleRoom(new RoomPO(false, true, "312", "豪华代码房", 650, RoomType.BigBed), "000002"));
//		System.out.println(driver.testAddSingleRoom(new RoomPO(false, true, "503", "简约debug房", 480, RoomType.Single), "000002"));
//		System.out.println(driver.testAddSingleRoom(new RoomPO(false, true, "504", "简约debug房", 480, RoomType.Single), "000002"));
//		System.out.println(driver.testAddSingleRoom(new RoomPO(false, true, "505", "简约debug房", 480, RoomType.Single), "000002"));
//		System.out.println(driver.testAddSingleRoom(new RoomPO(false, true, "1024", "奢华海景测试房", 1024, RoomType.Suite), "000002"));
//		System.out.println(driver.testAddSingleRoom(new RoomPO(false, true, "1023", "奢华海景测试房", 1024, RoomType.Suite), "000002"));
//		System.out.println(driver.testAddSingleRoom(new RoomPO(false, true, "1025", "奢华海景测试房", 1024, RoomType.Suite), "000002"));
//		System.out.println(driver.testAddSingleRoom(new RoomPO(false, true, "609", "标准结对编程房", 760, RoomType.TwinBed), "000002"));
//		System.out.println(driver.testAddSingleRoom(new RoomPO(false, true, "608", "标准结对编程房", 760, RoomType.TwinBed), "000002"));
//		System.out.println(driver.testAddSingleRoom(new RoomPO(false, true, "610", "标准结对编程房", 760, RoomType.TwinBed), "000002"));
//		System.out.println(driver.testAddSingleRoom(new RoomPO(false, true, "3406", "豪华湖景房", 1500, RoomType.BigBed), "000000"));
//		System.out.println(driver.testAddSingleRoom(new RoomPO(false, true, "902", "商务单人间", 830, RoomType.Single), "000000"));
//		System.out.println(driver.testAddSingleRoom(new RoomPO(false, true, "817", "高档温馨家庭套房", 2050, RoomType.Suite), "000000"));
//		System.out.println(driver.testAddSingleRoom(new RoomPO(false, true, "2107", "景观园林双床房", 998, RoomType.TwinBed), "000000"));
//		System.out.println(driver.testAddSingleRoom(new RoomPO(false, true, "1308", "浪漫沙滩海景大床房", 350, RoomType.BigBed), "000001"));
//		System.out.println(driver.testAddSingleRoom(new RoomPO(false, true, "1516", "舒适阳光单人房", 210, RoomType.Single), "000001"));
//		System.out.println(driver.testAddSingleRoom(new RoomPO(false, true, "2001", "豪华泰式温泉套房", 520, RoomType.Suite), "000001"));
//		System.out.println(driver.testAddSingleRoom(new RoomPO(false, true, "906", "标准双床房", 370, RoomType.TwinBed), "000001"));
		Date day = new Date(2016, 11, 1);
//		System.out.println(driver.testUpdateSingleRoom(day, new RoomPO(false, true, "903", "标准舒适商务双床房", 370, RoomType.TwinBed), "000001"));
//		System.out.println(driver.testDeleteSingleRoom("2010", "000001"));
//		System.out.println(driver.testUpdateSingleRoom(day, new RoomPO(false, true, "906", "标准舒适商务双床房", 370, RoomType.TwinBed), "000001"));
//		System.out.println(driver.testDeleteSingleRoom("2001", "000001"));
//		System.out.println(driver.testGetSingleRoom(day, "314", "000002"));
//		System.out.println(driver.testGetSingleRoom(day, "304", "000002"));
//		System.out.println(driver.testReserve(day, "3406", "000000"));
//		System.out.println(driver.testReserve(day, "3406", "000000"));
//		System.out.println(driver.testCheckIn(day, "3406", "000000"));
//		System.out.println(driver.testCheckIn(day, "3406", "000000"));
//		System.out.println(driver.testCheckOut(day, "3406", "000000"));
//		System.out.println(driver.testCheckOut(day, "3406", "000000"));
		System.out.println(driver.testGetRoomByDate(day, "000002"));
		System.out.println(driver.testGetRoomByNameDate(day, "豪华代码房", "000002"));
		System.out.println(driver.testGetRoomByTypeDate(day, RoomType.TwinBed, "000002"));

	}

	/**
	 *
	 * @param room
	 * @param hotelID
	 * @return
	 */
	public boolean testAddSingleRoom(RoomPO room, String hotelID){
		System.out.println("Add a single room whose ID is "+room.getRoomID());
		return test.addSingleRoom(room, hotelID);
	}

	/**
	 *
	 * @param day
	 * @param roomID
	 * @param hotelID
	 * @return
	 */
	public boolean testGetSingleRoom(Date day, String roomID, String hotelID){
		System.out.println("Look up for a single room whose ID is "+roomID);
		RoomPO result = test.getSingleRoom(day, roomID,hotelID);
		if(result==null) return false;
		output(result);
		return true;
	}

	/**
	 *
	 * @param roomID
	 * @param hotelID
	 * @return
	 */
	public boolean testDeleteSingleRoom(String roomID, String hotelID){
		System.out.println("Delete a single room whose ID is "+roomID);
		return test.deleteSingleRoom(roomID, hotelID);
	}

	/**
	 *
	 * @param day
	 * @param room
	 * @param hotelID
	 * @return
	 */
	public boolean testUpdateSingleRoom(Date day, RoomPO room, String hotelID){
		System.out.println("Update a single room whose ID is "+room.getRoomID());
		return test.updateSingleRoom(day, room, hotelID);
	}

	/**
	 *
	 * @param day
	 * @param hotelID
	 * @return
	 */
	public boolean testGetRoomByDate(Date day, String hotelID){
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("MM-dd-yyyy");
		System.out.println("Look up for rooms of hotel "+hotelID+" on "+bartDateFormat.format(day));
		ArrayList<RoomPO> result = test.getRoomsByDate(day, hotelID);
		if(result==null) return false;
		for (RoomPO thisRoom: result
		     ) {
			output(thisRoom);
		}
		return true;
	}

	/**
	 *
	 * @param day
	 * @param roomName
	 * @param hotelID
	 * @return
	 */
	public boolean testGetRoomByNameDate(Date day, String roomName, String hotelID){
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("MM-dd-yyyy");
		System.out.println("Look up for rooms of hotel "+hotelID+" whose name is "+roomName+" on "+bartDateFormat.format(day));
		ArrayList<RoomPO> result = test.getRoomsByNameDate(day, roomName, hotelID);
		if(result==null) return false;
		for (RoomPO thisRoom: result
				) {
			output(thisRoom);
		}
		return true;
	}

	/**
	 *
	 * @param day
	 * @param roomType
	 * @param hotelID
	 * @return
	 */
	public boolean testGetRoomByTypeDate(Date day, RoomType roomType, String hotelID){
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("MM-dd-yyyy");
		System.out.println("Look up for rooms of hotel "+hotelID+" whose type is "+roomType+" on "+bartDateFormat.format(day));
		ArrayList<RoomPO> result = test.getRoomsByTypeDate(day,roomType, hotelID);
		if(result==null) return false;
		for (RoomPO thisRoom: result
				) {
			output(thisRoom);
		}
		return true;
	}

	/**
	 *
	 * @param day
	 * @param roomNUM
	 * @param hotelID
	 * @return
	 */
	public boolean testReserve(Date day, String roomNUM, String hotelID){
		SimpleDateFormat bartDateFormate = new SimpleDateFormat("MM-dd-yyyy");
		System.out.println("Reserve "+roomNUM+" room on "+bartDateFormate.format(day)+" of hotel "+hotelID);
		return test.reserveSingleRoom(day, roomNUM, hotelID);
	}

	/**
	 *
	 * @param day
	 * @param roomNUM
	 * @param hotelID
	 * @return
	 */
	public boolean testCheckIn(Date day, String roomNUM, String hotelID){
		SimpleDateFormat bartDateFormate = new SimpleDateFormat("MM-dd-yyyy");
		System.out.println("Check in "+roomNUM+" room on "+bartDateFormate.format(day)+" of hotel "+hotelID);
		return test.checkIn(day, roomNUM, hotelID);
	}

	/**
	 *
	 * @param day
	 * @param roomNUM
	 * @param hotelID
	 * @return
	 */
	public boolean testCheckOut(Date day, String roomNUM, String hotelID){
		SimpleDateFormat bartDateFormate = new SimpleDateFormat("MM-dd-yyyy");
		System.out.println("Check out "+roomNUM+" room on "+bartDateFormate.format(day)+" of hotel "+hotelID);
		return test.checkOut(day, roomNUM, hotelID);
	}

	/**
	 *
	 * @param room
	 */
	private void output(RoomPO room){
		System.out.println("roomType: "+room.getRoomType());
		System.out.println("roomName: "+room.getRoomName());
		System.out.println("roomID: "+room.getRoomID());
		System.out.println("price: "+room.getPrice());
		System.out.println("isRserved: "+room.isReserved());
		System.out.println("isAvailable: "+room.isAvailable());
	}
}
