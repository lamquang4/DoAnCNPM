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

  const center: [number, number] =
    deliveries.length > 0 && deliveries[0].restaurantLocation
      ? [
          deliveries[0].restaurantLocation.latitude,
          deliveries[0].restaurantLocation.longitude,
        ]
      : [userLoc.latitude, userLoc.longitude];

  return (
    <>
      {center && order?.deliveries && (
        <MapContainer
          center={center}
          zoom={15}
          className="w-full h-[400px] z-5"
        >
          <TileLayer url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png" />

          {deliveries.map((delivery, idx) => (
            <Marker
              key={`restaurant-${idx}`}
              position={[
                delivery?.restaurantLocation!.latitude,
                delivery?.restaurantLocation!.longitude,
              ]}
              icon={restaurantIcon}
            >
              <Popup>
                <strong>Nhà hàng</strong>
              </Popup>
            </Marker>
          ))}

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

          {deliveries.map((delivery, idx) =>
            delivery.currentLocation ? (
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
      )}
    </>
  );
}

export default memo(DeliveryMap);
