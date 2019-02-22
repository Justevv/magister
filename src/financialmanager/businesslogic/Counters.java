package financialmanager.businesslogic;

import financialmanager.database.DbCounters;

import java.util.Date;

import static financialmanager.database.DbCounters.counters;

public class Counters {
    private int id;
    private Date date;
    private String userSurname;
    private float gasReadings;
    private float electricityReadings;
    private float waterReadings;
    private float gasPrice;
    private float electricityPrice;
    private float waterPrice;
    private float gasPaid;
    private float electricityPaid;
    private float waterPaid;
    private Date dateHistory = new Date();
    private float gasReadingsHistory = 0;
    private float electricityReadingsHistory = 0;
    private float waterReadingsHistory = 0;
    private float gasPriceHistory = 0;
    private float electricityPriceHistory = 0;
    private float waterPriceHistory = 0;
    private float gasPaidHistory = 0;
    private float electricityPaidHistory = 0;
    private float waterPaidHistory = 0;
    private float gasSum;
    private float electricitySum;
    private float waterSum;
    private float gasMonthVolume;
    private float electricityMonthVolume;
    private float waterMonthVolume;
    private float gasDayVolume;
    private float electricityDayVolume;
    private float waterDayVolume;
    private float consumptionAmount;
    private float paymentAmount;
    private int daysPeriod;

    public void start() {
        id = DbCounters.nId;
        date = DbCounters.dtDate;
        userSurname = DbCounters.nUserSurname;
        gasReadings = DbCounters.nGasReadings;
        electricityReadings = DbCounters.nElectricityReadings;
        waterReadings = DbCounters.nWaterReadings;
        gasPrice = DbCounters.nGasPrice;
        electricityPrice = DbCounters.nElectricityPrice;
        waterPrice = DbCounters.nWaterPrice;
        gasPaid = DbCounters.nGasPaid;
        electricityPaid = DbCounters.nElectricityPaid;
        waterPaid = DbCounters.nWaterPaid;
        gasMonthVolume = gasReadings - gasReadingsHistory;
        electricityMonthVolume = electricityReadings - electricityReadingsHistory;
        waterMonthVolume = waterReadings - waterReadingsHistory;
        gasSum = gasMonthVolume * gasPrice;
        electricitySum = electricityMonthVolume * electricityPrice;
        waterSum = waterMonthVolume * waterPrice;
        daysPeriod = (int) ((date.getTime() - dateHistory.getTime()) / 1000 / 60 / 60 / 24);
        gasDayVolume = gasMonthVolume / daysPeriod;
        electricityDayVolume = electricityMonthVolume / daysPeriod;
        waterDayVolume = waterMonthVolume / daysPeriod;
        consumptionAmount = gasSum + electricitySum + waterSum;
        paymentAmount = gasPaid * gasPrice + electricityPaid * electricityPrice + waterPaid * waterPrice;
        dateHistory = date;
        gasReadingsHistory = gasReadings;
        electricityReadingsHistory = electricityReadings;
        waterReadingsHistory = waterReadings;
        gasPriceHistory = gasPrice;
        electricityPriceHistory = electricityPrice;
        waterPriceHistory = waterPrice;
        gasPaidHistory = gasPaid;
        electricityPaidHistory = electricityPaid;
        waterPaidHistory = waterPaid;
        counters.add(new financialmanager.data.Counters(id, String.valueOf(date), userSurname,
                gasReadings, electricityReadings, waterReadings,
                gasPrice, electricityPrice, waterPrice,
                gasSum, electricitySum, waterSum,
                gasMonthVolume, electricityMonthVolume, waterMonthVolume,
                gasDayVolume, electricityDayVolume, waterDayVolume,
                gasPaid, electricityPaid, waterPaid,
                consumptionAmount, paymentAmount));
    }
}
