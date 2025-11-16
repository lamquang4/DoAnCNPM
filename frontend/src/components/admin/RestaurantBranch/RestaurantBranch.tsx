import Image from "../../Image";
import InputSearch from "../InputSearch";
import { Link } from "react-router-dom";
import { VscTrash } from "react-icons/vsc";
import { LiaEdit } from "react-icons/lia";
import { IoMdAddCircle } from "react-icons/io";
import { TbLock, TbLockOpen } from "react-icons/tb";
import Loading from "../../Loading";
import Pagination from "../Pagination";
import toast from "react-hot-toast";
import type { Restaurant } from "../../../types/type";
function RestaurantBranch() {
  const isLoading = false;
  const restaurants: Restaurant[] = [
    {
      id: "1",
      name: "FoodFast Branch 1",
      speaddress: "12 Nguyễn Huệ",
      ward: "Bến Nghé",
      city: "Hồ Chí Minh",
      location: {
        latitude: 10.77986,
        longitude: 106.68734,
      },
      status: 1,
      createdAt: "2024-01-10T10:30:00Z",
    },
    {
      id: "2",
      name: "FoodFast Branch 2",
      speaddress: "150 Võ Văn Tần",
      ward: "Phường 6",
      city: "Hồ Chí Minh",
      location: {
        latitude: 10.85278,
        longitude: 106.75852,
      },
      status: 1,
      createdAt: "2024-01-12T09:10:00Z",
    },
    {
      id: "3",
      name: "FoodFast Branch 3",
      speaddress: "22 Võ Văn Ngân",
      ward: "Linh Chiểu",
      city: "Thủ Đức",
      location: {
        latitude: 10.86968,
        longitude: 106.80352,
      },
      status: 1,
      createdAt: "2024-01-15T14:45:00Z",
    },
  ];
  return (
    <>
      <div className="py-[1.3rem] px-[1.2rem] bg-[#f1f4f9]">
        <div className="flex justify-between items-center">
          <h2 className=" text-[#74767d]">Chi nhánh nhà hàng ({})</h2>

          <Link
            to={"/admin/add-restaurant-branch"}
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
              <th className="p-[1rem]">Địa chỉ</th>
              <th className="p-[1rem]e">Tình trạng</th>
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
                  <td className="p-[1rem]  font-semibold">{restaurant.name}</td>
                  <td className="p-[1rem]">
                    {`${restaurant.speaddress}, ${restaurant.ward}, ${restaurant.city}`}
                  </td>

                  <td className="p-[1rem]  ">
                    {restaurant.status === 1 ? "Đang mở cửa" : "Đóng cửa"}
                  </td>
                  <td className="p-[1rem]  ">
                    <div className="flex items-center gap-[15px]">
                      <button>
                        {restaurant.status === 1 ? (
                          <TbLock size={22} className="text-[#74767d]" />
                        ) : (
                          <TbLockOpen size={22} className="text-[#74767d]" />
                        )}
                      </button>

                      <Link
                        to={`/admin/edit-restaurant-branch/${restaurant.id}`}
                      >
                        <LiaEdit size={22} className="text-[#076ffe]" />
                      </Link>

                      <button>
                        <VscTrash size={22} className="text-[#d9534f]" />
                      </button>
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

export default RestaurantBranch;
