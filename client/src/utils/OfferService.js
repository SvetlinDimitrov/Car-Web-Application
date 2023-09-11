export const getAllOffers = async (user) => {
  const queryParams = new URLSearchParams({
    all: true,
  });

  const response = await fetch(
    `http://127.0.0.1:8080/car/api/offer?${queryParams}`,
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

export const getOfferById = async (id, user) => {
  const queryParams = new URLSearchParams({
    offerId: id,
  });
  const response = await fetch(
    `http://127.0.0.1:8080/car/api/offer?${queryParams}`,
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

export const createOffer = async (user, offer) => {
  const response = await fetch("http://127.0.0.1:8080/car/api/offer", {
    method: "POST",
    headers: {
      Authorization: `Bearer ${user.token}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify(offer),
  });

  if (!response.ok) {
    const errorData = await response.text();
    throw new Error(errorData);
  }
};
