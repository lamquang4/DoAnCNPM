import { useState } from "react";
import Overplay from "./Overplay";
import { IoIosArrowDown } from "react-icons/io";
import { IoIosArrowUp } from "react-icons/io";
import { RiShoppingBag4Line } from "react-icons/ri";
import { Link, useLocation } from "react-router-dom";
import { GiDeliveryDrone } from "react-icons/gi";
import { IoFastFoodOutline, IoRestaurantOutline } from "react-icons/io5";
type Props = {
  menuOpen: boolean;
  toggleMenu: () => void;
};

function MenuSide({ menuOpen, toggleMenu }: Props) {
  const location = useLocation();
  const pathname = location.pathname;
  const [openMenus, setOpenMenus] = useState<Record<string, boolean>>({});

  const menuData = [
    {
      title: "Thực đơn",
      items: [
        {
          icon: <IoFastFoodOutline size={20} />,
          label: "Món ăn",
          key: "2a",
          children: [
            { label: "Danh sách món ăn", path: "/restaurant/products" },
            { label: "Thêm món ăn", path: "/restaurant/add-product" },
          ],
        },
        {
          icon: <IoRestaurantOutline size={20} />,
          label: "Nhà hàng",
          key: "4a",
          children: [
            {
              label: "Danh sách nhà hàng",
              path: "/restaurant/restaurants",
            },
            {
              label: "Thêm nhà hàng",
              path: "/restaurant/add-restaurant",
            },
          ],
        },
      ],
    },
    {
      title: "Đặt hàng",
      items: [
        {
          icon: <RiShoppingBag4Line size={20} />,
          label: "Đơn hàng",
          path: "/restaurant/orders",
        },
        {
          icon: <GiDeliveryDrone size={20} />,
          label: "Drone",
          path: "/restaurant/drones",
        },
      ],
    },
  ];

  const toggleOpen = (menu: string) => {
    setOpenMenus((prev) => ({
      ...prev,
      [menu]: !prev[menu],
    }));
  };

  return (
    <>
      <nav
        className={` ${menuOpen ? "left-0" : "left-[-100%]"} 
        ${
          menuOpen ? "xl:translate-x-[-100%] xl:p-0 xl:w-0" : "xl:translate-x-0"
        } custom-scroll fixed border top-0 h-screen w-[320px] pb-5 bg-white transition-all duration-350 ease-in-out z-100 xl:sticky overflow-y-auto border-b border-gray-200`}
      >
        <div className="mb-[20px] flex justify-center sticky top-0 bg-white px-3.5 py-4.5">
          <h2>Foodfast</h2>
        </div>
        <ul className="flex flex-col gap-[15px] font-semibold px-3.5">
          {menuData.map((group, groupIndex) => (
            <div key={groupIndex} className="flex flex-col gap-[10px]">
              <p className="  uppercase">{group.title}</p>
              {group.items.map((item, index) => (
                <li key={index}>
                  {'children' in item ? (
                    <>
                      <div
                        onClick={() => toggleOpen(item.key)}
                        className={`${
                          openMenus[item.key] ||
                          item.children.some((child) => pathname === child.path)
                            ? "text-[#C62028]"
                            : "hover:bg-gray-100"
                        } rounded-lg p-3 w-full cursor-pointer flex justify-between items-center`}
                      >
                        <p className="font-medium flex items-center gap-[10px]">
                          {item.icon} {item.label}
                        </p>
                        <button>
                          {openMenus[item.key] ||
                          item.children.some(
                            (child) => pathname === child.path
                          ) ? (
                            <IoIosArrowDown size={18} />
                          ) : (
                            <IoIosArrowUp size={18} />
                          )}
                        </button>
                      </div>
                      <ul
                        className={`max-h-0 overflow-hidden invisible transition-all duration-600 ease-in-out pl-[25px] ${
                          openMenus[item.key] ||
                          item.children.some((child) => pathname === child.path)
                            ? "max-h-fit visible"
                            : ""
                        }`}
                      >
                        {item.children.map((child, childIndex) => (
                          <li
                            key={childIndex}
                            className={`rounded-lg w-full cursor-pointer my-[5px] ${
                              pathname === child.path
                                ? "text-white bg-[#C62028]"
                                : "hover:bg-gray-100"
                            }`}
                          >
                            <Link
                              to={child.path}
                              className="text-[0.9rem] font-medium p-3"
                            >
                              {child.label}
                            </Link>
                          </li>
                        ))}
                      </ul>
                    </>
                  ) : (
                    <Link
                      to={item.path}
                      className={`${
                        pathname === item.path
                          ? "text-white bg-[#C62028]"
                          : "hover:bg-gray-100"
                      } rounded-lg p-3 w-full cursor-pointer flex justify-between items-center`}
                    >
                      <p className="font-medium flex items-center gap-[10px]">
                        {item.icon} {item.label}
                      </p>
                    </Link>
                  )}
                </li>
              ))}
            </div>
          ))}
        </ul>
      </nav>

      {menuOpen && <Overplay closeMenu={toggleMenu} />}
    </>
  );
}

export default MenuSide;
