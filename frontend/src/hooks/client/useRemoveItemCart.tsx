import axios from "axios";
import { useState } from "react";

export function useRemoveItemCart() {
  const [isLoading, setIsLoading] = useState(false);

  const removeItem = async (userId: string, productId: string) => {
    setIsLoading(true);
    if (!userId) {
      return;
    }

    try {
      const url = `${import.meta.env.VITE_BACKEND_URL}/cart/${userId}?productId=${productId}`;
      await axios.delete(url);
    } catch (err: any) {
      console.error("Lỗi:", err);
      throw err;
    } finally {
      setIsLoading(false);
    }
  };

  return { removeItem, isLoading };
}
