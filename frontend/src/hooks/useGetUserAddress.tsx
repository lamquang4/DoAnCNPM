import useSWR from "swr";
import type { Location } from "../types/type";

const fetcher = (url: string) => fetch(url).then((res) => res.json());

export function useGetUserAddress(location: Location | null) {
  const { data, isLoading } = useSWR(
    location
      ? `https://nominatim.openstreetmap.org/reverse?format=jsonv2&lat=${location.latitude}&lon=${location.longitude}`
      : null,
    fetcher,
    {
      shouldRetryOnError: false,
      revalidateOnFocus: false,
    }
  );

  const address = data
    ? [
        data.address.house_number,
        data.address.road,
        data.address.suburb,
        data.address.city || data.address.town || data.address.village,
      ]
        .filter(Boolean)
        .join(", ")
    : "";

  return { address, isLoading };
}
