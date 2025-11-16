import axios from "axios";
import useSWR from "swr";
import type { Drone } from "../types/type";

interface ResponseType {
  drones: Drone[];
}

const fetcher = (url: string) => axios.get(url).then((res) => res.data);

export default function useGetAvailableDrones() {
  const url = `${import.meta.env.VITE_BACKEND_URL}/drone/available`;

  const { data, error, isLoading, mutate } = useSWR<ResponseType>(
    url,
    fetcher,
    {
      shouldRetryOnError: false,
      revalidateOnFocus: false,
    }
  );

  return {
    drones: data?.drones ?? [],
    isLoading,
    error,
    mutate,
  };
}
