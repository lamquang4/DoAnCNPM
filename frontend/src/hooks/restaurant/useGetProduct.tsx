import axios from "axios";
import useSWR from "swr";
import type { Product } from "../../types/type";

const fetcher = (url: string) => axios.get(url).then((res) => res.data);

export default function useGetProduct(id: string) {
  const url = `${import.meta.env.VITE_BACKEND_URL}/product/${id}`;
  const { data, error, isLoading, mutate } = useSWR<Product>(url, fetcher, {
    shouldRetryOnError: false,
    revalidateOnFocus: false,
  });

  return {
    product: data,
    isLoading,
    error,
    mutate,
  };
}