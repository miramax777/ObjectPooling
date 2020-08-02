package pool_V2;

import java.io.Serializable;
import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;

public class PriceQuotationV2 implements Serializable{

    private final double id = Math.random();
    private double purchasePrice;
    private double sellingPrice;
    private long dateAndTime;
    private char[] nameOfTradingInstrument;

    public PriceQuotationV2() {
    }

    public PriceQuotationV2(
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

    public PriceQuotationV2(
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

    public PriceQuotationV2 setToInitial() {
        this.purchasePrice = 0.0d;
        this.sellingPrice = 0.0d;
        this.dateAndTime = 0L;
        this.nameOfTradingInstrument = null;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PriceQuotationV2 that = (PriceQuotationV2) o;
        return Double.compare(that.id, id) == 0 &&
                Double.compare(that.purchasePrice, purchasePrice) == 0 &&
                Double.compare(that.sellingPrice, sellingPrice) == 0 &&
                dateAndTime == that.dateAndTime &&
                Arrays.equals(nameOfTradingInstrument, that.nameOfTradingInstrument);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, purchasePrice, sellingPrice, dateAndTime);
        result = 31 * result + Arrays.hashCode(nameOfTradingInstrument);
        return result;
    }
}
