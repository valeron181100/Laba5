package Finances;

import Entities.LivingEntity;
import netscape.javascript.JSObject;
import org.json.JSONObject;

import java.io.Serializable;

public class Ruble implements Money, Serializable {

    /**
     * Курс валюты(должен быть в пределах от 0 до 100)
     */
    private static int rate = 1;

    private int amount;

    public Ruble(int amount){
        this.amount = amount;
        checkRate();
    }

    public Ruble(JSONObject json){
        amount = json.getInt("amount");
        checkRate();
    }

    private void checkRate(){
        if (rate > 100 || rate < 0){
            throw new RateOfBoundException(this);
        }
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public JSONObject getJson() {
        JSONObject json = new JSONObject();
        json.put("amount", amount);
        return json;
    }

    public static int getRate() {
        return rate;
    }

    @Override
    public String toString() {
        return amount + "рублей";
    }

    @Override
    public int hashCode() {
        int prime = 12;
        int result = 1;
        result = prime * result + rate + amount + this.getClass().toString().length();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Ruble other = (Ruble) obj;
        if (amount != other.amount)
            return false;
        return true;
    }
}
