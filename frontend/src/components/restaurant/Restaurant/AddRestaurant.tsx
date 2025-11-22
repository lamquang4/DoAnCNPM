import { useMemo, useState } from "react";
import toast from "react-hot-toast";
import { Link } from "react-router-dom";
import useGetProvincesVN from "../../../hooks/useGetProvincesVN";
import useGeocodeAddress from "../../../hooks/useGeocodeAddress";
import LeafletMap from "../../LeafletMap";
import useAddRestaurant from "../../../hooks/restaurant/useAddRestaurant";
import useGetCurrentUser from "../../../hooks/useGetCurrentUser";

function AddRestaurant() {
  const [data, setData] = useState({
    name: "",
    speaddress: "",
    ward: "",
    city: "",
    status: "",
  });

  const { user } = useGetCurrentUser("restaurant");
  const { addRestaurant, isLoading } = useAddRestaurant();
  const { provinces } = useGetProvincesVN();

  const selectedProvince = useMemo(
    () => provinces?.find((p) => p.province === data.city),
    [provinces, data.city]
  );

  const fullAddress = useMemo(() => {
    if (!data.speaddress || !data.ward || !data.city) return "";
    return `${data.speaddress}, ${data.ward}, ${data.city}, Việt Nam`;
  }, [data.speaddress, data.ward, data.city]);

  const { lat, lng } = useGeocodeAddress(fullAddress);

  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>
  ) => {
    const { name, value } = e.target;
    setData({
      ...data,
      [name]: value,
    });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (!lat || !lng) {
      toast.error("Địa chỉ không tìm thấy");
      return;
    }

    if (!user?.id) {
      toast.error("Bạn chưa đăng nhập");
      return;
    }

    try {
      await addRestaurant({
        name: data.name,
        speaddress: data.speaddress,
        ward: data.ward,
        city: data.city,
        location: { latitude: lat, longitude: lng },
        status: 1,
        ownerId: user?.id,
      });

      setData({
        name: "",
        speaddress: "",
        ward: "",
        city: "",
        status: "",
      });
    } catch (err: any) {
      toast.error(err?.response?.data?.message);
    }
  };

  console.log(lat, lng);

  return (
    <div className="py-[30px] sm:px-[25px] px-[15px] bg-[#F1F4F9] h-auto">
      <form
        className="flex flex-col gap-7 w-full h-full"
        onSubmit={handleSubmit}
      >
        <h2 className="text-[#74767d]">Thêm nhà hàng</h2>

        <div className="flex gap-[25px] w-full flex-col">
          <div className="md:p-[25px] p-[15px] bg-white rounded-md flex flex-col gap-[20px] w-full">
            <h5 className="font-bold text-[#74767d]">Thông tin chung</h5>

            <div className="flex flex-col gap-1">
              <label htmlFor="" className="text-[0.9rem] font-medium">
                Tên
              </label>
              <input
                type="text"
                name="name"
                value={data.name}
                onChange={handleChange}
                required
                className="border border-gray-300 p-[6px_10px] text-[0.9rem] w-full outline-none focus:border-gray-400  "
              />
            </div>

            <div className="flex flex-col gap-1">
              <label htmlFor="" className="text-[0.9rem] font-medium">
                Địa chỉ cụ thể
              </label>
              <input
                type="text"
                name="speaddress"
                value={data.speaddress}
                onChange={handleChange}
                required
                className="border border-gray-300 p-[6px_10px] text-[0.9rem] w-full outline-none focus:border-gray-400  "
              />
            </div>

            <div className="flex flex-wrap md:flex-nowrap gap-[15px]">
              <div className="flex flex-col gap-1 w-full">
                <label htmlFor="" className="text-[0.9rem] font-medium">
                  Phường/xã
                </label>
                <select
                  name="ward"
                  required
                  value={data.ward}
                  onChange={handleChange}
                  className="border border-gray-300 p-[6px_10px] text-[0.9rem] w-full outline-none focus:border-gray-400  "
                >
                  <option value="">Chọn Phường/xã</option>
                  {selectedProvince?.wards.map((ward, idx) => (
                    <option key={idx} value={ward.name}>
                      {ward.name}
                    </option>
                  ))}
                </select>
              </div>

              <div className="flex flex-col gap-1 w-full">
                <label htmlFor="" className="text-[0.9rem] font-medium">
                  Tỉnh/thành phố
                </label>
                <select
                  name="city"
                  value={data.city}
                  onChange={(e) =>
                    setData((prev) => ({
                      ...prev,
                      city: e.target.value,
                      ward: "",
                    }))
                  }
                  required
                  className="border border-gray-300 p-[6px_10px] text-[0.9rem] w-full outline-none focus:border-gray-400  "
                >
                  <option value="">Chọn Tỉnh/thành phố</option>
                  {provinces?.map((province) => (
                    <option key={province.id} value={province.province}>
                      {province.province}
                    </option>
                  ))}
                </select>
              </div>
            </div>

            <div
              className={`flex flex-col gap-1 ${
                lat && lng
                  ? "border-2 border-green-500"
                  : "border-2 border-red-500"
              }`}
            >
              <LeafletMap lat={lat} lng={lng} fullAddress={fullAddress} />
            </div>
          </div>
        </div>

        <div className="flex justify-center gap-6">
          <button
            disabled={isLoading}
            type="submit"
            className="p-[6px_10px] bg-teal-500 text-white text-[0.9rem] font-medium text-center hover:bg-teal-600 rounded-sm"
          >
            {isLoading ? "Đang thêm..." : "Thêm"}
          </button>

          <Link
            to="/restaurant/restaurants"
            className="p-[6px_10px] bg-red-500 text-white text-[0.9rem] text-center hover:bg-red-600 rounded-sm"
          >
            Trở về
          </Link>
        </div>
      </form>
    </div>
  );
}

export default AddRestaurant;
