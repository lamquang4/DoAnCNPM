import { memo, useState } from "react";
import {
  HiMiniXMark,
  HiOutlineMinusSmall,
  HiOutlinePlusSmall,
} from "react-icons/hi2";
import { FaArrowRightLong } from "react-icons/fa6";
import Image from "../Image";
import Overplay from "./Overplay";

type Props = {
  isOpen: boolean;
  toggleMenu: () => void;
};

function MenuSideCart({ isOpen, toggleMenu }: Props) {
  const products = [
    {
      id: "p1",
      name: "Cơm gà viên Nanban",
      image: "/assets/products/com-ga-vien-nanban.png",
      price: 120000,
      status: 1,
    },
    {
      id: "p2",
      name: "Gà miếng",
      image: "/assets/products/ga-mieng.png",
      price: 45000,
      status: 1,
    },
  ];

  const totalPrice = 200000;
  return (
    <>
      <div
        className={`fixed top-0 right-0 w-[400px] h-screen bg-white z-[25] transform transition-transform duration-300 ease-in-out ${
          isOpen ? "translate-x-0" : "translate-x-[400px]"
        } shadow-lg`}
      >
        <div className="sticky top-0 p-4 flex justify-between items-center border-b border-gray-300 bg-white z-10">
          <h4 className="uppercase font-semibold">Giỏ hàng</h4>
          <button onClick={toggleMenu}>
            <HiMiniXMark size={28} />
          </button>
        </div>

        <div className="overflow-y-auto h-[calc(100vh-120px)] p-4 space-y-4">
          {products.length > 0 ? (
            products.map((product) => {
              return (
                <div
                  key={product.id}
                  className="flex items-center justify-between border-b border-gray-200 pb-2"
                >
                  <div className="flex items-center gap-3">
                    {product.image && (
                      <div className="w-20 h-20 overflow-hidden">
                        <Image
                          source={product.image}
                          alt={product.name}
                          className="w-full h-full object-cover"
                          loading="lazy"
                        />
                      </div>
                    )}

                    <div className="space-y-3">
                      <h5 className="font-medium">{product.name}</h5>
                      <p className="text-[#C62028] font-semibold">
                        {(product.price * 2).toLocaleString("vi-VN")}₫
                      </p>
                      <div className="flex items-center gap-3">
                        <button className="flex items-center justify-center w-6 h-6 outline-none bg-[#F7F7F7] border-gray-300 border">
                          <HiOutlineMinusSmall size={15} />
                        </button>

                        <span className="font-medium">{2}</span>

                        <button className="flex items-center justify-center w-6 h-6 outline-none bg-[#F7F7F7] border-gray-300 border">
                          <HiOutlinePlusSmall size={15} />
                        </button>
                      </div>
                    </div>
                  </div>

                  <button>
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

          <button className="bg-[#C62028] text-white py-3 flex justify-center items-center gap-2 rounded font-semibold text-[0.9rem]">
            Thanh toán
            <FaArrowRightLong size={20} />
          </button>
        </div>
      </div>

      {isOpen && <Overplay closeMenu={toggleMenu} IndexForZ={15} />}
    </>
  );
}

export default memo(MenuSideCart);
