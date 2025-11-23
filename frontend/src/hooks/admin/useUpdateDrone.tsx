import axios from "axios";
import { useState } from "react";
import toast from "react-hot-toast";
import type { Drone } from "../../types/type";

export default function useUpdateDrone(id: string) {
  const [isLoading, setIsLoading] = useState(false);

  const updateDrone = async (data: Drone) => {
    if (!id) return;
    const loadingToast = toast.loading("Đang cập nhật...");
    setIsLoading(true);
    try {
      const url = `${import.meta.env.VITE_BACKEND_URL}/drone/${id}`; // đổi endpoint
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

  return { updateDrone, isLoading };
}
