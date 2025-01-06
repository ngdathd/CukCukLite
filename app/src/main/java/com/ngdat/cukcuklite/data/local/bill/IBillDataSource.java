package com.ngdat.cukcuklite.data.local.bill;

import java.util.List;

import com.ngdat.cukcuklite.data.models.Bill;
import com.ngdat.cukcuklite.data.models.BillDetail;
import com.ngdat.cukcuklite.data.models.Order;

/**
 * Lớp định nghĩa các phương thức cho lớp thao tác với dữ liệu của hóa đơn
 * Created at 12/04/2019
 */
public interface IBillDataSource {

    List<BillDetail> initNewBillDetailList(String BillId);

    boolean addBill(Bill bill, List<BillDetail> billDetails);

    boolean addBill(Bill bill);

    boolean addBillDetail(BillDetail billDetail);

    boolean addBillDetailList(List<BillDetail> billDetails);

    List<BillDetail> getAllBillDeTailByBillId(String billId);

    List<Bill> getAllBillUnpaid();

    List<Order> getAllOrder();

    boolean cancelOrder(String billId);

    List<BillDetail> initBillDetailList(String billId);

    Bill getBillById(String billId);

    boolean updateBill(Bill bill, List<BillDetail> validBillDetailList);

    List<String> getAllDishIdFromAllBillDetail();

    int countBillWasPaid();

    boolean payBill(Bill bill);
}
