import host from "../config";

export const getAllBrands = async () => {
  
  const response = await fetch(`http://${host}:8080/car/api/brand`);

  const result = await response.json();

  return result;
};

export const createBrand = async (user, data) => {
  return await fetch(`http://${host}:8080/car/api/brand`, {
    method: "POST",
    headers: {
      Authorization: `Bearer ${user.token}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify(data),
  });
};

export const getBrandById = async (user, id) => {
  const queryParams = new URLSearchParams({
    brandId: id,
  });
  return await fetch(`http://${host}:8080/car/api/brand?${queryParams}`, {
    method: "GET",
    headers: {
      Authorization: `Bearer ${user.token}`,
      "Content-Type": "application/json",
    },
  });
};

export const updateBrand = async (user, brand) => {
  const queryParams = new URLSearchParams({
    id: brand.id,
  });

  return await fetch(`http://${host}:8080/car/api/brand?${queryParams}`, {
    method: "PATCH",
    headers: {
      Authorization: `Bearer ${user.token}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify(brand),
  });
};

export const deleteBrand = async (user, brandId) => {
  const queryParams = new URLSearchParams({
    id: brandId,
  });
  return await fetch(`http://${host}:8080/car/api/brand?${queryParams}`, {
    method: "DELETE",
    headers: {
      Authorization: `Bearer ${user.token}`,
      "Content-Type": "application/json",
    },
  });
};
