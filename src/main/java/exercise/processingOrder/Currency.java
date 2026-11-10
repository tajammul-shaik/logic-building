package exercise.processingOrder;

public enum Currency {

    USD(1.0), EUR(0.92), INR(83.0);

    private final double conversionRateToUSD;

    Currency(double rate){
        this.conversionRateToUSD = rate;
    }

    public double toUSD(double amount){
        return amount / this.conversionRateToUSD;
    }

}
