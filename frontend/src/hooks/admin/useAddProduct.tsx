import axios from "axios";
import { useState } from "react";
import toast from "react-hot-toast";

export default function useAddProduct() {
  const [isLoading, setIsLoading] = useState(false);
  const addProduct = async (formData: FormData) => {
    const loadingToast = toast.loading("Đang thêm...");
    setIsLoading(true);
    try {
      const url = `${import.meta.env.VITE_BACKEND_URL}/product`;
      await axios.post(url, formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      });
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

  return { addProduct, isLoading };
}