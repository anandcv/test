
import java.math.BigDecimal;

import com.breezetrader.Context;
import com.breezetrader.Event;
import com.breezetrader.OrderType;
import com.breezetrader.Strategy;
import com.breezetrader.Tick;
import com.breezetrader.*;

public class MyStrategy extends Strategy {
	
	String symbol = "NIFTY-CURRENT";
	String isin = "NIFTY-CURRENT";
	//String symbol = "20MICRONS";
	//String isin = "20MICRONS";
	//String symbol = "GOOG";
	//String isin = "US38259P5089";
	long time;
	String stream;
	public void onEvent(Object object)
	{
		if(object instanceof Bar)
		{
			Bar bar = ((Bar)object);
			stream = bar.streamName;
			
			if(stream.equals("1hour")){
				//log("StreamName: "+stream);
				//log("Bar : "+ bar.open +", "+ bar.high+ ", "+ bar.low + ", "+ bar.close +", "+ bar.volume + ", "+ bar.dateTime);
				
			log("Bar : "+ bar.close);
		double ma1 =  getData("ma1" );
		double macd1 =  getData("macd1" , "macd");
		double macd1Hist = getData("macd1" , "macdhist");
		double bband1u = getData("bband1", "realupperband");
		double bband1m = getData("bband1", "realmiddleband");
		double bband1l = getData("bband1", "reallowerband");
		double rsi1 = getData("rsi1");
		double lbrsi1 = getData("lbrsi1");
		double vwap1 =getData("vwap1");
		double lbmacd1 =getData("lbmacd1");
		double lbisin= getData("lbisin");
		double stochF1K = getData("stochF1", "fastk");
		double stochF1D = getData("stochF1", "fastd");
		double stoch1K = getData("stoch1", "slowk");
		double stoch1D = getData("stoch1", "slowd");
		double adx1 = getData("adx1");
		double atr1 = getData("atr1");
		double cci1 = getData("cci1");
		double sar1 = getData("sar1");
		log("test*****************************************************************");
		log("ma1 = " +adx1);
		log("macd1= "+macd1Hist);
		log("rsi1 = "+rsi1);
		log("bband1u =" + bband1u);
		log("bband1m =" + bband1m);
		log("bband1l =" + bband1l);
		log("lookback macd1= "+ lbmacd1);
		log("lbrsi1 = "+lbrsi1);
		log("lbisin = "+lbisin);
		
		log("stochF1K = "+stochF1K);
		log("stochF1D = "+stochF1D);
		log("stoch1K = "+stoch1K);
		log("stoch1D = "+stoch1D);
		log("vwap= "+ vwap1);
		log("adx1 = " +adx1);
		log("atr1 = " +atr1);
		log("cci1 = " +cci1);
		log("sar1 = " +sar1);
			}
		}
	} 
	public void initialize(Context context)
	{	
			initTALib("ma","ma1", "12", "Sma" ,isin, "close","1hour");
			initTALib("macd","macd1", "12", "26","9", isin, "close","1hour" );
			initTALib("rsi","rsi1", "12", isin, "close","1hour" );
			initTALib("bollinger", "bband1",  "20", "2", isin,  "close","1hour");
			initTALib("lookback", "lbmacd1",  "macd1", "2",  "macdhist");
			initTALib("lookback", "lbrsi1",  "rsi1", "2");
			initTALib("lookback", "lbisin", isin, "5", "close","1hour");
			initTALib("stochF", "stochF1", "5","2", isin, "close","1hour");
			initTALib("stoch", "stoch1", "5","2","2", isin, "close","1hour");
			initTALib("vwap", "vwap1", isin,"1hour"); 
			initTALib("adx","adx1", "5", isin,"1hour" );
			initTALib("atr","atr1", "5", isin,"1hour");
			initTALib("cci","cci1", "5", isin ,"1hour");
			initTALib("sar","sar1", "0.02", "0.2", isin ,"1hour");
			context.setSymbols(symbol);
			context.setDataFrequency(1, Context.Frequency.MINUTE);
			//context.setDataURL("http://localhost/landing/EU0009652759.csv");
			context.setPortfolioValue(BigDecimal.valueOf(100000));
			context.setDataType(Event.Type.BAR);
			//context.setDataType(Event.Type.TICK);
			context.setStartDate("24-07-2013");
			context.setEndDate("31-07-2013");
			
			
			context.createDataStream("1hour", 1, Context.Frequency.HOUR);
			//context.createDataStream("3hour", 3, Context.Frequency.HOUR);
			//context.createDataStream("5hour", 5, Context.Frequency.HOUR);
			//context.createDataStream("day", 4, Context.Frequency.DAY);
			log("Init impl");
			time = getTimeInMillis();
			
			
	}
}