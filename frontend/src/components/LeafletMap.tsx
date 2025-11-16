import { memo } from "react";
import { MapContainer, Marker, Popup, TileLayer } from "react-leaflet";
import L from "leaflet";
import type { Restaurant } from "../types/type";

type Props = {
  lat: number | null;
  lng: number | null;
  fullAddress: string | null;
  restaurants: Restaurant[] | null;
};

const restaurantIcon = new L.Icon({
  iconUrl: "/assets/restaurant.png",
  iconSize: [35, 35],
});
const userIcon = new L.Icon({
  iconUrl: "/assets/location.png",
  iconSize: [35, 35],
});

function LeafletMap({ lat, lng, fullAddress, restaurants }: Props) {
  return (
    <MapContainer
      center={lat && lng ? [lat, lng] : [10.762622, 106.660172]}
      zoom={15}
      className="w-full h-[400px] z-5"
    >
      <TileLayer url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png" />

      {lat && lng && (
        <Marker position={[lat, lng]} icon={userIcon}>
          <Popup>
            <strong>Vị trí của bạn</strong>
            <br />
            {fullAddress}
          </Popup>
        </Marker>
      )}

      {restaurants?.map((r) => (
        <Marker
          key={r.id}
          position={[r.location.latitude, r.location.longitude]}
          icon={restaurantIcon}
        >
          <Popup>
            <strong>{r.name}</strong>
            <br />
            {r.name} — {r.speaddress}, {r.ward}, {r.city}
          </Popup>
        </Marker>
      ))}
    </MapContainer>
  );
}

export default memo(LeafletMap);
