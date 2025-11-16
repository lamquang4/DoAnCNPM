import axios from "axios";
import { useState } from "react";
import toast from "react-hot-toast";
import type { Restaurant } from "../../types/type";

export default function useUpdateRestaurantBranch(id: string) {
  const [isLoading, setIsLoading] = useState(false);
  const updateRestaurantBranch = async (data: Restaurant) => {
    if (!id) return;
    const loadingToast = toast.loading("Đang cập nhật...");
    setIsLoading(true);
    try {
      const url = `${import.meta.env.VITE_BACKEND_URL}/restaurant/${id}`;
      await axios.put(url, data);
      toast.dismiss(loadingToast);
      toast.success("Cập nhật thành công");
    } catch (err) {
      console.error("Lỗi:", err);
      throw err;
    } finally {
      toast.dismiss(loadingToast);
      setIsLoading(false);
    }
  };

  return { updateRestaurantBranch, isLoading };
}
