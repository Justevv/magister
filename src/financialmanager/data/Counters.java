package financialmanager.data;

import financialmanager.text.CounterType;

import java.util.Date;

public class Counters {
    private int id;
    private Date date;
    private float price;
    private float value;
    private float paid;
    private CounterType type;

    public Counters(int id,
                    Date date,
                    float price,
                    float value,
                    float paid,
                    CounterType type) {
        this.id = id;
        this.date = date;
        this.price = price;
        this.value = value;
        this.paid = paid;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public float getPrice() {
        return price;
    }

    public float getValue() {
        return value;
    }

    public float getPaid() {
        return paid;
    }

    public CounterType getType() {
        return type;
    }


    @Override
    public String toString() {
        return "Counters{" +
                "id=" + id +
                ", date=" + date +
                ", price=" + price +
                ", value=" + value +
                ", type=" + type +
                '}';
    }
}
