package financialmanager.businesslogic;

import java.util.Date;

public class Counters {

    public float getMonthVolume(float readings, float readingsHistory) {
        return readings - readingsHistory;
    }

    public float getSum(float monthVolume, float price) {
        return monthVolume * price;
    }

    public float getDayVolume(Date date, Date dateHistory, float monthVolume) {
        int daysPeriod = (int) ((date.getTime() - dateHistory.getTime()) / 1000 / 60 / 60 / 24);
        return monthVolume / daysPeriod;
    }

    public float getSumTotal(float countGas, float priceGas, float countElectricity, float priceElectricity, float countWater, float priceWater) {
        return countGas * priceGas + countElectricity * priceElectricity + countWater * priceWater;
    }


}
