import { useSearchParams } from "react-router-dom";
import axios from "axios";
import useSWR from "swr";
import type { Order } from "../../types/type";

interface ResponseType {
  orders: Order[];
  totalPages: number;
  total: number;
}

const fetcher = (url: string) => axios.get(url).then((res) => res.data);

export default function useGetOrders(userId: string) {
  const [searchParams] = useSearchParams();

  const page = parseInt(searchParams.get("page") || "1", 10);
  const limit = parseInt(searchParams.get("limit") || "12", 10);

  const query = new URLSearchParams();
  if (page) query.set("page", page.toString());
  if (limit) query.set("limit", limit.toString());

  const url = userId
    ? `${
        import.meta.env.VITE_BACKEND_URL
      }/order/owner/${userId}?${query.toString()}`
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
    orders: data?.orders ?? [],
    totalPages: data?.totalPages || 1,
    totalItems: data?.total || 0,
    currentPage: page,
    limit,
    isLoading,
    error,
    mutate,
  };
}
