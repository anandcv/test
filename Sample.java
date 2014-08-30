import java.math.BigDecimal;
import java.util.Date;
import com.breezetrader.Context;
import com.breezetrader.Event;
import com.breezetrader.OrderType;
import com.breezetrader.Strategy;
import com.breezetrader.Tick;
import com.breezetrader.*;

public class Sample extends Strategy {
	
	String symbol = "US38259P5089";
	String isin = "US38259P5089";
	//String symbol = "20MICRONS";
	//String isin = "20MICRONS";
	//String symbol = "GOOG";
	//String isin = "US38259P5089";
	public void onEvent(Object object)
	{
		System.out.println("event: "+object.toString());
		if(object instanceof Tick)
		{
			Tick tick = (Tick)object;
			System.out.println("Tick arrived =" + tick.toString());
			System.out.println("isin : "+tick.isin);
			openPosition(tick.symbol,1);
			
		}
	}
	public void initialize(Context context)
	{	
			context.setSymbols(symbol);
 			//context.setDataFrequency(1, Context.Frequency.MINUTE);
			context.setPortfolioValue(BigDecimal.valueOf(100000));
			context.setDataType(Event.Type.TICK);
			//context.createDataStream("1hour", 1, Context.Frequency.HOUR);
			log("Init impl");
	}
}
