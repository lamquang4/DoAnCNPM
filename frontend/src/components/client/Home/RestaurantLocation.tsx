import LeafletMap from "../../LeafletMap";

function RestaurantLocation() {
  const restaurants = [
    {
      id: "r1",
      name: "FastFood King",
      speaddress: "12 Nguyễn Văn Bảo",
      ward: "Phường 4",
      city: "Gò Vấp",
      location: {
        latitude: 10.82224,
        longitude: 106.68752,
      },
      userId: "u1",
      fullname: "Nguyễn Văn A",
      status: 1,
      createdAt: "2025-01-01T10:00:00Z",
    },
    {
      id: "r2",
      name: "Burger House",
      speaddress: "45 Lê Lợi",
      ward: "Phường Bến Nghé",
      city: "Quận 1",
      location: {
        latitude: 10.77438,
        longitude: 106.70042,
      },
      userId: "u2",
      fullname: "Trần Thị B",
      status: 1,
      createdAt: "2025-01-02T12:00:00Z",
    },
    {
      id: "r3",
      name: "Pizza Hub",
      speaddress: "88 Trường Chinh",
      ward: "Phường 13",
      city: "Tân Bình",
      location: {
        latitude: 10.79252,
        longitude: 106.64242,
      },
      userId: "u3",
      fullname: "Lê Hoàng C",
      status: 1,
      createdAt: "2025-01-03T09:30:00Z",
    },
  ];
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
