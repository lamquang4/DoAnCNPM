import axios from "axios";
import { useState } from "react";
import toast from "react-hot-toast";
import Swal from "sweetalert2";

export default function useUpdateStatusProduct() {
  const [isLoading, setIsLoading] = useState(false);
  const updateStatusProduct = async (id: string, status: number) => {
    const result = await Swal.fire({
      title: `Xác nhận cập nhật trạng thái?`,
      text: `Bạn có chắc muốn cập nhật trạng thái món ăn này không?`,
      icon: "warning",
      showCancelButton: true,
      confirmButtonText: "Đồng ý",
      cancelButtonText: "Hủy",
    });

    if (!result.isConfirmed || !id) {
      return;
    }

    const loadingToast = toast.loading("Đang cập nhật...");

    setIsLoading(true);
    try {
      const url = `${
        import.meta.env.VITE_BACKEND_URL
      }/product/${id}/status?status=${status}`;
      await axios.put(url);

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

  return { updateStatusProduct, isLoading };
}
