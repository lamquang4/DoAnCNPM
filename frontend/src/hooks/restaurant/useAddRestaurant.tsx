import axios from "axios";
import { useState } from "react";
import toast from "react-hot-toast";
import type { Restaurant } from "../../types/type";

export default function useAddRestaurant() {
  const [isLoading, setIsLoading] = useState(false);
  const addRestaurant = async (data: Restaurant) => {
    const loadingToast = toast.loading("Đang thêm...");
    setIsLoading(true);
    try {
      const url = `${import.meta.env.VITE_BACKEND_URL}/restaurant`;
      await axios.post(url, data);
      toast.dismiss(loadingToast);
      toast.success("Thêm thành công");
    } catch (err) {
      console.error("Lỗi:", err);
      throw err;
    } finally {
      toast.dismiss(loadingToast);
      setIsLoading(false);
    }
  };

  return { addRestaurant, isLoading };
}
