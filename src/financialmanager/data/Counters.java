package financialmanager.data;

public class Counters {
    Integer id;
    String date;
    String userSurname;
    float gasReadings;
    float electricityReadings;
    float waterReadings;
    float gasPrice;
    float electricityPrice;
    float waterPrice;
    float gasSum;
    float electricitySum;
    float waterSum;
    float gasMonthVolume;
    float electricityMonthVolume;
    float waterMonthVolume;
    float gasDayVolume;
    float electricityDayVolume;
    float waterDayVolume;
    float gasPaid;
    float electricityPaid;
    float waterPaid;
    float consumptionAmount;
    float paymentAmount;

    public Counters(Integer id, String date, String userSurname,
                    float gasReadings, float electricityReadings, float waterReadings,
                    float gasPrice, float electricityPrice, float waterPrice,
                    float gasSum, float electricitySum, float waterSum,
                    float gasMonthVolume, float electricityMonthVolume, float waterMonthVolume,
                    float gasDayVolume, float electricityDayVolume, float waterDayVolume,
                    float gasPaid, float electricityPaid, float waterPaid,
                    float consumptionAmount, float paymentAmount) {
        this.id = id;
        this.date = date;
        this.userSurname = userSurname;
        this.gasReadings = gasReadings;
        this.electricityReadings = electricityReadings;
        this.waterReadings = waterReadings;
        this.gasPrice = gasPrice;
        this.electricityPrice = electricityPrice;
        this.waterPrice = waterPrice;
        this.gasSum = gasSum;
        this.electricitySum = electricitySum;
        this.waterSum = waterSum;
        this.gasMonthVolume = gasMonthVolume;
        this.electricityMonthVolume = electricityMonthVolume;
        this.waterMonthVolume = waterMonthVolume;
        this.gasDayVolume = gasDayVolume;
        this.electricityDayVolume = electricityDayVolume;
        this.waterDayVolume = waterDayVolume;
        this.gasPaid = gasPaid;
        this.electricityPaid = electricityPaid;
        this.waterPaid = waterPaid;
        this.consumptionAmount = consumptionAmount;
        this.paymentAmount = paymentAmount;
    }

    public Integer getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public float getGasReadings() {
        return gasReadings;
    }

    public float getElectricityReadings() {
        return electricityReadings;
    }

    public float getWaterReadings() {
        return waterReadings;
    }

    public float getGasPrice() {
        return gasPrice;
    }

    public float getElectricityPrice() {
        return electricityPrice;
    }

    public float getWaterPrice() {
        return waterPrice;
    }

    public float getGasSum() {
        return gasSum;
    }

    public float getElectricitySum() {
        return electricitySum;
    }

    public float getWaterSum() {
        return waterSum;
    }

    public float getGasMonthVolume() {
        return gasMonthVolume;
    }

    public float getElectricityMonthVolume() {
        return electricityMonthVolume;
    }

    public float getWaterMonthVolume() {
        return waterMonthVolume;
    }

    public float getGasDayVolume() {
        return gasDayVolume;
    }

    public float getElectricityDayVolume() {
        return electricityDayVolume;
    }

    public float getWaterDayVolume() {
        return waterDayVolume;
    }

    public float getGasPaid() {
        return gasPaid;
    }

    public float getElectricityPaid() {
        return electricityPaid;
    }

    public float getWaterPaid() {
        return waterPaid;
    }

    public float getConsumptionAmount() {
        return consumptionAmount;
    }

    public float getPaymentAmount() {
        return paymentAmount;
    }

    @Override
    public String toString() {
        return "Counters{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", userSurname='" + userSurname + '\'' +
                ", gasReadings=" + gasReadings +
                ", electricityReadings=" + electricityReadings +
                ", waterReadings=" + waterReadings +
                ", gasPrice=" + gasPrice +
                ", electricityPrice=" + electricityPrice +
                ", waterPrice=" + waterPrice +
                ", gasSum=" + gasSum +
                ", electricitySum=" + electricitySum +
                ", waterSum=" + waterSum +
                ", gasMonthVolume=" + gasMonthVolume +
                ", electricityMonthVolume=" + electricityMonthVolume +
                ", waterMonthVolume=" + waterMonthVolume +
                ", gasDayVolume=" + gasDayVolume +
                ", electricityDayVolume=" + electricityDayVolume +
                ", waterDayVolume=" + waterDayVolume +
                ", gasPaid=" + gasPaid +
                ", electricityPaid=" + electricityPaid +
                ", waterPaid=" + waterPaid +
                ", consumptionAmount=" + consumptionAmount +
                ", paymentAmount=" + paymentAmount +
                '}';
    }
}

