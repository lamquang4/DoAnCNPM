import axios from "axios";
import { useState } from "react";
import type { Order } from "../../types/type";

export default function useAddOrder() {
  const [isLoading, setIsLoading] = useState(false);

  const addOrder = async (data: Order) => {
    setIsLoading(true);
    try {
      const url = `${import.meta.env.VITE_BACKEND_URL}/order`;
      const res = await axios.post(url, data);
      return res.data;
    } catch (err) {
      console.error("Lỗi:", err);
      throw err;
    } finally {
      setIsLoading(false);
    }
  };

  return { addOrder, isLoading };
}
