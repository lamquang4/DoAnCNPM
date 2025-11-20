import { useEffect, useState } from "react";
import "swiper/css";
import InputImage from "../InputImage";
import Image from "../../Image";
import ImageViewer from "../../ImageViewer";
import toast from "react-hot-toast";
import { Link, useNavigate, useParams } from "react-router-dom";
import { useInputImage } from "../../../hooks/admin/useInputImage";
import useGetProduct from "../../../hooks/restaurant/useGetProduct";
import useUpdateProduct from "../../../hooks/admin/useUpdateProduct";

function EditProduct() {
  const navigate = useNavigate();
  const { id } = useParams();
  const { product, isLoading, mutate } = useGetProduct(id as string);
  const { updateProduct, isLoading: isLoadingUpdate } = useUpdateProduct(
    id as string
  );
  const [data, setData] = useState({
    name: "",
    price: 1,
    status: "",
  });
  const [openViewer, setOpenViewer] = useState<boolean>(false);
  const [viewerImage, setViewerImage] = useState<string>("");

  const max = 1;

  const {
    previewImages,
    setPreviewImages,
    selectedFiles,
    setSelectedFiles,
    handlePreviewImage,
    handleRemovePreviewImage,
  } = useInputImage(max);

  useEffect(() => {
    if (isLoading) return;

    if (!product) {
      toast.error("Sản phẩm không tìm thấy");
      navigate("/admin/products");
      return;
    }

    setData({
      name: product?.name || "",
      price: product?.price || 1,
      status: product?.status?.toString() || "",
    });
  }, [isLoading, product, navigate]);

  const handleOpenViewer = (image: string) => {
    setViewerImage(image);
    setOpenViewer(true);
  };


  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>
  ) => {
    const { name, value } = e.target;
    setData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (Number(data.price) <= 0) {
      toast.error("Giá phải lớn hơn 0");
      return;
    }

    try {
      const formData = new FormData();

      formData.append("product[name]", data.name);
      formData.append("product[price]", data.price.toString());
      formData.append("product[status]", data.status);

      if (selectedFiles && selectedFiles.length > 0) {
        selectedFiles.forEach((file) => {
          formData.append("image", file); 
        });
      }

      await updateProduct(formData);

      setPreviewImages([]);
      setSelectedFiles([]);

      mutate();
    } catch (err: any) {
      toast.error(err?.response?.data?.message);
    }
  };

  return (
    <>
      <div className="py-[30px] sm:px-[25px] px-[15px] bg-[#F1F4F9] h-auto">
        <form className="flex flex-col gap-7 w-full" onSubmit={handleSubmit}>
          <h2 className="text-[#74767d]">Chỉnh sửa đồ ăn</h2>

          <div className="flex gap-[25px] w-full flex-col">
            <div className="md:p-[25px] p-[15px] bg-white rounded-md w-full space-y-[20px]">
              <InputImage
                InputId="images-food"
                previewImages={previewImages}
                onPreviewImage={handlePreviewImage}
                onRemovePreviewImage={handleRemovePreviewImage}
                blockIndex={0}
              />

              <div className="flex justify-center items-center">
                <div
                  className="w-[150px] border border-gray-300 cursor-pointer"
                  onClick={() => {
                    if (product?.image)
                      handleOpenViewer(
                        `${import.meta.env.VITE_BACKEND_URL}${product.image}`
                      );
                  }}
                >
                  <Image
                    source={`${import.meta.env.VITE_BACKEND_URL}${
                      product?.image
                    }`}
                    alt={product!.name}
                    className="w-full h-full"
                    loading="lazy"
                  />
                </div>
              </div>
            </div>
          </div>

          <div className="sm:p-[25px] p-[15px] bg-white rounded-md flex flex-col gap-[20px] w-full">
            <h5 className="font-bold text-[#74767d]">Thông tin chung</h5>

            <div className="flex flex-col gap-1 w-full">
              <label htmlFor="" className="text-[0.9rem] font-medium">
                Tên
              </label>
              <input
                type="text"
                value={data.name}
                onChange={handleChange}
                name="name"
                required
                className="border border-gray-300 p-[6px_10px] text-[0.9rem] w-full outline-none focus:border-gray-400  "
              />
            </div>

            <div className="flex flex-col gap-1 w-full">
              <label htmlFor="" className="text-[0.9rem] font-medium">
                Giá
              </label>
              <input
                type="number"
                name="price"
                inputMode="numeric"
                value={data.price}
                onChange={handleChange}
                required
                className="border border-gray-300 p-[6px_10px] text-[0.9rem] w-full outline-none focus:border-gray-400  "
              />
            </div>

            <div className="flex flex-wrap md:flex-nowrap gap-[15px]">
              <div className="flex flex-col gap-1 w-full">
                <label htmlFor="" className="text-[0.9rem] font-medium">
                  Tình trạng
                </label>
                <select
                  name="status"
                  required
                  onChange={handleChange}
                  value={data.status}
                  className="border border-gray-300 p-[6px_10px] text-[0.9rem] w-full outline-none focus:border-gray-400  "
                >
                  <option value="">Chọn tình trạng</option>
                  <option value="0">Ẩn</option>
                  <option value="1">Hiện</option>
                </select>
              </div>
            </div>
          </div>

          <div className="flex justify-center gap-6">
            <button
              type="submit"
              className="p-[6px_10px] bg-teal-500 text-white text-[0.9rem] font-medium text-center hover:bg-teal-600 rounded-sm"
            >
              {isLoadingUpdate ? "Đang cập nhật..." : "Cập nhật"}
            </button>
            <Link
              to="/restaurant/products"
              className="p-[6px_10px] bg-red-500 text-white text-[0.9rem] text-center hover:bg-red-600 rounded-sm"
            >
              Trở về
            </Link>
          </div>
        </form>
      </div>

      {openViewer && (
        <ImageViewer
          image={viewerImage}
          open={openViewer}
          onClose={() => setOpenViewer(false)}
        />
      )}
    </>
  );
}

export default EditProduct;
