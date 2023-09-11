export const login = async (user) => {
  const response = await fetch("http://127.0.0.1:8080/car/api/user/login", {
    method: "POST",
    headers: {
      "content-type": "application/json",
    },
    body: JSON.stringify(user),
  });

  if (!response.ok) {
    const errorData = await response.text();
    throw new Error(errorData);
  }

  const data = await response.json();
  return data;
};

export const register = async (user) => {
  const {password} = user;
  const userToRegister = {
    ...user,
    'confirmPassword':password,
  }
  const response = await fetch("http://127.0.0.1:8080/car/api/user/register", {
    method: "POST",
    headers: {
      "content-type": "application/json",
    },
    body: JSON.stringify(userToRegister),
  });

  if (!response.ok) {
    const errorData = await response.text();
    throw new Error(errorData);
  }
};
