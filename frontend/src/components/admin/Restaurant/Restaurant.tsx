import Image from "../../Image";
import InputSearch from "../InputSearch";
import Loading from "../../Loading";
import Pagination from "../Pagination";
import useGetRestaurants from "../../../hooks/admin/useGetRestaurants";
function Restaurant() {
  const { restaurants, isLoading, currentPage, totalItems, totalPages, limit } =
    useGetRestaurants();

  return (
    <>
      <div className="py-[1.3rem] px-[1.2rem] bg-[#f1f4f9]">
        <div className="flex justify-between items-center">
          <h2 className=" text-[#74767d]">Nhà hàng ({totalItems})</h2>
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
              <th className="p-[1rem]">Chủ nhà hàng</th>
              <th className="p-[1rem]e">Tình trạng</th>
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

                  <td className="p-[1rem]  ">{restaurant.fullname}</td>

                  <td className="p-[1rem]  ">
                    {restaurant.status === 1 ? "Đang mở cửa" : "Đóng cửa"}
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
