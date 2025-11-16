
import AccountInfo from "./AccountInfo";
import SideBarMenu from "../SideMenuBar";
function Account() {

  return (
    <>
      <section className="mb-[40px]">
        <div className="w-full max-w-[1200px] mx-auto">
          <div className="flex justify-center flex-wrap gap-5">
            <SideBarMenu />

            <AccountInfo />
          </div>
        </div>
      </section>
    </>
  );
}

export default Account;
