package financialmanager.businesslogic;

import java.util.Date;

public class Counter {
    private Date dateHistory = new Date();
    private float readingsHistory = 0;
    private float priceHistory = 0;
    private float paidHistory = 0;
    private float sum;
    float monthVolume;
    private float dayVolume;
    private float paymentAmount;
    private int daysPeriod;

    public financialmanager.data.Counters start(int id,
                                                Date date,
                                                String userSurname,
                                                float readings,
                                                float price,
                                                float paid) {
        monthVolume = readings - readingsHistory;
        sum = monthVolume * price;
        daysPeriod = (int) ((date.getTime() - dateHistory.getTime()) / 1000 / 60 / 60 / 24);
        dayVolume = monthVolume / daysPeriod;
        paymentAmount = paid * price;
        dateHistory = date;
        readingsHistory = readings;
        priceHistory = price;
        paidHistory = paid;
        return null;
    }

    float getMonthVolume(float readings, float readingsHistory) {
        return readings - readingsHistory;
    }

    float getSum(float monthVolume, float price) {
        return monthVolume * price;
    }

    float getDayVolume(Date date, Date dateHistory, float monthVolume) {
        daysPeriod = (int) ((date.getTime() - dateHistory.getTime()) / 1000 / 60 / 60 / 24);
        dayVolume = monthVolume / daysPeriod;
        return dayVolume;
    }

    float getPaymentAmount(float paid, float price) {
        return paid * price;
    }
}
