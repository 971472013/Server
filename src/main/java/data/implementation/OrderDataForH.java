package data.implementation;

import data.service.OrderDataService;
import other.OrderChanger;
import jxl.*;
import jxl.read.biff.BiffException;
import po.OrderPO;
import other.OrderStatus;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by apple on 2016/12/1.
 */
public class OrderDataForH implements OrderDataService, Serializable{
	private int dataSize = 20;
	private String sourceFile = "OrderDataForHotel.xls";
	private OrderChanger changerForH = new OrderChanger("OrderDataForHotel.xls");
	private OrderChanger changerForM = new OrderChanger("OrderDataForMember.xls");
	private Workbook book;
	private Sheet sheet;
	private static final long serialVersionUID = -6833877079313718314L;   //序列号
	/**
	 *
	 * @param order
	 * @return
	 */
	public boolean addOrder(OrderPO order) {
		int col = 0;
		int row = hash(order.getHotelID());
		boolean result = changerForH.addOrder(col, row, order);
		row = hash(order.getMemberID());
		boolean sync = changerForM.addOrder(col, row, order);
		return result&&sync;
	}

	/**
	 *
	 * @param order
	 * @return
	 */
	public boolean updateOrder(OrderPO order) {
		boolean result = changerForH.updateOrder(order);
		boolean sync = changerForM.updateOrder(order);
		return result&&sync;
	}

	/**
	 *
	 * @param orderID
	 * @return
	 */
	public boolean cancelOrder(String orderID) {
		boolean result = changerForH.cancelOrder(orderID);
		boolean sync = changerForM.cancelOrder(orderID);
		return result&&sync;
	}

	/**
	 *
	 * @param orderID
	 * @return
	 */
	public boolean makeOrderAbnormal(String orderID) {
		boolean result = changerForH.makeOrderAbnormal(orderID);
		boolean sync = changerForM.makeOrderAbnormal(orderID);
		return result&&sync;
	}

	/**
	 *
	 * @param orderID
	 * @param recover
	 * @return
	 */
	public boolean recoverOrder(String orderID, double recover) {
		boolean result = changerForH.recoverOrder(orderID, recover);
		boolean sync = changerForM.recoverOrder(orderID, recover);
		return result&&sync;
	}


	/**
	 *
	 * @param orderID
	 * @return
	 */
	public OrderPO getOrder(String orderID) {
		createSheet();
		Cell orderStart = sheet.findCell(orderID);
		if(orderStart==null){
			book.close();
			return null;
		}
		int col = orderStart.getColumn();
		int row = orderStart.getRow();
		OrderPO result = getOrder(col, row);
		book.close();
		return result;
	}

	/**
	 *
	 * @param userID
	 * @return
	 */
	public ArrayList<OrderPO> getOrderList(String userID) {
		createSheet();
		ArrayList<OrderPO> result = new ArrayList<OrderPO>();
		int col = 0;
		int row = hash(userID);
		if(row>=sheet.getRows()||col>=sheet.getColumns()){
			book.close();
			return null;
		}
		for (int i = 0; i < sheet.getRow(row).length; i+=dataSize) {
			result.add(getOrder(col+i, row));
		}
		book.close();
		if(result.size()==0) return null;   //This hotel does not have any order.
		return result;
	}

	/**
	 *
	 * @param userID
	 * @return
	 */
	public ArrayList<OrderPO> getFinishedOrders(String userID) {
		ArrayList<OrderPO> temp = getOrderList(userID);
		if(temp==null) return null;
		ArrayList<OrderPO> result = new ArrayList<>();
		for (OrderPO thisOrder: temp
				) {
			if(thisOrder.getOrderStatus()==OrderStatus.Executed){
				result.add(thisOrder);
			}
		}
		if(result.size()==0) return null;   //This hotel does not have any finished order.
		return result;
	}

