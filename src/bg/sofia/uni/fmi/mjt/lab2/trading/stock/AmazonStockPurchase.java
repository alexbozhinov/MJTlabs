package bg.sofia.uni.fmi.mjt.lab2.trading.stock;

import java.time.LocalDateTime;

public class AmazonStockPurchase implements StockPurchase{
    private int quantity;
    private LocalDateTime purchaseTimestamp;
    private double purchasePricePerUnit;

    public AmazonStockPurchase(int quantity, LocalDateTime purchaseTimestamp, double purchasePricePerUnit) {
        setQuantity(quantity);
        setPurchaseTimestamp(purchaseTimestamp);
        setPurchasePricePerUnit(purchasePricePerUnit);
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPurchaseTimestamp(LocalDateTime purchaseTimestamp) {
        this.purchaseTimestamp = purchaseTimestamp;
    }

    public void setPurchasePricePerUnit(double purchasePricePerUnit) {
        this.purchasePricePerUnit = purchasePricePerUnit;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }

    @Override
    public LocalDateTime getPurchaseTimestamp() {
        return purchaseTimestamp;
    }

    @Override
    public double getPurchasePricePerUnit() {
        return Math.round(purchasePricePerUnit * 100.0) / 100.0;
    }

    @Override
    public double getTotalPurchasePrice() {
        return Math.round(purchasePricePerUnit * quantity * 100.0) / 100.0;
    }

    @Override
    public String getStockTicker() {
        return "AMZ";
    }
}
