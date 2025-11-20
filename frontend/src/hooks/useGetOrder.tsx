import axios from "axios";
import useSWR from "swr";

const fetcher = (url: string) => axios.get(url).then((res) => res.data);

export default function useGetOrder(id: string) {
  const url = `${import.meta.env.VITE_BACKEND_URL}/order/${id}`;
  const { data, error, isLoading, mutate } = useSWR<any>(url, fetcher, {
    shouldRetryOnError: false,
    revalidateOnFocus: false,
  });

  return {
    order: data,
    isLoading,
    error,
    mutate,
  };
}
