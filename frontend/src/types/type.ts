export interface User {
  id?: string;
  fullname: string;
  email: string;
  phone?: string;
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
  createdAt?: string;
}

export interface Order {
  id: string;
  orderCode: string;
  userId: string;
  fullname: string;
  phone: string;
  speaddress: string;
  city: string;
  ward: string;
  location: Location;
  accountEmail: string;
  paymethod: string;
  items: ProductBuy[];
  status: number;
  total: number;
  createdAt: string;
}

export interface ProductBuy {
  idProduct: string;
  image: string;
  name: string;
  quantity: number;
  price: number;
}

export interface Payment {
  id?: string;
  orderId: string;
  orderCode: string;
  paymethod: string;
  amount: number;
  transactionId: string;
  status: number;
  createdAt: string;
}

export interface ProductInCart {
  idProduct: string;
  image: string;
  name: string;
  price: string;
  quantity: number;
}

export interface Cart {
  id?: string;
  user?: string;
  items: ProductInCart[];
}

export interface Location {
  latitude: number;
  longitude: number;
}

export interface Drone {
  id: string;
  restaurant: Restaurant;
  model: string;
  capacity: number;
  battery: number;
  range?: number;
  status: number;
  createdAt?: string;
}

export interface Delivery {
  id: string;
  orderId: string;
  droneId: string;
  restaurantId: string;
  currentLocation: Location;
  status: number;
  createdAt?: string;
}

export interface Restaurant {
  id: string;
  name: string;
  speaddress: string;
  ward: string;
  city: string;
  location: Location;
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
