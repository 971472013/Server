package data.implementation;

import data.dataservice.OrderDataService;
import jxl.Cell;
import jxl.DateCell;
import jxl.NumberCell;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.*;
import jxl.write.Number;
import po.OrderPO;
import helper.OrderStatus;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by apple on 2016/12/1.
 */
public class OrderDataForS implements OrderDataService {

	private int dataSize = 19;
	private String sourceFile = "OrderForMember.xls";
	private Workbook book;
	private WritableWorkbook wBook;
	private WritableSheet sheet;
	private OrderDataForH sync;

	public OrderDataForS(){
		sync = new OrderDataForH();
		try {
			book = Workbook.getWorkbook(new File(sourceFile));
			wBook = Workbook.createWorkbook(new File(sourceFile),book);
			sheet = wBook.getSheet(0);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BiffException e) {
			e.printStackTrace();
		}
	}

	public boolean addOrder(OrderPO order) {
		return false;
	}

	public boolean updateOrder(OrderPO order) {
		WritableCell orderStart = (WritableCell) sheet.findCell(order.getOrderID());
		int col = orderStart.getColumn()+1;
		int row = orderStart.getRow();
		Label memberID = new Label(col,row,order.getMemberID());
		col++;
		Label hotelID = new Label(col, row, order.getHotelID());
		col++;
		Label evaluation = new Label(col, row, order.getEvaluation());
		col++;
		Label promotion = new Label(col, row, order.getPromotionID());
		col++;
		DateTime checkIn = new DateTime(col, row, order.getCheckinTime());
		col++;
		DateTime checkOut = new DateTime(col, row, order.getCheckoutTime());
		col++;
		DateTime latestCheckIn = new DateTime(col, row, order.getLatestCheckinTime());
		col++;
		DateTime creatTime = new DateTime(col, row, order.getCreateTime());
		col++;
		DateTime actualCheckIn = new DateTime(col, row, order.getActualCheckinTime());
		col++;
		DateTime actualCheckOut = new DateTime(col, row, order.getActualCheckoutTime());
		col++;
		DateTime cancelTime = new DateTime(col, row, order.getCancelTime());
		col++;
		Number roomNUM = new Number(col, row, order.getNumberOfRoom());
		col++;
		Number numOfClient = new Number(col, row, order.getNumberOfClient());
		col++;
		Number price = new Number(col, row, order.getPrice());
		col++;
		Number score = new Number(col, row, order.getScore());
		col++;
		Number recover = new Number(col, row, order.getRecover());
		col++;
		double kid = 0.0;
		if(order.getHaveKids()){
			kid = 1;
		}
		Number hasKid = new Number(col, row, kid);
		col++;
		Label roomName = new Label(col, row, order.getRoomName());
		col++;
		Number orderStatus = new Number(col, row, order.getOrderStatus().getV());

		try {
			sheet.addCell(memberID);
			sheet.addCell(hotelID);
			sheet.addCell(promotion);
			sheet.addCell(evaluation);
			sheet.addCell(checkIn);
			sheet.addCell(checkOut);
			sheet.addCell(latestCheckIn);
			sheet.addCell(creatTime);
			sheet.addCell(actualCheckIn);
			sheet.addCell(actualCheckOut);
			sheet.addCell(cancelTime);
			sheet.addCell(numOfClient);
			sheet.addCell(roomNUM);
			sheet.addCell(price);
			sheet.addCell(score);
			sheet.addCell(recover);
			sheet.addCell(hasKid);
			sheet.addCell(roomName);
			sheet.addCell(orderStatus);
		} catch (WriteException e) {
			e.printStackTrace();
		}

		try {
			wBook.write();
		} catch (IOException e) {
			e.printStackTrace();
		}

		sync.updateOrder(order);
		return true;
	}

	public boolean cancelOrder(String orderID) {
		return false;
	}

	public boolean makeOrderAbnormal(String orderID) {
		return false;
	}

	public boolean recoverOrder(String orderID, double recover) {
		Cell orderStart = sheet.findCell(orderID);
		int col = orderStart.getColumn();
		int row = orderStart.getRow();
		Number orderStatus = new Number(col+dataSize-1, row, OrderStatus.Canceled.getV());
		Number recoverLocation = new Number(col+dataSize-4, row, recover);

		try {
			sheet.addCell(orderStatus);
			sheet.addCell(recoverLocation);
		} catch (WriteException e) {
			e.printStackTrace();
		}

		try {
			wBook.write();
		} catch (IOException e) {
			e.printStackTrace();
		}

		sync.recoverOrder(orderID,recover);
		return true;
	}

