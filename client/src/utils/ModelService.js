import host from "../config";

export const getAllModel = async () => {
  const response = await fetch(`http://${host}:8080/car/api/model`);
  
  const result = await response.json();
  
    return result;
};
export const getModelById = async (user, id) => {
  const queryParams = new URLSearchParams({
    id: id,
  });
  return await fetch(`http://${host}:8080/car/api/model?${queryParams}`, {
    method: "GET",
    headers: {
      Authorization: `Bearer ${user.token}`,
      "Content-Type": "application/json",
    },
  });
};

export const createModel = async (user, model) => {
  return await fetch(`http://${host}:8080/car/api/model`, {
     method: "POST",
     headers: {
       Authorization: `Bearer ${user.token}`,
       "Content-Type": "application/json",
     },
     body: JSON.stringify(model),
   });
};

export const deleteModel = async (id , user) =>{
  const queryParams = new URLSearchParams({
    'id': id,
  });
  return await fetch(`http://${host}:8080/car/api/model?${queryParams}`, {
    method: "DELETE",
    headers: {
      Authorization: `Bearer ${user.token}`,
      "Content-Type": "application/json",
    }
  });
};

export const updateModel = async (user, model) => {
  const queryParams = new URLSearchParams({
    'id': model.id,
  });

  return await fetch(`http://${host}:8080/car/api/model?${queryParams}`, {
    method: "PATCH",
    headers: {
      Authorization: `Bearer ${user.token}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify(model),
  });
};

