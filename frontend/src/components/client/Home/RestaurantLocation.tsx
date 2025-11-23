import useGetRestaurants from "../../../hooks/admin/useGetRestaurants";
import LeafletMap from "../../LeafletMap";

function RestaurantLocation() {
  const { restaurants } = useGetRestaurants();
  return (
    <section
      className="mb-[40px] px-[15px] text-black scroll-mt-[100px]"
      id="restaurant"
    >
      <div className="mx-auto max-w-[1200px] w-full">
        <h2 className="mb-[20px]">Hệ thống nhà hàng</h2>
        <LeafletMap restaurants={restaurants} />
      </div>
    </section>
  );
}

export default RestaurantLocation;
