package bg.sofia.uni.fmi.mjt.lab2.trading.price;

public class PriceChart implements PriceChartAPI{
    @Override
    public double getCurrentPrice(String stockTicker) {
        return 0;
    }

    @Override
    public boolean changeStockPrice(String stockTicker, int percentChange) {
        return false;
    }
}
