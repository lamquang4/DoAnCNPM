import OrderInfo from "./OrderInfo";
import SideBarMenu from "../../SideMenuBar";
import { useNavigate, useParams } from "react-router-dom";
import useGetOrder from "../../../../hooks/useGetOrder";
import { useEffect } from "react";
import toast from "react-hot-toast";
function OrderDetail() {
  const navigate = useNavigate();
  const { code } = useParams();
  const { order, isLoading } = useGetOrder(code as string);

  useEffect(() => {
    if (isLoading) return;

    if (!order) {
      toast.error("Đơn hàng không tìm thấy");
      navigate("/order");
    }
  }, [isLoading, order, navigate]);

  return (
    <>
      <section className="my-[40px]">
        <div className="w-full max-w-[1200px] mx-auto">
          <div className="flex justify-center flex-wrap gap-5">
            <SideBarMenu />

            <OrderInfo order={order} isLoading={isLoading} />
          </div>
        </div>
      </section>
    </>
  );
}

export default OrderDetail;
