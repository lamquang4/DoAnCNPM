import { Navigate } from "react-router-dom";
import Cookies from "js-cookie";
import { jwtDecode } from "jwt-decode";

interface JwtPayload {
  id: string;
  role: number;
  exp: number;
}

interface PublicRouteProps {
  children: React.ReactNode;
  redirectPath: string;
  type: "admin" | "client" | "restaurant";
}

const PublicRoute = ({ children, redirectPath, type }: PublicRouteProps) => {
  const token =
    type === "admin"
      ? Cookies.get("token-admin")
      : type === "client"
      ? Cookies.get("token-client")
      : Cookies.get("token-restaurant");

  if (!token) return <>{children}</>;

  try {
    const decoded = jwtDecode<JwtPayload>(token);
    const role = Number(decoded.role);
    const isExpired = decoded.exp * 1000 < Date.now();
    if (isExpired) {
      if (type === "admin") Cookies.remove("token-admin");
      else if (type === "client") Cookies.remove("token-client");
      else Cookies.remove("token-restaurant");

      return <>{children}</>;
    }

    // Nếu đã login redirect theo role
    if (type === "admin" && role === 0) {
      return <Navigate to={redirectPath} replace />;
    } else if (type === "client" && role === 2) {
      return <Navigate to="/" replace />;
    } else if (type === "restaurant" && role === 1) {
      return <Navigate to={redirectPath} replace />;
    }
  } catch {
    Cookies.remove("token");
    return <>{children}</>;
  }

  return <>{children}</>;
};

export default PublicRoute;
