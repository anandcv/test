import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.breezetrader.Context;
import com.breezetrader.Event;
import com.breezetrader.OrderType;
import com.breezetrader.Strategy;
import com.breezetrader.Order;
import com.breezetrader.OrderStatus;
import com.breezetrader.Bar;
import com.breezetrader.Tick;
/*
*  This is a sample strategy intended to get you started.
*  For more details on the APIs and usage please visit
*  http://docs.breezetrader.com
*
*/
public class Ema7CrossEma21 extends Strategy {
        // NIFTY50 - SSLT, M&M
        String nifty50 = "NIFTY-CURRENT,ACC-CURRENT,AMBUJACEM-CURRENT,ASIANPAINT-CURRENT,AXISBANK-CURRENT,BAJAJ-AUTO-CURRENT," +
            "BANKBARODA-CURRENT,BHEL-CURRENT,BPCL-CURRENT,BHARTIARTL-CURRENT,CAIRN-CURRENT,CIPLA-CURRENT,COALINDIA-CURRENT," +
            "DLF-CURRENT,DRREDDY-CURRENT,GAIL-CURRENT,GRASIM-CURRENT,HCLTECH-CURRENT,HDFCBANK-CURRENT,HEROMOTOCO-CURRENT," +
            "HINDALCO-CURRENT,HINDUNILVR-CURRENT,HDFC-CURRENT,ITC-CURRENT,ICICIBANK-CURRENT,IDFC-CURRENT,INDUSINDBK-CURRENT," +
            "INFY-CURRENT,JINDALSTEL-CURRENT,KOTAKBANK-CURRENT,LT-CURRENT,LUPIN-CURRENT,MARUTI-CURRENT," +
            "NMDC-CURRENT,NTPC-CURRENT,ONGC-CURRENT,POWERGRID-CURRENT,PNB-CURRENT,RELIANCE-CURRENT,SBIN-CURRENT," +
            "SUNPHARMA-CURRENT,TCS-CURRENT,TATAMOTORS-CURRENT,TATAPOWER-CURRENT,TATASTEEL-CURRENT,TECHM-CURRENT," +
            "ULTRACEMCO-CURRENT,MCDOWELL-N-CURRENT,WIPRO-CURRENT";
        double ema7, ema21;
        Date today;
        Map<String,String> trend; // Map of symbol by Trend
        Map<String,Boolean> crossOver,
                            pullBack,
                            rsiPlus40;
        /*

        *  initialize your context,
        *  technical indicators other variables

        */
        public void initialize(Context context)
        {

            context.setPortfolioValue(BigDecimal.valueOf(1500000));
            context.setDataType(Event.Type.BAR);

            context.setSymbols(nifty50);
            context.setStartDate("01-01-2012");
            context.setEndDate("31-12-2013");

            context.createDataStream("hourly", 60, Context.Frequency.MINUTE);
                        context.createDataStream("daily", 1, Context.Frequency.DAY);

                        String[] symbols = nifty50.split(",");
                        for(String symbol: symbols){

                            //String symbol = fullSymbol.substring(0,fullSymbol.lastIndexOf("-"));
                            String symbol1 = symbol.replaceAll("-","");
                            initTALib("lookback", symbol1 + "YestHi",  symbol, "1", "high", "daily");
                            initTALib("lookback", symbol1 + "DayBefHi",  symbol, "2", "high", "daily");
                            initTALib("lookback", symbol1 + "YestLo",  symbol, "1", "low", "daily");
                            initTALib("lookback", symbol1 + "DayBefLo",  symbol, "2", "low", "daily");


                            initTALib("ma", symbol1 + "ma7", "7","Ema", symbol, "close", "hourly" );
                initTALib("ma", symbol1 + "ma21", "21","Ema", symbol, "close", "hourly" );
                initTALib("lookback", "lb" + symbol1 + "ma7",  symbol1 + "ma7", "1");
                initTALib("lookback", "lb" + symbol1 + "ma21",  symbol1 + "ma21", "1");

                initTALib("lookback", symbol1 + "lastCo",  symbol, "1", "close", "hourly");

                initTALib("rsi", symbol1 + "rsi40", "40", symbol, "close", "hourly" );
                initTALib("lookback", "lb" + symbol1 + "rsi40",  symbol1 + "rsi40", "1");

                initTALib("vwap", symbol1 + "vwap", symbol , "hourly"); // VWAP
                context.registerCEPQuery("stddev", "select 'stddev', stddev(value) from " + symbol1 +"vwap as value");
                        }


            trend = new ConcurrentHashMap<String,String>();
            crossOver = new ConcurrentHashMap<String,Boolean>();
            pullBack = new ConcurrentHashMap<String,Boolean>();
            rsiPlus40 = new ConcurrentHashMap<String,Boolean>();
        }


        /*
        *  onEvent is the callback when a market event happens.

        *  The behaviour of how this is called depends on the context
        *  object you intialized in intialize(Context context)

        */

        public void onEvent(Object object)

