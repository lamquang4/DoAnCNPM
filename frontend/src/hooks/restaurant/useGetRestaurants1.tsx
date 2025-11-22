import axios from "axios";
import useSWR from "swr";
import type { Restaurant } from "../../types/type";

interface ResponseType {
  restaurants: Restaurant[];
  totalPages: number;
  total: number;
}

const fetcher = (url: string) => axios.get(url).then((res) => res.data);

export default function useGetRestaurants1(userId: string) {
  const url = userId
    ? `${import.meta.env.VITE_BACKEND_URL}/restaurant/user/${userId}/list`
    : null;

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
