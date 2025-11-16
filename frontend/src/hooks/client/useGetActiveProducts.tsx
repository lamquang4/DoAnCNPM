import { useSearchParams } from "react-router-dom";
import axios from "axios";
import useSWR from "swr";
import type { Product } from "../../types/type";

interface ResponseType {
  products: Product[];
  totalPages: number;
  total: number;
}

const fetcher = (url: string) => axios.get(url).then((res) => res.data);

export default function useGetActiveProducts() {
  const [searchParams] = useSearchParams();

  const page = parseInt(searchParams.get("page") || "1", 10);

  const query = new URLSearchParams();
  if (page) query.set("page", page.toString());

  const url = `${
    import.meta.env.VITE_BACKEND_URL
  }/product?${query.toString()}`;

  const { data, error, isLoading, mutate } = useSWR<ResponseType>(
    url,
    fetcher,
    {
      shouldRetryOnError: false,
      revalidateOnFocus: false,
    }
  );

  return {
    products: data?.products ?? [],
    totalPages: data?.totalPages || 1,
    totalItems: data?.total || 0,
    currentPage: page,
    isLoading,
    error,
    mutate,
  };
}