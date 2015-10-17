package bean;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * This class is used to define deposit and its properties, calculate payedInterest and compare object by their
 * payedInterest property.
 */
public class Deposit implements Comparable<Deposit> {
    private String customerNumber;
    private BigDecimal depositBalance;
    private Integer durationInDays;
    private BigDecimal payedInterest;
    private DepositType depositType;

    public Deposit(String customerNumber, BigDecimal depositBalance, Integer durationInDays, DepositType depositType) {
        this.customerNumber = customerNumber;
        this.depositBalance = depositBalance;
        this.durationInDays = durationInDays;
        this.depositType = depositType;
    }

    /**
     * This method is used to calculate payedInterest of a deposit.
     *
     * @return BigDecimal calculated interest.
     */
    public BigDecimal calculatePayedInterest() {
        payedInterest = depositBalance.multiply(new BigDecimal(depositType.getInterestRate() * durationInDays))
                .divide(new BigDecimal(36500), 2, RoundingMode.HALF_UP);
        System.out.println("your balance is: ("
                + depositBalance
                + ") Rial, and your duration day is: ("
                + durationInDays
                + "), therefore your payed "
                + "interest is: ("
                + payedInterest
                + ")Rial! ");
        return payedInterest;
    }

    /**
     * @param deposit is a object of deposit for comparing with others and sorting the array of objects.
     * @return int
     */
    @Override
    public int compareTo(Deposit deposit) {
        return this.getPayedInterest().compareTo(deposit.getPayedInterest());
    }


    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public BigDecimal getDepositBalance() {
        return depositBalance;
    }

    public void setDepositBalance(BigDecimal depositBalance) {
        this.depositBalance = depositBalance;
    }

    public Integer getDurationInDays() {
        return durationInDays;
    }

    public void setDurationInDays(Integer durationInDays) {
        this.durationInDays = durationInDays;
    }

    public BigDecimal getPayedInterest() {
        return payedInterest;
    }

    public void setPayedInterest(BigDecimal payedInterest) {
        this.payedInterest = payedInterest;
    }

    public DepositType getDepositType() {
        return depositType;
    }

    public void setDepositType(DepositType depositType) {
        this.depositType = depositType;
    }
}