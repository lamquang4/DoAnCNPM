import { HashLink } from "react-router-hash-link";

function MainBanner() {
  return (
    <>
      <section className="mb-[40px] text-black">
        <div
          className="relative h-96 flex items-center"
          style={{
            backgroundImage: "url('/assets/banner/mainbanner1.jpg')",
            backgroundSize: "cover",
            backgroundPosition: "center",
          }}
        >
          <div className="absolute inset-0 bg-gray-200 opacity-20"></div>

          <div className="relative z-10 w-full px-6">
            <div className="max-w-6xl mx-auto text-center">
              <h1 className="text-4xl md:text-5xl font-bold text-white mb-4 leading-tight">
                Hưởng thức món ngon
              </h1>

              <div className="flex flex-col sm:flex-row gap-4 justify-center">
                <HashLink
                  smooth
                  to="/#menu"
                  className="bg-primary text-white px-4 py-2 rounded-button text-[0.9rem] font-semibold bg-[#C62028] transition-colors whitespace-nowrap"
                >
                  Thực đơn
                </HashLink>
              </div>
            </div>
          </div>
        </div>
      </section>
    </>
  );
}

export default MainBanner;
