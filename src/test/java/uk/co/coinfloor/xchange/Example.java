/*
 * Created on Feb 8, 2017
 */
package uk.co.coinfloor.xchange;

import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

public class Example {

	static final String userID = System.getProperty("uk.co.coinfloor.api.userID");
	static final String cookie = System.getProperty("uk.co.coinfloor.api.cookie");
	static final String password = System.getProperty("uk.co.coinfloor.api.password");

	public static void main(String[] args) throws Exception {
		Exchange exchange = ExchangeFactory.INSTANCE.createExchangeWithoutSpecification(CoinfloorExchange.class.getName());
		ExchangeSpecification spec = exchange.getDefaultExchangeSpecification();
		spec.setUserName(userID);
		spec.setApiKey(cookie);
		spec.setPassword(password);
		exchange.applySpecification(spec);

		try {
			MarketDataService marketDataService = exchange.getMarketDataService();
			TradeService tradeService = exchange.getTradeService();

			// Coinfloor uses the ISO-compatible code "XBT" instead of "BTC".
			CurrencyPair currencyPair = new CurrencyPair(Currency.getInstance("XBT"), Currency.GBP);

			System.out.println(marketDataService.getTicker(currencyPair));

			System.out.println(marketDataService.getOrderBook(currencyPair));

			TradeHistoryParams tradeHistoryParams = tradeService.createTradeHistoryParams();
			((TradeHistoryParamCurrencyPair) tradeHistoryParams).setCurrencyPair(currencyPair);
			System.out.println(tradeService.getTradeHistory(tradeHistoryParams));

			OpenOrdersParams openOrdersParams = tradeService.createOpenOrdersParams();
			System.out.println(tradeService.getOpenOrders(openOrdersParams));

			BigDecimal quantity = BigDecimal.ONE, price = BigDecimal.ONE;
			String orderID = tradeService.placeLimitOrder(new LimitOrder(OrderType.BID, quantity, currencyPair, null, null, price));
			System.out.println("Placed order " + orderID);

			for (Order order : tradeService.getOrder(orderID)) {
				System.out.println(order);
			}

			if (tradeService.cancelOrder(orderID)) {
				System.out.println("Canceled order " + orderID);
			}
			else {
				System.err.println("Order " + orderID + " not found!");
			}
		}
		finally {
			/*
			 * XChange offers no straightforward way to disconnect an exchange, so we must downcast here to call
			 * CoinfloorExchange.disconnect(), or else the Coinfloor connection will keep the JVM running.
			 */
			((CoinfloorExchange) exchange).disconnect();
		}
	}

}
