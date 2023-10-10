import host from "../config";

export const getAllOffers = async (user) => {
  const queryParams = new URLSearchParams({
    all: true,
  });

  return await fetch(
    `http://${host}:8080/car/api/offer?${queryParams}`,
    {
      method: "GET",
      headers: {
        Authorization: `Bearer ${user.token}`,
        "Content-Type": "application/json",
      },
    }
  );
};

export const getOfferById = async (id, user) => {
  const queryParams = new URLSearchParams({
    offerId: id,
  });
  return await fetch(
    `http://${host}:8080/car/api/offer?${queryParams}`,
    {
      method: "GET",
      headers: {
        Authorization: `Bearer ${user.token}`,
        "Content-Type": "application/json",
      },
    }
  );
};

export const createOffer = async (user, offer) => {
 return  await fetch(`http://${host}:8080/car/api/offer`, {
    method: "POST",
    headers: {
      Authorization: `Bearer ${user.token}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify(offer),
  });
};

export const deleteOffer = async (id , user) =>{
  const queryParams = new URLSearchParams({
    'id': id,
  });
  return await fetch(`http://${host}:8080/car/api/offer?${queryParams}`, {
    method: "DELETE",
    headers: {
      Authorization: `Bearer ${user.token}`,
      "Content-Type": "application/json",
    }
  });
};

export const updateOffer = async (user, offer) => {
  const queryParams = new URLSearchParams({
    'id': offer.id,
  });

  const updateOffer = {...offer , modelName : offer.model.name}

  return await fetch(`http://${host}:8080/car/api/offer?${queryParams}`, {
    method: "PATCH",
    headers: {
      Authorization: `Bearer ${user.token}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify(updateOffer),
  });
};

