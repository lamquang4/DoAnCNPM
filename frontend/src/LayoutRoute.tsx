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
import RestaurantPage from "./pages/admin/RestaurantPage";
import EditDronePage from "./pages/admin/EditDronePage";
import ProductRestaurantPage from "./pages/restaurant/ProductRestaurantPage";
import AddProductRestaurantPage from "./pages/restaurant/AddProductRestaurantPage";
import OwnRestaurantPage from "./pages/restaurant/OwnRestaurantPage";
import AddRestaurantPage from "./pages/restaurant/AddRestaurantPage";
import EditRestaurantPage from "./pages/restaurant/EditRestaurantPage";
import OrderRestaurantPage from "./pages/restaurant/OrderRestaurantPage";
import OrderDetailRestaurantPage from "./pages/restaurant/OrderDetailRestaurantPage";
import AccountAdminPage from "./pages/admin/AccountAdminPage";
import EditProductRestaurantPage from "./pages/restaurant/EditProductRestaurantPage";
import AccountRestaurantPage from "./pages/restaurant/AccountRestaurantPage";
import AccountPage from "./pages/client/AccountPage";

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
        path="/account"
        element={
          <PrivateRoute type="client" allowedRoles={[2]} redirectPath="/">
            <AccountPage />
          </PrivateRoute>
        }
      />
      <Route
        path="/order"
        element={
          <PrivateRoute type="client" allowedRoles={[2]} redirectPath="/">
            <OrderPage />
          </PrivateRoute>
        }
      />
      <Route
        path="/order/:code"
        element={
          <PrivateRoute type="client" allowedRoles={[2]} redirectPath="/">
            <OrderDetailPage />
          </PrivateRoute>
        }
      />

      <Route
        path="/checkout"
        element={
          <PrivateRoute type="client" allowedRoles={[2]} redirectPath="/">
            <CheckoutPage />
          </PrivateRoute>
        }
      />
      <Route
        path="/order-result"
        element={
          <PrivateRoute type="client" allowedRoles={[2]} redirectPath="/">
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

      <Route
        path="/admin/account"
        element={
          <PrivateRoute
            type="admin"
            allowedRoles={[0]}
            redirectPath="/admin/login"
          >
            <AccountAdminPage />
          </PrivateRoute>
        }
      />

      <Route
        path="/admin/products"
        element={
          <PrivateRoute
            type="admin"
            allowedRoles={[0]}
            redirectPath="/admin/login"
          >
            <ProductPage />
          </PrivateRoute>
        }
      />

      <Route
        path="/admin/restaurant-owners"
        element={
          <PrivateRoute
            type="admin"
            allowedRoles={[0]}
            redirectPath="/admin/login"
          >
            <RestaurantOwnerPage />
          </PrivateRoute>
        }
      />
      <Route
        path="/admin/add-restaurant-owner"
        element={
          <PrivateRoute
            type="admin"
            allowedRoles={[0]}
            redirectPath="/admin/login"
          >
            <AddRestaurantOwnerPage />
          </PrivateRoute>
        }
      />
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

      <Route
        path="/admin/admins"
        element={
          <PrivateRoute
            type="admin"
            allowedRoles={[0]}
            redirectPath="/admin/login"
          >
            <AdminPage />
          </PrivateRoute>
        }
      />
      <Route
        path="/admin/add-admin"
        element={
          <PrivateRoute
            type="admin"
            allowedRoles={[0]}
            redirectPath="/admin/login"
          >
            <AddAdminPage />
          </PrivateRoute>
        }
      />
      <Route
        path="/admin/edit-admin/:id"
        element={
          <PrivateRoute
            type="admin"
            allowedRoles={[0]}
            redirectPath="/admin/login"
          >
            <EditAdminPage />
          </PrivateRoute>
        }
      />

      <Route
        path="/admin/customers"
        element={
          <PrivateRoute
            type="admin"
            allowedRoles={[0]}
            redirectPath="/admin/login"
          >
            <CustomerPage />
          </PrivateRoute>
        }
      />
      <Route
        path="/admin/add-customer"
        element={
          <PrivateRoute
            type="admin"
            allowedRoles={[0]}
            redirectPath="/admin/login"
          >
            <AddCustomerPage />
          </PrivateRoute>
        }
      />
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

      <Route
        path="/admin/drones"
        element={
          <PrivateRoute
            type="admin"
            allowedRoles={[0]}
            redirectPath="/admin/login"
          >
            <DronePage />
          </PrivateRoute>
        }
      />
      <Route
        path="/admin/add-drone"
        element={
          <PrivateRoute
            type="admin"
            allowedRoles={[0]}
            redirectPath="/admin/login"
          >
            <AddDronePage />
          </PrivateRoute>
        }
      />
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

      <Route
        path="/admin/restaurants"
        element={
          <PrivateRoute
            type="admin"
            allowedRoles={[0]}
            redirectPath="/admin/login"
          >
            <RestaurantPage />
          </PrivateRoute>
        }
      />

      <Route
        path="/admin/orders"
        element={
          <PrivateRoute
            type="admin"
            allowedRoles={[0]}
            redirectPath="/admin/login"
          >
            <OrderAdminPage />
          </PrivateRoute>
        }
      />
      <Route
        path="/admin/order/:id"
        element={
          <PrivateRoute
            type="admin"
            allowedRoles={[0]}
            redirectPath="/admin/login"
          >
            <OrderDetailAdminPage />
          </PrivateRoute>
        }
      />
      <Route
        path="/admin/payments"
        element={
          <PrivateRoute
            type="admin"
            allowedRoles={[0]}
            redirectPath="/admin/login"
          >
            <PaymentPage />
          </PrivateRoute>
        }
      />

      <Route
        path="/restaurant/account"
        element={
          <PrivateRoute
            type="restaurant"
            allowedRoles={[1]}
            redirectPath="/login"
          >
            <AccountRestaurantPage />
          </PrivateRoute>
        }
      />

      <Route
        path="/restaurant/products"
        element={
          <PrivateRoute
            type="restaurant"
            allowedRoles={[1]}
            redirectPath="/login"
          >
            <ProductRestaurantPage />
          </PrivateRoute>
        }
      />
      <Route
        path="/restaurant/add-product"
        element={
          <PrivateRoute
            type="restaurant"
            allowedRoles={[1]}
            redirectPath="/login"
          >
            <AddProductRestaurantPage />
          </PrivateRoute>
        }
      />
      <Route
        path="/restaurant/edit-product/:id"
        element={
          <PrivateRoute
            type="restaurant"
            allowedRoles={[1]}
            redirectPath="/login"
          >
            <EditProductRestaurantPage />
          </PrivateRoute>
        }
      />

      <Route
        path="/restaurant/restaurants"
        element={
          <PrivateRoute
            type="restaurant"
            allowedRoles={[1]}
            redirectPath="/login"
          >
            <OwnRestaurantPage />
          </PrivateRoute>
        }
      />
      <Route
        path="/restaurant/add-restaurant"
        element={
          <PrivateRoute
            type="restaurant"
            allowedRoles={[1]}
            redirectPath="/login"
          >
            <AddRestaurantPage />
          </PrivateRoute>
        }
      />
      <Route
        path="/restaurant/edit-restaurant/:id"
        element={
          <PrivateRoute
            type="restaurant"
            allowedRoles={[1]}
            redirectPath="/login"
          >
            <EditRestaurantPage />
          </PrivateRoute>
        }
      />

      <Route
        path="/restaurant/orders"
        element={
          <PrivateRoute
            type="restaurant"
            allowedRoles={[1]}
            redirectPath="/login"
          >
            <OrderRestaurantPage />
          </PrivateRoute>
        }
      />
      <Route
        path="/restaurant/order/:id"
        element={
          <PrivateRoute
            type="restaurant"
            allowedRoles={[1]}
            redirectPath="/login"
          >
            <OrderDetailRestaurantPage />
          </PrivateRoute>
        }
      />
    </Routes>
  );
}

export default LayoutRoute;
