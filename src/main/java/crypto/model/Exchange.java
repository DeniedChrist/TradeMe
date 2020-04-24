package crypto.model;

public class Exchange {

	private String address;// (address)
	private double rate;// (rate)
	private double quantity;// (quantity)

	private Wallet buyerWallet;// (buyer_wallet_id)
	private Wallet sellerWallet;// (seller_wallet_id)

	public Exchange() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Exchange(String address, double rate, double quantity, Wallet buyerWallet, Wallet sellerWallet) {
		super();
		this.address = address;
		this.rate = rate;
		this.quantity = quantity;
		this.buyerWallet = buyerWallet;
		this.sellerWallet = sellerWallet;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public Wallet getBuyerWallet() {
		return buyerWallet;
	}

	public void setBuyerWallet(Wallet buyerWallet) {
		this.buyerWallet = buyerWallet;
	}

	public Wallet getSellerWallet() {
		return sellerWallet;
	}

	public void setSellerWallet(Wallet sellerWallet) {
		this.sellerWallet = sellerWallet;
	}

	@Override
	public String toString() {
		return "Exchange [address=" + address + ", rate=" + rate + ", quantity=" + quantity + ", buyerWallet="
				+ buyerWallet.getId() + ", sellerWallet=" + sellerWallet.getId() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
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
		Exchange other = (Exchange) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		return true;
	}

}
