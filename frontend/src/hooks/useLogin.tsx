import axios from "axios";
import Cookies from "js-cookie";
import { useState } from "react";
import { toast } from "react-hot-toast";

export default function useLogin() {
  const [isLoading, setIsLoading] = useState(false);

  const handleLogin = async (email: string, password: string) => {
    setIsLoading(true);
    try {
      const url = `${import.meta.env.VITE_BACKEND_URL}/user/login`;
      const res = await axios.post(url, { email, password });
      const { token, role } = res.data;

      const path = window.location.pathname;

      const isAdminPage = path.startsWith("/admin");
      const isRestaurantPage = path.startsWith("/restaurant");
      const isClientPage = !isAdminPage && !isRestaurantPage;

      // Admin chỉ được vào /admin
      if (role === 0 && !isAdminPage) {
        toast.error(
          "Tài khoản quản trị viên chỉ được phép đăng nhập ở trang quản trị viên."
        );
        return;
      }

      // Restaurant owner chỉ được vào /restaurant
      if (role === 1 && !isRestaurantPage) {
        toast.error(
          "Tài khoản nhà hàng chỉ được phép đăng nhập ở trang nhà hàng."
        );
        return;
      }

      // Client chỉ được vào trang khách hàng (/)
      if (role === 2 && !isClientPage) {
        toast.error(
          "Tài khoản khách hàng chỉ được phép đăng nhập ở trang Khách hàng."
        );
        return;
      }

      toast.success("Đăng nhập thành công");

      if (role === 2) {
        Cookies.set("token-client", token, {
          expires: 1,
          sameSite: "strict",
          secure: import.meta.env.VITE_ENV === "production",
        });
        window.location.href = "/";
      } else if (role === 0) {
        Cookies.set("token-admin", token, {
          expires: 1,
          sameSite: "strict",
          secure: import.meta.env.VITE_ENV === "production",
        });
        window.location.href = "/admin/account";
      } else if (role === 1) {
        Cookies.set("token-restaurant", token, {
          expires: 1,
          sameSite: "strict",
          secure: import.meta.env.VITE_ENV === "production",
        });
        window.location.href = "/restaurant/account";
      }
    } catch (err: any) {
      console.error("Lỗi:", err);
      throw err;
    } finally {
      setIsLoading(false);
    }
  };

  return { handleLogin, isLoading };
}
