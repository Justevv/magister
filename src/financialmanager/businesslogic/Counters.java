package financialmanager.businesslogic;

import financialmanager.database.DbCounters;

import java.util.Date;


public class Counters {
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
    float gasMonthVolume;
    private float electricityMonthVolume;
    private float waterMonthVolume;
    private float gasDayVolume;
    private float electricityDayVolume;
    private float waterDayVolume;
    private float consumptionAmount;
    private float paymentAmount;
    private int daysPeriod;

    public void start(int id,
                      Date date,
                      String userSurname,
                      float gasReadings,
                      float electricityReadings,
                      float waterReadings,
                      float gasPrice,
                      float electricityPrice,
                      float waterPrice,
                      float gasPaid,
                      float electricityPaid,
                      float waterPaid) {
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
        DbCounters dbCounters = new DbCounters();
        dbCounters.counters.add(new financialmanager.data.Counters(id, String.valueOf(date), userSurname,
                gasReadings, electricityReadings, waterReadings,
                gasPrice, electricityPrice, waterPrice,
                gasSum, electricitySum, waterSum,
                gasMonthVolume, electricityMonthVolume, waterMonthVolume,
                gasDayVolume, electricityDayVolume, waterDayVolume,
                gasPaid, electricityPaid, waterPaid,
                consumptionAmount, paymentAmount));
    }
}
