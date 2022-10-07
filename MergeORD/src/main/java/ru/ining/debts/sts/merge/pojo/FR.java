package ru.ining.debts.sts.merge.pojo;

public class FR {
    public int result;
    public Item item;

    public class Item {
        public String operation_id;
    }

    public FR() {}

    @Override
    public String toString() {
        return "result = " + result + "; operation_id = " + item.operation_id;
    }
}
