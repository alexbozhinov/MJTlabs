package bg.sofia.uni.fmi.mjt.lab2.trading.price;

public class PriceChart implements PriceChartAPI{
    private double microsoftStockPrice;
    private double googleStockPrice;
    private double amazonStockPrice;

    public PriceChart(double microsoftStockPrice, double googleStockPrice, double amazonStockPrice) {
        setMicrosoftStockPrice(microsoftStockPrice);
        setGoogleStockPrice(googleStockPrice);
        setAmazonStockPrice(amazonStockPrice);
    }

    public void setMicrosoftStockPrice(double microsoftStockPrice) {
        this.microsoftStockPrice = microsoftStockPrice;
    }

    public void setGoogleStockPrice(double googleStockPrice) {
        this.googleStockPrice = googleStockPrice;
    }

    public void setAmazonStockPrice(double amazonStockPrice) {
        this.amazonStockPrice = amazonStockPrice;
    }

    @Override
    public double getCurrentPrice(String stockTicker) {
        return switch(stockTicker){
            case "MSFT" -> Math.round(microsoftStockPrice * 100.0) / 100.0;
            case "AMZ"  -> Math.round(amazonStockPrice * 100.0) / 100.0;
            case "GOOG" -> Math.round(googleStockPrice * 100.0) / 100.0;
            case null, default -> 0.00;
        };
    }

    @Override
    public boolean changeStockPrice(String stockTicker, int percentChange) {
        boolean priceChanged = false;

        if (percentChange > 0) {
            priceChanged = true;
        }else{
            return priceChanged;
        }

        switch(stockTicker){
            case "MSFT" -> microsoftStockPrice += (microsoftStockPrice * (percentChange/100.0));
            case "AMZ"  -> amazonStockPrice += (amazonStockPrice * (percentChange/100.0));
            case "GOOG" -> googleStockPrice += (googleStockPrice * (percentChange/100.0));
            case null, default -> priceChanged = false;
        }

        return priceChanged;
    }
}

