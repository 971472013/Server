package data.dataservice;

import po.SalerPO;

public interface SalerDataService {
	public boolean addSaler(SalerPO saler);
	public boolean deleteSaler(String salerID);
	public boolean updateSaler(SalerPO saler);
	public SalerPO getSaler(String ID);
}
