package bg.sofia.uni.fmi.mjt.lab2.trading;

import bg.sofia.uni.fmi.mjt.lab2.trading.price.PriceChartAPI;
import bg.sofia.uni.fmi.mjt.lab2.trading.stock.AmazonStockPurchase;
import bg.sofia.uni.fmi.mjt.lab2.trading.stock.GoogleStockPurchase;
import bg.sofia.uni.fmi.mjt.lab2.trading.stock.MicrosoftStockPurchase;
import bg.sofia.uni.fmi.mjt.lab2.trading.stock.StockPurchase;

import java.time.LocalDateTime;

public class Portfolio implements PortfolioAPI{

    private String owner;
    private PriceChartAPI priceChart;
    private StockPurchase[] stockPurchases;
    private double budget;
    private int maxSize;

    public Portfolio(String owner, PriceChartAPI priceChart, double budget, int maxSize) {
        setOwner(owner);
        setPriceChart(priceChart);
        setBudget(budget);
        setMaxSize(maxSize);
    }

    public Portfolio(String owner, PriceChartAPI priceChart, StockPurchase[] stockPurchases, double budget, int maxSize) {
        setOwner(owner);
        setPriceChart(priceChart);
        setStockPurchases(stockPurchases);
        setBudget(budget);
        setMaxSize(maxSize);
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public PriceChartAPI getPriceChart() {
        return priceChart;
    }

    public void setPriceChart(PriceChartAPI priceChart) {
        this.priceChart = priceChart;
    }

    public StockPurchase[] getStockPurchases() {
        return stockPurchases;
    }

    public void setStockPurchases(StockPurchase[] stockPurchases) {
        this.stockPurchases = new StockPurchase[0];
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    @Override
    public StockPurchase buyStock(String stockTicker, int quantity) {
        if (quantity < 0) return null;
        if (null == stockTicker) return null;
        if (!stockTicker.equals("MSFT") && !stockTicker.equals("GOOG") && !stockTicker.equals("AMZ")) return null;

        if (budget < priceChart.getCurrentPrice(stockTicker) * quantity) return null;
        if (stockPurchases.length == maxSize) return null;

        StockPurchase purchase;
        switch (stockTicker) {
            case "MSFT" -> {
                purchase = new MicrosoftStockPurchase(quantity, LocalDateTime.now(), priceChart.getCurrentPrice(stockTicker));
                budget -= purchase.getTotalPurchasePrice();
                priceChart.changeStockPrice(stockTicker, 5);
                StockPurchase[] newArray = new StockPurchase[stockPurchases.length + 1];

                System.arraycopy(stockPurchases, 0, newArray, 0, stockPurchases.length);

                newArray[stockPurchases.length] = purchase;

                stockPurchases = newArray;
            }
            case "GOOG" -> {
                purchase = new GoogleStockPurchase(quantity, LocalDateTime.now(), priceChart.getCurrentPrice(stockTicker));
                budget -= purchase.getTotalPurchasePrice();
                priceChart.changeStockPrice(stockTicker, 5);
                StockPurchase[] newArray = new StockPurchase[stockPurchases.length + 1];

                System.arraycopy(stockPurchases, 0, newArray, 0, stockPurchases.length);

                newArray[stockPurchases.length] = purchase;

                stockPurchases = newArray;
            }
            case "AMZ" -> {
                purchase = new AmazonStockPurchase(quantity, LocalDateTime.now(), priceChart.getCurrentPrice(stockTicker));
                budget -= purchase.getTotalPurchasePrice();
                priceChart.changeStockPrice(stockTicker, 5);
                StockPurchase[] newArray = new StockPurchase[stockPurchases.length + 1];

                System.arraycopy(stockPurchases, 0, newArray, 0, stockPurchases.length);

                newArray[stockPurchases.length] = purchase;

                stockPurchases = newArray;
            }
            default -> purchase = null;
        }

        return purchase;
    }

    @Override
    public StockPurchase[] getAllPurchases() {
        return stockPurchases;
    }

    @Override
    public StockPurchase[] getAllPurchases(LocalDateTime startTimestamp, LocalDateTime endTimestamp) {
        int newSize = 0;

        for (StockPurchase stockPurchase : stockPurchases) {
            if (stockPurchase.getPurchaseTimestamp().isAfter(startTimestamp) && stockPurchase.getPurchaseTimestamp().isBefore(endTimestamp)){
                newSize++;
            }else if (stockPurchase.getPurchaseTimestamp().isEqual(startTimestamp)) {
                newSize++;
            }else if (stockPurchase.getPurchaseTimestamp().isEqual(endTimestamp)){
                newSize++;
            }
        }
        StockPurchase[] purchasesInInterval = new StockPurchase[newSize];
        int idx = 0;

        for (StockPurchase stockPurchase : stockPurchases) {
            if (stockPurchase.getPurchaseTimestamp().isAfter(startTimestamp) && stockPurchase.getPurchaseTimestamp().isBefore(endTimestamp)){
                purchasesInInterval[idx] = stockPurchase;
                idx++;
            }else if (stockPurchase.getPurchaseTimestamp().isEqual(startTimestamp)) {
                purchasesInInterval[idx] = stockPurchase;
                idx++;
            }else if (stockPurchase.getPurchaseTimestamp().isEqual(endTimestamp)){
                purchasesInInterval[idx] = stockPurchase;
                idx++;
            }
        }

        return purchasesInInterval;
    }

    @Override
    public double getNetWorth() {
        double netWorth = 0.0;
        for (StockPurchase purchase: stockPurchases) {
            netWorth += (purchase.getQuantity() * priceChart.getCurrentPrice(purchase.getStockTicker()));
        }

        return netWorth;
    }

    @Override
    public double getRemainingBudget() {
        return Math.round(budget * 100.0) / 100.0;
    }

    @Override
    public String getOwner() {
        return owner;
    }
}

