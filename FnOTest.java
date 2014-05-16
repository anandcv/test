import java.math.BigDecimal;
import com.breezetrader.Context;
import com.breezetrader.Event;
import com.breezetrader.OrderType;
import com.breezetrader.Strategy;
import com.breezetrader.*;

public class FnOTest extends Strategy {
	
	String symbol = "NIFTY-NEAR";
	double macd1, lookbackIsin;
	public void onEvent(Object object)
	{
		
		if(object instanceof Bar)
		{
			macd1 =  getData("macd1" , "macd");
			lookbackIsin =  getData("lookbackIsin" );
			
			Bar bar = ((Bar)object);
			String stream = bar.streamName;
			
			if(stream.equals("default") )
				log("Bar : "+bar.symbol+", "+ bar.open +", "+ bar.high+ ", "+ bar.low + ", "+ bar.close +", "+ bar.volume + ", "+ bar.dateTime);
			
			if(stream.equals("minute"))
			{
				log("minute Bar : "+bar.symbol+", "+ bar.open +", "+ bar.high+ ", "+ bar.low + ", "+ bar.close +", "+ bar.volume + ", "+ bar.dateTime);
			}
			
			if(stream.equals("hourly"))
			{
				log("hourly Bar : "+bar.symbol+", "+ bar.open +", "+ bar.high+ ", "+ bar.low + ", "+ bar.close +", "+ bar.volume + ", "+ bar.dateTime);
			}
			
			if(stream.equals("daily"))
			{
				
				
				log("daily Bar : "+bar.symbol+", "+ bar.open +", "+ bar.high+ ", "+ bar.low + ", "+ bar.close +", "+ bar.volume + ", "+ bar.dateTime);
			}
			
			if(stream.equals("weekly"))
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
			context.setStartDate("25-01-2012");
			context.setEndDate("23-02-2012");
			
			
			context.createDataStream("minute", 1, Context.Frequency.MINUTE);
			context.createDataStream("hourly", 1, Context.Frequency.MINUTE);
			context.createDataStream("daily", 1, Context.Frequency.DAY);
			context.createDataStream("weekly", 1, Context.Frequency.WEEK);
			
			
	}
}