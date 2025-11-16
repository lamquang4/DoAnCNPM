import Image from "../../Image";
import InputSearch from "../InputSearch";
import { Link } from "react-router-dom";
import { VscTrash } from "react-icons/vsc";
import { LiaEdit } from "react-icons/lia";
import { IoMdAddCircle } from "react-icons/io";
import Loading from "../../Loading";
import Pagination from "../Pagination";
import toast from "react-hot-toast";
import type { Drone } from "../../../types/type";
import { HiOutlineWrenchScrewdriver } from "react-icons/hi2";
function Drone() {
  const isLoading = false;
  const drones: Drone[] = [
    {
      id: "D001",
      model: "DJI Mavic Pro",
      capacity: 5,
      battery: 85,
      range: 15,
      status: 1,
      createdAt: "2025-01-10",
      restaurant: {
        id: "R001",
        name: "Chi nhánh Hà Nội",
        speaddress: "12 Lê Duẩn",
        ward: "Hoàn Kiếm",
        city: "Hà Nội",
        status: 1,
        location: {
          latitude: 21.0278,
          longitude: 105.8342,
        },
      },
    },
    {
      id: "D002",
      model: "DJI Phantom 4",
      capacity: 8,
      battery: 65,
      range: 15,
      status: 1,
      createdAt: "2025-01-11",
      restaurant: {
        id: "R002",
        name: "Chi nhánh Sài Gòn",
        speaddress: "22 Nguyễn Huệ",
        ward: "Bến Nghé",
        city: "TP. Hồ Chí Minh",
        status: 1,
        location: {
          latitude: 10.7758,
          longitude: 106.7004,
        },
      },
    },
    {
      id: "D003",
      model: "Autel EVO II",
      capacity: 10,
      battery: 40,
      range: 15,
      status: 0,
      createdAt: "2025-01-15",
      restaurant: {
        id: "R003",
        name: "Chi nhánh Đà Nẵng",
        speaddress: "88 Trần Phú",
        ward: "Hải Châu 1",
        city: "Đà Nẵng",
        status: 1,
        location: {
          latitude: 16.0471,
          longitude: 108.2068,
        },
      },
    },
  ];
  return (
    <>
      <div className="py-[1.3rem] px-[1.2rem] bg-[#f1f4f9]">
        <div className="flex justify-between items-center">
          <h2 className=" text-[#74767d]">Drone ({})</h2>

          <Link
            to={"/admin/add-drone"}
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
              <th className="p-[1rem]">Model</th>
              <th className="p-[1rem]">Địa chỉ nhà hàng</th>
              <th className="p-[1rem]">Sức chứa (kg)</th>
              <th className="p-[1rem]"> Quãng đường bay tối đa (km)</th>
              <th className="p-[1rem]">Pin (%)</th>
              <th className="p-[1rem]">Tình trạng</th>

              <th className="p-[1rem]">Hành động</th>
            </tr>
          </thead>

          <tbody>
            {isLoading ? (
              <tr>
                <td colSpan={8} className="w-full">
                  <Loading height={60} size={50} color="black" thickness={2} />
                </td>
              </tr>
            ) : drones.length > 0 ? (
              drones.map((drone) => (
                <tr key={drone.id} className="hover:bg-[#f2f3f8]">
                  <td className="p-[1rem] font-semibold">{drone.model}</td>

                  <td className="p-[1rem]">
                    {drone.restaurant.name} -
                    {` ${drone.restaurant.speaddress}, ${drone.restaurant.ward}, ${drone.restaurant.city}`}
                  </td>
                  <td className="p-[1rem]">{drone.capacity}kg</td>
                  <td className="p-[1rem]">{drone.range}km</td>
                  <td className="p-[1rem]">{drone.battery}%</td>

                  <td className="p-[1rem]">
                    {drone.status === 0
                      ? "Đang rảnh"
                      : drone.status === 1
                      ? "Đang giao"
                      : drone.status === 2
                      ? "Giao thành công"
                      : drone.status === 3
                      ? "Đang bay về"
                      : drone.status === 4
                      ? "Bảo trì"
                      : ""}
                  </td>

                  <td className="p-[1rem]">
                    <div className="flex items-center gap-[15px]">
                      <button>
                        <HiOutlineWrenchScrewdriver
                          size={20}
                          className="text-[#74767d]"
                        />
                      </button>

                      <Link to={`/admin/edit-drone/${drone.id}`}>
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

export default Drone;
