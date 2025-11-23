import { useState } from "react";
import Image from "../../Image";
import Loading from "../../Loading";
import { HiOutlineMinusSmall, HiOutlinePlusSmall } from "react-icons/hi2";
import { BiCartAdd } from "react-icons/bi";
import { useAddItemToCart } from "../../../hooks/client/useAddItemToCart";
import useGetCurrentUser from "../../../hooks/useGetCurrentUser";
import toast from "react-hot-toast";
import { useNavigate } from "react-router-dom";
import useGetCart from "../../../hooks/client/useGetCart";
import useGetActiveProducts from "../../../hooks/client/useGetActiveProducts";
import Pagination from "../Pagination";

function ProductList() {
  const navigate = useNavigate();
  const [quantities, setQuantities] = useState<Record<string, number>>({});
  const { products, isLoading, currentPage, totalItems, totalPages } =
    useGetActiveProducts();
  const { addItem, isLoading: isLoadingAdd } = useAddItemToCart();
  const { user } = useGetCurrentUser("client");
  const { mutate } = useGetCart(user?.id || "");
  const max = 20;

  const increase = (id: string) => {
    setQuantities((prev) => ({
      ...prev,
      [id]: (prev[id] || 1) + 1,
    }));
  };

  const decrease = (id: string) => {
    setQuantities((prev) => ({
      ...prev,
      [id]: prev[id] > 1 ? prev[id] - 1 : 1,
    }));
  };

  const handleAddItemToCart = async (productId: string) => {
    if (!user?.id) {
      toast.error("Bạn phải đăng nhập");
      navigate("/login");
      return;
    }

    const quantity = quantities[productId] || 1;

    try {
      await addItem(user.id, productId, quantity);
      mutate();
    } catch (err: any) {
      toast.error(err?.response?.data?.message);
    }
  };

  return (
    <>
      {products.length > 0 && (
        <section
          className="mb-[40px] px-[15px] text-black scroll-mt-[100px]"
          id="menu"
        >
          <div className="mx-auto max-w-[1200px] w-full">
            <h2 className="mb-[20px]">Thực đơn</h2>

            {isLoading ? (
              <Loading height={60} size={50} color="black" thickness={2} />
            ) : (
              <div className="grid grid-cols-2 gap-x-[15px] gap-y-[35px] lg:grid-cols-3 2xl:grid-cols-4">
                {products.map((product) => {
                  const qty = quantities[product.id] || 1;

                  return (
                    <div className="space-y-[15px] rounded-sm" key={product.id}>
                      <div className="relative group">
                        {product.image && (
                          <div className="w-full overflow-hidden pt-[100%]">
                            <Image
                              source={`${product.image}`}
                              alt={product.name}
                              className="absolute inset-0 w-full h-full object-cover"
                              loading="lazy"
                            />
                          </div>
                        )}
                      </div>

                      <div className="space-y-[6px]">
                        <h5 className="font-medium capitalize">
                          {product.name}
                        </h5>

                        <h5 className="font-semibold text-[#C62028]">
                          {product.price.toLocaleString("vi-VN")}₫
                        </h5>
                      </div>

                      <div className="flex justify-between items-center gap-3">
                        <div className="flex items-center gap-3">
                          <button
                            className="flex items-center justify-center w-7 h-7 outline-none bg-[#F7F7F7] border-gray-300 border"
                            onClick={() => decrease(product.id)}
                          >
                            <HiOutlineMinusSmall size={18} />
                          </button>

                          <span className="font-medium">{qty}</span>

                          <button
                            className="flex items-center justify-center w-7 h-7 outline-none bg-[#F7F7F7] border-gray-300 border"
                            onClick={() => increase(product.id)}
                            disabled={qty >= max}
                          >
                            <HiOutlinePlusSmall size={18} />
                          </button>
                        </div>

                        <button
                          className=" bg-[#C62028] text-white rounded font-medium px-2.5 py-1 text-[0.9rem]"
                          onClick={() => handleAddItemToCart(product.id)}
                          disabled={isLoadingAdd}
                        >
                          <BiCartAdd size={22} />
                        </button>
                      </div>
                    </div>
                  );
                })}
              </div>
            )}

            <Pagination
              totalPages={totalPages}
              currentPage={currentPage}
              totalItems={totalItems}
            />
          </div>
        </section>
      )}
    </>
  );
}

export default ProductList;
