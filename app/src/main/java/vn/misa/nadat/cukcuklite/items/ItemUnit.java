package vn.misa.nadat.cukcuklite.items;

/**
 * Đối tượng đơn vị tính.
 *
 * @created_by nadat on 15/04/2019
 */
public class ItemUnit {
    private int ItemUnitID;
    private boolean IsCheck;
    private String ItemUnitName;

    public ItemUnit() {
    }

    public ItemUnit(String itemUnitName) {
        ItemUnitName = itemUnitName;
    }

    public int getItemUnitID() {
        return ItemUnitID;
    }

    public void setItemUnitID(int itemUnitID) {
        ItemUnitID = itemUnitID;
    }

    public boolean isCheck() {
        return IsCheck;
    }

    public void setCheck(boolean check) {
        IsCheck = check;
    }

    public String getItemUnitName() {
        return ItemUnitName;
    }

    public void setItemUnitName(String itemUnitName) {
        ItemUnitName = itemUnitName;
    }
}
