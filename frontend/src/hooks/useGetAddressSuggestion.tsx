import axios from "axios";
import useSWR from "swr";
import type { NominatimAddress } from "../types/type";

const fetcher = (url: string) => axios.get(url).then((res) => res.data);

export function useGetAddressSuggestions(query: string) {
  const { data, isLoading } = useSWR<NominatimAddress[]>(
    query
      ? `https://nominatim.openstreetmap.org/search?format=json&q=${encodeURIComponent(
          query
        )}&addressdetails=1&limit=5&countrycodes=vn`
      : null,
    fetcher,
    {
      shouldRetryOnError: false,
      revalidateOnFocus: false,
    }
  );

  return {
    suggestions: data ?? [],
    isLoading,
  };
}
