import axios from "axios";
import useSWR from "swr";
import type { Restaurant } from "../../types/type";

interface ResponseType {
  restaurants: Restaurant[];
}

const fetcher = (url: string) => axios.get(url).then((res) => res.data);

export default function useGetActiveRestaurants() {
  const url = `${import.meta.env.VITE_BACKEND_URL}/restaurant/active`;

  const { data, error, isLoading, mutate } = useSWR<ResponseType>(
    url,
    fetcher,
    {
      shouldRetryOnError: false,
      revalidateOnFocus: false,
    }
  );

  return {
    restaurants: data?.restaurants ?? [],
    isLoading,
    error,
    mutate,
  };
}