        {
            if(today == null){
                 today = new Date(getTimeInMillis());
            }

            if(hasDateChanged()){
                evaluateTrend();
            }

            if(object instanceof Bar){
                Bar data = (Bar) object;
                String fullSymbol = data.symbol;
                String symbol = fullSymbol.substring(0,fullSymbol.lastIndexOf("-"));

                if(data.streamName == "hourly"){
                    // NIFTY is UP, Symbol is UP
                    if(getTrend("NIFTY").equals("UP") && getTrend(symbol).equals("UP")){
                        // Go Long
                        log("NIFTY UP " + symbol + "UP");
                    }
                    // NIFTY is DOWN, Symbol is DOWN
                    else if(getTrend("NIFTY").equals("DOWN") && getTrend(symbol).equals("DOWN")){
                        // Go Short
                        log("NIFTY DOWN " + symbol + "DOWN");
                    }
                    // NIFTY is SIDEWAYS, Symbol is UP
                    // NIFTY is SIDEWAYS, Symbol is DOWN
                    else if(getTrend("NIFTY").equals("SIDEWAYS")){
                        if(getTrend(symbol).equals("UP")){
                            //Go Long
                            log("NIFTY SIDEWAYS " + symbol + "UP");
                        }else if(getTrend(symbol).equals("DOWN")){
                            //Go Short
                            log("NIFTY SIDEWAYS " + symbol + "DOWN");
                        }else if(getTrend(symbol).equals("SIDEWAYS")){
                            // Use VWAP Strategy

                            double vwap = getData(symbol + "vwap");

                            if(data.close.doubleValue() > vwap){
                                String orderId = order(OrderType.Market,symbol,1);
                                //check status of order after 30 seconds
                                sendObjectAfter(orderId,30000);

                            }
                            if(data.close.doubleValue() < vwap){
                                String orderId = order(OrderType.Market,symbol,-1);
                                //check status of order after 30 seconds
                                sendObjectAfter(orderId,30000);

                            }
                        }
                    }
                }
            }

            /*if(ma3>ma5 && lma3<= lma5)
            {
                log("openpositions: "+getPosition(symbol));

                if(getPosition(symbol) < 0){
                    closeAllPositions(symbol);

                }
                log("ID: "+order(OrderType.Market,symbol, 150));
            }
            if(ma3<ma5 && lma3>= lma5)
            {

                log("openpositions: "+getPosition(symbol));
                if(getPosition(symbol) > 0){

                    closeAllPositions(symbol);
                }

            }*/
        }

        private boolean hasDateChanged(){

            Date current = new Date(getTimeInMillis());
            if(today.getDate() != current.getDate()){
                today = current;
                return true;
            }
            else{
                return false;
            }
        }

        private void evaluateTrend(){
            String[] symbols = nifty50.split(",");
            for(String symbol: symbols){
                    //String symbol = fullSymbol.substring(0,fullSymbol.lastIndexOf("-"));
                    String symbol1 = symbol.replaceAll("-","");
                    double yestHi = getData(symbol1 + "YestHi");
                    double dayBefHi = getData(symbol1 + "DayBefHi");
                    double yestLo = getData(symbol1 + "YestLo");
                    double dayBefLo = getData(symbol1 + "DayBefLo");

                            if ((yestLo > dayBefLo) && (yestHi > dayBefHi)){
                                trend.put(symbol,"UP");
                            }else if((yestLo < dayBefLo) && (yestHi < dayBefHi)){
                                trend.put(symbol,"DOWN");
                            }else {
                                trend.put(symbol,"SIDEWAYS");
                            }
                        }
        }

        private String getTrend(String symbol){
            return trend.get(symbol);
        }


        private void evaluateConditions(Bar data, String symbol){

            // EMA(7) crosses EMA(21) from below in TF (30-60 mins)

            if(crossOver.get(symbol) == null){
                crossOver.put(symbol,false);
            }
              String symbol1 = symbol.replaceAll("-","");
            double ema7 = getData(symbol1 + "ma7");
            double ema21 = getData(symbol1 + "ma21");
            double ema7Last = getData("lb" + symbol1 + "ma7");
            double ema21Last = getData("lb" + symbol1 + "ma21");

            if ((ema7 > ema21) && (ema7Last < ema21Last)){

                crossOver.put(symbol,true);

            }

            // RSI > 40 for all the bars from cross to pullback

            double rsi40 = getData(symbol1 + "rsi40");
            double rsi40Last = getData("lb" + symbol1 + "rsi40");

            if(rsiPlus40.get(symbol) == null){
                rsiPlus40.put(symbol,false);
            }

            if((rsi40 > 40) && (crossOver.get(symbol) == true) && (rsiPlus40.get(symbol) == false)){
                rsiPlus40.put(symbol,true);
            }

            // Negative
            if(!((rsi40Last > 40) && (rsi40 > 40) && (crossOver.get(symbol) == true))){
                rsiPlus40.put(symbol,false);
            }

            // First Pullback of price to EMA(21)

            if(pullBack.get(symbol) == null){
                pullBack.put(symbol,false);
            }

            double prevClose = getData(symbol1 + "lastCo");
            if((data.close.doubleValue() >= ema21) && (prevClose < ema21Last)){
                pullBack.put(symbol,true);
            }else{ // Signal only on FIRST pullback
                pullBack.put(symbol,false);
            }
        }

        @Override
        protected void onTimeout(Object object){

            if(object instanceof String){
                String oid = (String)object;
                Order odr = getOrder(oid);
                OrderStatus status = odr.status;
                // If order is not executed cancel / replace
                if(status == OrderStatus.CANCELLED){ //FIXME: replace with Order.NEW
                    cancel(oid);
                }

                // If order is executed send target / stop loss
                if(status == OrderStatus.EXECUTED){
                    //  Place S/L
                    //  Place Target order
                }
            }


        }
}
