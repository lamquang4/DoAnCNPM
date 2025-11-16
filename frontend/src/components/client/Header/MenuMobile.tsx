import { memo, useEffect } from "react";
import Overplay from "../Overplay";
type MenuMobileProps = {
  isOpen: boolean;
  toggleMenu: () => void;
};
function MenuMobile({ isOpen, toggleMenu }: MenuMobileProps) {
  useEffect(() => {
    if (isOpen) {
      document.body.style.overflow = "hidden";
    } else {
      document.body.style.overflow = "";
    }

    return () => {
      document.body.style.overflow = "";
    };
  }, [isOpen]);

  return (
    <>
      <nav
        className={`custom-scroll fixed top-0 w-full max-w-[320px] h-screen py-[20px] px-[15px] overflow-y-auto bg-white shadow-md transition-all duration-500 ease-in-out z-[22] ${
          isOpen ? "right-0 visible" : "right-[-100%] invisible"
        }`}
      >
        <div className="flex justify-end items-center">
          <button onClick={toggleMenu}>
            <svg
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              stroke="currentColor"
              strokeWidth="2"
              strokeLinecap="round"
              strokeLinejoin="round"
              className="lucide lucide-x-icon lucide-x w-3.5"
              viewBox="5 5 14 14"
            >
              <path d="M18 6 6 18"></path>
              <path d="m6 6 12 12"></path>
            </svg>
          </button>
        </div>

        <ul className="py-[20px] font-semibold text-[0.9rem] uppercase">
          <li className="border-b border-gray-300 cursor-pointer">
            <a href="/#menu" className="py-[15px]">
              Thực đơn
            </a>
          </li>

          <li className="border-b border-gray-300 cursor-pointer">
            <a href="/#restaurant" className="py-[15px]">
              Hệ thống nhà hàng
            </a>
          </li>
        </ul>
      </nav>

      {isOpen && <Overplay closeMenu={toggleMenu} IndexForZ={15} />}
    </>
  );
}

export default memo(MenuMobile);
