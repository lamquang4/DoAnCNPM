import { memo, useEffect, useState } from "react";
import { MapContainer, Marker, Popup, TileLayer, useMap } from "react-leaflet";
import L from "leaflet";
import type { Delivery, Order, Restaurant, Location } from "../types/type";

const restaurantIcon = new L.Icon({
  iconUrl: "/assets/restaurant.png",
  iconSize: [35, 35],
});
const userIcon = new L.Icon({
  iconUrl: "/assets/location.png",
  iconSize: [35, 35],
});
const droneIcon = new L.Icon({
  iconUrl: "/assets/drone.png",
  iconSize: [35, 35],
});

type Props = {
  order: Order;
  restaurant: Restaurant;
};

function MapController({
  order,
  deliveryStatus,
}: {
  order: Order;
  deliveryStatus: number;
}) {
  const map = useMap();

  useEffect(() => {
    if (deliveryStatus === 1) {
      // Zoom map tới khách hàng khi drone bắt đầu di chuyển
      map.setView([order.location.latitude, order.location.longitude], 15);
    }
  }, [deliveryStatus, map, order.location]);

  return null;
}

function DeliveryMap({ order, restaurant }: Props) {
  const [droneLocation, setDroneLocation] = useState<Location>({
    ...restaurant.location,
  });
  const [deliveryStatus, setDeliveryStatus] = useState<number>(0); // 0: chưa giao, 1: đang di chuyển, 2: đã giao

  // Tính khoảng cách và di chuyển drone
  useEffect(() => {
    if (deliveryStatus !== 1) return;

    const interval = setInterval(() => {
      const step = 0.0005; // tốc độ di chuyển giả lập
      const { latitude: lat1, longitude: lng1 } = droneLocation;
      const { latitude: lat2, longitude: lng2 } = order.location;

      const deltaLat = lat2 - lat1;
      const deltaLng = lng2 - lng1;
      const distance = Math.sqrt(deltaLat ** 2 + deltaLng ** 2);

      if (distance < step) {
        setDroneLocation({ ...order.location });
        setDeliveryStatus(2);
        clearInterval(interval);
        return;
      }

      setDroneLocation({
        latitude: lat1 + (deltaLat / distance) * step,
        longitude: lng1 + (deltaLng / distance) * step,
      });
    }, 100);

    return () => clearInterval(interval);
  }, [deliveryStatus, droneLocation, order.location]);

  return (
    <div>
      <MapContainer
        center={[restaurant.location.latitude, restaurant.location.longitude]}
        zoom={15}
        className="w-full h-[400px] z-5"
      >
        <TileLayer url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png" />

        <Marker
          position={[
            restaurant.location.latitude,
            restaurant.location.longitude,
          ]}
          icon={restaurantIcon}
        >
          <Popup>
            <strong>{restaurant.name}</strong>
          </Popup>
        </Marker>

        <Marker
          position={[order.location.latitude, order.location.longitude]}
          icon={userIcon}
        >
          <Popup>
            <strong>{order.fullname}</strong>
            <br />
            {order.speaddress}, {order.ward}, {order.city}
          </Popup>
        </Marker>

        <Marker
          position={[droneLocation.latitude, droneLocation.longitude]}
          icon={droneIcon}
        >
          <Popup>
            <strong>Drone đang giao</strong>
          </Popup>
        </Marker>

        <MapController order={order} deliveryStatus={deliveryStatus} />
      </MapContainer>
    </div>
  );
}

export default memo(DeliveryMap);
