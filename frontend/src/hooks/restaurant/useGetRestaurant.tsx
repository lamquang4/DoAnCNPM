import axios from "axios";
import useSWR from "swr";
import type { Restaurant } from "../../types/type";

const fetcher = (url: string) => axios.get(url).then((res) => res.data);

export default function useGetRestaurant(id: string) {
  const url = `${import.meta.env.VITE_BACKEND_URL}/restaurant/${id}`;
  const { data, error, isLoading, mutate } = useSWR<Restaurant>(url, fetcher, {
    shouldRetryOnError: false,
    revalidateOnFocus: false,
  });

  return {
    restaurant: data,
    isLoading,
    error,
    mutate,
  };
}