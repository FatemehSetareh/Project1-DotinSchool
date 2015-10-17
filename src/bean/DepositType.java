package bean;

/**
 * This is an abstract class that represent deposit types.
 */
public abstract class DepositType {
    protected double interestRate;

    public DepositType() {
        this.interestRate = interestRate;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }
}
