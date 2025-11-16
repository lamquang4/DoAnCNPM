import { LiaEdit } from "react-icons/lia";
import { IoMdAddCircle } from "react-icons/io";
import { TbLock, TbLockOpen } from "react-icons/tb";
import Pagination from "../Pagination";
import Image from "../../Image";
import InputSearch from "../InputSearch";
import toast from "react-hot-toast";
import { Link } from "react-router-dom";
import Loading from "../../Loading";
import useGetUsersByRole from "../../../hooks/admin/useGetUsersByRole";
function Restaurant() {
  const {
    users: restaurants,
    isLoading,
    totalItems,
    totalPages,
    currentPage,
    limit,
  } = useGetUsersByRole(1);

  return (
    <>
      <div className="py-[1.3rem] px-[1.2rem] bg-[#f1f4f9]">
        <div className="flex justify-between items-center">
          <h2 className=" text-[#74767d]">Nhà hàng ({totalItems})</h2>

          <Link
            to={"/admin/add-restaurant"}
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
              <th className="p-[1rem]  ">Họ tên</th>
              <th className="p-[1rem]">Số điện thoại</th>
              <th className="p-[1rem]  ">Email</th>
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
            ) : restaurants.length > 0 ? (
              restaurants.map((restaurant) => (
                <tr key={restaurant.id} className="hover:bg-[#f2f3f8]">
                  <td className="p-[1rem]  font-semibold">
                    {restaurant.fullname}
                  </td>
                  <td className="p-[1rem]  ">{restaurant.phone}</td>
                  <td className="p-[1rem]">{restaurant.email}</td>
                  <td className="p-[1rem]  ">
                    {restaurant.status === 1 ? "Bình thường" : "Bị chặn"}
                  </td>
                  <td className="p-[1rem]  ">
                    <div className="flex items-center gap-[15px]">
                      <Link to={`/admin/edit-restaurant/${restaurant.id}`}>
                        <LiaEdit size={22} className="text-[#076ffe]" />
                      </Link>
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

export default Restaurant;
