package financialmanager.data;

public class Counters {
    Integer nId;
    String dtDate;
    String nUserSurname;
    float nGasReadings;
    float nElectricityReadings;
    float nWaterReadings;
    float nGasPrice;
    float nElectricityPrice;
    float nWaterPrice;
    float nGasSum;
    float nElectricitySum;
    float nWaterSum;
    float nGasMonthVolume;
    float nElectricityMonthVolume;
    float nWaterMonthVolume;
    float nGasDayVolume;
    float nElectricityDayVolume;
    float nWaterDayVolume;
    float nGasPaid;
    float nElectricityPaid;
    float nWaterPaid;
    float nConsumptionAmount;
    float nPaymentAmount;

    public Counters(Integer nId, String dtDate, String nUserSurname,
                    float nGasReadings, float nElectricityReadings, float nWaterReadings,
                    float nGasPrice, float nElectricityPrice, float nWaterPrice,
                    float nGasSum, float nElectricitySum, float nWaterSum,
                    float nGasMonthVolume, float nElectricityMonthVolume, float nWaterMonthVolume,
                    float nGasDayVolume, float nElectricityDayVolume, float nWaterDayVolume,
                    float nGasPaid, float nElectricityPaid, float nWaterPaid,
                    float nConsumptionAmount, float nPaymentAmount) {
        this.nId = nId;
        this.dtDate = dtDate;
        this.nUserSurname = nUserSurname;
        this.nGasReadings = nGasReadings;
        this.nElectricityReadings = nElectricityReadings;
        this.nWaterReadings = nWaterReadings;
        this.nGasPrice = nGasPrice;
        this.nElectricityPrice = nElectricityPrice;
        this.nWaterPrice = nWaterPrice;
        this.nGasSum = nGasSum;
        this.nElectricitySum = nElectricitySum;
        this.nWaterSum = nWaterSum;
        this.nGasMonthVolume = nGasMonthVolume;
        this.nElectricityMonthVolume = nElectricityMonthVolume;
        this.nWaterMonthVolume = nWaterMonthVolume;
        this.nGasDayVolume = nGasDayVolume;
        this.nElectricityDayVolume = nElectricityDayVolume;
        this.nWaterDayVolume = nWaterDayVolume;
        this.nGasPaid = nGasPaid;
        this.nElectricityPaid = nElectricityPaid;
        this.nWaterPaid = nWaterPaid;
        this.nConsumptionAmount = nConsumptionAmount;
        this.nPaymentAmount = nPaymentAmount;
    }

    public Integer getnId() {
        return nId;
    }

    public String getdtDate() {
        return dtDate;
    }

    public String getnUserSurname() {
        return nUserSurname;
    }

    public float getnGasReadings() {
        return nGasReadings;
    }

    public float getnElectricityReadings() {
        return nElectricityReadings;
    }

    public float getnWaterReadings() {
        return nWaterReadings;
    }

    public float getnGasPrice() {
        return nGasPrice;
    }

    public float getnElectricityPrice() {
        return nElectricityPrice;
    }

    public float getnWaterPrice() {
        return nWaterPrice;
    }

    public float getnGasSum() {
        return nGasSum;
    }

    public float getnElectricitySum() {
        return nElectricitySum;
    }

    public float getnWaterSum() {
        return nWaterSum;
    }

    public float getnGasMonthVolume() {
        return nGasMonthVolume;
    }

    public float getnElectricityMonthVolume() {
        return nElectricityMonthVolume;
    }

    public float getnWaterMonthVolume() {
        return nWaterMonthVolume;
    }

    public float getnGasDayVolume() {
        return nGasDayVolume;
    }

    public float getnElectricityDayVolume() {
        return nElectricityDayVolume;
    }

    public float getnWaterDayVolume() {
        return nWaterDayVolume;
    }

    public float getnGasPaid() {
        return nGasPaid;
    }

    public float getnElectricityPaid() {
        return nElectricityPaid;
    }

    public float getnWaterPaid() {
        return nWaterPaid;
    }

    public float getnConsumptionAmount() {
        return nConsumptionAmount;
    }

    public float getnPaymentAmount() {
        return nPaymentAmount;
    }
}

