package crypto.model;

import crypto.value.CurrencyType;
import crypto.value.Trade;

public class CurrencyTrade {
	private CurrencyType cType;
	private Trade trade;

	public CurrencyTrade(CurrencyType cType, Trade trade) {
		//System.out.println("In Currency trade Constr"+ cType+" "+trade);
		this.cType = cType;
		this.trade = trade;
	}

	public CurrencyType getcType() {
		return cType;
	}

	public void setcType(CurrencyType cType) {
		this.cType = cType;
	}

	public Trade getTrade() {
		return trade;
	}

	public void setTrade(Trade trade) {
		this.trade = trade;
	}

	@Override
	public String toString() {
		return "CurrencyTrade [cType=" + cType + ", trade=" + trade + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cType == null) ? 0 : cType.hashCode());
		result = prime * result + ((trade == null) ? 0 : trade.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CurrencyTrade other = (CurrencyTrade) obj;
		if (cType != other.cType)
			return false;
		if (trade != other.trade)
			return false;
		return true;
	}

}
