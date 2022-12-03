package vn.misa.nadat.cukcuklite.items;

/**
 * Đối tượng món ăn ở màn hình Thực đơn
 *
 * @created_by nadat on 4/5/2019
 */
public class ItemDish {
    private int ItemDishId;
    private String ItemDishName;
    private int ItemUnitId;
    private String PriceOfDish;
    private int Inactive;
    private int UseCount;
    private String TotalPrice;
    private String ItemDishColor;
    private String ItemDishIcon;

    public ItemDish() {
    }

    public int getItemDishId() {
        return ItemDishId;
    }

    public void setItemDishId(int itemDishId) {
        ItemDishId = itemDishId;
    }

    public String getItemDishName() {
        return ItemDishName;
    }

    public void setItemDishName(String itemDishName) {
        ItemDishName = itemDishName;
    }

    public int getItemUnitId() {
        return ItemUnitId;
    }

    public void setItemUnitId(int itemUnitId) {
        ItemUnitId = itemUnitId;
    }

    public String getItemDishPrice() {
        return PriceOfDish;
    }

    public void setItemDishPrice(String price) {
        PriceOfDish = price;
    }

    public int isInactive() {
        return Inactive;
    }

    public void setInactive(int inactive) {
        Inactive = inactive;
    }

    public int getUseCount() {
        return UseCount;
    }

    public void setUseCount(int useCount) {
        UseCount = useCount;
    }

    public String getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        TotalPrice = totalPrice;
    }

    public String getItemDishColor() {
        return ItemDishColor;
    }

    public void setItemDishColor(String itemDishColor) {
        ItemDishColor = itemDishColor;
    }

    public String getItemDishIcon() {
        return ItemDishIcon;
    }

    public void setItemDishIcon(String itemDishIcon) {
        ItemDishIcon = itemDishIcon;
    }
}
