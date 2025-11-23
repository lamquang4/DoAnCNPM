import Image from "../../Image";
import InputSearch from "../InputSearch";
import Loading from "../../Loading";
import Pagination from "../Pagination";
import useGetDrones from "../../../hooks/restaurant/useGetDrones";
import useGetCurrentUser from "../../../hooks/useGetCurrentUser";

function Drone() {
  const { user } = useGetCurrentUser("restaurant");
  const { drones, isLoading, totalItems, totalPages, currentPage, limit } =
    useGetDrones(user?.id || "");

  return (
    <>
      <div className="py-[1.3rem] px-[1.2rem] bg-[#f1f4f9]">
        <div className="flex justify-between items-center flex-wrap gap-[20px]">
          <h2 className=" text-[#74767d]">Drone ({totalItems})</h2>
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
              <th className="p-[1rem]">Nhà hàng</th>
              <th className="p-[1rem]">Sức chứa (kg)</th>
              <th className="p-[1rem]"> Quãng đường bay tối đa (km)</th>
              <th className="p-[1rem]">Pin (%)</th>
              <th className="p-[1rem]">Tình trạng</th>
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

                  <td className="p-[1rem]">{drone.restaurantName}</td>
                  <td className="p-[1rem]">{drone.capacity}kg</td>
                  <td className="p-[1rem]">{drone.range}km</td>
                  <td className="p-[1rem]">{drone.battery}%</td>

                  <td className="p-[1rem]">
                    {drone.status === 0
                      ? "Đang rảnh"
                      : drone.status === 1
                      ? "Đang giao"
                      : drone.status === 2
                      ? "Bảo trì"
                      : ""}
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

export default Drone;
