package crypto.service.manager;

import java.util.EnumSet;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import crypto.access.CurrencyAccess;
import crypto.model.Currency;
import crypto.model.CurrencyTrade;
import crypto.model.Order;
import crypto.value.CurrencyType;
import crypto.value.Trade;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CurrencyManager {
	@Autowired
	private CurrencyAccess currAcc;
	@Autowired
	private OrderManager orderMgr;

	private ConcurrentHashMap<CurrencyType, Currency> currencies;
	private ConcurrentHashMap<CurrencyTrade, ConcurrentHashMap<Double, Double>> marketChart;
	
	public CurrencyManager() {
		super();
		System.out.println("In Currency manager contr");
		
	}
	@Transactional
	@EventListener(ApplicationReadyEvent.class)
	public void populateCurrency() {
		System.out.println("In Populating Currencies");
		currencies = currAcc.getCurrencyMap();
		System.out.println("i am currincies "+currencies);
		marketChart = new ConcurrentHashMap<>();
		/*if(currencies!=null)
		currencies.forEach((k, v) -> {
			marketChart.put(new CurrencyTrade(k, Trade.BUY), getMapped(orderMgr.peakTopN(k, Trade.BUY, 10)));
			marketChart.put(new CurrencyTrade(k, Trade.SELL), getMapped(orderMgr.peakTopN(k, Trade.SELL, 10)));
		});
	}*/}
	
	public ConcurrentHashMap<CurrencyType, Currency> getTradeCurrencies() {
		return currencies;
	}

	public ConcurrentHashMap<Double, Double> getMarketChart(CurrencyType cType, Trade trade) {
		return marketChart.get(new CurrencyTrade(cType, trade));
	}

	public void updateChart(CurrencyType cType, Trade trade, Double rate, Double quantity) {
		ConcurrentHashMap<Double, Double> chart = marketChart.get(new CurrencyTrade(cType, trade));
		Double record = chart.get(rate);
		if (record == null) {
			chart.put(rate, quantity);
		} else {
			chart.put(rate, record + quantity);
		}
	}

	public void updateCurrencyValue(CurrencyType cType, Double rate) {
		currencies.get(cType).setRate(rate);
		currAcc.setNewRate(cType, rate);
	}

	public Currency getCurrency(CurrencyType cType) {
		System.out.println("in get CUrrency "+cType);
		return currencies.get(cType);
	}

	private ConcurrentHashMap<Double, Double> getMapped(Vector<Order> orders) {
		ConcurrentHashMap<Double, Double> chart = new ConcurrentHashMap<>();
		orders.forEach(order -> chart.put(order.getRate(), order.getQuantity()));
		return chart;
	}

}