	public OrderPO getOrder(String orderID) {
		return null;
	}

	public ArrayList<OrderPO> getOrderList(String userID) {
		ArrayList<OrderPO> result = new ArrayList<OrderPO>();
		int rows = sheet.getRows();
		for (int i = 0; i < rows; i++) {
			ArrayList<OrderPO> temp = getOrderList(i);
			if(temp!=null){
				for (OrderPO order:temp) {
					if(order.getCreateTime()==new Date()){    //baidu
						result.add(order);
					}
				}
			}
		}
		if(result.size()==0) return null;   //does not have any abnormal order.
		return result;
	}

	public ArrayList<OrderPO> getFinishedOrders(String userID) {
		return null;
	}

	public ArrayList<OrderPO> getUnfinishedOrders(String userID) {
		return null;
	}

	public ArrayList<OrderPO> getAbnormalOrders(String userID) {
		return null;
	}

	public ArrayList<OrderPO> getCancledOrders(String userID) {
		return null;
	}

	/**
	 *
	 */
	public void close() {
		try {
			wBook.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}
		book.close();
	}

	private ArrayList<OrderPO> getOrderList(int row){
		ArrayList<OrderPO> result = new ArrayList<OrderPO>();
		int col = 0;
		while(sheet.getCell(col, row).getContents()!=""){
			result.add(getOrder(col, row));
			col+=dataSize;
		}
		if(result.size()==0) return null;   //This hotel does not have any order.
		return result;
	}

	private OrderPO getOrder(int col, int row){
		String orderID = sheet.getCell(col, row).getContents();
		col++;
		String memberID = sheet.getCell(col, row).getContents();
		col++;
		String hotelID = sheet.getCell(col, row).getContents();
		col++;
		String evaluation = sheet.getCell(col, row).getContents();
		col++;
		String promotion = sheet.getCell(col, row).getContents();
		col++;
		Date checkIn = ((DateCell) sheet.getCell(col, row)).getDate();
		col++;
		Date checkOut = ((DateCell) sheet.getCell(col, row)).getDate();
		col++;
		Date latestCheckIn = ((DateCell) sheet.getCell(col, row)).getDate();
		col++;
		Date creatTime = ((DateCell) sheet.getCell(col, row)).getDate();
		col++;
		Date actualCheckIn = ((DateCell) sheet.getCell(col, row)).getDate();
		col++;
		Date actualCheckOut = ((DateCell) sheet.getCell(col, row)).getDate();
		col++;
		Date cancelTime = ((DateCell) sheet.getCell(col, row)).getDate();
		col++;
		int roomNUM = (int)((NumberCell) sheet.getCell(col, row)).getValue();
		col++;
		int numOfClient = (int)((NumberCell) sheet.getCell(col, row)).getValue();
		col++;
		double price = ((NumberCell) sheet.getCell(col, row)).getValue();
		col++;
		double score = ((NumberCell) sheet.getCell(col, row)).getValue();
		col++;
		double recover = ((NumberCell) sheet.getCell(col, row)).getValue();
		col++;
		boolean hasKid = true;
		int kid = (int)((NumberCell) sheet.getCell(col, row)).getValue();
		if(kid==0){
			hasKid=false;
		}
		col++;
		String roomName = sheet.getCell(col, row).getContents();
		col++;
		int status = (int)((NumberCell) sheet.getCell(col, row)).getValue();
		OrderStatus orderStatus = null;
		switch (status){
			case 0: orderStatus = OrderStatus.Executed; break;
			case 1: orderStatus = OrderStatus.Unexecuted; break;
			case 2: orderStatus = OrderStatus.Abnormal; break;
			case 3: orderStatus = OrderStatus.Canceled; break;
		}
		return new OrderPO(memberID,hotelID,orderID,orderStatus,creatTime,checkIn,actualCheckIn,latestCheckIn,checkOut,actualCheckOut,
				roomNUM,roomName,numOfClient,hasKid,score,evaluation,recover,promotion,price,cancelTime);
	}
}
