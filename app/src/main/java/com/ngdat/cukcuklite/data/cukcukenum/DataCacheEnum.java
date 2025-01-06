package com.ngdat.cukcuklite.data.cukcukenum;

/**
 * Enum cho trường type dữ liệu lưu trữ khi chưa update
 * Created at 24/04/2019
 */
public enum DataCacheEnum {
    ADD_UNIT(1),
    UPDATE_UNIT(2),
    DELETE_UNIT(3),
    ADD_DISH(4),
    UPDATE_DISH(5),
    DELETE_DISH(6),
    ADD_BILL(7),
    UPDATE_BILL(8),
    ADD_BILL_DETAIL(9),
    UPDATE_BILL_DETAIL(10);


    private int value;

    private DataCacheEnum(int value) {
        this.value = value;
    }

    public static DataCacheEnum getDataCacheEnum(int type) {
        switch (type) {
            case 1:
                return ADD_UNIT;
            case 2:
                return UPDATE_UNIT;
            case 3:
                return DELETE_UNIT;
            case 4:
                return ADD_DISH;
            case 5:
                return UPDATE_DISH;
            case 6:
                return DELETE_DISH;
            case 7:
                return ADD_BILL;
            case 8:
                return UPDATE_BILL;
            case 9:
                return ADD_BILL_DETAIL;
            case 10:
                return UPDATE_BILL_DETAIL;
            default:
                break;
        }
        return null;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
