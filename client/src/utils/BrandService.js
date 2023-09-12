export const getAllBrands = async () => {
  const response = await fetch("http://127.0.0.1:8080/car/api/brand");

  const result = await response.json();

  return result;
};

export const createBrand = async (user, data) => {
  const response = await fetch("http://127.0.0.1:8080/car/api/brand", {
    method: "POST",
    headers: {
      Authorization: `Bearer ${user.token}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify(data),
  });

  if (!response.ok) {
    const errorData = await response.text();
    throw new Error(errorData);
  }
};

export const getBrandById = async (user, id) => {
  const queryParams = new URLSearchParams({
    brandId: id,
  });
  const response = await fetch(
    `http://127.0.0.1:8080/car/api/brand?${queryParams}`,
    {
      method: "GET",
      headers: {
        Authorization: `Bearer ${user.token}`,
        "Content-Type": "application/json",
      },
    }
  );

  const result = await response.json();
  return result;
};

export const updateBrand = async (user , brand) => {
  const queryParams = new URLSearchParams({
    id: brand.id,
  });

  const response = await fetch(`http://127.0.0.1:8080/car/api/brand?${queryParams}`, {
    method: "PATCH",
    headers: {
      Authorization: `Bearer ${user.token}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify(brand),
  });

  if (!response.ok) {
    const errorData = await response.text();
    throw new Error(errorData);
  }
}
