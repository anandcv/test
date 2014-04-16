import java.math.BigDecimal;
import com.breezetrader.Context;
import com.breezetrader.Event;
import com.breezetrader.OrderType;
import com.breezetrader.Strategy;
import com.breezetrader.Tick;
/*
*  This is a sample strategy intended to get you started.
*  For more details on the APIs and usage please visit 
*  http://docs.breezetrader.com
*
*/
public class max extends Strategy {
	
	boolean flag = false;
	String symbol = "EUR.USD";
	/*
	*  initialize your context, 
	*  technical indicators other variables
	*/
	public void initialize(Context context)
	{	
			initTALib("macd","macd1", "12", "26","9", "EU0009652759", "currentValueDouble" );
			initTALib("rsi","rsi1", "12", "EU0009652759", "currentValueDouble" );
			context.setDataFrequency(2, Context.Frequency.WEEK);
			context.setSymbols("EU0009652759");
			context.setDataURL("https://s3-ap-southeast-1.amazonaws.com/breezetrader-test/EU0009652759.csv");
			context.setPortfolioValue(BigDecimal.valueOf(100000));
			context.setDataType(Event.Type.BAR);
			context.setStartDate("03-9-2012");
			context.setEndDate("31-10-2012");
			log("Initialized");
			
	}
	
	/*
	*  onEvent is the callback when a market event happens. 
	*  The behaviour of how this is called depends on the context 
	*  object you intialized in intialize(Context context) 
	*/
	
	public void onEvent(Object object)
	{
		double macd1 = getData("macd1" , "macd");
		double macd1Hist = getData("macd1" , "macdhist");
		double rsi1 = getData("rsi1");
//		log("macd1: "+macd1);
//		log("macd1hist: "+macd1Hist);
//		log("macd1sig: "+macd1Sig);
		log("current Time: "+getTimeStamp());
		if(rsi1>70)
			log("rsi > 70");
		else if(rsi1<30)
			log("rsi < 30");
		else
			log("rsi = "+rsi1);
		if(macd1 > macd1Hist)
		{
			log("ID: "+order(OrderType.Market,symbol, 10));
			
			log("openpositions: "+getPosition(symbol));
		}
		else
		{
			log("openpositions: "+getPosition(symbol));
			closeAllPositions(symbol);
			
		}
		
	}
	
}
	