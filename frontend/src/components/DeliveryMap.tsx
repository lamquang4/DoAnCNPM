import { memo } from "react";
import { MapContainer, Marker, Popup, TileLayer } from "react-leaflet";
import L from "leaflet";
import type { Order } from "../types/type";

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
};

function DeliveryMap({ order }: Props) {
  const deliveries = order.deliveries || [];
  const userLoc = order.location;

  const defaultCenter: [number, number] = [0, 0];

  const center: [number, number] =
    deliveries.length > 0 && deliveries[0].restaurantLocation
      ? [
          deliveries[0].restaurantLocation.latitude ?? 0,
          deliveries[0].restaurantLocation.longitude ?? 0,
        ]
      : userLoc
      ? [userLoc.latitude ?? 0, userLoc.longitude ?? 0]
      : defaultCenter;

  console.log(order);

  return (
    <MapContainer center={center} zoom={15} className="w-full h-[400px] z-5">
      <TileLayer url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png" />

      {deliveries.map((delivery, idx) =>
        delivery?.restaurantLocation ? (
          <Marker
            key={`restaurant-${idx}`}
            position={[
              delivery.restaurantLocation.latitude ?? 0,
              delivery.restaurantLocation.longitude ?? 0,
            ]}
            icon={restaurantIcon}
          >
            <Popup>
              <strong>Nhà hàng</strong>
            </Popup>
          </Marker>
        ) : null
      )}

      {userLoc && userLoc.latitude != null && userLoc.longitude != null && (
        <Marker
          position={[userLoc.latitude, userLoc.longitude]}
          icon={userIcon}
        >
          <Popup>
            <strong>Vị trí giao</strong>
            <br />
            {order.speaddress}, {order.ward}, {order.city}
          </Popup>
        </Marker>
      )}

      {deliveries.map((delivery, idx) =>
        delivery?.currentLocation &&
        delivery.currentLocation.latitude != null &&
        delivery.currentLocation.longitude != null ? (
          <Marker
            key={`drone-${idx}`}
            position={[
              delivery.currentLocation.latitude,
              delivery.currentLocation.longitude,
            ]}
            icon={droneIcon}
          >
            <Popup>
              <strong>Drone đang giao</strong>
            </Popup>
          </Marker>
        ) : null
      )}
    </MapContainer>
  );
}

export default memo(DeliveryMap);