	/**
	 *
	 * @param userID
	 * @return
	 */
	public ArrayList<OrderPO> getUnfinishedOrders(String userID) {
		ArrayList<OrderPO> temp = getOrderList(userID);
		if(temp==null) return null;
		ArrayList<OrderPO> result = new ArrayList<>();
		for (OrderPO thisOrder: temp
				) {
			if(thisOrder.getOrderStatus()==OrderStatus.Unexecuted){
				result.add(thisOrder);
			}
		}
		if(result.size()==0) return null;   //This hotel does not have any finished order.
		return result;
	}

	/**
	 *
	 * @param userID
	 * @return
	 */
	public ArrayList<OrderPO> getAbnormalOrders(String userID) {
		ArrayList<OrderPO> temp = getOrderList(userID);
		if(temp==null) return null;
		ArrayList<OrderPO> result = new ArrayList<>();
		for (OrderPO thisOrder: temp
				) {
			if(thisOrder.getOrderStatus()==OrderStatus.Abnormal){
				result.add(thisOrder);
			}
		}
		if(result.size()==0) return null;   //This hotel does not have any finished order.
		return result;
	}

	/**
	 *
	 * @param userID
	 * @return
	 */
	public ArrayList<OrderPO> getCancledOrders(String userID) {
		ArrayList<OrderPO> temp = getOrderList(userID);
		if(temp==null) return null;
		ArrayList<OrderPO> result = new ArrayList<>();
		for (OrderPO thisOrder: temp
				) {
			if(thisOrder.getOrderStatus()==OrderStatus.Canceled){
				result.add(thisOrder);
			}
		}
		if(result.size()==0) return null;   //This hotel does not have any finished order.
		return result;
	}

	/**
	 *
	 * @param ID
	 * @return
	 */
	private int hash(String ID){
		int hashResult = Integer.parseInt(ID);
		hashResult%=27;
		return hashResult;
	}

	/**
	 *
	 */
	private void createSheet(){
		try {
			book = Workbook.getWorkbook(new File(sourceFile));
			sheet = book.getSheet(0);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BiffException e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 * @param col
	 * @param row
	 * @return
	 */
	private OrderPO getOrder(int col, int row){
		int status = (int)((NumberCell) sheet.getCell(col+dataSize-1, row)).getValue();
		OrderStatus orderStatus = null;
		switch (status) {
			case 0:
				orderStatus = OrderStatus.Executed;
				break;
			case 1:
				orderStatus = OrderStatus.Unexecuted;
				break;
			case 2:
				orderStatus = OrderStatus.Abnormal;
				break;
			case 3:
				orderStatus = OrderStatus.Canceled;
				break;
		}
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
		String roomName = sheet.getCell(col, row).getContents();
		col++;
		long dateHelper = (long)((NumberCell) sheet.getCell(col, row)).getValue();
		Date checkIn = new Date(dateHelper);
		col++;
		dateHelper = (long)((NumberCell) sheet.getCell(col, row)).getValue();
		Date checkOut = new Date(dateHelper);
		col++;
		dateHelper = (long)((NumberCell) sheet.getCell(col, row)).getValue();
		Date latestCheckIn = new Date(dateHelper);
		col++;
		dateHelper = (long)((NumberCell) sheet.getCell(col, row)).getValue();
		Date creatTime = new Date(dateHelper);
		col++;
		Date actualCheckIn = null;
		Date actualCheckOut = null;
		Date cancelTime = null;
		if(orderStatus==OrderStatus.Executed){
			dateHelper = (long)((NumberCell) sheet.getCell(col, row)).getValue();
			actualCheckIn = new Date(dateHelper);
			col++;
			dateHelper = (long)((NumberCell) sheet.getCell(col, row)).getValue();
			if(dateHelper!=-1)actualCheckOut = new Date(dateHelper);
			col++;
		}
		else{
			col+=2;
		}
		if(orderStatus==OrderStatus.Canceled){
			dateHelper = (long)((NumberCell) sheet.getCell(col, row)).getValue();
			cancelTime = new Date(dateHelper);
		}
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
		return new OrderPO(memberID,hotelID,orderID,orderStatus,creatTime,checkIn,actualCheckIn,latestCheckIn,checkOut,actualCheckOut,
				roomNUM,roomName,numOfClient,hasKid,score,evaluation,recover,promotion,price,cancelTime);
	}

}
