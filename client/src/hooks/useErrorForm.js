import { useState } from "react";

export const useErrorLoginForm = (initValues) => {
  const [errors, setErrors] = useState(initValues);

  const onChangeError = (e) => {
    const { name, value } = e.target;
    checkEmpty(name, value, errors, setErrors);
  };

  return { errors, onChangeError };
};

export const useErrorRegisterForm = (initValues) => {
  const [errors, setErrors] = useState(initValues);

  const onChangeError = (e) => {
    const { name, value } = e.target;
    if (name === "username") {
      checkLength(name, value, 4, errors, setErrors);
    }
    if (name === "password") {
      checkLength(name, value, 5, errors, setErrors);
    }
    if (name === "firstName" || name === "lastName") {
      checkLength(name, value, 3, errors, setErrors);
    }
    checkEmpty(name, value, errors, setErrors);
  };

  return { errors, onChangeError };
};

export const useErrorOfferForm = (initValues) => {
  const [errors, setErrors] = useState(initValues);

  const onChangeError = (e) => {
    const { name, value } = e.target;

    checkEmpty(name, value, errors, setErrors);
  };

  return { errors, onChangeError };
};

const checkEmpty = (name, value, errors, setErrors) => {
  if (value.length === 0) {
    setErrors((state) => ({ ...state, [name]: `${name} must not be blank` }));
  } else {
    if (errors[name].includes("must not be blank")) {
      setErrors((state) => ({ ...state, [name]: "" }));
    }
  }
};

const checkLength = (name, value, number, errors, setErrors) => {
  if (value.length < parseInt(number)) {
    setErrors((state) => ({
      ...state,
      [name]: `${name} must be at least ${number} chars long`,
    }));
  } else {
    if (
      errors[name].includes(`${name} must be at least ${number} chars long`)
    ) {
      setErrors((state) => ({ ...state, [name]: "" }));
    }
  }
};
