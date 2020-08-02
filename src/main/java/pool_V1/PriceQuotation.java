package pool_V1;

import java.io.Serializable;
import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;

public class PriceQuotation implements Serializable{

    private double purchasePrice;
    private double sellingPrice;
    private long dateAndTime;
    private char[] nameOfTradingInstrument;

    public PriceQuotation() {
    }

    public PriceQuotation(
            final double purchasePrice,
            final double sellingPrice,
            final long dateAndTime,
            final char[] nameOfTradingInstrument
    ) {
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
        this.dateAndTime = dateAndTime;
        this.nameOfTradingInstrument = nameOfTradingInstrument;
    }

    public PriceQuotation(
            final double purchasePrice,
            final double sellingPrice,
            final Instant dateAndTime,
            final String nameOfTradingInstrument
    ) {
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
        this.dateAndTime = dateAndTime.getEpochSecond();
        this.nameOfTradingInstrument = nameOfTradingInstrument.toCharArray();
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public long getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(long dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public char[] getNameOfTradingInstrument() {
        return nameOfTradingInstrument;
    }

    public void setNameOfTradingInstrument(char[] nameOfTradingInstrument) {
        this.nameOfTradingInstrument = nameOfTradingInstrument;
    }

    public void setToInitialState() {
        this.purchasePrice = 0.0d;
        this.sellingPrice = 0.0d;
        this.dateAndTime = 0L;
        this.nameOfTradingInstrument = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PriceQuotation that = (PriceQuotation) o;
        return Double.compare(that.purchasePrice, purchasePrice) == 0 &&
                Double.compare(that.sellingPrice, sellingPrice) == 0 &&
                dateAndTime == that.dateAndTime &&
                Arrays.equals(nameOfTradingInstrument, that.nameOfTradingInstrument);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(purchasePrice, sellingPrice, dateAndTime);
        result = 31 * result + Arrays.hashCode(nameOfTradingInstrument);
        return result;
    }
}
