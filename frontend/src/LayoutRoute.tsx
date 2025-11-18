import { Route, Routes } from "react-router-dom";
import LoginPage from "./pages/client/LoginPage";
import RegisterPage from "./pages/client/RegisterPage";
import HomePage from "./pages/client/HomePage";
import OrderPage from "./pages/client/OrderPage";
import OrderDetailPage from "./pages/client/OrderDetailPage";
import LoginAdminPage from "./pages/admin/LoginAdminPage";
import AdminPage from "./pages/admin/AdminPage";
import AddAdminPage from "./pages/admin/AddAdminPage";
import EditAdminPage from "./pages/admin/EditAdminPage";
import CustomerPage from "./pages/admin/CustomerPage";
import AddCustomerPage from "./pages/admin/AddCustomerPage";
import EditCustomerPage from "./pages/admin/EditCustomerPage";
import PrivateRoute from "./components/PrivateRoute";
import PublicRoute from "./components/PublicRoute";
import CheckoutPage from "./pages/client/CheckoutPage";
import OrderAdminPage from "./pages/admin/OrderAdminPage";
import OrderDetailAdminPage from "./pages/admin/OrderDetailAdminPage";
import OrderResultPage from "./pages/client/OrderResultPage";
import PaymentPage from "./pages/admin/PaymentPage";
import ProductPage from "./pages/admin/ProductPage";
import RestaurantOwnerPage from "./pages/admin/RestaurantOwnerPage";
import AddRestaurantOwnerPage from "./pages/admin/AddRestaurantOwnerPage";
import EditRestaurantOwnerPage from "./pages/admin/EditRestaurantOwnerPage";
import DronePage from "./pages/admin/DronePage";
import AddDronePage from "./pages/admin/AddDronePage";
import EditDronePage from "./pages/admin/EditDronePage";
import RestaurantPage from "./pages/admin/RestaurantPage";

function LayoutRoute() {
  return (
    <Routes>
      <Route path="/" element={<HomePage />} />
      <Route
        path="/login"
        element={
          <PublicRoute type="client" redirectPath="/">
            <LoginPage />
          </PublicRoute>
        }
      />
      <Route
        path="/register"
        element={
          <PublicRoute type="client" redirectPath="/">
            <RegisterPage />
          </PublicRoute>
        }
      />

      <Route
        path="/order"
        element={
          <PrivateRoute type="client" allowedRoles={[3]} redirectPath="/">
            <OrderPage />
          </PrivateRoute>
        }
      />
      <Route
        path="/order/:code"
        element={
          <PrivateRoute type="client" allowedRoles={[3]} redirectPath="/">
            <OrderDetailPage />
          </PrivateRoute>
        }
      />

      <Route path="/checkout" element={<CheckoutPage />} />
      <Route
        path="/order-result"
        element={
          <PrivateRoute type="client" allowedRoles={[3]} redirectPath="/">
            <OrderResultPage />
          </PrivateRoute>
        }
      />

      <Route
        path="/admin/login"
        element={
          <PublicRoute type="admin" redirectPath="/admin/account">
            <LoginAdminPage />
          </PublicRoute>
        }
      />

      <Route path="/admin/products" element={<ProductPage />} />

      <Route path="/admin/restaurant-owners" element={<RestaurantOwnerPage />} />
      <Route path="/admin/add-restaurant-owner" element={<AddRestaurantOwnerPage />} />
      <Route
        path="/admin/edit-restaurant-owner/:id"
        element={
          <PrivateRoute
            type="admin"
            allowedRoles={[0]}
            redirectPath="/admin/login"
          >
            <EditRestaurantOwnerPage />
          </PrivateRoute>
        }
      />

      <Route path="/admin/admins" element={<AdminPage />} />
      <Route path="/admin/add-admin" element={<AddAdminPage />} />
      <Route path="/admin/edit-admin/:id" element={<EditAdminPage />} />
      
      <Route path="/admin/customers" element={<CustomerPage />} />
      <Route path="/admin/add-customer" element={<AddCustomerPage />} />
      <Route
        path="/admin/edit-customer/:id"
        element={
          <PrivateRoute
            type="admin"
            allowedRoles={[0]}
            redirectPath="/admin/login"
          >
            <EditCustomerPage />
          </PrivateRoute>
        }
      />

      <Route path="/admin/drones" element={<DronePage />} />
      <Route path="/admin/add-drone" element={<AddDronePage />} />
      <Route
        path="/admin/edit-drone/:id"
        element={
          <PrivateRoute
            type="admin"
            allowedRoles={[0]}
            redirectPath="/admin/login"
          >
            <EditDronePage />
          </PrivateRoute>
        }
      />

      <Route path="/admin/restaurant" element={<RestaurantPage />} />

      <Route path="/admin/orders" element={<OrderAdminPage />} />
      <Route path="/admin/order/:id" element={<OrderDetailAdminPage />} />
      <Route path="/admin/payments" element={<PaymentPage />} />
    </Routes>
  );
}

export default LayoutRoute;
