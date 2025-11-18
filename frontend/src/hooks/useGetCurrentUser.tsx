import axios from "axios";
import useSWR from "swr";
import Cookies from "js-cookie";
import type { User } from "../types/type";

const fetcher =
  (type: "admin" | "client" | "restaurant") =>
  async (url: string): Promise<User> => {
    const token =
      type === "admin"
        ? Cookies.get("token-admin")
        : type === "client"
        ? Cookies.get("token-client")
        : Cookies.get("token-restaurant");

    const res = await axios.get(url, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    return res.data;
  };

export default function useGetCurrentUser(type: "admin" | "client" | "restaurant") {
  const url = null;

  const { data, error, isLoading, mutate } = useSWR<User>(url, fetcher(type));

  return {
    user: data,
    isLoading,
    error,
    mutate,
  };
}
