export interface User {
  id?: string;
  fullname: string;
  email: string;
  phone: string;
  password?: string;
  status?: number;
  role?: number;
  createdAt?: string;
}

export interface Product {
  id: string;
  name: string;
  image: string;
  price: number;
  status: number;
  restaurantId: string;
  restaurantName: string;
  createdAt?: string;
}

export interface Order {
  id: string;
  orderCode: string;
  fullname: string;
  phone: string;
  speaddress: string;
  city: string;
  ward: string;
  location: Location;
  paymethod: string;
  status: number;
  total: number;
  createdAt: string;
  items: OrderItem[];
  delivery?: Delivery;
}

export interface OrderItem {
  productId: string;
  name: string;
  image: string;
  price: number;
  quantity: number;
}

export interface Payment {
  id: string;
  orderId: string;
  transactionId: string;
  paymethod: string;
  orderCode: string;
  amount: number;
  status: number;
  createdAt: string;
}

export interface CartItem {
  productId: string;
  image: string;
  name: string;
  price: string;
  quantity: number;
}

export interface Cart {
  id?: string;
  user?: string;
  items: CartItem[];
}

export interface Location {
  latitude: number;
  longitude: number;
}

export interface Drone {
  id?: string;
  restaurantId: string;
  restaurantName?: string;
  model: string;
  capacity: number;
  battery: number;
  range: number;
  status?: number;
  createdAt?: string;
}

export interface Delivery {
  id?: string;
  orderId: string;
  orderCode: string;
  destination?: Location;
  droneId: string;
  model?: string;
  restaurantId: string;
  restaurantName?: string;
  restaurantLocation?: Location;
  currentLocation: Location;
  createdAt?: string;
}

export interface Restaurant {
  id?: string;
  name: string;
  speaddress: string;
  ward: string;
  city: string;
  location: Location;
  userId: string;
  fullname?: string;
  status: number;
  createdAt?: string;
}

export type Ward = {
  name: string;
  mergedFrom: string[];
};

export type Province = {
  id: string;
  province: string;
  wards: Ward[];
};

export type NominatimAddress = {
  lat: string;
  lon: string;
  display_name: string;
  address: {
    house_number?: string;
    road?: string;
    suburb?: string;
    city?: string;
    town?: string;
    village?: string;
  };
};
