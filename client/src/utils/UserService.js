import host from "../config";

export const login = async (user) => {
  return await fetch(`http://${host}:8080/car/api/user/login`, {
    method: "POST",
    headers: {
      "content-type": "application/json",
    },
    body: JSON.stringify(user),
  });
};

export const register = async (user) => {
  const {password} = user;
  const userToRegister = {
    ...user,
    'confirmPassword':password,
  }
  return await fetch(`http://${host}:8080/car/api/user/register`, {
    method: "POST",
    headers: {
      "content-type": "application/json",
    },
    body: JSON.stringify(userToRegister),
  });
};
