import { Link } from "react-router-dom";
import Image from "../Image";
function Footer() {
  return (
    <footer className="bg-gray-50 border-t border-gray-200 px-[15px]">
      <div className="mx-auto w-full max-w-[1200px]">
        <div className="grid grid-cols-2 gap-8 md:grid-cols-4 py-8">
          <div className="col-span-full lg:col-span-1  space-y-4">
            <Link to={"/"}>
              <Image
                source={"/assets/logo.png"}
                alt={"logo"}
                className={"w-[80px]"}
                loading="eager"
              />
            </Link>
            <p className=" text-gray-500">
              KFC nhận đặt hàng trực tuyến và giao hàng tận nơi bằng drone.
            </p>
          </div>

          <div className="lg:mx-auto text-left space-y-4">
            <h5 className="relative font-bold text-black uppercase">
              Các trang
            </h5>
            <ul className="transition-all duration-500 text-[0.9rem] space-y-4">
              <li>
                <Link
                  to="/"
                  className=" text-gray-500 font-medium text-[0.9rem] hover:text-black"
                >
                  Trang chủ
                </Link>
              </li>

              <li>
                <a
                  href="#menu"
                  className=" text-gray-500 font-medium text-[0.9rem] hover:text-black"
                >
                  Thực đơn
                </a>
              </li>

              <li>
                <a
                  href="#restaurant"
                  className=" text-gray-500 font-medium text-[0.9rem] hover:text-black"
                >
                  Hệ thống nhà hàng
                </a>
              </li>
            </ul>
          </div>

          <div className="lg:mx-auto text-left space-y-4">
            <h5 className="relative font-bold text-black uppercase">DỊCH VỤ</h5>
            <ul className="transition-all duration-500 text-[0.9rem] space-y-4">
              <li>
                <Link
                  to="/"
                  className=" text-gray-500 font-medium text-[0.9rem] hover:text-black"
                >
                  Điều khoản sử dụng
                </Link>
              </li>

              <li>
                <Link
                  to={"/"}
                  className=" text-gray-500 font-medium text-[0.9rem] hover:text-black"
                >
                  Chính sách bảo mật thông tin cá nhân
                </Link>
              </li>

              <li>
                <Link
                  to={"/"}
                  className=" text-gray-500 font-medium text-[0.9rem] hover:text-black"
                >
                  Chính sách bảo mật thanh toán
                </Link>
              </li>

              <li>
                <Link
                  to={"/"}
                  className=" text-gray-500 font-medium text-[0.9rem] hover:text-black"
                >
                  Giới thiệu KFC
                </Link>
              </li>
            </ul>
          </div>

          <div className="lg:mx-auto text-left space-y-4">
            <h5 className="relative font-bold text-black uppercase">Hỗ trợ</h5>
            <ul className="transition-all duration-500 text-[0.9rem] space-y-4">
              <li>
                <Link
                  to="/"
                  className=" text-gray-500 font-medium text-[0.9rem] hover:text-black"
                >
                  Chính sách đổi - trả - hoàn tiền
                </Link>
              </li>

              <li>
                <Link
                  to={"/"}
                  className=" text-gray-500 font-medium text-[0.9rem] hover:text-black"
                >
                  Chính sách bảo hành - bồi hoàn
                </Link>
              </li>

              <li>
                <Link
                  to={"/"}
                  className=" text-gray-500 font-medium text-[0.9rem] hover:text-black"
                >
                  Chính sách vận chuyển
                </Link>
              </li>
            </ul>
          </div>
        </div>

        <div className="py-4 border-t border-gray-200 text-center">
          <p className="font-medium text-gray-500">© KFC Vietnam 2025</p>
        </div>
      </div>
    </footer>
  );
}

export default Footer;
