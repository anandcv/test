import java.math.BigDecimal;

import com.breezetrader.Context;
import com.breezetrader.Event;
import com.breezetrader.OrderType;
import com.breezetrader.Strategy;
import com.breezetrader.Bar;
/*
*  This is a sample strategy intended to get you started.
*  For more details on the APIs and usage please visit 
*  http://docs.breezetrader.com
*
*/
public class TaLibTest extends Strategy {


        boolean flag = false;

        String symbol = "NIFTYBEES";
	    String isin = "NIFTYBEES";
        double sma10, sma20, lsma10, lsma20;
        /*

        *  initialize your context,
        *  technical indicators other variables

        */
        public void initialize(Context context)
        {

            initTALib("ma","ma1", "12", "Sma" ,isin, "currentValueDouble" );
			initTALib("macd","macd1", "12", "26","9", isin, "currentValueDouble" );
			initTALib("rsi","rsi1", "12", isin, "currentValueDouble" );
			initTALib("bollinger", "bband1",  "20", "2", isin,  "close");
			initTALib("lookback", "lbmacd1",  "macd1", "2",  "macdhist");
			initTALib("lookback", "lbrsi1",  "rsi1", "2");
			initTALib("lookback", "lbisin", isin, "2", "close");
			initTALib("stochF", "stochF1", "14","2", isin, "close");
			initTALib("stoch", "stoch1", "14","2","2", isin, "close");
			initTALib("vwap", "vwap1", isin); 
			initTALib("adx","adx1", "5", isin );
			initTALib("atr","atr1", "5", isin );
			initTALib("cci","cci1", "5", isin );
			initTALib("sar","sar1", "0.02", "0.2", isin );
            context.setDataFrequency(1, Context.Frequency.DAY);
            context.setSymbols(isin);
            //context.setDataURL("http://localhost/landing/EU0009652759.csv");

            context.setPortfolioValue(BigDecimal.valueOf(150000));
            context.setDataType(Event.Type.BAR);

            context.setStartDate("01-01-2011");
            context.setEndDate("31-12-2013");

        }


        /*
        *  onEvent is the callback when a market event happens.

        *  The behaviour of how this is called depends on the context
        *  object you intialized in intialize(Context context)

        */

        public void onEvent(Object object)

        {
		if(object instanceof Bar)
		{
			Bar bar = ((Bar)object);
			String stream = bar.streamName;
			//log("StreamName: "+stream);
			//log("Bar : "+ bar.open +", "+ bar.high+ ", "+ bar.low + ", "+ bar.close +", "+ bar.volume + ", "+ bar.dateTime);
		}
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
