package financialmanager.businesslogic;

import financialmanager.database.DbCounters;

import java.util.Date;

import static financialmanager.database.DbCounters.counters;

public class Counters {
    static int id;
    static Date date;
    static String userSurname;
    static float gasReadings;
    static float electricityReadings;
    static float waterReadings;
    static float gasPrice;
    static float electricityPrice;
    static float waterPrice;
    static float gasPaid;
    static float electricityPaid;
    static float waterPaid;
    static Date dateHistory = new Date();
    static float gasReadingsHistory = 0;
    static float electricityReadingsHistory = 0;
    static float waterReadingsHistory = 0;
    static float gasPriceHistory = 0;
    static float electricityPriceHistory = 0;
    static float waterPriceHistory = 0;
    static float gasPaidHistory = 0;
    static float electricityPaidHistory = 0;
    static float waterPaidHistory = 0;
    static float gasSum;
    static float electricitySum;
    static float waterSum;
    static float gasMonthVolume;
    static float electricityMonthVolume;
    static float waterMonthVolume;
    static float gasDayVolume;
    static float electricityDayVolume;
    static float waterDayVolume;
    static float consumptionAmount;
    static float paymentAmount;
    static int daysPeriod;

    public static void start() {
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
