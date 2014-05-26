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
public class TaLibTest2 extends Strategy {


        boolean flag = false;

        String symbol = "NIFTY-CURRENT";
	    String isin = "NIFTY-CURRENT";
        double sma10, sma20, lsma10, lsma20;
        /*

        *  initialize your context,
        *  technical indicators other variables

        */
        public void initialize(Context context)
        {

            initTALib("ma","ma1", "12", "Sma" ,isin, "currentValueDouble" ,"1hour"); //initailize moving avaerage
			initTALib("macd","macd1", "12", "26","9", isin, "currentValueDouble" ,"1hour" );//initialize macd
			initTALib("rsi","rsi1", "12", isin, "currentValueDouble"  ,"1hour");//initialize rsi
			initTALib("bollinger", "bband1",  "20", "2", isin,  "close" ,"1hour"); //initialize bollinger
			initTALib("lookback", "lbmacd1",  "macd1", "2",  "macdhist");// initialize lookback for a macdhist of macd1 variable
			initTALib("lookback", "lbrsi1",  "rsi1", "2");// initialize lookback for rsi1 variable
			initTALib("lookback", "lbisin", isin, "2", "close" ,"1hour");//lookback a security's close value
			initTALib("stochF", "stochF1", "14","2", isin, "close" ,"1hour");//initialize Fast Stochastic oscillator
			initTALib("stoch", "stoch1", "14","2","2", isin, "close" ,"1hour");//initialize Full Stochastic Oscillator
			initTALib("vwap", "vwap1", isin ,"1hour"); //initialize vwap of a security
			initTALib("adx","adx1", "5", isin  ,"1hour");//initialize adx
			initTALib("atr","atr1", "5", isin  ,"1hour");//initialize atr
			initTALib("cci","cci1", "5", isin  ,"1hour");//initialize cci
			initTALib("sar","sar1", "0.02", "0.2", isin  ,"1hour");//initialize parabolic sar
            context.setDataFrequency(1, Context.Frequency.MINUTE);
            context.setSymbols(isin);
            
            context.setPortfolioValue(BigDecimal.valueOf(150000));
            context.setDataType(Event.Type.BAR);

            context.setStartDate("02-02-2012");
            context.setEndDate("05-02-2012");

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
    		if(stream.equals("1hour")
    		{
        		double ma1 =  getData("ma1" );//read data of movingaverage variable
        		double macd1 =  getData("macd1" , "macd");//read data of macd variable for macd component
        		double macd1Hist = getData("macd1" , "macdhist");//read data of macd for macdhist component
        		double bband1u = getData("bband1", "realupperband");//read data of bollinger upperband
        		double bband1m = getData("bband1", "realmiddleband");//read data bollinger middleband
        		double bband1l = getData("bband1", "reallowerband");//read data bollinger lowerband
        		double rsi1 = getData("rsi1");//read data of rsi variable 
        		double lbrsi1 = getData("lbrsi1");//read data of lookback rsi variable
        		double vwap1 =getData("vwap1");//read data vwap variable
        		double lbmacd1 =getData("lbmacd1");//read data lookback of macd
        		double lbisin= getData("lbisin");//read data of lookback of security
        		double stochF1K = getData("stochF1", "fastk");//read data of stochstic fast oscillator fastk value
        		double stochF1D = getData("stochF1", "fastd");//read data of stochstic fast oscillator fastd value
        		double stoch1K = getData("stoch1", "slowk");//read data of stochstic full oscillator slowk value
        		double stoch1D = getData("stoch1", "slowd");//read data of stochstic full oscillator slowd value
        		double adx1 = getData("adx1");//read data of adx variable
        		double atr1 = getData("atr1");//read data of  atr variable
        		double cci1 = getData("cci1");//read data of cci variable
        		double sar1 = getData("sar1");//read data of probabilstic SAR variables
        		
        		//Log values for reference
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
}
