import java.math.BigDecimal;
import com.breezetrader.Context;
import com.breezetrader.Event;
import com.breezetrader.OrderType;
import com.breezetrader.Strategy;
import com.breezetrader.*;

public class MyStrategy extends Strategy {
	
	String symbol = "NIFTY13AUGFUT";
	double macd1, lookbackIsin;
	public void onEvent(Object object)
	{
		
		if(object instanceof Bar)
		{
			macd1 =  getData("macd1" , "macd");
			lookbackIsin =  getData("lookbackIsin" );
			
			Bar bar = ((Bar)object);
			String stream = bar.streamName;
			
			if(stream.equals("default") && bar.symbol.equals(symbol))
				log("Bar : "+bar.symbol+", "+ bar.open +", "+ bar.high+ ", "+ bar.low + ", "+ bar.close +", "+ bar.volume + ", "+ bar.dateTime);
			
			if(stream.equals("minute")&& bar.symbol.equals(symbol))
			{
				log("minute Bar : "+bar.symbol+", "+ bar.open +", "+ bar.high+ ", "+ bar.low + ", "+ bar.close +", "+ bar.volume + ", "+ bar.dateTime);
			}
			
			if(stream.equals("hourly") && bar.symbol.equals(symbol))
			{
				log("hourly Bar : "+bar.symbol+", "+ bar.open +", "+ bar.high+ ", "+ bar.low + ", "+ bar.close +", "+ bar.volume + ", "+ bar.dateTime);
			}
			
			if(stream.equals("daily")&& bar.symbol.equals(symbol))
			{
				
				
				log("daily Bar : "+bar.symbol+", "+ bar.open +", "+ bar.high+ ", "+ bar.low + ", "+ bar.close +", "+ bar.volume + ", "+ bar.dateTime);
			}
			
			if(stream.equals("weekly") && bar.symbol.equals(symbol))
			{
				log("Weekly Bar : "+bar.symbol+", "+ bar.open +", "+ bar.high+ ", "+ bar.low + ", "+ bar.close +", "+ bar.volume + ", "+ bar.dateTime);
			}
			
		}
		
		
	} 
	public void initialize(Context context)
	{	
		
			context.setSymbols(symbol);
			context.setDataFrequency(1, Context.Frequency.MINUTE);
			context.setPortfolioValue(BigDecimal.valueOf(100000));
			context.setDataType(Event.Type.BAR);
			context.setStartDate("24-07-2013");
			context.setEndDate("30-07-2013");
			
			
			context.createDataStream("minute", 1, Context.Frequency.MINUTE);
			context.createDataStream("hourly", 1, Context.Frequency.MINUTE);
			context.createDataStream("daily", 1, Context.Frequency.DAY);
			context.createDataStream("weekly", 1, Context.Frequency.WEEK);
			
			
	}
}