import axios from "axios";
import useSWR from "swr";
import type { Drone } from "../../types/type";

const fetcher = (url: string) => axios.get(url).then((res) => res.data);

export default function useGetDrone(id: string) {
  const url = `${import.meta.env.VITE_BACKEND_URL}/drone/${id}`;
  const { data, error, isLoading, mutate } = useSWR<Drone>(url, fetcher, {
    shouldRetryOnError: false,
    revalidateOnFocus: false,
  });

  return {
    drone: data,
    isLoading,
    error,
    mutate,
  };
}
