import useSWR from "swr";

const fetcher = (url: string) => fetch(url).then((res) => res.json());

export default function useGeocodeAddress(fullAddress: string | null) {
  const encoded = fullAddress ? encodeURIComponent(fullAddress) : "";

  const { data, isLoading } = useSWR(
    fullAddress
      ? `https://nominatim.openstreetmap.org/search?format=json&limit=1&q=${encoded}`
      : null,
    fetcher,
    {
      shouldRetryOnError: false,
      revalidateOnFocus: false,
    }
  );

  if (!data || !data[0]) {
    return { lat: null, lng: null, isLoading };
  }

  return {
    lat: parseFloat(data[0].lat),
    lng: parseFloat(data[0].lon),
    isLoading,
  };
}
