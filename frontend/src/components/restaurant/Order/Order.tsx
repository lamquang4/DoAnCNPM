import { LiaExternalLinkAltSolid } from "react-icons/lia";
import Image from "../../Image";
import Pagination from "../Pagination";
import InputSearch from "../InputSearch";
import Loading from "../../Loading";
import { Link } from "react-router-dom";
import { SiTicktick } from "react-icons/si";
import useUpdateStatusOrder from "../../../hooks/useUpdateStatusOrder";
import toast from "react-hot-toast";
import useGetCurrentUser from "../../../hooks/useGetCurrentUser";
import useGetOrders from "../../../hooks/admin/useGetOrders";

function Order() {
  const { user } = useGetCurrentUser("restaurant");
  // do lỗi không lấy được đơn hàng của các nhà hàng thuộc tài khoản chủ nhà hàng
  const {
    orders,
    mutate,
    isLoading,
    totalItems,
    totalPages,
    limit,
    currentPage,
  } = useGetOrders();
  const { updateStatusOrder, isLoading: isLoadingUpdate } =
    useUpdateStatusOrder();

  console.log(orders);

  const handleUpdateStatus = async (id: string, status: number) => {
    if (!id && !status) {
      return;
    }

    try {
      await updateStatusOrder(id, status);
      mutate();
    } catch (err: any) {
      toast.error(err?.response?.data?.message);
      mutate();
    }
  };
  return (
    <>
      <div className="py-[1.3rem] px-[1.2rem] bg-[#f1f4f9] space-y-[20px]">
        <h2 className=" text-[#74767d]">Đơn hàng ({totalItems})</h2>
      </div>

      <div className=" bg-white  w-full overflow-auto">
        <div className="p-[1.2rem]">
          <InputSearch />
        </div>

        <table className="w-[350%] border-collapse sm:w-[220%] xl:w-full text-[0.9rem]">
          <thead>
            <tr className="bg-[#E9EDF2] text-left">
              <th className="p-[1rem]  ">Mã đơn</th>

              <th className="p-[1rem]  ">Người đặt</th>

              <th className="p-[1rem]  ">Thanh toán</th>

              <th className="p-[1rem]  ">Tổng tiền</th>
              <th className="p-[1rem]  ">Ngày tạo</th>
              <th className="p-[1rem] ">Tình trạng</th>
              <th className="p-[1rem]  ">Hành động</th>
            </tr>
          </thead>
          <tbody>
            {isLoading ? (
              <tr>
                <td colSpan={8} className="w-full">
                  <Loading height={60} size={50} color="black" thickness={2} />
                </td>
              </tr>
            ) : orders.length > 0 ? (
              orders.map((order) => (
                <tr key={order.id} className="hover:bg-[#f2f3f8]">
                  <td className="p-[1rem] font-semibold">{order.orderCode}</td>
                  <td className="p-[1rem]  ">{order.fullname}</td>
                  <td className="p-[1rem] uppercase">{order.paymethod}</td>
                  <td className="p-[1rem]  ">
                    {order.total!.toLocaleString("vi-VN")}₫
                  </td>
                  <td className="p-[1rem]">
                    {new Date(order.createdAt!).toLocaleString("vi-VN", {
                      year: "numeric",
                      month: "2-digit",
                      day: "2-digit",
                      hour: "2-digit",
                      minute: "2-digit",
                    })}
                  </td>

                  <td className="p-[1rem]">
                    {order.status === -1
                      ? "Chờ thanh toán"
                      : order.status === 0
                      ? "Đang xử lý"
                      : order.status === 1
                      ? "Đang giao"
                      : order.status === 2
                      ? "Giao thành công"
                      : ""}
                  </td>
                  <td className="p-[1rem]  ">
                    <div className="flex items-center gap-[15px]">
                      <Link to={`/restaurant/order/${order.id}`}>
                        <LiaExternalLinkAltSolid
                          size={23}
                          className="text-[#076ffe]"
                        />
                      </Link>

                      {order.status === 0 && (
                        <button
                          className="text-green-500"
                          disabled={isLoadingUpdate}
                          onClick={() => handleUpdateStatus(order?.id || "", 1)}
                        >
                          <SiTicktick size={20} />
                        </button>
                      )}
                    </div>
                  </td>
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan={8} className="w-full h-[70vh]">
                  <div className="flex justify-center items-center">
                    <Image
                      source={"/assets/notfound1.png"}
                      alt={""}
                      className={"w-[135px]"}
                      loading="lazy"
                    />
                  </div>
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>

      <Pagination
        totalPages={totalPages}
        currentPage={currentPage}
        limit={limit}
        totalItems={totalItems}
      />
    </>
  );
}

export default Order;
