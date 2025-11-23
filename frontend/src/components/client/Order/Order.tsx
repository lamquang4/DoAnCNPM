import SideBarMenu from "../SideMenuBar";
import OrderHistory from "./OrderHistory";
function Order() {
  return (
    <>
      <section className="my-[40px]">
        <div className="w-full max-w-[1200px] mx-auto relative">
          <div className="flex justify-center flex-wrap gap-5">
            <SideBarMenu />

            <OrderHistory />
          </div>
        </div>
      </section>
    </>
  );
}

export default Order;
