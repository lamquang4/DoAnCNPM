import { Link } from "react-router-dom";
import ProfileMenu from "./ProfileMenu";
import { CiShoppingCart } from "react-icons/ci";
import { CiUser } from "react-icons/ci";
import { AiOutlineMenu } from "react-icons/ai";
import MenuMobile from "./MenuMobile";
import Overplay from "../Overplay";
import { useCallback, useEffect, useMemo, useState } from "react";
import useCurrentUser from "../../../hooks/useGetCurrentUser";
import useGetCart from "../../../hooks/client/useGetCart";
import MenuSideCart from "../MenuSideCart";
import { HashLink } from "react-router-hash-link";
function Header() {
  const { user } = useCurrentUser("client");
  const { cart } = useGetCart(user?.id || "");

  const [openSearch, setOpenSearch] = useState<boolean>(false);
  const [menuMobileOpen, setMenuMobileOpen] = useState<boolean>(false);
  const [profileMenuOpen, setProfileMenuOpen] = useState<boolean>(false);
  const [cartMenuOpen, setCartMenuOpen] = useState<boolean>(false);

  const totalQuantity = useMemo(() => {
    return (
      cart?.items.reduce((sum, item) => {
        return sum + (item?.quantity || 0);
      }, 0) || 0
    );
  }, [cart?.items]);

  const toggleMobileMenu = useCallback(() => {
    setMenuMobileOpen((prev) => !prev);
    setOpenSearch(false);
    setProfileMenuOpen(false);
  }, []);

  const toggleProfileMenu = useCallback(() => {
    setProfileMenuOpen((prev) => !prev);
    setMenuMobileOpen(false);
    setOpenSearch(false);
  }, []);

  const toggleCartMenu = useCallback(() => {
    setCartMenuOpen((prev) => !prev);
    setMenuMobileOpen(false);
    setOpenSearch(false);
  }, []);

  useEffect(() => {
    const handleResize = () => {
      if (window.innerWidth >= 1024) {
        setMenuMobileOpen(false);
        setOpenSearch(false);
      }
    };

    window.addEventListener("resize", handleResize);
    return () => window.removeEventListener("resize", handleResize);
  }, []);
  return (
    <>
      <header className="w-full bg-white sticky top-0 border-b border-gray-200 z-[15] text-black">
        <div className=" py-[20px] px-[15px] relative">
          <div className="w-full max-w-[1200px] mx-auto flex justify-between items-center">
            <Link to={"/"}>
              <h2 className="!text-[#C62028]">Foodfast</h2>
            </Link>

            <nav className="hidden lg:block">
              <ul className="flex items-center gap-[30px] text-[0.9rem] font-semibold uppercase">
                <li className="cursor-pointer relative after:content-[''] after:absolute after:-bottom-2.5 after:left-0 after:w-full after:h-[1.5px] after:bg-black after:origin-left after:scale-x-0 after:transition-transform after:duration-200 after:ease-in-out hover:after:scale-x-100">
                  <HashLink smooth to="/#menu">
                    Thực đơn
                  </HashLink>
                </li>

                <li className="cursor-pointer relative after:content-[''] after:absolute after:-bottom-2.5 after:left-0 after:w-full after:h-[1.5px] after:bg-black after:origin-left after:scale-x-0 after:transition-transform after:duration-200 after:ease-in-out hover:after:scale-x-100">
                  <HashLink smooth to="/#restaurant">
                    Hệ thống nhà hàng
                  </HashLink>
                </li>
              </ul>
            </nav>

            <div className="hidden lg:flex items-center gap-5">
              <div
                className="relative cursor-pointer group"
                onMouseEnter={toggleProfileMenu}
                onMouseLeave={toggleProfileMenu}
              >
                <CiUser size={24} />
                <ProfileMenu isOpen={profileMenuOpen} />
              </div>

              <button className="relative" onClick={toggleCartMenu}>
                <CiShoppingCart size={26} />

                <small
                  className="absolute flex items-center justify-center 
    top-[-9px] right-[-11px] 
    bg-[#C62028] text-white text-[0.7rem] font-medium leading-none 
    rounded-full w-[20px] h-[20px]"
                >
                  {totalQuantity}
                </small>
              </button>
            </div>

            {/* Mobile */}
            <div className="flex lg:hidden items-center gap-4 relative">
              <div
                className="relative cursor-pointer group"
                onMouseOver={toggleProfileMenu}
                onMouseOut={toggleProfileMenu}
              >
                <CiUser size={24} />
                <ProfileMenu isOpen={profileMenuOpen} />
              </div>

              <button className="relative" onClick={toggleCartMenu}>
                <CiShoppingCart size={26} />

                <small
                  className="absolute flex items-center justify-center 
    top-[-9px] right-[-11px] 
    bg-[#C62028] text-white text-[0.7rem] font-medium leading-none 
    rounded-full w-[20px] h-[20px]"
                >
                  {totalQuantity}
                </small>
              </button>

              <button onClick={toggleMobileMenu}>
                <AiOutlineMenu size={24} />
              </button>
            </div>
          </div>

          <MenuMobile isOpen={menuMobileOpen} toggleMenu={toggleMobileMenu} />
          <MenuSideCart isOpen={cartMenuOpen} toggleMenu={toggleCartMenu} />
        </div>
      </header>

      {openSearch && <Overplay closeMenu={toggleCartMenu} IndexForZ={12} />}
    </>
  );
}

export default Header;
