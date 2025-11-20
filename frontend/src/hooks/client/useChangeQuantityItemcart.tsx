import axios from "axios";
import { useState } from "react";

export function useChangeQuantityItemCart() {
  const [isLoading, setIsLoading] = useState(false);

  const changeQuantity = async (
    userId: string,
    productId: string,
    quantity: number
  ) => {
    setIsLoading(true);
    if (!userId || !productId) {
      return;
    }
    try {
      const url = `${
        import.meta.env.VITE_BACKEND_URL
      }/cart/${userId}?productId=${productId}&quantity=${quantity}`;
      await axios.put(url);
    } catch (err: any) {
      console.error("Lỗi:", err);
      throw err;
    } finally {
      setIsLoading(false);
    }
  };

  return { changeQuantity, isLoading };
}
