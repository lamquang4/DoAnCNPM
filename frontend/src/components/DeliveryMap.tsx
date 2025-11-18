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
  const delivery = order.delivery;

  const restaurantLoc = delivery?.restaurantLocation;
  const droneLoc = delivery?.currentLocation;
  const userLoc = order.location;
  return (
    <MapContainer
      center={[restaurantLoc!.latitude, restaurantLoc!.longitude]}
      zoom={15}
      className="w-full h-[400px] z-5"
    >
      <TileLayer url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png" />

      <Marker
        position={[restaurantLoc!.latitude, restaurantLoc!.longitude]}
        icon={restaurantIcon}
      >
        <Popup>
          <strong>Nhà hàng</strong>
        </Popup>
      </Marker>

      <Marker position={[userLoc.latitude, userLoc.longitude]} icon={userIcon}>
        <Popup>
          <strong>Vị trí giao</strong>
          <br />
          {order.speaddress}, {order.ward}, {order.city}
        </Popup>
      </Marker>

      {droneLoc && (
        <Marker
          position={[droneLoc.latitude, droneLoc.longitude]}
          icon={droneIcon}
        >
          <Popup>
            <strong>Drone đang giao</strong>
          </Popup>
        </Marker>
      )}
    </MapContainer>
  );
}

export default memo(DeliveryMap);
