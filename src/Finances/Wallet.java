package Finances;

import Entities.Human;
import MainPkg.Main;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

public class Wallet implements Serializable {

    private Class<? extends Finances.Money> currencyType;

    private int amount = 0;

    public Wallet(Class<? extends Finances.Money> moneyKind){
        currencyType = moneyKind;
    }

    @SuppressWarnings("unchecked")
    public Wallet(JSONObject json){
        try {
            currencyType = (Class<? extends Money>) Class.forName(json.getString("currencyType"));
        } catch (ClassNotFoundException e) {
            System.err.println("Ошибка: неверный json!");
        }
        amount = json.getInt("amount");
    }

    public void addMoney(Money money){
        if (money.getClass() == currencyType) {
            this.amount += money.getAmount();
            Main.pause("Деньги были добавлены на счёт!");
        }
        else{
            Main.pause("Ошибка, несоответствующая валюта!");
        }
    }

    public void takeMoney(int amount){
        this.amount -= amount;
        Main.pause("Деньги были сняты со счёта!");
    }

    public Money checkMoney() {
        Money money = null;
        try {
            money = currencyType.getConstructor(int.class).newInstance(amount);
        } catch (InstantiationException |
                IllegalAccessException |
                InvocationTargetException |
                NoSuchMethodException e) {
            e.printStackTrace();
        }
        return money;
    }

    public Class<? extends Money> getCurrencyType() {
        return currencyType;
    }

    public void giveMoney(Human other, Money amount){
            other.getWallet().addMoney(amount);
    }

    public JSONObject getJson(){
        JSONObject json = new JSONObject();
        json.put("currencyType", currencyType.getName());
        json.put("amount", amount);
        return json;
    }

    @Override
    public String toString() {
        return "Кошек для хранения валюты " + currencyType.toString() + ", баланс: " + amount;
    }

    @Override
    public int hashCode() {
        int prime = 13;
        int result = 1;
        result = prime * result + amount + currencyType.toString().length();
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
        Wallet other = (Wallet) obj;
        if (currencyType != other.currencyType)
            return false;
        if (amount != other.amount)
            return false;
        return true;
    }
}
