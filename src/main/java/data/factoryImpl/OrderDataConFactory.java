package data.factoryImpl;

import data.service.OrderDataService;
import data.service.OrderDataAbstractFactory;
import data.implementation.OrderDataForH;
import data.implementation.OrderDataForM;
import data.implementation.OrderDataForS;

import java.rmi.RemoteException;

/**
 * Created by apple on 2016/11/29.
 */
public class OrderDataConFactory implements OrderDataAbstractFactory {
    public OrderDataService getOrderData(String userID) {
        switch(userID.length()) {
            case 8: return new OrderDataForM();
            case 6: return new OrderDataForH();
            case 4: return new OrderDataForS();
        }
        return null;
    }

    @Override
    public void setOrderData(String userID) throws RemoteException {

    }
}
