import { VscTrash } from "react-icons/vsc";
import { LiaEdit } from "react-icons/lia";
import { IoMdAddCircle } from "react-icons/io";
import { FaRegEyeSlash } from "react-icons/fa";
import Image from "../../Image";
import Pagination from "../Pagination";
import { MdOutlineRemoveRedEye } from "react-icons/md";
import Loading from "../../Loading";
import InputSearch from "../InputSearch";
import toast from "react-hot-toast";
import { Link } from "react-router-dom";
function Product() {
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

  const isLoading = false;

  return (
    <>
      <div className="py-[1.3rem] px-[1.2rem] bg-[#f1f4f9]">
        <div className="flex justify-between items-center">
          <h2 className=" text-[#74767d]">Đồ ăn</h2>

          <Link
            to={"/admin/add-product"}
            className="bg-[#C62028] border-0 cursor-pointer text-[0.9rem] font-medium w-[90px] !flex p-[10px_12px] items-center justify-center gap-[5px] text-white"
          >
            <IoMdAddCircle size={22} /> Thêm
          </Link>
        </div>
      </div>

      <div className=" bg-white w-full overflow-auto">
        <div className="p-[1.2rem]">
          <InputSearch />
        </div>

        <table className="w-[350%] border-collapse sm:w-[220%] xl:w-full text-[0.9rem]">
          <thead>
            <tr className="bg-[#E9EDF2] text-left">
              <th className="p-[1rem]  ">Tên</th>
              <th className="p-[1rem]  ">Giá</th>
              <th className="p-[1rem]">Tình trạng</th>
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
            ) : products.length > 0 ? (
              products.map((product) => {
                return (
                  <tr key={product.id} className="hover:bg-[#f2f3f8]">
                    <td className="p-[1rem] font-semibold">
                      <div className="flex gap-[10px] items-center">
                        <div className="relative group w-[80px] h-[80px] overflow-hidden">
                          {product.image && (
                            <Image
                              source={product.image}
                              alt={product.name}
                              className={
                                "w-full h-full object-contain z-1 relative"
                              }
                              loading="lazy"
                            />
                          )}
                        </div>

                        <p>{product.name}</p>
                      </div>
                    </td>

                    <td className="p-[1rem]  ">
                      <p className="font-medium text-[#C62028]">
                        {product.price.toLocaleString("vi-VN")}₫
                      </p>
                    </td>

                    <td className="p-[1rem]  ">
                      {product.status === 1
                        ? "Hiện"
                        : product.status === 0
                        ? "Ẩn"
                        : ""}
                    </td>

                    <td className="p-[1rem]  ">
                      <div className="flex items-center gap-[15px]">
                        <button>
                          {product.status === 1 ? (
                            <FaRegEyeSlash
                              size={22}
                              className="text-[#74767d]"
                            />
                          ) : (
                            <MdOutlineRemoveRedEye
                              size={22}
                              className="text-[#74767d]"
                            />
                          )}
                        </button>
                        <Link to={`/admin/edit-product/${product.id}`}>
                          <LiaEdit size={22} className="text-[#076ffe]" />
                        </Link>
                        <button>
                          <VscTrash size={22} className="text-[#d9534f]" />
                        </button>
                      </div>
                    </td>
                  </tr>
                );
              })
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

      {/*
      <Pagination
        totalPages={totalPages}
        currentPage={currentPage}
        limit={limit}
        totalItems={totalItems}
      />
  */}
    </>
  );
}

export default Product;
