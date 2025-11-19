import { useCallback, useEffect, useMemo, useState } from "react";
import Image from "../../Image";
import Loading from "../../Loading";
import Overplay from "./../Overplay";
import ProductBuyList from "./ProductBuyList";
import ShippingInfoForm from "./ShippingInfoForm";
import PaymentMethod from "./PaymentMethod";
import useCurrentUser from "../../../hooks/useGetCurrentUser";
import useGetCart from "../../../hooks/client/useGetCart";
import { Link, useNavigate } from "react-router-dom";
import { MdOutlineKeyboardBackspace } from "react-icons/md";
import toast from "react-hot-toast";
import useAddOrder from "../../../hooks/client/useAddOrder";
import usePaymentMomo from "../../../hooks/client/usePaymentMomo";
import { validatePhone } from "../../../utils/validatePhone";
import useGetProvincesVN from "../../../hooks/useGetProvincesVN";

function CheckoutForm() {
  const navigate = useNavigate();
  const { user } = useCurrentUser("client");
  const {
    cart,
    isLoading: isLoadingCart,
    mutate: mutateCart,
  } = useGetCart(user?.id || "");

  const { provinces } = useGetProvincesVN();
  const { addOrder, isLoading: isLoadingAdd } = useAddOrder(user?.id || "");
  const { createPaymentMomo, isLoading: isLoadingPaymentMomo } =
    usePaymentMomo();

  const [data, setData] = useState({
    fullname: "",
    phone: "",
    speaddress: "",
    city: "",
    ward: "",
    restaurantId: "",
  });
  const [paymethod, setPaymethod] = useState<string>("");
  const [latLng, setLatLng] = useState<{
    lat: number | null;
    lng: number | null;
  }>({ lat: null, lng: null });

  useEffect(() => {
    if (isLoadingCart) return;

    /*
    if (!cart || !cart.items?.length) {
      toast.error("Không có gì trong giỏ hết");
      navigate("/");
      return;
    }
      */
  }, [cart, navigate, isLoadingCart]);

  const handleChange = useCallback(
    (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
      const { name, value } = e.target;
      setData((prev) => ({ ...prev, [name]: value }));
    },
    []
  );

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (!paymethod) {
      toast.error("Vui lòng chọn phương thức thanh toán");
      return;
    }

    if (cart?.items.length === 0) {
      toast.error("Không có gì trong giỏ hết");
      navigate("/cart");
      return;
    }

    if (!validatePhone(data.phone)) {
      toast.error("Số điện thoại không hợp lệ");
      return;
    }

    if (!latLng.lat || !latLng.lng) {
      toast.error("Địa chỉ không tìm thấy");
      return;
    }

    if (paymethod === "momo") {
      try {
      } catch (err: any) {
        toast.error(err?.response?.data?.msg);
      }
    }
  };

  const totalPrice = 200000;

  return (
    <section className="my-[40px] px-[15px] text-black">
      <div className="mx-auto max-w-[1200px] w-full">
        <Link to={"/"}>
          <h2>Foodfast</h2>
        </Link>

        <hr className="border-gray-300 my-[15px]" />

        <form onSubmit={handleSubmit}>
          <div className="grid lg:grid-cols-2 gap-[40px]">
            <div className="order-last lg:order-first space-y-[15px]">
              <div className="space-y-[30px]">
                <ShippingInfoForm
                  data={data}
                  setData={setData}
                  handleChange={handleChange}
                  provinces={provinces ?? []}
                  setLatLng={setLatLng}
                />

                <PaymentMethod
                  paymethod={paymethod}
                  setPaymethod={setPaymethod}
                />

                <div className="flex justify-between items-center">
                  <button className="text-[0.9rem] rounded-md bg-[#C62028] px-4 py-2 font-medium text-white">
                    Đặt hàng
                  </button>

                  <Link
                    to={"/"}
                    className="text-[0.95rem] rounded-md bg-transparent px-4 py-2 font-medium text-[#C62028] border border-[#C62028]"
                  >
                    <div className="flex gap-[5px] items-center">
                      <MdOutlineKeyboardBackspace size={25} /> Quay về
                    </div>
                  </Link>
                </div>
              </div>
            </div>

            <div className="order-first lg:order-last space-y-[15px] lg:sticky lg:top-0 lg:self-start">
              <ProductBuyList productsInCart={cart?.items ?? []} />

              <hr className="border-gray-300" />

              <div className="flex items-center justify-between font-medium ">
                <h5>Phí giao hàng:</h5>
                <h5>Miễn phí</h5>
              </div>

              <div className="flex items-center justify-between font-semibold">
                <h5>Tổng cộng:</h5>
                <h5>{totalPrice.toLocaleString("vi-VN")}₫</h5>
              </div>
            </div>
          </div>
        </form>
      </div>

      {(isLoadingCart || isLoadingAdd || isLoadingPaymentMomo) && (
        <Overplay IndexForZ={50}>
          <Loading height={0} size={55} color="white" thickness={8} />
          <h4 className="text-white">Vui lòng chờ trong giây lát...</h4>
        </Overplay>
      )}
    </section>
  );
}

export default CheckoutForm;
