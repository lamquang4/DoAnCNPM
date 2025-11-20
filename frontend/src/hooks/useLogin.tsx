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
