import { memo, useEffect, useMemo } from "react";
import type { Province, Restaurant } from "../../../types/type";
import useGeocodeAddress from "../../../hooks/useGeocodeAddress";
import toast from "react-hot-toast";
import LeafletMap from "../../LeafletMap";

type Props = {
  data: {
    fullname: string;
    phone: string;
    speaddress: string;
    city: string;
    ward: string;
    restaurantId: string;
  };
  setData: React.Dispatch<
    React.SetStateAction<{
      fullname: string;
      phone: string;
      speaddress: string;
      city: string;
      ward: string;
      restaurantId: string;
    }>
  >;
  handleChange: (
    e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>
  ) => void;
  provinces: Province[];
  setLatLng: React.Dispatch<
    React.SetStateAction<{ lat: number | null; lng: number | null }>
  >;
};

function ShippingInfoForm({
  data,
  setData,
  handleChange,
  provinces,
  setLatLng,
}: Props) {
  const restaurants: Restaurant[] = [
    {
      id: "1",
      name: "FoodFast chi nhánh 1",
      speaddress: "12 Nguyễn Huệ",
      ward: "Bến Nghé",
      city: "Hồ Chí Minh",
      location: { latitude: 10.77986, longitude: 106.68734 },
      status: 1,
    },
    {
      id: "2",
      name: "FoodFast chi nhánh 2",
      speaddress: "150 Võ Văn Tần",
      ward: "Phường 6",
      city: "Hồ Chí Minh",
      location: { latitude: 10.85278, longitude: 106.75852 },
      status: 1,
    },
    {
      id: "3",
      name: "FoodFast chi nhánh 3",
      speaddress: "22 Võ Văn Ngân",
      ward: "Linh Chiểu",
      city: "Thủ Đức",
      location: { latitude: 10.86968, longitude: 106.80352 },
      status: 1,
    },
  ];

  const selectedProvince = useMemo(
    () => provinces?.find((p) => p.province === data.city),
    [provinces, data.city]
  );

  const fullAddress = useMemo(() => {
    if (!data.speaddress || !data.ward || !data.city) return "";
    return `${data.speaddress}, ${data.ward}, ${data.city}, Việt Nam`;
  }, [data.speaddress, data.ward, data.city]);

  const { lat, lng } = useGeocodeAddress(fullAddress);

  useEffect(() => {
    if (lat && lng) {
      setLatLng({ lat, lng });
    } else {
      setLatLng({ lat: null, lng: null });
    }
  }, [lat, lng, setLatLng]);

  return (
    <div className="space-y-[15px]">
      <h4>Thông tin giao hàng</h4>

      <div className="space-y-[5px]">
        <label htmlFor="" className="block text-[0.9rem] font-medium">
          Họ tên
        </label>
        <input
          type="text"
          name="fullname"
          value={data.fullname}
          onChange={handleChange}
          required
          className="w-full rounded-md border border-gray-200 px-2.5 py-2 text-[0.9rem] outline-none focus:z-10 focus:border-[#197FB6] focus:ring-[#197FB6]"
          placeholder="Họ tên"
        />
      </div>

      <div className="space-y-[5px]">
        <label htmlFor="" className="block text-[0.9rem] font-medium">
          Số điện thoại
        </label>
        <input
          type="number"
          inputMode="numeric"
          name="phone"
          value={data.phone}
          onChange={handleChange}
          required
          className="w-full rounded-md border border-gray-200 px-2.5 py-2 text-[0.9rem] outline-none focus:z-10 focus:border-[#197FB6] focus:ring-[#197FB6]"
          placeholder="Số điện thoại"
        />
      </div>

      <div className="space-y-[5px]">
        <label htmlFor="" className="block text-[0.9rem] font-medium">
          Địa chỉ cụ thể
        </label>
        <input
          type="text"
          name="speaddress"
          value={data.speaddress}
          onChange={handleChange}
          required
          className="w-full rounded-md border border-gray-200 px-2.5 py-2 text-[0.9rem] outline-none focus:z-10 focus:border-[#197FB6] focus:ring-[#197FB6]"
          placeholder="Địa chỉ cụ thể"
        />
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-[14px]">
        <div className="space-y-[5px]">
          <label htmlFor="" className="block text-[0.9rem] font-medium">
            Tỉnh/thành phố
          </label>
          <select
            name="city"
            required
            value={data.city}
            onChange={(e) =>
              setData((prev) => ({
                ...prev,
                city: e.target.value,
                ward: "",
              }))
            }
            className="w-full rounded-md text-[0.9rem] border border-gray-200 px-2.5 py-2 outline-none focus:z-10 focus:border-[#197FB6] focus:ring-[#197FB6]"
          >
            <option value="">Chọn Tỉnh/thành phố</option>
            {provinces?.map((province) => (
              <option key={province.id} value={province.province}>
                {province.province}
              </option>
            ))}
          </select>
        </div>

        <div className="space-y-[5px]">
          <label htmlFor="" className="block text-[0.9rem] font-medium">
            Phường/xã
          </label>
          <select
            name="ward"
            required
            value={data.ward}
            onChange={handleChange}
            className="w-full rounded-md text-[0.9rem] border border-gray-200 px-2.5 py-2 text-sm outline-none focus:z-10 focus:border-[#197FB6] focus:ring-[#197FB6]"
          >
            <option value="">Chọn Phường/xã</option>
            {selectedProvince?.wards.map((ward, idx) => (
              <option key={idx} value={ward.name}>
                {ward.name}
              </option>
            ))}
          </select>
        </div>
      </div>

      {lat && lng && (
        <div className="space-y-[5px]">
          <label htmlFor="" className="block text-[0.9rem] font-medium">
            Chi nhánh nhà hàng
          </label>
          <select
            name="restaurantId"
            required
            value={data.restaurantId}
            onChange={handleChange}
            className="w-full rounded-md text-[0.9rem] border border-gray-200 px-2.5 py-2 outline-none focus:z-10 focus:border-[#197FB6] focus:ring-[#197FB6]"
          >
            <option value="">Chọn chi nhánh nhà hàng</option>
            {restaurants?.map((restaurant) => (
              <option key={restaurant.id} value={restaurant.id}>
                {restaurant.name} — {restaurant.speaddress}, {restaurant.ward},{" "}
                {restaurant.city}
              </option>
            ))}
          </select>
        </div>
      )}

      <div
        className={`space-y-[5px] ${
          lat && lng ? "border-2 border-green-500" : "border-2 border-red-500"
        }`}
      >
        <LeafletMap
          lat={lat}
          lng={lng}
          fullAddress={fullAddress}
          restaurants={restaurants}
        />
      </div>
    </div>
  );
}

export default memo(ShippingInfoForm);
