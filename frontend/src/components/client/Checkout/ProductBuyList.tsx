import { memo } from "react";
import Image from "../../Image";
import type { ProductInCart } from "../../../types/type";

type Props = {
  productsInCart: ProductInCart[];
};

function ProductBuyList({ productsInCart }: Props) {
  return (
    <div className="space-y-[15px] bg-white">
      <h4>Đơn hàng</h4>

      {productsInCart.map((item, index) => (
        <div
          className="flex justify-between rounded-lg bg-white gap-[15px]"
          key={index}
        >
          <div className="relative">
            <div className="w-[120px] h-[120px]">
              <Image
                source={`${import.meta.env.VITE_BACKEND_URL}${item.images[0]}`}
                alt={item.name}
                className={"w-full h-full object-contain"}
                loading="eager"
              />
            </div>

            <small className="text-[0.8rem] absolute flex items-center justify-center top-[-7px] right-[-9px] bg-[#C62028] text-white font-medium rounded-full w-[25px] h-[25px]">
              {item.quantity}
            </small>
          </div>

          <div className="flex w-full flex-col my-auto gap-[5px]">
            <span className="font-semibold">{item.title}</span>

            <p className="font-medium text-[#C62028]">
              {item.price.toLocaleString("vi-VN")}₫
            </p>
          </div>

          <h5 className="font-medium text-[#C62028] my-auto">
            {(item.price * item.quantity).toLocaleString("vi-VN") + "₫"}
          </h5>
        </div>
      ))}
    </div>
  );
}

export default memo(ProductBuyList);
