import ProductList from "./ProductList";
import MainBanner from "./MainBanner";
import RestaurantLocation from "./RestaurantLocation";

function Home() {
  const products = [
    {
      id: "p1",
      name: "Cơm gà viên Nanban",
      image: "/assets/products/com-ga-vien-nanban.png",
      price: 120000,
      status: 1,
      restaurantId: "r1",
      restaurantName: "Nhà hàng Gà Rán Tokyo",
      createdAt: new Date().toISOString(),
    },
    {
      id: "p2",
      name: "Gà miếng",
      image: "/assets/products/ga-mieng.png",
      price: 45000,
      status: 1,
      restaurantId: "r1",
      restaurantName: "Nhà hàng Gà Rán Tokyo",
      createdAt: new Date().toISOString(),
    },
  ];

  return (
    <>
      <MainBanner />

      <ProductList products={products} isLoading={false} />

      <RestaurantLocation />
    </>
  );
}

export default Home;
