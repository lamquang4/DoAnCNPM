import { memo, useMemo } from "react";
import {
  HiMiniXMark,
  HiOutlineMinusSmall,
  HiOutlinePlusSmall,
} from "react-icons/hi2";
import { FaArrowRightLong } from "react-icons/fa6";
import Image from "../../Image";
import Overplay from "../Overplay";
import useGetCart from "../../../hooks/client/useGetCart";
import useGetCurrentUser from "../../../hooks/useGetCurrentUser";
import { useRemoveItemCart } from "../../../hooks/client/useRemoveItemCart";
import { useChangeQuantityItemCart } from "../../../hooks/client/useChangeQuantityItemcart";
import Loading from "../../Loading";
import { Link } from "react-router-dom";

type Props = {
  isOpen: boolean;
  toggleMenu: () => void;
};

function MenuSideCart({ isOpen, toggleMenu }: Props) {
  const { user } = useGetCurrentUser("client");
  const { cart, mutate, isLoading } = useGetCart(user?.id!);
  const { removeItem, isLoading: isLoadingRemove } = useRemoveItemCart();
  const { changeQuantity, isLoading: isLoadingChangeQuantity } =
    useChangeQuantityItemCart();

  const totalQuantity = useMemo(() => {
    return (
      cart?.items.reduce((sum, item) => {
        return sum + (item?.quantity || 0);
      }, 0) || 0
    );
  }, [cart?.items]);

  const totalPrice = useMemo(() => {
    return (
      cart?.items.reduce((sum, item) => {
        const finalPrice = item.price;

        return sum + finalPrice * item.quantity;
      }, 0) || 0
    );
  }, [cart?.items]);

  const handleChangeQuantity = async (productId: string, quantity: number) => {
    await changeQuantity(user?.id!, productId, quantity);
    mutate();
  };

  const handleIncrement = (productId: string, currentQuantity: number) => {
    handleChangeQuantity(productId, currentQuantity + 1);
  };

  const handleDecrement = (id: string, currentQuantity: number) => {
    if (currentQuantity <= 1) return;
    handleChangeQuantity(id, currentQuantity - 1);
  };

  const handleRemoveItem = async (productId: string) => {
    await removeItem(user?.id!, productId);
    mutate();
  };
  return (
    <>
      <div
        className={`fixed top-0 right-0 w-[400px] h-screen bg-white z-[25] transform transition-transform duration-300 ease-in-out ${
          isOpen ? "translate-x-0" : "translate-x-[400px]"
        } shadow-lg`}
      >
        <div className="sticky top-0 p-4 flex justify-between items-center border-b border-gray-300 bg-white z-10">
          <h4 className="uppercase font-semibold">
            Giỏ hàng ({totalQuantity})
          </h4>
          <button onClick={toggleMenu}>
            <HiMiniXMark size={28} />
          </button>
        </div>

        <div className="overflow-y-auto h-[calc(100vh-120px)] p-4 space-y-4">
          {isLoading ? (
            <Loading height={60} size={50} color="black" thickness={2} />
          ) : cart && cart?.items.length > 0 ? (
            cart?.items.map((item) => {
              return (
                <div
                  key={item.productId}
                  className="flex items-center justify-between border-b border-gray-200 pb-2"
                >
                  <div className="flex items-center gap-3">
                    {item.image && (
                      <div className="w-20 h-20 overflow-hidden">
                        <Image
                          source={item.image}
                          alt={item.name!}
                          className="w-full h-full object-cover"
                          loading="lazy"
                        />
                      </div>
                    )}

                    <div className="space-y-3">
                      <h5 className="font-medium">{item.name}</h5>
                      <p className="text-[#C62028] font-semibold">
                        {(item.price * 2).toLocaleString("vi-VN")}₫
                      </p>
                      <div className="flex items-center gap-3">
                        <button
                          onClick={() =>
                            handleDecrement(item.productId, item.quantity)
                          }
                          disabled={
                            item.quantity <= 1 || isLoadingChangeQuantity
                          }
                          className="flex items-center justify-center w-6 h-6 outline-none bg-[#F7F7F7] border-gray-300 border"
                        >
                          <HiOutlineMinusSmall size={15} />
                        </button>

                        <span className="font-medium">{2}</span>

                        <button
                          onClick={() =>
                            handleIncrement(item.productId, item.quantity)
                          }
                          className="flex items-center justify-center w-6 h-6 outline-none bg-[#F7F7F7] border-gray-300 border"
                        >
                          <HiOutlinePlusSmall size={15} />
                        </button>
                      </div>
                    </div>
                  </div>

                  <button
                    disabled={isLoadingRemove}
                    onClick={() => handleRemoveItem(item.productId)}
                  >
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      className="w-5 h-5 cursor-pointer fill-black hover:fill-red-600 inline-block"
                      viewBox="0 0 24 24"
                    >
                      <path
                        d="M19 7a1 1 0 0 0-1 1v11.191A1.92 1.92 0 0 1 15.99 21H8.01A1.92 1.92 0 0 1 6 19.191V8a1 1 0 0 0-2 0v11.191A3.918 3.918 0 0 0 8.01 23h7.98A3.918 3.918 0 0 0 20 19.191V8a1 1 0 0 0-1-1Zm1-3h-4V2a1 1 0 0 0-1-1H9a1 1 0 0 0-1 1v2H4a1 1 0 0 0 0 2h16a1 1 0 0 0 0-2ZM10 4V3h4v1Z"
                        data-original="#000000"
                      ></path>
                      <path
                        d="M11 17v-7a1 1 0 0 0-2 0v7a1 1 0 0 0 2 0Zm4 0v-7a1 1 0 0 0-2 0v7a1 1 0 0 0 2 0Z"
                        data-original="#000000"
                      ></path>
                    </svg>
                  </button>
                </div>
              );
            })
          ) : (
            <div className="flex justify-center items-center h-[60vh]">
              <div className="flex flex-col justify-center items-center gap-[15px]">
                <Image
                  source={"/assets/empty-cart.png"}
                  alt={""}
                  className={"w-[140px]"}
                  loading="eager"
                />

                <h4 className="text-gray-600">Không có gì trong giỏ hết</h4>
              </div>
            </div>
          )}
        </div>

        <div className="fixed bottom-0 right-0 w-[400px] bg-white p-4 border-t border-gray-300 flex flex-col gap-2">
          <div className="flex justify-between">
            <h4>Tổng:</h4>
            <h4>{totalPrice.toLocaleString("vi-VN")}₫</h4>
          </div>

          <Link
            to="/checkout"
            className="bg-[#C62028] text-white py-3 rounded font-semibold text-[0.9rem]"
          >
            <div className="flex justify-center items-center gap-2 ">
              Thanh toán
              <FaArrowRightLong size={20} />
            </div>
          </Link>
        </div>
      </div>

      {isOpen && <Overplay closeMenu={toggleMenu} IndexForZ={15} />}
    </>
  );
}

export default memo(MenuSideCart);
