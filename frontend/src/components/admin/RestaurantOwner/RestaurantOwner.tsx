import { LiaEdit } from "react-icons/lia";
import { IoMdAddCircle } from "react-icons/io";
import Pagination from "../Pagination";
import Image from "../../Image";
import InputSearch from "../InputSearch";
import { Link } from "react-router-dom";
import Loading from "../../Loading";
import useGetUsersByRole from "../../../hooks/admin/useGetUsersByRole";
import useUpdateStatusUser from "../../../hooks/admin/useUpdateStatusUser";
import toast from "react-hot-toast";
import { TbLock, TbLockOpen } from "react-icons/tb";
function RestaurantOwner() {
  const {
    users: restaurants,
    mutate,
    isLoading,
    totalItems,
    totalPages,
    currentPage,
    limit,
  } = useGetUsersByRole(1);
  const { updateStatusUser, isLoading: isLoadingUpdate } =
    useUpdateStatusUser();

  const handleUpdateStatus = async (id: string, status: number) => {
    if (!id && !status) {
      return;
    }

    try {
      await updateStatusUser(id, status);
      mutate();
    } catch (err: any) {
      toast.error(err?.response?.data?.message);
      mutate();
    }
  };

  return (
    <>
      <div className="py-[1.3rem] px-[1.2rem] bg-[#f1f4f9]">
             <div className="flex justify-between items-center flex-wrap gap-[20px]">
          <h2 className=" text-[#74767d]">Chủ nhà hàng ({totalItems})</h2>

          <Link
            to={"/admin/add-restaurant-owner"}
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
                      <button
                        disabled={isLoadingUpdate}
                        onClick={() =>
                          handleUpdateStatus(
                            restaurant?.id || "",
                            restaurant?.status === 1 ? 0 : 1
                          )
                        }
                      >
                        {restaurant?.status === 1 ? (
                          <TbLock size={22} className="text-[#74767d]" />
                        ) : (
                          <TbLockOpen size={22} className="text-[#74767d]" />
                        )}
                      </button>

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

export default RestaurantOwner;
