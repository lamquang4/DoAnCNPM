import { useMemo, useState, useEffect } from "react";
import "leaflet/dist/leaflet.css";
import type { Restaurant, Location } from "../../../types/type";
import { useGetAddressSuggestions } from "../../../hooks/useGetAddressSuggestion";
import { useGetUserAddress } from "../../../hooks/useGetUserAddress";
import { getDistance } from "../../../utils/geoLocation";
import { IoSearch } from "react-icons/io5";
import LeafletMap from "../../LeafletMap";

function RestaurantLocation() {
  const [userPos, setUserPos] = useState<Location | null>(null);
  const [searchResult, setSearchResult] = useState<Location | null>(null);
  const [searchQuery, setSearchQuery] = useState<string>("");

  const { suggestions } = useGetAddressSuggestions(searchQuery);
  const locationUserAddress = searchResult ?? userPos;
  const { address: userAddress } = useGetUserAddress(locationUserAddress);

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

  const nearestRestaurants = useMemo(() => {
    const baseLat = searchResult?.latitude ?? userPos?.latitude;
    const baseLon = searchResult?.longitude ?? userPos?.longitude;
    if (baseLat == null || baseLon == null) return [];

    return restaurants
      .map((res) => ({
        ...res,
        distance: getDistance(
          baseLat,
          baseLon,
          res.location.latitude,
          res.location.longitude
        ),
      }))
      .sort((a, b) => a.distance - b.distance)
      .slice(0, 5);
  }, [userPos, searchResult]);

  // Lấy vị trí người dùng
  useEffect(() => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition((pos) => {
        setUserPos({
          latitude: pos.coords.latitude,
          longitude: pos.coords.longitude,
        });
      });
    }
  }, []);

  const handleSelectSuggestion = (item: any) => {
    const newPos = {
      latitude: parseFloat(item.lat),
      longitude: parseFloat(item.lon),
    };
    setSearchResult(newPos);
    setSearchQuery("");
  };

  const center = searchResult ??
    userPos ?? { latitude: 10.77986, longitude: 106.68734 };

  return (
    <section
      className="mb-[40px] px-[15px] scroll-mt-[100px] text-black"
      id="restaurant"
    >
      <div className="mx-auto max-w-[1200px] w-full">
        <h2 className="mb-[20px]">Hệ thống nhà hàng</h2>

        <div className="space-y-2 z-8 mb-4 text-black">
          <div className="relative">
            <div className="relative">
              <input
                type="text"
                placeholder="Nhập địa chỉ của bạn, ví dụ: 99 An Dương Vương, Phường Phú Định"
                className="w-full text-[0.9rem] outline-none p-2 border border-gray-300"
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
              />

              <button
                onClick={handleSelectSuggestion}
                className="absolute top-1/2 right-0 transform -translate-y-1/2 flex items-center justify-center text-white bg-[#C62028] h-full w-[40px]"
                type="submit"
              >
                <IoSearch size={20} />
              </button>
            </div>

            {suggestions.length > 0 && (
              <ul className="absolute top-full left-0 right-0 bg-white border max-h-60 overflow-y-auto shadow-lg">
                {suggestions.map((item, idx) => (
                  <li
                    key={idx}
                    className="p-2 hover:bg-gray-200 cursor-pointer"
                    onClick={() => handleSelectSuggestion(item)}
                  >
                    {[
                      item.address.house_number,
                      item.address.road,
                      item.address.suburb,
                      item.address.city ||
                        item.address.town ||
                        item.address.village,
                    ]
                      .filter(Boolean)
                      .join(", ")}
                  </li>
                ))}
              </ul>
            )}
          </div>

          {nearestRestaurants.length > 0 && (
            <div className="space-y-2">
              <h4>5 chi nhánh gần bạn:</h4>
              <ul className="space-y-2 text-[0.9rem]">
                {nearestRestaurants.map((res) => (
                  <li key={res.id}>
                    {res.name} — {res.speaddress}, {res.ward}, {res.city} —{" "}
                    {res.distance.toFixed(2)} km
                  </li>
                ))}
              </ul>
            </div>
          )}
        </div>

        <LeafletMap
          lat={center.latitude}
          lng={center.longitude}
          fullAddress={userAddress}
          restaurants={restaurants}
        />
      </div>
    </section>
  );
}

export default RestaurantLocation;
