package financialmanager.businessLogic;

import financialmanager.database.DbCounters;

import java.util.Date;

import static financialmanager.database.DbCounters.counters;

public class Counters {
    static int nId;
    static Date dtDate;
    static String nUserSurname;
    static float nGasReadings;
    static float nElectricityReadings;
    static float nWaterReadings;
    static float nGasPrice;
    static float nElectricityPrice;
    static float nWaterPrice;
    static float nGasPaid;
    static float nElectricityPaid;
    static float nWaterPaid;
    static Date dtDateHistory = new Date();
    static float nGasReadingsHistory = 0;
    static float nElectricityReadingsHistory = 0;
    static float nWaterReadingsHistory = 0;
    static float nGasPriceHistory = 0;
    static float nElectricityPriceHistory = 0;
    static float nWaterPriceHistory = 0;
    static float nGasPaidHistory = 0;
    static float nElectricityPaidHistory = 0;
    static float nWaterPaidHistory = 0;
    static float nGasSum;
    static float nElectricitySum;
    static float nWaterSum;
    static float nGasMonthVolume;
    static float nElectricityMonthVolume;
    static float nWaterMonthVolume;
    static float nGasDayVolume;
    static float nElectricityDayVolume;
    static float nWaterDayVolume;
    static float nConsumptionAmount;
    static float nPaymentAmount;
    static int nDaysPeriod;

    public static void start() {
        nId = DbCounters.nId;
        dtDate = DbCounters.dtDate;
        nUserSurname = DbCounters.nUserSurname;
        nGasReadings = DbCounters.nGasReadings;
        nElectricityReadings = DbCounters.nElectricityReadings;
        nWaterReadings = DbCounters.nWaterReadings;
        nGasPrice = DbCounters.nGasPrice;
        nElectricityPrice = DbCounters.nElectricityPrice;
        nWaterPrice = DbCounters.nWaterPrice;
        nGasPaid = DbCounters.nGasPaid;
        nElectricityPaid = DbCounters.nElectricityPaid;
        nWaterPaid = DbCounters.nWaterPaid;
        nGasMonthVolume = nGasReadings - nGasReadingsHistory;
        nElectricityMonthVolume = nElectricityReadings - nElectricityReadingsHistory;
        nWaterMonthVolume = nWaterReadings - nWaterReadingsHistory;
        nGasSum = nGasMonthVolume * nGasPrice;
        nElectricitySum = nElectricityMonthVolume * nElectricityPrice;
        nWaterSum = nWaterMonthVolume * nWaterPrice;
        nDaysPeriod = (int) ((dtDate.getTime() - dtDateHistory.getTime()) / 1000 / 60 / 60 / 24);
        nGasDayVolume = nGasMonthVolume / nDaysPeriod;
        nElectricityDayVolume = nElectricityMonthVolume / nDaysPeriod;
        nWaterDayVolume = nWaterMonthVolume / nDaysPeriod;
        nConsumptionAmount = nGasSum + nElectricitySum + nWaterSum;
        nPaymentAmount = nGasPaid * nGasPrice + nElectricityPaid * nElectricityPrice + nWaterPaid * nWaterPrice;
        dtDateHistory = dtDate;
        nGasReadingsHistory = nGasReadings;
        nElectricityReadingsHistory = nElectricityReadings;
        nWaterReadingsHistory = nWaterReadings;
        nGasPriceHistory = nGasPrice;
        nElectricityPriceHistory = nElectricityPrice;
        nWaterPriceHistory = nWaterPrice;
        nGasPaidHistory = nGasPaid;
        nElectricityPaidHistory = nElectricityPaid;
        nWaterPaidHistory = nWaterPaid;
        counters.add(new financialmanager.data.Counters(nId, String.valueOf(dtDate), nUserSurname,
                nGasReadings, nElectricityReadings, nWaterReadings,
                nGasPrice, nElectricityPrice, nWaterPrice,
                nGasSum, nElectricitySum, nWaterSum,
                nGasMonthVolume, nElectricityMonthVolume, nWaterMonthVolume,
                nGasDayVolume, nElectricityDayVolume, nWaterDayVolume,
                nGasPaid, nElectricityPaid, nWaterPaid,
                nConsumptionAmount, nPaymentAmount));
    }
}
