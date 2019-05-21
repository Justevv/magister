package financialmanager.data;

import financialmanager.text.CounterType;

import java.util.Date;

public class Counter {
    private int id;
    private Date date;
    private float price;
    private float value;
    private CounterType type;

    public Counter(int id,
                   Date date,
                   float price,
                   float value,
                   CounterType type) {
        this.id = id;
        this.date = date;
        this.price = price;
        this.value = value;
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

    public CounterType getType() {
        return type;
    }
}
